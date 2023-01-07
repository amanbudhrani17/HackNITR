package com.example.HackNITR.Controller;

import com.example.HackNITR.Entity.CheckIn;
import com.example.HackNITR.Service.CheckInService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkIn")
public class CheckInController {
    private CheckInService checkInService;
    public CheckInController(CheckInService checkInService) {
        this.checkInService=checkInService;
    }
    @PostMapping
    public String create(@RequestBody CheckIn checkIn){
        checkInService.create(checkIn);
        return "done";
    }
    @PutMapping("/cleaner/{latitude}/{longitude}")
    public String update(@RequestParam("latitude") String latitude,@RequestParam("longitude") String longitude){

    }
}
