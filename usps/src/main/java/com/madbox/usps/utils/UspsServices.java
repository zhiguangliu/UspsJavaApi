/*******************************************************
 * Copyright (C) 2012-2019 Adam Dale adamdale2018@gmail.com
 *
 * This file is part of Madbox Solutions.
 *
 * USPS Java Api can not be copied and/or distributed without the express
 * permission of Madbox Solutions.
 *******************************************************/
package com.madbox.usps.utils;

import cn.hutool.core.util.XmlUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.madbox.usps.Address;
import com.madbox.usps.ZipCode;
import com.madbox.usps.request.*;
import com.madbox.usps.response.*;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class UspsServices {

    Logger logger = LoggerFactory.getLogger(UspsServices.class);

    private ThreadLocal<URLConnection> connection = new ThreadLocal<>();
    private ThreadLocal<String> query = new ThreadLocal<>();
    private ThreadLocal<Error> errorType = new ThreadLocal<>();
    private ThreadLocal<StringBuilder> errorDetails = new ThreadLocal<>();

    private URL uspsValidateUrl;
    private URL uspsShipUrl;

    {
        try {
            uspsValidateUrl = new URL("http://production.shippingapis.com/ShippingApi.dll");
            uspsShipUrl = new URL("https://secure.shippingapis.com/ShippingApi.dll");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private final String charset = "UTF-8";

    private String userId;

    public String getUserId() {
        return userId;
    }

    public UspsServices setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public UspsServices() {
        //Default Constructor
    }

    public UspsServices(String userId) {
        this.userId = userId;
    }

    /**
     * Query to bind xml and request parms needed for usps.
     *
     * @param apiContext
     * @param xmlString
     * @return
     */
    public String bindQuery(String apiContext, String xmlString) {

        try {
            this.query.set(String.format("API=%s&XML=%s", URLEncoder.encode(apiContext, charset), URLEncoder.encode(xmlString, charset)));
        } catch (UnsupportedEncodingException e) {
            UspsException.processError(e);
        }
        return this.query.get();
    }

    /**
     * Configure connection
     *
     * @throws IOException
     */
    public void configureConnection() throws IOException {
        final URLConnection value = uspsValidateUrl.openConnection();
        value.setDoOutput(true);
        value.setRequestProperty("Accept-Charset", charset);
        value.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        connection.set(value);
    }

    public void configureConnection1() throws IOException {
        final URLConnection conn = uspsShipUrl.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept-Charset", charset);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        this.connection.set(conn);
    }

    /**
     * Execute command
     *
     * @param clazz
     * @return
     * @throws IOException
     * @throws Exception
     */
    public Object execute(Class<?> clazz) throws UspsRequestException {
        String results = "";
        try (final OutputStream outputStream = connection.get().getOutputStream()) {
            logger.debug("================== execute payload ====================");
            logger.debug(URLDecoder.decode(query.get(), "utf-8"));
            logger.debug("=======================================================");
            outputStream.write(query.get().getBytes(charset));
        } catch (IOException e) {
            throw new UspsRequestException(e);
        }

        try (
                final InputStream inputStream = connection.get().getInputStream();
                final InputStreamReader in = new InputStreamReader(inputStream);
                final BufferedReader reader = new BufferedReader(in)
        ) {
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            //Prints the string content read from input stream
            results = out.toString();
            logger.debug("================== execute result ====================");
            logger.debug(URLDecoder.decode(results, "UTF-8"));
            logger.debug("=======================================================");
        } catch (IOException e) {
            throw new UspsRequestException(e);
        }


        if (results.contains("Error") && !results.contains("<RateV4")) {
            final Document document = XmlUtil.parseXml(results);

            logger.debug("解析错误，返回结果：");
            logger.debug(StringEscapeUtils.unescapeHtml4(XmlUtil.toStr(document, true)));

            final Element errorElement = XmlUtil.getElementByXPath("//Error", document);
            JAXBContext jaxbContext = null;
            try {
                jaxbContext = JAXBContext.newInstance(Error.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                StringReader sr = new StringReader(XmlUtil.toStr(errorElement));
                Error unmarshal = (Error) unmarshaller.unmarshal(sr);
                unmarshal.setDescription(StringEscapeUtils.unescapeHtml4(unmarshal.getDescription()));
                errorType.set(unmarshal);

            } catch (JAXBException e) {
                throw new UspsRequestException(e);
            }

            final StringBuilder errorDetailsBuilder = new StringBuilder();
            errorDetailsBuilder.append("Error :" + errorType.get().getNumber());
            errorDetailsBuilder.append("\nMessage :" + errorType.get().getDescription());
            errorDetailsBuilder.append("\nUrl :" + connection.get().getURL());
            this.errorDetails.set(errorDetailsBuilder);
//
            logger.debug(this.errorDetails.toString());
            //Get whatever the object was to be and create it with the added error
            //this will prevent bubble up of error handling
            throw new UspsRequestException(errorType.get());
        } else {
            try {
                return toObject(results, clazz);
            } catch (JAXBException e) {
                throw new UspsRequestException(e);
            }
        }

    }

    public boolean validateInState(String zipCode) {
        boolean inState = false;
        CityStateLookupRequest request = new CityStateLookupRequest();
        request.setID(userId);
        ZipCode zip = new ZipCode();
        zip.setID("0");
        zip.setZip5(zipCode);
        request.setZipCode(zip);
        bindQuery("CityStateLookup", request.toXML());
        try {
            CityStateLookupResponse response = (CityStateLookupResponse) configureAndExecute(CityStateLookupResponse.class);
            String state = response.getZipCode().getState();
            if ("VA".equalsIgnoreCase(state)) {
                inState = true;
            }

        } catch (Exception e) {
            UspsException.processError(e);
        }
        return inState;
    }

    /**
     * Validate Address
     * Save time and money by reducing shipping errors due to incorrect address entry.
     * This tool corrects errors in street addresses, including abbreviations and missing information.
     * It also supplies a Zip 4
     *
     * @param address
     * @return
     * @throws Exception
     */
    public Address validatedAddress(Address address) throws UspsRequestException {
        Address results = null;
        AddressValidateRequest requestAdd = new AddressValidateRequest();
        requestAdd.setUserId(userId);
        requestAdd.setAddress(address);
        String xml = requestAdd.toXML();
        bindQuery("Verify", xml);
        AddressValidateResponse addResponse = (AddressValidateResponse) configureAndExecute(AddressValidateResponse.class);
        logger.debug("==============validatedAddress result==================");
        logger.debug(JSON.toJSONString(addResponse, SerializerFeature.PrettyFormat));
        logger.debug("=======================================================");
        if (addResponse != null) {
            results = addResponse.getAddress();
        }
        return results;
    }

    public Address zipCodeLookup(Address address) {
        Address results = null;
        ZipCodeLookupRequest requestAdd = new ZipCodeLookupRequest();
        requestAdd.setUserId(userId);
        requestAdd.setAddress(address);
        String xml = requestAdd.toXML();
        bindQuery("ZipCodeLookup", xml);
        try {
            ZipCodeLookupResponse addResponse = (ZipCodeLookupResponse) configureAndExecute(ZipCodeLookupResponse.class);
            logger.debug("==============zipCodeLookup result==================");
            logger.debug(JSON.toJSONString(addResponse, SerializerFeature.PrettyFormat));
            logger.debug("=====================================================");
            results = addResponse.getAddress();
        } catch (Exception e) {
            UspsException.processError(e);
        }
        return results;
    }

    public ZipCode cityStateLookup(ZipCode zipCode) {
        ZipCode results = null;
        CityStateLookupRequest request = new CityStateLookupRequest();
        request.setID(userId);
        request.setZipCode(zipCode);

        String xml = request.toXML();
        bindQuery("CityStateLookup", xml);
        try {
            CityStateLookupResponse addResponse = (CityStateLookupResponse) configureAndExecute(CityStateLookupResponse.class);
            logger.debug("==============CityStateLookup result==================");
            logger.debug(JSON.toJSONString(addResponse, SerializerFeature.PrettyFormat));
            logger.debug("=====================================================");
            results = addResponse.getZipCode();
        } catch (Exception e) {
            UspsException.processError(e);
        }
        return results;
    }

    /**
     * Calculate postage rates quickly and easily online for domestic and international shipping.
     * V4 is domestic and V2 for international
     * This request is for multiple packages
     *
     * @return
     */
    public PackageResponse requestShipping(ArrayList<PackageRequest> packageList) {
        PackageResponse result = null;
        RateV4Request rateRequest = new RateV4Request();
        rateRequest.setPackageObj(packageList);
        String xml = rateRequest.toXML();
        logger.debug("Multiple Packages\n");
        rateRequest.printXML();
        bindQuery("RateV4", xml);
        try {
            RateV4Response rateResponse = (RateV4Response) configureAndExecute(RateV4Response.class);
            if (rateResponse != null && errorType == null) {
                result = rateResponse.getPackageObj();
                logger.debug("Muiltipe Response");
                result.printXML();
            }
            if (rateResponse == null && errorType.get() != null) {
                result = new PackageResponse();
                result.appendError(errorType.get());
            }
        } catch (Exception e) {
            UspsException.processError(e);
        }

        return result;
    }

    /**
     * Calculate postage rates quickly and easily online for domestic and international shipping.
     * V4 is domestic and V2 for international
     * This request only does a single request package.
     *
     * @return
     */
    public PackageResponse requestShipping(PackageRequest packageDetails) {
        PackageResponse result = null;
        ArrayList<PackageRequest> packageRequestList = new ArrayList<PackageRequest>();
        packageRequestList.add(packageDetails);
        RateV4Request rateRequest = new RateV4Request();
        rateRequest.setPackageObj(packageRequestList);
        String xml = rateRequest.toXML();
        bindQuery("RateV4", xml);
        try {
            RateV4Response rateResponse = (RateV4Response) configureAndExecute(RateV4Response.class);
            if (rateResponse != null) {
                result = rateResponse.getPackageObj();
            }
        } catch (Exception e) {
            UspsException.processError(e);
        }

        return result;
    }

    /**
     * The Priority Mail Express Label API will let customers create Priority Mail Express Labels.
     * Please note that the API labels are printed without postage.
     * Postage must be purchased and applied separately.
     *
     * @param labelRequest
     * @return labelResponse
     */
    public ExpressMailLabelResponse requestMailLabel(ExpressMailLabelRequest labelRequest) {
        ExpressMailLabelResponse result = null;
        String xml = labelRequest.toXML();
        logger.debug(labelRequest.toFormatedXML());
        bindQuery("ExpressMailLabel", xml);
        try {
            ExpressMailLabelResponse labelResponse = (ExpressMailLabelResponse) configureAndExecute(ExpressMailLabelResponse.class);
            if (labelResponse != null) {
                result = labelResponse;
            }
        } catch (Exception e) {
            UspsException.processError(e);
        }
        return result;
    }

    /**
     * @param carrierPickupScheduleRequest
     * @return labelResponse
     */
    public void carrierPickupAvailabilityRequest(CarrierPickupAvailabilityRequest carrierPickupScheduleRequest) {
        ExpressMailLabelResponse result = null;
        carrierPickupScheduleRequest.setUserId(userId);
        String xml = carrierPickupScheduleRequest.toXML();
        logger.debug(carrierPickupScheduleRequest.toFormatedXML());
        bindQuery("CarrierPickupAvailability", xml);
        logger.debug(this.query.get());
        try {
            configureAndExecute1(ExpressMailLabelResponse.class);
//            ExpressMailLabelResponse labelResponse = (ExpressMailLabelResponse) configureAndExecute1(ExpressMailLabelResponse.class);
            logger.debug(connection.get().getURL().toString());
//            if (labelResponse != null) {
//                result = labelResponse;
//            }
        } catch (Exception e) {
            UspsException.processError(e);
        }
//        return result;
    }

    /**
     * @return labelResponse
     */
    public void carrierPickupScheduleRequest(CarrierPickupScheduleRequest carrierPickupScheduleRequest) {
        carrierPickupScheduleRequest.setUserId(userId);
        String xml = carrierPickupScheduleRequest.toXML();
        logger.debug(carrierPickupScheduleRequest.toFormatedXML());
        bindQuery("CarrierPickupSchedule", xml);
        logger.debug(this.query.get());
        try {
            configureAndExecute1(ExpressMailLabelResponse.class);
//            ExpressMailLabelResponse labelResponse = (ExpressMailLabelResponse) configureAndExecute1(ExpressMailLabelResponse.class);
            logger.debug(connection.get().getURL().toString());
//            if (labelResponse != null) {
//                result = labelResponse;
//            }
        } catch (Exception e) {
            UspsException.processError(e);
        }
//        return result;
    }

    /**
     * @return the connection
     */
    public URLConnection getConnection() {
        return connection.get();
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(URLConnection connection) {
        this.connection.set(connection);
    }

    /**
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query.get();
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query.set(query);
    }

    /**
     * @return the uspsUrl
     */
    public URL getUspsValidateUrl() {
        return uspsValidateUrl;
    }

    /**
     * @param uspsValidateUrl the uspsUrl to set
     */
    public void setUspsValidateUrl(URL uspsValidateUrl) {
        this.uspsValidateUrl = uspsValidateUrl;
    }

    public Object configureAndExecute(Class<?> clazz) throws UspsRequestException {
        try {
            configureConnection();
        } catch (IOException e) {
            throw new UspsRequestException(e);
        }
        return execute(clazz);
    }

    public Object configureAndExecute1(Class<?> clazz) throws Exception {
        configureConnection1();
        return execute(clazz);
    }

    @SuppressWarnings("rawtypes")
    public Object toObject(String results, Class clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader sr = new StringReader(results);
        return unmarshaller.unmarshal(sr);
    }

    /**
     * @return the errorType
     */
    public Error getErrorType() {
        return errorType.get();
    }

    /**
     * @param errorType the errorType to set
     */
    public void setErrorType(Error errorType) {
        this.errorType.set(errorType);
    }
}
