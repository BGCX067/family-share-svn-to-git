/**
 * 
 */
package com.cql.familyshare.pc.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This class is used for : the main entrance to the program
 * 
 * @author cql
 *
 * date:2013年8月22日
 */
public class SystemStarter extends Application {

	private Parent parent;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		parent = FXMLLoader.load(getClass().getResource("pc.fxml"));
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
