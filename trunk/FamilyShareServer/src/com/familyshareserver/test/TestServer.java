package com.familyshareserver.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import android.widget.TextView;
import android.widget.Toast;

import util.MyLog;
import util.SDCardHelper;

public class TestServer extends Thread {

	// 服务套接字等待对方的连接和文件发送
	private static ServerSocket serSocket;
	// 由服务套接字产生的 套接字
	private static Socket tempSocket;
	// 用于读取
	private static InputStream inSocket = null;
	// 随机访问文件
	RandomAccessFile inFile = null;
	// 临时缓寸区
	byte byteBuffer[] = new byte[1024];
	// 默认用10000端口监听请求

	private static PrintWriter printWriter = null;

	Scanner myScanner = null;

	int defaultBindPort;
	// 初始的绑定端口次数为0
	private static int tryBindTimes = 0;
	// 当前绑定的端口号是10000默认端口
	private static int currentBindPort;
	private static OutputStream outSocket = null;
	private static boolean overFlag = false;

	/**
	 * @throws Exception
	 * @构造方法
	 * @抛出异常的原因是无法绑定服务的端口
	 * */
	public TestServer(int port, TextView textview) throws Exception {
		try {
			// 绑定服务的端口
			defaultBindPort = port;
			currentBindPort = defaultBindPort + tryBindTimes;
			this.bindToServerPort();
		} catch (Exception e) {
			e.printStackTrace();
			// 绑定不成功重试
			System.out.println(e.toString());
			MyLog.logErrot(e, "Bind Fail");
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @监控线程
	 */
	public void run() {
		printWriter = null;
		try {

			System.out.println("wait for..." + '\n' + "等待对方接入");
			// 等待对方的连接
			tempSocket = serSocket.accept();
			// 获取输入流
			System.out.println("有客户端接入");
			this.inSocket = tempSocket.getInputStream();
		
			this.outSocket = tempSocket.getOutputStream();
			myScanner = new Scanner(inSocket);
//			printWriter = new PrintWriter(outSocket, true);
//			printWriter.println("Hello,Client");
//			while (!overFlag) {
//				String messageClient = "";
//				if (myScanner.hasNext()) {
//					messageClient = myScanner.nextLine().trim();
//				}
//				if (messageClient != "") {
//					if (messageClient == "quit") {
//						this.disConnect();
//					} else {
//						printWriter.println("Client:" + messageClient);
//					}
//				}
//				sleep(10);
//			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			return;
		}

	}

	public static Socket getClientSocket() {
		return tempSocket;
	}

	/**
	 * @param pw
	 */
	private void recieveFile(PrintWriter pw) {
		// 以下为传送文件代码和 套接字清理工作
		// int amount;
		BufferedReader is = new BufferedReader(new InputStreamReader(inSocket));
		try {
			SDCardHelper sdh = new SDCardHelper();
			File savedFile = null;
			if (sdh.checkSDAccess()) {
				savedFile = new SDCardHelper().createFileRelatively("test", is
						.readLine().trim());
			}
			if (savedFile != null) {
				// 读取文件的数据，可以每次以快的方式读取数据
				inFile = new RandomAccessFile(savedFile, "rw");
			}
			int amount;
			while ((amount = inSocket.read(byteBuffer)) != -1) {
				inFile.write(byteBuffer, 0, amount);
			}
			if (!overFlag) {
				pw.println("Server:Continue?Y/N");
				try {
					String message = is.readLine().trim();
					if (message.equals("N")) {
						overFlag = true;
						pw.println("Server:quiting");
						// 关闭流
						this.inSocket.close();
						this.outSocket.close();
						// 关闭文件
						inFile.close();
						// 关闭临时套接字
						this.tempSocket.close();
						// 关闭服务方套接字
						// this.serSocket.close();
					} else {
						this.recieveFile(pw);
						// pw.println("Client:"+message);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			MyLog.logErrot(e, "filereceive");
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * @绑定端口
	 * @throws Exception
	 *             抛出异常的原因是无法绑定服务的端口
	 */
	private void bindToServerPort() throws Exception {
		try {
			// 输出绑定的端口号到当前的控制台上
			System.out.println("试绑定的端口号是:" + this.currentBindPort);
			// 在自己的机器上开一个服务类套接字并等待发送者的连接
			serSocket = new ServerSocket(this.currentBindPort);

		} catch (Exception e) {
			e.printStackTrace();
			// 绑定不成功重试
			System.out.println(e.toString());
			// 试了不止一次了
			this.tryBindTimes = this.tryBindTimes + 1;
			// 可查看试的次数getTryBindedTimes
			this.currentBindPort = this.defaultBindPort + this.tryBindTimes;

			// 如果试的次数超过20次 退出
			if (this.tryBindTimes >= 20) {
				throw new Exception("无法绑定到指定端口" + '\n' + "试了太多次了!");
			}
			// 递归的绑定
			this.bindToServerPort();
		}
		// 输出绑定的端口号到当前的控制台上
		System.out.println("成功绑定的端口号是: " + this.currentBindPort);

	}

	// 获取试绑定的端口
	public static int getTryBindedTimes() {
		return tryBindTimes;
	}

	// 获取已经绑定的端口
	public static int getCurrentBindingPort() {
		return currentBindPort;
	}

	public static boolean disConnect() {
		// TODO Auto-generated method stub
		boolean flag = false;

		if (tempSocket != null && !tempSocket.isClosed()) {
			try {
				inSocket.close();
				outSocket.close();
				tempSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
				return flag;
			}
		}
		if (inSocket != null) {
			try {
				inSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
				return flag;
			}
		}
		if (outSocket != null) {
			try {
				outSocket.close();
				flag = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
				return flag;
			}
		}

		return flag;
	}

	public static boolean sendMessage(String message) {
		if (printWriter != null) {
			printWriter.println(message);
		}
		return false;
		// TODO Auto-generated method stub
	}

	public static InputStream getInSocket() {
		return inSocket;
	}

}
