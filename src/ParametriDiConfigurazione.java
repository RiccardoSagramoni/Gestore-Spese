import com.thoughtworks.xstream.*;
import java.util.*;
import javafx.scene.paint.*;

public class ParametriDiConfigurazione { // 00)

	private static final int DEFAULT_NUMERO_RIGHE_TABELLA = 5;
	private static final int DEFAULT_PERIODO_TEMPORALE_INIZIALE = 60;
	private static final String[] DEFAULT_CATEGORIE_SPESA = {
																"Animali domestici",
																"Automobile",
																"Casa",
																"Generi alimentari",
																"Hobbies e tempo libero",
																"Istruzione",
																"Salute",
																"Spese bancarie",
																"Tasse e imposte",
																"Trasporto",
																"Utenze",
																"Varie",
																"Vestiario"
															};
	private static final int DEFAULT_NUMERO_CATEGORIE_MOSTRATE_GRAFICO = 5;
	private static final Color[] DEFAULT_COLORI_GRAFICO =   {
																new Color(1, 0, 0, 1),	// RED
																new Color(0, 1, 0, 1),	// LIME
																new Color(0, 0, 1, 1),	// BLUE
																new Color(1, 1, 0, 1),	// YELLOW
																new Color(1, 0.64705884, 0, 1), // ORANGE
																new Color(0.5019608, 0, 0.5019608, 1) // PURPLE
															};
	private static final Color DEFAULT_COLORE_CARICA_DATI = new Color(0, 0, 1, 1); // BLUE
	private static final Color DEFAULT_COLORE_INSERISCI = new Color(0, 0.5019608, 0, 1); // GREEN
	private static final Color DEFAULT_COLORE_ELIMINA = new Color(0, 0, 0, 1); // BLACK
	private static final Color DEFAULT_COLORE_ANNULLA = new Color(1, 0, 0, 1); // RED
	private static final Color DEFAULT_COLORE_CAMBIA_PERIODO = new Color(1, 0.64705884, 0, 1); // ORANGE
	private static final Color DEFAULT_COLORE_RIGA_INSERIMENTO = new Color(1, 1, 0.8784314, 1); // LIGHT YELLOW
	private static final String DEFAULT_INDIRIZZO_IP_LOG = "127.0.0.1";
	private static final int DEFAULT_PORTA_LOG = 8080;
	private static final String DEFAULT_INDIRIZZO_IP_DATABASE = "127.0.0.1";
	private static final int DEFAULT_PORTA_DATABASE = 3306;
	private static final String DEFAULT_NOME_DATABASE = "gestore_spese";
	private static final String DEFAULT_USERNAME_DATABASE = "root";
	private static final String DEFAULT_PASSWORD_DATABASE = "";

	public int numeroRigheTabella;
	public int periodoTemporaleIniziale;
	public String[] categorieSpesa;
	public int numeroCategorieMostrateGrafico;
	public Color[] coloriGrafico;
	public Color coloreCaricaDati;
	public Color coloreInserisci;
	public Color coloreElimina;
	public Color coloreAnnulla;
	public Color coloreCambiaPeriodo;
	public Color coloreRigaInserimento;
	public String indirizzoIpLog;
	public int portaLog;
	public String indirizzoIpDatabase;
	public int portaDatabase;
	public String nomeDatabase;
	public String usernameDatabase;
	public String passwordDatabase;

	public ParametriDiConfigurazione (String xml) { // 01)
		if (xml != null && !xml.equals("")) {
			ParametriDiConfigurazione par = (ParametriDiConfigurazione)creaXStream().fromXML(xml); // 02)
			numeroRigheTabella = par.numeroRigheTabella;
			periodoTemporaleIniziale = par.periodoTemporaleIniziale; 
			categorieSpesa = par.categorieSpesa;
			numeroCategorieMostrateGrafico = par.numeroCategorieMostrateGrafico;
			coloriGrafico = par.coloriGrafico;
			coloreCaricaDati = par.coloreInserisci;
			coloreInserisci = par.coloreInserisci;
			coloreElimina = par.coloreElimina;
			coloreAnnulla = par.coloreAnnulla;
			coloreCambiaPeriodo = par.coloreCambiaPeriodo;
			coloreRigaInserimento = par.coloreRigaInserimento;
			indirizzoIpLog = par.indirizzoIpLog;
			portaLog = par.portaLog;
			indirizzoIpDatabase = par.indirizzoIpDatabase;
			portaDatabase = par.portaDatabase;
			nomeDatabase = par.nomeDatabase;
			usernameDatabase = par.usernameDatabase;
			passwordDatabase = par.passwordDatabase;
		}
		
		configuraParametriMancanti(); // 03)
	}
	
	private void configuraParametriMancanti () { // 03)
		if (numeroRigheTabella == 0) {
			numeroRigheTabella = DEFAULT_NUMERO_RIGHE_TABELLA;
		}
		if (periodoTemporaleIniziale == 0) {
			periodoTemporaleIniziale = DEFAULT_PERIODO_TEMPORALE_INIZIALE;
		}
		if (categorieSpesa == null) {
			categorieSpesa = Arrays.copyOf(DEFAULT_CATEGORIE_SPESA, DEFAULT_CATEGORIE_SPESA.length);
		}
		if (numeroCategorieMostrateGrafico == 0) {
			numeroCategorieMostrateGrafico = DEFAULT_NUMERO_CATEGORIE_MOSTRATE_GRAFICO;
		}
		if (coloriGrafico == null) {
			coloriGrafico = Arrays.copyOf(DEFAULT_COLORI_GRAFICO, DEFAULT_COLORI_GRAFICO.length);
		}
		if (coloreCaricaDati == null) {
			coloreCaricaDati = DEFAULT_COLORE_CARICA_DATI;
		}
		if (coloreInserisci == null) {
			coloreInserisci = DEFAULT_COLORE_INSERISCI;
		}
		if (coloreElimina == null) {
			coloreElimina = DEFAULT_COLORE_ELIMINA;
		}
		if (coloreAnnulla == null) {
			coloreAnnulla = DEFAULT_COLORE_ANNULLA;
		}
		if (coloreCambiaPeriodo == null) {
			coloreCambiaPeriodo = DEFAULT_COLORE_CAMBIA_PERIODO;
		}
		if (coloreRigaInserimento == null) {
			coloreRigaInserimento = DEFAULT_COLORE_RIGA_INSERIMENTO;
		}
		if (indirizzoIpLog == null) {
			indirizzoIpLog = DEFAULT_INDIRIZZO_IP_LOG;
		}
		if (portaLog == 0) {
			portaLog = DEFAULT_PORTA_LOG;
		}
		if (indirizzoIpDatabase == null) {
			indirizzoIpDatabase = DEFAULT_INDIRIZZO_IP_DATABASE;
		}
		if (portaDatabase == 0) {
			portaDatabase = DEFAULT_PORTA_DATABASE;
		}
		if (nomeDatabase == null) {
			nomeDatabase = DEFAULT_NOME_DATABASE;
		}
		if (usernameDatabase == null) {
			usernameDatabase = DEFAULT_USERNAME_DATABASE;
		}
		if (passwordDatabase == null) {
			passwordDatabase = DEFAULT_PASSWORD_DATABASE;
		}
	}
	
	public static XStream creaXStream() { // 04)
		return new XStream();
	}

	public String toXML() { // 05)
		return creaXStream().toXML(this);
	}
}

/* Note:
	00) ParametriDiConfigurazione e' la classe che preleva i parametri inseriti dall'utente
		nel file di configurazione XML e li rende disponibili alle classi frontend 
		e middleware. La classe presenta una serie di attributi statici di default,
		il cui valore verra' usato nel caso di eventuali parametri di configurazione 
		mancanti
	01) Costruisce un oggetto ParametriDiConfigurazione a partire da una stringa
		XML gia' validata (passata come parametro)
	02) Se la stringa e' valida, converte la stringa XML in un oggetto ParametriDiConfigurazione
		ed effettua la copia membro a membro di tutti gli attributi
	03) Ricerca se vi sono dei parametri non inizializzati, ovvero che sono rimasti
		al valore Java di default. In tal caso, inizializza l'attributo con il valore
		del parametro statico di default. Se la stringa xml non era valida,
		tutti gli attributi verranno settati
	04) Crea un flusso XStream per la conversione da XML a ParametriDiConfigurazione e viceversa
	05) Converte l'oggetto ParametriDiConfigurazione in XML
		
*/