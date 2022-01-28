CREATE TABLE IF NOT EXISTS logs.LOG_LIST 
	(
		ID INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
		PROGRAM_ID VARCHAR(100) NOT NULL,
		SERVER_ID VARCHAR(30) NOT NULL,
		LOG_PATH VARCHAR(100) NOT NULL,
		LOG_FILENAME VARCHAR(100) NOT NULL,
		DAY_CUT VARCHAR(1) NOT NULL default 'N',
		DEL_CYCLE INT(5) NOT NULL default 0, 
		BACKUP_CYCLE INT(5) NOT NULL default 0,
		BACKUP_PATH VARCHAR(100),
		USE_FLAG VARCHAR(1) NOT NULL default 'Y',
		REG_DTD   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
		UPT_DTD   DATETIME
	)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS logs.SERVER_INFO 
	(
		ID INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
		SERVER_ID VARCHAR(30) NOT NULL,
		IP VARCHAR(100) NOT NULL,
		PORT VARCHAR(5) NOT NULL,
		USER VARCHAR(100) NOT NULL,
		PASSWORD VARCHAR(100) NOT NULL,		
		REG_DTD   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
		UPT_DTD   DATETIME  
	)
ENGINE = InnoDB;


