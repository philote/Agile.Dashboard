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

/**
 * RestServiceException.java
 * Purpose: RestServiceException is a wrapper exception  
 * designed to generalize low-level exceptions.
 *
 * @author Joseph T. Hopson
 * @version 1.0 10/08/12
 */
public class RestServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	private int statusCode = 0;
	
	public RestServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RestServiceException(String message) {
		super(message);
	}
	
	public RestServiceException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public RestServiceException(String message, Throwable cause, int statusCode) {
		super(message, cause);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
