package com.familyshare.pre.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.familyshare.pre.match.Md4Checking;
import com.familyshare.pre.match.SumChecking;
import com.familyshare.pre.test.FileTest;
import com.familyshare.pre.utils.DataConvertUtils;
import com.familyshare.pre.utils.ProgressGetter;

/**
 * 暂时未用到
 * @author 34262_000
 *
 */
public class FileThread extends Thread implements ProgressGetter {

	private File file = null;
	private String filename = "";
	private String filepath = "";
	private OutputStream outputStream = null;
	// private FileHelper fileHelper = new FileHelper();

	private int progressNum = 0;
	private int progressState = 0;
	private int totalNum = 0;
	private int option = -1;

	public FileThread(File file, OutputStream outputStream2, int option) {
		this.file = file;
		filename = file.getName();
		filepath = file.getAbsolutePath();
		this.outputStream = outputStream2;
		this.option = option;
	}

	public void setFilePath(File file) {
		this.file = file;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void run() {
		// TODO Auto-generated method stub
		FileTest.sendFile(filepath, outputStream,option);
	}

	private void sendFileInfo() {
		// TODO Auto-generated method stub
		PrintWriter printWriter = new PrintWriter(outputStream);
		printWriter.println(filename);
		printWriter.println(totalNum);
		printWriter.flush();
	}

	@Override
	public int getProgressState() {
		// TODO Auto-generated method stub
		return this.progressState;
	}

	@Override
	public int getProgressNum() {
		// TODO Auto-generated method stub
		int num = (int) (((float) progressNum / (float) totalNum) * 100);
		// System.out.println(num);
		return (int) num;
	}

}
