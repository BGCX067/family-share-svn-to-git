/**
 * 
 */
package com.cql.familyshare.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 测试用例，根据返回的未匹配数据头文件信息，建立表，用于判断是否删除无效的部分文件块
 * 
 * @author 蔡庆亮
 * 
 */
public class TestBuildMatchTable {

	private boolean matchindexs[];

	/**
	 * @param halfFileBlockNum
	 *            不完整文件块的数量，用于数组创建
	 * @param filematchindexs
	 * @throws IOException
	 */
	public void buildMatchTable(int halfFileBlockNum, String filematchindexs)
			throws IOException {
		DataInputStream dataInputStream = new DataInputStream(
				new BufferedInputStream(new FileInputStream(filematchindexs)));
		int num = dataInputStream.readInt();
		matchindexs = new boolean[halfFileBlockNum];
		for (int i = 0; i < num; i++) {
			matchindexs[dataInputStream.readInt()] = true;
		}
		dataInputStream.close();
	}

	public void readMatchTable() {

		System.out.println(Arrays.toString(matchindexs));
	}

	/**
	 * 判断是否有效，有效返回true
	 * 
	 * @param index
	 * @return
	 */
	public boolean checkUseful(int index) {
		return matchindexs[index];
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TestBuildMatchTable table = new TestBuildMatchTable();
		table.buildMatchTable(1000, MatchStarter.FIKEMATCHINDEXS);
		table.readMatchTable();
	}

}
