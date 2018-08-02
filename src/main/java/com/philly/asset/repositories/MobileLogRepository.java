package com.philly.asset.repositories;

import com.philly.asset.models.MobileLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface MobileLogRepository extends MongoRepository<MobileLog, String>{
    List<MobileLog> findAllByComputerName(String computerName) ;
    List<MobileLog> findByLogDateBetween(String start, String end);
    List<MobileLog> findAllByComputerNameAndLogDateBetween(String computerName, String start, String end);
    List<MobileLog> findAllByLogDateGreaterThan(String start);
    List<MobileLog> findAllByComputerNameAndLogDateGreaterThan(String computerName, String start);
    List<MobileLog> findAllByComputerNameContains(String name);

}
