package NewPangeaPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BuildWorker extends ImageView implements Action {
	
	public BuildWorker(double x, double y){
		super(new Image("images/createworker.png"));
		this.setFitWidth(Action.SIZE);
		this.setFitHeight(Action.SIZE);
		this.setX(x);
		this.setY(y);
	}

}
