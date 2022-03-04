/*******************************************************
 * Copyright (C) 2012-2019 Adam Dale adamdale2018@gmail.com
 *
 * This file is part of Madbox Solutions.
 *
 * USPS Java Api can not be copied and/or distributed without the express
 * permission of Madbox Solutions.
 *******************************************************/
package com.madbox.usps.request;

import com.madbox.usps.Package;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.StringWriter;


@XmlRootElement(name = "CarrierPickupScheduleRequest")
@XmlType(propOrder = {"firstName",
        "lastName",
        "firmName",
        "suiteOrApt",
        "address2",
        "urbanization",
        "city",
        "state",
        "ZIP5",
        "ZIP4",
        "phone",
        "extension",
        "aPackage",
        "estimatedWeight",
        "packageLocation",
        "specialInstructions"})
public class CarrierPickupScheduleRequest {
    private String userId;

    private String firstName;
    private Package aPackage;

    private String LastName;
    private String FirmName;
    private String SuiteOrApt;
    private String Address2;
    private String Urbanization;
    private String City;
    private String State;
    private String ZIP5;
    private String ZIP4;
    private String Phone;
    private String Extension;
    private String EstimatedWeight;
    private String PackageLocation;
    private String SpecialInstructions;

    public CarrierPickupScheduleRequest() {

    }

    public CarrierPickupScheduleRequest(String userId) {
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

    public Package getaPackage() {
        return aPackage;
    }

    @XmlElement(name = "Package")
    public CarrierPickupScheduleRequest setaPackage(Package aPackage) {
        this.aPackage = aPackage;
        return this;
    }

    public String getLastName() {
        return LastName;
    }

    @XmlElement(name = "LastName")
    public CarrierPickupScheduleRequest setLastName(String lastName) {
        LastName = lastName;
        return this;
    }

    public String getFirmName() {
        return FirmName;
    }

    @XmlElement(name = "FirmName")
    public CarrierPickupScheduleRequest setFirmName(String firmName) {
        FirmName = firmName;
        return this;
    }

    public String getSuiteOrApt() {
        return SuiteOrApt;
    }

    @XmlElement(name = "SuiteOrApt")
    public CarrierPickupScheduleRequest setSuiteOrApt(String suiteOrApt) {
        SuiteOrApt = suiteOrApt;
        return this;
    }

    public String getAddress2() {
        return Address2;
    }

    @XmlElement(name = "Address2")
    public CarrierPickupScheduleRequest setAddress2(String address2) {
        Address2 = address2;
        return this;
    }

    public String getUrbanization() {
        return Urbanization;
    }

    @XmlElement(name = "Urbanization")
    public CarrierPickupScheduleRequest setUrbanization(String urbanization) {
        Urbanization = urbanization;
        return this;
    }

    public String getCity() {
        return City;
    }

    @XmlElement(name = "City")
    public CarrierPickupScheduleRequest setCity(String city) {
        City = city;
        return this;
    }

    public String getState() {
        return State;
    }

    @XmlElement(name = "State")
    public CarrierPickupScheduleRequest setState(String state) {
        State = state;
        return this;
    }

    public String getZIP5() {
        return ZIP5;
    }

    @XmlElement(name = "ZIP5")
    public CarrierPickupScheduleRequest setZIP5(String ZIP5) {
        this.ZIP5 = ZIP5;
        return this;
    }

    public String getZIP4() {
        return ZIP4;
    }

    @XmlElement(name = "ZIP4")
    public CarrierPickupScheduleRequest setZIP4(String ZIP4) {
        this.ZIP4 = ZIP4;
        return this;
    }

    public String getPhone() {
        return Phone;
    }

    @XmlElement(name = "Phone")
    public CarrierPickupScheduleRequest setPhone(String phone) {
        Phone = phone;
        return this;
    }

    public String getExtension() {
        return Extension;
    }

    @XmlElement(name = "Extension")
    public CarrierPickupScheduleRequest setExtension(String extension) {
        Extension = extension;
        return this;
    }

    public String getEstimatedWeight() {
        return EstimatedWeight;
    }

    @XmlElement(name = "EstimatedWeight")
    public CarrierPickupScheduleRequest setEstimatedWeight(String estimatedWeight) {
        EstimatedWeight = estimatedWeight;
        return this;
    }

    public String getPackageLocation() {
        return PackageLocation;
    }

    @XmlElement(name = "PackageLocation")
    public CarrierPickupScheduleRequest setPackageLocation(String packageLocation) {
        PackageLocation = packageLocation;
        return this;
    }

    public String getSpecialInstructions() {
        return SpecialInstructions;
    }

    @XmlElement(name = "SpecialInstructions")
    public CarrierPickupScheduleRequest setSpecialInstructions(String specialInstructions) {
        SpecialInstructions = specialInstructions;
        return this;
    }

    public String toXML() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CarrierPickupScheduleRequest.class);
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
            JAXBContext jaxbContext = JAXBContext.newInstance(CarrierPickupScheduleRequest.class);
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

    public String getFirstName() {
        return firstName;
    }

    @XmlElement(name = "FirstName")
    public CarrierPickupScheduleRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
}
