package uk.org.mcdonnell.stonehouse;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.network.NetworkConnector;
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
    private static BrokerService broker;
    private static NetworkConnector networkConnector;
    private static TransportConnector transportConnector;
    private static Connection connection;

    @BeforeClass
    public static void setupClass() throws Exception {

        // Start the ActiveMQ broker.
        getBroker();

        // Create test data.
        createTestData();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        setConnection(null);
        setBroker(null);
    }

    private static void createTestData() throws Exception {
        System.out.println("TEST");
        final String testQueueName = "test_queue_%s";
        final int totalTestQueues = 10;
        final int totalTestMessagesPerQueue = 2;

        for (int i = 1; i <= totalTestQueues; i++) {
            new TestMessagesProducer(String.format(testQueueName, i), totalTestMessagesPerQueue).createMessages();
        }
    }

    private static BrokerService getBroker() throws Exception {
        if (broker == null) {
            broker = new BrokerService();
            broker.setBrokerName("stonehouse");
            broker.addConnector("tcp://localhost:61616");
            broker.setUseShutdownHook(false);
            broker.setPersistent(false);
            broker.setUseJmx(true); // jConsole connection URI: service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi
            // Add plugin
            // broker.setPlugins(new BrokerPlugin[] { new JaasAuthenticationPlugin() });
            broker.start();
        }

        return broker;
    }

    private static void setBroker(final BrokerService broker) throws Exception {
        if (broker == null) {
            setTransportConnector(null);
            ProviderTest.broker.stop();
        }

        ProviderTest.broker = broker;
    }

    private static void setTransportConnector(final TransportConnector transportConnector) throws Exception {
        if (transportConnector == null) {
            if (ProviderTest.networkConnector != null) {
                ProviderTest.transportConnector.stop();
            }
        }

        ProviderTest.transportConnector = transportConnector;
    }

    private static Connection getConnection() throws JMSException {
        if (connection == null) {
            final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://stonehouse");
            connection = connectionFactory.createConnection();
            connection.start();
        }

        return connection;
    }

    private static void setConnection(final Connection connection) throws JMSException {
        if (connection == null) {
            if (ProviderTest.connection != null) {
                ProviderTest.connection.stop();
            }
        }

        ProviderTest.connection = connection;
    }

    private static class TestMessagesProducer {

        private String queueName;
        private int totalTestMessages;

        public TestMessagesProducer(final String queueName, final int totalTestMessages) {
            setQueueName(queueName);
            setTotalTestMessages(totalTestMessages);
        }

        public void createMessages() {
            try {
                for (int i = 1; i <= getTotalTestMessages(); i++) {
                    // Create a Session
                    final Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
                    // Create the destination (Topic or Queue)
                    final Destination destination = session.createQueue(getQueueName());

                    // Create a MessageProducer from the Session to the Topic or Queue
                    final MessageProducer producer = session.createProducer(destination);
                    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                    // Create a messages
                    final String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
                    final TextMessage message = session.createTextMessage(text);

                    // Tell the producer to send the message
                    System.out.println(String.format("Sending message (%s), to destination \"%s\", from thread \"%s\": %s", i, getQueueName(), Thread.currentThread().getName(), message.hashCode()));
                    producer.send(message);
                }
            } catch (final Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }

        private void setQueueName(final String queueName) {
            this.queueName = queueName;
        }

        private String getQueueName() {
            return queueName;
        }

        private int getTotalTestMessages() {
            return totalTestMessages;
        }

        private void setTotalTestMessages(final int totalTestMessages) {
            this.totalTestMessages = totalTestMessages;
        }
    }
}
