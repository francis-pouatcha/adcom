package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.ProcmtPOType;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = ProcmtPOType.class)
public interface ProcmtPOTypeRepository extends EntityRepository<ProcmtPOType, String>{}
