package uk.org.mcdonnell.stonehouse.service;

import java.util.Iterator;

import org.junit.Test;

public class WebLogicMBeanHelperTest {

    @Test
    public void getQueueMessageCount() {
        // service:jmx:iiop://172.31.49.24:7001/jndi/weblogic.management.mbeanservers.runtime
        WebLogicMBeanHelper webLogicMBeanHelper = new WebLogicMBeanHelper("iiop://localhost:7001", "weblogic", "welcome1");

        @SuppressWarnings("unchecked")
        Iterator<Object> jmsRuntimesIterator = (Iterator<Object>) webLogicMBeanHelper.getJMSRuntimes();
        while (jmsRuntimesIterator.hasNext()) {
            Object object = jmsRuntimesIterator.next();

            System.out.println(object.toString());
        }
    }
}
