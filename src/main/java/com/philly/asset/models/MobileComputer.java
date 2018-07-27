package com.philly.asset.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="MobileComputers")
public class MobileComputer {
    @Id
    String id;
    String hostName;
    String assetNo;
    String modelName;

    public MobileComputer(String hostName, String assetNo, String modelName) {
        this.hostName = hostName;
        this.assetNo = assetNo;
        this.modelName = modelName;
    }

    public String getId() {
        return id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
