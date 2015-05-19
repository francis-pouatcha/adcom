package org.adorsys.adcore.pdfreport;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.utils.DateUtil;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 
 * @author guymoyo
 *
 * @param <T>
 */
@Stateless
public class PdfUtil<T> {
	@Inject
	private PdfI18n pdfI18n;
	
	private DateUtil dateUtil;
	
	private Class<T> entityKlass;

	private List<String> fieldsName = new ArrayList<String>();;
	
	public Class<T> getEntity() {
		return entityKlass;
	}

	public void setEntity(Class<T> entity) {
		this.entityKlass = entity;
	}

	public List<String> getFieldsName() {
		List<String> listStr = new ArrayList<String>();
		fieldsName.clear();
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(entityKlass);
		String name;
		for(int i=0;i<descriptors.length;i++){
			 name = descriptors[i].getName();
			if("id".equals(name) || "version".equals(name) || "class".equals(name) || "identif".equals(name))
				continue;
			 String propertyTypeName = descriptors[i].getPropertyType().getName();		
			if(propertyTypeName.equals("java.util.Date") || propertyTypeName.equals("java.lang.String") || propertyTypeName.equals("java.math.BigDecimal")){
				fieldsName.add(name);
				listStr.add(pdfI18n.internationalize(name));
			}		
		}
		return listStr;
	}

	public String getKlassName() {
		
		return pdfI18n.internationalize(entityKlass.getSimpleName());
	}

	public List<String> fieldValues(T entity) {
		List<String> listValues = new ArrayList<String>();
		String value;
		for(String fieldName:fieldsName){
			try {
				Class<?> propertyType = PropertyUtils.getPropertyType(entity, fieldName);
				 String propertyTypeName = propertyType.getName();
				
				 if(propertyTypeName.equals("java.util.Date")){
					 Date dte = (Date) PropertyUtils.getProperty(entity, fieldName);
						value = dateUtil.transform(dte,dateUtil.DATE_FORMAT_SHORT);
						listValues.add(value);
				 }
				 if(propertyTypeName.equals("java.lang.String")){
					 value = (String) PropertyUtils.getProperty(entity, fieldName);					
					 listValues.add(value);
				 }
				 if(propertyTypeName.equals("java.math.BigDecimal")){
					 BigDecimal bgd = (BigDecimal)PropertyUtils.getProperty(entity, fieldName);
						listValues.add(bgd!=null?bgd.toString():"0");
				 }
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listValues;
	}
	
	
}
