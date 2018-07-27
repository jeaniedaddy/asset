package com.philly.asset.repositories;

import com.philly.asset.models.MobileComputer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MobileComputerRepository  extends MongoRepository<MobileComputer,String> {

}


