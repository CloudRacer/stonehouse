package uk.org.mcdonnell.common.vendor.configuration;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

import uk.org.mcdonnell.common.generic.PropertyManipulation;

public class VendorConfiguration extends PropertyManipulation {

    private static final long serialVersionUID = -5811549072384642131L;

    public static enum VENDORS {
        WEBLOGIC,
        ACTIVEMQ,
        UNSUPPORTED
    };

    public VendorConfiguration() throws InvalidPropertiesFormatException, IOException {
        super();
    }
}
