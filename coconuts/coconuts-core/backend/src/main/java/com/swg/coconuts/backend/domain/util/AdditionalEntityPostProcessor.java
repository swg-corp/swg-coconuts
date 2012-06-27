package com.swg.coconuts.backend.domain.util;

import java.util.List;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

public class AdditionalEntityPostProcessor implements PersistenceUnitPostProcessor {
	
	private List<String> additionalEntities;

	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		for(String entityName:additionalEntities){
			pui.addManagedClassName(entityName);
		}
	}
	
	public void setAdditionalEntities(List<String> additionalEntities) {
		this.additionalEntities = additionalEntities;
	}
	
	public List<String> getAdditionalEntities() {
		return additionalEntities;
	}

}
