/**
 * 
 */
package com.cql.familyshare;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.cql.familyshare.client.ClientWithHalf;
import com.cql.familyshare.server.ServerWithTotal;

/**
 * @author 蔡庆亮
 * 
 */
public interface SystemStarter {

	void setClient(ClientWithHalf clientWithHalf);

	void setServer(ServerWithTotal serverWithTotal);

	void runSystem() throws FileNotFoundException, IOException;

}
