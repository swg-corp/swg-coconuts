/**
 * 
 */
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
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;
import com.swg.coconuts.backend.repo.TpsRepository;
import com.swg.coconuts.initiator.report.AbstractXlsExporter;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.AreaReportService.TpsReportService;
import com.swg.coconuts.report.xls.VoteMapExporter.TpsVoteMapExporter;

/**
 * @author satriaprayoga
 *
 */
@Component
public class TpsVoteMapExporterImpl extends AbstractXlsExporter<VoteMap> implements TpsVoteMapExporter{

	@Autowired
	private CandidateCoupleRepository coupleRepository;
	@Autowired
	private TpsRepository tpsRepository;
	@Autowired
	private TpsReportService tpsReportService;
	
	public TpsVoteMapExporterImpl() {
		super();
	}
	
	@Override
	public Collection<VoteMap> getData(Kelurahan kelurahan) {
		List<Tps> tps=tpsRepository.findByKelurahan(kelurahan);
		Set<VoteMap> voteMaps=new HashSet<VoteMap>();
		for(Tps t:tps){
			VoteMap map=tpsReportService.getVoteFor(t);
			voteMaps.add(map);
		}
		return voteMaps;
	}

	@Override
	protected void configureHeader(Map<Integer, String> columnTitle) {
		columnTitle.put(0, "Nama TPS");
		columnTitle.put(1, "Update Terakhir");
		columnTitle.put(2, "Tidak Sah");
		List<CandidateCouple> couples=coupleRepository.findAll();
		int count=3;
		for(CandidateCouple couple:couples){
			columnTitle.put(count, couple.getNickName());
			count++;
		}
		columnTitle.put(count, "Abstain");
		
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
			int count=2;
			for(Integer s:result.values()){
				Cell voteCell=row.createCell(count);
				voteCell.setCellValue(s);
				count++;
			}
			
		}
	}
	
	private String formatDate(VoteMap map){
		DateFormat dateFormat=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		return dateFormat.format(map.getLastUpdate());
	}

}
