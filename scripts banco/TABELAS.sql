-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Table `USUARIO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `USUARIO` (
  `usu_id` INT NOT NULL AUTO_INCREMENT,
  `usu_login` VARCHAR(45) NOT NULL,
  `usu_senha` VARCHAR(45) NOT NULL,
  `usu_nome` VARCHAR(100) NOT NULL,
  `usu_diretorio_casos` TEXT NULL,
  PRIMARY KEY (`usu_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CASO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CASO` (
  `cas_id` INT NOT NULL AUTO_INCREMENT,
  `cas_descricao` VARCHAR(50) NOT NULL,
  `cas_nome_investigado` VARCHAR(200) NOT NULL,
  `cas_data_criacao` DATETIME NOT NULL,
  `cas_diretorio` TEXT NOT NULL,
  PRIMARY KEY (`cas_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DISPOSITIVO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DISPOSITIVO` (
  `dps_id` INT NOT NULL AUTO_INCREMENT,
  `dps_cas_id` INT NOT NULL,
  `dps_nome` VARCHAR(100) NOT NULL,
  `dps_fabricante` VARCHAR(50) NOT NULL,
  `dps_idioma` VARCHAR(10) NOT NULL,
  `dps_modelo` VARCHAR(45) NOT NULL,
  `dps_numero_versao` VARCHAR(45) NOT NULL,
  `dps_numero_modelo` VARCHAR(45) NULL,
  `dps_versao_android` VARCHAR(5) NOT NULL,
  `dps_versao_sistema` VARCHAR(200) NOT NULL,
  `dps_diretorio` TEXT NOT NULL,
  `dps_tabela_contato` VARCHAR(200) NOT NULL,
  `dps_tabela_mensagem` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`dps_id`),
  INDEX `fk_dispositivo_caso1_idx` (`dps_cas_id` ASC),
  CONSTRAINT `fk_dispositivo_caso1`
    FOREIGN KEY (`dps_cas_id`)
    REFERENCES `CASO` (`cas_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `USUARIO_CASO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `USUARIO_CASO` (
  `usc_usu_id` INT NOT NULL,
  `usc_cas_id` INT NOT NULL,
  `usc_acesso` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`usc_usu_id`, `usc_cas_id`),
  INDEX `fk_usuario_has_caso_caso1_idx` (`usc_cas_id` ASC),
  INDEX `fk_usuario_has_caso_usuario1_idx` (`usc_usu_id` ASC),
  CONSTRAINT `fk_usuario_has_caso_usuario1`
    FOREIGN KEY (`usc_usu_id`)
    REFERENCES `USUARIO` (`usu_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_has_caso_caso1`
    FOREIGN KEY (`usc_cas_id`)
    REFERENCES `CASO` (`cas_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

/*
-- -----------------------------------------------------
-- Table `CONTATO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CONTATO` (
  `ctt_id` INT NOT NULL,
  `ctt_dps_id` INT NOT NULL,
  `ctt_jid` VARCHAR(45) NOT NULL,
  `ctt_status` VARCHAR(200) NULL,
  `ctt_status_date` DATE NOT NULL,
  `ctt_status_time` TIME(6) NOT NULL,
  `ctt_number` VARCHAR(45) NULL,
  `ctt_display_name` VARCHAR(100) NULL,
  `ctt_wa_name` VARCHAR(100) NULL,
  PRIMARY KEY (`ctt_id`),
  INDEX `fk_CONTATO_DISPOSITIVO1_idx` (`ctt_dps_id` ASC),
  CONSTRAINT `fk_CONTATO_DISPOSITIVO1`
    FOREIGN KEY (`ctt_dps_id`)
    REFERENCES `DISPOSITIVO` (`dps_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MENSAGEM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MENSAGEM` (
  `men_id` INT NOT NULL,
  `men_dps_id` INT NOT NULL,
  `men_key_remote_jid` INT NOT NULL,
  `men_remote_resource` VARCHAR(50) NULL,
  `men_key_from_me` TINYINT(1) NOT NULL,
  `men_data` LONGTEXT NULL,
  `men_date` DATE NULL,
  `men_time` TIME(6) NULL,
  `men_media_url` VARCHAR(200) NULL,
  `men_media_mime_type` VARCHAR(40) NULL,
  `men_media_size` INT NULL,
  `men_media_name` VARCHAR(300) NULL,
  `men_media_caption` VARCHAR(200) NULL,
  PRIMARY KEY (`men_id`),
  INDEX `fk_MENSAGEM_DISPOSITIVO_idx` (`men_dps_id` ASC),
  INDEX `fk_MENSAGEM_CONTATO1_idx` (`men_key_remote_jid` ASC),
  CONSTRAINT `fk_MENSAGEM_DISPOSITIVO`
    FOREIGN KEY (`men_dps_id`)
    REFERENCES `DISPOSITIVO` (`dps_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MENSAGEM_CONTATO1`
    FOREIGN KEY (`men_key_remote_jid`)
    REFERENCES `CONTATO` (`ctt_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;*/


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
