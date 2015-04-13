package uk.org.mcdonnell.stonehouse;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class BootstrapTest {

    @Test
    public void ClasspathInitialisationTest() {
        try {
            @SuppressWarnings("unused")
            Bootstrap bootstrap = new Bootstrap();

            assertTrue(true);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
