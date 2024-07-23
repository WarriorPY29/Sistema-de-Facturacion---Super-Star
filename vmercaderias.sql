CREATE
VIEW vmercaderias
AS
SELECT
mercaderias.codigointerno
,mercaderias.codigobarras
,mercaderias.descripcion
,mercaderias.codigounidadmedida
,unidadesmedidas.descripcion AS unidadmedida
,unidadesmedidas.simbolo
,mercaderias.codigomarca
,marcas.descripcion AS marca
,mercaderias.codigocategoria
,categorias.descripcion AS categoria
,mercaderias.codigotipoimpuesto 
,tiposimpuestos.descripcion AS tipoimpuesto
,tiposimpuestos.porcentaje
,tiposimpuestos.dividirpor
,stock.codigodeposito
,stock.stockactual
,stock.stockminimo
,stock.stockmaximo
,stock.preciocompra
,stock.precioventaminorista
,stock.precioventamayorista
FROM
superestrella.mercaderias
INNER JOIN superestrella.unidadesmedidas
ON (mercaderias.codigounidadmedida = unidadesmedidas.codigounidadmedida)
INNER JOIN superestrella.marcas
ON (mercaderias.codigomarca = marcas.codigomarca)
INNER JOIN superestrella.categorias
ON (mercaderias.codigocategoria = categorias.codigocategoria)
INNER JOIN superestrella.tiposimpuestos 
ON (mercaderias.codigotipoimpuesto = tiposimpuestos.codigotipoimpuesto)
INNER JOIN superestrella.stock
ON (stock.codigointerno = mercaderias.codigointerno);