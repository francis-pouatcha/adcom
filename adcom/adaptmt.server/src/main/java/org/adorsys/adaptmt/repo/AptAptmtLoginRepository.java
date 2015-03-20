package org.adorsys.adaptmt.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adaptmt.jpa.AptAptmtLogin;

@Repository(forEntity = AptAptmtLogin.class)
public interface AptAptmtLoginRepository extends EntityRepository<AptAptmtLogin, String>
{
}
