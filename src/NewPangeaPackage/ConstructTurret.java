package NewPangeaPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ConstructTurret extends ImageView implements Action {
	
	public ConstructTurret(double x, double y){
		super(new Image("images/construct.png"));
		this.setFitWidth(Action.SIZE);
		this.setFitHeight(Action.SIZE);
		this.setX(x);
		this.setY(y);
	}

}
