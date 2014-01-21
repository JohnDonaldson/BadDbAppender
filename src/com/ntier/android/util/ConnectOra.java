package com.ntier.android.util;

import java.sql.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.annotation.TargetApi;
//import android.os.Build;
import com.ntier.util.Messages;
@TargetApi(19)
@NonNullByDefault
public class ConnectOra implements AutoCloseable { 
/*Connects to Oracle DB.
make sure on the Oracle DBserver-side that SQLNET.ora contains
	SQLNET.ALLOWED_LOGON_VERSION = 8
https://forums.oracle.com/message/3283284
*/		
	private static final Logger log = LoggerFactory.getLogger(AndroidConnectDB.class);

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConnectOra [");
		if (oraConn != null) builder.append("oraConn=").append(oraConn);
		builder.append("]");
		return builder.toString();
	}

	private Connection			oraConn;
	
	// "jdbc:oracle:thin:[user/password]@[host][:port]:SID"
	// eg. "jdbc:oracle:thin:@10.0.0.14:1521:KARMA1";
	
	//CONSTRUCTORS
	public ConnectOra() {
		this(Messages.getString("ConnectOra.URL"),
			 Messages.getString("ConnectOra.oraUser"),
			 Messages.getString("ConnectOra.oraPW"));
	}
	
	public ConnectOra(String URL) {
		this(URL,
			 Messages.getString("ConnectOra.oraUser"),
			 Messages.getString("ConnectOra.oraPW"));
	}
	
	public ConnectOra(String oraUser, String oraPW) {
		this(Messages.getString("ConnectOra.URL"),
			 oraUser,
			 oraPW);
	}

	public ConnectOra( final String URL, final String oraUser, final String oraPW) {
		log.trace("ConnectOra constructor");

		try {
			Driver driver = (Driver) Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			DriverManager.registerDriver((driver));
			this.oraConn = DriverManager.getConnection(URL, oraUser, oraPW);
		}
			catch( Exception e) { log.error("ConnectOra: BAD constructor", e); }
	}
	//end CONSTRUCTORS

	
	public boolean isValid() {
		final int timeoutSeconds = 1; 
		boolean result = false;
		try { result = oraConn.isValid(timeoutSeconds); }
			catch(SQLException e) {log.error(": isValid():", e); }	
		return result;
	}//isValid()
	
	//@Override
	public void close() {
		log.trace("ConnectOra.close");
		//DriverManager.deregisterDriver(driver);
		try {	
			if ( oraConn != null && !oraConn.isClosed() ) this.oraConn.close(); 
		}
			catch(SQLException e) {log.error(":ConnectOra.close():", e); }
	}//close()
	
}// ConnectOra
