package org.adorsys.adstock.recptcls;

public class StkDeliveryEvtRef {
	private String evtId;
	private String deliveryId;
	private String deliverItemId;
	public String getEvtId() {
		return evtId;
	}
	public StkDeliveryEvtRef setEvtId(String evtId) {
		this.evtId = evtId;
		return this;
	}
	public String getDeliveryId() {
		return deliveryId;
	}
	public StkDeliveryEvtRef setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
		return this;
	}
	public String getDeliverItemId() {
		return deliverItemId;
	}
	public StkDeliveryEvtRef setDeliverItemId(String deliverItemId) {
		this.deliverItemId = deliverItemId;
		return this;
	}
}
