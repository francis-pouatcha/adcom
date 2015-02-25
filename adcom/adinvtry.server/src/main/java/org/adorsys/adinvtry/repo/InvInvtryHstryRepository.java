package org.adorsys.adinvtry.repo;

import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryHstry.class)
public interface InvInvtryHstryRepository extends EntityRepository<InvInvtryHstry, String>
{
}
