package com.familyshareserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import util.InternetHelper;
import util.MyLog;
import util.SDCardHelper;

import com.familyshareserver.test.TestServer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	Button button_connect;
	Button button_disconnect;
	Button button_keyup;
	Button button_keydown;
	Button button_write;
	Button button_read;
	Button button_pageup;
	Button button_pagedown;
	Button button_esc;

	TextView textView_message;
	TestServer testServer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findMyView();
		try {
			testServer = new TestServer(10008, textView_message);
			MyLog.showTextViewMessage("绑定成功,ip:" + InternetHelper.getIp(),
					textView_message);
			MyLog.addTextViewMessage(
					"端口号：" + TestServer.getCurrentBindingPort(),
					textView_message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			MyLog.addTextViewMessage(e.getMessage(), textView_message);
			e.printStackTrace();
		}
		testServer.start();
//		Intent intent = new Intent(this, FileReceiveActivity.class);
//		startActivity(intent);
	}

	private void findMyView() {
		// TODO Auto-generated method stub
		button_connect = (Button) findViewById(R.id.button_connect);
		button_connect.setOnClickListener(this);
		button_disconnect = (Button) findViewById(R.id.button_diconnect);
		button_disconnect.setOnClickListener(this);
		button_keyup = (Button) findViewById(R.id.button_keyup);
		button_keyup.setOnClickListener(this);
		button_keydown = (Button) findViewById(R.id.button_keydown);
		button_keydown.setOnClickListener(this);
		button_write = (Button) findViewById(R.id.button_write);
		button_write.setOnClickListener(this);
		button_read = (Button) findViewById(R.id.button_read);
		button_read.setOnClickListener(this);
		button_pagedown = (Button) findViewById(R.id.button_pagedown);
		button_pagedown.setOnClickListener(this);
		button_pageup = (Button) findViewById(R.id.button_pageup);
		button_pageup.setOnClickListener(this);
		button_esc = (Button) findViewById(R.id.button_esc);
		button_esc.setOnClickListener(this);
		textView_message = (TextView) findViewById(R.id.textView_message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		SDCardHelper sdh = new SDCardHelper();

		if (arg0 == button_connect) {
			testServer.start();
		}
		if (arg0 == button_disconnect) {
			TestServer.disConnect();
			TestServer.sendMessage("quit");
		}
		if (arg0 == button_read) {
			File fileout = sdh.getFile("test", "t1");
			try {
				BufferedReader br = new BufferedReader(new FileReader(fileout));
				String message = br.readLine();
				MyLog.showTextViewMessage(message, textView_message);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MyLog.showTextViewMessage(e.getMessage(), textView_message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MyLog.showTextViewMessage(e.getMessage(), textView_message);
			}
		}
		if (arg0 == button_write) {
			try {
				File filein = sdh.createFileRelatively("test", "t1");
				FileWriter fw = new FileWriter(filein);
				fw.write("asldhalshdla");
				fw.close();
				MyLog.showTextViewMessage("写文件成功", textView_message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MyLog.showTextViewMessage(e.getMessage(), textView_message);
			}
		}
		if (arg0 == button_keyup) {
			if (testServer != null) {
				TestServer.sendMessage("keyup");
			}
		}

		if (arg0 == button_keydown) {
			if (testServer != null) {
				TestServer.sendMessage("keydown");
			}
		}
		if (arg0 == button_pageup) {
			if (testServer != null) {
				TestServer.sendMessage("keypageup");
			}
		}
		if (arg0 == button_pagedown) {
			if (testServer != null) {
				TestServer.sendMessage("keypagedown");
			}
		}
		if (arg0 == button_esc) {
			if (testServer != null) {
				TestServer.sendMessage("keyesc");
			}
		}

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getTitle().equals("quit")) {
			System.exit(0);
		}
		if (item.getTitle().equals("ToFileReceieve")) {
			Intent intent = new Intent(this, FileReceiveActivity.class);
			startActivity(intent);
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
