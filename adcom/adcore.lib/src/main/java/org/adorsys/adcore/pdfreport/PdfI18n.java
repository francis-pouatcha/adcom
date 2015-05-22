package org.adorsys.adcore.pdfreport;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Stateless;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author guymoyo
 *
 */
@Stateless
public class PdfI18n {

	private static List<Class<?>> listKlass = new ArrayList<>();
	private static Map<String, Map<String, List<String>>> translations = new HashMap<String, Map<String, List<String>>>();
	
	public void registerKlass(Class<?> klass){
		if(!listKlass.contains(klass)){
			listKlass.add(klass);
			load(klass);
		}		
	}
	
	public String internationalize(String key, String lang) {	
		try {
			return translations.get(key).get(lang).get(0);
		} catch (Exception e) {
			return key;
		}
	}
	
	private void load(Class<?> klass) {
		List<String> keys = getKeys(klass);
		try {
			InputStream inputStream = klass.getResourceAsStream(klass.getSimpleName()+".properties");
			Properties enProps = new Properties();
			enProps.load(inputStream);
			inputStream = klass.getResourceAsStream(klass.getSimpleName()+"_fr.properties");
			Properties frProps = new Properties();
			frProps.load(inputStream);
			String simpleName = klass.getSimpleName();
			for (String key : keys) {
				String titleStr = simpleName+"_"+key+"_description.title";
				String textStr = simpleName+"_"+key+"_description.text";
				String enTitle = enProps.getProperty(titleStr);
				String enText = enProps.getProperty(textStr);
				String frTitle = frProps.getProperty(titleStr);
				String frText = frProps.getProperty(textStr);
				if(StringUtils.isBlank(enTitle) && StringUtils.isBlank(enText) && StringUtils.isBlank(frTitle) && StringUtils.isBlank(frText)){
					continue;
				}
				Map<String, List<String>> keyMap = new HashMap<String, List<String>>();
				translations.put(key, keyMap);
				if(StringUtils.isNotBlank(enTitle) || StringUtils.isNotBlank(enText)){
					List<String> enList = new ArrayList<String>();
					keyMap.put("en", enList);
					if(StringUtils.isNotBlank(enTitle)){
						enList.add(enTitle);
					}
					if(StringUtils.isNotBlank(enText)){
						enList.add(enText);
					}
				}
				if(StringUtils.isNotBlank(frTitle) || StringUtils.isNotBlank(frText)){
					List<String> frList = new ArrayList<String>();
					keyMap.put("fr", frList);
					if(StringUtils.isNotBlank(frTitle)){
						frList.add(frTitle);
					}
					if(StringUtils.isNotBlank(frText)){
						frList.add(frText);
					}
				}
			}
			
			//i18n for klass name
			String titleStr = simpleName+"_description.title";
			String enTitle = enProps.getProperty(titleStr);
			String frTitle = frProps.getProperty(titleStr);
			Map<String, List<String>> keyMap = new HashMap<String, List<String>>();
			translations.put(simpleName, keyMap);
			if(StringUtils.isNotBlank(enTitle)){
				List<String> enList = new ArrayList<String>();
				keyMap.put("en", enList);
				if(StringUtils.isNotBlank(enTitle)){
					enList.add(enTitle);
				}
			}
			if(StringUtils.isNotBlank(frTitle)){
				List<String> frList = new ArrayList<String>();
				keyMap.put("fr", frList);
				if(StringUtils.isNotBlank(frTitle)){
					frList.add(frTitle);
				}
			}
			
		} catch (IOException e) {
			// noop
		}
	}
	
	private List<String> getKeys(Class<?> klass) {
		List<String> keys = new ArrayList<String>();
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(klass);
		String name;
		for(int i=0;i<descriptors.length;i++){
			 name = descriptors[i].getName();
			if("id".equals(name) || "version".equals(name) || "class".equals(name) || "identif".equals(name))
				continue;	
			keys.add(name);
		}
		return keys;
	}

}
