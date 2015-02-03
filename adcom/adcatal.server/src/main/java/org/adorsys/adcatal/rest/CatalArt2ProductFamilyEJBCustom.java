/**
 * 
 */
package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;

/**
 * This Ejb exist, because there where a circular dependencies injection exception, when We Inject {@link CatalProductFamilyEJB} in {@link CatalArt2ProductFamilyEJB}.
 * This class is used in {@link CatalArt2ProductFamilyEndPoint}, for the custom create.
 * 
 * @author boriswaguia
 *
 */
@Stateless
public class CatalArt2ProductFamilyEJBCustom {
	@Inject
	private CatalProductFamilyEJB catalProductFamilyEJB;


	public void createCustom(String artPic,CatalArt2ProductFamily entity) {
		String famCode = entity.getFamCode();
		catalProductFamilyEJB.addProductFamilyMapping(artPic, famCode);
	}
}
