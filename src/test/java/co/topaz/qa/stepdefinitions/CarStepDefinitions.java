package co.topaz.qa.stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.pages.PageObject;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class CarStepDefinitions extends PageObject {

    private static final By INVENTORY = By.className("inventory_list");
    private static final By BACKPACK = By.id("add-to-cart-sauce-labs-backpack");
    private static final By REMOVE_BACKPACK = By.id("remove-sauce-labs-backpack");
    private static final By BIKE_LIGHT = By.id("add-to-cart-sauce-labs-bike-light");
    private static final By REMOVE_BIKE_LIGHT = By.id("remove-sauce-labs-bike-light");
    private static final By CART_LINK = By.className("shopping_cart_link");
    private static final By CART_BADGE = By.className("shopping_cart_badge");
    private static final By CART_ITEMS = By.cssSelector(".cart_item");
    private static final By CART_PRODUCT_NAMES =
            By.cssSelector(".cart_item .inventory_item_name");
    private static final By CART_PRODUCT_PRICES =
            By.cssSelector(".cart_item .inventory_item_price");
    private static final By SORT_CONTAINER = By.className("product_sort_container");
    private static final By INVENTORY_PRICES =
            By.cssSelector(".inventory_item_price");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private static final By SUBTOTAL = By.className("summary_subtotal_label");
    private static final By TAX = By.className("summary_tax_label");
    private static final By TOTAL = By.className("summary_total_label");

    private WebDriverWait crearEspera() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(20));
    }

    @When("agrega la mochila y la luz de bicicleta al carrito")
    public void agregarProductosAlCarrito() {
        crearEspera().until(ExpectedConditions.urlContains("inventory.html"));
        crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(INVENTORY)
        );

        agregarProductoSiHaceFalta(BACKPACK, REMOVE_BACKPACK);
        agregarProductoSiHaceFalta(BIKE_LIGHT, REMOVE_BIKE_LIGHT);

        crearEspera().until(
                ExpectedConditions.elementToBeClickable(CART_LINK)
        ).click();

        crearEspera().until(
                ExpectedConditions.numberOfElementsToBe(CART_ITEMS, 2)
        );
    }

    private void agregarProductoSiHaceFalta(By botonAgregar, By botonQuitar) {
        if (!getDriver().findElements(botonQuitar).isEmpty()) {
            return;
        }

        crearEspera().until(
                ExpectedConditions.elementToBeClickable(botonAgregar)
        ).click();

        crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(botonQuitar)
        );
    }

    @When("ordena los productos por precio de menor a mayor")
    public void ordenarProductosPorPrecioAscendente() {
        crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(INVENTORY)
        );

        WebElement selector = crearEspera().until(
                ExpectedConditions.elementToBeClickable(SORT_CONTAINER)
        );

        new Select(selector).selectByValue("lohi");

        crearEspera().until(driver ->
                new Select(driver.findElement(SORT_CONTAINER))
                        .getFirstSelectedOption()
                        .getAttribute("value")
                        .equals("lohi")
        );
    }

    @Then("los productos deben mostrarse en orden ascendente de precio")
    public void validarOrdenAscendenteDePrecio() {
        List<BigDecimal> precios = crearEspera().until(driver -> {
            List<WebElement> elementos = driver.findElements(INVENTORY_PRICES);

            if (elementos.isEmpty()) {
                return null;
            }

            return elementos.stream()
                    .map(elemento -> new BigDecimal(
                            elemento.getText().replace("$", "").trim()
                    ))
                    .toList();
        });

        for (int i = 0; i < precios.size() - 1; i++) {
            Assertions.assertTrue(
                    precios.get(i).compareTo(precios.get(i + 1)) <= 0,
                    "Los productos no están ordenados de menor a mayor. "
                            + "Precios encontrados: " + precios
            );
        }
    }

    @Then("el carrito debe mostrar dos productos")
    public void validarCantidadProductos() {
        String cantidad = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(CART_BADGE)
        ).getText().trim();

        Assertions.assertEquals(
                "2", cantidad,
                "El indicador del carrito debe mostrar dos productos."
        );

        Assertions.assertEquals(
                2, getDriver().findElements(CART_ITEMS).size(),
                "El carrito debe contener exactamente dos productos."
        );
    }

    @Then("el carrito debe contener {string} y {string}")
    public void validarProductosEnCarrito(
            String primerProducto, String segundoProducto) {

        List<String> nombres = crearEspera().until(driver -> {
            List<WebElement> elementos = driver.findElements(CART_PRODUCT_NAMES);
            return elementos.size() >= 2
                    ? elementos.stream().map(WebElement::getText).toList()
                    : null;
        });

        Assertions.assertTrue(
                nombres.contains(primerProducto),
                "No se encontró el producto: " + primerProducto
        );

        Assertions.assertTrue(
                nombres.contains(segundoProducto),
                "No se encontró el producto: " + segundoProducto
        );
    }

    @Then("el subtotal debe ser la suma de los precios de ambos productos")
    public void validarSubtotalCarrito() {
        List<WebElement> precios = crearEspera().until(driver -> {
            List<WebElement> elementos = driver.findElements(CART_PRODUCT_PRICES);
            return elementos.size() == 2 ? elementos : null;
        });

        BigDecimal subtotalCalculado = BigDecimal.ZERO;

        for (WebElement elemento : precios) {
            String precioTexto = elemento.getText().replace("$", "").trim();
            subtotalCalculado = subtotalCalculado.add(
                    new BigDecimal(precioTexto)
            );
        }

        Assertions.assertEquals(
                0, new BigDecimal("39.98").compareTo(subtotalCalculado),
                "La suma de los productos debe ser $39.98."
        );
    }

    @When("continua al checkout con los datos {string} {string} {string}")
    public void continuarAlCheckout(
            String nombre, String apellido, String codigoPostal) {

        abrirFormularioCheckout();
        diligenciarFormulario(nombre, apellido, codigoPostal);

        crearEspera().until(
                ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON)
        ).click();

        try {
            crearEspera().until(
                    ExpectedConditions.urlContains("checkout-step-two.html")
            );
            crearEspera().until(
                    ExpectedConditions.visibilityOfElementLocated(SUBTOTAL)
            );
        } catch (TimeoutException e) {
            Assertions.fail(
                    "No se llegó al resumen de compra. URL actual: "
                            + getDriver().getCurrentUrl()
                            + ". Mensaje mostrado: " + obtenerMensajeError()
            );
        }
    }

    @When("intenta continuar al checkout con los datos {string} {string} {string}")
    public void intentarContinuarAlCheckout(
            String nombre, String apellido, String codigoPostal) {

        abrirFormularioCheckout();
        diligenciarFormulario(nombre, apellido, codigoPostal);

        crearEspera().until(
                ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON)
        ).click();
    }

    private void abrirFormularioCheckout() {
        crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(CART_ITEMS)
        );
        crearEspera().until(
                ExpectedConditions.elementToBeClickable(CHECKOUT_BUTTON)
        ).click();
        crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(FIRST_NAME)
        );
    }

    private void diligenciarFormulario(
            String nombre, String apellido, String codigoPostal) {

        ingresarDato(FIRST_NAME, nombre, "nombre");
        ingresarDato(LAST_NAME, apellido, "apellido");
        ingresarDato(POSTAL_CODE, codigoPostal, "código postal");
    }

    private void ingresarDato(By selector, String valor, String nombreCampo) {
        WebElement campo = crearEspera().until(
                ExpectedConditions.elementToBeClickable(selector)
        );

        campo.clear();

        if (!valor.isEmpty()) {
            campo.sendKeys(valor);
        }

        Assertions.assertEquals(
                valor, campo.getAttribute("value"),
                "El campo " + nombreCampo
                        + " no quedó diligenciado correctamente."
        );
    }

    @Then("debe mostrarse el error de checkout {string}")
    public void validarErrorCheckout(String mensajeEsperado) {
        WebElement error = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE)
        );

        Assertions.assertEquals(
                mensajeEsperado, error.getText().trim(),
                "El mensaje de validación no coincide."
        );

        Assertions.assertTrue(
                getDriver().getCurrentUrl().contains("checkout-step-one.html"),
                "El sistema no debe avanzar si faltan datos."
        );
    }

    private String obtenerMensajeError() {
        List<WebElement> errores = getDriver().findElements(ERROR_MESSAGE);

        return errores.isEmpty()
                ? "No apareció un mensaje de validación."
                : errores.get(0).getText().trim();
    }

    @Then("el resumen de compra debe mostrar subtotal {string}")
    public void validarSubtotal(String esperado) {
        validarImporte(SUBTOTAL, "Item total: $", esperado, "Subtotal");
    }

    @Then("el impuesto debe ser {string}")
    public void validarImpuesto(String esperado) {
        validarImporte(TAX, "Tax: $", esperado, "Impuesto");
    }

    @Then("el total de compra debe ser {string}")
    public void validarTotal(String esperado) {
        validarImporte(TOTAL, "Total: $", esperado, "Total de compra");
    }

    private void validarImporte(
            By selector, String prefijo, String esperado, String concepto) {

        WebElement elemento = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(selector)
        );

        String texto = elemento.getText().trim();

        Assertions.assertTrue(
                texto.startsWith(prefijo),
                "Formato inesperado para " + concepto + ": " + texto
        );

        BigDecimal actual = new BigDecimal(
                texto.substring(prefijo.length()).trim()
        );

        Assertions.assertEquals(
                0, new BigDecimal(esperado).compareTo(actual),
                concepto + " incorrecto. Esperado: $"
                        + esperado + ", obtenido: $" + actual
        );
    }
}