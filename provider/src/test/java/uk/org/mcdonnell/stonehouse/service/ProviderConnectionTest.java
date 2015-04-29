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
    public void testProviderJNDIInitialContextEnvironmentIsNotEmpty() {
        Hashtable<String, String> emptyJNDIInitialContextEnvironment;
        try {
            ProviderConnection providerConnection = new ProviderConnection();

            emptyJNDIInitialContextEnvironment = (Hashtable<String, String>) PrivateAccessor
                    .invoke(providerConnection,
                            "getJNDIInitialContextEnvironment", new Class[] {},
                            new Object[] {});

            assertTrue(emptyJNDIInitialContextEnvironment != null
                    && emptyJNDIInitialContextEnvironment.isEmpty());
        } catch (Throwable e) {
            Assert.fail("Error occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGetAllProviders() throws Throwable {
        ProviderConnectionFactory providerConnectionFactory = new ProviderConnectionFactory();
        PropertyManipulation propertyManipulation;
        providerConnectionFactory.getAllProviders();

        assertTrue(providerConnectionFactory.getAllProviders() != null);
        assertTrue(!providerConnectionFactory.getAllProviders().isEmpty());

        propertyManipulation = (PropertyManipulation) PrivateAccessor.invoke(
                providerConnectionFactory, "getPropertyManipulation",
                new Class[] {}, new Object[] {});

        assertTrue(propertyManipulation.containsKey(String.format("1.%s",
                Context.INITIAL_CONTEXT_FACTORY)));
        assertTrue(propertyManipulation.containsKey(String.format("1.%s",
                Context.PROVIDER_URL)));
        assertTrue(propertyManipulation.containsKey(String.format("1.%s",
                Context.SECURITY_PRINCIPAL)));
        assertTrue(propertyManipulation.containsKey(String.format("1.%s",
                Context.SECURITY_CREDENTIALS)));
    }
}
