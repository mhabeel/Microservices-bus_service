package com.abatplus.busroute.repository;

import com.abatplus.busroute.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository < Bus,Integer> {


}
