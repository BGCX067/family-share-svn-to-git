package com.familyshareserver.test.file;

import android.widget.ProgressBar;

public class ProgressThread extends Thread {

	private ProgressGetter progessGetter = null;
	private ProgressBar jProgressBar;

	public ProgressThread(ProgressGetter pg, ProgressBar jProgressBar) {
		this.progessGetter = pg;
		this.jProgressBar = jProgressBar;
		jProgressBar.setMax(100);
	}

	public void run() {
		while (progessGetter.getProgressState() != ProgressGetter.finished) {
			int i = progessGetter.getProgressNum();
			jProgressBar.setProgress(i);
			// jProgressBar.setString(""+i+"%");
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
