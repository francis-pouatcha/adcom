package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseConfig.class)
public interface BaseConfigRepository extends
		EntityRepository<BaseConfig, String> {

}
