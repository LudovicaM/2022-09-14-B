/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnComponente"
    private Button btnComponente; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSet"
    private Button btnSet; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML
    void doComponente(ActionEvent event) {
    	Album album = cmbA1.getValue();
    	if (album == null) {
    		txtResult.setText("Selezionare un album dal menu a tendina\n");
    		return;
    	}
    	txtResult.appendText("\nComponente connessa - "+album);
    	txtResult.appendText("\nDimensione componente = "+model.dimConnessa(album)+
    			"\n# Album componente = "+model.getTotaleBrani());
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String durS = txtDurata.getText();
    	if (durS == "") {
    		txtResult.setText("Inserire una durata\n");
    		return;
    	}
    	try {
    		double durata = Double.parseDouble(durS);
    		model.creaGrafo(durata);
    		txtResult.setText("Grafo creato\n");
    		txtResult.appendText("#vertici: "+model.getNumV()+"\n#archi: "+model.getNumE()+"\n\n");
    		
    		cmbA1.getItems().addAll(model.getVertici());
    	}catch (NumberFormatException e) {
    		txtResult.setText("Inserire un valore numerico per la durata\n");
    		return;
    	}
    }

    @FXML
    void doEstraiSet(ActionEvent event) {
    	String dTotS = txtX.getText();
    	if (dTotS == "") {
    		txtResult.setText("Inserire una durata\n");
    		return;
    	}
    	try {
    		double dTot = Double.parseDouble(dTotS);
    		Album a1 = cmbA1.getValue();
    		txtResult.appendText("\n\nSet estratto:\n");
    		for (Album aa : model.ricercaSetMassimo(a1, dTot)) {
    			txtResult.appendText(aa+"\n");
    		}
    	}catch(NumberFormatException e) {
    		txtResult.setText("Inserire un valore numerico per la durata\n");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnComponente != null : "fx:id=\"btnComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSet != null : "fx:id=\"btnSet\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
