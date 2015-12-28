/**
 * 
 */
package com.cql.familyshare.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.cql.familyshare.server.MatchStarter;
import com.familyshare.pre.test.TestCreateHalf;
import com.familyshare.pre.test.TestCreateTotal;
import com.familyshare.pre.utils.DataConvertUtils;

/**
 * @author 蔡庆亮
 * 
 */
public class TestRebuidFile implements FileRebuider {

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

		byte halfBuffer[] = new byte[CheckSumCreater.BLOCKSIZE];
		byte backBuffer[] = new byte[MatchStarter.DATATOTALSIZE];
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

			while (backInput.read(backBuffer) != -1) {
				int indexFlag = backBuffer[MatchStarter.INDEXFLAGOFF];
				if (indexFlag == 2)
					break;
				int index = 0;
				int length = backBuffer[MatchStarter.LENGTHOFF];
				byte[] data = new byte[length];
				if (indexFlag == 1) {
					// index valid
					// seek to currentIndex*TestCreateCheckSum.BLOCKSIZE
					// read index - currentIndex blocks of halffile
					// write to rebuildfile
					// currentIndex = index
					byte[] indexbyte = new byte[4];
					for (int i = 0; i < 4; i++) {
						indexbyte[i] = backBuffer[MatchStarter.INDEXOFF + i];
					}
					index = DataConvertUtils.bytes2int(indexbyte);
					if (index > currentIndex) {
						randomAccessFile.seek(CheckSumCreater.BLOCKSIZE
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
					data[i] = backBuffer[MatchStarter.DATAOFF + i];// 待优化
				}
				rebuidOut.write(data);
			}

			// write remained halffile data
			for (; currentIndex < matchnum; currentIndex++) {
				int index = matchindexs[currentIndex];
				randomAccessFile.seek(CheckSumCreater.BLOCKSIZE * index);
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
		FileRebuider testRebuidFile = new TestRebuidFile();
		testRebuidFile.rebuidFile(TestCreateHalf.FILEHALF,
				MatchStarter.FILEBACK, FileRebuider.FILEREBUILD);
		// testRebuidFile.readTotal(TestRebuidFile.FILEREBUILD);
		System.out.println();
		TestCreateTotal.readTotal(TestCreateTotal.FILETOTAL);
	}

}
