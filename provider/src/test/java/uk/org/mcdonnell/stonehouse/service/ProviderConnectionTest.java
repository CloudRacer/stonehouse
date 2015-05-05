package uk.org.mcdonnell.stonehouse.service;

import static org.junit.Assert.assertTrue;

import java.util.Hashtable;

import javax.naming.Context;

import junitx.util.PrivateAccessor;

import org.junit.Assert;
import org.junit.Test;

import uk.org.mcdonnell.common.generic.PropertyManipulation;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnection;
import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;

public class ProviderConnectionTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testProviderJNDIInitialContextEnvironmentIsEmpty() {
        Hashtable<String, String> emptyJNDIInitialContextEnvironment;
        try {
            final ProviderConnection providerConnection = new ProviderConnection();

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
        final ProviderConnectionFactory providerConnectionFactory = new ProviderConnectionFactory();
        PropertyManipulation propertyManipulation;
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
