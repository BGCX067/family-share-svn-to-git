/**
 * 
 */
package com.cql.familyshare.pc.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;



/**
 * This class is used for : Client's main entrance to send halffile's checksum to Sever and so on
 * 
 * @author cql
 *
 * date:2013年8月22日
 */
public class ClientSystemRunner {

//	private static final String FileHalf = Messages.getString("ClientSystemRunner.FILEHALF"); //$NON-NLS-1$
	public static final String FileRebuild = "filerebuild.txt"; //$NON-NLS-1$
	private PrintStream log = System.out;

	public void runClient(ClientWithHalf clientWithHalf) {
//		log.println("starting Client..."); //$NON-NLS-1$
//		clientWithHalf.setHalfFile(FileHalf);
//		clientWithHalf.setRebuidFile(FileRebuild);
//		log.println("start connecting..."); //$NON-NLS-1$
//		if (clientWithHalf.connect("127.0.0.1", 10009, 5000)) { //$NON-NLS-1$
//			log.println("connect to server successfully."); //$NON-NLS-1$
//		} else {
//			log.println("connect to server fail."); //$NON-NLS-1$
////			clientWithHalf.close();
//			return;
//		}

		try {
			log.println("start sendCheckFile..."); //$NON-NLS-1$
			clientWithHalf.sendCheckSumFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			clientWithHalf.close();
			e.printStackTrace();
			return;
		}
		try {
			log.println("start rebuildFile..."); //$NON-NLS-1$
			
			clientWithHalf.rebuidFile();
			
//			TestCreateTotal.readTotal(FileRebuild);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			clientWithHalf.close();
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			clientWithHalf.close();
			e.printStackTrace();
			return;
		}
		log.println("rebuildFile finish."); //$NON-NLS-1$
		clientWithHalf.checkFileEqual();
		clientWithHalf.clearTempData();
//		clientWithHalf.close();
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		TestCreateTotal.readTotal(ClientSystemRunner.FileHalf);
		new ClientSystemRunner().runClient(new ClientH());
	}

}
