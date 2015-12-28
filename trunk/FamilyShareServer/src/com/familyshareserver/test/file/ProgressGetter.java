package com.familyshareserver.test.file;

public interface ProgressGetter {
	final static int finished = 1;
	final static int stopped = 2;
	final static int paused = 3;
	final static int running = 4;
	final static int falsed = -1;
	final static int unstarted = 0;

	int getProgressState();

	int getProgressNum();
}
