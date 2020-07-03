import java.time.*;
import javafx.beans.property.*;

public class VoceSpesaBean { // 00)

	private SimpleStringProperty categoria;
	private SimpleDoubleProperty importo;
	private SimpleObjectProperty<LocalDate> data;
	private SimpleStringProperty descrizione;
	
	private int id = -1; // 01)

	public VoceSpesaBean(String categoria, double importo, LocalDate data, String descrizione) {
		this.categoria = new SimpleStringProperty(categoria);
		this.importo = new SimpleDoubleProperty(importo);
		this.data = new SimpleObjectProperty<>(data);
		this.descrizione = new SimpleStringProperty(descrizione);
	}

	public VoceSpesaBean(VoceSpesa voce) { // 02)
		this(voce.getCategoria(), voce.getImporto(), voce.getData(), voce.getDescrizione());
		this.id = voce.getIdSpesa();
	}
	
	public VoceSpesa toVoceSpesa() { // 03)
		return new VoceSpesa(id, getCategoria(), getImporto(), getData(), getDescrizione());
	}
	
	public String getCategoria() {
		return categoria.getValue();
	}
	public Double getImporto() {
		return importo.getValue();
	}
	public LocalDate getData() {
		return data.getValue();
	}
	public String getDescrizione() {
		return descrizione.getValue();
	}
	
	public void setCategoria (String cat) {
		categoria.setValue(cat);
	}
	public void setImporto (Double imp){
		importo.setValue(imp);
	}
	public void setData (LocalDate data) {
		this.data.setValue(data);
	}
	public void setDescrizione (String desc) {
		descrizione.setValue(desc);
	}
}

/* Note:
	00) VoceSpesaBean e' il bean utilizzato per incapsulare le voci spesa e mostrarli nella tabella della GUI
	01) E' l'id della voce spesa originale. Non viene mostrata sugli elementi grafici che usano questo bean,
		poiche' non ha significato per l'utente. E' tuttavia necessaria per facilitare l'eliminazione di
		una record di spesa dal database.
		Ha come valore di default -1 (usato ad esempio nella TableView responsabile dell'inserimento di una voce
		spesa, in cui chiaramente non sappiamo quale sia il prossimo id)
	02) Costruisce un VoceSpesaBean a partire da una VoceSpesa
	03) Converte un VoceSpesaBean in un VoceSpesa
*/