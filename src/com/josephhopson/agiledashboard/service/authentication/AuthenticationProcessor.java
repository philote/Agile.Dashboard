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
package com.josephhopson.agiledashboard.service.authentication;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.os.Messenger;
import android.util.Log;

/**
 * AuthenticationProcessor.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 23, 2012
 */
public class AuthenticationProcessor {
	
	private final Context mContext;

	public AuthenticationProcessor(Context context) {
		this.mContext = context;
	}
	
	public void processAuthResponce(String authXmlResponce, Messenger messenger) 
			throws ParserConfigurationException, SAXException, IOException {
		
		/* Get a SAXParser from the SAXPArserFactory. */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		
		/* Get the XMLReader of the SAXParser we created. */
        XMLReader xr = sp.getXMLReader();
        /* Create a new ContentHandler and apply it to the XML-Reader*/ 
        AuthenticationXMLHandler mAuthenticationXMLHandler = new AuthenticationXMLHandler(mContext, messenger);
        xr.setContentHandler(mAuthenticationXMLHandler);
        
        /* Parse the xml-data from our URL. */
        InputSource inputSource = new InputSource();
        inputSource.setEncoding("UTF-8");
        inputSource.setCharacterStream(new StringReader(authXmlResponce));
        
        /* Parse the xml-data from our URL. */
        xr.parse(inputSource);
        /* Parsing has finished. */
        
        Log.d(getClass().getName(), "Parsing done.");
	}

}
