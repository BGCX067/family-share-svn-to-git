package com.cql.familyshare.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.familyshare.pre.test.Messages;

public interface CheckSumCreater {

	public static final int BLOCKSIZE = Integer.parseInt(Messages
			.getString("TestCreateCheckSum.BLOCKSIZE")); //$NON-NLS-1$
	public static final String FILECHECKSUM = Messages
			.getString("TestCreateCheckSum.FILECHECKSUMPATH"); //$NON-NLS-1$

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