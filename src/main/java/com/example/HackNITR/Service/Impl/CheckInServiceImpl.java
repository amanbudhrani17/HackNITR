package com.example.HackNITR.Service.Impl;

import com.example.HackNITR.Entity.CheckIn;
import com.example.HackNITR.Repository.CheckInRepository;
import com.example.HackNITR.Service.CheckInService;
import org.springframework.stereotype.Service;

@Service
public class CheckInServiceImpl implements CheckInService {
    private CheckInRepository checkInRepository;
    public CheckInServiceImpl(CheckInRepository checkInRepository){
        this.checkInRepository=checkInRepository;
    }
    @Override
    public void create(CheckIn checkIn) {
        checkInRepository.save(checkIn);
    }

    @Override
    public void update(CheckIn checkIn) {

    }
}
