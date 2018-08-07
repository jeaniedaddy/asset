package com.philly.asset.controllers;

import com.philly.asset.models.MobileLog;
import com.philly.asset.repositories.MobileLogRepository;
import com.philly.asset.utilities.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
public class MobileLogRestController {
    @Autowired
    private MobileLogRepository mobileLogRepository;

    @PostMapping("/mobilelog/save")
    public String saveMobileLog(@RequestParam(value="ipAddress") String ipAddress, @RequestParam(value="loginId") String loginId){
        Date logDate = new Date();
        String hostName = "";
        try {
            hostName = NetUtil.convertIPtoHostName(ipAddress);
        } catch (Exception e){
            return "N";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

        MobileLog ml = new MobileLog(hostName,loginId, "", ipAddress, dateFormatter.format(now), timeFormatter.format(now));
        mobileLogRepository.save(ml);
        return "Y";
    }

//    @RequestMapping("/mobilelog/save")
//    public String saveMobileLog(@RequestParam Map<String, String> requestParams){
//
//        String ipAddress = requestParams.get("ipAddress");
//        String loginId = requestParams.get("loginid");
//        Date logDate = new Date();
//        String hostName = "";
//        try {
//            hostName = NetUtil.convertIPtoMac(ipAddress);
//        } catch (Exception e){
//            return "N";
//        }
//
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
//
//        MobileLog ml = new MobileLog(hostName,loginId, "", ipAddress, dateFormatter.format(now), timeFormatter.format(now));
//        mobileLogRepository.save(ml);
//        return "Y";
//    }

    // test methods
    @GetMapping("/mobilelog/iphosttest")
    public String iphosttest(@RequestParam(value="ipAddress") String ipAddress ){

        String hotName = "";
        try {
            hotName = NetUtil.convertIPtoHostName(ipAddress);
        } catch (Exception e){

        };

        return "IP :" + ipAddress + "  Host name :" + hotName;
    }

    @RequestMapping("/mobilelog/savetest")
    public MobileLog saveMobileLogTest(@RequestParam(value="computerName") String computerName ){
        String ipAddress = "10.10.0.6";
        Date now = new Date();
        DateFormat dateFormatDate = new SimpleDateFormat("yyyyMMdd");
        DateFormat dateFormatTime = new SimpleDateFormat("HHmmss");

        String hostName = "hostName";
        try {
            hostName = NetUtil.convertIPtoMac(ipAddress);
        } catch (Exception e){

        };

        MobileLog ml = new MobileLog(computerName,"1111", "22222", ipAddress, dateFormatDate.format(now), dateFormatTime.format(now));
        mobileLogRepository.save(ml);
        return ml;
    }
}