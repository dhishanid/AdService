package com.dhishani.excersise.service;

import java.util.List;

import org.apache.jcs.access.exception.CacheException;

public interface ManyAdsService {

void createAds(Ad ad) throws CacheException;
	
	List<Ad> getAds(String id);
	
	boolean isAdExist(Ad ad);
	
	
}
