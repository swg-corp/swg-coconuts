package com.swg.coconuts.messaging.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.gateway.IOutgoingMessage;
import com.swg.coconuts.gateway.IOutgoingMessage.SendStatus;
import com.swg.coconuts.messaging.domain.OutgoingMessage;

public interface OutgoingMessageRepository extends JpaRepository<OutgoingMessage, Integer>,JpaSpecificationExecutor<OutgoingMessage> {

	List<IOutgoingMessage> findBySendStatus(SendStatus sendStatus);
}
