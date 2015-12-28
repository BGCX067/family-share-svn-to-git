/**
 * 
 */
package com.cql.familyshare.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import com.cql.familyshare.client.CheckSumCreater;
import com.familyshare.pre.match.SumChecking;
import com.familyshare.pre.test.TestCreateTotal;
import com.familyshare.pre.utils.DataConvertUtils;

/**
 * 测试用例，测试匹配并生成不匹配文件，及相应头信息 头信息包括：匹配数量和匹配序号 数据报 : 数据报 indexFlag index length
 * buffer 1 4 1 2 indexFlag=1代表有匹配序号列，且为数据类型报文
 * indexFlag=0代表数据类型报文，跟在前面报文后面，index无效 indexFlag=2代表数据类型报文已经结束
 * 
 * @author 蔡庆亮
 * 
 */
public class TestMatch implements MatchStarter {
	private PrintStream out = System.out;

	public void testMatch(String filetotal, String fileback,
			String filematchindexs) throws IOException {
		BufferedOutputStream outStream = new BufferedOutputStream(
				new FileOutputStream(fileback));
		DataOutputStream outStreamMatchIndexs = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(filematchindexs)));

		startMatch(filetotal,
				new FileInputStream(CheckSumCreater.FILECHECKSUM), outStream,
				outStreamMatchIndexs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.familyshare.pre.test.MatchStarter#startMatch(java.io.InputStream,
	 * java.io.OutputStream, java.io.OutputStream)
	 */
	@Override
	public void startMatch(String filetotal, InputStream inStreamCheckSum,
			OutputStream outStreamFIleBack, OutputStream outStreamMatchIndexs)
			throws IOException {
		BufferedInputStream inStreamFileTotal = new BufferedInputStream(
				new FileInputStream(filetotal));
//		BufferedOutputStream outStreamFIleBack = new BufferedOutputStream(
//				outStreamBack);
//		DataOutputStream outStreamMatchIndexs = new DataOutputStream(
//				new BufferedOutputStream(outStreamMatchOrder));
		DataInputStream bf = new DataInputStream(inStreamFileTotal);
		CheckSumHashCreater testCreateHash = new TestCreateHash();
		testCreateHash.buildHashTable(inStreamCheckSum);
		byte[] bufferWindow = new byte[CheckSumCreater.BLOCKSIZE];

		int amount = 0;// 缓冲区实际数量
		boolean newBufferSend = false;
		boolean lastmatchFlag = true;
		int bufferDataIndex = 0;
		long lastMatchIndex = 0;
		byte bufferSend[] = new byte[DATATOTALSIZE];
		int matchindexs[] = new int[inStreamFileTotal.available()
				/ CheckSumCreater.BLOCKSIZE + 1];
		int matchnum = 0;
		try {
			// 开始读取文件循环
			while ((amount = bf.read(bufferWindow)) != -1) {
				SumChecking.checkSum_Adler32(bufferWindow,
						CheckSumCreater.BLOCKSIZE);
				int matchflag = testCreateHash.checkMatch(bufferWindow,
						CheckSumCreater.BLOCKSIZE);
				if (matchflag != -1) {
					lastMatchIndex++;
					matchindexs[matchnum++] = matchflag;
				}

				while (matchflag == -1) {
					// writeLastMatchIndex
					if (lastmatchFlag == true) {
						lastmatchFlag = false;
						// writeIndexFlagTrue
						bufferSend[0] = 1;
						bufferSend[1] = (byte) (lastMatchIndex & 0xff);
						bufferSend[2] = (byte) ((lastMatchIndex & 0xff00) >> 8);
						bufferSend[3] = (byte) ((lastMatchIndex & 0xff0000) >> 16);
						bufferSend[4] = (byte) ((lastMatchIndex & 0xff000000) >> 24);
					} else if (newBufferSend) {
						// writeIndexFlagFalse
						bufferSend[0] = 0;
						newBufferSend = false;
					}

					// readNextByte
					byte nextbyte = bf.readByte();
					// addUnMatchByte
					bufferSend[DATAOFF + bufferDataIndex] = bufferWindow[0];
					bufferDataIndex++;
					if (bufferDataIndex == DATAACTUALSIZE) {

						// writeDataLength
						bufferSend[LENGTHOFF] = (byte) bufferDataIndex;
						// sendBuffer
						outStreamFIleBack.write(bufferSend);
						bufferDataIndex = 0;
						newBufferSend = true;
					}
					System.arraycopy(bufferWindow, 1, bufferWindow, 0,
							amount - 1);
					bufferWindow[amount - 1] = nextbyte;
					// computeCheckSum
					matchflag = testCreateHash.checkMatch(bufferWindow,
							CheckSumCreater.BLOCKSIZE);

					if (matchflag != -1) {
						lastMatchIndex++;
						matchindexs[matchnum++] = matchflag;
					}

				}// 未匹配内循环结束

				if (!lastmatchFlag && !newBufferSend) {
					// writeDataLength
					bufferSend[LENGTHOFF] = (byte) (bufferDataIndex);
					outStreamFIleBack.write(bufferSend);
					bufferDataIndex = 0;
					// getLastMatchIndex
				}
				newBufferSend = false;
				lastmatchFlag = true;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (bufferDataIndex > 0) {
				bufferSend[LENGTHOFF] = (byte) (bufferDataIndex);
				outStreamFIleBack.write(bufferSend);
				bufferDataIndex = 0;
				bufferSend[0] = 0;
			}

			for (int i = 0; i < amount; i++) {
				bufferSend[DATAOFF + bufferDataIndex] = bufferWindow[i];
				bufferDataIndex++;
				if (bufferDataIndex == DATAACTUALSIZE) {
					// writeDataLength
					bufferSend[LENGTHOFF] = (byte) bufferDataIndex;
					// sendBuffer
					outStreamFIleBack.write(bufferSend);
					bufferDataIndex = 0;
				}
			}
			if (bufferDataIndex > 0) {
				bufferSend[LENGTHOFF] = (byte) (bufferDataIndex);
				outStreamFIleBack.write(bufferSend);
				bufferSend[0] = 0;
				bufferDataIndex = 0;
			}
		} finally {
			// wirteMatchIndexs
			bufferSend[INDEXFLAGOFF] = 2;
			outStreamFIleBack.write(bufferSend);
			outStreamMatchIndexs.write(DataConvertUtils.int2bytes(matchnum));
			for (int i = 0; i < matchnum; i++) {
				outStreamMatchIndexs.write(DataConvertUtils.int2bytes(matchindexs[i]));
			}
			out.println("matchnum:" + matchnum);
			out.println("matchindexs:");
			out.println(Arrays.toString(matchindexs));

			// try {
			// inStreamFileTotal.close();
			// outStreamFIleBack.close();
			// outStreamMatchIndexs.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		}
	}

	public void readMatchIndexs(String filematchindexs) throws IOException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(filematchindexs));
		DataInputStream dataInputStream = new DataInputStream(
				bufferedInputStream);
		int matchnum = dataInputStream.readInt();
		System.out.println("matchnum:" + matchnum); //$NON-NLS-1$
		for (int i = 0; i < matchnum; i++) {
			System.out.print(dataInputStream.readInt() + ","); //$NON-NLS-1$
		}
		System.out.println();
		dataInputStream.close();
	}

	public void readUnmatchFile(String filein) throws IOException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(filein));
		byte[] buffer = new byte[DATATOTALSIZE];
		int indexFlag;
		long index;
		int length = 0;
		byte data[] = new byte[DATAACTUALSIZE];
		byte indexbyte[] = new byte[4];
		while (bufferedInputStream.read(buffer) != -1) {
			indexFlag = buffer[INDEXFLAGOFF];
			for (int i = 0; i < 4; i++) {
				indexbyte[i] = buffer[INDEXOFF + i];
			}
			index = DataConvertUtils.bytes2int(indexbyte);
			length = buffer[LENGTHOFF];
			System.out.print("indexFlag:" + indexFlag); //$NON-NLS-1$
			System.out.print(" index:" + index); //$NON-NLS-1$
			System.out.print(" length:" + length); //$NON-NLS-1$
			for (int i = 0; i < length; i++) {
				data[i] = buffer[DATAOFF + i];
				System.out.print(" data[" + i + "]:" + data[i]); //$NON-NLS-1$ //$NON-NLS-2$
			}
			System.out.println();
		}
		bufferedInputStream.close();
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TestMatch testMatch = new TestMatch();
		testMatch.testMatch(TestCreateTotal.FILETOTAL, MatchStarter.FILEBACK,
				MatchStarter.FIKEMATCHINDEXS);
		testMatch.readUnmatchFile(MatchStarter.FILEBACK);
	}

}
