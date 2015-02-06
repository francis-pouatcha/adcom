package org.adorsys.adbnsptnr.repo;

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpBnsPtnr.class)
public interface BpBnsPtnrRepository extends EntityRepository<BpBnsPtnr, String>
{
}
