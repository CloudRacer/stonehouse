package uk.org.mcdonnell.stonehouse.api.destination;

import java.io.IOException;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.NamingException;

import uk.org.mcdonnell.stonehouse.api.connection.ProviderConnectionFactory;

public class Destinations {

    public enum DestinationType {
        QUEUE, TOPIC
    };

    private ProviderConnectionFactory providerConnection = null;

    private Hashtable<String, Destination> destinationList = null;

    public Destinations(final ProviderConnectionFactory providerConnection) {
        setProviderConnection(providerConnection);
    }

    public Hashtable<String, Destination> getAllDestinations() throws IOException, MalformedObjectNameException, NamingException, JMSException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException {
        final String jmxURL = (String) getProviderConnection().getJNDIInitialContext().getEnvironment().get("jmx.url");
        final String jmxBrokerName = (String) getProviderConnection().getJNDIInitialContext().getEnvironment().get("jmx.broker.name");
        final String jmxBrokerQueueAttribute = (String) getProviderConnection().getJNDIInitialContext().getEnvironment().get("jmx.broker.queue.attribute");

        final JMXServiceURL url = new JMXServiceURL(jmxURL);
        final JMXConnector jmxc = JMXConnectorFactory.connect(url);
        final MBeanServerConnection conn = jmxc.getMBeanServerConnection();
        final ObjectName broker = new ObjectName(jmxBrokerName);
        final ObjectName[] queues = (ObjectName[]) conn.getAttribute(broker, jmxBrokerQueueAttribute);
        for (final ObjectName queue : queues) {
            getDestinationList().put(queue.toString(), new Destination(getProviderConnection(), DestinationType.QUEUE, queue.toString()));
        }

        return getDestinationList();
    }

    private ProviderConnectionFactory getProviderConnection() {
        return providerConnection;
    }

    private void setProviderConnection(final ProviderConnectionFactory providerConnection) {
        this.providerConnection = providerConnection;
    }

    private Hashtable<String, Destination> getDestinationList() {
        if (destinationList == null) {
            destinationList = new Hashtable<String, Destination>();
        }

        return destinationList;
    }
}
