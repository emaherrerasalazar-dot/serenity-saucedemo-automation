# Riesgos, escenarios pendientes y mejoras propuestas

## Riesgos identificados
- Cambios en la interfaz pueden invalidar selectores de Selenium.
- Diferencias entre Chrome, ChromeDriver y Selenium pueden afectar la ejecucion.
- Las rutas locales de Chrome y ChromeDriver pueden dificultar la ejecucion en otro equipo.
- Los importes deben compararse numericamente y no como texto.
- Las evidencias deben permitir identificar el paso en que ocurre una falla.

## Escenarios o requisitos pendientes de confirmar
- Verificar el uso de Scenario Outline y Examples en checkout negativo.
- Confirmar la validacion explicita del contador del carrito.
- Confirmar que las credenciales se administren como datos configurables.
- Ejecutar y validar los comandos de humo, regresion y seleccion individual documentados en README.

## Mejoras propuestas
- Centralizar los datos de prueba.
- Mantener selectores robustos y esperas explicitas.
- Utilizar BigDecimal para comparaciones monetarias.
- Incorporar ejecucion en integracion continua.
- Revisar la compatibilidad de las versiones del navegador y Selenium.
- Conservar reportes y capturas como evidencia.

## Alcance
Los puntos pendientes se presentan como verificaciones o mejoras, no como funcionalidades cuya implementacion ya este confirmada.
