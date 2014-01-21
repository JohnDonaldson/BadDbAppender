package com.ntier.android.jdbctest;

//import java.io.File;

import android.app.Activity;
//import android.content.Context;
import android.os.Bundle;
import android.view.Menu;


import com.ntier.android.jdbctest.R;
import com.ntier.android.util.AndroidConnectDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;

import com.ntier.android.jdbctest.TestLogging;

import android.os.StrictMode;

public class MainActivity extends Activity {
	private static Logger log ;
	private AndroidConnectDB connTask; 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	final String APPHOME = getFilesDir().getPath() ;
	//sets APPHOME referenced in logback.xml
	System.setProperty("APPHOME", APPHOME);
	//Quiets the NetworkOnMainThreadException which is necessary for the Logback DB appender
 	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(). permitNetwork().build());
 	
	log = LoggerFactory.getLogger(MainActivity.class);

	//TestLogging myTestLog = new TestLogging();
	
	log.trace( "onCreate" );
	
	super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

	try {
		connTask = new AndroidConnectDB(this);
		connTask.execute();
		log.trace( connTask.toString() );


/*		ResultSet rs = connTask.getResult();

		ArrayList<String> list = new ArrayList<String>();
		while (rs.next()) {
			list.add(rs.getString(1));
		}
*/
		/*
		 * setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
		 * list));
		 * 
		 * ListView lv = getListView(); lv.setTextFilterEnabled(true);
		 */
	} catch (Exception e) {
		log.error("Ntier exception!:", e);
		}
    }//onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
	protected void onDestroy() { 
    	log.trace("onDestroy():connTask.getStatus(): " + connTask.getStatus() );
    	connTask.close();
    	//FLUSH LOGS 
    	( (LoggerContext) LoggerFactory.getILoggerFactory() ).stop();


    	super.onDestroy();
    }//onDestroy()
    
}//MainActivity

