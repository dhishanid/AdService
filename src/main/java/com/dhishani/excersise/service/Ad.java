package com.dhishani.excersise.service;

import java.io.Serializable;

public class Ad implements Serializable {
	
	private static final long serialVersionUID = -8950930842070228675L;
	private String partnerId;
	private long duration;
	private String adContent;
	private long created;
	
	
	public Ad() {
		
	}
	public Ad(String partnerId, long duration, String adContent, long lastAccessed) {
		super();
		this.partnerId = partnerId;
		this.duration = duration;
		this.adContent = adContent;
		this.setCreated(lastAccessed);
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getAdContent() {
		return adContent;
	}
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	
	 @Override
	    public String toString() {
	        return "Partner with ID =" + partnerId + ", with campaign duration=" + duration ;
	    }
	
	@Override
	public int hashCode() {

		int result = partnerId.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		if (!(obj instanceof Ad))
			return false;
		Ad other = (Ad) obj;
		if (this.partnerId != other.partnerId)
			return false;
		if (this.duration != other.duration)
			return false;
		if (!this.adContent.equals(other.adContent))
			return false;

		return true;
	}


}
