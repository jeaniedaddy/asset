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

    //List<MobileLog> findAllByComputerName(String computerName) ;
//    @Query("{'dateAndtime':?0}")
    Page<MobileLog> findAllByOrderByDateAndtimeDesc ( Pageable pageable);
    List<MobileLog> findAllByComputerNameOrderByDateAndtimeDesc(String computerName) ;
    Page<MobileLog> findAllByComputerNameOrderByDateAndtimeDesc(String computerName, Pageable pageable) ;

    List<MobileLog> findByLogDateBetween(String start, String end);
    List<MobileLog> findAllByComputerNameAndLogDateBetween(String computerName, String start, String end);
    List<MobileLog> findAllByLogDateGreaterThan(String start);

    @Query("{'hostName':?0}")
    List<MobileLog> findAllByComputerNameAndLogDateGreaterThan(String computerName, String start);

    List<MobileLog> findAllByComputerNameContains(String name);
    List<MobileLog> findAllByComputerNameLikeOrderByDateAndtimeDesc(String name);

}
