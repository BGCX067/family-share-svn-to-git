/**
 * 
 */
package com.cql.familyshare.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.cql.familyshare.client.ClientWithHalf;

/**
 * @author 蔡庆亮
 * 
 */
public class ServerT implements ServerWithTotal {

	private static final int BUFERSIZEBACKDATA = 512;
	private static String FILETOTAL = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	private InputStream inStreamServer;
	private OutputStream outStreamServer;
	// private OutputStream outStreamMatchIndexs;
	public static final String FILEBACKDATA = "./filebackdata";

	private Socket socket;

	private ServerSocket serverSocket;
	private boolean inStreamOpen;
	private boolean outStreamOpen;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.server.ServerWithTotal#clearTempData()
	 */
	@Override
	public boolean clearTempData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			if (socket != null && !socket.isClosed())
				socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (inStreamServer != null)
				inStreamServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (outStreamServer != null)
				outStreamServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void closeInStream() {
		// TODO Auto-generated method stub
		try {
			if (inStreamServer != null)
				inStreamServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inStreamOpen = false;
	}

	@Override
	public void closeOutStream() {
		// TODO Auto-generated method stub
		try {
			if (outStreamServer != null)
				outStreamServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outStreamOpen = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.server.ServerWithTotal#connect()
	 */
	@Override
	public boolean connect() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cql.familyshare.server.ServerWithTotal#connect(com.cql.familyshare
	 * .client.ClientWithHalf)
	 */
	@Override
	public void connect(ClientWithHalf clientWithHalf) {
		// TODO Auto-generated method stub
		this.inStreamServer = clientWithHalf.getOutStreamClient();
		this.outStreamServer = clientWithHalf.getInStreamClient();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.server.ServerWithTotal#getConfirm()
	 */
	@Override
	public void getConfirm() {
		// TODO Auto-generated method stub
		getConfirm(inStreamServer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cql.familyshare.server.ServerWithTotal#getConfirm(java.io.InputStream
	 * )
	 */
	@Override
	public boolean getConfirm(InputStream inStreamConfirm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InputStream getInStreamServer() {
		// TODO Auto-generated method stub
		if (!inStreamOpen) {
			try {
				inStreamServer = socket.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return inStreamServer;
	}

	@Override
	public OutputStream getOutStreamServer() {
		// TODO Auto-generated method stub
		return outStreamServer;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public boolean listenToConnected(int port) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.server.ServerWithTotal#receieveCheckSumFile()
	 */
	@Override
	public void receieveCheckSumFile() {
		// TODO Auto-generated method stub
		receieveCheckSumFile(inStreamServer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cql.familyshare.server.ServerWithTotal#receieveCheckSumFile(java.
	 * io.InputStream)
	 */
	@Override
	public void receieveCheckSumFile(InputStream inStreamFileCheckSum) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.server.ServerWithTotal#sendBackData()
	 */
	@Override
	public void sendBackData() {
		// TODO Auto-generated method stub
		sendBackData(outStreamServer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cql.familyshare.server.ServerWithTotal#sendBackData(java.io.OutputStream
	 * )
	 */
	@Override
	public void sendBackData(OutputStream bufferedOutputStream) {
		// TODO Auto-generated method stub
		BufferedInputStream bufferedInputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(
					FILEBACKDATA));
//			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
//					outStreamBackData);
			int amount;
			byte bufferBackData[] = new byte[BUFERSIZEBACKDATA];
			while ((amount = bufferedInputStream.read(bufferBackData)) != -1) {
				bufferedOutputStream.write(bufferBackData, 0, amount);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setTotalFile(InputStream inSteamFileTotal) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cql.familyshare.server.ServerWithTotal#setTotalFile(java.lang.String)
	 */
	@Override
	public void setTotalFile(String fileTotal) throws FileNotFoundException {
		// TODO Auto-generated method stub
		FILETOTAL = fileTotal;
		setTotalFile(new FileInputStream(fileTotal));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.server.ServerWithTotal#startMatch()
	 */
	@Override
	public void startMatch() {
		// TODO Auto-generated method stub
		try {
			startMatch(inStreamServer, new FileOutputStream(FILEBACKDATA),
					outStreamServer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cql.familyshare.server.ServerWithTotal#startMatch(java.io.InputStream
	 * , java.io.OutputStream, java.io.OutputStream)
	 */
	@Override
	public void startMatch(InputStream inStreamFileCheckSum,
			OutputStream outStreamUnMatchData, OutputStream outStreamMatchIndexs) {
		// TODO Auto-generated method stub
		MatchStarter matchStarter = new TestMatch();
		try {
			matchStarter.startMatch(FILETOTAL, inStreamFileCheckSum,
					outStreamUnMatchData, outStreamMatchIndexs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outStreamUnMatchData.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
