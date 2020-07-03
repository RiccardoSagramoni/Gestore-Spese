import java.io.*;
import java.net.*;

public class ServerLogEventiGUI { // 00)

	private static final int PORTA = 8080; // 01)
	private static final String FILE_LOG = "files/server_log.txt"; // 02)
	private static final String SCHEMA_LOG = "files/schema_log.xsd"; // 03)

	public static void main(String args[]) {
		System.out.println("Server Log avviato\n");
        
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) { // 04)
            
			while (true) {
                try (	Socket socket = serverSocket.accept(); // 05)
						DataInputStream input = new DataInputStream(socket.getInputStream()); // 06)
					)
				{
                    String log = input.readUTF(); // 07)
                    System.out.println("Log ricevuto\n" + log + "\n"); 
                    
					if (ValidatoreXML.valida(log, SCHEMA_LOG)) { // 08)
                        GestoreFile.salva(log + "\n", FILE_LOG, true);
					}
                }
            }
			
        } catch(IOException ex) {
            System.err.println("Errore: " + ex.getMessage() + "\n");
        }
	}

}

/* Note:
	00) ServerLOGEventiGUI ha il compito di ricevere, validare e (se validi) memorizzare i log inviati dagli
		applicativi client GestoreSpese. I log vengono ricevuti in formato XML. 
		Questi log vengono raccolti per permettere ai progettisti software di valutare le prestazioni 
		e la facilita' d'uso della GUI
	01) Porta su cui il server rimane in ascolto
	02) File dove il server memorizzera' i log XML ricevuti
	03) File di schema per la validazione dei log XML ricevuti
	04) Il server apre un socket e si mette in ascolto di connessioni in ingresso
	05) Il server accetta una connessione in ingresso. Nel caso in cui non vi siano connessione,
		il server si blocca in attesa di una richiesta di connessione
	06) Crea uno stream per tipi primitivi, concatenato allo stream input del socket, per leggere il log inviato dal
		client come stringa
	07) Legge il log in ingresso e notifica la corretta ricezione stampando un messaggio sullo schermo
	08) Valida il log ricevuto secondo lo schema predefinito. Qualora sia valido, lo memorizza sul file txt
*/