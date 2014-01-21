/*
 * 
 */
package com.ntier.android.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
//import android.util.Log;

import com.ntier.util.Messages;

@TargetApi(19)
public class AndroidConnectDB extends AsyncTask<Void, Void, Void> implements AutoCloseable {
//usage: connTask = new AndroidConnectDB().execute(MyMainActivity);
//	private static final Logger log = LoggerFactory.getLogger(AndroidConnectDB.class);
	private static Logger log ; 

	private static Activity activity; 
	protected static boolean isAndroidOnline() { 
	/*
	 * http://androidresearch.wordpress.com/2013/05/10/dealing-with-asynctask-and-screen-orientation/
	 * http://developer.android.com/training/basics/network-ops/managing.html
	 * http://stackoverflow.com/questions/7739624/android-connectivity-manager
	 */
		final ConnectivityManager connMgr = (ConnectivityManager) AndroidConnectDB.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			log.warn("NO default network!");
			return false;
		}
		log.trace("network detected");
		final boolean isConnected = networkInfo.isConnected();
		log.trace("network " + (isConnected ? "" : "  NOT!!  ") + "connected");
		return isConnected;
		
	}//isAndroidOnline()

	private ConnectOra conn;
	private final String URL;
	private final String oraPW ;
	private final String oraUser;
	
	
	public AndroidConnectDB (final Activity act) { 
		this(act, 
			 Messages.getString("ConnectOra.URL"),
			 Messages.getString("ConnectOra.oraUser"),
			 Messages.getString("ConnectOra.oraPW")
			);
	}//constructor

	public AndroidConnectDB (final Activity act, final String aURL, final String aoraUser, final String aoraPW) {
		log = LoggerFactory.getLogger(AndroidConnectDB.class);
		AndroidConnectDB.activity = act; 
		URL = aURL;
		oraUser = aoraUser;
		oraPW = aoraPW;
	}//constructor
	
	@Override public void close() { conn.close(); }
	
	@Override
	protected Void doInBackground(Void... params) {
		log.trace( "doInBackground()");
		
		if (isAndroidOnline()) conn = new ConnectOra(URL, oraUser, oraPW);
		//TODO try catch{cancel(true);}
		if (isCancelled()) this.close(); 
		return null;
	}// doInBackground()
	
	@Override protected void onCancelled(Void result) { 
		log.trace("Void result onCancelled");
		this.close(); 
		//super.onCancelled();
	}

	@Override protected void onCancelled() { 
		log.trace("0 param onCancelled");
		this.close(); 
//		super.onCancelled();
	}
	
	@Override
	protected void onPostExecute(Void result) {
		log.trace(":onPostExecute()!"); 
		super.onPostExecute(result);
	}// onPostExecute()
	
	
	
	@Override
	protected void onPreExecute() {
		log.trace(":onPreExecute()");
		super.onPreExecute();
	}// onPreExecute()
	
	
}// class AndroidConnectDB
