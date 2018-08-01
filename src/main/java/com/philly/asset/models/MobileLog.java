package com.philly.asset.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="mobilelogs")
public class MobileLog {
    @Id
    String id;
    String computerName;
    String loginId;
    String macAddress;
    String ipAddress;
    String dateAndtime;
    String logDate;
    String logTime;



    public MobileLog(String computerName, String loginId, String macAddress, String ipAddress, String logDate, String logTime) {
        this.computerName = computerName;
        this.loginId = loginId;
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.logDate = logDate;
        this.logTime = logTime;
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

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
}
