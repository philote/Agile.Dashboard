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

public class Constants {

    public static enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    // (for Dev use only so they do not need to be translated)
    public static final String UNABLE_TO_AUTHENTICATE_MSG = "Unable to authenticate user.";

    // The timeout in milliseconds until a connection is established.
    public static final int CONNECTION_TIMEOUT = 30*1000; // 30 seconds
    // The socket timeout in milliseconds which
    // is the timeout for waiting for data.
    public static final int SOCKET_TIMEOUT = 30*1000;  // 30 seconds
}
