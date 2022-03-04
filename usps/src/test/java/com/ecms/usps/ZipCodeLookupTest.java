package com.ecms.usps;

import com.ecms.usps.constant.UserId;
import com.madbox.usps.Address;
import com.madbox.usps.utils.UspsServices;
import org.junit.Test;

public class ZipCodeLookupTest {


    @Test
    public void testZipCodeLookup() {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);

        Address address = new Address();
        address.setAddress1("");
        address.setAddress2("17651 Ralroad Street");
        address.setState("CA");
        address.setCity("City of Industry");
        address.setZip("");

        uspsServices.zipCodeLookup(address);

    }

    @Test
    public void testZipCodeLookupErrorLetter() {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);

        Address address = new Address();
        address.setAddress1("");
        address.setAddress2("17651 Rallroad Street");
        address.setState("CA");
        address.setCity("City of Industry");
        address.setZip("91748");

        uspsServices.zipCodeLookup(address);

    }
}
