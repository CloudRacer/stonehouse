package uk.org.mcdonnell.stonehouse;

import java.io.File;

import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.network.NetworkConnector;
import org.apache.activemq.security.JaasAuthenticationPlugin;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import uk.org.mcdonnell.stonehouse.service.DestinationTest;
import uk.org.mcdonnell.stonehouse.service.DestinationsTest;
import uk.org.mcdonnell.stonehouse.service.ProviderConnectionTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ProviderConnectionTest.class, DestinationsTest.class, DestinationTest.class })
public class ProviderTest {
    private final static File DEFAULT_FOLDER = new File("../application/plugins");
    private static BrokerService broker;

    @BeforeClass
    public static void setupClass() throws Exception {
        new Bootstrap(DEFAULT_FOLDER);

        // Start the ActiveMQ broker.
        getBroker();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        setBroker(null);
    }

    private static BrokerService getBroker() throws Exception {
        if (broker == null) {
            broker = new BrokerService();
            broker.setBrokerName("stonehouse");
            broker.setUseShutdownHook(false);
            broker.setPersistent(false);
            broker.setUseJmx(false);
            // Add plugin
            broker.setPlugins(new BrokerPlugin[] { new JaasAuthenticationPlugin() });
            // Add a network connection
            final NetworkConnector connector = broker.addNetworkConnector("static://" + "tcp://somehost:61616");
            connector.setDuplex(true);
            broker.addConnector("tcp://localhost:61616");
            broker.start();
        }

        return broker;
    }

    private static void setBroker(BrokerService broker) throws Exception {
        if (broker == null) {
            // connector.stop();
            // connection.close();
            ProviderTest.broker.stop();
        }

        ProviderTest.broker = broker;
    }
}
