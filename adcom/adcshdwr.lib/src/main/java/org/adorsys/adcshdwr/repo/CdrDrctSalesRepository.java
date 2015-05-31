package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSales.class)
public interface CdrDrctSalesRepository extends EntityRepository<CdrDrctSales, String>
{
}
