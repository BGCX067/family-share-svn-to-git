/**
 * 
 */
package com.cql.familyshare.pc.client;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import com.cql.familyshare.pc.match.Md4Checking;
import com.cql.familyshare.pc.match.SumChecking;
import com.cql.familyshare.pc.server.ServerCheckSumCreater;
import com.cql.familyshare.pc.utils.DataConvertUtils;


/**
 * 测试用例，根据不完整文件生成校验块文件
 * 
 * @author 蔡庆亮
 * 
 */
public class ClientCreateCheckSum implements ServerCheckSumCreater {
	public void writeCheckSumFile(String filein, String fileout)
			throws IOException {
		BufferedInputStream fileInputStream = new BufferedInputStream(
				new FileInputStream(filein));
		FileOutputStream fileOutputStream = new FileOutputStream(fileout, false);
		writeCheckSumFile(fileInputStream, fileOutputStream);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.familyshare.pre.test.CheckSumCreater#writeCheckSumFile(java.io.
	 * BufferedInputStream, java.io.FileOutputStream)
	 */
	@Override
	public void writeCheckSumFile(InputStream in,
			OutputStream outStreamCheckSumFile) throws IOException {
		BufferedInputStream inStreamHalfFile = new BufferedInputStream(in);
		byte[] buffer = new byte[BLOCKSIZE];
		int amount = 0;
		while ((amount = inStreamHalfFile.read(buffer)) != -1) {
			sendCheckSumToFile(buffer, outStreamCheckSumFile, amount);
		}
		//结束信息。
		byte end[] = new byte[24];
		Arrays.fill(end, (byte) -1);
		outStreamCheckSumFile.write(end);
	}

	public void sendCheckSumToFile(byte[] buffer, OutputStream outputStream,
			int amount) throws IOException {
		long sum1 = SumChecking.checkSum_Adler32(buffer, amount);
		byte sum2[] = Md4Checking.mdfour(buffer);
		outputStream.write(DataConvertUtils.long2bytes(sum1));
		outputStream.write(sum2);
	}

	public void readCheckSumFile(String filein) throws IOException {
		BufferedInputStream fileInputStream = new BufferedInputStream(
				new FileInputStream(filein));
		byte[] buffer1 = new byte[8];
		byte[] buffer2 = new byte[16];
		while ((fileInputStream.read(buffer1)) != -1) {
			fileInputStream.read(buffer2);
			System.out.print(DataConvertUtils.bytes2long(buffer1) + ","); //$NON-NLS-1$
			System.out.print(Md4Checking.toHexString(buffer2) + ";"); //$NON-NLS-1$
			System.out.println();
		}
		fileInputStream.close();
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		TestCreateCheckSum testCreateCheckSum = new TestCreateCheckSum();
//		testCreateCheckSum.writeCheckSumFile(TestCreateHalf.FILEHALF,
//				FILECHECKSUM);
//		testCreateCheckSum.readCheckSumFile(FILECHECKSUM);
	}

}
