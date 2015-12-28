/**
 * 
 */
package com.cql.familyshare.pc.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 检查文件是否一样
 * 
 * @author 蔡庆亮
 * 
 */
public class CheckFileEqual {

	private static final int BUFFERSIZE = 5;

	public static void checkEqual(String file1, String file2)
			throws IOException {
		System.out.println();
		BufferedInputStream inStream1 = new BufferedInputStream(
				new FileInputStream(file1));
		BufferedInputStream inStream2 = new BufferedInputStream(
				new FileInputStream(file2));
		byte buffer1[] = new byte[BUFFERSIZE];
		byte buffer2[] = new byte[BUFFERSIZE];
		int buffernum = 0;
		int unmatchnum = 0;
		while (inStream1.available() > 0) {
			inStream1.read(buffer1);
			inStream2.read(buffer2);
			if (!Arrays.equals(buffer1, buffer2)) {
				System.out.println("not match at bufferindex:" + buffernum);
				System.out.println("buffer1:" + Arrays.toString(buffer1));
				System.out.println("buffer2:" + Arrays.toString(buffer2));
				unmatchnum++;
			}
			buffernum++;
		}
		inStream1.close();
		inStream2.close();

		if (unmatchnum == 0) {
			System.out.println("totally matched!");
		} else {
			System.out.println(unmatchnum + " unMatched!");
		}

	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CheckFileEqual.checkEqual("./testfile_rebuild1.txt", "./totalfile.txt");
	}

}
