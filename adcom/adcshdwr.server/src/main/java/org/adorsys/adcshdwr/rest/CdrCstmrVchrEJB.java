package org.adorsys.adcshdwr.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.api.CdrDsArtHolder;
import org.adorsys.adcshdwr.api.CdrDsArtItemHolder;
import org.adorsys.adcshdwr.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.repo.CdrCstmrVchrRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrCstmrVchrEJB
{

   @Inject
   private CdrCstmrVchrRepository repository;
   @Inject
   private SecurityUtil securityUtil;
   @Inject
	private CdrCshDrawerEJB cshDrawerEJB;

   public CdrCstmrVchr create(CdrCstmrVchr entity)
   {
	   if (StringUtils.isBlank(entity.getVchrNbr())) {
			entity.setVchrNbr(SequenceGenerator
					.getSequence(SequenceGenerator.VOUCHER_SEQUENCE_PREFIXE));
		}
	   entity.setCashier(securityUtil.getCurrentLoginName());
      return repository.save(attach(entity));
   }

   public CdrCstmrVchr deleteById(String id)
   {
      CdrCstmrVchr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrCstmrVchr update(CdrCstmrVchr entity)
   {
      return repository.save(attach(entity));
   }

   public CdrCstmrVchr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrCstmrVchr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrCstmrVchr> findBy(CdrCstmrVchr entity, int start, int max, SingularAttribute<CdrCstmrVchr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrCstmrVchr entity, SingularAttribute<CdrCstmrVchr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrCstmrVchr> findByLike(CdrCstmrVchr entity, int start, int max, SingularAttribute<CdrCstmrVchr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrCstmrVchr entity, SingularAttribute<CdrCstmrVchr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrCstmrVchr attach(CdrCstmrVchr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

	public void generateVoucher(CdrDsArtHolder cdrDsArtHolder) throws AdException {
		CdrDrctSales cdrDrctSales = cdrDsArtHolder.getCdrDrctSales();
		BigDecimal amt = BigDecimal.ZERO;
		for(CdrDsArtItemHolder itemHolder:cdrDsArtHolder.getItems()){
			CdrDsArtItem item = itemHolder.getItem();
			if(item.getReturnedQty() != null && item.getReturnedQty().compareTo(BigDecimal.ZERO) == 1 ){
				BigDecimal grossSPPreTaxReturned = item.getReturnedQty().multiply(item.getSppuPreTax());
				BigDecimal vatAmountReturned = grossSPPreTaxReturned.divide(BigDecimal.valueOf(100)).multiply(item.getVatPct());
				amt = amt.add(grossSPPreTaxReturned).add(vatAmountReturned);
			}
		}
		CdrCstmrVchr vchr = new CdrCstmrVchr();
		vchr.setDsNbr(cdrDrctSales.getDsNbr());
		CdrCshDrawer activeCshDrawer = cshDrawerEJB.getActiveCshDrawer();
		vchr.setCdrNbr(activeCshDrawer.getCdrNbr());
		vchr.setAmt(amt);
		vchr.evlte();
		vchr = create(vchr);
		activeCshDrawer.AddTtlVchrOut(vchr.getAmt());
		cshDrawerEJB.update(activeCshDrawer);
	}
}
