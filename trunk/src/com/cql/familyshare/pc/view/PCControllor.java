/**
 * 
 */
package com.cql.familyshare.pc.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.cql.familyshare.pc.client.Cconnector;
import com.cql.familyshare.pc.client.ClientH;
import com.cql.familyshare.pc.client.ClientSystemRunner;
import com.cql.familyshare.pc.network.ShareConnector;
import com.cql.familyshare.pc.server.ServerT;
import com.cql.familyshare.pc.server.SeverSystemRuner;
import com.cql.familyshare.pc.utils.LogUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;

/**
 * This class is used for : Interacting with GUI
 * 
 * @author cql
 *
 * date:2013年8月22日
 */
public class PCControllor implements Initializable {

	@FXML
	private FlowPane FileBoard;
	@FXML
	private ToggleGroup serverOrClient;
	@FXML
	private TextField text_IP;
	@FXML
	private TextField text_PORT;
	@FXML
	private static ListView<String> logList;

	private ServerT serverT = new ServerT();
	private ClientH clientH = new ClientH();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		logList.setItems(LogUtils.getMessageList());
		text_IP.setText("127.0.0.1");
		text_IP.setEditable(false);
		text_PORT.setText("10009");
		LogUtils.logToListView("界面初始化。。。");
		FileBoard.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent dragEvent) {
				// TODO Auto-generated method stub
				Dragboard dragboard = dragEvent.getDragboard();
				List<File> files = dragboard.getFiles();
				switch (ShareConnector.getServerType()) {
				case ShareConnector.SERVER:
					try {
						serverT.setTotalFile(files.get(0).getAbsolutePath());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case ShareConnector.CLIENT:
					clientH.setHalfFile(files.get(0).getAbsolutePath());
					clientH.setRebuidFile(files.get(0).getParent() + "/rebuild");
					break;
				}
				// for (File f : files) {
				FileBoard.getChildren().add(
						new FileView(files.get(0)).getFileComponent());
			}
		});
		FileBoard.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent dragEvent) {
				// TODO Auto-generated method stub
				if (dragEvent.getGestureSource() == null) {
					dragEvent.acceptTransferModes(TransferMode.ANY);
				}
			}
		});
		serverOrClient.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {

					@Override
					public void changed(ObservableValue<? extends Toggle> arg0,
							Toggle oldarg, Toggle newarg) {
						// TODO Auto-generated method stub
						String sc = ((ToggleButton) newarg).getText();
						if (sc.equals("服务器")) {
							ShareConnector.setServerType(ShareConnector.SERVER);
							text_IP.setEditable(false);
						} else {
							ShareConnector.setServerType(ShareConnector.CLIENT);
							text_IP.setEditable(true);
						}
					}
				});
	}

	@FXML
	protected void StartRebuild(javafx.scene.input.MouseEvent mouseEvent) {
		javafx.concurrent.Task<Void> task = null;
		switch (ShareConnector.getServerType()) {
		case ShareConnector.SERVER:
			task = new javafx.concurrent.Task<Void>() {
				@Override
				protected Void call() throws Exception {
					new SeverSystemRuner().runServer(serverT);
					return null;
				}
			};

			break;
		case ShareConnector.CLIENT:

			task = new javafx.concurrent.Task<Void>() {
				@Override
				protected Void call() throws Exception {
					new ClientSystemRunner().runClient(clientH);
					return null;
				}
			};
			break;
		}
		Thread thread = new Thread(task);
		thread.start();
	}

	@FXML
	protected void connect_click(javafx.scene.input.MouseEvent mouseEvent) {
		final int port = Integer.parseInt(text_PORT.getText());
		javafx.concurrent.Task<Void> task = null;
		switch (ShareConnector.getServerType()) {
		case ShareConnector.SERVER:
			LogUtils.logToListView("服务器开始监听。。。");
			task = new javafx.concurrent.Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// TODO Auto-generated method stub
					// ServerConnector serverConnector = new SConnector();
					if (serverT.listenToConnected(port)) {
						LogUtils.logToListView("客户连接成功。");
						// serverT.setInStreamServer(serverConnector.getInStream());
						// serverT.setOutStreamServer(serverConnector
						// .getOutStream());
					}
					return null;
				}
			};

			break;
		case ShareConnector.CLIENT:
			LogUtils.logToListView("客户端开始连接。。。");
			task = new javafx.concurrent.Task<Void>() {
				@Override
				protected Void call() throws Exception {
					new Cconnector();
					if (clientH.connect(text_IP.getText(), port, 5000)) {
						LogUtils.logToListView("连接成功。");
						// clientH.setInStreamClient(clientConnector.getInStream());
						// clientH.setOutStreamClient(clientConnector
						// .getOutStream());
					}
					return null;
				}
			};

			break;
		}

		Thread thread = new Thread(task);
		thread.start();
	}

	public static void addMessage(String text) {

	}

	//
	// @FXML
	// protected void FileBord_DragDrop(DragEvent dragEvent) {
	// // if (dragEvent.getGestureSource() == null) {

	// }
	//
	// @FXML
	// protected void FileBord_DragOver(DragEvent dragEvent) {
	// Dragboard dragboard = dragEvent.getDragboard();
	// out.println("FileBord_DragOver");
	// List<File> files = dragboard.getFiles();
	// if (files.size() > 0) {
	// out.println(files.get(0).getName());
	// // m_textArea.setText(FileTools.readFile(files.get(0)));
	// }
	// }

}
