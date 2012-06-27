package com.swg.coconuts.report.xls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;
import com.swg.coconuts.backend.repo.KelurahanRepository;
import com.swg.coconuts.initiator.report.AbstractXlsExporter;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KelurahanReportService;
import com.swg.coconuts.report.xls.VoteMapExporter.KelurahanVoteMapExporter;

@Component
public class KelurahanVoteMapExporterImpl extends AbstractXlsExporter<VoteMap> implements KelurahanVoteMapExporter{

	@Autowired
	private CandidateCoupleRepository coupleRepository;
	@Autowired
	private KelurahanRepository kelurahanRepository;
	@Autowired
	private KelurahanReportService reportService;
	
	public KelurahanVoteMapExporterImpl() {
		super();
	}
	
	@Override
	protected void configureHeader(Map<Integer, String> columnTitle) {
		columnTitle.put(0, "Nama Kelurahan");
		columnTitle.put(1, "Update Terakhir");
		columnTitle.put(2, "Tidak Sah");
		columnTitle.put(3, "Abstain");
		List<CandidateCouple> couples=coupleRepository.findAll();
		int count=4;
		for(CandidateCouple couple:couples){
			columnTitle.put(count, couple.getNickName());
			count++;
		}
		
	}

	@Override
	protected void configureRow(Object data, Sheet sheet, int rowNumber) {
		VoteMap map=(VoteMap)data;
		if(map!=null && !(map.getVoteResult().isEmpty())){
			Row row=sheet.createRow(rowNumber);
			Cell idCell=row.createCell(0);
			idCell.setCellValue(map.getAreaId());
			Cell dateCell=row.createCell(1);
			dateCell.setCellValue(formatDate(map));
			dateCell.setCellStyle(getBodyStyle());
			Map<String, Integer> result=map.getVoteResult();
			Cell invalidCell=row.createCell(2);
			invalidCell.setCellValue(result.get("Tidak Sah"));
			Cell abstainCell=row.createCell(3);
			abstainCell.setCellValue(result.get("Abstain"));
			int count=4;
			List<CandidateCouple> couples=coupleRepository.findAll();
			for(CandidateCouple couple:couples){
				Cell voteCell=row.createCell(count);
				voteCell.setCellValue(result.get(couple.getNickName()));
				count++;
			}
			
		}
		
	}
	
	private String formatDate(VoteMap map){
		DateFormat dateFormat=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		return dateFormat.format(map.getLastUpdate());
	}

	@Override
	public Collection<VoteMap> getData(Kecamatan source) {
		List<Kelurahan> kelurahans=kelurahanRepository.findByKecamatan(source);
		Set<VoteMap> voteMaps=new HashSet<VoteMap>();
		for(Kelurahan k:kelurahans){
			VoteMap map=reportService.getVoteFor(k);
			voteMaps.add(map);
		}
		return voteMaps;
	}

}
