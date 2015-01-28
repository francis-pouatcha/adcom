package org.adorsys.adinvtry.repo;

import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryItem.class)
public interface InvInvtryItemRepository extends EntityRepository<InvInvtryItem, String>
{
}
