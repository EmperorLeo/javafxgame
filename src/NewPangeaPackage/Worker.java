// This entire file is part of my masterpiece.
// Leo Feldman

package NewPangeaPackage;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Worker extends ImageView implements Unit {
	private double xVel;
	private double yVel;	
	private boolean hasOre;
	private int health;
	private final String name = "Worker";
	private Point2D dest;
	private Unit destUnit;
	private IronOre oreToMine;
	private boolean buildOnArrival;
	private static final double workerSpeed = .5;

	
	public Worker(double x, double y){
		super(new Image("images/worker.png"));
		this.setFitWidth(25);
		this.setFitHeight(25);
		this.setX(x);
		this.setY(y);
		this.health = 50;
		this.hasOre = false;
		this.buildOnArrival = false;
	}
	
	public boolean getOreStatus(){
		return hasOre;
	}
	
	public double getVelocityX(){
		return xVel;
	}
	public double getVelocityY(){
		return yVel;
	}
	
	public void setVelocityX(double vel){
		xVel = vel;
	}
	
	public void setVelocityY(double vel){
		yVel = vel;
	}
	
	public void setOre(boolean bool){
		hasOre = bool;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int h) {
		health = h;
	}

	public String getName() {
		return name;
	}
	
	public void setDestination(){
		dest = null;
	}
	public void setDestination(double x, double y){
		if(!(x>=855 && y<=200)){
			dest = new Point2D(x,y);
		}
	}
	public Point2D getDestination(){
		return dest;
	}
	public void setDestUnit(Unit u){
		destUnit = u;
	}
	public Unit getDestUnit(){
		return destUnit;
	}
	public void setOreToMine(IronOre ore){
		oreToMine = ore;
	}
	public IronOre getOreToMine(){
		return oreToMine;
	}
	public void setBuildOnArrival(Boolean b){
		buildOnArrival = b;
	}
	
	
	public void doAngleMath(){
		if(this.dest!=null){
			double diffX = this.dest.getX() - this.getX();
			double diffY = this.dest.getY() - this.getY();
			double angle = Math.atan(diffY/diffX);
			int factor;
			if(diffX<0){
				factor = -1;
			}
			else {
				factor = 1;
			}
			this.setVelocityX(workerSpeed*Math.cos(angle)*factor);
			this.setVelocityY(workerSpeed*Math.sin(angle)*factor);
		}
	}

	public boolean getBuildOnArrival() {
		return buildOnArrival;
	}

	public Node[] updateGameInterface(GameInterface gi) {
		GoTo gt = new GoTo(gi.getMenu().get(8).getX(),gi.getMenu().get(8).getY());
		ConstructTurret ct = new ConstructTurret(gi.getMenu().get(6).getX(),gi.getMenu().get(6).getY());
		Text t = new Text(gi.getMenu().get(4).getX(),gi.getMenu().get(4).getY()+(Action.SIZE/2),"Health= "+health);
		t.setWrappingWidth(Action.SIZE);
		Node[] items = {gt,ct,t};
		//addActionsToGameInterface(gt,ct,t);
		return items;

	}

	@Override
	public void updateUnitActions(CentralCommand c, GameInterface gi,
			int oreCount[], Unit selectedUnit, ArrayList<Node> toAdd,
			ArrayList<Node> toRemove, Group root) {
		if(dest!=null){
			this.setX(this.getX()+this.getVelocityX());
			this.setY(this.getY()+this.getVelocityY());
			if(Math.abs(this.getDestination().getX()-this.getX())<1 &&
				Math.abs(this.getDestination().getY()-this.getY())<1){
					if(this.getBuildOnArrival() && oreCount[0]>=Turret.COST){
						Turret t = new Turret(this.getDestination().getX(),this.getDestination().getY());
						//addMouseListenersToUnits(t);
						toAdd.add(t);
						this.setBuildOnArrival(false);
						oreCount[0]-=Turret.COST;
					}
					this.setDestination();
			}
			if(this.getDestUnit()!=null && EscapeNewPangea.doesCollide(this, this.getDestUnit())){
				this.setDestination();
				if(this.getDestUnit() instanceof IronOre){
					if(root.getChildren().contains(this.getDestUnit())){
						if(!this.getOreStatus()){
							this.getDestUnit().setHealth(this.getDestUnit().getHealth()-10);
						}
						this.setOre(true);
						this.setOreToMine((IronOre) this.getDestUnit());
						EscapeNewPangea.updatePath(540,550,this,c);
					}						
					else{
						this.setDestUnit(null);
					}
				}
				else if((this.getDestUnit() instanceof CentralCommand) && this.getOreStatus()){
					EscapeNewPangea.updatePath(this.getOreToMine().getX(),this.getOreToMine().getY(),this,this.getOreToMine());
					this.setOre(false);
					oreCount[0]+=10;
				}
			}

		}
		
	}
}
