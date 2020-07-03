import java.io.*;
import java.time.*;

public class GestoreCache { // 00)

	private static final String FILE_CACHE = "files/cache.bin";

	public static void carica (InterfacciaGestoreSpese interfacciaGUI) { // 01)
		Cache cache;
		
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILE_CACHE))) // 02)
		{
			cache = (Cache)input.readObject(); // 03)
		} catch (IOException | ClassNotFoundException ex) {
			System.err.println("Caricamento cache fallito: " + ex.getMessage());
			return;
		}
		
		caricaDatiCache(interfacciaGUI, cache);
	}
	
	private static void caricaDatiCache (InterfacciaGestoreSpese interfacciaGUI, Cache cache) { // 04)
		interfacciaGUI.getBarraCaricaDati().setNomeUtente(cache.nomeUtente); // 05)
		
		VoceSpesa spesa = new VoceSpesa(0, cache.categoriaSpesaInserita, cache.importoSpesaInserito, 
										cache.dataSpesaInserita, cache.descrizioneSpesaInserita); // 06)
		interfacciaGUI.getFormDiInserimento().setVoceInserita(spesa); // 06)
		
		interfacciaGUI.getZonaCambioPeriodo().setDataInizio(cache.dataInizioPeriodo); // 07)
		interfacciaGUI.getZonaCambioPeriodo().setDataFine(cache.dataFinePeriodo); // 07)
	}
	
	public static void salva (InterfacciaGestoreSpese interfacciaGUI) { // 08)
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILE_CACHE))) // 09)
		{
			output.writeObject(generaCache(interfacciaGUI)); // 10)
		} 
		catch (IOException ex) {
			System.err.println("Salvataggio cache fallito: " + ex.getMessage());
		}
	}
	
	private static Cache generaCache (InterfacciaGestoreSpese interfacciaGUI) { // 11)
		String nomeUtente = interfacciaGUI.getBarraCaricaDati().getNomeUtente(); // 12)
		
		VoceSpesa spesa = interfacciaGUI.getFormDiInserimento().getVoceInserita(); // 13)
		String categoriaSpesaInserita = spesa.getCategoria();
		double importoSpesaInserito = spesa.getImporto();
		LocalDate dataSpesaInserita = spesa.getData();
		String descrizioneSpesaInserita = spesa.getDescrizione();
		
		ZonaCambioPeriodo cambioPeriodo = interfacciaGUI.getZonaCambioPeriodo(); // 14)
		LocalDate dataInizioPeriodo = cambioPeriodo.getDataInizio();
		LocalDate dataFinePeriodo = cambioPeriodo.getDataFine();
		
		return new Cache(nomeUtente, categoriaSpesaInserita, importoSpesaInserito, dataSpesaInserita,
						descrizioneSpesaInserita, dataInizioPeriodo, dataFinePeriodo);
	}
}

/* Note:
	00) La classe GestoreCache gestisce le operazioni di salvataggio in cache e caricamento dalla cache dei
		dati inseriti dall'utente ancora visualizzati sull'interfaccia
	01) Estra i dati della cahe e li carica sulla GUI
	02) Apre uno strem oggetto di input per leggere un oggetto Cache serializzato su file
	03) Legge l'oggetto da file
	04) Carica sui vari elementi grafici della GUI i dati presenti nell'oggetto Cache
	05) Carica il nome utente nella barra
	06) Crea un oggetto VoceSpesa e lo passa al form d'inserimento cosi' che possa inizializzare i suoi campi
	07) Inizializza le date di inizio e fine del periodo
	08) Salva in cache i dati inseriti dall'utente sulla GUI
	09) Apre uno strem oggetto di output per scrivere un oggetto Cache su file
	10) Serializza l'oggetto Cache su file
	11) Genera un oggetto Cache, prelevando le informazioni necessarie dal'interfaccia
	12) Preleva il nome dell'utente
	13) Preleva i dati inseriti nel form come oggetto VoceSpesa e ne preleva i singoli attributi
	14) Prelava la date di inizio e fine del periodo configurate dall'utente
*/