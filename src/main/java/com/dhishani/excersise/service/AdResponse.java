package com.dhishani.excersise.service;

public class AdResponse {
	private String partnerId;
	private long duration;
	private String adContent;
	
	
	public AdResponse() {
				
	}
	public AdResponse(String partnerId, long duration, String adContent) {
		super();
		this.partnerId = partnerId;
		this.duration = duration;
		this.adContent = adContent;
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

}
