package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalPkgMode;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalPkgMode.class)
public interface CatalPkgModeRepository extends EntityRepository<CatalPkgMode, String>
{
}
