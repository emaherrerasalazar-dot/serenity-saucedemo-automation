@regression @checkout
Feature: Finalizar compra

  Scenario: Completar una compra exitosamente
    Given que el usuario inicia sesion correctamente
    When agrega la mochila y la luz de bicicleta al carrito
    And continua al checkout con los datos "Ana" "Tester" "110111"
    Then el resumen del checkout debe contener "Sauce Labs Backpack" y "Sauce Labs Bike Light"
    And el resumen de compra debe mostrar subtotal "39.98"
    And el impuesto debe ser "3.20"
    And el total de compra debe ser "43.18"
    When finaliza la compra
    Then debe mostrarse la confirmacion de compra "Your order has been dispatched, and will arrive just as fast as the pony can get there!"

  @negative
  Scenario Outline: Rechazar checkout con campos obligatorios inválidos
    Given que el usuario inicia sesion correctamente
    When agrega la mochila y la luz de bicicleta al carrito
    And intenta continuar al checkout con los datos "<nombre>" "<apellido>" "<codigoPostal>"
    Then debe mostrarse el error de checkout "<mensajeError>"

    Examples:
      | nombre | apellido | codigoPostal | mensajeError                  |
      |        | Tester   | 110111       | Error: First Name is required |
      | Ana    |          | 110111       | Error: Last Name is required  |
