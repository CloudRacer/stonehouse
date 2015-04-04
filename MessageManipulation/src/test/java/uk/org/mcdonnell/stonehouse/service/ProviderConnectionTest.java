package uk.org.mcdonnell.stonehouse.service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junitx.util.PrivateAccessor;

public class ProviderConnectionTest extends TestCase {

	public ProviderConnectionTest(String testName){
		super(testName);
	}
	
	public static Test suite(){
		return new TestSuite(ProviderConnectionTest.class);
	}
	
	public void testProviderConnection(){
		ProviderConnection providerConnection = new ProviderConnection();
		
		
		//PrivateAccessor.invoke(ProviderConnection.class, "method", new Class[]{String.class}}, args)
		
		//providerConnection.getInitialContext();
	}
}
