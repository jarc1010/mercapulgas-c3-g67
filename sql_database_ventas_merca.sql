CREATE SCHEMA IF NOT EXISTS `ventas_merca` DEFAULT CHARACTER SET utf8 ;
USE `ventas_merca` ;
-- -----------------------------------------------------
-- Table `ventas_merca`.`Producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ventas_merca`.`Producto` (
  `idProducto` INT NOT NULL AUTO_INCREMENT,
  `Nombre del producto` VARCHAR(45) NULL,
  `Descripcion del producto` VARCHAR(45) NULL,
  `Costo unitario` DOUBLE NULL,
  `Imagen` LONGTEXT NULL,
  `Productos disponibles` INT NULL,
  PRIMARY KEY (`idProducto`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ventas_merca`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ventas_merca`.`Usuario` (
  `idusuario` INT NOT NULL,
  `Nombre` VARCHAR(45) NULL,
  `Apellido` VARCHAR(45) NULL,
  `Password` VARCHAR(45) NULL,
  PRIMARY KEY (`idusuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ventas_merca`.`Compras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ventas_merca`.`Compras` (
  `idCompras` INT NOT NULL,
  `Nombre Cliente` VARCHAR(45) NULL,
  `Nit` INT NULL,
  `Fecha de ingreso` DATE NULL,
  `Total` DOUBLE NULL,
  `Estado` CHAR(1) NULL,
  `Usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`idCompras`),
  INDEX `fk_Compras_Usuario1_idx` (`Usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_Compras_Usuario1`
    FOREIGN KEY (`Usuario_idusuario`)
    REFERENCES `ventas_merca`.`Usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ventas_merca`.`Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ventas_merca`.`Clientes` (
  `idClientes` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Apellido` VARCHAR(45) NOT NULL,
  `Fecha_Nacimiento` VARCHAR(45) NOT NULL,
  `Celular` VARCHAR(45) NOT NULL,
  `Direccion` VARCHAR(45) NOT NULL,
  `Usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`idClientes`),
  INDEX `fk_Clientes_Usuario1_idx` (`Usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `fk_Clientes_Usuario1`
    FOREIGN KEY (`Usuario_idusuario`)
    REFERENCES `ventas_merca`.`Usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ventas_merca`.`Ventas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ventas_merca`.`Ventas` (
  `idVentas` INT NOT NULL AUTO_INCREMENT,
  `Fecha` DATE NOT NULL,
  `idclientes` INT NOT NULL,
  `Usuario_idusuario` INT NOT NULL,
  PRIMARY KEY (`idVentas`),
  INDEX `idclientes_idx` (`idclientes` ASC) VISIBLE,
  INDEX `fk_Ventas_Usuario1_idx` (`Usuario_idusuario` ASC) VISIBLE,
  CONSTRAINT `idclientes`
    FOREIGN KEY (`idclientes`)
    REFERENCES `ventas_merca`.`Clientes` (`idClientes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ventas_Usuario1`
    FOREIGN KEY (`Usuario_idusuario`)
    REFERENCES `ventas_merca`.`Usuario` (`idusuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ventas_merca`.`Detallecompra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ventas_merca`.`Detallecompra` (
  `idDetallecompra` BIGINT NOT NULL,
  `compra_idcompra` INT NULL,
  `producto_idproducto` INT NULL,
  `Unidades` DOUBLE NULL,
  `costo_unidades` DOUBLE NULL,
  `total` DOUBLE NULL,
  `Compras_idCompras` INT NOT NULL,
  PRIMARY KEY (`idDetallecompra`),
  INDEX `fk_Detallecompra_Compras1_idx` (`Compras_idCompras` ASC) VISIBLE,
  INDEX `fk_Detallecompra_Producto1_idx` (`Producto_idProducto` ASC) VISIBLE,
  CONSTRAINT `fk_Detallecompra_Compras1`
    FOREIGN KEY (`Compras_idCompras`)
    REFERENCES `ventas_merca`.`Compras` (`idCompras`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Detallecompra_Producto1`
    FOREIGN KEY (`Producto_idProducto`)
    REFERENCES `ventas_merca`.`Producto` (`idProducto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ventas_merca`.`detalleventa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ventas_merca`.`detalleventa` (
  `iddetalleventa` BIGINT NOT NULL,
  `venta_idventa` INT NOT NULL,
  `producto_idproducto` INT NOT NULL,
  `unidades` DOUBLE NULL,
  `costounitario` DOUBLE NULL,
  `total` DOUBLE NULL,
  `Ventas_idVentas` INT NOT NULL,
  PRIMARY KEY (`iddetalleventa`),
  INDEX `fk_detalleventa_Producto1_idx` (`Producto_idProducto` ASC) VISIBLE,
  INDEX `fk_detalleventa_Ventas1_idx` (`Ventas_idVentas` ASC) VISIBLE,
  CONSTRAINT `fk_detalleventa_Producto1`
    FOREIGN KEY (`Producto_idProducto`)
    REFERENCES `ventas_merca`.`Producto` (`idProducto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalleventa_Ventas1`
    FOREIGN KEY (`Ventas_idVentas`)
    REFERENCES `ventas_merca`.`Ventas` (`idVentas`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
