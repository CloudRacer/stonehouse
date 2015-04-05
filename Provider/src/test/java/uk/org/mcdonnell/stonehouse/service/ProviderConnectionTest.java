package uk.org.mcdonnell.stonehouse.service;

import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junitx.util.PrivateAccessor;

public class ProviderConnectionTest extends TestCase {

	public ProviderConnectionTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(ProviderConnectionTest.class);
	}

	public void testProviderConnection() throws Throwable {
		final String PROTOCOL = "t3";
		final String SERVER_NAME = "localhost";
		final int PORT = 7001;
		final String URL = String.format("%s://%s:%s", PROTOCOL, SERVER_NAME,
				PORT);
		final String USERNAME = "weblogic";
		final String PASSWORD = "welcome1";
		final String JMS_SERVER_NAME = "WM6JMSServer";
		final String JMS_MODULE_NAME = "WM6_QUEUES";

		InitialContext initialContext = (InitialContext) PrivateAccessor.invoke(ProviderConnection.class, "getInitialContext",
				new Class[] { String.class, String.class, String.class }, new Object[] { URL, USERNAME, PASSWORD });

		assertTrue(initialContext.getEnvironment().containsKey("java.naming.provider.url"));
		assertEquals(initialContext.getEnvironment().get("java.naming.provider.url"), URL);
		
		ProviderConnection providerConnection = new ProviderConnection();
	}
}
