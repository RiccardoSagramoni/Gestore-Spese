import java.time.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class ZonaCambioPeriodo { // 00)
	private final VBox contenitore;
	private final DatePicker dataInizioDatePicker;	// 01)
	private final DatePicker dataFineDatePicker;	// 01)
	
	private final InterfacciaGestoreSpese interfacciaGUI;
	private LocalDate dataInizio;
	private LocalDate dataFine;

	public ZonaCambioPeriodo (InterfacciaGestoreSpese interfacciaGUI, ParametriDiConfigurazione configXML) { // 02)
		this.interfacciaGUI = interfacciaGUI;
		dataFine = LocalDate.now(); // 03)
		dataInizio = LocalDate.now().minusDays(configXML.periodoTemporaleIniziale);
		
		dataInizioDatePicker = new DatePicker(dataInizio); // 04)
		dataFineDatePicker = new DatePicker(dataFine);
		
		Button button = new Button("CAMBIA PERIODO"); // 05)
		configuraButton(button, configXML);
		
		Label labelDataInizio = new Label("Data Inizio:"); // 05)
		configuraLabel(labelDataInizio);
		VBox.setMargin(labelDataInizio, new Insets(0, 0, 10, 0));
		
		Label labelDataFine = new Label("Data Fine:"); // 05)
		configuraLabel(labelDataFine);
		VBox.setMargin(labelDataFine, new Insets(30, 0, 10, 0));
		
		contenitore = new VBox(labelDataInizio, dataInizioDatePicker, labelDataFine, 
								dataFineDatePicker, button); // 06)
		contenitore.setAlignment(Pos.TOP_CENTER);
	}

	private void configuraButton(Button button, ParametriDiConfigurazione config) { // 07)
		button.setOnAction((ActionEvent ev)-> {
			dataInizio = dataInizioDatePicker.getValue();
			dataFine = dataFineDatePicker.getValue();
			interfacciaGUI.getGestoreSpese().aggiornaDatiSpeseGUI();
			interfacciaGUI.getGestoreSpese().inviaLogEvento("CAMBIO PERIODO");
		});
		button.setOnMouseEntered((MouseEvent ev)-> interfacciaGUI.getGestoreSpese().setCursorHand()); // 07b)
		button.setOnMouseExited((MouseEvent ev)-> interfacciaGUI.getGestoreSpese().setCursorDefault());
		
		button.setStyle("-fx-padding: 5px 20px; -fx-text-align: center; "
				+ "-fx-font-size: 12pt; -fx-font-family: sans-serif; "
				+ "-fx-text-fill: white;  -fx-font-weight: bold;"
				+ "-fx-background-color: " + config.coloreCambiaPeriodo.toString().substring(2));
		button.setPrefHeight(50);
		
		VBox.setMargin(button, new Insets(30, 0, 0, 0));
	}
	
	private void configuraLabel(Label label) {
		label.setStyle("-fx-font-size: 12pt; -fx-text-fill: black; -fx-font-weight: bold;"
				+ "-fx-font-family: sans-serif;");
	}
	
	public Node getGUI() { // 08)
		return contenitore;
	}
	
	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataInizio (LocalDate dataInizioPeriodo) {
		dataInizio = dataInizioPeriodo;
		dataInizioDatePicker.setValue(dataInizioPeriodo);
	}

	public void setDataFine (LocalDate dataFinePeriodo) {
		dataFine = dataFinePeriodo;
		dataFineDatePicker.setValue(dataFinePeriodo);
	}
}

/* Note:
	00) ZonaCambioPeriodo e' la classe che gestisce il lato frontend del cambio del periodo
		di analisi
	01) DatePicker e' un elemento di controllo che permette la selezione di una data
		utilizzando una GUi user-friendly simile ad un calendario
		https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/DatePicker.html
	02) Costruisce e inizializza la zona di cambio del periodo
	03) Il periodo iniziale di analisi e' configurabile dall'utente attraverso il file
		di configurazione XML. Si assume come data di fine periodo la data corrente.
	04) I DatePicker vengono inzializzati con le date del periodo
	05) Configurazione degli elementi grafici della classe (il pulsante e le due etichette)
	06) Inserimento nel nodo principale della classe di tutti gli elementi grafici da mostrare
	07) Configura il pulsante sia dal punto di vista grafico sia comportamentale (se premuto causa 
		l'aggiornamento della GUI con i dati delle spese del periodo selezionato e invia
		un log al server log; se il puntatore del mouse vi passa sopra, il puntatore
		default viene sostituito da uno a forma di mano)
	07b) MouseEvent e' la classe usata per memorizzare le informazioni relative agli
		eventi causati dal mouse
		https://docs.oracle.com/javafx/2/api/javafx/scene/input/MouseEvent.html
	08) Restituisce il nodo grafic principale della classe, cos= da poter essere aggiunto
		alla GUI
*/