package org.adorsys.adacc.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adacc.jpa.AccPstg;

@Repository(forEntity = AccPstg.class)
public interface AccPstgRepository extends EntityRepository<AccPstg, String>
{
}
