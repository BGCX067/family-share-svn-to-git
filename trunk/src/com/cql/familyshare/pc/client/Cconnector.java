package com.cql.familyshare.pc.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.cql.familyshare.pc.network.ClientConnector;

public class Cconnector implements ClientConnector {

	private Socket socket;
	private InputStream inStreamClient;
	private OutputStream outStreamClient;

	@Override
	public boolean connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			inStreamClient = socket.getInputStream();
			outStreamClient = socket.getOutputStream();
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public InputStream getInStream() {
		// TODO Auto-generated method stub
		return inStreamClient;
	}

	@Override
	public OutputStream getOutStream() {
		// TODO Auto-generated method stub
		return outStreamClient;
	}

}
