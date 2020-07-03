import java.sql.*;
import java.time.*;
import java.util.*;
import javafx.util.Pair;

public class GestoreDatabase { // 00)
	private final String nomeDb;
	private final String indirizzoIp;
	private final int porta;
	private final String username;
	private final String password;
	
	public GestoreDatabase (String nomeDb, String indirizzoIp, int porta, String username, String password) {
		this.nomeDb = nomeDb;
		this.indirizzoIp = indirizzoIp;
		this.porta = porta;
		this.username = username;
		this.password = password;
	}
	
	public List<VoceSpesa> ottieniVociSpesa(String utente, LocalDate dataInizio, LocalDate dataFine) { // 01)
		List<VoceSpesa> listaSpese = new ArrayList<>(); // 15)
		
		if (utente == null || utente.equals("")) return listaSpese; // 16)
		
		try (	Connection connection = DriverManager.getConnection(
					"jdbc:mysql://" + indirizzoIp + ":" + porta + "/" + nomeDb, username, password); // 02)
				PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM spesa WHERE username = ? AND data BETWEEN ? AND ? ORDER BY data DESC"); // 03)
		) {
			statement.setString(1, utente);
			statement.setString(2, dataInizio.toString());
			statement.setString(3, dataFine.toString());
			ResultSet result = statement.executeQuery();	
			
			while (result.next()) {	// 04)
				listaSpese.add(
					new VoceSpesa(result.getInt("id_spesa"), result.getString("categoria"), 
						result.getDouble("importo"), result.getDate("data").toLocalDate(),
						result.getString("descrizione")));
			}
		} catch (SQLException ex) {
			System.err.println("Impossibile ottenere voci spesa dell'utente: " + ex.getMessage());
		}
		
		return listaSpese;
	}

	public boolean inserisciVoceSpesa (String utente, String categoria, double importo, 
										LocalDate data, String descrizione) { // 06)
		int result = 0; // 07)
		
		try (	Connection connection = DriverManager.getConnection(
					"jdbc:mysql://" + indirizzoIp + ":" + porta + "/" + nomeDb, username, password); // 08)
				PreparedStatement statementControllaUsername =
						connection.prepareStatement("CALL assicura_esistenza_utente(?);"); // 08b)
				PreparedStatement statementInsert = 
						connection.prepareStatement("INSERT INTO spesa VALUES(?,0,?,?,?,?)"); // 08c)
		) {
			statementControllaUsername.setString(1, utente);
			statementControllaUsername.execute();
			
			statementInsert.setString(1, utente);
			statementInsert.setString(2, categoria);
			statementInsert.setDouble(3, importo);
			statementInsert.setString(4, data.toString());
			statementInsert.setString(5, descrizione);
			result = statementInsert.executeUpdate(); // 09)
		} catch (SQLException ex) {
			System.err.println("Impossibile inserire voce spesa: " + ex.getMessage());
		} 
		
		return (result != 0);
	}
	
	public boolean eliminaVoceSpesa(String utente, int idSpesa) { // 10)
		int result = 0; // 11)
		
		try (	Connection connection = DriverManager.getConnection(
					"jdbc:mysql://" + indirizzoIp + ":" + porta + "/" + nomeDb, username, password);
				PreparedStatement statement = connection.prepareStatement(
						"DELETE FROM spesa WHERE username = ? AND id_spesa = ?"); // 12)
		) {
			statement.setString(1, utente);
			statement.setInt(2, idSpesa);
			result = statement.executeUpdate(); // 13)
		} catch (SQLException ex) {
			System.err.println("Impossibile eliminare voce spesa: " + ex.getMessage());
		} 
		
		return (result != 0);
	}
	 
	public List<Pair<String, Double>> ottieniCoppieCategoriaTotaleImporti (String utente, LocalDate dataInizio, 
																LocalDate dataFine, int quanteCoppie) { // 14)
		List<Pair<String, Double>> lista = new ArrayList<>(); // 15)
		
		if (utente == null || utente.equals("")) return lista; // 16)
		
		try (	Connection connection = DriverManager.getConnection(
					"jdbc:mysql://" + indirizzoIp + ":" + porta + "/" + nomeDb, username, password);
				PreparedStatement statement = connection.prepareStatement(
					"SELECT categoria, ROUND(SUM(importo), 2) AS quanto FROM spesa WHERE username = ? "
					+ "AND data BETWEEN ? AND ? GROUP BY categoria ORDER BY quanto DESC "
					+ "LIMIT ?"); // 17)
		) {
			statement.setString(1, utente);
			statement.setString(2, dataInizio.toString());
			statement.setString(3, dataFine.toString());
			statement.setInt(4, quanteCoppie);
			ResultSet result = statement.executeQuery(); // 18)
			
			while (result.next()) {
				lista.add(new Pair<>(result.getString("categoria"), result.getDouble("quanto"))); // 19)
			}
		} catch (SQLException ex) {
			System.err.println("Impossibile ottenere coppie Categoria-SpesaTotale: " + ex.getMessage());
		}
		
		return lista;
	}
}

/* Note:
	00) GestoreDatabase offre un insieme di metodi utilizzati dalle classi middleware per interrogare
		il database ed estrarre i dati necessari
	01) Interroga il database per leggere tutte le voci spesa di un utente all'interno del periodo temporale selezionato
	02) Instaura una connessione con il database MySQL
	03) Prepara uno statement MySQL per ottenere le voci di spesa dell'utente username nel periodo temporale 
		compreso tra DataInizio e DataFine
	04) Estra i risultati della query creando un oggetto VoceSpesa per ogni tupla, aggiungendola alla lista che 
		verra' restituita al chiamante
	06) Inserisce nel database una nuove voce di spesa per un dato utente. Se l'utente non esiste,
		il database e' stato configurato affinche' venga aggiunto in automatico nel database alla ricezione
		di un'operazione di INSERT
	07) Variabile che conterra' il risultato della query. Se e' uguale a zero, allora l'operazione di INSERT
		e' fallita	
	08) Apre la connessione con il database MySQL
	08b) Questo statement chiama una procedure del database che verifica l'esistenza dell'utente.
		In caso contrario, l'utente viene automaticamente creato
	08c) Questo statement inserisce una nuova spesa nel database
	09) Inserisce i valori nello statement ed esegue la INSERT.
		Il metodo executeUpdate() restituisce il numero di righe influenzate dall'operazione. 
		Se uguale a zero, significa che l'inserimento e' fallito.
	10) Elimina una voce spesa (identificata dalla chiave utente-idSpesa) dal database
	11) Variabile che conterra' il risultato della query. Se e' uguale a zero, allora l'operazione di UPDATE
		e' fallita
	12) Apre una connessione con il database e prepara uno statement MySQL che elimina una spesa
	13) Inserisce i valori nello statement ed esegue la DELETE.
		Il metodo executeUpdate() restituisce il numero di righe influenzate dall'operazione. 
		Se uguale a zero, significa che l'operazione e' fallita.
	14) Interroga il database per restituisce una lista di coppie {Categoria, Importo_Totale} 
		relative all'utente indicato nel periodo temporale scelto.
		Per ogni categoria, quindi, vengono sommate le spese totali effettuate afferenti a tale categoria.

		Pair<K,V> e' una classe Generics usata per rappresentare in maniera	conveniente 
		coppie chiave-valore
		https://docs.oracle.com/javafx/2/api/javafx/util/Pair.html
		
	15) E' la lista che verra' restituita.
	16) Se l'utente non e' indicato, restituisce direttamente la lista vuota
	17) Apre la connessione e prepara la query
	18) Compila i campi della query e la esegue
	19) Scorre il result set e inserisce ogni coppia nella lista, creando un oggetto Pair<>
*/