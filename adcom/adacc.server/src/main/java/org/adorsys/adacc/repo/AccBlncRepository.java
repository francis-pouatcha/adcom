package org.adorsys.adacc.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adacc.jpa.AccBlnc;

@Repository(forEntity = AccBlnc.class)
public interface AccBlncRepository extends EntityRepository<AccBlnc, String>
{
}
