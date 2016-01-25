// This entire file is part of my masterpiece.
// Leo Feldman

package NewPangeaPackage;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Turret extends ImageView implements Unit {
	private int health;
	private int damage;
	private int ammoCooldown;
	
	public final static int COST=100;
	
	public Turret(double x, double y){
		super(new Image("images/turret.png"));
		health = 300;
		damage = 50;
		this.setX(x);
		this.setY(y);
		this.setFitWidth(50);
		this.setFitHeight(50);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int h) {
		health = h;
	}

	public String getName() {
		return "Turret";
	}
	public void upgradeDefense(){
		setHealth(health+100);
	}
	public void upgradeAttack(){
		damage += 30;
	}
	public int getCooldown(){
		return ammoCooldown;
	}
	public void setCooldown(){
		ammoCooldown++;
	}
	public void resetCooldown(){
		ammoCooldown = 0;
	}
	public NumanFighter findTarget(ObservableList<Node> gameItems){
		int minDist = 250;
		NumanFighter target = null;
		for(Node n:gameItems){
			if(n instanceof NumanFighter
					&& distance(this.getX(),this.getY(),((NumanFighter) n).getX(),((NumanFighter) n).getY())<minDist){
				target = (NumanFighter) n;
			}
		}
		return target;
		
	}
	public double distance(double x, double y, double targetX, double targetY){
		return Math.sqrt(Math.pow(targetX-x, 2)+Math.pow(targetY-y,2));
	}
	public int getDamage(){
		return damage;
	}

	@Override
	public Node[] updateGameInterface(GameInterface gi) {
		UpgradeAtk atk = new UpgradeAtk(gi.getMenu().get(6).getX(),gi.getMenu().get(6).getY());
		UpgradeDef def = new UpgradeDef(gi.getMenu().get(8).getX(),gi.getMenu().get(8).getY());
		Text t = new Text(gi.getMenu().get(4).getX(),gi.getMenu().get(4).getY()+(Action.SIZE/2),"Health= "+health);
		t.setWrappingWidth(Action.SIZE);
		Text atkText = new Text(gi.getMenu().get(0).getX(),gi.getMenu().get(0).getY()+(Action.SIZE/2),"Atk="+damage);
		atkText.setWrappingWidth(Action.SIZE);
		Node[] items = {atk,def,t,atkText};
		//addActionsToGameInterface(atk,def,t,atkText);
		return items;
	}

	@Override
	public void updateUnitActions(CentralCommand c, GameInterface gi,
			int oreCount[], Unit selectedUnit, ArrayList<Node> toAdd,
			ArrayList<Node> toRemove, Group root) {
		if(selectedUnit!=null && selectedUnit.equals(this)){
			((Text) gi.getMenuActions().get(2)).setText("Health= "+selectedUnit.getHealth());
			((Text) gi.getMenuActions().get(3)).setText("Atk="+this.getDamage());
		}
		if(this.getCooldown()==180){
			NumanFighter nf = this.findTarget(root.getChildren());
			if(nf!=null && nf.getHealth()>0){
				toAdd.add(new Missile(this.getX(),this.getY(),nf,this.getDamage()));
			}
			this.resetCooldown();
		}
		this.setCooldown();

		
	}
}
