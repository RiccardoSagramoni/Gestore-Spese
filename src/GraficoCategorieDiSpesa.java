import java.text.*;
import java.util.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.util.*;

public class GraficoCategorieDiSpesa { // 00)

	private final PieChart grafico; // 01)
	private final ObservableList<PieChart.Data> dati; // 02)
	
	public GraficoCategorieDiSpesa() { // 03)
		dati = FXCollections.observableArrayList();
		grafico = new PieChart(dati);
		
		grafico.setStyle("-fx-border: thin; -fx-border-color:black;");
		grafico.setPrefSize(550, 300);
		grafico.setLabelsVisible(false);
		grafico.setLegendSide(Side.LEFT);
	}

	public void aggiorna (List<Pair<String, Double>> dati, double speseTotali) { // 04)
		this.dati.clear(); // 05)
		
		for (Pair<String, Double> coppia : dati) { // 06)
			this.dati.add(
				new PieChart.Data(
					coppia.getKey() + " " + 
						(new DecimalFormat("0.00")).format(coppia.getValue()/speseTotali * 100) + "%", // 07)
					coppia.getValue()
				)
			);
		}
	}
	
	public Node getGUI() { // 08)
		return grafico;
	}
}

/* Note:
	00) GraficoCategorieDiSpesa si occupa di creare e mantenere aggiornato i grafico a torta 
		che mostra per ogni	categoria di spesa l-impatto percentuale sul totale delle
		spese
	01) La classe PieChart mostra un grafico a torta
		https://docs.oracle.com/javafx/2/api/javafx/scene/chart/PieChart.html
	02) Lista contenente i dati del grafico (coppie chiave-valore)
	03) Inizializza il grafico, instanziando sia l'observable list dei dati che il grafico stesso
		e configurandone lo stile visivo
	04) Aggiorna il grafico sostituendo i dati vecchi con quelli passati dal chiamante.
		La lista "dati" (passata per riferimento) contiene una lista di coppie chiave-valore, 
		dove la chiave e' il nome della categoria e il valore e' l'importo totale di tale
		categoria.
		La variabile speseTotali serve per calcolare la percentuale di ogni categoria
	05) Svuota i dati vecchi del grafico
	06) Inserisce ogni coppia di dati nel grafico
	07) Modifica la chiave, in modo che accanto al nome della Categoria vi sia la percentuale
		approssimata alla seconda cifra decimale
	08) Restituisce l'oggetto grafico della classe, ovvero la PieChart, 
		cosi' da poter essere inserita nella GUI
*/