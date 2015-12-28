/**
 * 
 */
package com.cql.familyshare.pc.client;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import com.cql.familyshare.pc.network.DatagramType;
import com.cql.familyshare.pc.server.ServerCheckSumCreater;
import com.cql.familyshare.pc.server.ServerMatchStarter;
import com.cql.familyshare.pc.utils.DataConvertUtils;

/**
 * @author 蔡庆亮
 * 
 */
public class ClientRebuidFile implements ClientFileRebuider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.client.FileRebuider#rebuidFile(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void rebuidFile(String filehalf, String fileback, String filerebuid)
			throws IOException {

		InputStream inFileBack = new FileInputStream(fileback);
		OutputStream outFileRebuid = new FileOutputStream(filerebuid);

		rebuidFile(filehalf, inFileBack, outFileRebuid);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cql.familyshare.client.FileRebuider#rebuidFile(java.lang.String,
	 * java.io.InputStream, java.io.OutputStream)
	 */
	@Override
	public void rebuidFile(String filehalf, InputStream inFileBack,
			OutputStream rebuidOut) throws FileNotFoundException, IOException {
		RandomAccessFile randomAccessFile = new RandomAccessFile(filehalf, "r");
		BufferedInputStream backInput = null;
		backInput = new BufferedInputStream(inFileBack);

		byte halfBuffer[] = new byte[ServerCheckSumCreater.BLOCKSIZE];
		byte backBuffer[] = new byte[ServerMatchStarter.DATATOTALSIZE];
		int currentIndex = 0;
		int amount = 0;

		try {

			// getMatchTable
			// DataInputStream dataInputStream = new
			// DataInputStream(inFileBack);
			byte b[] = new byte[4];
			inFileBack.read(b);

			int matchnum = DataConvertUtils.bytes2int(b);
			int matchindexs[] = new int[matchnum];
			for (int i = 0; i < matchnum; i++) {
				inFileBack.read(b);
				matchindexs[i] = DataConvertUtils.bytes2int(b) - 1;
			}
			// dataInputStream.close();
			//
			// boolean rebuildSwitch = false;
			// if (backInput.read(backBuffer) != -1) {
			// byte indexFlag = backBuffer[MatchStarter.INDEXFLAGOFF];
			// switch (indexFlag) {
			// case DatagramType.ALLMATCH: {
			//
			// rebuildSwitch = true;
			// }
			// break;
			// case DatagramType.ZEROMATCH: {
			//
			// // rebuildSwitch = true;
			// }
			// break;
			// }
			// }

			while (backInput.read(backBuffer) != -1) {
				int indexFlag = backBuffer[ServerMatchStarter.INDEXFLAGOFF];
				if (indexFlag == DatagramType.UNMATCHDATA_END)
					break;
				int index = 0;
				int length = backBuffer[ServerMatchStarter.LENGTHOFF];
				byte[] data = new byte[length];
				if (indexFlag == DatagramType.UNMATCHDATA_INDEX_VALID) {
					// index valid
					// seek to currentIndex*TestCreateCheckSum.BLOCKSIZE
					// read index - currentIndex blocks of halffile
					// write to rebuildfile
					// currentIndex = index
					byte[] indexbyte = new byte[4];
					for (int i = 0; i < 4; i++) {
						indexbyte[i] = backBuffer[ServerMatchStarter.INDEXOFF + i];
					}
					index = DataConvertUtils.bytes2int(indexbyte);
					if (index > currentIndex) {
						randomAccessFile.seek(ServerCheckSumCreater.BLOCKSIZE
								* matchindexs[currentIndex]);
						for (int i = 0; i < (index - currentIndex); i++)
							if ((amount = randomAccessFile.read(halfBuffer)) != -1) {
								rebuidOut.write(halfBuffer, 0, amount);
							}
						currentIndex = index;

					}
				}
				// read a block_backfile and write to filerebuild
				for (int i = 0; i < length; i++) {
					data[i] = backBuffer[ServerMatchStarter.DATAOFF + i];// 待优化
				}
				rebuidOut.write(data);
			}

			// write remained halffile data
			for (; currentIndex < matchnum; currentIndex++) {
				int index = matchindexs[currentIndex];
				randomAccessFile.seek(ServerCheckSumCreater.BLOCKSIZE * index);
				if ((amount = randomAccessFile.read(halfBuffer)) != -1) {
					rebuidOut.write(halfBuffer, 0, amount);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		FileRebuider testRebuidFile = new TestRebuidFile();
//		testRebuidFile.rebuidFile(TestCreateHalf.FILEHALF,
//				MatchStarter.FILEBACK, FileRebuider.FILEREBUILD);
//		// testRebuidFile.readTotal(TestRebuidFile.FILEREBUILD);
//		System.out.println();
//		TestCreateTotal.readTotal(TestCreateTotal.FILETOTAL);
	}

}
