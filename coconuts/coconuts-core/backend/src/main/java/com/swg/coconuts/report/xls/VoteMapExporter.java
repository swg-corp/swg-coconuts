package com.swg.coconuts.report.xls;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.initiator.report.VoteMapsAware;
import com.swg.coconuts.initiator.report.XlsDataExporter;

public interface VoteMapExporter<T> extends XlsDataExporter<VoteMap>,VoteMapsAware<T> {

	public interface TpsVoteMapExporter extends VoteMapExporter<Kelurahan>{
		
	}
	
	public interface KelurahanVoteMapExporter extends VoteMapExporter<Kecamatan>{
		
	}
	
	public interface KecamatanVoteMapExporter extends VoteMapExporter<Kabupaten>{
		
	}
	
	public interface KabupatenVoteMapExporter extends VoteMapExporter<Provinsi>{
		
	}
}
