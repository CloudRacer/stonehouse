package uk.org.mcdonnell.stonehouse.service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

public class DistribuedQueueBrowser {
    private final Connection connection;
    private final Session session;
    private final InitialContext ctx;
    private final Iterable<String> queueNames;

    public DistribuedQueueBrowser(String adminUrl, String providerUrl,
            String distributedDestinationName, String userName, String password) throws Exception {
        ctx = getInitialContext(providerUrl, userName, password);
        WeblogicMBeanHelper factory = null;

        try {
            factory = new WeblogicMBeanHelper(adminUrl, userName, password);
            queueNames = factory.getDistributedMemberJndiNames(distributedDestinationName);
        } finally {
            if (factory != null) {
                factory.close();
            }
        }

        ConnectionFactory connFactory = (ConnectionFactory) ctx
                .lookup("com.overstock.jmshelpertest.jms.connectionfactory");
        connection = connFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    private InitialContext getInitialContext(String providerUrl, String userName, String password) throws Exception {
        Hashtable<String, String> ht = new Hashtable<String, String>();

        ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        ht.put(Context.PROVIDER_URL, providerUrl);
        ht.put(Context.SECURITY_PRINCIPAL, userName);
        ht.put(Context.SECURITY_CREDENTIALS, password);

        return new InitialContext(ht);
    }

    public Enumeration<Message> getEnumeration() throws JMSException, NamingException {
        return new JmsMessageEnumeration(getMessageEnumeratorMap());
    }

    private Map<String, Enumeration<Message>> getMessageEnumeratorMap() throws JMSException,
            NamingException {
        Map<String, Enumeration<Message>> serverMessageMap = new HashMap<String, Enumeration<Message>>();

        for (String queueName : queueNames) {
            String serverDq[] = StringUtils.split(queueName, "@");
            Queue queue = (Queue) ctx.lookup(queueName);
            javax.jms.QueueBrowser qb = session.createBrowser(queue);
            @SuppressWarnings("unchecked")
            Enumeration<Message> enumeration = qb.getEnumeration();
            serverMessageMap.put(serverDq[0], enumeration);
        }

        return serverMessageMap;
    }

    public Enumeration<ServerLocatedMessage> getServerLocatedEnumeration() throws JMSException,
            NamingException {
        return new ServerLocatedMessageEnumeration(getMessageEnumeratorMap());
    }

    public static class ServerLocatedMessage {
        private final Message message;

        private final String jmsServerName;

        public ServerLocatedMessage(String jmsServerName, Message message) {
            this.message = message;
            this.jmsServerName = jmsServerName;
        }

        public Message getMessage() {
            return message;
        }

        public String getJmsServerName() {
            return jmsServerName;
        }

        @Override
        public String toString() {
            return jmsServerName + "-" + message;
        }
    }

    private static abstract class AbstractMessageEnumeration<T> implements Enumeration<T> {
        Map.Entry<String, Enumeration<Message>> current;

        private Enumeration<Message> currMessageEnumer;

        private final Iterator<Map.Entry<String, Enumeration<Message>>> iterator;

        public AbstractMessageEnumeration(Map<String, Enumeration<Message>> map) {
            iterator = map.entrySet().iterator();
            current = iterator.hasNext()
                    ? iterator.next()
                    : null;
            currMessageEnumer = current != null
                    ? current.getValue()
                    : new Enumeration<Message>() {

                        public boolean hasMoreElements() {
                            return false;
                        }

                        public Message nextElement() {
                            throw new NoSuchElementException();
                        }
                    };
        }

        Enumeration<Message> getEnumeration() {
            if (current == null || currMessageEnumer.hasMoreElements()) {
                return currMessageEnumer;
            }

            while (iterator.hasNext()) {
                current = iterator.next();
                currMessageEnumer = current.getValue();
                if (currMessageEnumer.hasMoreElements()) {
                    return currMessageEnumer;
                }
            }

            return currMessageEnumer;
        }

        public boolean hasMoreElements() {
            return getEnumeration().hasMoreElements();
        }
    }

    private static class ServerLocatedMessageEnumeration extends
            AbstractMessageEnumeration<ServerLocatedMessage> {

        public ServerLocatedMessageEnumeration(Map<String, Enumeration<Message>> map) {
            super(map);
        }

        public ServerLocatedMessage nextElement() {
            Message message = getEnumeration().nextElement();
            return new ServerLocatedMessage(current.getKey(), message);
        }
    }

    private static class JmsMessageEnumeration extends AbstractMessageEnumeration<Message> {
        public JmsMessageEnumeration(Map<String, Enumeration<Message>> map) {
            super(map);
        }

        public Message nextElement() {
            return getEnumeration().nextElement();
        }
    }

    public void close() {
        try {
            session.close();
        } catch (JMSException ignored) {
        }
        try {
            connection.close();
        } catch (JMSException ignored) {
        }

        try {
            ctx.close();
        } catch (NamingException ignored) {
        }
    }
}
