package org.adorsys.adstock.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.rest.CatalArtFeatMappingLookupEJB;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLotSearchInput;
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
	private StkLotStockQtyLookup lotStockQtyEJB;

	@Inject
	private CatalArtFeatMappingLookupEJB artFeatMappingReaderEJB;

	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private StkArticleLotLookup articleLotLookup;

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

		List<String> lotPics = articleLotLookup.findLotPicByArtPic(entity.getArtPic());
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
}
