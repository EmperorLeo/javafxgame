// This entire file is part of my masterpiece.
// Leo Feldman

package NewPangeaPackage;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class NumanFighter extends ImageView implements Unit {
	
	private int health;
	private Unit targetAcquired;
	private Point2D vel;
	
	public static final double DPF = 75.0/60.0;
	public static final double SPEED = .5;
	
	public NumanFighter(double x){
		super(new Image("images/numan.png"));
		this.setFitWidth(40);
		this.setFitHeight(40);
		this.setX(x);
		this.setY(0);
		this.health = 250;
		this.vel = new Point2D(0,0);
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int h) {
		health = h;
	}
	
	public void setTarget(ObservableList<Node> gameItems){
		double minDist = Integer.MAX_VALUE;
		for(Node n:gameItems){
			if((n instanceof Turret || n instanceof CentralCommand)){
				double dist = distance(this.getX(),this.getY(),((ImageView) n).getX(),((ImageView) n).getY());
				if(dist<minDist){
					minDist = dist;
					targetAcquired = (Unit) n;
				}
			}
		}
	}
	
	public double distance(double x, double y, double targetX, double targetY){
		return Math.sqrt(Math.pow(targetX-x, 2)+Math.pow(targetY-y,2));
	}
	
	public void setVelocity(){
		if(targetAcquired==null){
			vel = new Point2D(0,0);
		}
		else{
			double diffX = ((ImageView) targetAcquired).getX() - this.getX();
			double diffY = ((ImageView) targetAcquired).getY() - this.getY();
			double angle = Math.atan(diffY/diffX);
			int factor;
			if(diffX<0){
				factor = -1;
			}
			else {
				factor = 1;
			}
			vel = new Point2D((SPEED*Math.cos(angle)*factor),SPEED*Math.sin(angle)*factor);
		}
	}
	public double getVelocityX(){
		return vel.getX();
	}
	public double getVelocityY(){
		return vel.getY();
	}
	
	public Unit getTarget(){
		return targetAcquired;
	}

	public String getName() {
		return "Numan Fighter";
	}

	public Node[] updateGameInterface(GameInterface gi) {
		Text t = new Text(gi.getMenu().get(4).getX(),gi.getMenu().get(4).getY()+(Action.SIZE/2),"Health= "+health);
		t.setWrappingWidth(Action.SIZE);
		Node[] items = {t};
		return items;
		//addActionsToGameInterface(t);
	}

	@Override
	public void updateUnitActions(CentralCommand c, GameInterface gi,
			int oreCount[], Unit selectedUnit, ArrayList<Node> toAdd,
			ArrayList<Node> toRemove, Group root) {
		if(selectedUnit!=null && this.equals(selectedUnit)){
			((Text) gi.getMenuActions().get(0)).setText("Health= "+selectedUnit.getHealth());
		}
		if(EscapeNewPangea.doesCollide(this, this.getTarget())){
			this.getTarget().setHealth(this.getTarget().getHealth()-1);
		}
		else{
			this.setX(this.getX()+this.getVelocityX());
			this.setY(this.getY()+this.getVelocityY());
		}
		
		if(this.getTarget()!=null && this.getTarget().getHealth()<=0){
			this.setTarget(root.getChildren());
			this.setVelocity();
		}
		
	}

}
