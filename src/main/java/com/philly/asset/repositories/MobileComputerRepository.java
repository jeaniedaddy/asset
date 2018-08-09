package com.philly.asset.repositories;

import com.philly.asset.models.MobileComputer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MobileComputerRepository  extends MongoRepository<MobileComputer,String> {
    @Query("{'hostName':?0}")
    List<MobileComputer>  findAll();
    Optional<MobileComputer> findById(String id);
    Page<MobileComputer> findAllByOrderByHostNameAsc(Pageable pageable);
    Page<MobileComputer> findAllByHostNameLikeOrderByHostNameAsc(String name, Pageable pageable);
    Iterable<MobileComputer> findAllByOrderByHostNameAsc();
}


