package com.dhishani.excersise.service;

import java.util.List;

import org.apache.jcs.access.exception.CacheException;

public interface AdSrevice {
 
	void createAd(Ad ad) throws CacheException;
	
	Ad getAd(String id);
	
	boolean isAdExist(Ad ad);
	boolean isAdExpired(Ad ad);
	
	AdResponse createResponseAd(Ad ad);
	
	List<Ad> findAllAds();
	
	
	
}
