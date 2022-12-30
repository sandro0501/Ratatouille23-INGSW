CREATE DATABASE  IF NOT EXISTS ratatouilledb;
USE ratatouilledb;
-- ================================================== -- 
CREATE TABLE IF NOT EXISTS ristorante
(
	idRistorante INT AUTO_INCREMENT PRIMARY KEY,
	denominazione VARCHAR(100) NOT NULL,
	numeroTelefono VARCHAR(20) NOT NULL,
	indirizzo VARCHAR(100) NOT NULL,
	turistico BOOLEAN NOT NULL,
	urlFoto VARCHAR(200) 
	
)ENGINE=InnoDB;
-- ================================================== -- 
CREATE TABLE IF NOT EXISTS utente
(
	idUtente INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	cognome VARCHAR(100) NOT NULL,
	email  VARCHAR(100) NOT NULL,
	password VARCHAR(50) NOT NULL, 
	ruolo ENUM ('Amministratore','Supervisore','AddettoAllaCucina', 'AddettoAlServizio') NOT NULL,
	super BOOLEAN NOT NULL,
	ristorante INT NOT NULL
	
)ENGINE=InnoDB;

ALTER TABLE utente ADD
(
	CONSTRAINT FK_LAVORO FOREIGN KEY (ristorante) REFERENCES ristorante(idRistorante) ON DELETE CASCADE,
	CONSTRAINT EMAIL_LEGALE CHECK (email LIKE '_%@_%.__%'),
	CONSTRAINT EMAIL_UNICA UNIQUE (email),
	CONSTRAINT SOLO_AMMINISTRATORE_SUPER CHECK ((ruolo LIKE 'Amministratore') OR (super = 0))
);
-- ================================================== -- 
CREATE TABLE IF NOT EXISTS avviso
(
	idAvviso INT AUTO_INCREMENT PRIMARY KEY,
	oggetto VARCHAR(100) NOT NULL,
	corpo VARCHAR(500) NOT NULL,
	dataCreazione DATE NOT NULL,
	autore INT NOT NULL

)ENGINE=InnoDB;

ALTER TABLE avviso ADD
(
	CONSTRAINT FK_CREAZIONE_AVVISO FOREIGN KEY (autore) REFERENCES utente(idUtente) ON DELETE CASCADE
);
-- ================================================== -- 
CREATE TABLE IF NOT EXISTS bacheca
(
	idAvviso INT NOT NULL,
	idUtente INT NOT NULL,
	visibile BOOLEAN NOT NULL DEFAULT '0',
	visualizzato BOOLEAN NOT NULL DEFAULT '1'

)ENGINE=InnoDB;

ALTER TABLE bacheca ADD
(
	CONSTRAINT FK_UTENTE_BACHECA FOREIGN KEY (idUtente) REFERENCES utente(idUtente) ON DELETE CASCADE,
	CONSTRAINT FK_AVVISO_BACHECA FOREIGN KEY (idAvviso) REFERENCES avviso(idAvviso)ON DELETE CASCADE,
	CONSTRAINT AVVISI_NON_VISUALIZZATI_VISIBILI CHECK ((((0 <> visualizzato) IS TRUE) OR ((0 <> visibile) IS TRUE)))
);
-- ================================================== -- 
CREATE TABLE IF NOT EXISTS sezionemenu
(
	idSezioneMenu INT AUTO_INCREMENT PRIMARY KEY,
	titolo VARCHAR(100) NOT NULL,
	posizione INT NOT NULL,
	ristorante INT NOT NULL 
	
)ENGINE=InnoDB;

ALTER TABLE sezionemenu ADD
(
	CONSTRAINT FK_SEZIONE_MENU_RISTORANTE FOREIGN KEY (ristorante) REFERENCES ristorante(idRistorante) ON DELETE CASCADE,
	CONSTRAINT TITOLO_SEZIONE_UNICO UNIQUE (titolo, ristorante),
	CONSTRAINT POSIZIONE_LEGALE CHECK (posizione >= 0)
);
-- ================================================== -- 
CREATE TABLE IF NOT EXISTS elemento
(
	idElemento INT AUTO_INCREMENT PRIMARY KEY,
	denominazionePrincipale VARCHAR(100) NOT NULL,
	denominazioneSecondaria VARCHAR(100),
	descrizionePrincipale VARCHAR(500) NOT NULL,
	descrizioneSecondaria VARCHAR(500),
	costo DOUBLE NOT NULL,
	posizione INT NOT NULL,
	sezioneMenu INT NOT NULL 
	
)ENGINE=InnoDB;

ALTER TABLE elemento ADD
(
	CONSTRAINT FK_APPARTENENZA_ELEMENTO_SEZIONEMENU FOREIGN KEY (sezioneMenu) REFERENCES sezioneMenu(idSezioneMenu) ON DELETE CASCADE,
	CONSTRAINT UNICA_DENOMINAZIONE_PRINCIPALE UNIQUE (denominazionePrincipale, sezioneMenu),
	CONSTRAINT UNICA_DENOMINAZIONE_SECONDARIA UNIQUE (denominazioneSecondaria, sezioneMenu),
	CONSTRAINT FK_COSTO_LEGALE CHECK (costo > 0.0)
);
-- ================================================== -- 
CREATE TABLE IF NOT EXISTS prodotto
(
	idProdotto INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	descrizione VARCHAR(500) NOT NULL,
	unita VARCHAR(10) NOT NULL,
	costoAcquisto VARCHAR (20) NOT NULL,
	quantita DOUBLE NOT NULL,
	soglia DOUBLE NOT NULL,
	ristorante INT NOT NULL

)ENGINE=InnoDB;

ALTER TABLE prodotto ADD
(
	CONSTRAINT FK_DISPENSA FOREIGN KEY (ristorante) REFERENCES ristorante(idRistorante) ON DELETE CASCADE,
	CONSTRAINT NOME_PRODOTTO_UNICO UNIQUE (nome, ristorante),
	CONSTRAINT QUANTITA_LEGALE CHECK (quantita > 0.0),
	CONSTRAINT SOGLIA_LEGALE CHECK (soglia > 0.0)
);
-- ================================================== -- 
CREATE TABLE IF NOT EXISTS preparazione
(
	elementoAssociato INT NOT NULL,
	prodottoAssociato INT NOT NULL,
	quantitaNecessaria DOUBLE NOT NULL
)ENGINE=InnoDB;

ALTER TABLE preparazione ADD
(
	CONSTRAINT FK_ELEMENTO FOREIGN KEY (elementoAssociato) REFERENCES elemento(idElemento) ON DELETE CASCADE,
	CONSTRAINT FK_PRODOTTO FOREIGN KEY (prodottoAssociato) REFERENCES prodotto(idProdotto) ON DELETE CASCADE,
	CONSTRAINT QUANTITA_NECESSRIA_LEGALE CHECK (quantitaNecessaria > 0.0)
);
-- ================================================== -- 
CREATE TABLE IF NOT EXISTS allergene
(
	idAllergene INT AUTO_INCREMENT PRIMARY KEY,
	nome ENUM('Crostacei','Molluschi','Uova','Soia','Sedano','Solfiti',
	'Lupini','Lattosio','Pesce','Senape','Noci','Arachidi','Sesamo','Glutine') NOT NULL 
)ENGINE=InnoDB;

ALTER TABLE allergene ADD
(
	CONSTRAINT NOME_ALLERGENE_UNICO UNIQUE (nome)
);

-- ================================================== -- 
CREATE TABLE IF NOT EXISTS listaAllergeni
(
	idAllergene INT NOT NULL,
	idProdotto INT NOT NULL 
	
)ENGINE=InnoDB;

ALTER TABLE listaAllergeni ADD
(
	CONSTRAINT FK_ALLERGENE FOREIGN KEY (idAllergene) REFERENCES allergene(idAllergene) ON DELETE CASCADE,
	CONSTRAINT FK_PRODOTTO_ALLERGENE FOREIGN KEY (idProdotto) REFERENCES prodotto(idProdotto) ON DELETE CASCADE
);
-- ================================================== -- 

-- ================================================== -- 
-- TRIGGER E PROCEDURE (DA INSERIRE A PARTE) 
-- ================================================== --

DELIMITER ;;
CREATE TRIGGER AvvisiSoloDaGestori 
BEFORE INSERT ON avviso 
FOR EACH ROW 
BEGIN
	DECLARE Conta INT UNSIGNED DEFAULT 0;
	
	SELECT COUNT(*) INTO Conta
	FROM utente
	WHERE utente.idUtente = new.autore AND utente.ruolo <> 'Amministratore' AND utente.ruolo <> 'Supervisore';
	
	IF Conta = 1 THEN SIGNAL SQLSTATE "10000"
			SET MESSAGE_TEXT = "Non sei autorizzato a creare l'avviso";
    END IF;
END;;
DELIMITER ;






















