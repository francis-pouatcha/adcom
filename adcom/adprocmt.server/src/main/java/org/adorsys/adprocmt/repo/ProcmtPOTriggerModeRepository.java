package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.ProcmtPOTriggerMode;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = ProcmtPOTriggerMode.class)
public interface ProcmtPOTriggerModeRepository extends EntityRepository<ProcmtPOTriggerMode, String>{}
