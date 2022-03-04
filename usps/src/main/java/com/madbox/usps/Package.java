/*******************************************************
 * Copyright (C) 2012-2019 Adam Dale adamdale2018@gmail.com
 * 
 * This file is part of Madbox Solutions.
 * 
 * USPS Java Api can not be copied and/or distributed without the express
 * permission of Madbox Solutions.
 *******************************************************/
package com.madbox.usps;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Package")
@XmlType(propOrder = {"serviceType", "count"})
public class Package {
	private String ServiceType;
	private String Count;

	public String getServiceType() {
		return ServiceType;
	}

	@XmlElement(name="ServiceType")
	public Package setServiceType(String serviceType) {
		ServiceType = serviceType;
		return this;
	}

	public String getCount() {
		return Count;
	}

	@XmlElement(name="Count")
	public Package setCount(String count) {
		Count = count;
		return this;
	}
}
