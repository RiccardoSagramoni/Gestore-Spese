import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.basic.*;
import java.util.*;

public class LogEventoXML { // 00)
	
	public final String nomeApplicazione;
	public final String indirizzoIP;
	public final Date timestamp;
	public final String nomeEvento;

	public LogEventoXML (String nomeApplicazione, String indirizzoIP, Date timestamp, String nomeEvento) { // 01)
		this.nomeApplicazione = nomeApplicazione;
		this.indirizzoIP = indirizzoIP;
		this.timestamp = timestamp;
		this.nomeEvento = nomeEvento;
	}

	public String toXML() { // 02)
		XStream xs = new XStream();
		xs.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss", null, TimeZone.getDefault())); // 03)
		return xs.toXML(this);
	}
}

/* Note:
	00) La classe LogEventoXML rappresenta i log da inviare al server log, al verificarsi di alcuni eventi
		come l'avvio o la chiusura dell'applicazione o l'interazione dell'utente con alcuni elementi della GUI
	01) E' necessario utilizzare la classe java.util.Date per rappresentare il timestamp dell'evento scatenante,
		poiche' la versione XStream 1.4.7 non supporta i converter la nuova API LocalDateTime di Java 8
		(utilizzata in altre classi dell'applicazione)
	02) Converte un oggetto LogEventoXML in una stringa XML, secondo le regole di buona progettazione XML
		(tutti gli attributi della classe possono assumere una moltitudine di valori)
	03) Configura il formato della conversione del timestamp (java.util.Date) in un oggetto String, utilizzando
		il fuso orario locale
		TimeZone e' la classe utilizzata per rappresentare i fusi orari 
		https://docs.oracle.com/javase/8/docs/api/java/util/TimeZone.html
*/