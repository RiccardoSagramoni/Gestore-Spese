import java.io.*;
import java.nio.file.*;

public class GestoreFile { // 01)

	public static String carica(String file) { // 02)
		String contenuto = "";
		
		try {
			contenuto = new String(Files.readAllBytes(Paths.get(file))); // 03)
		} catch (IOException ex) {
			System.err.println("Caricamento file " + file + "fallito: " + ex.getMessage());
		}
		
		return contenuto;
	}

	public static void salva(Object oggetto, String file, boolean append) { // 04)
		try {
			Files.write(
                Paths.get(file), // 05)
                oggetto.toString().getBytes(),
				StandardOpenOption.CREATE,	// 06)
                (append) ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING // 07)
            );
		} catch (IOException ex) {
			System.err.println("Salvataggio su file " + file + "fallito: " + ex.getMessage());
		}
	}
}

/* Note:
	01) GestoreFile si occupa di leggere e scrivere su file con codifica ASCII
	02) Il metodo "carica" si occupa di leggere l'intero contenuto di un file (specificato come parametro)
		e lo restituisce come un oggetto String
	03) Legge l'intero contenuto del file
	
	04) Il metodo "salva" si occupa di scrivere il contenuto di un oggetto passato come parametro in un determinato
		file. La stringa ottenuta dalla conversione di oggetto puo' essere concatenata al file (append = true) o
		puo' sovrascrivere il file (append = false)
	05) Ricava il percorso del file target
	06) Crea il file se non esiste
	07) Si specifica la modalita' di scrittura in base al valore del parametro "append"
		https://docs.oracle.com/javase/8/docs/api/java/nio/file/StandardOpenOption.html
*/