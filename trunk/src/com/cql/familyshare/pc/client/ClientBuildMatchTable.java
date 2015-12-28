/**
 * 
 */
package com.cql.familyshare.pc.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import com.cql.familyshare.pc.server.ServerMatchStarter;

/**
 * 测试用例，根据返回的未匹配数据头文件信息，建立表，用于判断是否删除无效的部分文件块
 * 
 * @author 蔡庆亮
 * 
 */
public class ClientBuildMatchTable {

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
		ClientBuildMatchTable table = new ClientBuildMatchTable();
		table.buildMatchTable(1000, ServerMatchStarter.FIKEMATCHINDEXS);
		table.readMatchTable();
	}

}
