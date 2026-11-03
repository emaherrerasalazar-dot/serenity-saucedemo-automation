package co.topaz.qa.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.pages.PageObject;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginStepDefinitions extends PageObject {

    private static final By CAMPO_USUARIO =
            By.id("user-name");

    private static final By CAMPO_CLAVE =
            By.id("password");

    private static final By BOTON_LOGIN =
            By.id("login-button");

    private static final By INVENTARIO =
            By.className("inventory_list");

    private static final By TITULO =
            By.className("title");

    private static final By MENSAJE_ERROR =
            By.cssSelector("[data-test='error']");

    private WebDriverWait crearEspera() {
        return new WebDriverWait(
                getDriver(),
                Duration.ofSeconds(20)
        );
    }

    @Given("que estoy en la página de inicio de SauceDemo")
    public void abrirPaginaDeInicio() {
        open();

        crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        CAMPO_USUARIO
                )
        );

        crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        CAMPO_CLAVE
                )
        );
    }

    @When("ingreso el usuario válido {string} y la contraseña {string}")
    public void ingresarCredencialesValidas(
            String usuario,
            String clave
    ) {
        diligenciarCredenciales(usuario, clave);

        crearEspera().until(
                ExpectedConditions.elementToBeClickable(BOTON_LOGIN)
        ).click();

        crearEspera().until(
                ExpectedConditions.urlContains("inventory.html")
        );

        crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(INVENTARIO)
        );
    }

    @When("ingreso el usuario {string} y la contraseña {string}")
    public void ingresarCredencialesInvalidas(
            String usuario,
            String clave
    ) {
        diligenciarCredenciales(usuario, clave);

        crearEspera().until(
                ExpectedConditions.elementToBeClickable(BOTON_LOGIN)
        ).click();
    }

    private void diligenciarCredenciales(
            String usuario,
            String clave
    ) {
        WebElement campoUsuario = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        CAMPO_USUARIO
                )
        );

        campoUsuario.clear();
        campoUsuario.sendKeys(usuario);

        WebElement campoClave = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        CAMPO_CLAVE
                )
        );

        campoClave.clear();
        campoClave.sendKeys(clave);
    }

    @Then("debo visualizar el inventario de productos")
    public void verificarInventario() {
        WebElement inventario = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        INVENTARIO
                )
        );

        Assertions.assertTrue(
                inventario.isDisplayed(),
                "El inventario de productos debe estar visible."
        );

        Assertions.assertTrue(
                getDriver().getCurrentUrl().contains("inventory.html"),
                "La URL debe corresponder al inventario."
        );
    }

    @Then("debo visualizar el encabezado {string}")
    public void verificarEncabezado(String encabezadoEsperado) {
        WebElement encabezado = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(TITULO)
        );

        Assertions.assertEquals(
                encabezadoEsperado,
                encabezado.getText().trim(),
                "El encabezado de la página no coincide."
        );
    }

    @Then("debo visualizar un mensaje de error de inicio de sesión")
    public void verificarMensajeDeError() {
        WebElement error = crearEspera().until(
                ExpectedConditions.visibilityOfElementLocated(
                        MENSAJE_ERROR
                )
        );

        Assertions.assertTrue(
                error.getText().contains(
                        "Username and password do not match"
                ),
                "El mensaje debe indicar que las credenciales son inválidas. "
                        + "Mensaje obtenido: " + error.getText()
        );
    }

    @Then("no debo acceder al inventario de productos")
    public void verificarQueNoHayInventario() {
        Assertions.assertFalse(
                getDriver().getCurrentUrl().contains("inventory.html"),
                "El usuario no debe acceder al inventario."
        );

        Assertions.assertTrue(
                getDriver().findElements(INVENTARIO).isEmpty(),
                "El inventario no debe mostrarse si el inicio de sesión falla."
        );
    }
}