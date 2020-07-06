# Gestore Spese
Progetto in Java di 3 cfu per il corso [**Programmazione Avanzata**](https://elearn.ing.unipi.it/course/view.php?id=430) (Ingegneria Informatica - UniPi, A.A. 2019-20).

Nel file Documentazione.pdf è presente la documentazione del progetto, comprensiva di casi d'uso e schema UML delle classi.

L'applicazione è stata sviluppata usando Java 8 e JavaFX 2.2, oltre alle librerie di terze parti presenti nella cartella *libs/*

## Come eseguire il progetto
1. Scaricare i file del progetto localmente
1. Installare Java 8 e JavaFX 2.2
1. Installare MySQL 5.2 o superiori ed eseguire lo script [DumpDatabase.sql](https://github.com/RiccardoSagramoni/Gestore-Spese/blob/master/DumpDatabase.sql) per la creazione corretta del database
1. Avviare il DBMS MySQL sulla porta 3306 (configurabile per il lato client via file di configurazione XML)
1. Compilare i file **.java** presenti in *src/*, includendo attraverso il comando classpath le librerie presenti in *libs/*
1. Lanciare **ServerLogEventiGUI.class** dalla cartella root del progetto (**NON** dalla cartella che contiene i sorgenti)
1. Lanciare **GestoreSpese.class** dalla cartella root del progetto (**NON** dalla cartella che contiene i sorgenti)
