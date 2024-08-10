package com.hospital.management.controller;

import com.hospital.management.config.JwtTokenUtil;
import com.hospital.management.config.JwtUserDetailsService;
import com.hospital.management.entity.BaseUser;
import com.hospital.management.payload.AuthRequestDto;
import com.hospital.management.payload.AuthResponseDto;
import com.hospital.management.payload.OTPRequestDto;
import com.hospital.management.payload.OTPVerificationDto;
import com.hospital.management.payload.PasswordChangeDto;
import com.hospital.management.payload.UserDto;
import com.hospital.management.repository.BaseUserRepository;
import com.hospital.management.service.email.EmailService;
import com.hospital.management.service.token.TokenManagement;
import com.hospital.management.utils.ApiResponse;
import com.hospital.management.utils.OTPGenerator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private TokenManagement tokenManagement;
    @Autowired
    private BaseUserRepository baseUserRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/current-user")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> createAuthenticationToken(@Valid @RequestBody AuthRequestDto authRequest) {
        AuthResponseDto response = new AuthResponseDto();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails.getUsername());
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Now you are logged in");
            response.setToken(token);
            response.setUserDetails(userDetails);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DisabledException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Session expired!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (BadCredentialsException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/logoutProcess")
    public ResponseEntity<String> logout(HttpServletRequest request, Principal principal) {
        try {
            String token = extractTokenFromRequest(request);
            if (!token.isEmpty()) {
                String username = principal.getName();
                BaseUser user = baseUserRepository.findByEmail(username);
                tokenManagement.invalidateToken(token, user.getId());
            }
            return ResponseEntity.status(HttpStatus.OK).body("Logout success");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Opps! Something wrong on server");
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationToken = request.getHeader("Authorization");
        if (authorizationToken != null && authorizationToken.startsWith("Bearer ")) {
            String token = authorizationToken.substring(7);
            return token;
        }
        return null;
    }

    @PostMapping("/verify-token")
    public ResponseEntity<ApiResponse<UserDto>> currentUser(HttpServletRequest request, Principal principal) {
        ApiResponse<UserDto> response = new ApiResponse<>();
        try {
            String token = extractTokenFromRequest(request);
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(principal.getName());

            if (jwtTokenUtil.isValidToken(token, userDetails) && !(jwtTokenUtil.isTokenExpired(token))) {
                BaseUser user = baseUserRepository.findByEmail(principal.getName());
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("You are login");
                response.setData(mapper.map(user, UserDto.class));
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Session expired");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody OTPRequestDto recipient,
            HttpSession session) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            BaseUser user = baseUserRepository.findByEmail(recipient.getEmail());
            if (user != null) {
                String otp = OTPGenerator.generateOTP();
                String subject = "Sending from Hospital";
                String message = "<div style='border:1px solid #e2e2e2; padding: 20px'>"
                        + "<h3>"
                        + "Your OTP for Hospital reset password is "
                        + "<b><a href='#'>"
                        + otp
                        + "</a></b>"
                        + " and is valid for 5 mins. Please DO NOT share this OTP with anyone to keep your account safe."
                        + "</n>"
                        + "<h2><a href='#'>"
                        + "HOSPITAL"
                        + "</a></h2>"
                        + "</h3>"
                        + "</div>";

                boolean status = emailService.sendEmail(subject, message, recipient.getEmail());
                if (status) {
                    session.setAttribute("generatedOTP", otp);
                    session.setAttribute("validTime", LocalDateTime.now());
                    session.setAttribute("recipientEmail", recipient);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("OTP sent to your email");
                    response.setData(recipient.getEmail());
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setMessage("Invalid email");
                    response.setData(null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Your email is not registered.");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOpt(@Valid @RequestBody OTPVerificationDto oTPVerificationDto,
            HttpSession session) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            String generatedOTP = (String) session.getAttribute("generatedOTP");
            LocalDateTime generatedTime = (LocalDateTime) session.getAttribute("validTime");
            String setEmail = (String) session.getAttribute("recipientEmail");
            if (oTPVerificationDto.getOtp() != null && oTPVerificationDto.getOtp().equals(generatedOTP)
                    && oTPVerificationDto.getEmail().equals(setEmail)) {
                if (checkTimeLimitForOTP(generatedTime)) {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setMessage("Your OTP is expired, Please resend OTP.");
                    response.setData(null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                } else {
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("OTP Verified Successfully.");
                    response.setData(null);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Invalid OTP");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // method for check validation time for OTP
    private boolean checkTimeLimitForOTP(LocalDateTime generatedDateTime) {
        boolean flage = false;
        long expirationTime = 5;
        LocalDateTime currentDateTime = LocalDateTime.now();
        long passedTime = ChronoUnit.MINUTES.between(generatedDateTime, currentDateTime);
        if (passedTime >= expirationTime) {
            return true;
        }
        return flage;
    }

    @PostMapping("/reset-pass?email={email}&password={newPassword}")
    public ResponseEntity<ApiResponse<String>> resetPassword(@PathVariable("email") String email,
            @PathVariable("password") String newPassword,
            HttpSession httpSession) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            String setEmail = (String) httpSession.getAttribute("recipientEmail");
            BaseUser user = baseUserRepository.findByEmail(email);
            if (setEmail.equals(email)) {
                user.setPassword(passwordEncoder.encode(newPassword));
                this.baseUserRepository.save(user);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Your Password Changed Successfully.");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Session expired, Please try again.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto,
            Principal principal) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getConfirmNewPassword())) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Password do not match.");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(principal.getName());
            BaseUser user = baseUserRepository.findByEmail(userDetails.getUsername());
            if (user != null) {
                boolean status = passwordEncoder.matches(passwordChangeDto.getOldPassword(), user.getPassword());
                if (status) {
                    user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
                    baseUserRepository.save(user);
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Your password Changed Successfully.");
                    response.setData(null);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setMessage("Your old password is incorrect.");
                    response.setData(null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setMessage("Session Timeout");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
