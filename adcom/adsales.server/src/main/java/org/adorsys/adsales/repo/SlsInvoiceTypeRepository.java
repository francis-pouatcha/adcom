package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsInvoiceType;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvoiceType.class)
public interface SlsInvoiceTypeRepository extends EntityRepository<SlsInvoiceType, String>
{
}
