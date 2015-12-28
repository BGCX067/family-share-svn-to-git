package com.familyshare.pre;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.familyshare.pre.file.FileReBuild;
import com.familyshare.pre.file.FileThread;
import com.familyshare.pre.test.FileTest;
import com.familyshare.pre.test.MatchTest;
import com.familyshare.pre.utils.ProgressThread;

/**
 * Swing界面
 * 
 * @author 34262_000
 * 
 */
public class MyMainFrame extends JFrame implements ActionListener, KeyListener {

	private final int windowWidth = 800;
	private final int windowHeight = 600;

	JPanel mainPanel_Up = new JPanel();

	JPanel mainPanel_Connect = new JPanel();

	JPanel mainPanel_File = new JPanel();

	JPanel message_Panel = new JPanel();

	private JButton button_Connect;
	private JButton button_DisConnect;
	private JButton button_SendFile;
	private JButton button_FileBrowse;
	private JButton button_SendMessage;
	private JButton button_SendCheckSum;
	private JButton button_ClientMatch;
	private JButton button_SendHalfFile;
	private JButton button_ClientHalfMatch;
	private JButton button_rebuild;

	private JTextArea text_ServerMessage;
	private JTextField text_SendMessage = new JTextField(10);
	private JTextField text_File;
	private JTextField text_ip = new JTextField("192.168.137.90");
	private JTextField text_port = new JTextField("10008");

	private File fileSend = null;
	private MyClient myClient = new MyClient();
	private FileThread fileThread = null;

	private JProgressBar jProgressBar = new JProgressBar();
	private ProgressThread threadProgressBar;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5521663140655807451L;

	public MyMainFrame() {

		initFrame();
		initButton();
		initComponent();

		addToConnectPanel();
		addToMessagePanel();
		addToFrame();
		setFocusable(true);
	}

	/**
	 * 初始化面板
	 */
	private void initFrame() {
		setSize(windowWidth, windowHeight);
		this.setLayout(new BorderLayout());
		Dimension screenSize = JViewUtils.getScreeanSize();
		setLocation(screenSize.width / 8, screenSize.height / 8);
		mainPanel_Up.add(mainPanel_Connect);
		mainPanel_Up.add(jProgressBar);
	}

	/**
	 * 初始化控件
	 */
	private void initComponent() {
		text_ServerMessage = new JTextArea(20, 20);
		MyMessage.setMessageField(text_ServerMessage);
		text_SendMessage.addKeyListener(this);
		text_File = new JTextField(20);
		mainPanel_Connect.setLayout(new GridLayout(5, 2, 10, 10));
		message_Panel.setLayout(new BorderLayout());
		mainPanel_Connect.setSize(100, 200);

	}

	/**
	 * 初始化按钮
	 */
	private void initButton() {

		button_Connect = new JButton("Connect");
		button_DisConnect = new JButton("DisConnect");
		button_SendFile = new JButton("SendFile");
		button_SendMessage = new JButton("SendMessage");
		button_FileBrowse = new JButton("FileBrowse");
		button_SendCheckSum = new JButton("SendCheckSum");
		button_ClientMatch = new JButton("ClientMatch");
		button_SendHalfFile = new JButton("SendHalfFile");
		button_ClientHalfMatch = new JButton("ClientHalfMatch");
		button_rebuild = new JButton("Rebuild");

		button_SendFile.addActionListener(this);
		button_DisConnect.addActionListener(this);
		button_FileBrowse.addActionListener(this);
		button_Connect.addActionListener(this);
		button_SendMessage.addActionListener(this);
		button_SendCheckSum.addActionListener(this);
		button_ClientMatch.addActionListener(this);
		button_SendHalfFile.addActionListener(this);
		button_ClientHalfMatch.addActionListener(this);
		button_rebuild.addActionListener(this);
	}

	/**
	 * 添加面板布局
	 */
	private void addToFrame() {
		this.add(mainPanel_Up, BorderLayout.NORTH);
		this.add(mainPanel_File, BorderLayout.CENTER);
		this.add(message_Panel, BorderLayout.EAST);
	}

	/**
	 * 添加控件到消息面板
	 */
	private void addToMessagePanel() {
		JLabel text_ServerMessageTitle = new JLabel("ServerMessage:");
		message_Panel.add(text_ServerMessageTitle, BorderLayout.NORTH);
		message_Panel.add(text_ServerMessage, BorderLayout.CENTER);
		Panel psouth = new Panel();
		psouth.add(text_SendMessage);
		psouth.add(button_SendMessage);
		message_Panel.add(psouth, BorderLayout.SOUTH);
	}

	/**
	 * 添加控件到连接面板
	 */
	private void addToConnectPanel() {
		JLabel lable_ip = new JLabel("ip:");
		JLabel lable_port = new JLabel("port:");
		JLabel lable_file = new JLabel("file:");
		// mainPanel_Connect.add(lable_ip);
		// mainPanel_Connect.add(text_ip);
		// mainPanel_Connect.add(lable_port);
		// mainPanel_Connect.add(text_port);
		// mainPanel_Connect.add(button_Connect);
		// mainPanel_Connect.add(button_DisConnect);
		// mainPanel_Connect.add(lable_file);
		mainPanel_Connect.add(text_File);
		mainPanel_Connect.add(button_FileBrowse);
		mainPanel_Connect.add(button_SendFile);
		mainPanel_Connect.add(button_SendCheckSum);
		mainPanel_Connect.add(button_ClientMatch);
		mainPanel_Connect.add(button_ClientHalfMatch);
		mainPanel_Connect.add(button_rebuild);
	}

	public static void main(String[] args) {
		MyMainFrame myMainFrame = new MyMainFrame();
		myMainFrame.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10) {
			String message = text_SendMessage.getText();
			text_SendMessage.setText("");
			MyMessage.addMessage(message);
			myClient.sendMessage(message);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == button_Connect) {
			String ipAddress = text_ip.getText().trim();
			// String ipAddress = "192.168.137.90";
			int port = Integer.parseInt(text_port.getText().trim());
			myClient = new MyClient(ipAddress, port);
			myClient.start();

		} else if (e.getSource() == button_DisConnect) {
			myClient.disConnect();
			MyMessage.addMessage("quiting");

		} else if (e.getSource() == button_SendFile) {
			threadProgressBar = new ProgressThread(jProgressBar);
			threadProgressBar.start();
			fileThread = new FileThread(fileSend,
					FileTest.getOutputStream(FileTest.clientfilepath),
					FileTest.halfFile);
			fileThread.start();

		} else if (e.getSource() == button_SendMessage) {
			String message = text_SendMessage.getText();
			MyMessage.addMessage(message);
			text_SendMessage.setText("");
			myClient.sendMessage(message);
		} else if (e.getSource() == button_FileBrowse) {
			JFileChooser jf = new JFileChooser(FileTest.testdir);
			int returnVal = jf.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fileSend = jf.getSelectedFile();
				FileTest.setTotalfilepath(fileSend.getAbsolutePath());
				text_File.setText(fileSend.getAbsolutePath());
			}
		} else if (e.getSource() == button_SendCheckSum) {
			threadProgressBar = new ProgressThread(jProgressBar);
			threadProgressBar.start();
			fileThread = new FileThread(fileSend,
					FileTest.getOutputStream(FileTest.sumfilepath),
					FileTest.checkSum);

			fileThread.start();
		} else if (e.getSource() == button_ClientMatch) {
			MatchTest matchTest = new MatchTest();
			matchTest.start();
			threadProgressBar = new ProgressThread(jProgressBar);
			threadProgressBar.start();
			// matchTest.startTest();
		} else if (e.getSource() == button_ClientHalfMatch) {
			FileTest.setTotalfilepath(FileTest.clientfilepath);
			MatchTest matchTest = new MatchTest();
			matchTest.start();
			threadProgressBar = new ProgressThread(jProgressBar);
			threadProgressBar.start();
			// fileThread = new FileThread(fileSend,
			// FileTest.getOutputStream(FileTest.sumfilepath),
			// FileTest.checkSum);
			// fileThread.start();
			// matchTest.startTest();
		} else if (e.getSource() == button_rebuild) {
			Thread thread=new FileReBuild();
			thread.start();
		}
	}

}
