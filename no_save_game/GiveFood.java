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
		int toGive = Integer.parseInt( giveFood.getText() );
		
		if( !isStringInt( giveFood.getText() ) || toGive <= 0 || toGive > haveFood ) {
			if( !isStringInt( giveFood.getText() ) ) {
				foodAlert("숫자만 입력할 수 있습니다.");
				return;
			}
			if( toGive <= 0 )
				foodAlert("1이상은 먹여야 합니다.");
			if( toGive > haveFood )
				foodAlert("밥이 부족합니다. 현재 " + haveFood + "개의 밥을 가지고 있습니다.");
		}
		else {
			food = toGive;
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
	public void foodAlert( String content ) {
		Alert tooSmall = new Alert( AlertType.ERROR );
		
		tooSmall.setHeaderText( content );
		tooSmall.showAndWait();
	}
}
