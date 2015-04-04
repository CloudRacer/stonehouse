package uk.org.mcdonnell.stonehouse.service;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProviderConnection {
	public ProviderConnection() {
	}

	private static InitialContext getInitialContext(String url, String username, String password)
			throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, getJndiFactory());
		env.put(Context.PROVIDER_URL, url);
		env.put( Context.SECURITY_PRINCIPAL, username );
		env.put( Context.SECURITY_CREDENTIALS, password );
		return new InitialContext(env);
	}

	public static String getJndiFactory() {
		final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";

		return JNDI_FACTORY;
	}

	public static String getJmsFactory() {
		final String JMS_FACTORY = "weblogic.examples.jms.QueueConnectionFactory";

		return JMS_FACTORY;
	}
}
