// This entire file is part of my masterpiece.
// Leo Feldman


package NewPangeaPackage;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class EscapeNewPangea {
	
	private static final int length = 720;
	private static final int startToSpawn = 3600;
	private static final int width = 1080;
	private CentralCommand centralCommand;
	private GameInterface gameInterface;
	private boolean gameOver;
	private int gameTimer;
	private int[] oreCount;
	private ArrayList<IronOre> oreFields;
	private Group root;
	private Unit selectedUnit;
	private boolean stopLeftClick = false;
			
	private void addActionsToGameInterface(Node... actions){
		for(int i=0;i<actions.length;i++){
			actions[i].setOnMouseClicked(e -> selectAction(e));
			gameInterface.add(actions[i]);
			root.getChildren().add(actions[i]);
		}
	}
	
	private void addMouseListenersToUnits(Node... units){
		for(int i=0;i<units.length;i++){
			units[i].setOnMouseClicked(e -> selectUnit(e));
		}
	}
	
	private void cancelWorker(){
		if(centralCommand.isProducing()){
			centralCommand.removeWorker();
			centralCommand.setProgress(0);
			oreCount[0]+=100;
		}
	}
	
	private void createResetButton(String text){
		Button b = new Button(text);
		b.setMinWidth(200);
		b.setMinHeight(50);
		b.setLayoutX(440);
		b.setLayoutY(400);
		b.setOnMouseClicked(e -> setToBeginning());
		root.getChildren().add(b);
	}
	
	public static boolean doesCollide(Unit w, Unit u){
		return u!=null && w.getBoundsInParent().intersects(u.getBoundsInParent());
	}
	
	private void handleKeyInput(KeyEvent e){
		if(e.getCode().getName().equals("Back Slash")){
			oreCount[0]+=200;
		}
		else if(selectedUnit!=null){
			if(selectedUnit instanceof CentralCommand){
				if(e.getCode().getName().toLowerCase().equals("w")){
					makeWorker();
				}
				if(e.getCode().getName().equals("Esc")){
					cancelWorker();
				}
				setUpActions();
			}
			if(selectedUnit instanceof Worker){
				if(e.getCode().getName().toLowerCase().equals("g")){
					gameInterface.getMenuActions().get(0).setOpacity(0.5);
				}
				if(e.getCode().getName().toLowerCase().equals("b")){
					gameInterface.getMenuActions().get(1).setOpacity(0.5);
				}
			}
		}
	}
	
	private void handleMouseInput(MouseEvent e){
		if(selectedUnit!=null && (selectedUnit.getName().equals("Worker") && !stopLeftClick)){
			if(workerOrderedToLocation(e.getButton())){
				((Worker) selectedUnit).setDestination(e.getSceneX(),e.getSceneY());
				((Worker) selectedUnit).doAngleMath();
				((Worker) selectedUnit).setBuildOnArrival(false);
				gameInterface.getMenuActions().get(0).setOpacity(1);
			}
			if(workerOrderedToBuildAtLocation(e.getButton())){
				updatePath(e.getSceneX(), e.getSceneY(), (Worker) selectedUnit, null);
				((Worker) selectedUnit).setBuildOnArrival(true);
				gameInterface.getMenuActions().get(1).setOpacity(1);
			}
		}
		stopLeftClick = false;
	}
	
	public Scene initialize() {
		root = new Group();
		setToBeginning();
		Scene scene = new Scene(root, width,length,Color.WHITE);
		scene.setOnMouseClicked(e -> handleMouseInput(e));
		scene.setOnKeyReleased(e -> handleKeyInput(e));
		return scene;
	}
	
	private void loseConditions(){
		gameOver = true;
		createResetButton("You lose! Restart?");
	}
	
	private void makeWorker(){
		if(oreCount[0]>=100 && centralCommand.getQueue().size()<6){
			centralCommand.addWorker();
			oreCount[0]-=100;
		}
	}
	
	private void selectAction(MouseEvent e){
		if(e.getSource() instanceof BuildWorker){
			makeWorker();
			setUpActions();
		}
		
		else if(e.getSource() instanceof CancelWorker){
			cancelWorker();
			setUpActions();
		}
		
		else if(e.getSource() instanceof GoTo){
			((GoTo) e.getSource()).setOpacity(.5);
			gameInterface.getMenuActions().get(1).setOpacity(1);
			stopLeftClick = true;
		}
		
		else if(e.getSource() instanceof ConstructTurret && oreCount[0]>=Turret.COST){
			((ConstructTurret) e.getSource()).setOpacity(.5);
			gameInterface.getMenuActions().get(0).setOpacity(1);
			stopLeftClick = true;
		}
		
		else if(e.getSource() instanceof UpgradeAtk && oreCount[0]>=100){
			((Turret) selectedUnit).upgradeAttack();
			oreCount[0]-=100;
		}
		
		else if(e.getSource() instanceof UpgradeDef && oreCount[0]>=40){
			((Turret) selectedUnit).upgradeDefense();
			oreCount[0]-=40;
		}
	}
	
	private void selectUnit(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY){
			if(selectedUnit!=null){
				selectedUnit.setOpacity(1);
			}
			selectedUnit = (Unit) e.getSource();
			selectedUnit.setOpacity(0.5);
			setUpActions();
		}
		if(e.getButton() == MouseButton.SECONDARY &&
				selectedUnit!=null &&
				selectedUnit instanceof Worker){
			((Worker) selectedUnit).setDestUnit((Unit) e.getSource());
		}
	}
	
	private void setToBeginning(){
		root.getChildren().clear();
		oreCount = new int[1];
		oreCount[0]=100;
		gameOver = false;
		selectedUnit = null;
		centralCommand = new CentralCommand();
		Worker worker1 = new Worker(480,606);
		Worker worker2 = new Worker(585,606);
		addMouseListenersToUnits(centralCommand,worker1,worker2);
		oreFields = new ArrayList<IronOre>();
		for(int i=0;i<8;i++){
			oreFields.add(new IronOre(130+(i*110),670));
			oreFields.get(i).setOnMouseClicked(e -> selectUnit(e));
		}
		gameInterface = new GameInterface();
		HBox hb = new HBox();
		hb.getChildren().addAll(new ImageView(new Image("images/ironingot.png")), new Text(""+oreCount[0]));
		((ImageView) hb.getChildren().get(0)).setFitWidth(15);
		((ImageView) hb.getChildren().get(0)).setFitHeight(15);

		ObservableList<Node> gameItems = root.getChildren();
		gameItems.addAll(centralCommand,worker1,worker2,gameInterface,centralCommand.getProgressBar(),hb);
		for(IronOre ore:oreFields){
			gameItems.add(ore);
		}
		gameTimer = 0;
	}
	
	private void setUpActions(){
		for(Node n:gameInterface.getMenuActions()){
			root.getChildren().remove(n);
		}
		gameInterface.wipeCommands();
		
		if(selectedUnit==null){
			return;
		}
		
		Node[] doActionsTo = selectedUnit.updateGameInterface(gameInterface);
		addActionsToGameInterface(doActionsTo);
		
	}
	
	public KeyFrame start(){
		return new KeyFrame(Duration.millis(1000/60),e -> updateUnits());
	}
	
	public static void updatePath(double x, double y, Worker u, Unit v){
		u.setDestination(x,y);
		u.setDestUnit(v);
		u.doAngleMath();
	}
	
	private void updateUnits(){
		ArrayList<Node> toAdd = new ArrayList<Node>();
		ArrayList<Node> toRemove = new ArrayList<Node>();
		for(Node n:root.getChildren()){
			if(n instanceof Unit){
				((Unit) n).updateUnitActions(centralCommand, gameInterface, oreCount, selectedUnit, toAdd, toRemove, root);
				if(((Unit) n).getHealth()<=0){
					toRemove.add(n);
					if(n.equals(centralCommand)){
						toRemove.add(centralCommand.getProgressBar());
					}
				}
			}
			
			if(n instanceof Missile){
				Missile m = (Missile) n;
				if(m.getTarget().getHealth()<=0){
					toRemove.add(n);
				}
				if(m.getBoundsInParent().intersects(m.getTarget().getBoundsInParent())){
					m.getTarget().setHealth(m.getTarget().getHealth()-m.getDamage());
					toRemove.add(n);
				}
				else{
					m.updateVelocity();
					m.setX(m.getX()+m.getVelocityX());
					m.setY(m.getY()+m.getVelocityY());
				}
			}
			
			if(n instanceof HBox && (((HBox) n).getChildren().get(1) instanceof Text)){
				((Text) ((HBox) n).getChildren().get(1)).setText(""+oreCount[0]);
			}
		}
		if(gameTimer>startToSpawn && Math.random()<(Math.pow(gameTimer-startToSpawn,.12)/900.0)){ //magical balance reasons :)
			NumanFighter nf = new NumanFighter(720*Math.random()+100);
			nf.setTarget(root.getChildren());
			nf.setVelocity();
			toAdd.add(nf);
		}
		for(Node n:toAdd){
			if(n instanceof Unit){
				addMouseListenersToUnits(n);
			}
			root.getChildren().add(n);
		}
		for(Node n:toRemove){
			root.getChildren().remove(n);
			if(selectedUnit!=null && selectedUnit.equals(n)){
				selectedUnit = null;
				setUpActions();
			}
			if(n instanceof CentralCommand){
				centralCommand = null;
			}
		}
		gameTimer+=1;
		if(!gameOver && oreCount[0]>=2000){
			winConditions();
		}
		if(!gameOver && (centralCommand==null || centralCommand.getHealth()<=0)){
			loseConditions();
		}
	}
	
	private void winConditions(){
		gameOver = true;
		createResetButton("You win. Restart?");
	}
		
	private boolean workerOrderedToBuildAtLocation(MouseButton mb){
		return (mb == MouseButton.PRIMARY) && (gameInterface.getMenuActions().get(1).getOpacity() == .5);
	}
	
	private boolean workerOrderedToLocation(MouseButton mb){
		return (mb == MouseButton.SECONDARY) ||
				((mb == MouseButton.PRIMARY) && (gameInterface.getMenuActions().get(0).getOpacity() == .5));
	}

}
