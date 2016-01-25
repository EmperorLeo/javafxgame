// This entire file is part of my masterpiece.
// Leo Feldman

package NewPangeaPackage;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class IronOre extends ImageView implements Unit {
	
	private int oreValue;
	private final String name = "IronOre";

	
	public IronOre(int x, int y){
		super(new Image("images/ironore.png"));
		this.setFitWidth(50);
		this.setFitHeight(50);
		this.setX(x);
		this.setY(y);
		oreValue = 1800;
	}

	@Override
	public int getHealth() {
		return oreValue;
	}

	@Override
	public void setHealth(int h) {
		oreValue = h;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Node[] updateGameInterface(GameInterface gi) {
		Text t = new Text(gi.getMenu().get(7).getX(),gi.getMenu().get(7).getY(),"Ore="+oreValue);
		Node[] items = {t};
		//addActionsToGameInterface(t);
		return items;
		
	}

	@Override
	public void updateUnitActions(CentralCommand c, GameInterface gi,
			int oreCount[], Unit selectedUnit, ArrayList<Node> toAdd,
			ArrayList<Node> toRemove, Group root) {
		if(selectedUnit!=null && selectedUnit.equals(this)){
			((Text) gi.getMenuActions().get(0)).setText("Ore="+selectedUnit.getHealth());
		}		
	}

}
