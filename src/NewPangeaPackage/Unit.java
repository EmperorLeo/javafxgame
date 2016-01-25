// This entire file is part of my masterpiece.
// Leo Feldman

package NewPangeaPackage;

import java.util.ArrayList;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;

public interface Unit {
	
	abstract int getHealth();
	
	abstract void setHealth(int h);
	
	abstract String getName();
	
	abstract Bounds getBoundsInParent();
		
	abstract void setOpacity(double arg0);
	
	abstract Node[] updateGameInterface(GameInterface gi);
	
	abstract void updateUnitActions(CentralCommand c, GameInterface gi, int[] oreCount, Unit selectedUnit, ArrayList<Node> toAdd, ArrayList<Node> toRemove, Group root);
	
}
