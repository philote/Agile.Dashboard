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
package com.josephhopson.agiledashboard.fragments;

import java.lang.ref.WeakReference;

import com.josephhopson.agiledashboard.interfaces.OnAuthTokenReceivedListener;
import com.josephhopson.pivotal.tracker.dashboard.R;
import com.josephhopson.agiledashboard.service.authentication.*;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * LoginDialogFragment.java
 * Purpose: 
 * 
 * @author "Joseph T. Hopson"
 * @version 1.0 Oct 18, 2012
 */
public class SignInDialogFragment extends DialogFragment implements OnClickListener  {
	
	private static final String DIALOG_TITLE = "Sign In";
	
	private EditText et_Username;
	private EditText et_Password;
	private Button btn_StartTracking;
	private Button btn_RecoverPassword;
	
	OnAuthTokenReceivedListener mOnAuthTokenReceivedListener;
	
	// Empty constructor required for DialogFragment
	public SignInDialogFragment() {}
	
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	        	mOnAuthTokenReceivedListener = (OnAuthTokenReceivedListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString() 
	            		+ " must implement Listeners");
	        }
	 }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.sign_in_dialog_fragment, container, false);
		
		et_Username = (EditText) mView.findViewById(R.id.et_login_username);
		et_Password = (EditText) mView.findViewById(R.id.et_login_password);
		
		btn_StartTracking = (Button) mView.findViewById(R.id.btn_login_starttracking);
		btn_StartTracking.setOnClickListener(this);
		
		btn_RecoverPassword = (Button) mView.findViewById(R.id.btn_login_forgotpassword);
		btn_RecoverPassword.setOnClickListener(this);
		
		getDialog().setTitle(DIALOG_TITLE);
		
		return mView;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_login_starttracking:
				if(TextUtils.isEmpty(et_Username.getText().toString()) || TextUtils.isEmpty(et_Password.getText().toString())) {
					et_Username.setText("");
					et_Password.setText("");
					Toast.makeText(getActivity().getApplicationContext(), "The username or password were left blank.", Toast.LENGTH_SHORT).show();
				} else {
					/*
					 * TODO start the login process
					 * - overlay a waiting spinner
					 * - pass the username and password to the auth manager
					 * - wait for response
					 * - if success 
					 * 		- stop spinner 
					 * 		- tell calling activity to refresh
					 * 		- dismiss dialog
					 * 	 else
					 * 		- stop spinner
					 * 		- tell calling activity the error
					 * 		- dismiss dialog
					 * 		- calling activity displays error dialog
					 */
					AuthenticationHelper.getUserToken(getActivity().getApplicationContext(), 
							et_Username.getText().toString(), 
							et_Password.getText().toString(), 
							new AuthenticationHandler(this));
					et_Username.setText("");
					et_Password.setText("");
					// TODO set spinner
					Log.d(getClass().getName(), "Atempting to sign the user in.");
					Toast.makeText(getActivity().getApplicationContext(), "Atempting to sign you in.", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btn_login_forgotpassword:
				// TODO go to pivotal tracker password recovery page
				Log.d(getClass().getName(), "Forgot Password.");
				Toast.makeText(getActivity().getApplicationContext(), "Sending you to Pivotal Trackers web site.", Toast.LENGTH_SHORT).show();
				break;
		}
	}
	
	public void sendResponceToActivity(String error) {
		mOnAuthTokenReceivedListener.authTokenReceived(error);
		dismiss();
	}
	
	
	// ----------------------
 	// Authentication message handler
 	// ----------------------
 	
 	static class AuthenticationHandler extends Handler {
 	    private final WeakReference<SignInDialogFragment> mService; 
 	    
 	    AuthenticationHandler(SignInDialogFragment service) {
 	        mService = new WeakReference<SignInDialogFragment>(service);
 	    }
 	    
 	    @Override
 	    public void handleMessage(Message msg) {
 	    	SignInDialogFragment service = mService.get();
 	    	if (service != null) {
 	    		if(msg.arg1 == 1) {
 	    			service.sendResponceToActivity(null);
 	    			Log.d(getClass().getName(), "Token recieved");
 	    		} else {
 	    			service.sendResponceToActivity("Token not recieved");
 	    			Log.e(getClass().getName(),"Token not recieved");
 	    		}
 	    	}
 	    }
 	}
}
