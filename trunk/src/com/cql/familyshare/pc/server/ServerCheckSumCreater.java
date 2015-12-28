package com.cql.familyshare.pc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public interface ServerCheckSumCreater {

	public static final int BLOCKSIZE = 126; //$NON-NLS-1$
	public static final String FILECHECKSUM = ""; //$NON-NLS-1$

	/**
	 * 内部待修改，流处理
	 * @param inStreamHalfFile
	 * @param outStreamCheckSumFile
	 * @throws IOException
	 */
	public abstract void writeCheckSumFile(
			InputStream inStreamHalfFile,
			OutputStream outStreamCheckSumFile) throws IOException;

}