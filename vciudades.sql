CREATE VIEW vciudades AS `ciudades`(SELECT 
d.codigodepartamento,
d.nombre,
d.codigopais,
p.nombre AS pais,
c.codigociudad,
c.nombre AS ciudad
FROM
departamentos AS d,
paises AS p,
ciudades AS c
WHERE d.codigopais = p.codigopais AND d.codigodepartamento = c.codigodepartamento);