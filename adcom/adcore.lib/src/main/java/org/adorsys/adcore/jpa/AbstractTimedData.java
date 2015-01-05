package org.adorsys.adcore.jpa;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.adcore.utils.FormatedValidFrom;
import org.adorsys.javaext.format.DateFormatPattern;
import org.apache.commons.lang3.StringUtils;

/**
 * Beside the identifier, we also have a time component.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class AbstractTimedData extends AbstractIdentifData {

	private static final long serialVersionUID = -8592023482340812797L;

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date validFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date validTo;

	@PrePersist
	public void prePersist() {
		if (this.validFrom == null)this.validFrom = new Date();
		if (StringUtils.isBlank(getId())) setId(getIdentif() + "_" + FormatedValidFrom.format(validFrom));
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
}