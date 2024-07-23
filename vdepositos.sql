CREATE VIEW vdepositos  AS SELECT
depositos.`nombre` AS deposito,
depositos.`codigodeposito`,
sucursales.`codigosucursal`,
sucursales.nombre AS sucursal
FROM
superestrella.`depositos`
INNER JOIN superestrella.sucursales
ON (depositos.`codigosucursal`=sucursales.`codigosucursal`);
