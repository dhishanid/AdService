package com.dhishani.excersise.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.control.CompositeCacheManager;
import org.springframework.stereotype.Service;

@Service("adService")

public class AdServiceImpl implements AdSrevice {

	private JCS cache;

	public AdServiceImpl() throws CacheException {

		cache = JCS.getInstance("adCache");

	}

	public void createAd(Ad ad) throws CacheException {

		String id = null;

		id = ad.getPartnerId();

		cache.put(id, ad);

	}

	public Ad getAd(String id) {

		Ad val = (Ad) cache.get(id);

		return val;
	}

	public boolean isAdExpired(Ad ad) {
		long ctime = System.currentTimeMillis();
		long crtime = ad.getCreated();
		long duration = (ctime - crtime);

		long campdur = ad.getDuration();

		return ((duration / 1000) > campdur);

	}

	public boolean isAdExist(Ad ad) {
		return getAd(ad.getPartnerId()) != null;
	}

	public AdResponse createResponseAd(Ad ad) {
		AdResponse adResponse = new AdResponse();
		adResponse.setAdContent(ad.getAdContent());
		adResponse.setDuration(ad.getDuration());
		adResponse.setPartnerId(ad.getPartnerId());

		return adResponse;

	}

	@Override
	public List<Ad> findAllAds() {
		List<Ad> adList = new ArrayList<Ad>();

		Object[] arr =  CompositeCacheManager.getInstance().getCache("adCache").getMemoryCache().getKeyArray();
		for (Object s : arr) {
			Ad ad = (Ad) cache.get(s);
			adList.add(ad);
			System.out.println(s);
		}
		return adList;
	}
}
