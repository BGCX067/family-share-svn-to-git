package com.cql.familyshare.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.cql.familyshare.client.CheckSumCreater;
import com.familyshare.pre.match.Md4Checking;
import com.familyshare.pre.match.SumChecking;
import com.familyshare.pre.utils.DataConvertUtils;

/**
 * 测试用例，建立哈希表
 * 
 * @author 蔡庆亮
 * 
 */
public class TestCreateHash implements CheckSumHashCreater {

	/**
	 * 记录校验块编号
	 */
	public int[][] sumIndex;
	private String[][] mySums_table;

	/**
	 * 建立哈希表
	 * 
	 * @param filechecksum
	 * @return
	 * @throws IOException
	 */
	public String[][] buildHashTable(String filechecksum) throws IOException {

		BufferedInputStream fileInputStream = new BufferedInputStream(
				new FileInputStream(filechecksum));
		return buildHashTable(fileInputStream);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.familyshare.pre.test.checkSumHashCreater#buildHashTable(java.io.
	 * BufferedInputStream)
	 */
	@Override
	public String[][] buildHashTable(InputStream in) throws IOException {
		mySums_table = new String[TABLESIZE][HITNUM];
		sumIndex = new int[TABLESIZE][HITNUM];
		byte[] buffer1 = new byte[8];
		byte[] buffer2 = new byte[16];
		BufferedInputStream inStreamCheckSumFile = new BufferedInputStream(in);
		int ordernum = 0;
		byte end1[] = new byte[8];
		byte end2[] = new byte[16];
		Arrays.fill(end1, (byte) -1);
		Arrays.fill(end2, (byte) -1);
		inStreamCheckSumFile.read(buffer1);
		inStreamCheckSumFile.read(buffer2);
		//此处待优化
		while (!Arrays.equals(buffer1, end1) && !Arrays.equals(buffer2, end2)) {
			int index = (int) (DataConvertUtils.bytes2long(buffer1) % TABLESIZE);
			int index2 = 0;
			String md4 = Md4Checking.toHexString(buffer2);
			boolean sameflag = false;
			while (mySums_table[index][index2] != null && !sameflag) {
				if (mySums_table[index][index2] != md4) {
					index2++;
				} else {
					sameflag = true;
				}
			}
			if (!sameflag) {
				mySums_table[index][index2] = md4;
				sumIndex[index][index2] = ordernum++;
			}
			inStreamCheckSumFile.read(buffer1);
			inStreamCheckSumFile.read(buffer2);
		}
		// inStreamCheckSumFile.close();
		return mySums_table;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.familyshare.pre.test.checkSumHashCreater#checkMatch(byte[], int)
	 */
	@Override
	public int checkMatch(byte buffer[], int num) {
		long sum1 = SumChecking.checkSum_Adler32(buffer, num);
		// boolean find = false;
		String sum2 = Md4Checking.toHexString(Md4Checking.mdfour(buffer));
		int index = (int) (sum1 % TABLESIZE);
		int i = 0;

		while (mySums_table[index][i] != null) {
			if (mySums_table[index][i].equals(sum2)) {
				// find = true;
				return sumIndex[index][i] + 1;
			}
			i++;
		}
		return -1;
		// return find;
	}

	public static void main(String args[]) throws IOException {
		TestCreateHash testCreateHash = new TestCreateHash();
		testCreateHash.buildHashTable(CheckSumCreater.FILECHECKSUM);
		byte[] buffer = new byte[] { 3 };
		System.out.println(testCreateHash.checkMatch(buffer, 1));
	}
}
