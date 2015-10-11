package uk.org.mcdonnell.stonehouse.service;

import static org.junit.Assert.assertTrue;

import java.util.Hashtable;

import javax.naming.Context;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import junitx.util.PrivateAccessor;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnections;
import uk.org.mcdonnell.utility.common.Bootstrap;
import uk.org.mcdonnell.utility.common.PropertyManipulation;

public class ProviderConnectionTest {

    @BeforeClass
    public static void setupClass() throws Exception {
        // Load plugin JAR files.
        Bootstrap.start();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testProviderJNDIInitialContextEnvironmentIsEmpty() {
        Hashtable<String, String> emptyJNDIInitialContextEnvironment;
        try {
            final ProviderConnectionFactory providerConnection = new ProviderConnectionFactory();

            emptyJNDIInitialContextEnvironment = (Hashtable<String, String>) PrivateAccessor
                    .invoke(providerConnection,
                            "getJNDIInitialContextEnvironment", new Class[] {},
                            new Object[] {});

            assertTrue(emptyJNDIInitialContextEnvironment != null
                    && emptyJNDIInitialContextEnvironment.isEmpty());
        } catch (final Throwable e) {
            Assert.fail("Error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGetAllProviders() throws Throwable {
        final ProviderConnections providerConnectionFactory = new ProviderConnections();
        uk.org.mcdonnell.utility.common.PropertyManipulation propertyManipulation;
        providerConnectionFactory.getAllProviders();

        Assert.assertNotNull("Not provider definitions retrieved from the configuration file.", providerConnectionFactory.getAllProviders());
        assertTrue(!providerConnectionFactory.getAllProviders().isEmpty());

        propertyManipulation = (PropertyManipulation) PrivateAccessor.invoke(
                providerConnectionFactory, "getPropertyManipulation",
                new Class[] {}, new Object[] {});

        Assert.assertNotNull("No properties retrieved from the configuration file.", propertyManipulation);
        assertTrue(!propertyManipulation.isEmpty());

        System.out.println(String.format("Properties retrieved from the configuration file: %s.", propertyManipulation.toString()));

        assertTrue(propertyManipulation.containsKey(String.format("1.%s",
                Context.INITIAL_CONTEXT_FACTORY)));
        assertTrue(propertyManipulation.containsKey(String.format("1.%s",
                Context.PROVIDER_URL)));
    }
}
