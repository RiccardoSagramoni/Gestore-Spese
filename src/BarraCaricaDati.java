import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class BarraCaricaDati { // 00)
	private static final String LABEL_CARICA = "CARICA DATI";
	
	private String nomeUtente; // 01)
	
	private final InterfacciaGestoreSpese interfacciaGUI;
	
	private final AnchorPane contenitore; // 01b)
	private final TextField textFieldNomeUtente;

	public BarraCaricaDati (InterfacciaGestoreSpese interfacciaGUI, ParametriDiConfigurazione configXML) { // 02)
		this.interfacciaGUI = interfacciaGUI;
		
		Label titolo = new Label("Nome Utente"); // 02)
		titolo.setStyle("-fx-font-family: sans-serif; -fx-font-weight: bold; -fx-text-fill: blue;");

		textFieldNomeUtente = new TextField(); // 03)
		textFieldNomeUtente.setPrefSize(300, 15); textFieldNomeUtente.setMaxSize(300, 15);
		textFieldNomeUtente.setStyle("-fx-font-size: 12pt;");
		nomeUtente = "";
		
		Button caricaDati = new Button(LABEL_CARICA); // 04)
		caricaDati.setOnAction((ActionEvent ev)-> {	// 04)
			nomeUtente = textFieldNomeUtente.getText();
			this.interfacciaGUI.getGestoreSpese().aggiornaDatiSpeseGUI();
			this.interfacciaGUI.getGestoreSpese().inviaLogEvento("CARICA");
		});
		caricaDati.setOnMouseEntered((MouseEvent ev)->  // 04b)
				this.interfacciaGUI.getGestoreSpese().setCursorHand());
		caricaDati.setOnMouseExited((MouseEvent ev)-> // 04b)
				this.interfacciaGUI.getGestoreSpese().setCursorDefault());
		caricaDati.setStyle("-fx-padding: 5px 20px; -fx-border-radius: 15px;"
				+ "-fx-text-align: center; -fx-font-size: 12pt; -fx-font-family: sans-serif; "
				+ "-fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: blue");
				
		contenitore = new AnchorPane(titolo, textFieldNomeUtente, caricaDati); // 05)
		AnchorPane.setRightAnchor(caricaDati, 0.0); AnchorPane.setTopAnchor(caricaDati, 12.0);
		AnchorPane.setLeftAnchor(textFieldNomeUtente, 0.0); AnchorPane.setTopAnchor(textFieldNomeUtente, 12.0);
		AnchorPane.setLeftAnchor(titolo, 0.0); AnchorPane.setTopAnchor(titolo, -7.0);
		contenitore.setPrefWidth(530);
		contenitore.setStyle("-fx-padding: 10px");
	}

	public Node getGUI() { // 06)
		return contenitore;
	}
	
	public String getNomeUtente() {
		return nomeUtente;
	}
	
	public void setNomeUtente(String username) { // 07)
		nomeUtente = username;
		textFieldNomeUtente.setText(nomeUtente);
	}
}

/* Note:
	00) BarraCaricaDati gestisce l'area di input e il relativo pulsanti, attraverso cui l'utente puo' 
		inserire il proprio username e caricare i suoi dati sulla GUI
	01) Contenuto backend della TextArea: contiene il nome utente inserito e validato
	01b) AnchorPane e' un contenitore che permette di ancorare i nodi figli a distanze fisse dai bordi
		https://docs.oracle.com/javafx/2/api/javafx/scene/layout/AnchorPane.html
	02) Crea un'etichetta per indicare all'utente cosa inserire nel campo di input presente
	03) Crea il campo di input dove l'utente dovrebbe inserire il proprio nome utente
	04) Crea il pulsante per caricare i dati sulla GUI. Configura l'handler dell'evento di selezione
		del pulsante, provocando:
		- l'aggiornamento della variabile backend che contiene il nome utente
		- l'aggiornamento della GUI con i nuovi dati
		- l'invio di un messaggio di log per segnalare la selezione del pulsante
	04b) Configura effetto grafico di selezione del pulsante (puntatore a forma di mano)
		MouseEvent registra l'evento di interazione dell'utente con il mouse
		https://docs.oracle.com/javafx/2/api/javafx/scene/input/MouseEvent.html
	05) Crea il contenitore di tutti i precedenti elementi grafici e configura la posizione al suo interno 
		di ogni elemento
	06) Restituisce l'elemento grafico principale (il contenitore di tutti gli elementi grafici), così che
		possa essere mostrato sull'interfaccia grafica
	07) Imposta il nome utente
*/