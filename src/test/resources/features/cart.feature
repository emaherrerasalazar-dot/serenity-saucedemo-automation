
@regression @cart
Feature: Carrito y resumen de compra

  Scenario: Agregar dos productos y validar los importes de compra
    Given que el usuario inicia sesion correctamente
    When agrega la mochila y la luz de bicicleta al carrito
    Then el carrito debe mostrar dos productos
    And el carrito debe contener "Sauce Labs Backpack" y "Sauce Labs Bike Light"
    And el subtotal debe ser la suma de los precios de ambos productos
    When continua al checkout con los datos "Ana" "Tester" "110111"
    Then el resumen de compra debe mostrar subtotal "39.98"
    And el impuesto debe ser "3.20"
    And el total de compra debe ser "43.18"