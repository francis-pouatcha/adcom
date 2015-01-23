/**
 * 
 */
package org.adorsys.adbase.rest;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OuType;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
@Stateless
public class OrgUnitIdEJB {
	@Inject
	private OrgUnitEJB orgUnitEJB;
	
	@Inject
	private OuTypeEJB ouTypeEJB;
	
	
	/**
	 * Get the parent Id of this organization unit Id
	 * @param ouId
	 * @return
	 */
	public String getParentId(String ouId) {
		if(StringUtils.isBlank(ouId)) {
			throw new IllegalArgumentException("Invalid organisation unit Id");
		}
		Date date = new Date();
		OrgUnit orgUnit = orgUnitEJB.findByIdentif(ouId, date);
		return getParentId(orgUnit,date);
	}


	/**
	 * <p>
	 * 	When Creating OrgUnit, their id are generated as follow :
	 * 	<ul>
	 * 		<li>Get the Id of the parent Organization Unit (mostly form the UI)</li>
	 * 		<li>Concatenate the parentId with a randomStringutil with the ouType of the organization unit we are about to create.</li>
	 *  </ul>
	 * </p>
	 * So the following method is trying to do the reverse processus.
	 * @param orgUnit
	 * @param date
	 * @return
	 */
	public String getParentId(OrgUnit orgUnit,Date date) {
		if(orgUnit == null) throw new IllegalArgumentException("Organisation Unit should not be null here");
		if(date == null) date = new Date();
		
		String typeIdentif = orgUnit.getTypeIdentif();
		String ouId = orgUnit.getIdentif();
		OuType ouType = ouTypeEJB.findByIdentif(typeIdentif, date);
		Integer idSize = ouType.getIdSize();
		String parentId = subStringParentId(ouId, idSize);
		return parentId;
	}


	/**
	 * @param childId
	 * @param childIdSize
	 * @return
	 */
	public String subStringParentId(String childId, Integer childIdSize) {
		int parentIdSize = childId.length() - childIdSize;
		String parentId = childId.substring(0, parentIdSize);
		return parentId;
	}
}
