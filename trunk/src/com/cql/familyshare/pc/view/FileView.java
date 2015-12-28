/**
 * 
 */
package com.cql.familyshare.pc.view;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * @author cql
 * 
 */
public class FileView {

	private File file;
	public FileView(String filename) {
		this.file = new File(filename);
	}

	public FileView(File f) {
		// TODO Auto-generated constructor stub
		this.file = f;
	}

	public Node getFileComponent() {
		ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView()
				.getSystemIcon(file);
		java.awt.Image image = icon.getImage();
		@SuppressWarnings("deprecation")
		Image img = javafx.scene.image.Image.impl_fromExternalImage(image);
		ImageView imageView = new ImageView(img);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		// imageView.gets
		Label label = new Label(file.getName());
		ProgressBar progressBar = new ProgressBar();
		VBox vBox = new VBox();
		vBox.getChildren().addAll(imageView, label, progressBar);
		return vBox;
	}

}
