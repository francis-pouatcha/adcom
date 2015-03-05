package org.adorsys.adprocmt.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.enums.BaseProcStepEnum;
import org.adorsys.adbase.enums.BaseProcessStatusEnum;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adprocmt.api.ProcOrderInfo;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.repo.PrcmtProcOrderRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtProcOrderEJB
{

   @Inject
   private PrcmtProcOrderRepository repository;
   @Inject
   private SecurityUtil securityUtil;
   @Inject
   private PrcmtProcOrderHstryEJB orderHstryEJB;
   
   public PrcmtProcOrder createCustom(PrcmtProcOrder entity) {
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		entity.setCreatingUsr(currentLoginName);
		entity.setCreatedDt(now);
		if(entity.getOrderDt()==null) entity.setOrderDt(now);
		entity.setPoStatus(BaseProcessStatusEnum.ONGOING.name());
		if (StringUtils.isBlank(entity.getPoNbr())) {
			entity.setPoNbr(SequenceGenerator.getSequence(SequenceGenerator.PRCMT_ORDER_SEQUENCE_PREFIXE));
		}
		entity.setId(entity.getPoNbr());
		entity = create(entity);
		createInitialDeliveryHistory(entity);
		return entity;
	}

   
   private void createInitialDeliveryHistory(PrcmtProcOrder procOrder){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtProcOrderHstry procOrderHstry = new PrcmtProcOrderHstry();
		procOrderHstry.setComment(BaseHistoryTypeEnum.INITIATED.name());
		procOrderHstry.setAddtnlInfo(ProcOrderInfo.prinInfo(procOrder));
		procOrderHstry.setEntIdentif(procOrder.getId());
		procOrderHstry.setEntStatus(procOrder.getPoStatus());
		procOrderHstry.setHstryDt(new Date());
		procOrderHstry.setHstryType(BaseHistoryTypeEnum.INITIATED.name());
		
		procOrderHstry.setOrignLogin(callerPrincipal.getName());
		procOrderHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		procOrderHstry.setProcStep(BaseProcStepEnum.INITIATING.name());
		orderHstryEJB.create(procOrderHstry);
	}
   
   public PrcmtProcOrder create(PrcmtProcOrder entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtProcOrder deleteById(String id)
   {
      PrcmtProcOrder entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtProcOrder update(PrcmtProcOrder entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtProcOrder findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtProcOrder> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtProcOrder> findBy(PrcmtProcOrder entity, int start, int max, SingularAttribute<PrcmtProcOrder, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtProcOrder entity, SingularAttribute<PrcmtProcOrder, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtProcOrder> findByLike(PrcmtProcOrder entity, int start, int max, SingularAttribute<PrcmtProcOrder, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtProcOrder entity, SingularAttribute<PrcmtProcOrder, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtProcOrder attach(PrcmtProcOrder entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   public PrcmtProcOrder findByPoNbr(String poNbr)
   {
      return repository.findBy(poNbr);
   }
   
}
