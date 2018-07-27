package com.philly.asset.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="mobilelogs")
public class MobileLog {
    @Id
    String id;
    String computerName;
    String loginId;
    String macAddress;
    String ipAddress;
    String dateAndtime;


    public MobileLog(String computerName, String loginId, String macAddress, String ipAddress, String dateAndtime) {
        this.computerName = computerName;
        this.loginId = loginId;
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.dateAndtime = dateAndtime;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        ipAddress = ipAddress;
    }

    public String getDateAndtime() {
        return dateAndtime;
    }

    public void setDateAndtime(String dateAndtime) {
        this.dateAndtime = dateAndtime;
    }


}
