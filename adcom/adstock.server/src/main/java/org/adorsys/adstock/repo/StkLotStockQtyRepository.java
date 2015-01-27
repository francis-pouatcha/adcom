package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkLotStockQty;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkLotStockQty.class)
public interface StkLotStockQtyRepository extends EntityRepository<StkLotStockQty, String>
{
}
