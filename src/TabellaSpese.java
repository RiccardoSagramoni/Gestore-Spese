import java.time.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class TabellaSpese { // 00)

	private final TableView<VoceSpesaBean> tabella; // 01)
	private final ObservableList<VoceSpesaBean> voci; // 02)
	
	private final InterfacciaGestoreSpese interfacciaGUI;

	public TabellaSpese(InterfacciaGestoreSpese interfacciaGUI, ParametriDiConfigurazione configXML) { // 03)
		tabella = new TableView<>();
		voci = FXCollections.observableArrayList();
		this.interfacciaGUI = interfacciaGUI;

		tabella.setFixedCellSize(32); // 04)
		tabella.setPrefSize(1200, configXML.numeroRigheTabella * tabella.getFixedCellSize() + 30);
		tabella.setMaxSize(tabella.getPrefWidth(), tabella.getPrefHeight());
		
		TableColumn<VoceSpesaBean, String> colonnaCategoria = new TableColumn<>("Categoria"); // 05)
		colonnaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		colonnaCategoria.setPrefWidth(300); colonnaCategoria.setMaxWidth(colonnaCategoria.getPrefWidth());
		
		TableColumn<VoceSpesaBean, Double> colonnaImporto = new TableColumn<>("Importo"); // 05)
		colonnaImporto.setCellValueFactory(new PropertyValueFactory<>("importo"));
		colonnaImporto.setPrefWidth(100); colonnaImporto.setMaxWidth(colonnaImporto.getPrefWidth());
		
		TableColumn<VoceSpesaBean, LocalDate> colonnaData = new TableColumn<>("Data"); // 05)
		colonnaData.setCellValueFactory(new PropertyValueFactory<>("data"));
		colonnaData.setPrefWidth(150); colonnaData.setMaxWidth(colonnaData.getPrefWidth());
		
		TableColumn<VoceSpesaBean, String> colonnaDescrizione = new TableColumn<>("Descrizione"); // 05)
		colonnaDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
		colonnaDescrizione.setPrefWidth(tabella.getPrefWidth() - colonnaCategoria.getWidth() - 
				colonnaImporto.getWidth() - colonnaData.getWidth() - 2);
		colonnaDescrizione.setMaxWidth(colonnaDescrizione.getPrefWidth());
		
		tabella.setItems(voci); // 06)
		tabella.getColumns().add(colonnaCategoria);	tabella.getColumns().add(colonnaImporto);
		tabella.getColumns().add(colonnaData); tabella.getColumns().add(colonnaDescrizione);
	}

	public void caricaVoci(List<VoceSpesa> vociSpesa) { // 07)
		voci.clear();
		
		for (VoceSpesa v : vociSpesa) {
			voci.add(new VoceSpesaBean(v));
		}
	}

	public void eliminaVoceSelezionata() { // 08)
		interfacciaGUI.getGestoreSpese().eliminaVoceSpesa(
				tabella.getSelectionModel().getSelectedItem().toVoceSpesa()
		);
	}

	public Node getGUI() { // 09)
		return tabella;
	}
}

/* Note:
	00) TabellaSpese si occupa le voci di spesa dell'utente sotto forma di tabella. Permette di selezionare una
		riga della tabella per eliminare la relativa voce spesa dal database
	01) E' la tabella che si occupa di mostrare i dati
	02) Contiene la parte di "backend" della tabella, ovvero la lista dei beans che contengono i dati mostrati
	03) Inizializza la TabellaSpese secondo i parametri di configurazione forniti
	04) Configura l'aspetto grafico della tabella: altezza delle righe e dimensioni della tabella
	05) Crea le colonne della tabella, configurandone il nome, la factory delle celle e la larghezza
	06) Associa l'observable list e le colonne alla tabella
	07) Svuota la tabella e la riempie con le nuove vociSpesa
	08) Preleva i dati della riga selezionata dall'utente e li passa al metodo middleware responsabile 
		all'eliminazione delle voci spesa
	09) Restituisce l'oggetto grafico della classe, ovvero la TableView, cosi' da poter essere inserita nella GUI
*/