package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymnt.class)
public interface CdrPymntRepository extends EntityRepository<CdrPymnt, String>
{
}
