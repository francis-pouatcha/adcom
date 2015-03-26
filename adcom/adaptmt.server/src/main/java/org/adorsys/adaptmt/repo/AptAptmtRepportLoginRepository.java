package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmtRepportLogin;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmtRepportLogin.class)
public interface AptAptmtRepportLoginRepository extends EntityRepository<AptAptmtRepportLogin, String>
{
}
