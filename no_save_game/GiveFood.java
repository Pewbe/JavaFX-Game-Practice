package no_save_game;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class GiveFood {
	Stage stg;
	int haveFood;
	
	public GiveFood( int haveFood ) {
		this.haveFood = haveFood;
		Scene sc = new Scene( getPane() );
		
		stg = new Stage( StageStyle.UTILITY );
		stg.setScene( sc );
	}
	
	TextField giveFood;
	int food;
	
	Pane getPane() {
		Button ok = new Button("확인");
		giveFood = new TextField("1");
		ToolBar tool = new ToolBar( giveFood, ok );
		
		ok.setOnAction( ev -> okHandler() );
		HBox hbox = new HBox( tool );
		
		return hbox;
	}
	public void okHandler() {
		if( isStringInt( giveFood.getText() ) == false || Integer.parseInt( giveFood.getText() ) <= 0 || Integer.parseInt( giveFood.getText() ) > haveFood ) {
			if( isStringInt( giveFood.getText() ) == false ) {
				notIntAlert();
				return;
			}
			if( Integer.parseInt( giveFood.getText() ) <= 0 )
				tooSmallAlert();
			if( Integer.parseInt( giveFood.getText() ) > haveFood )
				tooMuchAlert();
		}
		else {
			food = Integer.parseInt( giveFood.getText() );
			stg.close();
		}
	}
	public boolean isStringInt( String str ) {
		try {
			Integer.parseInt( str );
			return true;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}
	public int execute() {
		stg.showAndWait();
		return food;
	}
	public void tooSmallAlert() {
		Alert tooSmall = new Alert( AlertType.ERROR );
		
		tooSmall.setHeaderText("1이상은 먹여야 합니다.");
		tooSmall.showAndWait();
	}
	public void tooMuchAlert() {
		Alert tooMuch = new Alert( AlertType.ERROR );
		
		tooMuch.setHeaderText("밥이 부족합니다. 현재 " + haveFood + "개의 밥을 가지고 있습니다.");
		tooMuch.showAndWait();
	}
	public void notIntAlert() {
		Alert notInt = new Alert( AlertType.ERROR );
		
		notInt.setHeaderText("숫자만 입력할 수 있습니다.");
		notInt.showAndWait();
	}
}
