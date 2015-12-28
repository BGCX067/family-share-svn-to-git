package com.cql.familyshare.pc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public interface ServerMatchStarter {

	public static final String FILEBACK = "test_fileback"; //$NON-NLS-1$
	public static final String FIKEMATCHINDEXS = "test_filematchindexs"; //$NON-NLS-1$
	public static final int DATAACTUALSIZE = Integer.parseInt("126"); //$NON-NLS-1$
	public static final int DATATOTALSIZE = DATAACTUALSIZE + 6;
	// public static final int BUFFERDATAOFF = 5;
	public static final int INDEXFLAGOFF = 0;
	public static final int INDEXOFF = 1;
	public static final int LENGTHOFF = 5;
	public static final int DATAOFF = 6;

	/**
	 * @param fILETOTAL
	 * @param inStreamTotal
	 * @param outStreamBack
	 * @param outStreamMatchOrder
	 * @throws IOException
	 */
	public abstract void startMatch(String fILETOTAL,
			InputStream inStreamTotal, OutputStream outStreamBack,
			OutputStream outStreamMatchOrder) throws IOException;

}