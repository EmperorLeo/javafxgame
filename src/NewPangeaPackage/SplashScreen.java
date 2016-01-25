package NewPangeaPackage;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SplashScreen {
	
	private Stage stage;
		
	public Scene initialize(Stage s){
		stage = s;
		Group root = new Group();
		Text t = new Text(260,100," Escape From New Pangea                               The Survival of Humankind");
		t.setWrappingWidth(500);
		t.setFont(Font.font("Verdana", 20));
		t.setFill(Color.AZURE);
		Button b = new Button("Click to start");
		ImageView im = new ImageView(new Image("images/Gobi_Desert.jpg"));
		b.setLayoutX(300);
		b.setLayoutY(275);
		b.setMinWidth(200);
		b.setMinHeight(50);
		b.setOnMouseClicked(e -> startGame(e));
		root.getChildren().add(im);
		root.getChildren().add(t);
		root.getChildren().add(b);
		
		Scene scene = new Scene(root,800,600,Color.WHITE);
		return scene;
	}
	
	public void startGame(MouseEvent e){
		if(e.getButton().equals(MouseButton.PRIMARY)){
			stage.close();
			EscapeNewPangea game = new EscapeNewPangea();
			Scene scene = game.initialize();
			stage.setScene(scene);
			stage.show();
			KeyFrame frame = game.start();
			Timeline animation = new Timeline();
			animation.setCycleCount(Animation.INDEFINITE);
			animation.getKeyFrames().add(frame);
			animation.play();
		}
		
	}

}
