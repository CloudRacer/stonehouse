package uk.org.mcdonnell.stonehouse.helper;

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

public abstract class ActiveMQHelperBaseIT {
    private static BrokerService broker;
    private static NetworkConnector networkConnector;
    private static TransportConnector transportConnector;
    private static Connection connection;

    protected static void createTestData() throws Exception {
        final String testQueueName = "test_queue_%s";
        final int totalTestQueues = 10;
        final int totalTestMessagesPerQueue = 2;

        for (int i = 1; i <= totalTestQueues; i++) {
            new TestMessagesProducer(String.format(testQueueName, i), totalTestMessagesPerQueue).createMessages();
        }
    }

    protected static BrokerService getBroker() throws Exception {
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

    protected static void setBroker(final BrokerService broker) throws Exception {
        if (broker == null) {
            setTransportConnector(null);
            if (ActiveMQHelperBaseIT.broker != null) {
                ActiveMQHelperBaseIT.broker.stop();
            }
        }

        ActiveMQHelperBaseIT.broker = broker;
    }

    private static void setTransportConnector(final TransportConnector transportConnector) throws Exception {
        if (transportConnector == null) {
            if (ActiveMQHelperBaseIT.networkConnector != null) {
                ActiveMQHelperBaseIT.transportConnector.stop();
            }
        }

        ActiveMQHelperBaseIT.transportConnector = transportConnector;
    }

    private static Connection getConnection() throws JMSException {
        if (connection == null) {
            final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://stonehouse");
            connection = connectionFactory.createConnection();
            connection.start();
        }

        return connection;
    }

    protected static void setConnection(final Connection connection) throws JMSException {
        if (connection == null) {
            if (ActiveMQHelperBaseIT.connection != null) {
                ActiveMQHelperBaseIT.connection.stop();
            }
        }

        ActiveMQHelperBaseIT.connection = connection;
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
