/**
 * 
 */
package com.cql.familyshare.pc.network;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * This class is used for : Client connection
 * 
 * @author cql
 *
 * date:2013年8月22日
 */
public interface ClientConnector {

	boolean connect(String ip, int port);

	InputStream getInStream();

	OutputStream getOutStream();

}
