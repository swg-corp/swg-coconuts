package com.swg.coconuts.messaging.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.messaging.domain.SerialModem;

public interface SerialModemRepository extends JpaRepository<SerialModem, Integer>,JpaSpecificationExecutor<SerialModem>{

}
