package org.adorsys.adbase.jpa;

import javax.persistence.Entity;

import org.adorsys.javaext.description.Description;

@Entity
@Description("SecTerminal_description")
public class SecTerminal extends SecAbstractTerminal {

	private static final long serialVersionUID = 3435915707608174598L;

	public SecTerminal() {
		super();
	}

	public SecTerminal(SecAbstractTerminal d) {
		super(d);
	}

}
