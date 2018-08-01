package com.philly.asset.models;

public class MobileStatusView extends MobileComputer {
    public String logCount;
    public Integer percent;

    public MobileStatusView(String hostName, String assetNo, String modelName, Integer percent) {
        super(hostName, assetNo, modelName);
        this.percent = percent;

    }

    public String getLogCount() {
        return logCount;
    }

    public void setLogCount(String logCount) {
        this.logCount = logCount;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }
}
