package com.ecms.usps;

import com.ecms.usps.constant.UserId;
import com.madbox.usps.ZipCode;
import com.madbox.usps.utils.UspsServices;
import org.junit.Test;

public class CityStateLookupTest {


    @Test
    public void testCityStateLookup()  {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        ZipCode zipCode = new ZipCode();
        zipCode.setZip5("91748");
        zipCode.setCity("City of Industry");
        zipCode.setState("CA");

        uspsServices.cityStateLookup(zipCode);

    }

    @Test
    public void testZipCodeLookupErrorLetter()  {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);

        ZipCode zipCode = new ZipCode();
        zipCode.setCity("City of Industry");
        zipCode.setZip5("91748");

        uspsServices.cityStateLookup(zipCode);

    }
}
