package com.hospital.management.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.handleexception.DuplicateException;
import com.hospital.management.payload.SubscribeDto;
import com.hospital.management.service.subscribe.SubscribeService;
import com.hospital.management.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
public class SubscribeController {
    @Autowired
    private SubscribeService subscribeService;

    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponse<SubscribeDto>> subscribe(@Valid @RequestBody SubscribeDto subscribeDto) {
        ApiResponse<SubscribeDto> response = new ApiResponse<>();
        try {
            SubscribeDto responseSubscribeDto = this.subscribeService.addSubscribe(subscribeDto);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Thanks for subscribe our Newslater");
            response.setData(responseSubscribeDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DuplicateException e) {
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Opps! Something wrong on server");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
