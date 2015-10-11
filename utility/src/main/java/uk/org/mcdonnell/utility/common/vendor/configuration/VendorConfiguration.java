package uk.org.mcdonnell.utility.common.vendor.configuration;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

public class VendorConfiguration extends uk.org.mcdonnell.utility.common.PropertyManipulation {

    private static final long serialVersionUID = -5811549072384642131L;

    public static enum VENDORS {
        WEBLOGIC, ACTIVEMQ, UNSUPPORTED
    };

    public VendorConfiguration() throws InvalidPropertiesFormatException, IOException {
        super();
    }

    public static boolean isSupportedVendor(String vendorName) {
        return (getVendorType(vendorName).equals(VENDORS.UNSUPPORTED) ? false : true);
    }

    private static VENDORS getVendorType(String vendorName) {
        VENDORS supportedVendor;

        if (vendorName.startsWith("weblogic")) {
            supportedVendor = VENDORS.WEBLOGIC;
        } else if (vendorName.startsWith("org.apache.activemq")) {
            supportedVendor = VENDORS.ACTIVEMQ;
        } else {
            supportedVendor = VENDORS.UNSUPPORTED;
        }

        return supportedVendor;
    }
}
