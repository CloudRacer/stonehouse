package uk.org.mcdonnell.common;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.org.mcdonnell.common.generic.ClasspathLoaderTest;
import uk.org.mcdonnell.common.generic.PropertyManipulationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PropertyManipulationTest.class, ClasspathLoaderTest.class })
public class CommonTest {
}
