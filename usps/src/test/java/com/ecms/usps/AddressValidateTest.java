package com.ecms.usps;

import com.alibaba.fastjson.JSON;
import com.ecms.usps.constant.UserId;
import com.madbox.usps.Address;
import com.madbox.usps.utils.UspsRequestException;
import com.madbox.usps.utils.UspsServices;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressValidateTest {

    Logger logger = LoggerFactory.getLogger(AddressValidateTest.class);

    /**
     * 一般的请求
     */
    @Test
    public void testAddressValidate() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address();
        address.setAddress1("");
        address.setAddress2("17651 Railroad Street");
        address.setState("CA");
        address.setCity("City of Industry");
        address.setZip("91747");
        address.setZip2("");
        uspsServices.validatedAddress(address);
    }

    /**
     * 字段设置为空值，xml有对应节点，没有值，报错
     */
    @Test(expected = UspsRequestException.class)
    public void testAddressValidateOnlyAddress1() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address().init();
        address.setAddress2("17651 Railroad Street");
        try {
            uspsServices.validatedAddress(address);
        } catch (UspsRequestException e) {
            logger.info(JSON.toJSONString(e.getError()));
            throw e;
        }

    }

    /**
     * 不设置字段值，xml没有对应节点，报错
     */
    @Test(expected = UspsRequestException.class)
    public void testAddressValidateOnlyAddress2() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address();
        address.setAddress2("17651 Railroad Street");
        try {
            uspsServices.validatedAddress(address);
        } catch (UspsRequestException e) {
            logger.info(JSON.toJSONString(e.getError()));
            throw e;
        }
    }

    @Test(expected = UspsRequestException.class)
    public void testAddressValidateOnlyAddressAndState() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address().init();
        address.setAddress2("17651 Railroad Street");
        address.setState("CA");
        try {
            uspsServices.validatedAddress(address);
        } catch (UspsRequestException e) {
            logger.info(JSON.toJSONString(e.getError()));
            throw e;
        }
    }

    /**
     * 这个是地址+城市+州  可以用
     */
    @Test
    public void testAddressValidateAddressAndStateAndCity() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address().init();
        address.setAddress2("17651 Railroad Street");
        address.setCity("City of Industry");
        address.setState("CA");
        uspsServices.validatedAddress(address);
    }

    /**
     * 这个是地址+zip5  可以用
     */
    @Test
    public void testAddressValidateAddressAndZip5() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address().init();
        address.setAddress2("17651 Railroad Street");
        address.setZip("91748");
        uspsServices.validatedAddress(address);
    }

    /**
     * 测试拼写错误1 这个可以修正
     * 17651 Rallroad Street -> 17651 RAILROAD ST
     */
    @Test
    public void testAddressValidateAddressSpellError1() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address().init();
        address.setAddress2("17651 Rallroad Street");
        address.setZip("91748");
        uspsServices.validatedAddress(address);
    }

    /**
     * 测试拼写错误2 这个可以修正
     * 17651 Ralroad Street -> 17651 RAILROAD ST
     */
    @Test
    public void testAddressValidateAddressSpellError2() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address().init();
        address.setAddress2("17651 Ralroad Street");
        address.setZip("91748");
        uspsServices.validatedAddress(address);
    }

    /**
     * 测试拼写错误3 这个无法修正
     * 17651 Raiload Street   缺少r
     */
    @Test(expected = UspsRequestException.class)
    public void testAddressValidateAddressSpellError3() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address().init();
        address.setAddress2("17651 Raiload Street");
        address.setZip("91748");
        try {
            uspsServices.validatedAddress(address);
        } catch (UspsRequestException e) {
            logger.info(JSON.toJSONString(e.getError()));
            throw e;
        }
    }

    /**
     * 只传邮编，不能用
     */
    @Test(expected = UspsRequestException.class)
    public void testAddressValidateOnlyZipCode() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices(UserId.VALUE);
        Address address = new Address().init();
        address.setZip("91748");
        try {
            uspsServices.validatedAddress(address);
        } catch (UspsRequestException e) {
            logger.info(JSON.toJSONString(e.getError()));
            throw e;
        }
    }

}
