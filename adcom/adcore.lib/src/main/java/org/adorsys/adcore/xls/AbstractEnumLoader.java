package org.adorsys.adcore.xls;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.jpa.DynEnum;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractEnumLoader<T extends DynEnum> {

	protected abstract T newObject();
	protected abstract T findByIdentif(String identif);
	protected abstract T create(T entity);
	protected abstract T update(T found);
	protected abstract T deleteById(String id);


	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void load(Map<String, Map<String, List<String>>> data){
		Set<Entry<String,Map<String,List<String>>>> entrySet = data.entrySet();
		for (Entry<String, Map<String, List<String>>> entry : entrySet) {
			String key = entry.getKey();
			Map<String, List<String>> value = entry.getValue();
			Set<Entry<String, List<String>>> subEntries = value.entrySet();
			for (Entry<String, List<String>> subEntry: subEntries) {
				String language = subEntry.getKey();
				List<String> locStrings = subEntry.getValue();
				update(key, language, locStrings);
			}
		}
	}
	
	private void update(String key, String language, List<String> locStrings) {
		String identif = DynEnum.toIdentif(key, language);
		T existing = findByIdentif(identif);
		if(locStrings.isEmpty()) {
			if(existing!=null){
				deleteById(existing.getId());
			}
			return;
		}
		
		if(existing==null) {
			existing = newObject();
			existing.setEnumKey(key);
			existing.setLangIso2(language);
		}
		boolean updated = false;
		if(locStrings.size()<2) return;
		if(!StringUtils.equals(existing.getName(), locStrings.get(0))){
			existing.setName(locStrings.get(0));
			updated=true;
		}
		if(!StringUtils.equals(existing.getDescription(), locStrings.get(1))){
			existing.setDescription(locStrings.get(1));
			updated=true;
		}
		if(updated){
			if(StringUtils.isBlank(existing.getId())){
				create(existing);
			} else {
				update(existing);
			}
		}
	}
}
