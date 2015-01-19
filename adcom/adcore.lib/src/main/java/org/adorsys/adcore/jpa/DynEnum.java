package org.adorsys.adcore.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("DynEnum_description")
public abstract class DynEnum extends AbstractIdentifData {

	private static final long serialVersionUID = 7396951609058069987L;

	@Column
	@Description("DynEnum_enumKey_description")
	@NotNull
	private String enumKey;

	@Column
	@Description("DynEnum_langIso2_description")
	@NotNull
	private String langIso2;

	@Column
	@Description("DynEnum_name_description")
	@NotNull
	private String name;

	@Column
	@Description("DynEnum_description_description")
	@NotNull
	@Size(max = 256)
	private String description;

	public String getEnumKey() {
		return this.enumKey;
	}

	public void setEnumKey(final String enumKey) {
		this.enumKey = enumKey;
	}

	public String getLangIso2() {
		return this.langIso2;
	}

	public void setLangIso2(final String langIso2) {
		this.langIso2 = langIso2;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	protected String makeIdentif() {
		return toIdentif(enumKey,langIso2);
	}
	
	public static String toIdentif(String enumKey, String langIso2){
		return enumKey + "_" + langIso2;
	}
}