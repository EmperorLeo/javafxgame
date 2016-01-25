package NewPangeaPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GoTo extends ImageView implements Action {
	
	public GoTo(double x, double y){
		super(new Image("images/goto.png"));
		this.setFitWidth(Action.SIZE);
		this.setFitHeight(Action.SIZE);
		this.setX(x);
		this.setY(y);
	}

	
}
