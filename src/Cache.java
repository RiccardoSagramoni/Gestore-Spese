import java.io.*;
import java.time.*;

public class Cache implements Serializable { // 00)
	public String nomeUtente;
	public String categoriaSpesaInserita;
	public double importoSpesaInserito;
	public LocalDate dataSpesaInserita; // 01)
	public String descrizioneSpesaInserita;
	public LocalDate dataInizioPeriodo;
	public LocalDate dataFinePeriodo;
	
	public Cache (	String nomeUtente, String categoriaSpesaInserita, double importoSpesaInserito, 
					LocalDate dataSpesaInserita, String descrizioneSpesaInserita, LocalDate dataInizioPeriodo,
					LocalDate dataFinePeriodo)
	{
		this.nomeUtente = nomeUtente;
		this.categoriaSpesaInserita = categoriaSpesaInserita;
		this.importoSpesaInserito = importoSpesaInserito;
		this.dataSpesaInserita = dataSpesaInserita;
		this.descrizioneSpesaInserita = descrizioneSpesaInserita;
		this.dataInizioPeriodo = dataInizioPeriodo;
		this.dataFinePeriodo = dataFinePeriodo;
	}
}

/* Note:
	00) La classe Cache rappresenta i dati che devono essere caricati nell'applicazione all'avvio e salvati
		su file alla chiusura. E' serializzabile in binario
	01) LocalDate e' una classe adottata in Java 8 per memorizzare i timestamp (data + ora)
		https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
*/