package org.adorsys.adbase.spi.dflt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.Stateless;

import org.adorsys.adbase.enums.BaseDocTypeEnum;
import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.enums.BaseProcStepEnum;
import org.adorsys.adbase.enums.BaseProcessStatusEnum;
import org.adorsys.adbase.enums.BaseRoleInProcessEnum;
import org.adorsys.adbase.enums.BaseSttlmtOpEnum;
import org.adorsys.adbase.spi.BaseEnumDataProvider;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class DefaultBaseEnumDataProvider implements BaseEnumDataProvider {
	
	@Override
	public Map<String, Map<String, List<String>>> getDocTypeData() {
		BaseDocTypeEnum[] values = BaseDocTypeEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (BaseDocTypeEnum e : values) {
			keys.add(e.name());
		}
		return load(BaseDocTypeEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getHistoryTypeData() {
		BaseHistoryTypeEnum[] values = BaseHistoryTypeEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (BaseHistoryTypeEnum e : values) {
			keys.add(e.name());
		}
		return load(BaseHistoryTypeEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getProcessStatusData() {
		BaseProcessStatusEnum[] values = BaseProcessStatusEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (BaseProcessStatusEnum e : values) {
			keys.add(e.name());
		}
		return load(BaseProcessStatusEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getProcStepData() {
		BaseProcStepEnum[] values = BaseProcStepEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (BaseProcStepEnum e : values) {
			keys.add(e.name());
		}
		return load(BaseProcStepEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getRoleInProcessData() {
		BaseRoleInProcessEnum[] values = BaseRoleInProcessEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (BaseRoleInProcessEnum e : values) {
			keys.add(e.name());
		}
		return load(BaseRoleInProcessEnum.class, keys);
	}

	@Override
	public Map<String, Map<String, List<String>>> getSttlmtOpData() {
		BaseSttlmtOpEnum[] values = BaseSttlmtOpEnum.values();
		List<String> keys = new ArrayList<String>(values.length);
		for (BaseSttlmtOpEnum e : values) {
			keys.add(e.name());
		}
		return load(BaseSttlmtOpEnum.class, keys);
	}

	private Map<String, Map<String, List<String>>> load(Class<?> enumKlass, List<String> keys) {
		Map<String, Map<String, List<String>>> result = new HashMap<String, Map<String, List<String>>>();
		try {
			InputStream inputStream = enumKlass.getResourceAsStream(enumKlass.getSimpleName()+".properties");
			Properties enProps = new Properties();
			enProps.load(inputStream);
			inputStream = enumKlass.getResourceAsStream(enumKlass.getSimpleName()+"_fr.properties");
			Properties frProps = new Properties();
			frProps.load(inputStream);
			String simpleName = enumKlass.getSimpleName();
			for (String enumKey : keys) {
				String titleStr = simpleName+"_"+enumKey+"_description.title";
				String textStr = simpleName+"_"+enumKey+"_description.text";
				String enTitle = enProps.getProperty(titleStr);
				String enText = enProps.getProperty(textStr);
				String frTitle = frProps.getProperty(titleStr);
				String frText = frProps.getProperty(textStr);
				if(StringUtils.isBlank(enTitle) && StringUtils.isBlank(enText) && StringUtils.isBlank(frTitle) && StringUtils.isBlank(frText)){
					continue;
				}
				Map<String, List<String>> keyMap = new HashMap<String, List<String>>();
				result.put(enumKey, keyMap);
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
		} catch (IOException e) {
			// noop
		}
		return result;
	}
	
}
