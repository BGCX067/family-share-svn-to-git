/**
 * 
 */
package com.cql.familyshare;

import java.io.IOException;
import java.io.PrintStream;

import com.cql.familyshare.client.CheckSumCreater;
import com.cql.familyshare.client.FileRebuider;
import com.cql.familyshare.client.TestCreateCheckSum;
import com.cql.familyshare.client.TestRebuidFile;
import com.cql.familyshare.server.MatchStarter;
import com.cql.familyshare.server.TestCreateHash;
import com.cql.familyshare.server.TestMatch;
import com.familyshare.pre.test.Messages;
import com.familyshare.pre.test.TestCreateHalf;
import com.familyshare.pre.test.TestCreateTotal;
import com.familyshare.pre.utils.CheckFileEqual;

/**
 * @author 蔡庆亮
 * 
 */
public class TestSystemActual1 extends TestSystem {

	public static final String TOTALFILE = Messages
			.getString("TestSystemActual1.TOTALFILE"); //$NON-NLS-1$
	public static final String HALFFILE = Messages
			.getString("TestSystemActual1.HALFFILE"); //$NON-NLS-1$
	private static PrintStream out = System.out;

	@Override
	protected void runSystem() throws IOException {
		TestCreateCheckSum testCreateCheckSum = new TestCreateCheckSum();
		new TestCreateHalf();
		TestCreateHash testCreateHash = new TestCreateHash();
		new TestCreateTotal();
		TestMatch testMatch = new TestMatch();
		FileRebuider testRebuidFile = new TestRebuidFile();
		out.println("TotalFile:");
		TestCreateTotal.readTotal(TOTALFILE);
		out.println();
		out.println("HalfFile:");
		TestCreateTotal.readTotal(HALFFILE);
		out.println();
		out.println("start..."); //$NON-NLS-1$
		testCreateCheckSum.writeCheckSumFile(HALFFILE,
				CheckSumCreater.FILECHECKSUM);
		testCreateHash.buildHashTable(CheckSumCreater.FILECHECKSUM);
		testMatch.testMatch(TOTALFILE, MatchStarter.FILEBACK,
				MatchStarter.FIKEMATCHINDEXS);
		testMatch.readUnmatchFile(MatchStarter.FILEBACK);
		testMatch.readMatchIndexs(MatchStarter.FIKEMATCHINDEXS);
		testRebuidFile.rebuidFile(HALFFILE, MatchStarter.FILEBACK,
				FileRebuider.FILEREBUILD);
		System.out.println("end"); //$NON-NLS-1$
		out.println("rebuildFile:");
		TestCreateTotal.readTotal(FileRebuider.FILEREBUILD);
		CheckFileEqual.checkEqual(TOTALFILE, FileRebuider.FILEREBUILD);
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TestSystem testSystem = new TestSystemActual1();
		testSystem.runSystem();
	}

}
