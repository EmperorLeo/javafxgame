package NewPangeaPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UpgradeDef extends ImageView implements Action {
	
	public UpgradeDef(double x, double y){
		super(new Image("images/def.png"));
		this.setFitWidth(Action.SIZE);
		this.setFitHeight(Action.SIZE);
		this.setX(x);
		this.setY(y);
	}

}
