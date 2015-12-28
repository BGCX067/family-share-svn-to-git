package com.familyshare.pre.match;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.familyshare.pre.utils.ProgressGetter;
import com.familyshare.pre.utils.ProgressThread;

/**
 * 匹配相关类
 * 
 * @author 34262_000
 * 
 */
public class MyMatch {

	/**
	 * 开始匹配并发送未匹配数据与相关信息
	 * 
	 * @param hs
	 *            哈希表
	 * @param inputStream
	 *            完整文件输入流
	 * @param outputStream
	 *            未匹配结果输出流
	 */
	public static void startMatch(MyHash hs, InputStream inputStream,
			OutputStream outputStream) {
		BufferedInputStream bf2 = new BufferedInputStream(inputStream);
		DataInputStream bf = new DataInputStream(bf2);
		byte[] buffer = new byte[1024];

		int amount = 0;// 缓冲区实际数量
		int matchnum = 0;// 匹配数量
		int totalBufferNum = 0;// 总缓冲块数量
		int matchbyte = 0;
		int unmatchbyte = 0;
		int totalbyte = 0;
		try {
			// 进度条初始化
			ProgressThread.clear();
			ProgressThread.setTotalNum(bf.available());
			totalbyte = bf.available();
			totalBufferNum = totalbyte / 1024;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int writedata = 0;
		try {
			ProgressThread.setProgressState(ProgressGetter.running);
			MySum m;
			byte bufferSend[] = new byte[137];
			boolean newBufferSend = true;
			boolean lastmatchFlag = false;
			int bufferDataNum = 0;
			int bufferDataOff = 9;
			int lastMatchIndex = 0;
			int testnumber = 0;
			// 开始读取文件循环
			while ((amount = bf.read(buffer)) != -1) {

				ProgressThread.addProgressNum(amount);

				m = getCheckSum(buffer, amount);

				while (!hs.checkExists(m)) {
					// System.out.println(bf.available());
					unmatchbyte++;
					if (lastmatchFlag == true) {

						// writeIndexFlagTrue
						bufferSend[0] = 1;

						// writeLastMatchIndex
						bufferSend[1] = (byte) (lastMatchIndex & 0xff);
						bufferSend[2] = (byte) ((lastMatchIndex & 0xff00) >> 8);
						bufferSend[3] = (byte) ((lastMatchIndex & 0xff0000) >> 16);
						bufferSend[4] = (byte) ((lastMatchIndex & 0xff000000) >> 24);

					}
					if (newBufferSend) {

						// writeIndexFlagFalse
						bufferSend[0] = 0;
						newBufferSend = false;

					}

					// addUnMatchByte
					bufferSend[bufferDataOff + bufferDataNum] = buffer[0];
					bufferDataNum++;
					if (bufferDataNum == 128) {

						// writeDataLength
						bufferSend[5] = (byte) (0xff & bufferDataNum);
						bufferSend[6] = (byte) ((0xff00 & bufferDataNum) >> 8);
						bufferSend[7] = (byte) ((0xff0000 & bufferDataNum) >> 16);
						bufferSend[8] = (byte) ((0xff000000 & bufferDataNum) >> 24);
						writedata += bufferDataNum;
						// sendBuffer
						testnumber++;
//						if (testnumber == 109) {
//							int i;
//							i = 10;
//						}
						outputStream.write(bufferSend);
						bufferDataNum = 0;
						newBufferSend = true;
					}

					// int i = 0;
					// for (; i < amount - 1; i++) {
					// buffer[i] = buffer[i + 1];
					// }
					byte lastbyte = buffer[0];
					System.arraycopy(buffer, 1, buffer, 0, amount - 1);

					// readNextByte
					byte nextbyte = bf.readByte();
					buffer[amount - 1] = nextbyte;
					// computeCheckSum
					// m = getCheckSum(buffer, amount);
					byte[] md4 = Md4Checking.mdfour(buffer);
					long roll2 = SumChecking.checkSum_Adler32(buffer, amount);
					long roll = SumChecking.adler32_rolling_checksum(
							m.getRollCheckSum(), amount, lastbyte, nextbyte);
//					if (roll2 != roll) {
//						int i = 3;
//						i++;
//					}
					// System.out.println("sum1:" + roll);
					// System.out.println("sum2:" +
					// Md4Checking.toHexString(md4));
					m = new MySum(roll, md4);
					ProgressThread.addProgressNum(1);
					lastmatchFlag = false;
				}// 未匹配内循环结束

				if (lastmatchFlag == false) {

					// writeDataLength
					bufferSend[5] = (byte) (0xff & bufferDataNum);
					bufferSend[6] = (byte) ((0xff00 & bufferDataNum) >> 8);
					bufferSend[7] = (byte) ((0xff0000 & bufferDataNum) >> 16);
					bufferSend[8] = (byte) ((0xff000000 & bufferDataNum) >> 24);
					writedata += bufferDataNum;

					// sendBuffer
					testnumber++;
//					if (testnumber == 588) {
//						int i;
//						i = 10;
//					}
					outputStream.write(bufferSend);
					newBufferSend = true;
					bufferDataNum = 0;
				}

				// getLastMatchIndex
				lastMatchIndex = m.getBuffer_index();
				lastmatchFlag = true;
				matchnum++;
				matchbyte += amount;
			}
			ProgressThread.setProgressState(ProgressGetter.finished);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("matchedNum:" + (matchnum) + ";totalNum:"
				+ totalBufferNum);
		System.out.println("matchedbyte:" + (matchbyte) + ";unmatchedbyte:"
				+ unmatchbyte + ";totalbyte:" + totalbyte);
		System.out.println("writedata:" + (writedata));
		// System.out.println("hhh");
	}

	/**
	 * 根据数据块和实际读取长度获取校验码
	 * 
	 * @param buffer
	 *            数据块
	 * @param amount
	 *            实际数据长度
	 * @return
	 */
	public static MySum getCheckSum(byte[] buffer, int amount) {
		byte[] md4 = Md4Checking.mdfour(buffer);
		long roll = SumChecking.checkSum_Adler32(buffer, amount);
		// System.out.println("sum1:" + roll);
		// System.out.println("sum2:" + Md4Checking.toHexString(md4));
		return new MySum(roll, md4);
	}
}
