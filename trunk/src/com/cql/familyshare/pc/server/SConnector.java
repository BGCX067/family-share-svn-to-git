package com.cql.familyshare.pc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SConnector implements ServerConnector {

	private ServerSocket serverSocket;
	private Socket socket;
	private OutputStream outStreamServer;
	private InputStream inStreamServer;

	@Override
	public boolean listenAt(int port) {
		// TODO Auto-generated method stub
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			outStreamServer = socket.getOutputStream();
			inStreamServer = socket.getInputStream();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
		}
	}

	@Override
	public InputStream getInStream() {
		// TODO Auto-generated method stub
		return inStreamServer;
	}

	@Override
	public OutputStream getOutStream() {
		// TODO Auto-generated method stub
		return outStreamServer;
	}

}
