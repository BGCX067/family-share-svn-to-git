package com.familyshareserver;

import com.familyshareserver.test.TestServer;
import com.familyshareserver.test.file.FileReceieveThread;
import com.familyshareserver.test.file.ProgressThread;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ProgressBar;

public class FileReceiveActivity extends Activity {

	private ProgressBar progressBar = null;
	private FileReceieveThread rFileReceieveThread = null;
	private ProgressThread progressThread = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_receive);
		findView();
		if(TestServer.getInSocket()!=null){
			rFileReceieveThread = new FileReceieveThread("test",
					TestServer.getInSocket());
			rFileReceieveThread.start();
			progressThread = new ProgressThread(rFileReceieveThread, progressBar);
			progressThread.start();
		}
	
	}

	private void findView() {
		progressBar = (ProgressBar) findViewById(R.id.progressBar_receieve);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_receive, menu);
		return true;
	}

}
