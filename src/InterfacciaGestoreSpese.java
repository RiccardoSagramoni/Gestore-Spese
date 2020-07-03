import java.text.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

public class InterfacciaGestoreSpese { // 00)
	
	private final GestoreSpese gestoreSpese;
	private double totaleSpese; // 01)
	
	private final VBox contenitorePrincipale; // 02)
	private final AnchorPane header;
	private final HBox pulsantiTabella;
	private final HBox zonaAnalisi;
	
	private final BarraCaricaDati barraCaricaDati; // 03)
	private final FormDiInserimento formInserimento;
	private final GraficoCategorieDiSpesa grafico;
	private final TabellaSpese tabella;
	private final ZonaCambioPeriodo zonaCambioPeriodo;

	public InterfacciaGestoreSpese (Group root, GestoreSpese gestoreSpese, ParametriDiConfigurazione configXML, 
									String nomeApp) { // 04)
		this.gestoreSpese = gestoreSpese;
		
		header = new AnchorPane(); // 05)
		pulsantiTabella = new HBox();
		zonaAnalisi = new HBox();		
		
		tabella = new TabellaSpese(this, configXML); // 06)
		formInserimento = new FormDiInserimento(configXML);
		grafico = new GraficoCategorieDiSpesa();
		barraCaricaDati = new BarraCaricaDati(this, configXML);
		zonaCambioPeriodo = new ZonaCambioPeriodo(this, configXML);
		
		costruisciHeader(nomeApp); // 07)
		costruisciPulsantiTabella(configXML);
		costruisciZonaAnalisi();
		
		contenitorePrincipale = new VBox(header, tabella.getGUI(), formInserimento.getGUI(), 
				pulsantiTabella, zonaAnalisi); // 08)
		contenitorePrincipale.setAlignment(Pos.TOP_CENTER);
                contenitorePrincipale.setPrefWidth(GestoreSpese.SCENE_WIDTH);
		contenitorePrincipale.setMaxWidth(GestoreSpese.SCENE_WIDTH);
                contenitorePrincipale.setMaxHeight(GestoreSpese.SCENE_HEIGHT);
		contenitorePrincipale.setSpacing(7);
		contenitorePrincipale.setPadding(new Insets(0, 15, 0, 15));
		VBox.setMargin(pulsantiTabella, new Insets(15, 0, 20, 0));
		
		root.getChildren().add(contenitorePrincipale); // 09)
	}
	
	private void costruisciHeader (String nomeApp) { // 10)
		header.getChildren().clear(); 
		
		Label titolo = new Label(nomeApp); // 11)
		titolo.setStyle("-fx-font-size: 28pt; -fx-text-fill: red; -fx-font-style: italic; -fx-font-weight: bold;"
				+ "-fx-font-family: \"Comic Sans MS\", cursive, sans-serif");
		
		header.getChildren().addAll(titolo, barraCaricaDati.getGUI());
		AnchorPane.setLeftAnchor(titolo, 200.0);
		AnchorPane.setRightAnchor(barraCaricaDati.getGUI(), 50.0);
	}
	
	private void costruisciPulsantiTabella (ParametriDiConfigurazione conf) { // 12)
		pulsantiTabella.getChildren().clear();
		pulsantiTabella.setAlignment(Pos.CENTER);
		
		Button inserisci = new Button("INSERISCI"); // 13)
		setDesignPulsante(inserisci, conf.coloreInserisci);
		inserisci.setOnAction((ActionEvent ev)-> {
			gestoreSpese.inserisciNuovaSpesa(formInserimento.getVoceInserita());
			formInserimento.svuota();
			gestoreSpese.inviaLogEvento("INSERISCI");
		});
		inserisci.setOnMouseEntered((MouseEvent ev)-> getGestoreSpese().setCursorHand()); // 13b)
		inserisci.setOnMouseExited((MouseEvent ev)-> getGestoreSpese().setCursorDefault());
		
		Button annulla = new Button("ANNULLA"); // 14)
		setDesignPulsante(annulla, conf.coloreAnnulla);
		annulla.setOnAction((ActionEvent ev)-> {
			formInserimento.svuota(); gestoreSpese.inviaLogEvento("ANNULLA");
		});
		annulla.setOnMouseEntered((MouseEvent ev)-> getGestoreSpese().setCursorHand()); // 13b)
		annulla.setOnMouseExited((MouseEvent ev)-> getGestoreSpese().setCursorDefault());
		
		Button elimina = new Button("ELIMINA"); // 15)
		setDesignPulsante(elimina, conf.coloreElimina);
		elimina.setOnAction((ActionEvent ev)-> {
			tabella.eliminaVoceSelezionata(); gestoreSpese.inviaLogEvento("ELIMINA");
		});
		elimina.setOnMouseEntered((MouseEvent ev)-> getGestoreSpese().setCursorHand()); // 13b)
		elimina.setOnMouseExited((MouseEvent ev)-> getGestoreSpese().setCursorDefault());
		
		pulsantiTabella.getChildren().addAll(inserisci, annulla, elimina);
		pulsantiTabella.setSpacing(50);
	}
	
	private void setDesignPulsante (Button button, Color colore) { // 16)
		button.setStyle("-fx-padding: 5px 15px; -fx-border-radius: 15px;"
				+ "-fx-text-align: center; -fx-font-size: 12pt; -fx-font-family: sans-serif; "
				+ "-fx-text-fill: white;  -fx-font-weight: bold;"
				+ "-fx-background-color: " + colore.toString().replace("0x", "#"));
		button.setPrefSize(130, 35);
	}
	
	private void costruisciZonaAnalisi () { // 17)
		zonaAnalisi.getChildren().clear();
		
		Label titoloContatore = new Label("Totale spese"); // 19)
		titoloContatore.setFont(new Font("sans-serif", 20));
		titoloContatore.setMinWidth(300);
		titoloContatore.setPadding(new Insets(5, 5, 5, 5));
		titoloContatore.setAlignment(Pos.CENTER);
		titoloContatore.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
		
		Label displayContatore = new Label("-"); // 20)
		displayContatore.setFont(new Font("sans-serif", 40));
		displayContatore.setMinSize(300, 175);
		displayContatore.setAlignment(Pos.CENTER);
		displayContatore.setStyle("-fx-font-weight: bold; -fx-border-width: 5px; -fx-border-color:black;"
				+ "-fx-text-fill: black; -fx-border-radius: 25px");
		
		VBox contatoreTotaleSpese = new VBox(); // 18)
		contatoreTotaleSpese.getChildren().addAll(titoloContatore, displayContatore);
		
		zonaAnalisi.getChildren().addAll(grafico.getGUI(), contatoreTotaleSpese, zonaCambioPeriodo.getGUI());
		zonaAnalisi.setAlignment(Pos.TOP_CENTER);
		zonaAnalisi.setSpacing(50);
	}
	
	
	public void aggiornaTotaleSpese (double quanto) { // 21)
		totaleSpese = quanto;
		
		VBox boxTotaleSpese = (VBox)zonaAnalisi.getChildren().get(1);
		Label testoTotaleSpese = (Label)boxTotaleSpese.getChildren().get(1);
		testoTotaleSpese.setText("\u20ac " + (new DecimalFormat("0.00")).format(totaleSpese)); // 22)
	}
	
	public GestoreSpese getGestoreSpese() {
		return gestoreSpese;
	}

	public BarraCaricaDati getBarraCaricaDati() {
		return barraCaricaDati;
	}

	public GraficoCategorieDiSpesa getGraficoCategorieDiSpesa() {
		return grafico;
	}
	
	public TabellaSpese getTabellaSpese() {
		return tabella;
	}

	public FormDiInserimento getFormDiInserimento() {
		return formInserimento;
	}
	
	public ZonaCambioPeriodo getZonaCambioPeriodo() {
		return zonaCambioPeriodo;
	}
}

/* Note:
	00) La classe InterfacciaGestoreSpese si occupa di costruire e gestire l'interfaccia grafica,
		instanziando i vari elementi grafici che la compongono
	01) Variabile che contiene il valore da mostrare come totale delle spese
	02) Dichiarazione dei contenitori degli elementi grafici: header e' la parte superiore (titolo e CARICA_DATI),
		pulsantiTabella contiene i pulsanti sottostanti la tabelle e zonaAnalisi e' la parte inferiore della GUI
		(grafico, totale spese, modifica periodo temporale)
	03) Dichiarazione degli oggetti grafici
	04) Costruttore: inizializza l'interfaccia e inserisce il contenitore principale nell'oggetto Group 
		passato come riferimento
	05) Istanziazione dei contenitori
	06) Istanziazione degli oggetti grafici
	07) Lancia le routine che si occupano di costruire e inserire gli elementi grafici appartenenti 
		ai vari contenitori
	08) Inizializza il contenitore principale della GUI
	09) Inserisce il contenitore principale nell'oggeto Group passato come parametro, che ha il compito di
		contenere tutti gli elementi grafici da mostrare sulla schermata
	10) Costruisce lo header dell'applicazione, composto dal titolo dell'applicazione e dall'area in cui l'utente
		puo' inserire il proprio Nome Utente per caricare i dati
	11) Crea il titolo dell'applicazione come Label
	12) Costruisce i pulsanti INSERISCI, ANNULLA e ELIMINA, ne configura la grafica e gli handler degli eventi
		di selezione, li inserisce nel contenitore apposito.
	13) Creazione del pulsante INSERISCI. Quando l'utente lo seleziona, viene inviato un log dell'evento e viene
		lanciata la	procedura di inserimento di una nuova voce di spesa nel database. Inoltre, viene
		svuotato il form di inserimento
	13b) Il pulsante viene configurato in modo da cambiare l'icona usata dal puntatore 
		del mouse quando vi passa sopra, col fine di notificare visualmente all'utente
		che quello e' un elemento grafico selezionabile
		
		MouseEvent e' la classe usata per memorizzare le informazioni relative agli
		eventi causati dal mouse
		https://docs.oracle.com/javafx/2/api/javafx/scene/input/MouseEvent.html

	14) Creazione del pulsante ANNULLA. Quando l'utente lo seleziona, viene inviato un log dell'evento e viene
		svuotato il form d'inserimento
	15) Creazione del pulsante ELIMINA. Quando l'utente lo seleziona, viene inviato un log dell'evento e viene
		lanciata l'operazione di rimozione dal database della voce di spesa selezionata sulla tabella
	16) Configura l'aspetto di un pulsante, usando come colore di background il colore passato come parametro
	17) Costruisce la parte inferiore della GUI, ovvero l'area contenente il grafico a torta, il totale delle spese
		e la zona per cambiare il periodo di analisi
	18) E' il box che mostra il totale delle spese dell'utente, nel periodo selezionato
	19) E' l'etichetta presente sopra il box
	20) E' il display che mostra il totale delle spese
	21) Aggiorna il totale spese mostrato, sia nella variabile backend che nel box frontend 
	22) La classe java.text.DecimalFormat fornisce metodi per la formattazione di numeri
		con cifre decimale. In questo caso, l'importo totale viene formattato su due cifre
		decimali
		https://docs.oracle.com/javase/8/docs/api/java/text/DecimalFormat.html

		\u20ac e' il simbolo dell'euro in codifica Unicode
*/