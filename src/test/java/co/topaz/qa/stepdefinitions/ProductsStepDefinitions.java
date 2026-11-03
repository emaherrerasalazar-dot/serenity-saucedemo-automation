package co.topaz.qa.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.pages.PageObject;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductsStepDefinitions extends PageObject {

    private static final String URL =
            "https://www.saucedemo.com/";

    private static final String USUARIO =
            "standard_user";

    private static final String CLAVE =
            "secret_sauce";

    private static final By CAMPO_USUARIO =
            By.id("user-name");

    private static final By CAMPO_CLAVE =
            By.id("password");

    private static final By BOTON_LOGIN =
            By.id("login-button");

    private static final By INVENTARIO =
            By.className("inventory_list");

    private static final By TITULO_PRODUCTOS =
            By.className("title");

    private static final By PRODUCTOS =
            By.className("inventory_item");

    private WebDriverWait crearEspera() {
        return new WebDriverWait(
                getDriver(),
                Duration.ofSeconds(20)
        );
    }

    @Given("que el usuario inicia sesion correctamente")
    public void iniciarSesionCorrectamente() {
        getDriver().get(URL);

        WebElement campoUsuario = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        CAMPO_USUARIO
                )
        );

        campoUsuario.clear();
        campoUsuario.sendKeys(USUARIO);

        WebElement campoClave = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        CAMPO_CLAVE
                )
        );

        campoClave.clear();
        campoClave.sendKeys(CLAVE);

        crearEspera().until(
                ExpectedConditions.elementToBeClickable(BOTON_LOGIN)
        ).click();

        crearEspera().until(
                ExpectedConditions.urlContains("inventory.html")
        );

        crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(INVENTARIO)
        );

        Assertions.assertTrue(
                getDriver().getCurrentUrl().contains("inventory.html"),
                "El usuario no llegó a la página del inventario."
        );
    }

    @Then("debe visualizar el inventario de productos")
    public void verificarInventario() {
        WebElement inventario = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        INVENTARIO
                )
        );

        Assertions.assertTrue(
                inventario.isDisplayed(),
                "El inventario de productos no está visible."
        );

        List<WebElement> productos = crearEspera().until(
                driver -> {
                    List<WebElement> encontrados =
                            driver.findElements(PRODUCTOS);

                    return !encontrados.isEmpty()
                            ? encontrados
                            : null;
                }
        );

        Assertions.assertFalse(
                productos.isEmpty(),
                "No se encontraron productos en el inventario."
        );
    }

    @Then("debe visualizar el encabezado {string}")
    public void verificarEncabezado(String encabezadoEsperado) {
        WebElement titulo = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        TITULO_PRODUCTOS
                )
        );

        Assertions.assertEquals(
                encabezadoEsperado,
                titulo.getText().trim(),
                "El encabezado de la página no coincide."
        );
    }
}