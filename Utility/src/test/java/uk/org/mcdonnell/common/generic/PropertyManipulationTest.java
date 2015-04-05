package uk.org.mcdonnell.common.generic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PropertyManipulationTest {

	private static final String PROVIDER_PROPERTY_FILENAME = "provider.properties";

	PropertyManipulation propertyManipulation;

	@Test
	public void testGetDefaultProperties() {
		try {
			assertTrue(getPropertyManipulation().getFilename().endsWith(PROVIDER_PROPERTY_FILENAME));
			assertTrue(new File(getPropertyManipulation().getFilename()).exists());
			assertTrue(getPropertyManipulation().getFile().exists());
			assertTrue(getPropertyManipulation().getFile().getAbsolutePath() == getPropertyManipulation().getFilename());
		} catch (Exception e) {
			e.printStackTrace();

			Assert.fail("Error occurred: " + e.getMessage());
		}
	}

	private PropertyManipulation getPropertyManipulation()
			throws InvalidPropertiesFormatException, IOException {
		if (propertyManipulation == null) {
			propertyManipulation = new PropertyManipulation(
					PROVIDER_PROPERTY_FILENAME);
		}

		return propertyManipulation;
	}
}
