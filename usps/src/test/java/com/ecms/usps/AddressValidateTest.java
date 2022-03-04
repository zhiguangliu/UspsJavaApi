package com.ecms.usps;

import com.madbox.usps.Address;
import com.madbox.usps.utils.UspsRequestException;
import com.madbox.usps.utils.UspsServices;
import org.junit.Test;

import java.io.IOException;

public class AddressValidateTest {


    /**
     * 一般的请求
     *
     * @throws IOException
     */
    @Test
    public void testAddressValidate() throws IOException, UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
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
     *
     * @throws IOException
     */
    @Test(expected = UspsRequestException.class)
    public void testAddressValidateOnlyAddress1() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
        Address address = new Address().init();
        address.setAddress2("17651 Railroad Street");
        try {
            uspsServices.validatedAddress(address);
        } catch (UspsRequestException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 不设置字段值，xml没有对应节点，报错
     *
     * @throws IOException
     */
    @Test(expected = UspsRequestException.class)
    public void testAddressValidateOnlyAddress2() throws UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
        Address address = new Address();
        address.setAddress2("17651 Railroad Street");
        uspsServices.validatedAddress(address);
    }

    @Test(expected = UspsRequestException.class)
    public void testAddressValidateOnlyAddressAndState() throws IOException, UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
        Address address = new Address().init();
        address.setAddress2("17651 Railroad Street");
        address.setState("CA");
        uspsServices.validatedAddress(address);
    }

    /**
     * 这个是地址+城市+州  可以用
     *
     * @throws IOException
     */
    @Test
    public void testAddressValidateAddressAndStateAndCity() throws IOException, UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
        Address address = new Address().init();
        address.setAddress2("17651 Railroad Street");
        address.setCity("City of Industry");
        address.setState("CA");
        uspsServices.validatedAddress(address);
    }

    /**
     * 这个是地址+zip5  可以用
     *
     * @throws IOException
     */
    @Test
    public void testAddressValidateAddressAndZip5() throws IOException, UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
        Address address = new Address().init();
        address.setAddress2("17651 Railroad Street");
        address.setZip("91748");
        uspsServices.validatedAddress(address);
    }

    /**
     * 测试拼写错误1 这个可以修正
     * 17651 Rallroad Street -> 17651 RAILROAD ST
     *
     * @throws IOException
     */
    @Test
    public void testAddressValidateAddressSpellError1() throws IOException, UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
        Address address = new Address().init();
        address.setAddress2("17651 Rallroad Street");
        address.setZip("91748");
        uspsServices.validatedAddress(address);
    }

    /**
     * 测试拼写错误2 这个可以修正
     * 17651 Ralroad Street -> 17651 RAILROAD ST
     *
     * @throws IOException
     */
    @Test
    public void testAddressValidateAddressSpellError2() throws IOException, UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
        Address address = new Address().init();
        address.setAddress2("17651 Ralroad Street");
        address.setZip("91748");
        uspsServices.validatedAddress(address);
    }

    /**
     * 测试拼写错误3 这个无法修正
     * 17651 Raiload Street   缺少r
     *
     * @throws IOException
     */
    @Test(expected = UspsRequestException.class)
    public void testAddressValidateAddressSpellError3() throws IOException, UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
        Address address = new Address().init();
        address.setAddress2("17651 Raiload Street");
        address.setZip("91748");
        uspsServices.validatedAddress(address);
    }

    /**
     * 只传邮编，不能用
     *
     * @throws IOException
     */
    @Test(expected = UspsRequestException.class)
    public void testAddressValidateOnlyZipCode() throws IOException, UspsRequestException {
        UspsServices uspsServices = new UspsServices("628ECMS00573");
        Address address = new Address().init();
        address.setZip("91748");
        uspsServices.validatedAddress(address);
    }

}
