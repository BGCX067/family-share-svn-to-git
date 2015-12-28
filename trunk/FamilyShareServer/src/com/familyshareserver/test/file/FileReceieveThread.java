package com.familyshareserver.test.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import util.SDCardHelper;

public class FileReceieveThread extends Thread implements ProgressGetter {

	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	private int progressState = 0;
	private int progressNum = 0;
	private int totalNum = 0;

	private SDCardHelper sdCardHelper = new SDCardHelper();
	private String filename = "";

	public FileReceieveThread(String savePath, InputStream inputStream) {
		if (inputStream == null)
			return;
		this.inputStream = inputStream;
		if (sdCardHelper.checkSDAccess()) {
			try {
				getFileInfoFromInputStream();
				File savefile = sdCardHelper.createFileRelatively(savePath,
						filename);
				outputStream = new FileOutputStream(savefile);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void getFileInfoFromInputStream() {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		try {
			filename = bufferedReader.readLine().trim();
			totalNum = Integer.parseInt(bufferedReader.readLine().trim());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return this.filename;
	}

	public void run() {
		BufferedInputStream fileBufferedInputStream = new BufferedInputStream(
				inputStream);
		int amount = 0;
		byte[] buffer = new byte[1024];
		try {
			while ((amount = fileBufferedInputStream.read(buffer)) != -1) {
				progressState = ProgressGetter.running;
				outputStream.write(buffer, 0, amount);
				progressNum += amount;
			}
			progressState = ProgressGetter.finished;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			progressState = ProgressGetter.falsed;
		} finally {
			try {
				outputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		System.out.println(num);
		return (int) num;
	}
}
