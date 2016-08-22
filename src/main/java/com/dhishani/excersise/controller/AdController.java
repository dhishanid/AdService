package com.dhishani.excersise.controller;

import java.util.List;

import org.apache.jcs.access.exception.CacheException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dhishani.excersise.service.Ad;
import com.dhishani.excersise.service.AdResponse;

import com.dhishani.excersise.service.AdSrevice;

@RestController
public class AdController {

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	AdSrevice adService;

	@RequestMapping(value = "/adv", method = RequestMethod.POST, headers = "Accept=application/json")
	public Ad addCountry(@RequestBody Ad ad) throws CacheException {
		// System.out.println("Creating ad for " + ad.getPartnerId());

		logger.info("Creating ad for " + ad.getPartnerId());
		long createtime = System.currentTimeMillis();
		ad.setCreated(createtime);
		adService.createAd(ad);
		return ad;
	}

	// ----- Create a Ad-----------------------------//
	
	@RequestMapping(value = "/ad/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody Ad ad, UriComponentsBuilder ucBuilder) throws CacheException {

		long createtime = System.currentTimeMillis();
		ad.setCreated(createtime);

		if (adService.isAdExist(ad)) {

			logger.warn("A Ad with Partner ID " + ad.getPartnerId() + " already exist");
			HttpHeaders headers = new HttpHeaders();
			headers.set("AdServiceHeader", "Ad Campaign already exist");
			return new ResponseEntity<Void>(headers, HttpStatus.CONFLICT);
		} else {
			adService.createAd(ad);
			logger.info("Creating ad for " + ad.getPartnerId());

			HttpHeaders headers = new HttpHeaders();
			headers.set("AdServiceHeader", "Ad Campaign created");
			headers.setLocation(ucBuilder.path("/ad/{partnerId}").buildAndExpand(ad.getPartnerId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

		}
	}

	// -------------------Retrieve Ad ---------------------------//

	@RequestMapping(value = "/ad/{partnerId}", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdResponse> getAd(@PathVariable("partnerId") String id) {

		logger.info("Fetching Ad with Partner id " + id);
		Ad ad = adService.getAd(id);

		if (ad == null) {

			logger.warn("Ad with Partner id " + id + " not found");
			HttpHeaders responseHeaders = new HttpHeaders();

			responseHeaders.set("AdServiceHeader", "Partner has no Ad Campaigns");

			return new ResponseEntity<AdResponse>(responseHeaders, HttpStatus.NOT_FOUND);

		} else {

			if (adService.isAdExpired(ad)) {

				logger.warn("Ad with Partner id " + id + " Expired");
				HttpHeaders responseHeaders = new HttpHeaders();

				responseHeaders.set("AdServiceHeader", "Ad Campaigns is expired");
				return new ResponseEntity<AdResponse>(responseHeaders, HttpStatus.CONFLICT);
			}

			AdResponse ar = adService.createResponseAd(ad);

			HttpHeaders responseHeaders = new HttpHeaders();

			responseHeaders.set("AdServiceHeader", "Ad Campaigns is found");
			return new ResponseEntity<AdResponse>(ar, responseHeaders, HttpStatus.OK);

		}

	}
	
	 //-------------------Retrieve All Ads----------------------------------//
	
    
    @RequestMapping(value = "/ad/", method = RequestMethod.GET)
    public ResponseEntity<List<Ad>> listAllAds() {
        List<Ad> ads = adService.findAllAds();
        if(ads.isEmpty()){
            return new ResponseEntity<List<Ad>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Ad>>(ads, HttpStatus.OK);
    }

	@ExceptionHandler(CacheException.class)
	public ResponseEntity<Void> exceptionHandler() {
		logger.error("Error occured in server cache ");
		HttpHeaders headers = new HttpHeaders();
		headers.set("AdServiceHeader", "Error occured in server cache ,request failed");
		return new ResponseEntity<Void>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> serverExceptionHandler() {
		logger.error("Error occured in server, request failed ");
		HttpHeaders headers = new HttpHeaders();
		headers.set("AdServiceHeader", "Error occured in server ");
		return new ResponseEntity<Void>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
