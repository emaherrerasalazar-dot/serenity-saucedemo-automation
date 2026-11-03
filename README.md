# Topaz QA Automation

## Objetivo

Automatizar el flujo de compra de SauceDemo con Serenity BDD, Cucumber/Gherkin, Selenium WebDriver, Java y Maven, mediante escenarios de prueba automatizados, validaciones claras y generación de reportes.

## Aplicación bajo prueba

https://www.saucedemo.com/

## Prerrequisitos

- JDK 17.
- Apache Maven.
- Google Chrome.
- ChromeDriver compatible con la versión de Chrome instalada.

## Tecnologías utilizadas

- Java 17.
- Serenity BDD 4.2.34.
- Cucumber 7.20.1.
- Selenium WebDriver 4.27.0.
- JUnit 5.11.4.
- Maven.

## Arquitectura del proyecto

Los escenarios de prueba escritos en Gherkin se encuentran en:

`src/test/resources/features/`

Las definiciones de pasos se encuentran en:

`src/test/java/co/topaz/qa/stepdefinitions/`

El runner de Cucumber se encuentra en:

`src/test/java/co/topaz/qa/runners/`

La configuración del navegador, las opciones de Chrome, las capturas y la URL base se encuentran en:

`src/test/resources/serenity.conf`

El proyecto utiliza el patrón Page Object Model (POM) mediante clases basadas en `PageObject` de Serenity.

## Escenarios automatizados

### Inicio de sesión

- Inicio de sesión exitoso.
- Validación del inventario y del encabezado `Products`.
- Inicio de sesión fallido con credenciales inválidas.
- Validación del mensaje de error y comprobación de que no se accede al inventario.

### Ordenamiento de productos

- Ordenamiento de los productos por precio de menor a mayor.
- Obtención de los precios mostrados en la interfaz.
- Validación programática del orden ascendente.

### Carrito de compras

- Adición de Sauce Labs Backpack y Sauce Labs Bike Light.
- Validación de la cantidad de productos.
- Validación de los nombres de los productos.
- Cálculo de la suma de los precios y comparación con el subtotal esperado de `$39.98`.

### Checkout

- Validación de los productos en el resumen de compra.
- Validación del subtotal de `$39.98`.
- Validación del impuesto de `$3.20`.
- Validación del total de `$43.18`.
- Finalización exitosa de la compra y validación del mensaje de confirmación.
- Pruebas negativas mediante `Scenario Outline` y `Examples` para validar campos obligatorios.

## Comparación de importes monetarios

Los precios de los productos se obtienen de la interfaz y se convierten a `BigDecimal` antes de sumarlos. Este enfoque evita los problemas de precisión que pueden presentarse al realizar cálculos monetarios con números de punto flotante.

La suma de los precios de los dos productos se compara con el subtotal esperado de `$39.98`. En el checkout se validan por separado el subtotal, el impuesto y el total mostrados por la aplicación.

## Configuración

La URL base y las opciones del navegador están centralizadas en `src/test/resources/serenity.conf`.

La configuración utiliza Chrome y la ruta relativa `drivers/chromedriver.exe`. Es necesario disponer de una versión de ChromeDriver compatible con la versión de Google Chrome instalada.

Las credenciales de prueba utilizadas actualmente están declaradas en los escenarios del archivo `login.feature`. Para adaptar el proyecto a otros entornos, se recomienda centralizar los datos de prueba y permitir su configuración externa.

## Ejecución de las pruebas

Ejecutar la suite completa:

```powershell
mvn clean verify
```

Ejecutar la suite de humo:

```powershell
mvn clean verify "-Dcucumber.filter.tags=@smoke"
```

Ejecutar la suite de regresión:

```powershell
mvn clean verify "-Dcucumber.filter.tags=@regression"
```

Ejecutar únicamente el feature de productos:

```powershell
mvn clean verify "-Dcucumber.features=src/test/resources/features/products.feature"
```

Ejecutar un escenario por nombre:

```powershell
mvn clean verify "-Dcucumber.filter.name=Ordenar productos por precio de menor a mayor"
```

Ejecut