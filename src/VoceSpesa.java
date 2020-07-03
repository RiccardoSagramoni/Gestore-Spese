import java.time.*;

public class VoceSpesa { // 00)

	private final int idSpesa;
	private final String categoria;
	private final double importo;
	private final LocalDate data; // 01)
	private final String descrizione;

	public VoceSpesa(int idSpesa, String categoria, double importo, LocalDate data, String descrizione) {
		this.idSpesa = idSpesa;
		this.categoria = categoria;
		this.importo = importo;
		this.data = data;
		this.descrizione = descrizione;
	}

	public int getIdSpesa() {
		return idSpesa;
	}

	public String getCategoria() {
		return categoria;
	}

	public double getImporto() {
		return importo;
	}

	public LocalDate getData() {
		return data;
	}

	public String getDescrizione() {
		return descrizione;
	}
}

/* Note:
	00) VoceSpesa rappresenta una voce di spesa relativa all'utente che sta usando l'applicazione
	01)	LocalDate e' una classe utilizzata per rappresentare i timestamp senza fuso orario,
		basandosi sullo standard ISO-8601
		https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
*/