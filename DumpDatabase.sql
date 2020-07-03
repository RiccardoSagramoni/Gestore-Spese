CREATE DATABASE  IF NOT EXISTS `gestore_spese` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `gestore_spese`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: gestore_spese
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `spesa`
--

DROP TABLE IF EXISTS `spesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spesa` (
  `username` varchar(50) NOT NULL,
  `id_spesa` int(10) unsigned NOT NULL,
  `categoria` varchar(50) NOT NULL,
  `importo` float unsigned NOT NULL,
  `data` date NOT NULL,
  `descrizione` varchar(1500) NOT NULL,
  PRIMARY KEY (`username`,`id_spesa`),
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `utente` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spesa`
--

LOCK TABLES `spesa` WRITE;
/*!40000 ALTER TABLE `spesa` DISABLE KEYS */;
INSERT INTO `spesa` VALUES ('Riccardo Sagramoni',0,'Animali domestici',12,'2020-06-05','Cibo per gatti'),('Riccardo Sagramoni',1,'Hobbies e tempo libero',50,'2020-06-04','Manuale di Dungeons&Dragons'),('Riccardo Sagramoni',2,'Trasporto',200,'2020-05-13','Aereo per Madrid'),('Riccardo Sagramoni',3,'Istruzione',30,'2020-06-02','Libro su Java 8'),('Riccardo Sagramoni',4,'Generi alimentari',40,'2020-06-03','Spesa all\'Esselunga'),('Riccardo Sagramoni',5,'Vestiario',12,'2020-06-04','Biancheria intima'),('Riccardo Sagramoni',6,'Vestiario',15,'2020-05-12','Maglietta'),('Riccardo Sagramoni',7,'Spese bancarie',1.5,'2020-06-01','Costo gestione del cc'),('Riccardo Sagramoni',8,'Utenze',23,'2020-06-01','Ricarica telefono');
/*!40000 ALTER TABLE `spesa` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `spesa_BINS`
BEFORE INSERT ON `spesa` 
FOR EACH ROW
BEGIN
	DECLARE _spesa_index INT UNSIGNED DEFAULT NULL;

	IF new.data > CURRENT_DATE() THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Impossibile aggiungere una spesa futura';
	END IF;

	SELECT next_spesa_index INTO _spesa_index
    FROM utente u 
    WHERE u.username = new.username
	FOR UPDATE;

	UPDATE utente u
	SET u.next_spesa_index = u.next_spesa_index + 1
	WHERE u.username = new.username;

	IF _spesa_index IS NULL THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'L\'utente non esiste';
	END IF;

	SET new.id_spesa = _spesa_index;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `spesa_BUPD`
BEFORE UPDATE ON `spesa`
FOR EACH ROW
BEGIN
	IF new.data > CURRENT_DATE() THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Impossibile aggiungere una spesa futura';
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `username` varchar(50) NOT NULL,
  `next_spesa_index` int(10) unsigned NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('Riccardo Sagramoni',9);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `utente_BINS` 
BEFORE INSERT ON `utente` 
FOR EACH ROW
BEGIN
	SET new.next_spesa_index = 0;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'gestore_spese'
--

--
-- Dumping routines for database 'gestore_spese'
--
/*!50003 DROP PROCEDURE IF EXISTS `assicura_esistenza_utente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `assicura_esistenza_utente`(IN user VARCHAR(50))
BEGIN
	DECLARE u VARCHAR(50);
	
	SELECT username INTO u
	FROM utente
	WHERE username = user;
	
	IF u IS NULL THEN
		INSERT INTO utente VALUES (user, 0);
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-05 20:00:13
