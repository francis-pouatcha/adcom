package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmtLogin;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmtLogin.class)
public interface AptAptmtLoginRepository extends EntityRepository<AptAptmtLogin, String>
{
}
