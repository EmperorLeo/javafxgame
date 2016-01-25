package NewPangeaPackage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LeoGameMain extends Application {
	
	
	public void start(Stage stage){	
		stage.setTitle("Escape from New Pangea");
		
		SplashScreen splashScreen = new SplashScreen();
		Scene scene = splashScreen.initialize(stage);
		stage.setScene(scene);
		stage.show();		
	}	
	
	
	public static void main(String[] args){
		launch(args);
	}

}
