package NewPangeaPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UpgradeAtk extends ImageView implements Action {
	
	public UpgradeAtk(double x, double y){
		super(new Image("images/atk.png"));
		this.setFitWidth(Action.SIZE);
		this.setFitHeight(Action.SIZE);
		this.setX(x);
		this.setY(y);
	}

}
