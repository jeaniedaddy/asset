package com.philly.asset.controllers;

import com.philly.asset.models.MobileLog;
import com.philly.asset.repositories.MobileLogRepository;
import com.philly.asset.utilities.MacAddress;
import com.philly.asset.utilities.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class MobileLogRestController {
    @Autowired
    private MobileLogRepository mobileLogRepository;



    @RequestMapping("/mobilelog/savetest")
    public MobileLog saveMobileLogTest(@RequestParam(value="computerName") String computerName ){ //   }, (@RequestParam(value="loginId") String loginId, (@RequestParam(value="macAddress") String macAddress,  (@RequestParam(value="ipAddress") String ipAddress, (@RequestParam(value="dateAndtime") String dateAndtime, (@RequestParam(value="isActiveDevice") String isActiveDevice ){
        String ipAddress = "10.10.0.6";
        String dateAndTime = (new Date()).toString();
        String hostName = "hostName";
        try {
            hostName = NetUtil.convertIPtoMac(ipAddress);
        } catch (Exception e){

        };

        MobileLog ml = new MobileLog(computerName,"1111", "22222", ipAddress, dateAndTime);//,loginId,macAddress,macAddress, ipAddress, dateAndtime, isActiveDevice);
        mobileLogRepository.save(ml);
        return ml;
    }

    @RequestMapping("/mobilelog/save")
    public String saveMobileLog(@RequestParam Map<String, String> requestParams){

        String ipAddress = requestParams.get("ipAddress");
        String loginId = requestParams.get("loginid");
        String dateAndTime = (new Date()).toString();
        String hostName = "hostName";
        try {
            hostName = NetUtil.convertIPtoMac(ipAddress);
        } catch (Exception e){
            return "N";
        }

        MobileLog ml = new MobileLog(hostName,loginId, "22222", ipAddress, dateAndTime);
        mobileLogRepository.save(ml);
        return "Y";
    }

    @RequestMapping("/mobilelog/iphosttest")
    public String iphosttest(@RequestParam(value="ipAddress") String ipAddress ){

        String hotName = "";
        try {
            hotName = MacAddress.convertIPtoHostName(ipAddress);
        } catch (Exception e){

        };

        return "IP :" + ipAddress + "  Host name :" + hotName;
    }

}