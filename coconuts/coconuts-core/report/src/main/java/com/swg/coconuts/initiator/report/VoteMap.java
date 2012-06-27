package com.swg.coconuts.initiator.report;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VoteMap implements Serializable{
	private static final long serialVersionUID = 5837171637578992120L;
	
	private Map<String, Integer> voteResult;
	
	private String areaId;
	
	private Date lastUpdate;
	
	private Integer total;
	
	public VoteMap() {
		voteResult=new HashMap<String, Integer>();
	}
	
	public Map<String, Integer> getVoteResult() {
		return voteResult;
	}
	
	public Integer getValue(String key){
		return voteResult.get(key);
	}

	@Override
	public String toString() {
		return "VoteMap [voteResult=" + voteResult + "]";
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	public String getAreaId() {
		return areaId;
	}
	
	public void append(VoteMap voteMap){
		Map<String, Integer> map=voteMap.getVoteResult();
		for(String s:map.keySet()){
			Integer vote=map.get(s);
			Integer voteIni=this.voteResult.get(s);
			if(voteIni!=null){
				voteIni+=vote;
			}else{
				voteIni=vote;
			}
			this.voteResult.put(s, voteIni);
		}
	}
	
	public Integer getTotal() {
		total=0;
		for(Integer vote:voteResult.values()){
			total+=vote;
		}
		return total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VoteMap other = (VoteMap) obj;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		return true;
	}

}
