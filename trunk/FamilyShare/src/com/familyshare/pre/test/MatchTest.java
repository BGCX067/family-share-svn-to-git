package com.familyshare.pre.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import com.familyshare.pre.match.MyHash;
import com.familyshare.pre.match.MyMatch;

/**
 * 匹配测试模块
 * 
 * @author 34262_000
 * 
 */
public class MatchTest extends Thread {

	private File file;
	/**
	 * 哈希函数类
	 */
	private MyHash myHash = new MyHash();

	/**
	 * 根据路径获取输入流
	 * 
	 * @param filepath
	 * @return
	 */
	public InputStream getInputStream(String filepath) {
		file = new File(filepath);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * 开始匹配测试
	 */
	public void startTest() {
		
		//获取校验码输入流
		InputStream in_sum = getInputStream(FileTest.sumfilepath);
		
		//建立哈希表
		myHash.buildHashTable(in_sum);
		// InputStream in_clientfile = getInputStream(FileTest.totalfilepath);
		
		//获取完整文件输入流
		InputStream in_clientfile = getInputStream(FileTest.totalfilepath);
		
		//获取匹配返回数据输出流
		OutputStream out_backdatafile = FileTest
				.getOutputStream(FileTest.backdatafile);
		
		
		MyMatch.startMatch(myHash, in_clientfile, out_backdatafile);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		startTest();
		// super.run();
	}

}
