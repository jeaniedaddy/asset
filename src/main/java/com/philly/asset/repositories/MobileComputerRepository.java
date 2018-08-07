package com.philly.asset.repositories;

import com.philly.asset.models.MobileComputer;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MobileComputerRepository  extends MongoRepository<MobileComputer,String> {
    public MobileComputer findFirstByHostName(String hostName);

    @Query("{'hostName':?0}")
    public List<MobileComputer>  findAll();

    public List<MobileComputer> findAllOrderByHostName(Sort sort);
    //public List<MobileComputer>  findAllOrderByHostName();
    public Optional<MobileComputer> findById(String id);
}


