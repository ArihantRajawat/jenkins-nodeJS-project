package com.hospital.management.service.subscribe;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.management.entity.Subscribe;
import com.hospital.management.handleexception.DuplicateException;
import com.hospital.management.payload.SubscribeDto;
import com.hospital.management.repository.SubscribeRepository;

@Service
public class SubscribeServiceImpl implements SubscribeService {
    @Autowired
    private SubscribeRepository subscribeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SubscribeDto addSubscribe(SubscribeDto subscribeDto) {
        Subscribe subscribe = this.subscribeRepository.findByEmail(subscribeDto.getEmail());
        if (subscribe != null) {
            throw new DuplicateException("Already subscribe our Newslater");
        } else {
            Subscribe newSubscribe = new Subscribe();
            newSubscribe.setEmail(subscribeDto.getEmail());
            newSubscribe.setCreatedAt(LocalDateTime.now());
            this.subscribeRepository.save(newSubscribe);
            return modelMapper.map(newSubscribe, SubscribeDto.class);
        }
    }

}
