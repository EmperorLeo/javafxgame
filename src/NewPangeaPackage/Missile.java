package NewPangeaPackage;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Missile extends ImageView {
	
	private NumanFighter target;
	private Point2D vel;
	private int damage;
	
	private static final int SPEED = 3;
	
	public Missile(double x, double y, NumanFighter nf, int d){
		super(new Image("images/missile.png"));
		this.setX(x);
		this.setY(y);
		vel = new Point2D(0,0);
		target = nf;
		damage = d;
	}
	public NumanFighter getTarget(){
		return target;
	}
	public int getDamage(){
		return damage;
	}
	public void updateVelocity(){
		double diffX = this.target.getX() - this.getX();
		double diffY = this.target.getY() - this.getY();
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
	public double getVelocityX(){
		return vel.getX();
	}
	public double getVelocityY(){
		return vel.getY();
	}
}
