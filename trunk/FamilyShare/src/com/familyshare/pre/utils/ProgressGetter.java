package com.familyshare.pre.utils;

/**
 * 进度数据获取接口
 * @author 34262_000
 *
 */
public interface ProgressGetter {
	final static int finished = 1;
	final static int stopped = 2;
	final static int paused = 3;
	final static int running = 4;
	final static int falsed = -1;
	final static int unstarted = 0;

	/**
	 * 获取当前进度条状态
	 * @return
	 */
	int getProgressState();

	/**
	 * 获取进度条进度
	 * @return
	 */
	int getProgressNum();
}
