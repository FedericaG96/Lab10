package it.polito.tdp.porto;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.CoAutore;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

public class PortoController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	Author autore1 = boxPrimo.getValue();
    	
    	if(autore1==null)
    		txtResult.setText("Selezionare un autore! ");
    	
    	for(Author ca : model.getCoAutori(autore1)) {
    		txtResult.appendText(ca.toString());
    	}
   
    }
    
    @FXML
    void doPrimo(ActionEvent event) {
    	boxSecondo.getItems().addAll(model.getAuthor());
    	boxSecondo.getItems().removeAll(model.getCoAutori(boxPrimo.getValue()));
    	boxSecondo.getItems().remove(boxPrimo.getValue());
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    	Author autore1 = boxPrimo.getValue();
    	Author autore2 = boxSecondo.getValue();
    	if(autore1==null||autore2==null)
    		txtResult.setText("Selezionare entrambi gli autori! ");
    	
    	txtResult.setText("Lista di articoli tra "+autore1 +" e "+ autore2);
    	for(Paper p: model.trovaCamminoMinimo(autore1, autore2)) {
    		txtResult.appendText(p.toString());
    	}
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
    
    public void setModel (Model model) {
    	this.model = model;
    	boxPrimo.getItems().addAll(model.getAuthor());
    	model.doGrafo();
    }
}
