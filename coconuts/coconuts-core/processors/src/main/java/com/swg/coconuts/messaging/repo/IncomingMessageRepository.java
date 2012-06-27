package com.swg.coconuts.messaging.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swg.coconuts.gateway.IIncomingMessage;
import com.swg.coconuts.gateway.IIncomingMessage.ProcessStatus;
import com.swg.coconuts.messaging.domain.IncomingMessage;


public interface IncomingMessageRepository extends JpaRepository<IncomingMessage, Integer>{


	List<IIncomingMessage> findByProcessStatus(ProcessStatus processStatus);
	
	@Query(value="SELECT i FROM IncomingMessage AS i WHERE i.processStatus IN :processStatus")
	List<IIncomingMessage> findByStatusIn(@Param("processStatus") List<ProcessStatus> processStatus);

}
