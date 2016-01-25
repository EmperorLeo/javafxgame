// This entire file is part of my masterpiece.
// Leo Feldman

package NewPangeaPackage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class CentralCommand extends ImageView implements Unit {
	
	private int health;
	private int timer;
	private Queue<Worker> q;
	private final String name = "CentralCommand";
	private ProgressBar pb;

	
	public CentralCommand(){
		super(new Image("images/centralcommand.gif"));
		this.setFitWidth(100);
		this.setFitHeight(100);
		this.setX(490);
		this.setY(500);
		this.health = 2000;
		q = new LinkedList<Worker>();
		pb = new ProgressBar(0);
		pb.setLayoutX(490);
		pb.setLayoutY(480);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int h) {
		health = h;
	}
	
	public boolean isProducing(){
		return q.size()>0;
	}
	
	public int getWorkerTime(){
		return timer;
	}
	
	public void setWorkerTime(int t){
		timer = t;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void addWorker() {
		if(q.size()<6){
			q.add(new Worker(527.5,600));
		}
	}
	public void removeWorker() {
		if(q.size()>0){
			q.poll();
			timer = 0;
		}
	}
	public Queue<Worker> getQueue(){
		return q;
	}
	public void setProgress(double d){
		pb.setProgress(d);
	}
	public ProgressBar getProgressBar(){
		return pb;
	}
	public Node[] updateGameInterface(GameInterface gi){
		Node[] items = new Node[2+q.size()];
		Text t = new Text(gi.getMenu().get(7).getX(),gi.getMenu().get(7).getY()+(Action.SIZE/2),"Health= "+this.getHealth());
		t.setWrappingWidth(Action.SIZE); //have to add text first to access it without running into bugs
		BuildWorker b = new BuildWorker(gi.getMenu().get(8).getX(),gi.getMenu().get(8).getY());
		//addActionsToGameInterface(t,b);
		items[0] = t;
		items[1] = b;
		for(int i=0;i<q.size();i++){
			CancelWorker w = new CancelWorker(gi.getMenu().get(i).getX(),gi.getMenu().get(i).getY());
			items[2+i] = w;
			//addActionsToGameInterface(w);
		}
		return items;

	}

	public void updateUnitActions(CentralCommand c, GameInterface gi,
			int oreCount[], Unit selectedUnit, ArrayList<Node> toAdd, ArrayList<Node> toRemove, Group root) {
		if(this.isProducing()){
			this.setWorkerTime(this.getWorkerTime()+1);
			if(this.getWorkerTime() == 600){
				Worker w = this.getQueue().poll();
				if(selectedUnit instanceof CentralCommand){
					int s = this.getQueue().size();
					toRemove.add(gi.getMenuActions().get(s+2));
				}
				//w.setOnMouseClicked(e -> selectUnit(e));
				toAdd.add(w);
				this.setWorkerTime(0);
			}
			this.setProgress(((double) this.getWorkerTime())/600);
		}
		if(selectedUnit!=null && this.equals(selectedUnit)){
			((Text) gi.getMenuActions().get(0)).setText("Health= "+this.getHealth());
		}
	}
	
}
