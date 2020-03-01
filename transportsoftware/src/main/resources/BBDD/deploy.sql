--------------
-- TB_pedidos
--------------
CREATE TABLE `DES_TS`.`TB_orders` (
  `order_request` INT NOT NULL AUTO_INCREMENT,
  `id` INT NOT NULL,
  PRIMARY KEY (`order_request`, `id`),
  UNIQUE INDEX `pedido_UNIQUE` (`order_request` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) );
  
--------------
-- TB_truckType
--------------
CREATE TABLE `DES_TS`.`TB_truckType` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `truckType` VARCHAR(12) NOT NULL,
  `load` INT NOT NULL,
  `download` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idType_UNIQUE` (`id` ASC),
  UNIQUE INDEX `type_UNIQUE` (`truckType` ASC));
  
--------------
-- TB_dockState
--------------
CREATE TABLE `DES_TS`.`TB_dockState` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `state_UNIQUE` (`state` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
--------------
-- TB_docks
--------------  
CREATE TABLE `DES_TS`.`TB_docks` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `truckType` INT NOT NULL,
  `range_6` INT NOT NULL,
  `range_7` INT NOT NULL,
  `range_8` INT NOT NULL,
  `range_9` INT NOT NULL,
  `range_10` INT NOT NULL,
  `range_11` INT NOT NULL,
  `range_12` INT NOT NULL,
  `range_13` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_truckType_idx` (`truckType` ASC) VISIBLE,
  INDEX `fk_range_6_idx` (`range_6` ASC) VISIBLE,
  INDEX `fk_range_7_idx` (`range_7` ASC) VISIBLE,
  INDEX `fk_range_8_idx` (`range_8` ASC) VISIBLE,
  INDEX `fk_range_9_idx` (`range_9` ASC) VISIBLE,
  INDEX `fk_range_10_idx` (`range_10` ASC) VISIBLE,
  INDEX `fk_range_11_idx` (`range_11` ASC) VISIBLE,
  INDEX `fk_franja_12_idx` (`range_12` ASC) VISIBLE,
  INDEX `fk_franja_13_idx` (`range_13` ASC) VISIBLE,
  CONSTRAINT `fk_truckType`
    FOREIGN KEY (`truckType`)
    REFERENCES `DES_TS`.`TB_truckType` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_range_6`
    FOREIGN KEY (`range_6`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_range_7`
    FOREIGN KEY (`range_7`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_range_8`
    FOREIGN KEY (`range_8`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_range_9`
    FOREIGN KEY (`range_9`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_range_10`
    FOREIGN KEY (`range_10`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_range_11`
    FOREIGN KEY (`range_11`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_range_12`
    FOREIGN KEY (`range_12`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_range_13`
    FOREIGN KEY (`range_13`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

--------------
-- TB_reservas
--------------  
CREATE TABLE `DES_TS`.`TB_bookings` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `truckPlate` VARCHAR(12) NOT NULL,
  `order_request` INT NOT NULL,
  `loadDownload` INT NOT NULL,
  `bookingDate` DATETIME NOT NULL,
  `arrivalDate` DATETIME NULL,
  `departureDate` DATETIME NULL,
  `state` INT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `order_request_UNIQUE` (`order_request` ASC) ,
  INDEX `fk_loadDownload_idx` (`loadDownload` ASC) ,
  CONSTRAINT `fk_order_request`
    FOREIGN KEY (`order_request`)
    REFERENCES `DES_TS`.`TB_orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_loadDownload`
    FOREIGN KEY (`loadDownload`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

--------------
-- TB_timeOut
--------------  
CREATE TABLE `DES_TS`.`TB_timeOut` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `truckPlate` VARCHAR(12) NOT NULL,
  `order_request` INT NOT NULL,
  `loadDownload` INT NOT NULL,
  `bookingDate` DATETIME NOT NULL,
  `arrivalDate` DATETIME NOT NULL,
  `departureDate` DATETIME NULL,
  `state` INT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `pedido_UNIQUE` (`order_request` ASC) ,
  INDEX `fk_loadDownload_idx` (`loadDownload` ASC) ,
  CONSTRAINT `fk_order_request_timeOut`
    FOREIGN KEY (`order_request`)
    REFERENCES `DES_TS`.`TB_orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_loadDownload_timeOut`
    FOREIGN KEY (`loadDownload`)
    REFERENCES `DES_TS`.`TB_dockState` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
  
--------------
-- TB_roles
--------------  
CREATE TABLE `DES_TS`.`TB_roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `rol_UNIQUE` (`rol` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
--------------
-- TB_holidays
-------------- 
CREATE TABLE `DES_TS`.`TB_holidays` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(12) NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));
  
  
  
  
  