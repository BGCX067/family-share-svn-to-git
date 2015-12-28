package com.familyshare.pre.test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import com.familyshare.pre.match.Md4Checking;
import com.familyshare.pre.match.MyHash;
import com.familyshare.pre.match.MySum;
import com.familyshare.pre.match.SumChecking;
import com.familyshare.pre.utils.DataConvertUtils;
import com.familyshare.pre.utils.ProgressGetter;
import com.familyshare.pre.utils.ProgressThread;

/**
 * 文件传输，构造碎片文件，传输校验码文件相关
 * 
 * @author 34262_000
 * 
 */
public class FileTest {

	public static final String testdir = "E:\\test";
	/**
	 * 校验码文件不可修改
	 */
	public static final String sumfilepath = testdir + "\\sumtest.temp";
	/**
	 * 碎片文件不可修改
	 */
	public static final String clientfilepath = testdir + "\\clientfile.temp";
	// public static String totalfilepath=testdir + "\\Java网络socket编程详解.doc";
	/**
	 * 完整文件可修改
	 */
	public static String totalfilepath = testdir + "\\艾斯和路飞（原画）.f4v";

	// 文件传输选项
	public static final int halfFile = 0;
	public static final int totalFile = 1;
	public static final int checkSum = 3;
	/**
	 * 匹配返回文件
	 */
	public static final String backdatafile = testdir + "\\backdatafile";
	public static final String rebuidfile = testdir+"\\rebuildfile";

	/**
	 * 根据文件地址获取输出流
	 * 
	 * @param filepath
	 * @return
	 */
	public static OutputStream getOutputStream(String filepath) {
		File file = new File(filepath);
		// String p = file.getAbsolutePath();
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputStream;
	}

	/**
	 * 未用到
	 */
	public static void printCheckSumInFile() {
		File file = new File("filetest.txt");
		FileInputStream fileInputStream;
		DataInputStream bufferedInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			bufferedInputStream = new DataInputStream(fileInputStream);
			byte b[] = new byte[8];
			byte b2[] = new byte[16];
			do {
				b[0] = bufferedInputStream.readByte();
				b[1] = bufferedInputStream.readByte();
				b[2] = bufferedInputStream.readByte();
				b[3] = bufferedInputStream.readByte();
				b[4] = bufferedInputStream.readByte();
				b[5] = bufferedInputStream.readByte();
				b[6] = bufferedInputStream.readByte();
				b[7] = bufferedInputStream.readByte();

				b2[0] = bufferedInputStream.readByte();
				b2[1] = bufferedInputStream.readByte();
				b2[2] = bufferedInputStream.readByte();
				b2[3] = bufferedInputStream.readByte();
				b2[4] = bufferedInputStream.readByte();
				b2[5] = bufferedInputStream.readByte();
				b2[6] = bufferedInputStream.readByte();
				b2[7] = bufferedInputStream.readByte();
				b2[8] = bufferedInputStream.readByte();
				b2[9] = bufferedInputStream.readByte();
				b2[10] = bufferedInputStream.readByte();
				b2[11] = bufferedInputStream.readByte();
				b2[12] = bufferedInputStream.readByte();
				b2[13] = bufferedInputStream.readByte();
				b2[14] = bufferedInputStream.readByte();
				b2[15] = bufferedInputStream.readByte();

				String sum1 = String.valueOf(DataConvertUtils.bytes2long(b));
				String sum2 = Md4Checking.toHexString(b2);
				System.out.println("sum1:" + sum1);
				System.out.println("sum2:" + sum2);
			} while (bufferedInputStream.available() != 0);

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

	/**
	 * 未用到
	 */
	public static void buildHashTable() {
		Hashtable<Integer, MySum> hashtable = MyHash.hashTable;
		File file = new File("filetest.txt");
		FileInputStream fileInputStream;
		DataInputStream bufferedInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			bufferedInputStream = new DataInputStream(fileInputStream);
			byte b[] = new byte[8];
			byte b2[] = new byte[16];
			do {
				b[0] = bufferedInputStream.readByte();
				b[1] = bufferedInputStream.readByte();
				b[2] = bufferedInputStream.readByte();
				b[3] = bufferedInputStream.readByte();
				b[4] = bufferedInputStream.readByte();
				b[5] = bufferedInputStream.readByte();
				b[6] = bufferedInputStream.readByte();
				b[7] = bufferedInputStream.readByte();

				b2[0] = bufferedInputStream.readByte();
				b2[1] = bufferedInputStream.readByte();
				b2[2] = bufferedInputStream.readByte();
				b2[3] = bufferedInputStream.readByte();
				b2[4] = bufferedInputStream.readByte();
				b2[5] = bufferedInputStream.readByte();
				b2[6] = bufferedInputStream.readByte();
				b2[7] = bufferedInputStream.readByte();
				b2[8] = bufferedInputStream.readByte();
				b2[9] = bufferedInputStream.readByte();
				b2[10] = bufferedInputStream.readByte();
				b2[11] = bufferedInputStream.readByte();
				b2[12] = bufferedInputStream.readByte();
				b2[13] = bufferedInputStream.readByte();
				b2[14] = bufferedInputStream.readByte();
				b2[15] = bufferedInputStream.readByte();
				long sum1 = DataConvertUtils.bytes2long(b);
				MySum mySum = new MySum(sum1, b2);
				int index = MyHash.BIG_SUM2HASH(sum1);
				hashtable.put(index, mySum);
			} while (bufferedInputStream.available() != 0);
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

	/**
	 * 发送碎片文件
	 * 
	 * @param filepath
	 * @param outputStream
	 *            输出流
	 */
	public static void sendHalfFile(String filepath, OutputStream outputStream) {
		BufferedInputStream fileInputStream = null;
		try {
			fileInputStream = new BufferedInputStream(new FileInputStream(
					filepath));
			int totalNum = fileInputStream.available();
			ProgressThread.setTotalNum(totalNum);
			byte[] buffer = new byte[1024];
			int amount = 0;
			int readed = 0;
			ProgressThread.setProgressState(ProgressGetter.running);
			while ((amount = fileInputStream.read(buffer)) != -1) {
				if (readed >= (totalNum / 1.5)) {
					outputStream.write(buffer);
					ProgressThread.addProgressNum(amount);
				}
				readed += amount;
			}
			ProgressThread.setProgressState(ProgressGetter.finished);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ProgressThread.setProgressState(ProgressGetter.falsed);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ProgressThread.setProgressState(ProgressGetter.falsed);
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
				fileInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据选项发送文件
	 * 
	 * @param filepath
	 * @param outputStream
	 * @param option
	 *            选项
	 */
	public static void sendFile(String filepath, OutputStream outputStream,
			int option) {
		if (option == FileTest.halfFile) {
			FileTest.sendHalfFile(filepath, outputStream);
		} else {
			try {
				BufferedInputStream fileInputStream = new BufferedInputStream(
						new FileInputStream(filepath));
				int totalNum = fileInputStream.available();
				ProgressThread.setTotalNum(totalNum);
				byte[] buffer = new byte[1024];
				int amount = 0;
				int bufferindex = 0;
				ProgressThread.setProgressState(ProgressGetter.running);
				while ((amount = fileInputStream.read(buffer)) != -1) {
					switch (option) {
					case FileTest.totalFile:
						FileTest.sendTotalFile(buffer, outputStream);
						break;
					case FileTest.checkSum:
						FileTest.sendCheckSumToFile(buffer, outputStream,
								amount);
						break;
					}
					bufferindex++;
					ProgressThread.addProgressNum(amount);
				}
				ProgressThread.setProgressState(ProgressGetter.finished);
				fileInputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ProgressThread.setProgressState(ProgressGetter.falsed);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ProgressThread.setProgressState(ProgressGetter.falsed);
			} finally {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 发送完整文件
	 * 
	 * @param buffer
	 * @param outputStream
	 * @throws IOException
	 */
	private static void sendTotalFile(byte[] buffer, OutputStream outputStream)
			throws IOException {
		// TODO Auto-generated method stub
		outputStream.write(buffer);
	}

	@SuppressWarnings("unused")
	private static void sendCheckSumToFile(byte[] buffer,
			OutputStream outputStream, int amount) throws IOException {
		// TODO Auto-generated method
		long sum1 = SumChecking.checkSum_Adler32(buffer, amount);
		// System.out.println("sum1:" + sum1);
		byte sum2[] = Md4Checking.mdfour(buffer);
		// System.out.println("sum2:"+Md4Checking.toHexString(sum2));
		outputStream.write(DataConvertUtils.long2bytes(sum1));
		outputStream.write(sum2);
	}

	public static String getTotalfilepath() {
		return totalfilepath;
	}

	/**
	 * 设置完整文件路径
	 * 
	 * @param totalfilepath
	 */
	public static void setTotalfilepath(String totalfilepath) {
		FileTest.totalfilepath = totalfilepath;
	}

}
