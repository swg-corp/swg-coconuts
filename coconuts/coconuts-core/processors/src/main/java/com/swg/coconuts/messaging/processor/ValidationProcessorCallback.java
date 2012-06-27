package com.swg.coconuts.messaging.processor;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.VoteSender;
import com.swg.coconuts.backend.repo.VoteSenderRepository;
import com.swg.coconuts.gateway.IIncomingMessage;
import com.swg.coconuts.gateway.processor.MessageProcessingCallback;
import com.swg.coconuts.gateway.processor.MessageProcessingException.FailedStep;
import com.swg.coconuts.gateway.processor.MessageProcessingException.MessagePostProcessingException;
import com.swg.coconuts.gateway.processor.MessageProcessingException.MessagePreProcessingException;
import com.swg.coconuts.gateway.processor.ProcessFailureInfo;

@Component
public class ValidationProcessorCallback implements MessageProcessingCallback {

	private final Logger logger=Logger.getLogger(getClass());
	
	@Autowired
	private VoteSenderRepository repository;
	

	private boolean validNumber=false;


	@Override
	public void onPreProcess(IIncomingMessage incomingMessage)
			throws MessagePreProcessingException {
		String num=incomingMessage.getOrigin();
		StringBuilder builder=new StringBuilder();
		String sixty2="62";
		String psixty2="+62";
		String zero="0";
		if(num.startsWith(sixty2)){
			builder.append(num.replace(sixty2, ""));
		}else if(num.startsWith(zero)){
			builder.append(num.replace(zero, ""));
		}else if(num.startsWith(psixty2)){
			builder.append(num.replace(psixty2, ""));
		}
		String rnum=builder.toString();
		logger.info("validasi terhadap nomor pengirim pesan yang masuk: "+rnum);
		List<VoteSender> voteSenders=repository.findByCellularNumberLike("%"+rnum);
		if(voteSenders.isEmpty()){
			
			logger.error("Nomor Pengirim bukan nomor yang dikenali system");
			throw new MessagePreProcessingException("Nomor Pengirim bukan nomor yang dikenali system", FailedStep.PRE_PROCESSING.ordinal());
		}else{
			validNumber=true;
			logger.info("validasi terhadap nomor pengirim pesan yang masuk. Nomor dikenali");
		}
		
		
	}


	@Override
	public void onPostProcess(IIncomingMessage incomingMessage)
			throws MessagePostProcessingException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProcessFailure(ProcessFailureInfo processFailureInfo) {
		// TODO Auto-generated method stub
		
	}

	public boolean isValidNumber() {
		return validNumber;
	}
	
	
	

}
