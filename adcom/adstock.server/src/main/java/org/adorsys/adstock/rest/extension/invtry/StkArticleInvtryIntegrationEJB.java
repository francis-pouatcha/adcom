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

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
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
	private SecurityUtil securityUtil;
	
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
			map.put(stkArticleLot.getIdentif(), stkArticleLot);
		}
		return map;
	}
	public List<StkArticleLot2Ou> findArtLot2Ou(List<String> artPics) {
		if(artPics == null) artPics = new ArrayList<String>();
		Map<String,StkArticleLot2Ou> artLot2Ous = new HashMap<String, StkArticleLot2Ou>();
		String ouId = securityUtil.getCurrentOrgUnit().getIdentif();
		for (String artPic : artPics) {
			Map<String, StkArticleLot2Ou> resultMap = findArtLot2OuByArtPic(artPic,ouId);
			artLot2Ous.putAll(resultMap);
		}
		return new ArrayList<StkArticleLot2Ou>(artLot2Ous.values());
	}

	/**
	 * findArtLot2OuByArtPic.
	 *
	 * @param artPic
	 * @return
	 */
	private Map<String, StkArticleLot2Ou> findArtLot2OuByArtPic(String artPic,String ouId) {
		if(StringUtils.isBlank(artPic)) throw new IllegalArgumentException("Invalid article pic. Empty pic is not accepted here.");
		if(StringUtils.isBlank(ouId)) ouId = securityUtil.getCurrentOrgUnit().getIdentif();
		List<StkArticleLot2Ou> stkArticleLot2Ous = articleLot2OuEJB.findByArtPicLikeAndOuLike(artPic, ouId);
		return transformStkArticleLot2OusToMap(stkArticleLot2Ous);
	}

	/**
	 * transformStkArticleLot2OusToMap.
	 *
	 * @param stkArticleLot2Ous
	 * @return
	 */
	private Map<String, StkArticleLot2Ou> transformStkArticleLot2OusToMap(
			List<StkArticleLot2Ou> stkArticleLot2Ous) {
		HashMap<String,StkArticleLot2Ou> map = new HashMap<String, StkArticleLot2Ou>(stkArticleLot2Ous.size());
		for (StkArticleLot2Ou stkArticleLot2Ou : stkArticleLot2Ous) {
			map.put(stkArticleLot2Ou.getIdentif(), stkArticleLot2Ou);
		}
		return map;
	}
	
	public ArticleLotSearchResult searchArtStkArtLot(ArtLotSearchInput searchInput) {
		if(searchInput == null) throw new IllegalArgumentException("searchInput should not be null");
		List<String> artPics = searchInput.getArtPics();
		List<StkArticleLot2Ou> artLot2Ous = findArtLot2Ou(artPics);
		List<StkArticleLot> lotByArticlePics = findArtLotByArticlePic(artPics);
		
		ArticleLotSearchResult lotSearchResult = new ArticleLotSearchResult(Long.valueOf(artLot2Ous.size()), lotByArticlePics,artLot2Ous, searchInput);
		return lotSearchResult;
	}
}
