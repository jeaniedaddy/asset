package com.philly.asset.repositories;

import com.philly.asset.models.MobileLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Date;
import java.util.List;

public interface MobileLogRepository extends MongoRepository<MobileLog, String>  {

    Page<MobileLog> findAllByOrderByDateAndtimeDesc ( Pageable pageable);
    Page<MobileLog> findAllByComputerNameOrderByDateAndtimeDesc(String computerName, Pageable pageable) ;
    List<MobileLog> findAllByComputerNameOrderByDateAndtimeDesc(String computerName) ;
    List<MobileLog> findAllByComputerNameAndLogDateGreaterThan(String hostName, String start);

}
