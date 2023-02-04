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

@productpage
Feature: Product Page Features

  
  Background: 
  Given User logged into the application
  

  Scenario: Products displayed
    When Check for the displayed products
    Then Verify product list
    

    Scenario: Search bar function
    When User enters search names in the search bar 
      |keyword|
      | iphone | 
      | zara |
      | adidas |
    Then The required product is visible
    


 Scenario: Categories functionality
    When check different checkboxes in the 
    | fashion |  
      | electronics | 
      | household |
    Then verify the number products shown
    
  

  Scenario Outline: Search For functionality
    When check different "<checkboxes>" in the 
    Then verify the "<products>" shown
    

    Examples: 
      | checkboxes  | products |
      | men |  iphone 13 pro, adidas original |
      | women | zara coat 3 |
      
 
     Scenario: Add To Cart functionality
    When Add Products to Cart 
    | Product   | Quantity |
      | zara coat 3 | 2|
      | adidas original |1|
      | iphone 13 pro  |3|
    Then verify that products are added to cart
   
