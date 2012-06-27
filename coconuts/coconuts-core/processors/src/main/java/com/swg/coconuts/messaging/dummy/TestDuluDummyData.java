package com.swg.coconuts.messaging.dummy;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Candidate;
import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.domain.VoteSender;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.backend.repo.KecamatanRepository;
import com.swg.coconuts.backend.repo.KelurahanRepository;
import com.swg.coconuts.backend.repo.TpsRepository;
import com.swg.coconuts.backend.repo.VoteSenderRepository;
import com.swg.coconuts.messaging.domain.IncomingMessage;
import com.swg.coconuts.messaging.repo.IncomingMessageRepository;

@Component
public class TestDuluDummyData {

	private final Logger logger=Logger.getLogger(getClass());
	
	@Autowired
	private IncomingMessageRepository repository;
	
	@Autowired
	private VoteSenderRepository senderRepository;
	
	@Autowired
	private KelurahanRepository kelurahanRepository;
	
	@Autowired
	private KecamatanRepository kecamatanRepository;
	
	@Autowired
	private TpsRepository tpsRepository;
	
	@Autowired
	private KabupatenRepository kabupatenRepository;
	
	@Autowired
	private CandidateCoupleRepository coupleRepository;
	
	
	//@PostConstruct
	public void init(){
		VoteSender sender=new VoteSender();
		sender.setCellularNumber("08996849517");
		sender.setName("Aing");
		senderRepository.save(sender);
		
		Kabupaten kabupaten=new Kabupaten();
		kabupaten.setName("Testkabupaten");
		kabupatenRepository.save(kabupaten);
		
		Kabupaten kabf=kabupatenRepository.findByName("Testkabupaten");
		
		Kecamatan kecamatan=new Kecamatan();
		kecamatan.setName("Kecamatan Super");
		kecamatan.setKabupaten(kabf);
		kecamatanRepository.save(kecamatan);
		
		Kecamatan kfound=kecamatanRepository.findByName("Kecamatan Super");
		
		Kelurahan kelurahan=new Kelurahan();
		kelurahan.setName("Kelurahan Berbahaya");
		kelurahan.setKecamatan(kfound);
		kelurahanRepository.save(kelurahan);
		
		Kelurahan kelurahan2=new Kelurahan();
		kelurahan2.setName("Kelurahan Berbahagia");
		kelurahan2.setKecamatan(kfound);
		kelurahanRepository.save(kelurahan2);
		
		Kelurahan found=kelurahanRepository.findByName("Kelurahan Berbahaya");
		Tps tps=new Tps();
		tps.setKelurahan(found);
		tps.setNomor(1);
		tpsRepository.save(tps);
		
		Tps tps2=new Tps();
		tps2.setKelurahan(found);
		tps2.setNomor(2);
		tpsRepository.save(tps2);
		
		Kelurahan found2=kelurahanRepository.findByName("Kelurahan Berbahagia");
		Tps tps3=new Tps();
		tps3.setKelurahan(found2);
		tps3.setNomor(3);
		tpsRepository.save(tps3);
		
		Tps tps4=new Tps();
		tps4.setKelurahan(found2);
		tps4.setNomor(4);
		tpsRepository.save(tps4);
		
		CandidateCouple couple=new CandidateCouple();
		couple.setNickName("SBY-JK");
		couple.setNomorUrut(1);
		Candidate head=new Candidate();
		head.setName("Susilo BY");
		head.setBirthDay(new Date());
		couple.setHead(head);
		
		Candidate vice=new Candidate();
		vice.setName("Yusuf Kalla");
		vice.setBirthDay(new Date());
		couple.setVice(vice);
		coupleRepository.save(couple);
		
		CandidateCouple couple2=new CandidateCouple();
		couple2.setNickName("MegaPro");
		couple2.setNomorUrut(2);
		Candidate head2=new Candidate();
		head2.setName("Megawati");
		head2.setBirthDay(new Date());
		couple2.setHead(head2);
		
		Candidate vice2=new Candidate();
		vice2.setName("Prabowo");
		vice2.setBirthDay(new Date());
		couple2.setVice(vice2);
		coupleRepository.save(couple2);
		
	}
	static int sby=10;
	static int megaPro=23;
	static int abstain=2;
	static int invalid=3;
	static final AtomicInteger INTEGER=new AtomicInteger();
	//@Scheduled(fixedRate=40000)
	public synchronized void dummySender(){
		sby+=INTEGER.incrementAndGet();
		megaPro+=INTEGER.incrementAndGet();
		logger.info("dummy sender");
		IncomingMessage incomingMessage=new IncomingMessage();
		incomingMessage.setContent("SUARA (TPS=1) (1=110#2=230#ABS=1#TS=2)");
		incomingMessage.setOrigin("08996849517");
	//	incomingMessage.setProcessStatus(ProcessStatus.NEW_MESSAGE);
		incomingMessage.setReceivedDate(new Date());
		repository.save(incomingMessage);
		
		IncomingMessage incomingMessage2=new IncomingMessage();
		incomingMessage2.setContent("SUARA (TPS=2) (1=110#2=230#ABS=1#TS=2)");
		incomingMessage2.setOrigin("08996849517");
		//incomingMessage2.setProcessStatus(ProcessStatus.NEW_MESSAGE);
		incomingMessage2.setReceivedDate(new Date());
		repository.save(incomingMessage2);
		
		IncomingMessage incomingMessage3=new IncomingMessage();
		incomingMessage3.setContent("SUARA (TPS=3) (1=110#2=230#ABS=1#TS=2)");
		incomingMessage3.setOrigin("08996849517");
		//incomingMessage3.setProcessStatus(ProcessStatus.NEW_MESSAGE);
		incomingMessage3.setReceivedDate(new Date());
		repository.save(incomingMessage3);
		
		IncomingMessage incomingMessage4=new IncomingMessage();
		incomingMessage4.setContent("SUARA (TPS=4) (1=110#2=230#ABS=1#TS=2)");
		incomingMessage4.setOrigin("08996849517");
		//incomingMessage4.setProcessStatus(ProcessStatus.NEW_MESSAGE);
		incomingMessage4.setReceivedDate(new Date());
		repository.save(incomingMessage4);
	}
	//@Scheduled(fixedRate=42000)
	public synchronized void dummyReport(){
		logger.info("dummy report");
		IncomingMessage incomingMessage=new IncomingMessage();
		incomingMessage.setContent("LAPOR (Tps 2 di mana yah)");
		incomingMessage.setOrigin("08996849517");
		//incomingMessage.setProcessStatus(ProcessStatus.NEW_MESSAGE);
		incomingMessage.setReceivedDate(new Date());
		repository.save(incomingMessage);
		
		IncomingMessage incomingMessage2=new IncomingMessage();
		incomingMessage2.setContent("LAPER (Tps 2 di mana yah)");
		incomingMessage2.setOrigin("08996849519");
		//incomingMessage2.setProcessStatus(ProcessStatus.NEW_MESSAGE);
		incomingMessage2.setReceivedDate(new Date());
		repository.save(incomingMessage2);
		
	}
}
