/*
 * Copyright © 2012 Joseph Hopson
 * 
 * This file is part of Agile.Dashboard.
 * 
 * This software is dual-licensed:
 * Agile.Dashboard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Agile.Dashboard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Agile.Dashboard.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Alternatively, you can be released from the requirements of the license 
 * by purchasing a commercial license. Buying such a license is mandatory 
 * as soon as you develop commercial activities involving the Agile.Dashboard 
 * software without disclosing the source code of your own applications.
 * 
 * For more information, please contact Joseph Hopson. at the following
 * address: sales@josephhopson.com
 */
package com.josephhopson.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * RestClient.java
 * Purpose: An abstract rest client used to GET, POST, 
 * PUT or DELETE data to a REST based service.
 *
 * @author Joseph T. Hopson
 * @version 1.0 10/08/12
 */
public abstract class RestClient {
	private ArrayList <NameValuePair> params;
    private ArrayList <NameValuePair> headers;
    private String url;

    private int responseCode;
    private String message;
    private String response;

    private boolean authentication;
    protected String username;
    protected String password;

    /**
     * Class constructor that initiates some parameters
     * @param url the url string (example: http://appgateway.minick.net/int/visible/videos)
     */
    protected RestClient(String url) {
        authentication = false;
        this.url = url;
        params = new ArrayList<NameValuePair>();
        headers = new ArrayList<NameValuePair>();
    }

    /**
     * Adds a namevalue pair to the parameter var.
     * @param name name of the param
     * @param value value of the param
     */
    public void addParam(String name, String value) {
        params.add(new BasicNameValuePair(name, value));
    }

    /**
     * Adds a namevalue pair to the header var
     * @param name name of the header
     * @param value value of the header
     */
    public void addHeader(String name, String value) {
        headers.add(new BasicNameValuePair(name, value));
    }

    /**
     * Gets the response string returned by service
     * @return response string
     */
    public String getResponse() {
        if (response != null){
            return response;
        } else return "";
    }

    /**
     * Gets the Error message returned by the service
     * @return Error message String
     */
    public String getErrorMessage() {
        if (message != null){
            return message;
        } else return "";
    }

    /**
     * Gets the http response code returned by the service
     * @return integer representing the http response code
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Allows that calling class to add basic auth to the call.
     * NOTE: Be warned that this is sent in clear text, don't use basic auth unless you have to.
     * @param username the user name of the service user
     * @param password the password of the service user
     */
    public void addBasicAuthentication(String username, String password) {
        authentication = true;
        this.username = username;
        this.password = password;
    }

    /**
     * takes a Http request and adds headers and basic auth
     * @param request the Http request for which the app needs to add headers and or basic auth
     * @throws AuthenticationException 
     */
    private HttpUriRequest addHeaderParams(HttpUriRequest request) throws AuthenticationException {

        for (NameValuePair header : headers) {
            request.addHeader(header.getName(), header.getValue());
        }
        
        if (authentication) {
        	addAuthHeaderParams(request);
        }

        return request;
    }
    
    /**
     * Takes a request object and adds basic auth. This 
     * has been pulled out in to its own class and made 
     * protected so that it is overridable
     * @param request the request object being built
     * @throws AuthenticationException if building the auth headers fail
     */
    protected void addAuthHeaderParams(HttpUriRequest request) throws AuthenticationException {
    	UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
    	try {
    		request.addHeader(new BasicScheme().authenticate(credentials, request));
    	} catch (org.apache.http.auth.AuthenticationException e) {
    		throw new AuthenticationException(e.getMessage(), e.getCause());
    	}
    }

    /**
     * Formats all of the added parameters for a GET call
     * @return a url string representation of the parameters to be used for a GET call
     * @throws RestServiceException wraps the UnsupportedEncodingException
     */
    private String addGetParams() throws RestServiceException {
        StringBuilder combinedParams = new StringBuilder();
        if (!params.isEmpty()) {
            combinedParams.append("?");
            for (NameValuePair p : params) {
                try {
                    combinedParams.append((combinedParams.length() > 1 ? "&" : ""))
                            .append(p.getName())
                            .append("=")
                            .append(URLEncoder.encode(p.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RestServiceException(e.getMessage(), e.getCause());
                }
            }
        }
        return combinedParams.toString();
    }

    /**
     * Adds body parameters to a given POST or PUT request
     * @param request the POST or PUT that needs to have body params added
     * @return the original request with the body params added
     * @throws RestServiceException wraps the UnsupportedEncodingException
     */
    private HttpUriRequest addBodyParams(HttpUriRequest request) throws RestServiceException {
        // TODO: add body stuff here?
        if (!params.isEmpty()) {
            if (request instanceof HttpPost) {
                try {
                    ((HttpPost) request).setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    ((HttpPost) request).setHeader("Content-type", "application/x-www-form-urlencoded");
//                    ((HttpPost) request).setHeader("Content-Length", "0"); // causes 404
//                    ((HttpPost) request).setHeader("Accept", "*/*");
                } catch (UnsupportedEncodingException e) {
                	 throw new RestServiceException(e.getMessage(), e.getCause());
                }
            } else if (request instanceof HttpPut) {
                try {
                    ((HttpPut) request).setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                } catch (UnsupportedEncodingException e) {
                    throw new RestServiceException(e.getMessage(), e.getCause());
                }
            }
        }
        return request;
    }

    /**
     * Sets up either a POST, GET, POST and DELETE request to be executed by
     * the executeRequest function
     * @param method POST, GET, POST and DELETE using the RequestMethod Enum
     * @throws RestServiceException
     */
    public void execute(Constants.RequestMethod method) throws RestServiceException {
        switch(method) {
            case GET: {
                HttpGet request = new HttpGet(url + addGetParams());
                request = (HttpGet) addHeaderParams(request);
                executeRequest(request);
                break;
            }
            case POST: {
                HttpPost request = new HttpPost(url);
                request = (HttpPost) addHeaderParams(request);
                request = (HttpPost) addBodyParams(request);
                executeRequest(request);
                break;
            }
            case PUT: {
                HttpPut request = new HttpPut(url);
                request = (HttpPut) addHeaderParams(request);
                request = (HttpPut) addBodyParams(request);
                executeRequest(request);
                break;
            }
            case DELETE: {
                HttpDelete request = new HttpDelete(url);
                request = (HttpDelete) addHeaderParams(request);
                executeRequest(request);
            }
        }
    }

    /**
     * This function takes a prepared request object and run and executes it
     * @param request either a GET or a POST that  has all the headers and 
     * parameters attached.
     * @throws RestServiceException
     */
    private void executeRequest(HttpUriRequest request) throws RestServiceException {

        // Set Parameters
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, Constants.CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParameters, Constants.SOCKET_TIMEOUT);
        DefaultHttpClient restHTTPClient = new DefaultHttpClient(httpParameters);

        // Get the response
        HttpResponse httpResponse;
        
        try {
            httpResponse = restHTTPClient.execute(request);
            responseCode = httpResponse.getStatusLine().getStatusCode();
            message = httpResponse.getStatusLine().getReasonPhrase();
            
            // end result
            response = EntityUtils.toString(httpResponse.getEntity());

        } catch (Exception e) {
            throw new RestServiceException(e.getMessage(), e, responseCode);
        } finally {
            try {
                restHTTPClient.getConnectionManager().shutdown();
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
