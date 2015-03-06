package org.adorsys.adstock.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.rest.CatalArtFeatMappingReaderEJB;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkArticleLotSearchInput;
import org.adorsys.adstock.jpa.StkArticleLotSearchResult;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.apache.commons.lang3.StringUtils;

/**
 * Not an ejb.
 * 
 * @author francis
 *
 */
public class StkArticleLotDetachHelper {

	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;

	@Inject
	private CatalArtFeatMappingReaderEJB artFeatMappingReaderEJB;

	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private StkArticleLotEJB ejb;

	@Inject
	private StkArticleLot2StrgSctnEJB strgSctnEJB;

	public StkArticleLot detach(StkArticleLot entity) {
		if (entity == null)
			return null;

		BigDecimal vatSalesPct = entity.getVatSalesPct();
		BigDecimal sppuHT = entity.getSppuHT();
		BigDecimal vat = BigDecimalUtils.basePercentOfRatePct(vatSalesPct,
				sppuHT, RoundingMode.HALF_EVEN);
		BigDecimal sppuTaxIncl = BigDecimalUtils.sum(sppuHT, vat);
		entity.setSppuTaxIncl(sppuTaxIncl);
		entity.setSalesVatAmt(vat);

		List<String> lotPics = ejb.findLotPicByArtPic(entity.getArtPic());
		List<StkLotStockQty> artQties = lotStockQtyEJB.findLatestArtStockQuantities(entity.getArtPic(), lotPics);
		entity.setArtQties(artQties);
		// Now set the value for the current lot
		for (StkLotStockQty stkLotStockQty : artQties) {
			if(StringUtils.equals(stkLotStockQty.getLotPic(),entity.getLotPic())){
				entity.setLotQty(stkLotStockQty.getStockQty());
				entity.setLotQtyDt(stkLotStockQty.getQtyDt());
			}
		}
		String langIso2 = securityUtil.getUserLange();
		CatalArtFeatMapping artFeatures = artFeatMappingReaderEJB
				.findByIdentif(CatalArtFeatMapping.toIdentif(
						entity.getArtPic(), langIso2));
		entity.setArtFeatures(artFeatures);
		return entity;
	}

	public List<StkArticleLot> detach(List<StkArticleLot> list) {
		if (list == null)
			return list;
		List<StkArticleLot> result = new ArrayList<StkArticleLot>();
		for (StkArticleLot entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	public StkArticleLotSearchInput detach(StkArticleLotSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}

	public StkArticleLotSearchResult processSearchResult(
			StkArticleLotSearchInput searchInput,
			StkArticleLotSearchResult searchResult) {
		List<StkArticleLot> resultList = searchResult.getResultList();
		Map<String, StkArticleLot2StrgSctn> foundCache = new HashMap<String, StkArticleLot2StrgSctn>();
		if (StringUtils.isNotBlank(searchInput.getSectionCode())) {
			for (StkArticleLot stkArticleLot : resultList) {
				StkArticleLot2StrgSctn sctn = strgSctnEJB.findByStrgSectionAndLotPicAndArtPic(
						searchInput.getSectionCode(), stkArticleLot.getLotPic(),stkArticleLot.getArtPic());
				putAndCache(foundCache, Arrays.asList(sctn), stkArticleLot);
			}
		} else if (searchInput.isWithStrgSection()) {
			for (StkArticleLot stkArticleLot : resultList) {
				List<StkArticleLot2StrgSctn> sctns = strgSctnEJB
						.findByArtPicAndLotPic(stkArticleLot.getArtPic(),
								stkArticleLot.getLotPic());
				putAndCache(foundCache, sctns, stkArticleLot);
			}
		}
		return searchResult;
	}

	public void putAndCache(Map<String, StkArticleLot2StrgSctn> foundCache,
			List<StkArticleLot2StrgSctn> sctns, StkArticleLot stkArticleLot) {
		for (StkArticleLot2StrgSctn strgSctn : sctns) {
			if (!foundCache.containsKey(strgSctn.getId())) {
				foundCache.put(strgSctn.getId(), strgSctn);
				stkArticleLot.getStrgSctns().add(strgSctn);
			} else {
				if (!stkArticleLot.getStrgSctns().contains(strgSctn)) {
					stkArticleLot.getStrgSctns().add(strgSctn);
				}
			}
		}
	}
}
