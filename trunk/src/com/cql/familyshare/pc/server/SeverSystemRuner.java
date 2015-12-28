/**
 * 
 */
package com.cql.familyshare.pc.server;

import java.io.IOException;
import java.io.PrintStream;


/**
 * This class is used for : Server's main entrance To analyse the difference and send unmatched data
 * 
 * @author cql
 *
 * date:2013年8月22日
 */
public class SeverSystemRuner {

	public static final int serverPort = 10009;
//	public static final String FILETOTAL = Messages.getString("SeverSystemRuner.FILETOTAL"); //$NON-NLS-1$
	private PrintStream log = System.out;

	public void runServer(ServerWithTotal serverWithTotal) {
		log.println("startingServer."); //$NON-NLS-1$
//		try {
//			serverWithTotal.setTotalFile(FILETOTAL);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return;
//		}
//		log.println("port:" + serverPort + "startListening..."); //$NON-NLS-1$ //$NON-NLS-2$
//		if (serverWithTotal.listenToConnected(serverPort)) {
//			log.println("a client has Connected"); //$NON-NLS-1$
			log.println("start Matching..."); //$NON-NLS-1$
			serverWithTotal.startMatch();
			try {
				log.println();
				new ServerMatch().readUnmatchFile(ServerT.FILEBACKDATA);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.println("start sendBackData..."); //$NON-NLS-1$
			serverWithTotal.sendBackData();
			log.println("start getConfirm..."); //$NON-NLS-1$
			serverWithTotal.getConfirm();
			serverWithTotal.clearTempData();
//		} else {
//			log.println("connected fail"); //$NON-NLS-1$
//			serverWithTotal.close();
//			return;
//		}
		serverWithTotal.close();

	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		TestCreateTotal.readTotal(SeverSystemRuner.FILETOTAL);
		// TestCreateTotal.readTotal(SeverSystemRuner.fileTotal);
		new SeverSystemRuner().runServer(new ServerT());
	}

}
