package com.swg.coconuts.backend.init;

import com.swg.coconuts.backend.domain.Provinsi;

public interface ProvinceService {

	public void save(Provinsi provinsi);
	public Provinsi findSingle();
}
