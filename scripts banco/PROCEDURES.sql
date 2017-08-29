-- VERIFICA SE O USUARIO EXISTE
DELIMITER $$
CREATE PROCEDURE SP_VERIFICA_USUARIO (P_USUARIO VARCHAR(45), P_SENHA VARCHAR(45))
BEGIN
	
    SELECT * FROM USUARIO
	WHERE usu_login = P_USUARIO AND usu_senha = MD5(P_SENHA);

END $$
DELIMITER ;


-- -----------------------------------------------
-- VERIFICA SE O LOGIN JA EXISTE
DELIMITER $$
CREATE PROCEDURE SP_VERIFICA_LOGIN_EXISTENTE (P_USUARIO VARCHAR(45))
BEGIN
	
    SELECT COUNT(*) FROM USUARIO
	WHERE usu_login = P_USUARIO;

END $$
DELIMITER ;



-- -----------------------------------------------
-- INSERE NOVO USUÁRIO
DELIMITER $$
CREATE PROCEDURE SP_INSERE_USUARIO (P_LOGIN VARCHAR(45), P_SENHA VARCHAR(45), P_NOME VARCHAR(100), P_DIRETORIO TEXT)
BEGIN
	
    INSERT INTO USUARIO (usu_login, usu_senha, usu_nome, usu_diretorio_casos)
    VALUES (P_LOGIN, MD5(P_SENHA), P_NOME, P_DIRETORIO);
    
END $$
DELIMITER ;


-- -----------------------------------------------
-- ALTERA DADOS DO USUÁRIO
DELIMITER $$
CREATE PROCEDURE SP_ALTERA_USUARIO (P_ID INT, P_LOGIN VARCHAR(45), P_SENHA VARCHAR(45), P_NOME VARCHAR(100))
BEGIN
	
    UPDATE USUARIO 
    SET usu_login = P_LOGIN, 
		usu_senha = MD5(P_SENHA),
        usu_nome = P_NOME
    WHERE usu_id = P_ID;
    
END $$
DELIMITER ;


-- -----------------------------------------------
-- LISTA TODOS OS USUARIOS
DELIMITER $$
CREATE PROCEDURE SP_LISTA_USUARIOS (P_ID INT)
BEGIN
	
    SELECT * FROM USUARIO
	WHERE usu_id != P_ID;

END $$
DELIMITER ;


-- -----------------------------------------------
-- LISTA OS USUARIOS COM ACESSO A UM CASO
DELIMITER $$
CREATE PROCEDURE SP_LISTA_USUARIOS_ACESSO (P_ID INT, P_CASO INT)
BEGIN
	
	SELECT usu_id, usu_nome
	FROM USUARIO
	WHERE usu_id IN (SELECT usc_usu_id FROM USUARIO_CASO
					 WHERE usc_usu_id != P_ID AND usc_cas_id = P_CASO);

END $$
DELIMITER ;


-- -----------------------------------------------
-- LISTA CASOS DE UM USUARIO ESPECÍFICO
DELIMITER $$
CREATE PROCEDURE SP_LISTA_CASOS (P_ID INT, P_TIPO_ACESSO VARCHAR(50))
BEGIN
	
	SELECT * FROM CASO
	WHERE cas_id IN (SELECT usc_cas_id FROM USUARIO_CASO
					 WHERE usc_usu_id = P_ID AND usc_acesso = P_TIPO_ACESSO);

END $$
DELIMITER ;



-- -----------------------------------------------
-- INSERE NOVO CASO
DELIMITER $$
CREATE PROCEDURE SP_INSERE_CASO (P_DESCRICAO VARCHAR(100), P_NOME_INVESTIGADO VARCHAR(200), P_DIRETORIO VARCHAR(200))
BEGIN

	INSERT INTO CASO (cas_descricao, cas_nome_investigado, cas_data_criacao, cas_diretorio) 
    VALUES(P_DESCRICAO, P_NOME_INVESTIGADO, sysdate(), P_DIRETORIO);

END $$
DELIMITER ;



-- -----------------------------------------------
-- EXCLUIR CASO
DELIMITER $$
CREATE PROCEDURE SP_EXCLUI_CASO (P_ID INT)
BEGIN

	DELETE FROM USUARIO_CASO
    WHERE usc_cas_id = P_ID;

	DELETE FROM CASO
    WHERE cas_id = P_ID;
	
END $$
DELIMITER ;



-- -----------------------------------------------
-- RELACIONA CASO COM USUARIO
DELIMITER $$
CREATE PROCEDURE SP_INSERE_CASO_USUARIO (P_TIPO_ACESSO VARCHAR(50), P_ID INT, P_CASO INT)
BEGIN

	DECLARE V_ULTIMO_ID INT;
    
    SET V_ULTIMO_ID = (SELECT MAX(cas_id) FROM CASO);
    
    IF(P_CASO = 0) THEN
		INSERT INTO USUARIO_CASO (usc_usu_id, usc_cas_id, usc_acesso)
		VALUES (P_ID, V_ULTIMO_ID, P_TIPO_ACESSO);   
    ELSE
		INSERT INTO USUARIO_CASO (usc_usu_id, usc_cas_id, usc_acesso)
		VALUES (P_ID, P_CASO, P_TIPO_ACESSO);   
    END IF;
    
END $$
DELIMITER ;



-- -----------------------------------------------
-- LISTA DISPOSITIVOS DE CASO ESPECÍFICO
DELIMITER $$
CREATE PROCEDURE SP_LISTA_DISPOSITIVOS_CASO (P_CASO INT)
BEGIN

	SELECT * FROM DISPOSITIVO
	WHERE dps_cas_id = P_CASO;

END $$
DELIMITER ;



-- -----------------------------------------------
-- VERIFICA SE NOME DO CASO JÁ EXISTE
DELIMITER $$
CREATE PROCEDURE SP_VERIFICA_NOME_CASO (P_DESCRICAO VARCHAR(20))
BEGIN

	SELECT COUNT(*) FROM CASO 
    WHERE cas_descricao = P_DESCRICAO;

END $$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA DISPOSITIVO POR NOME
DELIMITER $$
CREATE PROCEDURE SP_PESQUISA_DISPOSITIVO_NOME (P_NOME_DISPOSITIVO VARCHAR(200))
BEGIN

	SELECT * FROM DISPOSITIVO
	WHERE dps_nome = P_NOME_DISPOSITIVO;

END $$
DELIMITER ;



-- -----------------------------------------------
-- VERIFICA SE O DISPOSITIVO JÁ TEM TABELAS IMPORTADAS
DELIMITER $$
CREATE PROCEDURE SP_VERIFICA_IMPORTACAO_DISPOSITIVO (P_TABELA_DISPOSITIVO VARCHAR(200))
BEGIN
	
    SET @s = CONCAT(' SELECT COUNT(*) FROM ', P_TABELA_DISPOSITIVO);
					
    PREPARE stmt FROM @s;
	EXECUTE stmt;

END $$
DELIMITER ;


-- -----------------------------------------------
-- INSERE NOVO DISPOSITIVO
DELIMITER $$
CREATE PROCEDURE SP_INSERE_DISPOSITIVO (P_CASO INT, P_NOME VARCHAR(200), P_FABRICANTE VARCHAR(50), P_IDIOMA VARCHAR(10), 
									    P_MODELO VARCHAR(45), P_NUMEROVERSAO VARCHAR(45), P_NUMEROMODELO VARCHAR(45), 
									    P_VERSAOANDROID VARCHAR(5), P_VERSAOSISTEMA VARCHAR(200), P_DIRETORIO VARCHAR(200),
									    P_TABELA_CONTATO VARCHAR(200), P_TABELA_MENSAGEM VARCHAR(200))
BEGIN

	INSERT INTO DISPOSITIVO (dps_cas_id, dps_nome, dps_fabricante, dps_idioma, dps_modelo, dps_numero_versao, dps_numero_modelo, 
							 dps_versao_android, dps_versao_sistema, dps_diretorio, dps_tabela_contato, dps_tabela_mensagem) 
    VALUES (P_CASO, P_NOME, P_FABRICANTE, P_IDIOMA, P_MODELO, P_NUMEROVERSAO, P_NUMEROMODELO, 
		    P_VERSAOANDROID, P_VERSAOSISTEMA, P_DIRETORIO, P_TABELA_CONTATO, P_TABELA_MENSAGEM);
       
	-- APÓS CRIAR REGISTRO DE NOVO DISPOSITIVO, CRIA AS TABELAS CONTATO E MENSAGEM
	CALL SP_CRIA_TABELA_CONTATO (P_TABELA_CONTATO);
    CALL SP_CRIA_TABELA_MENSAGEM (P_TABELA_MENSAGEM, P_TABELA_CONTATO);
	
          
END $$
DELIMITER ;



-- -----------------------------------------------
-- EXCLUI TABELAS CONTATO E MENSAGEM DE UM DISPOSITIVO
DELIMITER $$
CREATE PROCEDURE SP_EXCLUI_TABELAS_DISPOSITIVO (P_ID_DISPOSITIVO INT)
BEGIN

	DECLARE V_TAB_MENSAGEM VARCHAR (200);
    DECLARE V_TAB_CONTATO VARCHAR (200);
    
    SET V_TAB_MENSAGEM = (SELECT dps_tabela_mensagem FROM DISPOSITIVO WHERE dps_id = P_ID_DISPOSITIVO);
	SET V_TAB_CONTATO = (SELECT dps_tabela_contato FROM DISPOSITIVO WHERE dps_id = P_ID_DISPOSITIVO);

	SET @tabMensagem = CONCAT(' DROP TABLE ', V_TAB_MENSAGEM);
	PREPARE stmtm FROM @tabMensagem;
	EXECUTE stmtm;
    
    SET @tabContato = CONCAT(' DROP TABLE ', V_TAB_CONTATO);
	PREPARE stmtc FROM @tabContato;
	EXECUTE stmtc;
	
END $$
DELIMITER ;



-- -----------------------------------------------
-- EXCLUIR DISPOSITIVO
DELIMITER $$
CREATE PROCEDURE SP_EXCLUI_DISPOSITIVO (P_ID_DISPOSITIVO INT)
BEGIN

	CALL SP_EXCLUI_TABELAS_DISPOSITIVO (P_ID_DISPOSITIVO);

	DELETE FROM DISPOSITIVO
    WHERE DPS_ID = P_ID_DISPOSITIVO;
	
END $$
DELIMITER ;



-- -----------------------------------------------
-- CRIA TABELA CONTATO
DELIMITER $$
CREATE PROCEDURE SP_CRIA_TABELA_CONTATO (P_TABELACONTATO VARCHAR(200))
BEGIN

	SET @s = CONCAT(' CREATE TABLE ', P_TABELACONTATO, '('
					'   ctt_id INT NOT NULL, ',
					'   ctt_dps_id INT NOT NULL, ',
					'   ctt_jid VARCHAR(45) NULL, ',
					'   ctt_status VARCHAR(200) NULL, ',
					'   ctt_status_date DATE NULL, ',
					'   ctt_status_time TIME(6) NULL, ', 
					'   ctt_number VARCHAR(45) NULL, ',
					'   ctt_display_name VARCHAR(100) NULL, ', 
					'   ctt_wa_name VARCHAR(100) NULL, ',
					'   PRIMARY KEY (ctt_id), ', 
					'   INDEX fk_',P_TABELACONTATO,'_dispositivo1_idx (ctt_dps_id ASC), ',
					'   CONSTRAINT fk_',P_TABELACONTATO,'_dispositivo1 ',
					'     FOREIGN KEY (ctt_dps_id) ',
					'     REFERENCES dispositivo (dps_id)  ',
					'     ON DELETE NO ACTION  ',
					'     ON UPDATE NO ACTION)  ',
					' ENGINE = InnoDB; ');

					
    PREPARE stmt FROM @s;
	EXECUTE stmt;
          
END $$
DELIMITER ;



-- -----------------------------------------------
-- CRIA TABELA MENSAGEM
DELIMITER $$
CREATE PROCEDURE SP_CRIA_TABELA_MENSAGEM (P_TABELA_MENSAGEM VARCHAR(200), P_TABELA_CONTATO VARCHAR(200))
BEGIN

	SET @s = CONCAT(' CREATE TABLE ', P_TABELA_MENSAGEM, '('
					'   men_id INT NOT NULL, '
					'   men_dps_id INT NOT NULL, '
					'   men_key_remote_jid INT NULL, '
					'   men_remote_resource VARCHAR(50) NULL, '
					'   men_key_from_me TINYINT(1) NOT NULL, '
					'   men_data LONGTEXT NULL, '
					'   men_date DATE NULL, '
					'   men_time TIME(6) NULL, '
					'   men_media_url VARCHAR(200) NULL, '
					'   men_media_mime_type VARCHAR(40) NULL, '
					'   men_media_size INT NULL, '
					'   men_media_name VARCHAR(1000) NULL, '
					'   men_media_caption VARCHAR(400) NULL, '
					'   PRIMARY KEY (men_id), '
					'   INDEX fk_',P_TABELA_MENSAGEM,'_dispositivo_idx (men_dps_id ASC), '
					'   INDEX fk_',P_TABELA_MENSAGEM,'_CONTATO1_idx (men_key_remote_jid ASC), '
					'   CONSTRAINT fk_',P_TABELA_MENSAGEM,'_dispositivo '
					'   FOREIGN KEY (men_dps_id) '
					'     REFERENCES dispositivo (dps_id) '
					'      ON DELETE NO ACTION '
					'      ON UPDATE NO ACTION, ' 
					'   CONSTRAINT fk_',P_TABELA_MENSAGEM,'_CONTATO1 '
					'   FOREIGN KEY (men_key_remote_jid) '
					'     REFERENCES ',P_TABELA_CONTATO,' (ctt_id) '
					'      ON DELETE NO ACTION '
					'      ON UPDATE NO ACTION) '
					'   ENGINE = InnoDB; ');
			
    PREPARE stmt FROM @s;
	EXECUTE stmt;
          
END $$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA CONTATO POR JID
DELIMITER $$
CREATE PROCEDURE SP_PESQUISA_CONTATO_JID (P_TABELA_CONTATO VARCHAR(200), P_JID VARCHAR(100))
BEGIN
	
    SET @s = CONCAT(' SELECT * FROM ', P_TABELA_CONTATO,
					' WHERE CTT_JID = "', P_JID,'"');
    PREPARE stmt FROM @s;
	EXECUTE stmt;

END $$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA TODOS CONTATOS
DELIMITER $$
CREATE PROCEDURE SP_PESQUISACONTATOS_TODOS(P_TABELA_CONTATO VARCHAR(200))
BEGIN
	SET @s = CONCAT(' SELECT ctt_id, ctt_dps_id, ctt_jid, ctt_status, ctt_status_date, ctt_status_time, ctt_number, ',
					' ctt_display_name FROM ', P_TABELA_CONTATO,
					' WHERE ctt_jid LIKE "%@s.whatsapp.net" AND (ctt_display_name IS NOT NULL AND ctt_wa_name IS NULL) ',
                    '       OR (ctt_display_name IS NOT NULL AND ctt_wa_name IS NOT NULL) ',
                    ' UNION ',
                    ' SELECT ctt_id, ctt_dps_id, ctt_jid, ctt_status, ctt_status_date, ctt_status_time, ctt_number, ',
                    ' ctt_wa_name FROM ', P_TABELA_CONTATO,
                    ' WHERE ctt_jid LIKE "%@s.whatsapp.net" AND (ctt_wa_name IS NOT NULL AND ctt_display_name IS NULL) ',
                    '       OR (ctt_wa_name IS NULL AND ctt_display_name IS NULL) ',
					' ORDER BY ctt_display_name ASC');
					
    PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA CONTATOS DA AGENDA
DELIMITER $$
CREATE PROCEDURE SP_PESQUISACONTATOS_AGENDA(P_TABELA_CONTATO VARCHAR(200))
BEGIN
	SET @s = CONCAT(' SELECT * FROM ', P_TABELA_CONTATO,
					' WHERE ctt_jid LIKE "%@s.whatsapp.net" AND ctt_display_name IS NOT NULL ',
                    ' ORDER BY ctt_display_name ASC');
	
    PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA CONTATOS NÃO SALVOS
DELIMITER $$
CREATE PROCEDURE SP_PESQUISACONTATOS_NAOSALVOS(P_TABELA_CONTATO VARCHAR(200))
BEGIN
	SET @s = CONCAT(' SELECT * FROM ', P_TABELA_CONTATO,
					' WHERE ctt_jid LIKE "%@s.whatsapp.net" AND (ctt_wa_name IS NOT NULL AND ctt_display_name IS NULL) ',
                    ' 	    OR (ctt_wa_name IS NULL AND ctt_display_name IS NULL) ', 
                    ' ORDER BY ctt_wa_name ASC ');
    
    PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA GRUPOS
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAGRUPOS(P_TABELA_CONTATO VARCHAR(200))
BEGIN
	SET @s = CONCAT(' SELECT * FROM ', P_TABELA_CONTATO,
					' WHERE ctt_jid LIKE "%@g.us" ');
    
    PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;



/******************************************************************
CONSULTAS POR CONTATO ESPECÍFICO
*******************************************************************/

-- -----------------------------------------------
-- PESQUISA MENSAGENS DE CONTATO ESPECÍFICO
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_CONTATO(P_TABELA_MENSAGEM VARCHAR(200), P_ID INT)
BEGIN
	SET @s = CONCAT(' SELECT * FROM ', P_TABELA_MENSAGEM , 
					' WHERE men_key_remote_jid = ', P_ID);
					
    PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA MENSAGENS POR PALAVRA CHAVE DE CONTATO ESPECÍFICO
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_CONTATO_PALAVRACHAVE(P_TABELA_MENSAGEM VARCHAR(200), P_ID INT, P_PALAVRA_CHAVE VARCHAR(100))
BEGIN

	SET @s = CONCAT(' SELECT * FROM ', P_TABELA_MENSAGEM , 
					' WHERE men_key_remote_jid = ', P_ID, ' AND men_data LIKE "%', P_PALAVRA_CHAVE, '%"');
					
    PREPARE stmt FROM @s;
	EXECUTE stmt;
                                                                                                                                                   
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA MENSAGENS POR DATA DE CONTATO ESPECÍFICO
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_CONTATO_DATA(P_TABELA_MENSAGEM VARCHAR(200), P_ID INT, P_DATA_INICIAL DATE, P_DATA_FINAL DATE)
BEGIN
	SET @s = CONCAT(' SELECT * FROM ', P_TABELA_MENSAGEM , 
					' WHERE men_key_remote_jid = ', P_ID, ' AND men_date BETWEEN "', P_DATA_INICIAL, '" AND "', P_DATA_FINAL,'"');
                    
    PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA MENSAGENS POR DATA e HORA DE CONTATO ESPECÍFICO
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_CONTATO_DATA_HORA(P_TABELA_MENSAGEM VARCHAR(200), P_ID INT, P_DATA DATE, P_HORA_INICIAL TIME, P_HORA_FINAL TIME)
BEGIN
	SET @s = CONCAT(' SELECT * FROM ', P_TABELA_MENSAGEM , 
					' WHERE men_key_remote_jid = ', P_ID, ' AND men_date = "', P_DATA, '" ',
                    '   AND men_time BETWEEN "', P_HORA_INICIAL, '" AND "', P_HORA_FINAL,'"');
               
	PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA MENSAGENS POR DATA e PALAVRA CHAVE DE CONTATO ESPECÍFICO
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_CONTATO_PALAVRACHAVE_DATA(P_TABELA_MENSAGEM VARCHAR(200), P_ID INT, P_PALAVRACHAVE VARCHAR(100), P_DATA_INICIAL DATE, P_DATA_FINAL DATE)
BEGIN
	SET @s = CONCAT(' SELECT * FROM ', P_TABELA_MENSAGEM , 
					' WHERE men_key_remote_jid = ', P_ID, ' AND men_data LIKE "%', P_PALAVRACHAVE, '%" ',
                    '   AND men_date BETWEEN "', P_DATA_INICIAL, '" AND "', P_DATA_FINAL,'" ');
                    
    PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA MENSAGENS POR DATA, HORA E PALAVRA CHAVE DE CONTATO ESPECÍFICO
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_CONTATO_PALAVRACHAVE_DATA_HORA(P_TABELA_MENSAGEM VARCHAR(200), P_ID INT, P_PALAVRACHAVE VARCHAR(100), P_DATA DATE, P_HORA_INICIAL TIME, P_HORA_FINAL TIME)
BEGIN
	SET @s = CONCAT(' SELECT * FROM ', P_TABELA_MENSAGEM , 
					' WHERE men_key_remote_jid = ', P_ID, ' AND men_data LIKE "%', P_PALAVRACHAVE, '%" AND ',
                    '       men_date = "', P_DATA, '" AND men_time BETWEEN "', P_HORA_INICIAL, '" AND "', P_HORA_FINAL,'"');
               
	PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;




/******************************************************************
CONSULTAS TODAS AS MENSAGENS
Filtra por contatos e grupos
*******************************************************************/

-- -----------------------------------------------
-- PESQUISA TODAS AS MENSAGENS POR PALAVRA CHAVE
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_PALAVRACHAVE(P_TABELA_MENSAGEM VARCHAR(200), P_TABELA_CONTATO VARCHAR(200), P_FILTRO CHAR, P_PALAVRA_CHAVE VARCHAR(100))
BEGIN

	DECLARE V_INSTRUCAO TEXT;
    
    -- pesquisa só mensagens de contatos por palavra chave    
	IF(P_FILTRO = 'C') THEN
		SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								 ' WHERE men_data LIKE "%', P_PALAVRA_CHAVE, '%" AND ctt_jid LIKE "%@s.whatsapp.net" ',
								 ' GROUP BY men_key_remote_jid ');
			
	ELSE 
		-- pesquisa só mensagens de grupos por palavra chave    
		IF(P_FILTRO = 'G') THEN
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
									 ' WHERE men_data LIKE "%', P_PALAVRA_CHAVE, '%" AND ctt_jid LIKE "%@g.us" ',
									 ' GROUP BY men_key_remote_jid ');
		
		ELSE -- pesquisa todas as mensagens por palavra chave    
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
									 ' WHERE men_data LIKE "%', P_PALAVRA_CHAVE, '%" ',
									 ' GROUP BY men_key_remote_jid ');
		END IF;
	END IF;
    
    SET @s = CONCAT(' SELECT count(*), ctt_id, ctt_jid, ctt_display_name, ctt_wa_name FROM ', P_TABELA_MENSAGEM,
					' ', V_INSTRUCAO); 
					
    PREPARE stmt FROM @s;
	EXECUTE stmt;
                                                                                                                                                   
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA TODAS AS MENSAGENS POR DATA
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_DATA(P_TABELA_MENSAGEM VARCHAR(200), P_TABELA_CONTATO VARCHAR(200), P_FILTRO CHAR, P_DATA_INICIAL DATE, P_DATA_FINAL DATE)
BEGIN

	DECLARE V_INSTRUCAO TEXT;
    
    -- pesquisa só mensagens de contatos por DATA   
	IF(P_FILTRO = 'C') THEN
		SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								 ' WHERE men_date BETWEEN "', P_DATA_INICIAL, '" AND "', P_DATA_FINAL,'" AND ctt_jid LIKE "%@s.whatsapp.net" ',
                                 ' GROUP BY men_key_remote_jid ');
	ELSE 
		-- pesquisa só mensagens de grupos por DATA   
		IF(P_FILTRO = 'G') THEN
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_date BETWEEN "', P_DATA_INICIAL, '" AND "', P_DATA_FINAL,'" AND ctt_jid LIKE "%@g.us" ',
                                     ' GROUP BY men_key_remote_jid ');
		ELSE 
			-- pesquisa todas as mensagens por DATA    
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_date BETWEEN "', P_DATA_INICIAL, '" AND "', P_DATA_FINAL,'" ',
                                     ' GROUP BY men_key_remote_jid ');
        END IF;
    END IF;
    
    SET @s = CONCAT(' SELECT count(*), ctt_id, ctt_jid, ctt_display_name, ctt_wa_name FROM ', P_TABELA_MENSAGEM,
					' ', V_INSTRUCAO); 
					
    PREPARE stmt FROM @s;
	EXECUTE stmt;
                                                                                                                                                   
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA TODAS AS MENSAGENS POR PALAVRA CHAVE E DATA
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_PALAVRACHAVE_DATA(P_TABELA_MENSAGEM VARCHAR(200), P_TABELA_CONTATO VARCHAR(200), P_FILTRO CHAR, 
                                           P_PALAVRA_CHAVE VARCHAR(100), P_DATA_INICIAL DATE, P_DATA_FINAL DATE)
BEGIN

	DECLARE V_INSTRUCAO TEXT;
    
    -- pesquisa só mensagens de contatos por DATA   
	IF(P_FILTRO = 'C') THEN
		SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								 ' WHERE men_data LIKE "%', P_PALAVRA_CHAVE, '%" AND men_date BETWEEN "', P_DATA_INICIAL, '" AND "', P_DATA_FINAL,'" AND ctt_jid LIKE "%@s.whatsapp.net" ',
                                 ' GROUP BY men_key_remote_jid ');
	ELSE 
		-- pesquisa só mensagens de grupos por DATA   
		IF(P_FILTRO = 'G') THEN
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_data LIKE "%', P_PALAVRA_CHAVE, '%" AND men_date BETWEEN "', P_DATA_INICIAL, '" AND "', P_DATA_FINAL,'" AND ctt_jid LIKE "%@g.us" ',
                                     ' GROUP BY men_key_remote_jid ');
		ELSE 
			-- pesquisa todas as mensagens por DATA    
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_data LIKE "%', P_PALAVRA_CHAVE, '%" AND men_date BETWEEN "', P_DATA_INICIAL, '" AND "', P_DATA_FINAL,'" ',
                                     ' GROUP BY men_key_remote_jid ');
        END IF;
    END IF;
    
    SET @s = CONCAT(' SELECT count(*), ctt_id, ctt_jid, ctt_display_name, ctt_wa_name FROM ', P_TABELA_MENSAGEM,
					' ', V_INSTRUCAO); 
					
    PREPARE stmt FROM @s;
	EXECUTE stmt;
                                                                                                                                                   
END$$
DELIMITER ;



-- -----------------------------------------------
-- PESQUISA TODAS AS MENSAGENS POR DATA E HORA
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_DATA_HORA(P_TABELA_MENSAGEM VARCHAR(200), P_TABELA_CONTATO VARCHAR(200), 
				                                P_FILTRO CHAR, P_DATA DATE, P_HORA_INICIAL TIME, P_HORA_FINAL TIME)
BEGIN

	DECLARE V_INSTRUCAO TEXT;
    
    -- pesquisa só mensagens de contatos por DATA   
	IF(P_FILTRO = 'C') THEN
		SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								 ' WHERE men_date  = "', P_DATA, '" AND ctt_jid LIKE "%@s.whatsapp.net" ',
                                 '   AND men_time BETWEEN "', P_HORA_INICIAL, '" AND "', P_HORA_FINAL,'" ',
                                 ' GROUP BY men_key_remote_jid ');
                                 
	ELSE 
		-- pesquisa só mensagens de grupos por DATA   
		IF(P_FILTRO = 'G') THEN
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_date  = "', P_DATA, '" AND ctt_jid LIKE "%@g.us" ',
                                     '   AND men_time BETWEEN "', P_HORA_INICIAL, '" AND "', P_HORA_FINAL,'" ',
                                     ' GROUP BY men_key_remote_jid ');
		ELSE 
			-- pesquisa todas as mensagens por DATA    
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_date  = "', P_DATA, '" ',
                                     '   AND men_time BETWEEN "', P_HORA_INICIAL, '" AND "', P_HORA_FINAL,'" ',
                                     ' GROUP BY men_key_remote_jid ');
        END IF;
    END IF;
    
    SET @s = CONCAT(' SELECT count(*), ctt_id, ctt_jid, ctt_display_name, ctt_wa_name FROM ', P_TABELA_MENSAGEM,
					' ', V_INSTRUCAO); 
					
    PREPARE stmt FROM @s;
	EXECUTE stmt;
                                                                                                                                                   
END$$
DELIMITER ;


-- -----------------------------------------------
-- PESQUISA TODAS AS MENSAGENS POR PALAVRA CHAVE, DATA E HORA
DELIMITER $$
CREATE PROCEDURE SP_PESQUISAMENSAGENS_DATA_HORA_PALAVRACHAVE(P_TABELA_MENSAGEM VARCHAR(200), P_TABELA_CONTATO VARCHAR(200), 
				P_FILTRO CHAR, P_DATA DATE, P_HORA_INICIAL TIME, P_HORA_FINAL TIME, P_PALAVRA_CHAVE VARCHAR(100))
BEGIN

	DECLARE V_INSTRUCAO TEXT;
    
    -- pesquisa só mensagens de contatos por DATA   
	IF(P_FILTRO = 'C') THEN
		SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								 ' WHERE men_date  = "', P_DATA, '" AND ctt_jid LIKE "%@s.whatsapp.net" ',
                                 '   AND men_time BETWEEN "', P_HORA_INICIAL, '" AND "', P_HORA_FINAL,'" AND men_data LIKE "%', P_PALAVRA_CHAVE, '%" ',
                                 ' GROUP BY men_key_remote_jid ');
                                 
	ELSE 
		-- pesquisa só mensagens de grupos por DATA   
		IF(P_FILTRO = 'G') THEN
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_date  = "', P_DATA, '" AND ctt_jid LIKE "%@g.us" ',
                                     '   AND men_time BETWEEN "', P_HORA_INICIAL, '" AND "', P_HORA_FINAL,'" AND men_data LIKE "%', P_PALAVRA_CHAVE, '%" ',
                                     ' GROUP BY men_key_remote_jid ');
		ELSE 
			-- pesquisa todas as mensagens por DATA    
			SET V_INSTRUCAO = CONCAT(' INNER JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_date  = "', P_DATA, '"  AND men_data LIKE "%', P_PALAVRA_CHAVE, '%" ',
                                     '   AND men_time BETWEEN "', P_HORA_INICIAL, '" AND "', P_HORA_FINAL,'" ',
                                     ' GROUP BY men_key_remote_jid ');
        END IF;
    END IF;
    
    SET @s = CONCAT(' SELECT count(*), ctt_id, ctt_jid, ctt_display_name, ctt_wa_name FROM ', P_TABELA_MENSAGEM,
					' ', V_INSTRUCAO); 
					
    PREPARE stmt FROM @s;
	EXECUTE stmt;
                                                                                                                                                   
END$$
DELIMITER ;



-- -----------------------------------------------
-- VERIFICA OS FILTROS SELECIONADOS PARA CONSULTAS DE UM CONTATO ESPECIFICO
DELIMITER $$
CREATE PROCEDURE SP_FILTRA_CONSULTAS_CONTATO(P_TABELA_MENSAGEM VARCHAR(200), P_CONTATO INT, P_DATA_INICIAL DATE, 
										   P_DATA_FINAL DATE, P_HORA_INICIAL TIME, P_HORA_FINAL TIME, 
                                           P_PALAVRA_CHAVE VARCHAR(100))
BEGIN
	
	-- consulta por palavra chave
	IF (P_PALAVRA_CHAVE IS NOT NULL && P_DATA_INICIAL IS NULL ) THEN 
		CALL SP_PESQUISAMENSAGENS_CONTATO_PALAVRACHAVE(P_TABELA_MENSAGEM, P_CONTATO, P_PALAVRA_CHAVE);
        
    ELSE 
		-- consulta por data
		IF(P_PALAVRA_CHAVE IS NULL && P_DATA_INICIAL IS NOT NULL && P_DATA_FINAL IS NOT NULL && P_HORA_INICIAL IS NULL) THEN  
			CALL SP_PESQUISAMENSAGENS_CONTATO_DATA(P_TABELA_MENSAGEM, P_CONTATO, P_DATA_INICIAL, P_DATA_FINAL);
		
        ELSE
			-- consulta por palavra chave e data
			IF (P_PALAVRA_CHAVE IS NOT NULL && P_DATA_INICIAL IS NOT NULL && P_DATA_FINAL IS NOT NULL && P_HORA_INICIAL IS NULL) THEN 
				CALL SP_PESQUISAMENSAGENS_CONTATO_PALAVRACHAVE_DATA(P_TABELA_MENSAGEM, P_CONTATO, P_PALAVRA_CHAVE, P_DATA_INICIAL, P_DATA_FINAL);
			
            ELSE
				-- consulta por data e hora
				IF (P_PALAVRA_CHAVE IS NULL && P_DATA_INICIAL IS NOT NULL && P_HORA_INICIAL IS NOT NULL && P_HORA_FINAL IS NULL) THEN
					CALL SP_PESQUISAMENSAGENS_CONTATO_DATA_HORA(P_TABELA_MENSAGEM, P_CONTATO, P_DATA_INICIAL, P_HORA_INICIAL, P_HORA_FINAL);
					
                ELSE 
					-- consulta por palavra, data e hora
					IF (P_PALAVRA_CHAVE IS NOT NULL && P_DATA_INICIAL IS NOT NULL && P_HORA_INICIAL IS NOT NULL && P_HORA_FINAL IS NOT NULL) THEN
						CALL SP_PESQUISAMENSAGENS_CONTATO_PALAVRACHAVE_DATA_HORA(P_TABELA_MENSAGEM, P_CONTATO, P_PALAVRA_CHAVE, P_DATA_INICIAL, P_HORA_INICIAL, P_HORA_FINAL);
					END IF;
                END IF;
            END IF;
        END IF;
	END IF;
                                                                                                                                                   
END$$
DELIMITER ;



-- -----------------------------------------------
-- VERIFICA OS FILTROS SELECIONADOS PARA CONSULTAS DE TODOS / CONTATOS / GRUPOS
DELIMITER $$
CREATE PROCEDURE SP_FILTRA_CONSULTAS_TODOS(P_TABELA_MENSAGEM VARCHAR(200), P_TABELA_CONTATO VARCHAR(200), P_DATA_INICIAL DATE, 
										   P_DATA_FINAL DATE, P_HORA_INICIAL TIME, P_HORA_FINAL TIME, 
                                           P_PALAVRA_CHAVE VARCHAR(100), P_FILTRO CHAR)
BEGIN
	
	-- consulta por palavra chave
	IF (P_PALAVRA_CHAVE IS NOT NULL && P_DATA_INICIAL IS NULL ) THEN 
		CALL SP_PESQUISAMENSAGENS_PALAVRACHAVE(P_TABELA_MENSAGEM, P_TABELA_CONTATO, P_FILTRO, P_PALAVRA_CHAVE);
        
    ELSE 
		-- consulta por data
		IF(P_PALAVRA_CHAVE IS NULL && P_DATA_INICIAL IS NOT NULL && P_DATA_FINAL IS NOT NULL && P_HORA_INICIAL IS NULL) THEN  
			CALL SP_PESQUISAMENSAGENS_DATA(P_TABELA_MENSAGEM, P_TABELA_CONTATO, P_FILTRO, P_DATA_INICIAL, P_DATA_FINAL);
		
        ELSE
			-- consulta por palavra chave e data
			IF (P_PALAVRA_CHAVE IS NOT NULL && P_DATA_INICIAL IS NOT NULL && P_DATA_FINAL IS NOT NULL && P_HORA_INICIAL IS NULL) THEN 
				CALL SP_PESQUISAMENSAGENS_PALAVRACHAVE_DATA(P_TABELA_MENSAGEM, P_TABELA_CONTATO, P_FILTRO, P_PALAVRA_CHAVE, P_DATA_INICIAL, P_DATA_FINAL);
			
            ELSE
				-- consulta por data e hora
				IF (P_PALAVRA_CHAVE IS NULL && P_DATA_INICIAL IS NOT NULL && P_HORA_INICIAL IS NOT NULL && P_HORA_FINAL IS NULL) THEN
					CALL SP_PESQUISAMENSAGENS_DATA_HORA(P_TABELA_MENSAGEM, P_TABELA_CONTATO, P_FILTRO, P_DATA_INICIAL, P_HORA_INICIAL, P_HORA_FINAL);
					
                ELSE 
					-- consulta por palavra, data e hora
					IF (P_PALAVRA_CHAVE IS NOT NULL && P_DATA_INICIAL IS NOT NULL && P_HORA_INICIAL IS NOT NULL && P_HORA_FINAL IS NOT NULL) THEN
						CALL SP_PESQUISAMENSAGENS_DATA_HORA_PALAVRACHAVE(P_TABELA_MENSAGEM, P_TABELA_CONTATO, P_FILTRO, P_PALAVRA_CHAVE, P_DATA_INICIAL, P_HORA_INICIAL, P_HORA_FINAL);
					END IF;
                END IF;
            END IF;
        END IF;
	END IF;
                                                                                                                                                   
END$$
DELIMITER ;



-- -----------------------------------------------
-- CALCULA O TOTAL DE MENSAGENS AGRUPANDO POR CONTATOS, TODAS AS MENSAGENS EM UM ANO
DELIMITER $$
CREATE PROCEDURE SP_PESQUISATIMELINE(P_TABELA_MENSAGEM VARCHAR(200), P_TABELA_CONTATO VARCHAR(200),
										 P_DATA_INICIAL DATE, P_DATA_FINAL DATE, P_FILTRO CHAR)
BEGIN
	
    DECLARE V_INSTRUCAO TEXT;
    
    -- pesquisa só mensagens de contatos por DATA   
	IF(P_FILTRO = 'C') THEN
		SET V_INSTRUCAO = CONCAT(' LEFT JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								 ' WHERE men_date BETWEEN "',P_DATA_INICIAL,'" AND "',P_DATA_FINAL,'" AND ctt_jid LIKE "%@s.whatsapp.net" ');
	ELSE 
		-- pesquisa só mensagens de grupos por DATA   
		IF(P_FILTRO = 'G') THEN
			SET V_INSTRUCAO = CONCAT(' LEFT JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_date BETWEEN "',P_DATA_INICIAL,'" AND "',P_DATA_FINAL,'" AND ctt_jid LIKE "%@g.us" ');
 
		ELSE 
			-- pesquisa todas as mensagens por DATA    
			SET V_INSTRUCAO = CONCAT(' LEFT JOIN ', P_TABELA_CONTATO, ' ON ctt_id = men_key_remote_jid ',
								     ' WHERE men_date BETWEEN "',P_DATA_INICIAL,'" AND "',P_DATA_FINAL,'"' );
        END IF;
    END IF;

    
    SET @s = CONCAT(' SELECT count(*), ctt_id, ctt_jid, ctt_display_name, ctt_wa_name ',
					' FROM ', P_TABELA_MENSAGEM,
					' ', V_INSTRUCAO,
                    ' GROUP BY men_key_remote_jid ',
					' ORDER BY COUNT(*) DESC'); 

	PREPARE stmt FROM @s;
	EXECUTE stmt;
END$$
DELIMITER ;
