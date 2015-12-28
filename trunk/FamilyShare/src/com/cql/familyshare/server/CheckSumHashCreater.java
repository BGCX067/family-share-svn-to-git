package com.cql.familyshare.server;

import java.io.IOException;
import java.io.InputStream;

import com.familyshare.pre.test.Messages;

public interface CheckSumHashCreater {

	public static final int TABLESIZE = Integer.parseInt(Messages
			.getString("TestCreateHash.HASHTABLESIZE")); //$NON-NLS-1$
	public static final int HITNUM = Integer.parseInt(Messages
			.getString("TestCreateHash.HITNUM")); //$NON-NLS-1$

	/**
	 * 建立哈希表
	 * 
	 * @param inStreamCheckSum
	 * @return
	 * @throws IOException
	 */
	public abstract String[][] buildHashTable(InputStream inStreamCheckSum)
			throws IOException;

	/**
	 * 查看是否匹配，若匹配返回校验块序号（从1开始），否则返回-1
	 * 
	 * @param buffer
	 * @param num
	 * @return
	 */
	public abstract int checkMatch(byte buffer[], int num);

}