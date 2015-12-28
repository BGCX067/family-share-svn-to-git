package com.familyshare.pre.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.familyshare.pre.test.FileTest;
import com.familyshare.pre.utils.ProgressGetter;
import com.familyshare.pre.utils.ProgressThread;

public class FileReBuild extends Thread {
	public static void reuild(InputStream inHalfFile, InputStream inBackFile,
			OutputStream rebuildFile) {
		byte bufferHalfFile[] = new byte[1024];
		byte bufferBack[] = new byte[137];

		int amount = 0;
		int amountBack = 0;
		int bufferindex = 0;
		int bufferDataLength = 0;
		int halfReadNum = 0;
		int totalNum = 0;
		try {
			totalNum = inHalfFile.available() + inBackFile.available();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ProgressThread.clear();
		ProgressThread.setTotalNum(totalNum);
		ProgressThread.setProgressState(ProgressGetter.running);
		int number=0;
		try {
			while ((amountBack = inBackFile.read(bufferBack)) != -1) {

				if (bufferBack[0] == 1) {
					bufferindex = (bufferBack[1] & 0xFF);
					bufferindex |= ((bufferBack[2] << 8) & 0xFF00);
					bufferindex |= ((bufferBack[3] << 16) & 0xFF0000);
					bufferindex |= ((bufferBack[4] << 24) & 0xFF000000);
				}
				bufferDataLength = (bufferBack[5] & 0xFF);
				bufferDataLength |= ((bufferBack[6] << 8) & 0xFF00);
				bufferDataLength |= ((bufferBack[7] << 16) & 0xFF0000);
				bufferDataLength |= ((bufferBack[8] << 24) & 0xFF000000);
				number++;
				byte bufferRealData[] = new byte[bufferDataLength];
				System.arraycopy(bufferBack, 9, bufferRealData, 0,
						bufferDataLength);
				while (halfReadNum < bufferindex) {
					inHalfFile.read(bufferHalfFile);
					ProgressThread.addProgressNum(bufferDataLength);
					halfReadNum++;
					rebuildFile.write(bufferHalfFile);
				}
				rebuildFile.write(bufferRealData);
				ProgressThread.addProgressNum(amountBack);
			}
			while ((amount = inHalfFile.read(bufferHalfFile)) != -1) {
				rebuildFile.write(bufferHalfFile);
				ProgressThread.addProgressNum(amount);
			}
			ProgressThread.setProgressState(ProgressGetter.finished);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ProgressThread.setProgressState(ProgressGetter.finished);
		}finally{
			try {
				inHalfFile.close();
				inBackFile.close();
				rebuildFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("number:"+number);
		System.out.println("rebuild finish");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			File rebuidfile = new File(FileTest.rebuidfile);
			if (!rebuidfile.exists()) {
				rebuidfile.createNewFile();
			}
			FileReBuild.reuild(new FileInputStream(FileTest.clientfilepath),
					new FileInputStream(FileTest.backdatafile),
					new FileOutputStream(rebuidfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
