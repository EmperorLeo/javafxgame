package NewPangeaPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CancelWorker extends ImageView implements Action {
	
	public CancelWorker(double x, double y){
		super(new Image("images/worker.png"));
		this.setFitWidth(Action.SIZE);
		this.setFitHeight(Action.SIZE);
		this.setX(x);
		this.setY(y);
	}


	
}
