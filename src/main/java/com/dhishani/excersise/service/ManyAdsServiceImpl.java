package com.dhishani.excersise.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.springframework.stereotype.Service;

@Service("manyadService")
public final class ManyAdsServiceImpl implements ManyAdsService  {
	
	private JCS cache;

	public ManyAdsServiceImpl() throws CacheException {

		cache = JCS.getInstance("adCache");

	}

	@SuppressWarnings("unchecked")
	@Override
	public void createAds(Ad ad) throws CacheException {
		List<Ad> adList=null;
		if(isAdExist(ad)){
			adList=(List<Ad>) cache.get(ad.getPartnerId());
			adList.add(ad);
			cache.put(ad.getPartnerId(),adList);
		}
		else{
			adList=new ArrayList<Ad>();
			 adList.add(ad);}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ad> getAds(String id) {
		
		return (List<Ad>) cache.get(id);
	}

	@Override
	public boolean isAdExist(Ad ad) {
		return getAds(ad.getPartnerId()) != null;
	}

}
