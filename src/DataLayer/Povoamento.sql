-- Criar utilizador 'oficina' se não existir
CREATE USER IF NOT EXISTS 'oficina'@'localhost' IDENTIFIED BY 'oficina';

-- Criar banco de dados 'oficina' se não existir
CREATE DATABASE IF NOT EXISTS oficina;

-- Conceder permissões ao utilizador 'oficina' para acessar o banco de dados 'oficina'
GRANT ALL PRIVILEGES ON oficina.* TO 'oficina'@'localhost';

-- Atualizar as permissões
FLUSH PRIVILEGES;

USE oficina;

-- Table `Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cliente` (
  `NIF` INT UNSIGNED NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `morada` VARCHAR(100) NOT NULL,
  `telefone` INT NOT NULL,
  `email` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`NIF`),
  UNIQUE INDEX `NIF_UNIQUE` (`NIF` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Motor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Motor` (
  `idMotor` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(45) NOT NULL,
  `tipoMotor` INT NOT NULL,
  PRIMARY KEY (`idMotor`),
  UNIQUE INDEX `codMotor_UNIQUE` (`idMotor` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FichaVeiculo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FichaVeiculo` (
  `matricula` VARCHAR(15) NOT NULL,
  `clienteNIF` INT UNSIGNED NOT NULL,
  `Motor_idMotor` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`matricula`),
  UNIQUE INDEX `matricula_UNIQUE` (`matricula` ASC) VISIBLE,
  INDEX `fk_FichaVeiculo_Cliente_idx` (`clienteNIF` ASC) VISIBLE,
  INDEX `fk_FichaVeiculo_Motor1_idx` (`Motor_idMotor` ASC) VISIBLE,
  CONSTRAINT `fk_FichaVeiculo_Cliente`
    FOREIGN KEY (`clienteNIF`)
    REFERENCES `Cliente` (`NIF`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FichaVeiculo_Motor1`
    FOREIGN KEY (`Motor_idMotor`)
    REFERENCES `Motor` (`idMotor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Servico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Servico` (
  `codServico` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `universal` BIT NOT NULL,
  `duracaoMedia` INT UNSIGNED NOT NULL,
  `nome` VARCHAR(75) NOT NULL,
  `preco` FLOAT UNSIGNED NOT NULL,
  `tipoServico` TINYINT NOT NULL,
  `codMotor` INT NOT NULL,
  PRIMARY KEY (`codServico`),
  UNIQUE INDEX `codServico_UNIQUE` (`codServico` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PostoTrabalho`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PostoTrabalho` (
    `codPostoTrabalho` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(45) NOT NULL,
    `disponibilidade` TIME NULL,
    `codServicoFK` INT UNSIGNED NULL,
    `ativo` BOOLEAN  NOT NULL,
    PRIMARY KEY (`codPostoTrabalho`),
    UNIQUE INDEX `nome_UNIQUE` (`nome` ASC),
    INDEX `fk_PostoTrabalho_Servico1_idx` (`codServicoFK` ASC),
    CONSTRAINT `fk_PostoTrabalho_Servico1`
    FOREIGN KEY (`codServicoFK`)
    REFERENCES `Servico` (`codServico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    ) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Turno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Turno` (
  `codTurno` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `horaInicio` TIME NOT NULL,
  `horaFim` TIME NOT NULL,
  PRIMARY KEY (`codTurno`),
  UNIQUE INDEX `codTurno_UNIQUE` (`codTurno` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Funcionario` (
  `codFuncionario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `codPostoTrabalhoFK` INT UNSIGNED NOT NULL,
  `codTurnoFK` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`codFuncionario`),
  UNIQUE INDEX `codFuncionario_UNIQUE` (`codFuncionario` ASC) VISIBLE,
  INDEX `fk_Funcionario_PostoTrabalho1_idx` (`codPostoTrabalhoFK` ASC) VISIBLE, -- Correção aqui
  INDEX `fk_Funcionario_Turno1_idx` (`codTurnoFK` ASC) VISIBLE,
  CONSTRAINT `fk_Funcionario_PostoTrabalho1`
    FOREIGN KEY (`codPostoTrabalhoFK`)
    REFERENCES `PostoTrabalho` (`codPostoTrabalho`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Funcionario_Turno1`
    FOREIGN KEY (`codTurnoFK`)
    REFERENCES `Turno` (`codTurno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Mecanico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Mecanico` (
  `codFuncionarioFK` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`codFuncionarioFK`),
  UNIQUE INDEX `codFuncionarioFK_UNIQUE` (`codFuncionarioFK` ASC) VISIBLE,
  CONSTRAINT `fk_Mecanico_Funcionario1`
    FOREIGN KEY (`codFuncionarioFK`)
    REFERENCES `Funcionario` (`codFuncionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Estado` (
  `codEstado` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codEstado`),
  UNIQUE INDEX `codEstado_UNIQUE` (`codEstado` ASC) VISIBLE,
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) VISIBLE)
  
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Pedidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Pedidos` (
  `codPedidos` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `mensagem` BIT NOT NULL,
  `motivo` VARCHAR(200) NULL,
  `atribuido` BIT NOT NULL,
  `codServicoFK` INT UNSIGNED NULL,
  `matriculaFK` VARCHAR(15)  NULL,
  `codPostoTrabalhoFK` INT UNSIGNED NULL,
  `mecanicoCodFuncionarioFK` INT UNSIGNED NULL,
  `codEstadoFK` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`codPedidos`),
  INDEX `fk_Pedidos_Servico1_idx` (`codServicoFK` ASC) VISIBLE,
  INDEX `fk_Pedidos_FichaVeiculo1_idx` (`matriculaFK` ASC) VISIBLE,
  INDEX `fk_Pedidos_oTrabalho1_idx` (`codPostoTrabalhoFK` ASC) VISIBLE,
  INDEX `fk_Pedidos_Mecanico1_idx` (`mecanicoCodFuncionarioFK` ASC) VISIBLE,
  INDEX `fk_Pedidos_Estado1_idx` (`codEstadoFK` ASC) VISIBLE,
  CONSTRAINT `fk_Pedidos_Servico1`
    FOREIGN KEY (`codServicoFK`)
    REFERENCES `Servico` (`codServico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pedidos_FichaVeiculo1`
    FOREIGN KEY (`matriculaFK`)
    REFERENCES `FichaVeiculo` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pedidos_PostoTrabalho1`
    FOREIGN KEY (`codPostoTrabalhoFK`)
    REFERENCES `PostoTrabalho` (`codPostoTrabalho`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pedidos_Mecanico1`
    FOREIGN KEY (`mecanicoCodFuncionarioFK`)
    REFERENCES `Mecanico` (`codFuncionarioFK`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pedidos_Estado1`
    FOREIGN KEY (`codEstadoFK`)
    REFERENCES `Estado` (`codEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Pack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Pack` (
  `codServicoPackFK` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`codServicoPackFK`),
  INDEX `fk_Pack_Servico1_idx` (`codServicoPackFK` ASC) VISIBLE,
  UNIQUE INDEX `Servico_codServico_UNIQUE` (`codServicoPackFK` ASC) VISIBLE,
  CONSTRAINT `fk_Pack_Servico1`
    FOREIGN KEY (`codServicoPackFK`)
    REFERENCES `Servico` (`codServico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PackServico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PackServico` (
  `codServicoFK` INT UNSIGNED NOT NULL,
  `codServicoPackFK` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`codServicoFK`, `codServicoPackFK`),
  INDEX `fk_PackServico_Pack1_idx` (`codServicoPackFK` ASC) VISIBLE,
  CONSTRAINT `fk_PackServico_Servico1`
    FOREIGN KEY (`codServicoFK`)
    REFERENCES `Servico` (`codServico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PackServico_Pack1`
    FOREIGN KEY (`codServicoPackFK`)
    REFERENCES `Pack` (`codServicoPackFK`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Rececionista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Rececionista` (
  `codFuncionarioFK` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`codFuncionarioFK`),
  UNIQUE INDEX `codFuncionario_UNIQUE` (`codFuncionarioFK` ASC) VISIBLE,
  CONSTRAINT `fk_Rececionista_Funcionario1`
    FOREIGN KEY (`codFuncionarioFK`)
    REFERENCES `Funcionario` (`codFuncionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DiaTrabalho`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DiaTrabalho` (
  `codDiaTrabalho` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `dia` DATE NOT NULL,
  `horaEntrada` TIME NOT NULL,
  `horaSaida` TIME NULL,
  `codFuncionarioFK` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`codDiaTrabalho`),
  UNIQUE INDEX `codDiaTrabalho_UNIQUE` (`codDiaTrabalho` ASC) VISIBLE,
  INDEX `fk_DiaTrabalho_Funcionario1_idx` (`codFuncionarioFK` ASC) VISIBLE,
  CONSTRAINT `fk_DiaTrabalho_Funcionario1`
    FOREIGN KEY (`codFuncionarioFK`)
    REFERENCES `Funcionario` (`codFuncionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `DiaTrabalho`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DiaTrabalho` (
                                             `codDiaTrabalho` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                             `dia` DATE NOT NULL,
                                             `horaEntrada` TIME NOT NULL,
                                             `horaSaida` TIME NULL,
                                             `codFuncionarioFK` INT UNSIGNED NOT NULL,
                                             PRIMARY KEY (`codDiaTrabalho`),
    UNIQUE INDEX `codDiaTrabalho_UNIQUE` (`codDiaTrabalho` ASC) VISIBLE,
    INDEX `fk_DiaTrabalho_Funcionario1_idx` (`codFuncionarioFK` ASC) VISIBLE,
    CONSTRAINT `fk_DiaTrabalho_Funcionario1`
    FOREIGN KEY (`codFuncionarioFK`)
    REFERENCES `Funcionario` (`codFuncionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MotorCombustao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MotorCombustao` (
  `idMotorFK` INT UNSIGNED NOT NULL,
  `cilindrada` INT UNSIGNED NOT NULL,
  INDEX `fk_MotorCombustao_Motor1_idx` (`idMotorFK` ASC) VISIBLE,
  PRIMARY KEY (`idMotorFK`),
  UNIQUE INDEX `idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE,
  CONSTRAINT `fk_MotorCombustao_Motor1`
    FOREIGN KEY (`idMotorFK`)
    REFERENCES `Motor` (`idMotor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MotorEletrico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MotorEletrico` (
  `idMotorFK` INT UNSIGNED NOT NULL,
  `potenciaEltrica` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idMotorFK`),
  INDEX `fk_MotorEletrico_Motor1_idx` (`idMotorFK` ASC) VISIBLE,
  UNIQUE INDEX `idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE,
  CONSTRAINT `fk_MotorEletrico_Motor1`
    FOREIGN KEY (`idMotorFK`)
    REFERENCES `Motor` (`idMotor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MotorHibrido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MotorHibrido` (
  `idMotorFK` INT UNSIGNED NOT NULL,
  `motorCombustaoFK` INT UNSIGNED NOT NULL,
  `motorEletricoFK` INT UNSIGNED NOT NULL,
  INDEX `fk_MotorHibrido_Motor1_idx` (`idMotorFK` ASC) VISIBLE,
  PRIMARY KEY (`idMotorFK`),
  INDEX `fk_MotorHibrido_MotorCombustao1_idx` (`motorCombustaoFK` ASC) VISIBLE,
  INDEX `fk_MotorHibrido_MotorEletrico1_idx` (`motorEletricoFK` ASC) VISIBLE,
  UNIQUE INDEX `idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE,
  CONSTRAINT `fk_MotorHibrido_Motor1`
    FOREIGN KEY (`idMotorFK`)
    REFERENCES `Motor` (`idMotor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MotorHibrido_MotorCombustao1`
    FOREIGN KEY (`motorCombustaoFK`)
    REFERENCES `MotorCombustao` (`idMotorFK`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MotorHibrido_MotorEletrico1`
    FOREIGN KEY (`motorEletricoFK`)
    REFERENCES `MotorEletrico` (`idMotorFK`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MotorGasolina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MotorGasolina` (
  `idMotorFK` INT UNSIGNED NOT NULL,
  INDEX `fk_MotorGasolina_MotorCombustao1_idx` (`idMotorFK` ASC) VISIBLE,
  PRIMARY KEY (`idMotorFK`),
  UNIQUE INDEX `idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE,
  CONSTRAINT `fk_MotorGasolina_MotorCombustao1`
    FOREIGN KEY (`idMotorFK`)
    REFERENCES `MotorCombustao` (`idMotorFK`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MotorGasoleo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MotorGasoleo` (
  `idMotorFK` INT UNSIGNED NOT NULL,
  INDEX `fk_MotorGasoleo_MotorCombustao1_idx` (`idMotorFK` ASC) VISIBLE,
  PRIMARY KEY (`idMotorFK`),
  UNIQUE INDEX `MotorCombustao_idMotorFK_UNIQUE` (`idMotorFK` ASC) VISIBLE,
  CONSTRAINT `fk_MotorGasoleo_MotorCombustao1`
    FOREIGN KEY (`idMotorFK`)
    REFERENCES `MotorCombustao` (`idMotorFK`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FichaVeiculo_Servico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FichaVeiculo_Servico` (
  `FichaVeiculo_matricula` VARCHAR(15) NOT NULL,
  `Servico_codServico` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`FichaVeiculo_matricula`, `Servico_codServico`),
  INDEX `fk_FichaVeiculo_has_Servico_Servico1_idx` (`Servico_codServico` ASC) VISIBLE,
  INDEX `fk_FichaVeiculo_has_Servico_FichaVeiculo1_idx` (`FichaVeiculo_matricula` ASC) VISIBLE,
  CONSTRAINT `fk_FichaVeiculo_has_Servico_FichaVeiculo1`
    FOREIGN KEY (`FichaVeiculo_matricula`)
    REFERENCES `FichaVeiculo` (`matricula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FichaVeiculo_has_Servico_Servico1`
    FOREIGN KEY (`Servico_codServico`)
    REFERENCES `Servico` (`codServico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- Povoamento (ignore serve para passar à frente caso já exista essa entrada na tabela)--
INSERT IGNORE INTO Cliente (NIF, nome, morada, telefone, email) VALUES
(123456789, 'João Silva', 'Rua A, nº 123', 987654321, 'joao.silva@email.com'),
(234567890, 'Maria Santos', 'Avenida B, nº 456', 654321987, 'maria.santos@email.com'),
(345678901, 'Carlos Oliveira', 'Travessa C, nº 789', 123456789, 'carlos.oliveira@email.com'),
(456789012, 'Ana Pereira', 'Rua D, nº 1011', 987123654, 'ana.pereira@email.com'),
(567890123, 'Paulo Rodrigues', 'Avenida E, nº 1213', 321654987, 'paulo.rodrigues@email.com'),
(678901234, 'Catarina Costa', 'Travessa F, nº 1415', 456789012, 'catarina.costa@email.com'),
(789012345, 'Miguel Santos', 'Rua G, nº 1617', 789012345, 'miguel.santos@email.com'),
(890123456, 'Sofia Fernandes', 'Avenida H, nº 1819', 987012345, 'sofia.fernandes@email.com'),
(901234567, 'Ricardo Lima', 'Travessa I, nº 2021', 123987456, 'ricardo.lima@email.com'),
(123012345, 'Isabel Pereira', 'Rua J, nº 2223', 456789012, 'isabel.pereira@email.com');

INSERT IGNORE INTO Motor (idMotor,marca, tipoMotor) VALUES
(1,'BMW', 4),
(2,'RedBull', 4),
(3,'Audi', 5),
(4,'MotorD', 5),
(5,'MotorJ', 3);

INSERT IGNORE INTO FichaVeiculo (matricula, clienteNIF, Motor_idMotor) VALUES
('ABC123', 123456789, 1),
('XYZ789', 234567890, 2),
('DEF456', 345678901, 3),
('GHI789', 456789012, 4),
('JKL012', 567890123, 5),
('MNO345', 678901234, 1),
('PQR678', 789012345, 2),
('STU901', 890123456, 3),
('VWX234', 901234567, 4),
('YZA567', 123012345, 5);


INSERT IGNORE INTO Servico (codServico, universal, duracaoMedia, nome, preco, codMotor) VALUES
(1,1, 120, 'Troca de Óleo', 50.0, 1),
(2,1, 90, 'Alinhamento de Rodas', 80.0, 1),
(3,1, 180, 'Revisão Geral', 150.0, 1),
(4,1, 60, 'Troca de Pastilhas de Freio', 120.0, 1),
(5,1, 240, 'Check-up Completo', 100.0, 1),
(6,1, 45, 'Balanceamento de Rodas', 60.0, 1),
(7,0, 210, 'Pack', 130, 2),
(8,0, 75, 'Troca de Filtro de Ar do Motor', 30.0, 2),
(9,1, 150, 'Diagnóstico Eletrônico', 80.0, 1),
(10,0, 120, 'Troca de Bateria', 100.0, 3);

INSERT IGNORE INTO PostoTrabalho (codPostoTrabalho, nome, disponibilidade, codServicoFK, ativo) VALUES
(1, 'Posto1', '08:00:00', 1, 0),
(2, 'Posto2', '08:00:00', 2, 0),
(3, 'Posto3', '08:00:00', 3, 0),
(4, 'Posto4', '08:00:00', 4, 0),
(5, 'Posto5', '08:00:00', 5, 0),
(6, 'Posto6', '08:00:00', 6, 0),
(7, 'Posto7', '08:00:00', 7, 0),
(8, 'Posto8','08:00:00', 8, 0),
(9, 'Posto9', '08:00:00', 9, 0),
(10, 'Posto10', '08:00:00', 10, 0);


INSERT IGNORE INTO Turno (codTurno, horaInicio, horaFim) VALUES
(1,'08:00:00', '12:00:00'),
(2,'12:00:00', '20:00:00'),
(3,'10:00:00', '14:00:00'),
(4,'11:00:00', '15:00:00'),
(5,'12:00:00', '16:00:00'),
(6,'13:00:00', '17:00:00'),
(7,'14:00:00', '18:00:00'),
(8,'15:00:00', '19:00:00'),
(9,'16:00:00', '20:00:00'),
(10,'17:00:00', '21:00:00');

INSERT IGNORE INTO Funcionario (codFuncionario, nome, codPostoTrabalhoFK, codTurnoFK) VALUES
(1,'Jose', 1, 1),
(2,'Maria', 2, 2),
(3,'Leonor', 3, 1),
(4,'Joao', 4, 1),
(5,'Manel', 5, 2),
(6,'Orlando', 6, 1),
(7,'Rui', 7, 2),
(8,'Joana', 8, 1),
(9,'Marco', 9, 2),
(10,'Paula', 10, 1);

INSERT IGNORE INTO Mecanico (codFuncionarioFK) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9);


INSERT IGNORE INTO Estado (codEstado, nome) VALUES
(1,'sucesso'),
(2,'insucesso'),
(3,'pendente');

INSERT IGNORE INTO Pedidos (codPedidos, mensagem, motivo, atribuido, codServicoFK, matriculaFK, codPostoTrabalhoFK, mecanicoCodFuncionarioFK, codEstadoFK) VALUES
(1,0, null, 0, 1, 'XYZ789', 1, 1, 3),
(2,0, null, 0, 2, 'XYZ789', 2, 2, 3),
(3,1, 'Motivo3', 0, 3, 'DEF456', 3, 3, 2),
(4,1, 'Motivo4', 0, 4, 'GHI789', 4, 4, 2),
(5,0, null, 0, 5, 'JKL012', 5, 5, 1),
(6,1, 'Motivo6', 0, 6, 'MNO345', 6, 6, 2),
(7,0, null, 0, 7, 'PQR678', 7, 7, 1),
(8,0, null, 0, 8, 'XYZ789', 8, 8, 3),
(9,0, null, 0, 9, 'VWX234', 9, 9, 1);

INSERT IGNORE INTO Pack (codServicoPackFK) VALUES
(7);

INSERT IGNORE INTO PackServico (codServicoFK, codServicoPackFK) VALUES
(1,7),
(2,7);


INSERT IGNORE INTO Rececionista (codFuncionarioFK) VALUES
(10);

INSERT IGNORE INTO DiaTrabalho (codDiaTrabalho, dia, horaEntrada, horaSaida, codFuncionarioFK) VALUES
(1,'2024-01-06', '08:00:00', '17:00:00', 1),
(2,'2024-01-07', '09:00:00', '18:00:00', 2),
(3,'2024-01-08', '10:00:00', '19:00:00', 3),
(4,'2024-01-09', '11:00:00', '20:00:00', 4),
(5,'2024-01-10', '12:00:00', '21:00:00', 5),
(6,'2024-01-11', '13:00:00', '22:00:00', 6),
(7,'2024-01-12', '14:00:00', '23:00:00', 7),
(8,'2024-01-13', '15:00:00', '00:00:00', 8),
(9,'2024-01-14', '16:00:00', '01:00:00', 9),
(10,'2024-01-15', '17:00:00', '02:00:00', 10);


INSERT IGNORE INTO MotorCombustao (idMotorFK, cilindrada) VALUES
(1,2000),
(2,1200),
(3,2000),
(4,1200);

INSERT IGNORE INTO MotorGasolina (idMotorFK) VALUES
(1),
(2);

INSERT IGNORE INTO MotorGasoleo (idMotorFK) VALUES
(3),
(4);

INSERT IGNORE INTO MotorEletrico(idMotorFK) VALUES
(5);


INSERT IGNORE INTO MotorEletrico (idMotorFK, potenciaEltrica) VALUES
(2,44),
(4,33),
(5,20);

INSERT IGNORE INTO FichaVeiculo_Servico (FichaVeiculo_matricula, Servico_codServico) VALUES
('ABC123', 1),
('XYZ789', 2),
('DEF456', 3),
('GHI789', 4),
('MNO345', 5),
('PQR678', 6),
('STU901', 7),
('VWX234', 8),
('YZA567', 9),
('JKL012', 10);