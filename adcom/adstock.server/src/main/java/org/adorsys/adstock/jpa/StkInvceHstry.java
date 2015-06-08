package org.adorsys.adstock.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.AbstractEntityHistory;

/**
 * Document the processing of each direct sales closed event. This history is used to document 
 * the evolution of the processing of a direct sales event. This is only created when the 
 * all items have been processed.
 * 
 * @author francis
 *
 */
@Entity
public class StkInvceHstry extends AbstractEntityHistory {
	private static final long serialVersionUID = -4451478064748002973L;
}