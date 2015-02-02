package org.adorsys.adcatal.repo;

import java.util.List;

import org.adorsys.adcatal.jpa.CatalCipOrigine;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalCipOrigine.class)
public interface CatalCipOrigineRepository extends EntityRepository<CatalCipOrigine, String>
{
	public List<CatalCipOrigine> findByLangIso2(String langIso2);
}
