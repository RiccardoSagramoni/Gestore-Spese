import java.util.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class FormDiInserimento { // 00)

	private static final String ICONA_INSERIMENTO = "file:../../files/icona_inserisci.png"; // 01)
	
	private final AnchorPane form; // 02)
	private ChoiceBox<String> categoria;
	private TextField importo;
	private DatePicker data;
	private TextField descrizione;


	public FormDiInserimento(ParametriDiConfigurazione configXML) { // 03)
		ImageView immagine = new ImageView(ICONA_INSERIMENTO); // 04)
		immagine.setPreserveRatio(true); 
		immagine.setFitWidth(30);
		AnchorPane.setLeftAnchor(immagine, 10.0); 
		AnchorPane.setTopAnchor(immagine, 25.0);
		
		HBox campiForm = new HBox( // 05)
				creaCampoCategoria(configXML), 
				creaCampoImporto(configXML), 
				creaCampoData(), 
				creaCampoDescrizione(configXML));
		campiForm.setSpacing(10);
		campiForm.setAlignment(Pos.CENTER_RIGHT);
		AnchorPane.setRightAnchor(campiForm, 32.0);
		
		form = new AnchorPane(immagine, campiForm); // 06)
	}

	private Node creaCampoCategoria (ParametriDiConfigurazione configXML) { // 07)
		categoria = new ChoiceBox<>( // 08)
				FXCollections.observableArrayList(Arrays.asList(configXML.categorieSpesa)));
		categoria.setPrefWidth(250);
		categoria.setStyle("-fx-font-size: 12pt; -fx-border: thin; -fx-border-color: grey; "
				+ "-fx-background-color: #" + configXML.coloreRigaInserimento.toString().substring(2) + ";");
		
		Label categoriaLabel = new Label("Categoria");
		categoriaLabel.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold;");
		
		VBox vbox = new VBox(categoriaLabel, categoria);
		vbox.setAlignment(Pos.TOP_LEFT);
		return vbox;
	}
	
	private Node creaCampoImporto (ParametriDiConfigurazione configXML) { // 09)
		importo = new TextField();
		importo.setPrefWidth(100);
		importo.setStyle("-fx-font-size: 12pt; -fx-border: thin; -fx-border-color: grey;"
				+ "-fx-background-color: #" + configXML.coloreRigaInserimento.toString().substring(2) + ";");
		
		Label importoLabel = new Label("Importo");
		importoLabel.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold;");
		
		VBox vbox = new VBox(importoLabel, importo);
		vbox.setAlignment(Pos.TOP_LEFT);
		return vbox;
	}
	
	private Node creaCampoData () { // 10)
		data = new DatePicker(); // 11)
		data.setPrefWidth(200); 
		data.setStyle("-fx-font-size: 12pt; -fx-border: thin; -fx-border-color: grey;");
		
		Label dataLabel = new Label("Data");
		dataLabel.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold;");
		
		VBox vbox = new VBox(dataLabel, data);
		vbox.setAlignment(Pos.TOP_LEFT);
		return vbox;
	}
	
	private Node creaCampoDescrizione (ParametriDiConfigurazione configXML) { // 12)
		descrizione = new TextField();
		descrizione.setPrefWidth(610);
		descrizione.setStyle("-fx-font-size: 12pt; -fx-border: thin; -fx-border-color: grey;"
				+ "-fx-background-color: #" + configXML.coloreRigaInserimento.toString().substring(2) + ";");
		
		Label descrizioneLabel = new Label("Descrizione");
		descrizioneLabel.setStyle("-fx-font-size: 10pt; -fx-font-weight: bold;");
		
		VBox vbox = new VBox(descrizioneLabel, descrizione);
		vbox.setAlignment(Pos.TOP_LEFT);
		return vbox;
	}
	
	
	public VoceSpesa getVoceInserita() { // 13)
		double importoInserito;
		try {
			importoInserito = Double.valueOf(importo.getText()); // 14)
		} catch (NumberFormatException ex) {
			importoInserito = 0.0;
		}
		
		return new VoceSpesa(
				-1, // 15)
				categoria.getValue(), 
				importoInserito,
				data.getValue(), 
				descrizione.getText()
			);
	}
	
	public void setVoceInserita(VoceSpesa spesa) { // 16)
		categoria.getSelectionModel().select(spesa.getCategoria());
		importo.setText(Double.toString(spesa.getImporto()));
		data.setValue(spesa.getData());
		descrizione.setText(spesa.getDescrizione());
		
		if (importo.getText().equals("0.0")) { // 17)
			importo.setText("");
		}
	}
	
	public void svuota() { // 18)
		categoria.getSelectionModel().clearSelection();
		importo.setText("");
		data.setValue(null);
		descrizione.setText("");
	}
	
	public Node getGUI() { // 19)
		return form;
	}
}

/* Note:
	00) FormDiInserimento si occupa della gestione frontend dei campi del form, grazie
		ai quali l'utente puo' inserire una nuova spesa
	01) E' il file contenente l'icona per segnalare all'utente la presenza del form
	02) Sono gli elementi grafici del form il cui riferimento e' necessario per il corretto
		funzionamento dei metodi.

		AnchorPane e' un contenitore che permette di ancorare i nodi figli a distanze fisse dai bordi
		https://docs.oracle.com/javafx/2/api/javafx/scene/layout/AnchorPane.html

	03) Inizializza il form, instanziando i nodi grafici e configurandone l'aspeto grafico
	04) Configurazione dell'icona da posizionare di fianco ai campi del form
	05) Contenitore dei campi del form, che vengono disposti orizzontalmente
	06) I campi del form e l'icona vengono inseriti in un apposito contenitore cosi' da
		poter essere visualizzati sulla GUI applicativa
	07) Crea il campo per la scelta delle categorie con l'apposita etichetta,
		in disposizione verticale
	08) ChoiceBox e' un elemento di controllo usato per permettere la selezione all'utente
		di un unico valore da un insieme limitato di possibili scelte.
		https://docs.oracle.com/javafx/2/api/javafx/scene/control/ChoiceBox.html
		Viene inizializzato con una Observable List costruita a partire dal vettore
		contenente le categorie di spesa selezionabili fornito dai parametri di configurazione XML
	09) Crea il campo per l'inserimento dell'importo della spesa con l'apposita etichetta,
		in disposizione verticale
	10) Crea il campo per la selezione della data della spesa con l'apposita etichetta,
		in disposizione verticale
	11) DatePicker e' un elemento di controllo che permette la selezione di una data
		utilizzando una GUi user-friendly simile ad un calendario
		https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/DatePicker.html
	12) Crea il campo per l'inserimento della descrizione della spesa con l'apposita etichetta,
		in disposizione verticale
	13) Restituisce un oggetto VoceSpesa contenente i valori inseriti dall'utente
	14) Preleva l'importo inserito dall'utente. In caso di fallimento della conversione
		stringa-to-double (ad esempio se il campo e' rimasto vuoto), l'importo
		viene fissato a zero
	15) Poiche' questo oggetto VoceSpesa rappresenta una spesa da inserire e non una
		spesa prelevata dal database, il campo ID non e' significativo e vi viene
		inserito un valore dummy (il valore e' negativo, poiche' per progettazione gli id
		sono numeri positivi)
	16) Imposta i valori del form a partire da una VoceSpesa passata per riferimento
	17) Nel caso in cui l'importo sia zero, il campo testuale viene reso vuoto.
		Questa scelta e' coerente con la scelta del punto 14 di impostare un importo pari
		a zero quando il campo testuale e' vuoto
	18) Svuota il form
	19) Restituisce il nodo grafico principale del form, cosi' da poter essere aggiunta
		alla GUI applicativa
*/