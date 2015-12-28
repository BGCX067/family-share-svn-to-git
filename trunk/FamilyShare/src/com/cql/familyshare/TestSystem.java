/**
 * 
 */
package com.cql.familyshare;

import java.io.IOException;

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

/**
 * 测试系统集成
 * 
 * @author 蔡庆亮
 * 
 */
public class TestSystem {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TestSystem testSystem = new TestSystem();
		testSystem.runSystem();
	}

	/**
	 * @throws IOException
	 */
	protected void runSystem() throws IOException {
		TestCreateCheckSum testCreateCheckSum = new TestCreateCheckSum();
		new TestCreateHalf();
		TestCreateHash testCreateHash = new TestCreateHash();
		new TestCreateTotal();
		TestMatch testMatch = new TestMatch();
		FileRebuider testRebuidFile = new TestRebuidFile();

		TestCreateTotal.writeTotal(
				Messages.getString("TestCreateTotal.TotalContent0"),
				TestCreateTotal.FILETOTAL);
		TestCreateHalf.writeHalf(
				Messages.getString("TestCreateHalf.HalfFileContet0"),
				TestCreateHalf.FILEHALF);
		testCreateCheckSum.writeCheckSumFile(TestCreateHalf.FILEHALF,
				CheckSumCreater.FILECHECKSUM);
		testCreateHash.buildHashTable(CheckSumCreater.FILECHECKSUM);
		testMatch.testMatch(TestCreateTotal.FILETOTAL, MatchStarter.FILEBACK,
				MatchStarter.FIKEMATCHINDEXS);
		testMatch.readUnmatchFile(MatchStarter.FILEBACK);
		testMatch.readMatchIndexs(MatchStarter.FIKEMATCHINDEXS);
		testRebuidFile.rebuidFile(TestCreateHalf.FILEHALF,
				MatchStarter.FILEBACK, FileRebuider.FILEREBUILD);
		System.out.println("rebuidfile:");
		TestCreateTotal.readTotal(FileRebuider.FILEREBUILD);
		System.out.println("totalfile:");
		TestCreateTotal.readTotal(TestCreateTotal.FILETOTAL);
	}
}
