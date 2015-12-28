package com.familyshare.pre.test;

import java.io.IOException;

/**
 * 测试用例，生成不完整文件
 * 
 * @author 蔡庆亮
 * 
 */
public class TestCreateHalf {

	public static final String FILEHALF = Messages
			.getString("TestCreateHalf.halffile"); //$NON-NLS-1$

	public static void writeHalf(String str, String file) throws IOException {
		TestCreateTotal.writeTotal(str, file);
	}

	public static void readHalf(String file) throws IOException {
		TestCreateTotal.readTotal(file);
	}

	public static void main(String args[]) throws IOException {
		TestCreateHalf.writeHalf(Messages.getString("TestCreateHalf.HalfFileContet0"), FILEHALF); //$NON-NLS-1$
		TestCreateHalf.readHalf(FILEHALF);
	}

}
