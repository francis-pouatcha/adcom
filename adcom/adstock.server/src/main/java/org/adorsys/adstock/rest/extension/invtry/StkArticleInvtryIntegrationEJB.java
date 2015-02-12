/**
 * 
 */
package org.adorsys.adstock.rest.extension.invtry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2OuEJB;
import org.adorsys.adstock.rest.StkArticleLotEJB;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
@Stateless
public class StkArticleInvtryIntegrationEJB {

	@Inject
	private StkArticleLotEJB articleLotEJB;
	
	@Inject
	private StkArticleLot2OuEJB articleLot2OuEJB;
	
	@Inject
	private StkArticleLot2StrgSctn articleLot2StrgSctn;
	
	public List<StkArticleLot> findArtLotByArticlePic(List<String> artPics) {
		if(artPics == null) artPics = new ArrayList<String>();
		Map<String,StkArticleLot> results = new HashMap<String, StkArticleLot> ();
		
		for (String artPic : artPics) {
			Map<String, StkArticleLot> artLotByArtPicMap = findArtLotByArtPicLike(artPic);
			results.putAll(artLotByArtPicMap);
		}
		return new ArrayList<StkArticleLot>(results.values());
	}

	private Map<String, StkArticleLot> findArtLotByArtPicLike(String artPic) {
		if(StringUtils.isBlank(artPic)) throw new IllegalArgumentException("Invalid Article Pic");
		List<StkArticleLot> artPicLike = articleLotEJB.findByArtPicLike(artPic);
		Map<String, StkArticleLot> artLotByArtPicMap = transformToPicMap(artPicLike);
		return artLotByArtPicMap;
	}

	private Map<String, StkArticleLot> transformToPicMap(
			List<StkArticleLot> artPicLike) {
		HashMap<String,StkArticleLot> map = new HashMap<String, StkArticleLot>(artPicLike.size());
		for (StkArticleLot stkArticleLot : artPicLike) {
			map.put(stkArticleLot.getArtPic(), stkArticleLot);
		}
		return map;
	}
}
