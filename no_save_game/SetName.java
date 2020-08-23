package no_save_game;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class SetName {
	Stage stg;
	String name;
	
	public SetName() {
		Scene sc = new Scene( getPane() );
		
		stg = new Stage( StageStyle.UTILITY );
		stg.setScene( sc );
		stg.setResizable( false );
	}
	
	TextField txtName;

	private Pane getPane() {
		txtName = new TextField();
		Button btnOk = new Button("결정");
		ToolBar tool = new ToolBar( new Label("이름:"), txtName, btnOk );
		HBox hbox = new HBox( tool );
		
		hbox.setPadding( new Insets(5) );
		btnOk.setOnAction( ev -> okHandler() );
		
		return hbox;
	}
	
	public String execute(){
		stg.showAndWait();
		return name;
	}
	
	public void okHandler(){
		if( txtName.getText().isEmpty() )
			noNameAlert();
		else {
			name = txtName.getText();
			stg.close();
		}
	}
	
	public void noNameAlert() {
		Alert alert = new Alert( AlertType.ERROR );
		
		alert.setHeaderText("이름을 입력해주세요.");
		alert.showAndWait();
	}
}
