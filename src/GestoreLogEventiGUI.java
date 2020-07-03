import java.io.*;
import java.net.*;
import java.util.*;

public class GestoreLogEventiGUI { // 00)	
	private final String indirizzoIp;
	private final int porta;
	
	public GestoreLogEventiGUI (String indirizzoIp, int porta) {
		this.indirizzoIp = indirizzoIp;
		this.porta = porta;
	}
	
	public void inviaLogEvento(String nomeApplicazione, String nomeEvento) { // 01)
		try (	Socket socket = new Socket(indirizzoIp, porta); // 02)
				DataOutputStream output = new DataOutputStream(socket.getOutputStream()) // 03)
			)
		{
			LogEventoXML log = new LogEventoXML( // 04)
					nomeApplicazione, 
					((InetSocketAddress)socket.getLocalSocketAddress()).getAddress().getHostAddress(), // 05)
					new Date(),
					nomeEvento); 
			
			output.writeUTF(log.toXML()); // 06)
		} catch (IOException ex) {
			System.err.println("Impossibile inviare log al server: " + ex.getMessage());
		}
	}
}

/* Note:
	00) La classe GestoreLogEventiGUI si occupa di inviare i log dell'applicazione in formato XML ad un server log, 
		al verificarsi di alcuni eventi come l'avvio o la chiusura dell'applicazione 
		o l'interazione dell'utente con alcuni elementi della GUI
	01) Invia un log al server log
	02) Apre una connessione TCP con il server log
	03) Concatena un flusso oggetto al flusso di uscita del socket, cosi' da poter inviare 
		al server il log in formato XML
	04) Crea un oggetto LogEventoXML con i parametri passati dal chiamante
	05) Ottiene l'indirizzo IP in notazione puntata su cui e' stata aperto la socket di output.
		InetSocketAddress e' la classe che implementa l'indirizzo di una socket 
		(indirizzo IP dell'host + porta)
		https://docs.oracle.com/javase/8/docs/api/java/net/InetSocketAddress.html

	06) Converte il log in una stringa XML e la invia sulla connessione con il server
*/