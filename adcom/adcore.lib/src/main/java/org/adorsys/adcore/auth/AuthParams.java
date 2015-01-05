package org.adorsys.adcore.auth;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONException;
import org.apache.tomcat.util.json.JSONObject;

/**
 * Holds authentication parameters.
 * 
 * Each user authenticated with this system is given 2 tokens:
 * - The termSessId : The terminal session id. This is the temporal identifier of the terminal
 * used by the user to access the system. This information is only held in the memory of the
 * client application and sent back to the server as part of the basic auth header.
 * - The userSessId : The user session id. This is the temporary identifier of the user session.
 * Just like the terminal session if, this information shall not be stored in a cookie. It
 * must be sent to the server with each call as part of the basic auth header.
 * 
 * Both token combined are used to identify the current session.
 * 
 * 
 * 
 * @author francis
 *
 */
public class AuthParams {
	public static final String[] idFields = {"trm","usr"};
	private String opr;
	private String trm;
	private String usr;
	private String lgn;
	private String pwd;
	private String wrk;
	private String tky;
	private String rdm;

	public AuthParams(JSONObject o) {
		try {
			this.opr = readString(o,"opr");
			this.trm = readString(o,"trm");
			this.usr = readString(o,"usr");
			this.lgn = readString(o,"lgn");
			this.pwd = readString(o,"pwd");
			this.wrk = readString(o,"wrk");
			this.tky = readString(o,"tky");
			this.rdm = RandomStringUtils.randomAlphanumeric(5);
		} catch(JSONException e){
			throw new IllegalStateException(e);
		}
	}
	
	private String readString(JSONObject o, String key) throws JSONException{
		if(!o.has(key)) return null;
		String string = o.getString(key);
		if(StringUtils.isBlank(string)) return null;
		if(StringUtils.equalsIgnoreCase(string, "null")) return null;
		return string;
	}

	public AuthParams(String trm, String usr) {
		this.trm = trm;
		this.usr = usr;
	}

	public AuthParams() {
		super();
	}

	public String getOpr() {
		return opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

	public String getTrm() {
		return trm;
	}

	public void setTrm(String trm) {
		this.trm = trm;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getLgn() {
		return lgn;
	}

	public void setLgn(String lgn) {
		this.lgn = lgn;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getWrk() {
		return wrk;
	}

	public void setWrk(String wrk) {
		this.wrk = wrk;
	}

	public String getTky() {
		return tky;
	}

	public void setTky(String tky) {
		this.tky = tky;
	}

	public String getRdm() {
		return rdm;
	}

	public void setRdm(String rdm) {
		this.rdm = rdm;
	}

	public final String toIdString() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("trm", getTrm());
			jsonObject.put("usr", getUsr());
		} catch (JSONException e) {
			throw new IllegalStateException(e);
		}
		return jsonObject.toString();
	}

	public static AuthParams fromString(String s) {
		if(StringUtils.isBlank(s)) return new AuthParams();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(s);
		} catch (JSONException e) {
			throw new IllegalStateException(e);
		}
		return new AuthParams(jsonObject);
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject(this);
		return jsonObject.toString();
	}
}
