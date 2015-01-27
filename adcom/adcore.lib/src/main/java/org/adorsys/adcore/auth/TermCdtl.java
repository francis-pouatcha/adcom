package org.adorsys.adcore.auth;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONException;
import org.apache.tomcat.util.json.JSONObject;


/**
 * 
 * @author francis
 *
 */
public class TermCdtl {

	private String sso;
	private String cre;
	private String cert;

	public TermCdtl() {
	}

	public TermCdtl(JSONObject o) {
		try {
			sso = o.getString("sso");
			cre = o.getString("cre");
			cert = o.getString("cert");
		} catch(JSONException e){
			throw new IllegalStateException(e);
		}
	}
	
	
	public String getSso() {
		return sso;
	}

	public void setSso(String sso) {
		this.sso = sso;
	}

	public String getCre() {
		return cre;
	}

	public void setCre(String cre) {
		this.cre = cre;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public boolean hasTermCredential() {
		return StringUtils.isNotBlank(cre);
	}

	public static TermCdtl fromString(String s) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(s);
		} catch (JSONException e) {
			throw new IllegalStateException(e);
		}
		return new TermCdtl(jsonObject);
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject(this);
		return jsonObject.toString();
	}
}
