package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalCipOrigine;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalCipOrigine.class)
public interface CatalCipOrigineRepository extends EntityRepository<CatalCipOrigine, String>
{
}
