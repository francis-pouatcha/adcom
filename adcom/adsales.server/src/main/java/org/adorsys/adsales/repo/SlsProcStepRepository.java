package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsProcStep;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsProcStep.class)
public interface SlsProcStepRepository extends EntityRepository<SlsProcStep, String>
{
}
