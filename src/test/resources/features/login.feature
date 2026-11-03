
Feature: Inicio de sesión en SauceDemo
  Como usuario de SauceDemo
  Quiero iniciar sesión
  Para acceder al inventario de productos

  @smoke @login
  Scenario: Inicio de sesión exitoso
    Given que estoy en la página de inicio de SauceDemo
    When ingreso el usuario válido "standard_user" y la contraseña "secret_sauce"
    Then debo visualizar el inventario de productos
    And debo visualizar el encabezado "Products"

  @regression @login
  Scenario: Inicio de sesión fallido con credenciales inválidas
    Given que estoy en la página de inicio de SauceDemo
    When ingreso el usuario "usuario_invalido" y la contraseña "clave_incorrecta"
    Then debo visualizar un mensaje de error de inicio de sesión
    And no debo acceder al inventario de productos
