package com.familyshare.pre;

/*文件发送端*/
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.Scanner;

/**
 * 在服务器端开启的情况下 实例化套接字 并发送文件
 * 
 * @author
 */
class MyClient extends Thread {

	// 远程的IP字符串
	String remoteIPString = null;
	// 远程的服务端口
	int port;
	// 临时套接字
	Socket tempSocket;
	// 发送文件用的输出流
	OutputStream outSocket;
	// 欲发送的文件
	RandomAccessFile outFile;

	PrintWriter myPrintWriter = null;

	File file;
	// 发送文件用的临时缓存区
	byte byteBuffer[] = new byte[1024];
	private InputStream inSocket;
	boolean overFlag = false;

	/**
	 * 构造方法仅用于选择发送文件的位置 并从外部接收远程地址和端口
	 * 
	 */
	public MyClient(String remoteIPString, int port) {
		try {
			this.remoteIPString = remoteIPString;
			this.port = port;

		} catch (Exception e) {
		}
	}
	
	public MyClient(){
		
	}

	/**
	 * 先决条件是服务器端先开启
	 * 
	 */
	public void run() {
		try {
			this.tempSocket = new Socket(this.remoteIPString, this.port);
			MyMessage.addMessage("与服务器连接成功");
			System.out.println("与服务器连接成功!");
			inSocket = tempSocket.getInputStream();
			outSocket = tempSocket.getOutputStream();
			myPrintWriter = new PrintWriter(outSocket, true);
			Scanner sc = new Scanner(inSocket);
			while (!overFlag) {
				if (sc.hasNext()) {
					String message = sc.nextLine();
					MyMessage.addMessage(message);
					try {
						Robot rb = new Robot();
						if (message.equals("keyup")) {
							rb.keyPress(KeyEvent.VK_UP);
						} else if (message.equals("keypagedown")) {
							rb.keyPress(KeyEvent.VK_PAGE_DOWN);
						} else if (message.equals("keypageup")) {
							rb.keyPress(KeyEvent.VK_PAGE_UP);
						} else if (message.equals("keydown")) {
							rb.keyPress(KeyEvent.VK_DOWN);
						} else if (message.equals("keyesc")) {
							rb.keyPress(KeyEvent.VK_ESCAPE);
						}
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				sleep(10);
			}
			// inSocket = tempSocket.getInputStream();
			// BufferedReader bReader = new BufferedReader(new
			// InputStreamReader(
			// inSocket));
			//
			// int amount;
			// boolean overFlag = false;
			//
			// while (!overFlag) {
			// Scanner sc = new Scanner(System.in);
			// String myIput = sc.nextLine().toString();
			// int num = inSocket.available();
			//
			// outSocket = tempSocket.getOutputStream();
			// PrintWriter os = new PrintWriter(outSocket, true);
			// String messageServer = "";
			// if (num > 0) {
			// messageServer = bReader.readLine();
			// System.out.println(messageServer);
			// }
			// if (myIput.equals("quit")) {
			// overFlag = true;
			// } else if (myIput.equals("sendFile")) {
			// // 选择发送的文件位置
			// JFileChooser jfc = new JFileChooser(".");
			// file = null;
			// int returnVal = jfc
			// .showOpenDialog(new javax.swing.JFrame());
			// if (returnVal == JFileChooser.APPROVE_OPTION) {
			// file = jfc.getSelectedFile();
			// }
			// outFile = new RandomAccessFile(file, "r");
			//
			// if (messageServer.equals("Server:Continue?Y/N")) {
			// myIput = sc.nextLine().toString();
			// os.println(myIput);
			// if (myIput.equals("N")) {
			// overFlag = true;
			// break;
			// }
			// }
			//
			// if (!overFlag) {
			// System.out.println("开始发送文件...");
			// String name = file.getName();
			// os.println(name);
			// while ((amount = outFile.read(byteBuffer)) != -1) {
			// outSocket.write(byteBuffer, 0, amount);
			// System.out.println("文件发送中...");
			// }
			//
			// System.out.println("Send File complete");
			// javax.swing.JOptionPane.showMessageDialog(
			// new javax.swing.JFrame(), "已发送完毕", "提示!",
			// javax.swing.JOptionPane.PLAIN_MESSAGE);
			// }
			//
			// } else if (myIput.equals("")) {
			//
			// } else {
			//
			// }

			// outSocket.close();
			// inSocket.close();
			// outFile.close();
			// tempSocket.close();

		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean disConnect() {
		boolean flag = false;
		try {
			this.sendMessage("quit");
			if (this.tempSocket != null && !this.tempSocket.isClosed())
				this.tempSocket.close();
			if (inSocket != null) {
				inSocket.close();
			}
			if (outSocket != null) {
				outSocket.close();
			}
			flag = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public Socket getSocket() {
		return this.tempSocket;
	}

	public InputStream getInputStream() {
		try {
			return this.tempSocket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public OutputStream getOutputStream() {
//		try {
//			String pathname = "e:\\test\\2.rmvb";
//			File newFile = new File(pathname);
//			newFile.delete();
//			newFile.createNewFile();
//			FileOutputStream fileOutputStream = new FileOutputStream(newFile);
//			return fileOutputStream;
//			// return this.tempSocket.getOutputStream();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return this.outSocket;
	}

	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		myPrintWriter.println(message);
	}

	// /**
	// * 测试方法
	// *
	// * @param args
	// */
	// public static void main(String args[]) {
	// SendFile sf = new SendFile("10.30.3.5", 10008);
	// sf.start();
	// }
}