/**
 * 
 */
package com.cql.familyshare.pc.server;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import com.cql.familyshare.pc.client.ClientWithHalf;

/**
 * 拥有完整文件的服务器端
 * 
 * @author 蔡庆亮
 * 
 */
public interface ServerWithTotal {

	/**
	 * 清楚临时文件
	 * 
	 * @return
	 */
	boolean clearTempData();

	boolean connect();

	void connect(ClientWithHalf clientWithHalf);

	void getConfirm();

	/**
	 * 确认客户端重组是否成功
	 * 
	 * @param inStreamConfirm
	 * @return
	 */
	boolean getConfirm(InputStream inStreamConfirm);

	InputStream getInStreamServer();

	void closeInStream();

	void closeOutStream();

	OutputStream getOutStreamServer();

	boolean listenToConnected(int port);

	void receieveCheckSumFile();

	void receieveCheckSumFile(InputStream inStreamFileCheckSum);

	void sendBackData();

	void sendBackData(OutputStream outStreamBackData);

	void setTotalFile(InputStream fileTotal);

	void setTotalFile(String fileTotal) throws FileNotFoundException;

	void startMatch();

	/**
	 * 开始匹配，并输出未匹配数据包和匹配编号数组
	 * 
	 * @param inStreamFileCheckSum
	 * @param outStreamUnMatchData
	 * @param outStreamMatchIndexs
	 */
	void startMatch(InputStream inStreamFileCheckSum,
			OutputStream outStreamUnMatchData, OutputStream outStreamMatchIndexs);

	void close();

}
