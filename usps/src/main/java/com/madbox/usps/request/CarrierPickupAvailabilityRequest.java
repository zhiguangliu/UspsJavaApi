/*******************************************************
 * Copyright (C) 2012-2019 Adam Dale adamdale2018@gmail.com
 *
 * This file is part of Madbox Solutions.
 *
 * USPS Java Api can not be copied and/or distributed without the express
 * permission of Madbox Solutions.
 *******************************************************/
package com.madbox.usps.request;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.StringWriter;


@XmlRootElement(name = "CarrierPickupAvailabilityRequest")
@XmlType(propOrder = {
        "firmName",
        "suiteOrApt",
        "address2",
        "urbanization",
        "city",
        "state",
        "ZIP5",
        "ZIP4"})
public class CarrierPickupAvailabilityRequest {
    private String userId;

    private String FirmName;
    private String SuiteOrApt;
    private String Address2;
    private String Urbanization;
    private String City;
    private String State;
    private String ZIP5;
    private String ZIP4;

    public CarrierPickupAvailabilityRequest() {

    }

    public CarrierPickupAvailabilityRequest(String userId) {
        this.userId = userId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    @XmlAttribute(name = "USERID")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirmName() {
        return FirmName;
    }

    @XmlElement(name = "FirmName")
    public CarrierPickupAvailabilityRequest setFirmName(String firmName) {
        FirmName = firmName;
        return this;
    }

    public String getSuiteOrApt() {
        return SuiteOrApt;
    }

    @XmlElement(name = "SuiteOrApt")
    public CarrierPickupAvailabilityRequest setSuiteOrApt(String suiteOrApt) {
        SuiteOrApt = suiteOrApt;
        return this;
    }

    public String getAddress2() {
        return Address2;
    }

    @XmlElement(name = "Address2")
    public CarrierPickupAvailabilityRequest setAddress2(String address2) {
        Address2 = address2;
        return this;
    }

    public String getUrbanization() {
        return Urbanization;
    }

    @XmlElement(name = "Urbanization")
    public CarrierPickupAvailabilityRequest setUrbanization(String urbanization) {
        Urbanization = urbanization;
        return this;
    }

    public String getCity() {
        return City;
    }

    @XmlElement(name = "City")
    public CarrierPickupAvailabilityRequest setCity(String city) {
        City = city;
        return this;
    }

    public String getState() {
        return State;
    }

    @XmlElement(name = "State")
    public CarrierPickupAvailabilityRequest setState(String state) {
        State = state;
        return this;
    }

    public String getZIP5() {
        return ZIP5;
    }

    @XmlElement(name = "ZIP5")
    public CarrierPickupAvailabilityRequest setZIP5(String ZIP5) {
        this.ZIP5 = ZIP5;
        return this;
    }

    public String getZIP4() {
        return ZIP4;
    }

    @XmlElement(name = "ZIP4")
    public CarrierPickupAvailabilityRequest setZIP4(String ZIP4) {
        this.ZIP4 = ZIP4;
        return this;
    }

    public String toXML() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CarrierPickupAvailabilityRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(this, sw);
            String xmlString = sw.toString();
            return xmlString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void printXML() {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(this.getClass());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);
            System.out.println(stringWriter.toString());
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String toFormatedXML() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CarrierPickupAvailabilityRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(this, sw);
            String xmlString = sw.toString();
            return xmlString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
