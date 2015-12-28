/**
 * 
 */
package com.cql.familyshare.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import com.familyshare.pre.test.TestCreateTotal;

/**
 * 服务器主函数
 * 
 * @author 蔡庆亮
 * 
 */
public class SeverSystemRuner {

	public static final int serverPort = 10009;
	public static final String FILETOTAL = Messages.getString("SeverSystemRuner.FILETOTAL"); //$NON-NLS-1$
	private PrintStream log = System.out;

	public void runServer(ServerWithTotal serverWithTotal) {
		log.println("startingServer."); //$NON-NLS-1$
		try {
			serverWithTotal.setTotalFile(FILETOTAL);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		log.println("port:" + serverPort + "startListening..."); //$NON-NLS-1$ //$NON-NLS-2$
		if (serverWithTotal.listenToConnected(serverPort)) {
			log.println("a client has Connected"); //$NON-NLS-1$
			log.println("start Matching..."); //$NON-NLS-1$
			serverWithTotal.startMatch();
			try {
				log.println();
				new TestMatch().readUnmatchFile(ServerT.FILEBACKDATA);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.println("start sendBackData..."); //$NON-NLS-1$
			serverWithTotal.sendBackData();
			log.println("start getConfirm..."); //$NON-NLS-1$
			serverWithTotal.getConfirm();
			serverWithTotal.clearTempData();
		} else {
			log.println("connected fail"); //$NON-NLS-1$
			serverWithTotal.close();
			return;
		}
		serverWithTotal.close();

	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TestCreateTotal.readTotal(SeverSystemRuner.FILETOTAL);
		// TestCreateTotal.readTotal(SeverSystemRuner.fileTotal);
		new SeverSystemRuner().runServer(new ServerT());
	}

}
