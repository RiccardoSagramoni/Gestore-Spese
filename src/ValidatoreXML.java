import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ValidatoreXML { // 00)
	
	public static boolean valida(String xmlDaValidare, String fileSchemaURI)  { // 01)
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();	// 02)
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); // 03)
			Document d = db.parse(new InputSource(new StringReader(xmlDaValidare))); // 04)
			Schema s = sf.newSchema(new StreamSource(new File(fileSchemaURI))); // 05)
			s.newValidator().validate(new DOMSource(d)); // 06)
			
			return true;
		} 
		catch (SAXException ex) {
            System.err.println("Errore di validazione: " + ex.getMessage());
		}
		catch (IOException | ParserConfigurationException ex) {
			System.err.println("Impossibile validare la stringa XML: " + ex.getMessage());
		}
		
		return false;
	}
}

/* Note:
	00) ValidatoreXML offre metodi per la validazione di file XML. 
		Restituisce true se l'operazione di validazione ha successo, false altrimenti.
	01) Questo metodo valida un file XML secondo un dato schema XSD
	02) Instanzia un parser che produca oggetti DOM da documenti XML
	03) Questa instanza di SchemaFactory legge rappresentazioni esterne di schemi XML, per la validazione
	04) Estrae l'oggetto DOM dalla stringa XML da validare
	05) Estra l'oggetto schema vero e proprio dal file XSD
	06) Crea un oggetto validatore che valida il documento XML sullo schema caricato.
		In caso di un errore nella validazione lancia una SAXException
*/