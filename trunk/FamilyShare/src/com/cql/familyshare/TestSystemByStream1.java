/**
 * 
 */
package com.cql.familyshare;

import java.io.IOException;
import com.cql.familyshare.client.ClientH;
import com.cql.familyshare.client.ClientWithHalf;
import com.cql.familyshare.server.ServerT;
import com.cql.familyshare.server.ServerWithTotal;

/**
 * 测试系统1，使用流传输
 * 
 * @author 蔡庆亮
 * 
 */
public class TestSystemByStream1 implements SystemStarter {

	private static final String fileHalf = null;
	private static final String fileTotal = null;
	private ClientWithHalf clientWithHalf;
	private ServerWithTotal serverWithTotal;

	@Override
	public void runSystem() throws IOException {
		// TODO Auto-generated method stub
		clientWithHalf.setHalfFile(fileHalf);
		serverWithTotal.setTotalFile(fileTotal);
		clientWithHalf.connect(serverWithTotal);
		serverWithTotal.connect(clientWithHalf);
		clientWithHalf.sendCheckSumFile();
		serverWithTotal.receieveCheckSumFile();
		serverWithTotal.startMatch();
		serverWithTotal.sendBackData();
		clientWithHalf.rebuidFile();
		clientWithHalf.checkFileEqual();
		clientWithHalf.clearTempData();
		serverWithTotal.getConfirm();
		serverWithTotal.clearTempData();
	}

	@Override
	public void setClient(ClientWithHalf clientWithHalf) {
		// TODO Auto-generated method stub
		this.clientWithHalf = clientWithHalf;
	}

	@Override
	public void setServer(ServerWithTotal serverWithTotal) {
		// TODO Auto-generated method stub
		this.serverWithTotal = serverWithTotal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SystemStarter starter = new TestSystemByStream1();
		starter.setClient(new ClientH());
		starter.setServer(new ServerT());
	}

}
