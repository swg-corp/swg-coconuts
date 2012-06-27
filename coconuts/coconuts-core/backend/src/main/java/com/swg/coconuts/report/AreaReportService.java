/**
 * 
 */
package com.swg.coconuts.report;

import java.util.Collection;
import java.util.List;

import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.initiator.param.Area;
import com.swg.coconuts.initiator.report.ReportService;
import com.swg.coconuts.initiator.report.VoteMap;

/**
 * @author satriaprayoga
 *
 */
public interface AreaReportService<T extends Area> extends ReportService<T> {

	public Collection<VoteMap> getChildVoteMap(List<?> object);
	

	public interface TpsReportService extends ReportService<Tps>{
		
	}
}
