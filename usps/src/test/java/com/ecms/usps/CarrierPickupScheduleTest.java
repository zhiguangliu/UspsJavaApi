package com.ecms.usps;

import com.ecms.usps.constant.UserId;
import com.madbox.usps.Package;
import com.madbox.usps.request.CarrierPickupScheduleRequest;
import com.madbox.usps.utils.UspsServices;
import org.junit.Test;

import java.io.IOException;

public class CarrierPickupScheduleTest {


    /**
     * 一般的请求
     * @throws IOException
     */
    @Test
    public void testCarrierPickupSchedule() throws IOException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);

        CarrierPickupScheduleRequest carrierPickupScheduleRequest = new CarrierPickupScheduleRequest();
        carrierPickupScheduleRequest.setFirstName("zhiguang");
        carrierPickupScheduleRequest.setLastName("liu");
        carrierPickupScheduleRequest.setFirmName("ECMS Corp.");
        carrierPickupScheduleRequest.setSuiteOrApt("Suit A");
        carrierPickupScheduleRequest.setAddress2("17651 RAILROAD ST");
        carrierPickupScheduleRequest.setUrbanization("");
        carrierPickupScheduleRequest.setCity("CITY INDUSTRY");
        carrierPickupScheduleRequest.setState("CA");
        carrierPickupScheduleRequest.setZIP5("91748");
        carrierPickupScheduleRequest.setZIP4("1194");
        carrierPickupScheduleRequest.setPhone("(626)9565388");
        carrierPickupScheduleRequest.setExtension("");
        carrierPickupScheduleRequest.setaPackage(new Package().setCount("1").setServiceType("PriorityMailExpress"));
        carrierPickupScheduleRequest.setEstimatedWeight("12");
        carrierPickupScheduleRequest.setPackageLocation("Front Door");
        carrierPickupScheduleRequest.setSpecialInstructions("This is a test order.");


        uspsServices.carrierPickupScheduleRequest(carrierPickupScheduleRequest);



//        Address address = new Address();
//        address.setAddress1("");
//        address.setAddress2("17651 Railroad Street");
//        address.setState("CA");
//        address.setCity("City of Industry");
//        address.setZip("91747");
//        address.setZip2("");
//        uspsServices.validatedAddress(address);
    }

}
