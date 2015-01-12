package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkArtStockQty;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArtStockQty.class)
public interface StkArtStockQtyRepository extends EntityRepository<StkArtStockQty, String>
{
}
