package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrCshDrawer.class)
public interface CdrCshDrawerRepository extends EntityRepository<CdrCshDrawer, String>
{
}
