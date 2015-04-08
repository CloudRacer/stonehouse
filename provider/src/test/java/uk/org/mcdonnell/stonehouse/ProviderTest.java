package uk.org.mcdonnell.stonehouse;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.org.mcdonnell.stonehouse.service.ProviderConnectionTest;
import uk.org.mcdonnell.stonehouse.service.DestinationsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ProviderConnectionTest.class, DestinationsTest.class })
public class ProviderTest {
}
