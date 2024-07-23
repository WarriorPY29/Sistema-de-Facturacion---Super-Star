CREATE VIEW vdepartamentos AS (
SELECT 
d.codigodepartamento,
d.nombre,
d.codigopais,
p.nombre AS pais
FROM
departamentos AS d,
paises AS p
WHERE d.codigopais = p.codigopais);