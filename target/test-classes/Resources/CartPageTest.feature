#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

@cartpage
Feature: Product Page Features


Background: 
Given User is logged in to the application

  Scenario: Products in Cart
    And user has added products to cart
      | Product   | Quantity |
      | zara coat 3 | 2|
      | adidas original |1|
      | iphone 13 pro  |3|
      
    And user opens the cart
    When check for the products in cart
    Then The above products should be present in the cart
    
    Scenario: Delete Products in Cart
    And user has added products to cart
      | Product   | Quantity |
      | zara coat 3 | 2|
      | adidas original |1|
      | iphone 13 pro  |3|
      
    And user opens the cart
    When delete the products in cart
     | adidas original |
      | iphone 13 pro  |
    Then The above products should be deleted

  #@tag2
  #Scenario Outline: Title of your scenario outline
    #Given I want to write a step with <name>
    #When I check for the <value> in step
    #Then I verify the <status> in step
#
    #Examples: 
      #| name  | value | status  |
      #| name1 |     5 | success |
      #| name2 |     7 | Fail    |
