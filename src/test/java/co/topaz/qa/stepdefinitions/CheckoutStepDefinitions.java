package co.topaz.qa.stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

public class CheckoutStepDefinitions extends PageObject {

    @Then("el resumen del checkout debe contener {string} y {string}")
    public void validarProductosEnResumen(
            String primerProducto,
            String segundoProducto) {

        WebElementFacade resumen = $(By.id("checkout_summary_container"));
        resumen.waitUntilVisible();

        Assertions.assertTrue(
                resumen.containsText(primerProducto),
                "El resumen no contiene el producto: " + primerProducto
        );

        Assertions.assertTrue(
                resumen.containsText(segundoProducto),
                "El resumen no contiene el producto: " + segundoProducto
        );
    }

    @When("finaliza la compra")
    public void finalizarCompra() {
        $(By.id("finish"))
                .waitUntilClickable()
                .click();

        $(By.className("complete-header"))
                .waitUntilVisible();
    }

    @Then("debe mostrarse la confirmacion de compra {string}")
    public void validarConfirmacionCompra(String mensajeEsperado) {
        WebElementFacade mensaje = $(By.className("complete-text"));
        mensaje.waitUntilVisible();

        Assertions.assertEquals(
                mensajeEsperado,
                mensaje.getText().trim(),
                "El mensaje de confirmación no coincide."
        );
    }
}