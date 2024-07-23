/*
SQLyog Ultimate v13.1.1 (32 bit)
MySQL - 8.0.19 : Database - superestrella
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`superestrella` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `superestrella`;

/*Table structure for table `categorias` */

DROP TABLE IF EXISTS `categorias`;

CREATE TABLE `categorias` (
  `codigocategoria` int NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`codigocategoria`),
  UNIQUE KEY `descripcion_UNIQUE` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `categorias` */

/*Table structure for table `ciudades` */

DROP TABLE IF EXISTS `ciudades`;

CREATE TABLE `ciudades` (
  `codigociudad` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `codigodepartamento` int NOT NULL,
  PRIMARY KEY (`codigociudad`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `fk_ciudades_departamentos1_idx` (`codigodepartamento`),
  CONSTRAINT `fk_ciudades_departamentos1` FOREIGN KEY (`codigodepartamento`) REFERENCES `departamentos` (`codigodepartamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ciudades` */

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `codigocliente` int NOT NULL,
  `cedula` varchar(45) DEFAULT NULL,
  `ruc` varchar(45) DEFAULT NULL,
  `razonsocial` varchar(100) NOT NULL,
  `fechanacimiento` date DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `celular` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`codigocliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `clientes` */

/*Table structure for table `compras` */

DROP TABLE IF EXISTS `compras`;

CREATE TABLE `compras` (
  `codigotipodocumento` int NOT NULL,
  `codigosucursal` int NOT NULL,
  `puntoexpedicion` int NOT NULL,
  `numerocompra` int NOT NULL,
  `fecha` date NOT NULL,
  `codigocondicion` int NOT NULL,
  `codigoestadocompra` int NOT NULL,
  `codigoproveedor` int NOT NULL,
  `codigousuario` int NOT NULL,
  PRIMARY KEY (`codigotipodocumento`,`puntoexpedicion`,`numerocompra`,`codigosucursal`,`codigoproveedor`),
  KEY `fk_compras_condiciones1_idx` (`codigocondicion`),
  KEY `fk_compras_estadosdocumentos1_idx` (`codigoestadocompra`),
  KEY `fk_compras_proveedores1_idx` (`codigoproveedor`),
  KEY `fk_compras_usuarios1_idx` (`codigousuario`),
  CONSTRAINT `fk_compras_condiciones1` FOREIGN KEY (`codigocondicion`) REFERENCES `condiciones` (`codigocondicion`),
  CONSTRAINT `fk_compras_estadosdocumentos1` FOREIGN KEY (`codigoestadocompra`) REFERENCES `estadosdocumentos` (`codigoestadoventa`),
  CONSTRAINT `fk_compras_proveedores1` FOREIGN KEY (`codigoproveedor`) REFERENCES `proveedores` (`codigoproveedor`),
  CONSTRAINT `fk_compras_tiposdocumentos1` FOREIGN KEY (`codigotipodocumento`) REFERENCES `tiposdocumentos` (`codigotipodocumento`),
  CONSTRAINT `fk_compras_usuarios1` FOREIGN KEY (`codigousuario`) REFERENCES `usuarios` (`codigousuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `compras` */

/*Table structure for table `condiciones` */

DROP TABLE IF EXISTS `condiciones`;

CREATE TABLE `condiciones` (
  `codigocondicion` int NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`codigocondicion`),
  UNIQUE KEY `descripcion_UNIQUE` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `condiciones` */

/*Table structure for table `cuentasapagar` */

DROP TABLE IF EXISTS `cuentasapagar`;

CREATE TABLE `cuentasapagar` (
  `numerocuota` int NOT NULL,
  `codigotipodocumento` int NOT NULL,
  `puntoexpedicion` int NOT NULL,
  `numerocompra` int NOT NULL,
  `codigosucursal` int NOT NULL,
  `codigoproveedor` int NOT NULL,
  `montocuota` decimal(14,2) NOT NULL,
  `montopagado` decimal(14,2) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `vencimiento` date NOT NULL,
  `cancelado` int NOT NULL,
  PRIMARY KEY (`numerocuota`,`codigotipodocumento`,`puntoexpedicion`,`numerocompra`,`codigosucursal`,`codigoproveedor`),
  KEY `fk_cuentasapagar_compras1_idx` (`codigotipodocumento`,`puntoexpedicion`,`numerocompra`,`codigosucursal`,`codigoproveedor`),
  CONSTRAINT `fk_cuentasapagar_compras1` FOREIGN KEY (`codigotipodocumento`, `puntoexpedicion`, `numerocompra`, `codigosucursal`, `codigoproveedor`) REFERENCES `compras` (`codigotipodocumento`, `puntoexpedicion`, `numerocompra`, `codigosucursal`, `codigoproveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cuentasapagar` */

/*Table structure for table `cuotas` */

DROP TABLE IF EXISTS `cuotas`;

CREATE TABLE `cuotas` (
  `codigotipodocumento` int NOT NULL,
  `codigosucursal` int(3) unsigned zerofill NOT NULL,
  `codigopuntoexpedicion` int(3) unsigned zerofill NOT NULL,
  `numeroventa` int(7) unsigned zerofill NOT NULL,
  `numerocuota` int NOT NULL,
  `montocuota` decimal(14,2) NOT NULL,
  `montopagado` decimal(14,2) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `vencimiento` date NOT NULL,
  `cancelado` int NOT NULL,
  PRIMARY KEY (`codigotipodocumento`,`codigosucursal`,`codigopuntoexpedicion`,`numeroventa`,`numerocuota`),
  KEY `fk_cuotas_ventas1_idx` (`codigotipodocumento`,`codigosucursal`,`codigopuntoexpedicion`,`numeroventa`),
  CONSTRAINT `fk_cuotas_ventas1` FOREIGN KEY (`codigotipodocumento`, `codigosucursal`, `codigopuntoexpedicion`, `numeroventa`) REFERENCES `ventas` (`codigotipodocumento`, `codigosucursal`, `codigopuntoexpedicion`, `numeroventa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cuotas` */

/*Table structure for table `departamentos` */

DROP TABLE IF EXISTS `departamentos`;

CREATE TABLE `departamentos` (
  `codigodepartamento` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `codigopais` int NOT NULL,
  PRIMARY KEY (`codigodepartamento`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `fk_departamentos_paises_idx` (`codigopais`),
  CONSTRAINT `fk_departamentos_paises` FOREIGN KEY (`codigopais`) REFERENCES `paises` (`codigopais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `departamentos` */

/*Table structure for table `depositos` */

DROP TABLE IF EXISTS `depositos`;

CREATE TABLE `depositos` (
  `codigodeposito` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `codigosucursal` int(3) unsigned zerofill NOT NULL,
  PRIMARY KEY (`codigodeposito`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `fk_depositos_sucursales1_idx` (`codigosucursal`),
  CONSTRAINT `fk_depositos_sucursales1` FOREIGN KEY (`codigosucursal`) REFERENCES `sucursales` (`codigosucursal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `depositos` */

/*Table structure for table `detallescompras` */

DROP TABLE IF EXISTS `detallescompras`;

CREATE TABLE `detallescompras` (
  `codigointerno` int NOT NULL,
  `codigotipodocumento` int NOT NULL,
  `puntoexpedicion` int NOT NULL,
  `numerocompra` int NOT NULL,
  `codigosucursal` int NOT NULL,
  `codigoproveedor` int NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  `descripcion` varchar(150) NOT NULL,
  `preciocompra` decimal(14,2) NOT NULL,
  `codigotipoimpuesto` int NOT NULL,
  PRIMARY KEY (`codigointerno`,`codigotipodocumento`,`puntoexpedicion`,`numerocompra`,`codigosucursal`,`codigoproveedor`),
  KEY `fk_mercaderias_has_compras_compras1_idx` (`codigotipodocumento`,`puntoexpedicion`,`numerocompra`,`codigosucursal`,`codigoproveedor`),
  KEY `fk_mercaderias_has_compras_mercaderias1_idx` (`codigointerno`),
  CONSTRAINT `fk_mercaderias_has_compras_compras1` FOREIGN KEY (`codigotipodocumento`, `puntoexpedicion`, `numerocompra`, `codigosucursal`, `codigoproveedor`) REFERENCES `compras` (`codigotipodocumento`, `puntoexpedicion`, `numerocompra`, `codigosucursal`, `codigoproveedor`),
  CONSTRAINT `fk_mercaderias_has_compras_mercaderias1` FOREIGN KEY (`codigointerno`) REFERENCES `mercaderias` (`codigointerno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `detallescompras` */

/*Table structure for table `detallesventas` */

DROP TABLE IF EXISTS `detallesventas`;

CREATE TABLE `detallesventas` (
  `codigotipodocumento` int NOT NULL,
  `codigosucursal` int(3) unsigned zerofill NOT NULL,
  `codigopuntoexpedicion` int(3) unsigned zerofill NOT NULL,
  `numeroventa` int(7) unsigned zerofill NOT NULL,
  `codigointerno` int NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  `descripcion` varchar(150) NOT NULL,
  `preciocompra` decimal(14,2) NOT NULL,
  `precioventa` decimal(14,2) NOT NULL,
  `codigotipoimpuesto` int NOT NULL,
  PRIMARY KEY (`codigotipodocumento`,`codigosucursal`,`codigopuntoexpedicion`,`numeroventa`,`codigointerno`),
  KEY `fk_ventas_has_mercaderias_mercaderias1_idx` (`codigointerno`),
  KEY `fk_ventas_has_mercaderias_ventas1_idx` (`codigotipodocumento`,`codigosucursal`,`codigopuntoexpedicion`,`numeroventa`),
  CONSTRAINT `fk_ventas_has_mercaderias_mercaderias1` FOREIGN KEY (`codigointerno`) REFERENCES `mercaderias` (`codigointerno`),
  CONSTRAINT `fk_ventas_has_mercaderias_ventas1` FOREIGN KEY (`codigotipodocumento`, `codigosucursal`, `codigopuntoexpedicion`, `numeroventa`) REFERENCES `ventas` (`codigotipodocumento`, `codigosucursal`, `codigopuntoexpedicion`, `numeroventa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `detallesventas` */

/*Table structure for table `empresas` */

DROP TABLE IF EXISTS `empresas`;

CREATE TABLE `empresas` (
  `codigoempresa` int NOT NULL,
  `ruc` varchar(45) NOT NULL,
  `razonsocial` varchar(45) NOT NULL,
  `logo` varchar(45) DEFAULT NULL,
  `web` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`codigoempresa`),
  UNIQUE KEY `razonsocial_UNIQUE` (`razonsocial`),
  UNIQUE KEY `ruc_UNIQUE` (`ruc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `empresas` */

/*Table structure for table `estadosdocumentos` */

DROP TABLE IF EXISTS `estadosdocumentos`;

CREATE TABLE `estadosdocumentos` (
  `codigoestadoventa` int NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`codigoestadoventa`),
  UNIQUE KEY `descripcion_UNIQUE` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `estadosdocumentos` */

/*Table structure for table `marcas` */

DROP TABLE IF EXISTS `marcas`;

CREATE TABLE `marcas` (
  `codigomarca` int NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`codigomarca`),
  UNIQUE KEY `descripcion_UNIQUE` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `marcas` */

/*Table structure for table `mercaderias` */

DROP TABLE IF EXISTS `mercaderias`;

CREATE TABLE `mercaderias` (
  `codigointerno` int NOT NULL,
  `codigobarras` varchar(45) NOT NULL,
  `descripcion` varchar(150) NOT NULL,
  `codigounidadmedida` int NOT NULL,
  `codigomarca` int NOT NULL,
  `codigocategoria` int NOT NULL,
  `codigotipoimpuesto` int NOT NULL,
  `activo` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`codigointerno`),
  KEY `fk_mercaderias_marcas1_idx` (`codigomarca`),
  KEY `fk_mercaderias_categorias1_idx` (`codigocategoria`),
  KEY `fk_mercaderias_tiposimpuestos1_idx` (`codigotipoimpuesto`),
  KEY `fk_mercaderias_unidadesmedidas1_idx` (`codigounidadmedida`),
  CONSTRAINT `fk_mercaderias_categorias1` FOREIGN KEY (`codigocategoria`) REFERENCES `categorias` (`codigocategoria`),
  CONSTRAINT `fk_mercaderias_marcas1` FOREIGN KEY (`codigomarca`) REFERENCES `marcas` (`codigomarca`),
  CONSTRAINT `fk_mercaderias_tiposimpuestos1` FOREIGN KEY (`codigotipoimpuesto`) REFERENCES `tiposimpuestos` (`codigotipoimpuesto`),
  CONSTRAINT `fk_mercaderias_unidadesmedidas1` FOREIGN KEY (`codigounidadmedida`) REFERENCES `unidadesmedidas` (`codigounidadmedida`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `mercaderias` */

/*Table structure for table `nivelesusuarios` */

DROP TABLE IF EXISTS `nivelesusuarios`;

CREATE TABLE `nivelesusuarios` (
  `codigonivelusuario` int NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`codigonivelusuario`),
  UNIQUE KEY `descripcion_UNIQUE` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `nivelesusuarios` */

/*Table structure for table `paises` */

DROP TABLE IF EXISTS `paises`;

CREATE TABLE `paises` (
  `codigopais` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`codigopais`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `paises` */

/*Table structure for table `proveedores` */

DROP TABLE IF EXISTS `proveedores`;

CREATE TABLE `proveedores` (
  `codigoproveedor` int NOT NULL,
  `cedula` varchar(45) NOT NULL,
  `ruc` varchar(45) DEFAULT NULL,
  `razonsocial` varchar(45) NOT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `celular` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`codigoproveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `proveedores` */

/*Table structure for table `stock` */

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `codigodeposito` int NOT NULL,
  `codigointerno` int NOT NULL,
  `stockactual` decimal(10,2) NOT NULL,
  `stockminimo` decimal(10,2) NOT NULL,
  `stockmaximo` decimal(10,2) NOT NULL,
  `preciocompra` decimal(14,2) NOT NULL,
  `precioventaminorista` decimal(14,2) NOT NULL,
  `precioventamayorista` decimal(14,2) DEFAULT NULL,
  PRIMARY KEY (`codigodeposito`,`codigointerno`),
  KEY `fk_depositos_has_mercaderias_mercaderias1_idx` (`codigointerno`),
  KEY `fk_depositos_has_mercaderias_depositos1_idx` (`codigodeposito`),
  CONSTRAINT `fk_depositos_has_mercaderias_depositos1` FOREIGN KEY (`codigodeposito`) REFERENCES `depositos` (`codigodeposito`),
  CONSTRAINT `fk_depositos_has_mercaderias_mercaderias1` FOREIGN KEY (`codigointerno`) REFERENCES `mercaderias` (`codigointerno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `stock` */

/*Table structure for table `sucursales` */

DROP TABLE IF EXISTS `sucursales`;

CREATE TABLE `sucursales` (
  `codigosucursal` int(3) unsigned zerofill NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `celular` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `codigoempresa` int NOT NULL,
  `codigociudad` int NOT NULL,
  PRIMARY KEY (`codigosucursal`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `fk_sucursales_empresas1_idx` (`codigoempresa`),
  KEY `fk_sucursales_ciudades1_idx` (`codigociudad`),
  CONSTRAINT `fk_sucursales_ciudades1` FOREIGN KEY (`codigociudad`) REFERENCES `ciudades` (`codigociudad`),
  CONSTRAINT `fk_sucursales_empresas1` FOREIGN KEY (`codigoempresa`) REFERENCES `empresas` (`codigoempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sucursales` */

/*Table structure for table `tiposdocumentos` */

DROP TABLE IF EXISTS `tiposdocumentos`;

CREATE TABLE `tiposdocumentos` (
  `codigotipodocumento` int NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`codigotipodocumento`),
  UNIQUE KEY `descripcion_UNIQUE` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tiposdocumentos` */

/*Table structure for table `tiposimpuestos` */

DROP TABLE IF EXISTS `tiposimpuestos`;

CREATE TABLE `tiposimpuestos` (
  `codigotipoimpuesto` int NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `porcentaje` decimal(5,2) NOT NULL,
  `dividirpor` decimal(5,2) NOT NULL,
  PRIMARY KEY (`codigotipoimpuesto`),
  UNIQUE KEY `descripcion_UNIQUE` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tiposimpuestos` */

/*Table structure for table `unidadesmedidas` */

DROP TABLE IF EXISTS `unidadesmedidas`;

CREATE TABLE `unidadesmedidas` (
  `codigounidadmedida` int NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `simbolo` varchar(45) NOT NULL,
  PRIMARY KEY (`codigounidadmedida`),
  UNIQUE KEY `simbolo_UNIQUE` (`simbolo`),
  UNIQUE KEY `descripcion_UNIQUE` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `unidadesmedidas` */

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `codigousuario` int NOT NULL,
  `cedula` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `clave` varchar(45) NOT NULL,
  `activo` int NOT NULL,
  `codigonivelusuario` int NOT NULL,
  PRIMARY KEY (`codigousuario`),
  UNIQUE KEY `cedula_UNIQUE` (`cedula`),
  UNIQUE KEY `clave_UNIQUE` (`clave`),
  UNIQUE KEY `usuario_UNIQUE` (`usuario`),
  KEY `fk_usuarios_nivelesusuarios1_idx` (`codigonivelusuario`),
  CONSTRAINT `fk_usuarios_nivelesusuarios1` FOREIGN KEY (`codigonivelusuario`) REFERENCES `nivelesusuarios` (`codigonivelusuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `usuarios` */

/*Table structure for table `ventas` */

DROP TABLE IF EXISTS `ventas`;

CREATE TABLE `ventas` (
  `codigotipodocumento` int NOT NULL,
  `codigosucursal` int(3) unsigned zerofill NOT NULL,
  `codigopuntoexpedicion` int(3) unsigned zerofill NOT NULL,
  `numeroventa` int(7) unsigned zerofill NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `codigocondicion` int NOT NULL,
  `codigoestadoventa` int NOT NULL,
  `codigocliente` int NOT NULL,
  `codigousuario` int NOT NULL,
  PRIMARY KEY (`codigotipodocumento`,`codigosucursal`,`codigopuntoexpedicion`,`numeroventa`),
  KEY `fk_ventas_condiciones1_idx` (`codigocondicion`),
  KEY `fk_ventas_estadosventas1_idx` (`codigoestadoventa`),
  KEY `fk_ventas_clientes1_idx` (`codigocliente`),
  KEY `fk_ventas_usuarios1_idx` (`codigousuario`),
  CONSTRAINT `fk_ventas_clientes1` FOREIGN KEY (`codigocliente`) REFERENCES `clientes` (`codigocliente`),
  CONSTRAINT `fk_ventas_condiciones1` FOREIGN KEY (`codigocondicion`) REFERENCES `condiciones` (`codigocondicion`),
  CONSTRAINT `fk_ventas_estadosventas1` FOREIGN KEY (`codigoestadoventa`) REFERENCES `estadosdocumentos` (`codigoestadoventa`),
  CONSTRAINT `fk_ventas_tiposdocumentos1` FOREIGN KEY (`codigotipodocumento`) REFERENCES `tiposdocumentos` (`codigotipodocumento`),
  CONSTRAINT `fk_ventas_usuarios1` FOREIGN KEY (`codigousuario`) REFERENCES `usuarios` (`codigousuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ventas` */

/*Table structure for table `vciudades` */

DROP TABLE IF EXISTS `vciudades`;

/*!50001 DROP VIEW IF EXISTS `vciudades` */;
/*!50001 DROP TABLE IF EXISTS `vciudades` */;

/*!50001 CREATE TABLE  `vciudades`(
 `codigodepartamento` int ,
 `departamento` varchar(45) ,
 `codigopais` int ,
 `pais` varchar(45) ,
 `codigociudad` int ,
 `ciudad` varchar(45) 
)*/;

/*Table structure for table `vdepartamentos` */

DROP TABLE IF EXISTS `vdepartamentos`;

/*!50001 DROP VIEW IF EXISTS `vdepartamentos` */;
/*!50001 DROP TABLE IF EXISTS `vdepartamentos` */;

/*!50001 CREATE TABLE  `vdepartamentos`(
 `codigodepartamento` int ,
 `nombre` varchar(45) ,
 `codigopais` int ,
 `pais` varchar(45) 
)*/;

/*Table structure for table `vdepositos` */

DROP TABLE IF EXISTS `vdepositos`;

/*!50001 DROP VIEW IF EXISTS `vdepositos` */;
/*!50001 DROP TABLE IF EXISTS `vdepositos` */;

/*!50001 CREATE TABLE  `vdepositos`(
 `deposito` varchar(45) ,
 `codigodeposito` int ,
 `codigosucursal` int(3) unsigned zerofill ,
 `sucursal` varchar(45) 
)*/;

/*Table structure for table `vmercaderias` */

DROP TABLE IF EXISTS `vmercaderias`;

/*!50001 DROP VIEW IF EXISTS `vmercaderias` */;
/*!50001 DROP TABLE IF EXISTS `vmercaderias` */;

/*!50001 CREATE TABLE  `vmercaderias`(
 `codigointerno` int ,
 `codigobarras` varchar(45) ,
 `descripcion` varchar(150) ,
 `codigounidadmedida` int ,
 `unidadmedida` varchar(45) ,
 `simbolo` varchar(45) ,
 `codigomarca` int ,
 `marca` varchar(45) ,
 `codigocategoria` int ,
 `categoria` varchar(45) ,
 `codigotipoimpuesto` int ,
 `tipoimpuesto` varchar(45) ,
 `porcentaje` decimal(5,2) ,
 `dividirpor` decimal(5,2) ,
 `codigodeposito` int ,
 `stockactual` decimal(10,2) ,
 `stockminimo` decimal(10,2) ,
 `stockmaximo` decimal(10,2) ,
 `preciocompra` decimal(14,2) ,
 `precioventaminorista` decimal(14,2) ,
 `precioventamayorista` decimal(14,2) ,
 `activo` int 
)*/;

/*Table structure for table `vsucursales` */

DROP TABLE IF EXISTS `vsucursales`;

/*!50001 DROP VIEW IF EXISTS `vsucursales` */;
/*!50001 DROP TABLE IF EXISTS `vsucursales` */;

/*!50001 CREATE TABLE  `vsucursales`(
 `empresa` varchar(45) ,
 `codigoempresa` int ,
 `codigosucursal` int(3) unsigned zerofill ,
 `sucursal` varchar(45) ,
 `direccion` varchar(45) ,
 `telefono` varchar(45) ,
 `celular` varchar(45) ,
 `email` varchar(45) ,
 `codigociudad` int ,
 `ciudad` varchar(45) 
)*/;

/*View structure for view vciudades */

/*!50001 DROP TABLE IF EXISTS `vciudades` */;
/*!50001 DROP VIEW IF EXISTS `vciudades` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vciudades` AS select `d`.`codigodepartamento` AS `codigodepartamento`,`d`.`nombre` AS `departamento`,`d`.`codigopais` AS `codigopais`,`p`.`nombre` AS `pais`,`c`.`codigociudad` AS `codigociudad`,`c`.`nombre` AS `ciudad` from ((`departamentos` `d` join `paises` `p`) join `ciudades` `c`) where ((`d`.`codigopais` = `p`.`codigopais`) and (`d`.`codigodepartamento` = `c`.`codigodepartamento`)) */;

/*View structure for view vdepartamentos */

/*!50001 DROP TABLE IF EXISTS `vdepartamentos` */;
/*!50001 DROP VIEW IF EXISTS `vdepartamentos` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vdepartamentos` AS select `d`.`codigodepartamento` AS `codigodepartamento`,`d`.`nombre` AS `nombre`,`d`.`codigopais` AS `codigopais`,`p`.`nombre` AS `pais` from (`departamentos` `d` join `paises` `p`) where (`d`.`codigopais` = `p`.`codigopais`) */;

/*View structure for view vdepositos */

/*!50001 DROP TABLE IF EXISTS `vdepositos` */;
/*!50001 DROP VIEW IF EXISTS `vdepositos` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vdepositos` AS select `depositos`.`nombre` AS `deposito`,`depositos`.`codigodeposito` AS `codigodeposito`,`sucursales`.`codigosucursal` AS `codigosucursal`,`sucursales`.`nombre` AS `sucursal` from (`depositos` join `sucursales` on((`depositos`.`codigosucursal` = `sucursales`.`codigosucursal`))) */;

/*View structure for view vmercaderias */

/*!50001 DROP TABLE IF EXISTS `vmercaderias` */;
/*!50001 DROP VIEW IF EXISTS `vmercaderias` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vmercaderias` AS select `mercaderias`.`codigointerno` AS `codigointerno`,`mercaderias`.`codigobarras` AS `codigobarras`,`mercaderias`.`descripcion` AS `descripcion`,`mercaderias`.`codigounidadmedida` AS `codigounidadmedida`,`unidadesmedidas`.`descripcion` AS `unidadmedida`,`unidadesmedidas`.`simbolo` AS `simbolo`,`mercaderias`.`codigomarca` AS `codigomarca`,`marcas`.`descripcion` AS `marca`,`mercaderias`.`codigocategoria` AS `codigocategoria`,`categorias`.`descripcion` AS `categoria`,`mercaderias`.`codigotipoimpuesto` AS `codigotipoimpuesto`,`tiposimpuestos`.`descripcion` AS `tipoimpuesto`,`tiposimpuestos`.`porcentaje` AS `porcentaje`,`tiposimpuestos`.`dividirpor` AS `dividirpor`,`stock`.`codigodeposito` AS `codigodeposito`,`stock`.`stockactual` AS `stockactual`,`stock`.`stockminimo` AS `stockminimo`,`stock`.`stockmaximo` AS `stockmaximo`,`stock`.`preciocompra` AS `preciocompra`,`stock`.`precioventaminorista` AS `precioventaminorista`,`stock`.`precioventamayorista` AS `precioventamayorista`,`mercaderias`.`activo` AS `activo` from (((((`mercaderias` join `unidadesmedidas` on((`mercaderias`.`codigounidadmedida` = `unidadesmedidas`.`codigounidadmedida`))) join `marcas` on((`mercaderias`.`codigomarca` = `marcas`.`codigomarca`))) join `categorias` on((`mercaderias`.`codigocategoria` = `categorias`.`codigocategoria`))) join `tiposimpuestos` on((`mercaderias`.`codigotipoimpuesto` = `tiposimpuestos`.`codigotipoimpuesto`))) join `stock` on((`stock`.`codigointerno` = `mercaderias`.`codigointerno`))) */;

/*View structure for view vsucursales */

/*!50001 DROP TABLE IF EXISTS `vsucursales` */;
/*!50001 DROP VIEW IF EXISTS `vsucursales` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vsucursales` AS select `empresas`.`razonsocial` AS `empresa`,`sucursales`.`codigoempresa` AS `codigoempresa`,`sucursales`.`codigosucursal` AS `codigosucursal`,`sucursales`.`nombre` AS `sucursal`,`sucursales`.`direccion` AS `direccion`,`sucursales`.`telefono` AS `telefono`,`sucursales`.`celular` AS `celular`,`sucursales`.`email` AS `email`,`sucursales`.`codigociudad` AS `codigociudad`,`ciudades`.`nombre` AS `ciudad` from ((`empresas` join `sucursales` on((`empresas`.`codigoempresa` = `sucursales`.`codigoempresa`))) join `ciudades` on((`sucursales`.`codigociudad` = `ciudades`.`codigociudad`))) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
