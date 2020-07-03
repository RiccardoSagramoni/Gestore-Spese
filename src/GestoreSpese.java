import java.time.*;
import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.util.*;

public class GestoreSpese extends Application { // 00)
	
	private static final String NOME_APPLICAZIONE = "Gestore Spese";
	private static final String FILE_CONFIGURAZIONE_XML = "files/config.xml";
	private static final String SCHEMA_FILE_CONFIGURAZIONE_XML = "files/schema_config.xsd";
	
	public static final int SCENE_WIDTH = 1300;
	public static final int SCENE_HEIGHT = 775;
	
	private InterfacciaGestoreSpese interfacciaGUI;
	private ParametriDiConfigurazione configuration;
	private GestoreLogEventiGUI gestoreLog;
	private GestoreDatabase gestoreDatabase;
	
	private Scene scene;
	
	@Override
	public void start(Stage stage) { // 01)
		String confXML = GestoreFile.carica(FILE_CONFIGURAZIONE_XML); // 02)
		configuration = new ParametriDiConfigurazione( // 03)
				ValidatoreXML.valida(confXML, SCHEMA_FILE_CONFIGURAZIONE_XML) ? // 04)
					confXML : null
				);
		
		gestoreLog = new GestoreLogEventiGUI(configuration.indirizzoIpLog, configuration.portaLog); // 05)
		gestoreDatabase = new GestoreDatabase(configuration.nomeDatabase, configuration.indirizzoIpDatabase, 
				configuration.portaDatabase, configuration.usernameDatabase, configuration.passwordDatabase); // 05)
		
		Group root = new Group();
		
		interfacciaGUI = new InterfacciaGestoreSpese(root, this, configuration, NOME_APPLICAZIONE); // 06)
		GestoreCache.carica(interfacciaGUI); // 07)
		aggiornaDatiSpeseGUI(); // 08)
		
		inviaLogEvento("AVVIO"); 
		stage.setOnCloseRequest( (WindowEvent we) -> { // 09)
					inviaLogEvento("TERMINA");
					GestoreCache.salva(interfacciaGUI);
				}
			);
		
		stage.setTitle(NOME_APPLICAZIONE);
		scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		creaStileScena();
		stage.setScene(scene);
		stage.show();
	}
	
	public void inserisciNuovaSpesa (VoceSpesa spesa) { // 10)
		gestoreDatabase.inserisciVoceSpesa(
			interfacciaGUI.getBarraCaricaDati().getNomeUtente(),
			spesa.getCategoria(),
			spesa.getImporto(),
			spesa.getData(),
			spesa.getDescrizione()
		);
		
		aggiornaDatiSpeseGUI();
	}

	public void aggiornaDatiSpeseGUI() { // 11)
		String nomeUtente = interfacciaGUI.getBarraCaricaDati().getNomeUtente();
		LocalDate dataInizio = interfacciaGUI.getZonaCambioPeriodo().getDataInizio();
		LocalDate dataFine = interfacciaGUI.getZonaCambioPeriodo().getDataFine(); // 12)
		
		List<VoceSpesa> spese = gestoreDatabase.ottieniVociSpesa(nomeUtente, dataInizio, dataFine); // 13)
		interfacciaGUI.getTabellaSpese().caricaVoci(spese); // 13)
		
		double totaleSpese = 0;
		for (VoceSpesa v : spese) { // 14)
			totaleSpese += v.getImporto();
		}
		totaleSpese = Math.round(totaleSpese * 100.0) / 100.0; // 14b)
		interfacciaGUI.aggiornaTotaleSpese(totaleSpese);
		
		List<Pair<String, Double>> coppieGrafico = 
				gestoreDatabase.ottieniCoppieCategoriaTotaleImporti(
						nomeUtente, dataInizio, dataFine, configuration.numeroCategorieMostrateGrafico
				); // 15)
		double parzialeSpeseMostrate = 0;
		for (Pair<String, Double> p : coppieGrafico) { // 16)
			parzialeSpeseMostrate += p.getValue();
		}
		if (parzialeSpeseMostrate < totaleSpese) { // 17)
			Pair<String, Double> altro = new Pair<>("Altro", totaleSpese - parzialeSpeseMostrate); 
			coppieGrafico.add(altro);
		}
		
		interfacciaGUI.getGraficoCategorieDiSpesa().aggiorna(coppieGrafico, totaleSpese); // 18)
	}
	
	public void eliminaVoceSpesa (VoceSpesa spesa) { // 19)
		gestoreDatabase.eliminaVoceSpesa(
			interfacciaGUI.getBarraCaricaDati().getNomeUtente(),
			spesa.getIdSpesa()
		);
		
		aggiornaDatiSpeseGUI();
	}
	
	public void inviaLogEvento (String evento) { // 20)
		gestoreLog.inviaLogEvento(NOME_APPLICAZIONE, evento);
	}
	
	public void setCursorHand(){ // 21)
		scene.setCursor(Cursor.HAND); // 21b)
	}
	public void setCursorDefault(){ // 22)
		scene.setCursor(Cursor.DEFAULT);
	}
	
	private void creaStileScena() { // 23)
		String stile = "";
		
		for (int i = 0; i < configuration.coloriGrafico.length; ++i) { // 24)
			stile += ".default-color"+i+".chart-pie { -fx-pie-color: #" +
				configuration.coloriGrafico[i].toString().substring(2) + "; }\n";
		}
		
		GestoreFile.salva(stile, "files/style.css", false); // 25)
		scene.getStylesheets().add("file:files/style.css"); // 26)
	}
}

/* Note:
	00) GestoreSpese e' il controller applicativo: estrae e valida i parametri di configurazione,
		instanzia e configura il gestore del database e il gestore degli eventi di log,
		costruisce l'interfaccia grafica e la inizializza con i dati della cache, configura un
		apposito handler per il salvataggio della cache e l'invio di un log alla chiusura dell'applicazione.
		Inoltre offre alle classi di frontend, metodi per la corretta interazione con il database e con i log
	01) Il metodo start e' il primo metodo applicativo che viene eseguito. Si occupa delle operazioni di
		inizializzazione dell'applicazione
	02) Legge il file XML di configurazione e lo memorizza su una stringa
	03) Costruisce un oggetto ParametriDiConfigurazione dal contenuto del file XML
	04) Valida il file XML di configurazione. In caso sia valido lo passa al costruttore di ParametriDiConfigurazione
	05) Instanzia il gestore del database e il gestore dei log, inizializzandoli con alcuni parametri di configurazioni
	06) Costruisce l'interfaccia grafica. Passa al costruttore un riferimento ad un oggetto Group nel quale
		verranno inseriti gli elementi grafici da mostrare sullo schermo
	07) Carica i dati memorizzati in cache sulla GUI
	08) Se in cache e' presente il nomeutente, carica sulla schermata iniziale i dati dell'utente inserito.
		Altrimenti mostra la tabella e il grafico vuoti
	09) Configura le operazioni da svolgere alla chiusura dell'applicazione: invio del log di CHIUSURA e
		salvataggio in cache dei dati temporanei
	10) Inserisce una nuova spesa nel database e aggiorna la GUI
	11) Aggiorna i dati mostrati dalla GUI con le spese effettuate dall'utente e nel periodo
	12) Preleva dalla GUI il nome utente e le date di inizio e fine del periodo da considerare, necessari
		per poter interrogare il database
	13) Recupera dal database le spese dell'utente e le inserisce nella Tabella Spese
	14) Calcola il totale delle spese effettuate dall'utente nel periodo e lo inserisce nell'apposito box
		presente sulla GUI. 
	14b) Il totale delle spese viene approssimato alla seconda cifra decimale
	15) Estrae dal database le coppie Categoria-TotaleImporti relative alle spese effettuate dall'utente
		nel periodo per configurare il grafico a torta.

		Pair<K,V> e' una classe Generics usata per rappresentare in maniera	conveniente 
		coppie chiave-valore
		https://docs.oracle.com/javafx/2/api/javafx/util/Pair.html

	16) Nel grafico a torta vengono mostrate solo un numero ben preciso (configurabile) di categorie. Le altre vengono
		accorpate in una categoria "Altro".
		Questo for calcola il totale delle spese mostrate, per poter ricavare successivamente le spese appartenenti
		ad "Altro"
	17) Il grafico a torta mostra un numero limitato di categorie e raggruppa le categorie rimanenti in una
		apposita categoria "Altro", in modo da non appesantire inutilmente l'interfaccia. La categoria "Altro"
		ha senso di esistere solo se la somma delle spese della categorie mostrate e' inferiore al totale delle spese
	18) Aggiorna il grafico a torta con i dati ricavati
	19) Elimina una voce spesa (il cui id e' passato come parametro) effettuata dall'utente ed aggiorna l'interfaccia
	20) Invia un log di evento al server log
	21) Modifica lo stile del puntatore in quello a forma di mano, per la selezione dei pulsanti
	21b) Cursor e' la classe che incapsula la rappresentazione bitmap del puntatore del mouse
		https://docs.oracle.com/javafx/2/api/javafx/scene/Cursor.html
	22) Modifica lo stile del puntatore in quello standard
	23) Utilizza i paramentri di configurazione settati dall'utente per scrivere su un file css le regole
		necessarie per utilizzare correttamente i colori personalizzati nel grafico a torta della GUI
	24) Preleva i colori inseriti dall'utente e prepara il file css
	25) Scrive la stringa preparata sul file css di stile
	26) Importa il file css nella scena
*/