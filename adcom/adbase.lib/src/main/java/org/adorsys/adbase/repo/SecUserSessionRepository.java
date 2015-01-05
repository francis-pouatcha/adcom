package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.SecUserSession;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecUserSession.class)
public interface SecUserSessionRepository extends EntityRepository<SecUserSession, String>
{
}
