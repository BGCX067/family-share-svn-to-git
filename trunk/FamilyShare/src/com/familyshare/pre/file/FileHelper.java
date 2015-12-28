package com.familyshare.pre.file;
//package com.famiyshare.pre.file;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.OutputStream;
//
//import com.familyshare.pre.utils.ProgressGetter;
//
//public class FileHelper implements ProgressGetter {
//
//	private int progressNum = 0;
//	private int progessState = 0;
//	private int totalNum = 0;
//
//	public boolean sendFile(File file, OutputStream outputStream) {
//		if (file == null) {
//			return false;
//		}
//		if (outputStream == null) {
//			return false;
//		}
//		try {
//			FileInputStream fileInputStream = new FileInputStream(file);
//			totalNum = fileInputStream.available();
//			byte[] buffer = new byte[1024];
//			int amount = 0;
//			while ((amount = fileInputStream.read(buffer)) != -1) {
//				progessState = ProgressGetter.running;
//				outputStream.write(buffer, 0, amount);
//				progressNum++;
//			}
//			progessState = ProgressGetter.finished;
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			progessState = ProgressGetter.falsed;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			progessState = ProgressGetter.falsed;
//		}
//		return false;
//	}
//
//	@Override
//	public int getProgressState() {
//		// TODO Auto-generated method stub
//		return progressNum;
//	}
//
//	@Override
//	public int getProgress() {
//		// TODO Auto-generated method stub
//		return progressNum/totalNum;
//	}
//
//}
