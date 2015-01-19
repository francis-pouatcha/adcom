package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrDsHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDsHstry.class)
public interface CdrDsHstryRepository extends EntityRepository<CdrDsHstry, String>
{
}
