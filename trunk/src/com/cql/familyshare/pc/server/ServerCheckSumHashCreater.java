package com.cql.familyshare.pc.server;

import java.io.IOException;
import java.io.InputStream;



public interface ServerCheckSumHashCreater {

	public static final int TABLESIZE = 1000; //$NON-NLS-1$
	public static final int HITNUM = 3000; //$NON-NLS-1$

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