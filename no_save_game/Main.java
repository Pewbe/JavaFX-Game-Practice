package no_save_game;

import java.util.*;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class Main extends Application{
	public static void main(String[] args) {
		launch( args );
	}

	String name;
	Thread stateCheck = new Thread( new Task() );

	public void start( Stage stg ){
		SetName setname = new SetName();
		name = setname.execute();

		Scene sc = new Scene( getPane(), 500, 500 );
		stateCheck.setDaemon( true );
		
		stg.setScene( sc );
		stg.show();
		stateCheck.start();
	}
	
	Rectangle rectEyeR, rectEyeL, rectBody;
	TextField txtFull, txtFun, txtMoney, txtFood;
	Button btnMeal, btnTalk, btnWork, btnShop, btnTest;
	Label lbState, lbFull, lbFun, lbMoney, lbFood;
	TextArea txtaTalk;
	Integer full, fun, money, food;
	
	public Pane getPane(){
		full = 100;//배부름
		fun = 100;//재미
		money = 0;//돈
		food = 5;//가진 밥
		
		rectBody = new Rectangle( 290, 255, 140, 120 );
		rectEyeR = new Rectangle( 310, 285, 10, 50 );
		rectEyeL = new Rectangle( 340, 285, 10, 50 );
		
		txtFull = new TextField();
		txtFun = new TextField();
		txtMoney = new TextField();
		txtFood = new TextField();
		lbState = new Label("상태");
		
		btnMeal = new Button( "밥" );
		btnTalk = new Button( "대화" );
		btnWork = new Button( "돈벌기" );
		btnShop = new Button( "상점" );
		btnTest = new Button( "테스트(상태-1)" );
		
		lbFull = new Label("배부름");
		lbFun = new Label("재미");
		lbMoney = new Label("돈");
		lbFood = new Label("밥");
		
		Label lblName = new Label( name );
		ToolBar tool = new ToolBar( lblName, btnMeal, btnTalk, btnWork, btnShop, new Label("이름: "), lbState, btnTest );
		txtaTalk = new TextArea();
		AnchorPane apane = new AnchorPane( rectBody, rectEyeR, rectEyeL, txtaTalk, txtFull, txtFun, txtMoney, lbFull, lbFun, lbMoney, lbFood, txtFood );
		BorderPane bpane = new BorderPane();
		int red, green, blue;

		red = (int)(Math.random()*256);
		green = (int)(Math.random()*256);
		blue = (int)(Math.random()*256);
		rectBody.setFill( Color.rgb( red, green, blue) );
		rectEyeR.setFill( Color.BLACK );
		rectEyeL.setFill( Color.BLACK );
		
		txtFull.setEditable( false );
		txtFun.setEditable( false );
		txtMoney.setEditable( false );
		txtaTalk.setEditable( false );
		txtFood.setEditable( false );
		
		txtFull.setText( full.toString() );
		txtFun.setText( fun.toString() );
		txtMoney.setText( money.toString() );
		txtFood.setText( food.toString() );
		
		txtFull.setLayoutX( 80 );
		txtFull.setLayoutY( 280 );
		txtFun.setLayoutX( 80 );
		txtFun.setLayoutY( 320 );
		txtMoney.setLayoutX( 80 );
		txtMoney.setLayoutY( 360 );
		txtFood.setLayoutX( 80 );
		txtFood.setLayoutY( 240 );
		
		lbFull.setLayoutX( 25 );
		lbFull.setLayoutY( 285 );
		lbFun.setLayoutX( 25 );
		lbFun.setLayoutY( 325 );
		lbMoney.setLayoutX( 25 );
		lbMoney.setLayoutY( 365 );
		lbFood.setLayoutX( 25 );
		lbFood.setLayoutY( 245 );
		
		lbState.setTextFill( Color.YELLOWGREEN );
		lblName.setTextFill( Color.CADETBLUE );
		bpane.setTop( tool );
		bpane.setCenter( apane );
		
		btnMeal.setOnAction( ev -> btnMealHandler() );
		btnTalk.setOnAction( ev -> btnTalkHandler() );
		btnWork.setOnAction( ev -> money++ );
		btnTest.setOnAction( ev -> {
			full--;
			fun--;
		});
		
		return bpane;
	}
	public void btnMealHandler() {
		if( Integer.parseInt( txtFull.getText() ) >= 100 )
			fulledAlert();
		else {
			GiveFood givefood = new GiveFood( food );
			int ateFood = givefood.execute();
			food -= ateFood;
			full += ateFood;
			txtFood.setText( food.toString() );
		}
	}
	public void btnTalkHandler() {
		Talk talk = new Talk();
		talk.dialogList();
		
		int rand = (int)(Math.random()*(talk.dialog.size()));
		
		txtaTalk.appendText( talk.dialog.get(rand) );
	}
	public void fulledAlert() {
		Alert fulled = new Alert( AlertType.ERROR );
		
		fulled.setHeaderText(name + "은(는)이미 충분히 배부릅니다.");
		fulled.showAndWait();
	}
	class Task implements Runnable{
		boolean isTextSetted;
		
		public void run() {
			while( true ){
				if( (full <= 70 && full > 30) && (fun <= 70 && fun > 30) ) {
					if( !isTextSetted ) {
						Platform.runLater( () -> {
							lbState.setText( "보통" );
							lbState.setTextFill( Color.YELLOW );
						});
					
						txtaTalk.appendText("현재" + name +"의 상태가 보통입니다.\n");
						isTextSetted = true;
					}
				}
				if( full <= 30 && fun <= 30 ) {
					if( !isTextSetted ) {
						Platform.runLater( () -> {
							lbState.setText( "나쁨" );
							lbState.setTextFill( Color.CRIMSON );
						});

						txtaTalk.appendText( name + "의 상태가 나쁩니다! 밥을 주거나 놀아줘서 상태를 화복시켜주세요.\n");
						isTextSetted = true;
					}
				}
				txtFull.setText( full.toString() );
				txtFun.setText( fun.toString() );
				txtMoney.setText( money.toString() );
				txtFood.setText( food.toString() );

				try { Thread.sleep( 500 ); } catch (InterruptedException e){}
			}
		}
	}
}