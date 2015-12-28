package com.familyshare.pre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

 /**
  * 暂时未用
 * @author 34262_000
 *
 */
class TalkClient {

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Socket socket;
//		try {
//			socket = new Socket("127.0.0.1", 4700);
//
//			BufferedReader sin = new BufferedReader(new InputStreamReader(
//					System.in));
//			PrintWriter os = new PrintWriter(socket.getOutputStream());
//			BufferedReader is = new BufferedReader(new InputStreamReader(
//					socket.getInputStream()));
//			String readline;
//			readline = sin.readLine();
//			while (!readline.equals("bye")) {
//				os.println(readline);
//				os.flush();
//				System.out.println("Client:" + readline);
//				System.out.println("Server:" + is.readLine());
//				readline = sin.readLine();
//			}
//			os.close();
//			is.close();
//			socket.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
