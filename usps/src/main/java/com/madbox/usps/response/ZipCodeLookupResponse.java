/*******************************************************
 * Copyright (C) 2012-2019 Adam Dale adamdale2018@gmail.com
 * 
 * This file is part of Madbox Solutions.
 * 
 * USPS Java Api can not be copied and/or distributed without the express
 * permission of Madbox Solutions.
 *******************************************************/
package com.madbox.usps.response;

import com.madbox.usps.Address;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;

@XmlRootElement(name="ZipCodeLookupResponse")
public class ZipCodeLookupResponse {
	private Address address;
	public ZipCodeLookupResponse(){
		
	}
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	@XmlElement(name="Address")
	public void setAddress(Address address) {
		this.address = address;
	}
	public String toXML() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ZipCodeLookupResponse.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(this, sw);
			String xmlString = sw.toString();
			return xmlString;
		} catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	public String toFormatedXML() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ZipCodeLookupResponse.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(this, sw);
			String xmlString = sw.toString();
			return xmlString;
		} catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
}
