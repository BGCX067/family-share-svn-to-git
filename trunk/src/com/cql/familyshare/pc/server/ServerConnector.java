package com.cql.familyshare.pc.server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ServerConnector {

	boolean listenAt(int port);

	InputStream getInStream();

	OutputStream getOutStream();

}
