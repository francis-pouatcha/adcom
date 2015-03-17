package org.adorsys.adprocmt.trigger;

import org.adorsys.adprocmt.api.PrcmtOrderHolder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;

public interface TriggerModeExecuter {
	
	public PrcmtOrderHolder executeTriggerMode(PrcmtProcOrder prcmtProcOrder);

}
