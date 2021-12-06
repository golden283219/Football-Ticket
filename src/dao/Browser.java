package dao;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class Browser extends Region {

	private WebView browser = new WebView();
	WebEngine webEngine = browser.getEngine();

	/***
	 *
	 * This function is where the javascript is called and map is loaded inside the
	 * webengine the cluster is handled by google api itself main inputs are lat and
	 * long
	 */
	
	static { 
		// use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }
	
	public WebView getWebView() {
		return browser;
	}
	
	public Browser(String locations) {

		webEngine.setJavaScriptEnabled(true);

		// A Worker load the page
		getChildren().add(browser);

		Worker<Void> worker = webEngine.getLoadWorker();

		// Listening to the status of worker
		worker.stateProperty().addListener(new ChangeListener<Worker.State>() {

			@Override
			public void changed(ObservableValue<? extends Worker.State> observable, //
					Worker.State oldValue, Worker.State newValue) {

				// When load successed.
				if (newValue == Worker.State.SUCCEEDED) {

					try {
						// Get window object of page.
						JSObject jsobj = (JSObject) webEngine.executeScript("window");

						// Set member for 'window' object.
						// In Javascript access: window.myJavaMember....
						jsobj.setMember("java", new Bridge());

						System.out.println(locations);

						browser.getEngine().executeScript("initMap()");

						String script = "drawMap(" + locations + ")";

						browser.getEngine().executeScript(script);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		});
		// noinspection ConstantConditions
		webEngine.load(ClassLoader.getSystemClassLoader().getResource("resources/google.html").toExternalForm());
		webEngine.reload();

	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.TOP);
	}

	@Override
	protected double computePrefWidth(double height) {
		return 750;
	}

	@Override
	protected double computePrefHeight(double width) {
		return 700;
	}
}
