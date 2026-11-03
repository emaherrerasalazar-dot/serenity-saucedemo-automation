@regression @products
Feature: Ordenamiento de productos

  Scenario: Ordenar productos por precio de menor a mayor
    Given que el usuario inicia sesion correctamente
    When ordena los productos por precio de menor a mayor
    Then los productos deben mostrarse en orden ascendente de precio