package uk.org.mcdonnell.stonehouse;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;
import uk.org.mcdonnell.stonehouse.service.ProviderConnectionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ProviderConnectionTest.class })
public class ProviderTest{
	
	public static Test suite() {
		return new TestSuite(ProviderConnectionTest.class);
	}
}
