package NewPangeaPackage;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GameInterface extends ImageView {
	
	private ArrayList<Node> menuActions;
	private Action command;
	private ArrayList<Point2D> menu;
		
	public GameInterface(){
		super(new Image("images/gameinterface.gif"));
		this.setX(880);
		this.setY(0);
		this.setFitWidth(200);
		this.setFitHeight(200);
		this.menuActions = new ArrayList<>();
		this.menu = new ArrayList<>();
		menu.add(new Point2D(880,0));
		menu.add(new Point2D(880+(200.0/3.0),0));
		menu.add(new Point2D(880+(400.0/3.0),0));
		menu.add(new Point2D(880,(200.0/3.0)));
		menu.add(new Point2D(880+(200.0/3.0),(200.0/3.0)));
		menu.add(new Point2D(880+(400.0/3.0),(200.0/3.0)));
		menu.add(new Point2D(880,(400.0/3.0)));
		menu.add(new Point2D(880+(200.0/3.0),(400.0/3.0)));
		menu.add(new Point2D(880+(400.0/3.0),(400.0/3.0)));
	}
	
	public void add(Node a){
		menuActions.add(a);
	}
	public void remove(){
		menuActions.remove(0);
	}
	public ArrayList<Node> getMenuActions(){
		return menuActions;
	}
	public void wipeCommands(){
		menuActions = new ArrayList<>();
	}
	public ArrayList<Point2D> getMenu(){
		return menu;
	}
	public void changeCommand(Action c){
		command = c;
	}
	public Action getCommand(){
		return command;
	}
	
}
