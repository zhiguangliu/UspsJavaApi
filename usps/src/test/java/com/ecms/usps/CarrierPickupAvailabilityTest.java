package com.ecms.usps;

import com.ecms.usps.constant.UserId;
import com.madbox.usps.request.CarrierPickupAvailabilityRequest;
import com.madbox.usps.utils.UspsServices;
import org.junit.Test;

import java.io.IOException;

public class CarrierPickupAvailabilityTest {


    /**
     * 一般的请求
     *
     * @throws IOException
     */
    @Test
    public void testCarrierPickupAvailability() throws IOException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);

        CarrierPickupAvailabilityRequest carrierPickupAvailabilityRequest = new CarrierPickupAvailabilityRequest("628ECMS00573");
//        carrierPickupAvailabilityRequest.setFirmName("ECMS Corp.");
//        carrierPickupAvailabilityRequest.setSuiteOrApt("");
//        carrierPickupAvailabilityRequest.setAddress2("17651 Railroad Street");
//        carrierPickupAvailabilityRequest.setUrbanization("");
//        carrierPickupAvailabilityRequest.setCity("CITY INDUSTRY");
//        carrierPickupAvailabilityRequest.setState("CA");
//        carrierPickupAvailabilityRequest.setZIP5("91748");
//        carrierPickupAvailabilityRequest.setZIP4("1194");

        //test address:        209 W 118th St #2G New York, NY 10026. USA

        carrierPickupAvailabilityRequest.setFirmName("ECMS Corp.");
        carrierPickupAvailabilityRequest.setSuiteOrApt("");
        carrierPickupAvailabilityRequest.setAddress2("209 W 118th St #2G");
        carrierPickupAvailabilityRequest.setUrbanization("");
        carrierPickupAvailabilityRequest.setCity("New York");
        carrierPickupAvailabilityRequest.setState("NY");
        carrierPickupAvailabilityRequest.setZIP5("11208");
        carrierPickupAvailabilityRequest.setZIP4("");


        uspsServices.carrierPickupAvailabilityRequest(carrierPickupAvailabilityRequest);


//        Address address = new Address();
//        address.setAddress1("");
//        address.setAddress2("17651 Railroad Street");
//        address.setState("CA");
//        address.setCity("City of Industry");
//        address.setZip("91747");
//        address.setZip2("");
//        uspsServices.validatedAddress(address);
    }

    /**
     * 一般的请求
     *
     * @throws IOException
     */
    @Test
    public void testCarrierPickupAvailability1() throws IOException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);

        //17651 Railroad Street, City of Industry CA 91748 USA
        //17651 RAILROAD ST, CITY INDUSTRY, CA 91748-1194
        /*       验证返回的结果
        {
            "address":{
                "address2":"17651 RAILROAD ST",
                "city":"CITY INDUSTRY",
                "state":"CA",
                "zip":"91748",
                "zip2":"1194"
            }
        }
        */

        CarrierPickupAvailabilityRequest carrierPickupAvailabilityRequest = new CarrierPickupAvailabilityRequest();
        carrierPickupAvailabilityRequest.setFirmName("ECMS Corp.");
        carrierPickupAvailabilityRequest.setSuiteOrApt("");
        carrierPickupAvailabilityRequest.setAddress2("17651 RAILROAD ST, CITY INDUSTRY");
        carrierPickupAvailabilityRequest.setUrbanization("");
        carrierPickupAvailabilityRequest.setCity("CITY INDUSTRY");
        carrierPickupAvailabilityRequest.setState("CA");
        carrierPickupAvailabilityRequest.setZIP5("91748");
        carrierPickupAvailabilityRequest.setZIP4("1194");

        uspsServices.carrierPickupAvailabilityRequest(carrierPickupAvailabilityRequest);
    }

}
