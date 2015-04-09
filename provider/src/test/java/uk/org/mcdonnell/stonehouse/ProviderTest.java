package uk.org.mcdonnell.stonehouse;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.org.mcdonnell.stonehouse.service.DestinationTest;
import uk.org.mcdonnell.stonehouse.service.DestinationsTest;
import uk.org.mcdonnell.stonehouse.service.ProviderConnectionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ProviderConnectionTest.class, DestinationsTest.class, DestinationTest.class })
public class ProviderTest {
}
