package org.adorsys.adcatal.spi;

import java.util.List;
import java.util.Map;

public interface CatalEnumDataProvider {
	public Map<String, Map<String, List<String>>> getPkgModeData();
	public Map<String, Map<String, List<String>>> getCipOrigineData();
}
