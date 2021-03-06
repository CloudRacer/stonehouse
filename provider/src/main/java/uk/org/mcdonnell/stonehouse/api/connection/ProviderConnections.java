package uk.org.mcdonnell.stonehouse.api.connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Map.Entry;

import uk.org.mcdonnell.utility.common.PropertyManipulation;

public class ProviderConnections {

    private uk.org.mcdonnell.utility.common.PropertyManipulation propertyManipulation;

    Map<Integer, ProviderConnectionFactory> providerConnectionList = null;

    @SuppressWarnings("unchecked")
    public Map<Integer, ProviderConnectionFactory> getAllProviders()
            throws InvalidPropertiesFormatException, IOException {
        if (providerConnectionList == null) {
            providerConnectionList = new HashMap<Integer, ProviderConnectionFactory>();

            Hashtable<String, String> providersJNDIInitialContextEnvironment = new Hashtable<String, String>();
            int index = 1;
            while (providersJNDIInitialContextEnvironment != null) {
                for (final Entry<Object, Object> property : getPropertyManipulation()
                        .entrySet()) {
                    if (property.getKey().toString()
                            .startsWith(Integer.toString(index) + ".")) {
                        String key = property.getKey().toString();

                        key = key.substring(key.indexOf(".") + 1, key.length());

                        providersJNDIInitialContextEnvironment.put(key,
                                property.getValue().toString());
                    }
                }
                if (providersJNDIInitialContextEnvironment.isEmpty()) {
                    providersJNDIInitialContextEnvironment = null;
                } else {
                    final ProviderConnectionFactory providerConnection = new ProviderConnectionFactory();

                    providerConnection
                            .setJNDIInitialContextEnvironment((Hashtable<String, String>) providersJNDIInitialContextEnvironment
                                    .clone());
                    providerConnectionList.put(index, providerConnection);

                    providersJNDIInitialContextEnvironment.clear();

                    index++;
                }
            }
        }

        return providerConnectionList;
    }

    private PropertyManipulation getPropertyManipulation()
            throws InvalidPropertiesFormatException, IOException {
        if (propertyManipulation == null) {
            final String PROVIDER_PROPERTY_FILENAME = "provider.properties";
            propertyManipulation = new PropertyManipulation(
                    PROVIDER_PROPERTY_FILENAME);
        }

        return propertyManipulation;
    }
}
