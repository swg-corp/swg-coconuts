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
import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;
import com.swg.coconuts.backend.repo.KecamatanRepository;
import com.swg.coconuts.initiator.report.AbstractXlsExporter;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KecamatanReportService;
import com.swg.coconuts.report.xls.VoteMapExporter.KecamatanVoteMapExporter;

@Component
public class KecamatanVoteMapExporterImpl extends AbstractXlsExporter<VoteMap> implements KecamatanVoteMapExporter{

	@Autowired
	private CandidateCoupleRepository coupleRepository;
	@Autowired
	private KecamatanRepository kecamatanRepository;
	@Autowired
	private KecamatanReportService reportService;
	
	@Override
	public Collection<VoteMap> getData(Kabupaten source) {
		List<Kecamatan> kecamatans=kecamatanRepository.findByKabupaten(source);
		Set<VoteMap> voteMaps=new HashSet<VoteMap>();
		for(Kecamatan k:kecamatans){
			VoteMap map=reportService.getVoteFor(k);
			voteMaps.add(map);
		}
		return voteMaps;
	
	}

	@Override
	protected void configureHeader(Map<Integer, String> columnTitle) {
		columnTitle.put(0, "Nama Kecamatan");
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

}
