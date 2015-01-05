package org.adorsys.adacc.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adacc.jpa.AccCoA;

@Repository(forEntity = AccCoA.class)
public interface AccCoARepository extends EntityRepository<AccCoA, String>
{
}
