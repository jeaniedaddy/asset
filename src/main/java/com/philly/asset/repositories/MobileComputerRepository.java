package com.philly.asset.repositories;

import com.philly.asset.models.MobileComputer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MobileComputerRepository  extends MongoRepository<MobileComputer,String> {
    public MobileComputer findFirstByHostName(String hostName);
    public List<MobileComputer>  findAll();
    //public List<MobileComputer>  findAllOrderByHostName();
    public Optional<MobileComputer> findById(String id);
}


