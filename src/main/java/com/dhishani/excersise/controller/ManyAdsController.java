package com.dhishani.excersise.controller;

import java.util.List;

import org.apache.jcs.access.exception.CacheException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dhishani.excersise.service.Ad;
import com.dhishani.excersise.service.ManyAdsService;

@RestController
public class ManyAdsController {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	ManyAdsService manyadService;

	// ---------Add a Ad for Partner-------//

	@RequestMapping(value = "/ads/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody Ad ad) throws CacheException {

		long createtime = System.currentTimeMillis();
		ad.setCreated(createtime);

		manyadService.createAds(ad);
		logger.info("Creating ad for " + ad.getPartnerId());

		HttpHeaders headers = new HttpHeaders();
		headers.set("AdServiceHeader", "Ad Campaign created");

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}

	// -------------------Retrieve Ads ---------------------------//

	@RequestMapping(value = "/ads/{partnerId}", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ad>> getAd(@PathVariable("partnerId") String id) {

		logger.info("Fetching Ads with Partner id " + id);
		List<Ad> adList = manyadService.getAds(id);

		if (adList == null) {

			logger.warn("Ads with Partner id " + id + " not found");

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("AdServiceHeader", "Partner has no Ad Campaigns");

			return new ResponseEntity<List<Ad>>(responseHeaders, HttpStatus.NOT_FOUND);

		} else {

			HttpHeaders responseHeaders = new HttpHeaders();

			responseHeaders.set("AdServiceHeader", "Ad Campaigns  found");
			return new ResponseEntity<List<Ad>>(adList, responseHeaders, HttpStatus.OK);

		}
	}
}
