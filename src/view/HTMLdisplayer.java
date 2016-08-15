package view;

import javafx.scene.layout.Region;
import javafx.scene.web.WebView;


/**
 * 
 * @author emanuele
 * 
 */

public class HTMLdisplayer extends Region{
	
	private WebView myWebView;
	
	public HTMLdisplayer(String htmlFile, double width, double height){
		myWebView = new WebView();
		myWebView.setPrefSize(width, height);
		String html = this.getClass().getResource(htmlFile).toExternalForm(); 
		myWebView.getEngine().load(html);
		this.getChildren().add(myWebView);
	
	}

}
