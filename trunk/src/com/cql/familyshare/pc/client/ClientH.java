/**
 * 
 */
package com.cql.familyshare.pc.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.cql.familyshare.pc.server.ServerCheckSumCreater;
import com.cql.familyshare.pc.server.ServerWithTotal;

/**
 * @author 蔡庆亮
 * 
 */
public class ClientH implements ClientWithHalf {

	private static String FILEHALF = null;

	/**
	 * 客户端主函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private InputStream inStreamHalf;
	private InputStream inStreamClient;
	private OutputStream outStreamClient;
	public void setInStreamClient(InputStream inStreamClient) {
		this.inStreamClient = inStreamClient;
	}

	public void setOutStreamClient(OutputStream outStreamClient) {
		this.outStreamClient = outStreamClient;
	}

	private static OutputStream outFileRebuid;

	private Socket socket;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.client.ClientWithHalf#checkFileEqual()
	 */
	@Override
	public boolean checkFileEqual() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.client.ClientWithHalf#clearTempData()
	 */
	@Override
	public void clearTempData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (inStreamClient != null) {
			try {
				inStreamClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (outStreamClient != null) {
			try {
				outStreamClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (inStreamHalf != null) {
			try {
				inStreamHalf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (outFileRebuid != null) {
			try {
				outFileRebuid.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cql.familyshare.client.ClientWithHalf#connect(com.cql.familyshare
	 * .server.ServerWithTotal)
	 */
	@Override
	public void connect(ServerWithTotal serverWithTotal) {
		// TODO Auto-generated method stub
		// this.inStreamClient = serverWithTotal.getOutStreamServer();
		// this.outStreamClient = serverWithTotal.getInStreamServer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.client.ClientWithHalf#connect()
	 */
	@Override
	public boolean connect(String ip, int port, int timeout) {
		// TODO Auto-generated method stub
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
	public OutputStream getInStreamClient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getOutStreamClient() {
		// TODO Auto-generated method stub
		return null;
	}

	public Socket getSocket() {
		return socket;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.client.ClientWithHalf#rebuidFile()
	 */
	@Override
	public void rebuidFile() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
////		byte b[]=new byte[256];
////		
////		inStreamClient.read(b);
//		System.out.println(Arrays.toString(b));
		rebuidFile(inStreamClient, FILEHALF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cql.familyshare.client.ClientWithHalf#rebuidFile(java.io.InputStream,
	 * java.io.OutputStream)
	 */
	@Override
	public void rebuidFile(InputStream inStreamBackData, String filehalf)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ClientFileRebuider fileRebuider = new ClientRebuidFile();
		fileRebuider.rebuidFile(filehalf, inStreamBackData, outFileRebuid);
		outFileRebuid.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.client.ClientWithHalf#sendCheckSumFile()
	 */
	@Override
	public void sendCheckSumFile() throws IOException {
		// TODO Auto-generated method stub
		sendCheckSumFile(outStreamClient);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.client.ClientWithHalf#sendCheckSumFile(java.io.
	 * OutputStream)
	 */
	@Override
	public void sendCheckSumFile(OutputStream outStreamFileCheckSum)
			throws IOException {
		// TODO Auto-generated method stub
		ServerCheckSumCreater checkSumCreater = new ClientCreateCheckSum();
		checkSumCreater.writeCheckSumFile(inStreamHalf, outStreamFileCheckSum);
		inStreamHalf.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cql.familyshare.client.ClientWithHalf#setHalfFile(java.io.InputStream
	 * )
	 */
	@Override
	public void setHalfFile(InputStream inStreamHalf) {
		// TODO Auto-generated method stub
		this.inStreamHalf = inStreamHalf;
	}

	@Override
	public void setHalfFile(String FileHalf) {
		// TODO Auto-generated method stub
		ClientH.FILEHALF = FileHalf;
		try {
			setHalfFile(new FileInputStream(FileHalf));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setRebuidFile(String FileRebuild) {
		// TODO Auto-generated method stub
		try {
			outFileRebuid = new FileOutputStream(FileRebuild);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
