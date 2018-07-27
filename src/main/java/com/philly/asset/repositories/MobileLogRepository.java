package com.philly.asset.repositories;

import com.philly.asset.models.MobileLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MobileLogRepository extends MongoRepository<MobileLog, String>{
}
