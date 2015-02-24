package org.adorsys.adinvtry.repo;

import java.util.List;

import org.adorsys.adinvtry.jpa.InvInvtryItemEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryItemEvtData.class)
public interface InvInvtryItemEvtDataRepository extends EntityRepository<InvInvtryItemEvtData, String>
{
	public List<InvInvtryItemEvtData> findByInvtryNbr(String invtryNbr);
}
