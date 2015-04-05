package uk.org.mcdonnell.stonehouse.service;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProviderConnection {
	Hashtable<String, String> jndiInitialContextEnvironment;
	InitialContext jndiInitialContext;

	public ProviderConnection() {
	}

	public void setJNDIInitialContextEnvironment(
			Hashtable<String, String> jndiInitialContextEnvironment) {
		this.jndiInitialContextEnvironment = jndiInitialContextEnvironment;
	}

	private Hashtable<String, String> getJNDIInitialContextEnvironment() {
		if (jndiInitialContextEnvironment == null) {
			jndiInitialContextEnvironment = new Hashtable<String, String>();
		}

		return jndiInitialContextEnvironment;
	}

	public InitialContext getJNDIInitialContext() throws NamingException {
		if (jndiInitialContext == null) {
			jndiInitialContext = new InitialContext(
					getJNDIInitialContextEnvironment());
		}

		return jndiInitialContext;

	}
}
