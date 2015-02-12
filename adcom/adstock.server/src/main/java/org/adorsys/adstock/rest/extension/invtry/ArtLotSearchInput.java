package org.adorsys.adstock.rest.extension.invtry;

import java.util.List;

/**
 * 
 * @author boriswaguia
 *
 */
public class ArtLotSearchInput {
	/**
	 * The artPic field.
	 */
	private String artPic;
	/**
	 * The artDesign field.
	 */
	private String artDesign;
	/**
	 * The start field.
	 */
	private long start;
	/**
	 * The max field.
	 */
	private long max;
	/**
	 * The fields field.
	 */
	private List<String> fields;
	/**
	 * The applyLike field.
	 */
	private boolean applyLike;
	
	public ArtLotSearchInput() {}
	
	public String getArtPic() {
		return artPic;
	}
	public void setArtPic(String artPic) {
		this.artPic = artPic;
	}
	public String getArtDesign() {
		return artDesign;
	}
	public void setArtDesign(String artDesign) {
		this.artDesign = artDesign;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public boolean isApplyLike() {
		return applyLike;
	}
	public void setApplyLike(boolean applyLike) {
		this.applyLike = applyLike;
	}
	@Override
	public String toString() {
		return "ArtLotSearchInput [artPic=" + artPic + ", artDesign="
				+ artDesign + ", start=" + start + ", max=" + max + ", fields="
				+ fields + ", applyLike=" + applyLike + "]";
	}
	
}