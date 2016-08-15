package view;

import java.io.File;
import java.util.Arrays;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;

public class ImageDialog extends ChoiceDialog<String>{

	public ImageDialog() {
		File folder = new File("Images");
		File[] listOfFiles = folder.listFiles();
		//Arrays.asList(listOfFiles)
		this.setSelectedItem(listOfFiles[0].getName());
		for(File image: listOfFiles){
			this.getItems().add(image.getName());
		}
		//this.showAndWait();
	}

}
