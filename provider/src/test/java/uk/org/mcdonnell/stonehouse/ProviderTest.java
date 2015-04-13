package uk.org.mcdonnell.stonehouse;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.org.mcdonnell.stonehouse.service.DestinationTest;
import uk.org.mcdonnell.stonehouse.service.DestinationsTest;
import uk.org.mcdonnell.stonehouse.service.ProviderConnectionTest;
import uk.org.mcdonnell.stonehouse.service.WebLogicMBeanHelperTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ProviderConnectionTest.class, DestinationsTest.class, DestinationTest.class, WebLogicMBeanHelperTest.class })
public class ProviderTest {
}
