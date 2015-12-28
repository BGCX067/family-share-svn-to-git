/**
 * 
 */
package com.cql.familyshare.pc.network;


/**
 * This class is used for : Switch Program To Server or Client
 * 
 * @author cql
 *
 * date:2013年8月22日
 */
public class ShareConnector {

	public static final int SERVER = 0;

	public static final int CLIENT = 1;
	private static int ServerType;

	public static int getServerType() {
		return ServerType;
	}

	public static void setServerType(int servertype) {
		// TODO Auto-generated method stub
		ShareConnector.ServerType = servertype;
	}

}
