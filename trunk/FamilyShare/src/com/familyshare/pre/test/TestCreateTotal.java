package com.familyshare.pre.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 测试用例，生成测试源文件
 * 
 * @author 蔡庆亮
 * 
 */
public class TestCreateTotal {

	public static final String FILETOTAL = Messages
			.getString("TestCreateTotal.totalfile"); //$NON-NLS-1$

	public static void writeTotal(String str, String file) throws IOException {

		String content[] = str.split(Messages
				.getString("TestCreateTotal.splitflag1")); //$NON-NLS-1$
		FileOutputStream fileOutputStream = new FileOutputStream(file, false);

		for (String s : content) {
			byte b = Byte.parseByte(s);
			fileOutputStream.write(b);
		}

		fileOutputStream.close();
	}

	public static void readTotal(String file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		while (fileInputStream.available() != 0) {
			int b = fileInputStream.read();
			System.out.print(b + Messages.getString("TestCreateTotal.2")); //$NON-NLS-1$
		}
		fileInputStream.close();
	}

	public static void main(String args[]) throws IOException {
		TestCreateTotal.writeTotal(Messages.getString("TestCreateTotal.TotalContent0"), FILETOTAL); //$NON-NLS-1$
		TestCreateTotal.readTotal(FILETOTAL);
	}

}
