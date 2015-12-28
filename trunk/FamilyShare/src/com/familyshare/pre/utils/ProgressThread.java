package com.familyshare.pre.utils;

import javax.swing.JProgressBar;

/**
 * 进度条线程
 * @author 34262_000
 *
 */
public class ProgressThread extends Thread implements ProgressGetter {

	private static ProgressGetter progessGetter = null;
	/**
	 * 进度条控件
	 */
	private static JProgressBar jProgressBar;
	private static int progressNum = 0;
	private static int totalNum = 0;
	private static int progressState = ProgressGetter.unstarted;

	public ProgressThread(JProgressBar jProgressBar) {
		ProgressThread.progessGetter = this;
		ProgressThread.jProgressBar = jProgressBar;
		ProgressThread.progressNum = 0;
		ProgressThread.totalNum = 0;
		ProgressThread.progressState = ProgressGetter.unstarted;
		jProgressBar.setMinimum(0);
		jProgressBar.setMaximum(100);
	}

	public ProgressThread(ProgressGetter pg, JProgressBar jProgressBar) {
		ProgressThread.progessGetter = pg;
		ProgressThread.jProgressBar = jProgressBar;
		jProgressBar.setMinimum(0);
		jProgressBar.setMaximum(100);
	}

	public void run() {
		while (progessGetter.getProgressState() != ProgressGetter.finished) {
			jProgressBar.setValue(this.getProgressNum());
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		jProgressBar.setValue(100);
	}

	@Override
	public int getProgressState() {
		// TODO Auto-generated method stub
		return progressState;
	}

	@Override
	public int getProgressNum() {
		// TODO Auto-generated method stub
		int num = (int) (((float) progressNum / (float) totalNum) * 100);
		// System.out.println(num);
		return (int) num;
	}

	public static void setProgressNum(int progressNum) {
		ProgressThread.progressNum = progressNum;
	}

	public static void setProgressState(int progressState) {
		ProgressThread.progressState = progressState;
	}

	public static void addProgressNum(int amount) {
		// TODO Auto-generated method stub
		ProgressThread.progressNum += amount;
	}

	public static int getTotalNum() {
		return totalNum;
	}

	public static void setTotalNum(int totalNum) {
		ProgressThread.totalNum = totalNum;
	}

	/**
	 * 清空进度条
	 */
	public static void clear() {
		// TODO Auto-generated method stub
		if (jProgressBar != null) {
			jProgressBar.setValue(0);
		}
	}

}
