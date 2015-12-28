/**
 * 
 */
package com.cql.familyshare.pc.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.cql.familyshare.pc.server.ServerWithTotal;


/**
 * 拥有不完整文件的客户端
 * 
 * @author 蔡庆亮
 * 
 */
public interface ClientWithHalf {

	/**
	 * 
	 * @param FileHalf
	 */
	void setHalfFile(InputStream FileHalf);

	/**
	 * 
	 * @param FileHalf
	 */
	void setHalfFile(String FileHalf);

	/**
	 * @param FileRebuild
	 */
	void setRebuidFile(String FileRebuild);

	/**
	 * 建立连接，获取输入输出流
	 */
	boolean connect(String ip, int port, int timeout);

	/**
	 * 发送校验块文件
	 * 
	 * @param outStreamFileCheckSum
	 * @throws IOException
	 */
	void sendCheckSumFile(OutputStream outStreamFileCheckSum)
			throws IOException;

	// /**
	// * 接受未匹配数据报到本地
	// *
	// * @param inStreamFileUnMatched
	// * 未匹配数据流
	// */
	// boolean receieveFileUnMatched(InputStream inStreamFileUnMatched,
	// OutputStream outStreamFileUnMatched);

	/**
	 * 最终简单检验文件是否重组成功
	 * 
	 * @return
	 */
	boolean checkFileEqual();

	/**
	 * 清理临时文件
	 */
	void clearTempData();

	void connect(ServerWithTotal serverWithTotal);

	void sendCheckSumFile() throws IOException;

	void rebuidFile() throws FileNotFoundException, IOException;

	InputStream getOutStreamClient();

	OutputStream getInStreamClient();

	/**
	 * 待修改，matchindexs流
	 * 
	 * @param inStreamBackData
	 * @param filehalf
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	void rebuidFile(InputStream inStreamBackData, String filehalf)
			throws FileNotFoundException, IOException;

	void close();

}
