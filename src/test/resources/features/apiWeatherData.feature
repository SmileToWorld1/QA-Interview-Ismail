
@api  @weatherData
Feature: Validate weather backend returns
  @weather  @positiveScenario
  Scenario: Successful Retrieval of Current Weather
    When I request the current weather for the city "vienna"
    Then the response status should be 200
    And the response body fields should not be null
    And the response time should be under 1500

  @weather @negativeScenario @invalidMethods
  Scenario Outline: Unsuccessful Attempt to Retrieve Weather Data with Unsupported HTTP Methods
    When I submit a invalid "<method>" method to retrieval weather data
    Then the response status should be 405
    And the response body should contain the error message "Method Not Allowed"

    Examples:
      | method |
      | POST   |
      | PUT    |
      | PATCH  |



  @weather  @positiveScenario @condition @icon
  Scenario Outline: Sending a condition ID returns the correct weather condition and corresponding icon
    When I send the condition ID <condition_id> with putCondition request
    Then the response status should be 200
    When I request the current weather for the city "vienna"
    Then the response status should be 200
    Then the response should have the "condition" field with value "<condition>"
    And the response should have the "icon" field with value "<condition>"

    Examples:
      | condition_id | condition |
      | 1            | clear     |
      | 2            | windy     |
      | 3            | mist      |
      | 4            | drizzle   |
      | 5            | dust      |


  @weather  @positiveScenario @tempInFahrenheit
  Scenario Outline: Sending a tempInFahrenheit returns the correct tempInFahrenheit value
    When I send the Fahrenheit temperature <tempInFahrenheit> with putTemp request
    Then the response status should be 200
    When I request the current weather for the city "vienna"
    Then the response status should be 200
    And the response should have the "tempInFahrenheit" field with value "<tempInFahrenheit>"
    And "tempInFahrenheit" field value should have zero digits

    Examples:
      | tempInFahrenheit |
      | -25              |
      | -1               |
      | 0                |
      | 17               |
      | 99               |

  @weather  @positiveScenario @tempInCelsius @description
  Scenario Outline: Temperature in Fahrenheit is converted to Celsius with normal rounding and corresponding weather description
    When I send the Fahrenheit temperature <tempInFahrenheit> with putTemp request
    Then the response status should be 200
    When I request the current weather for the city "vienna"
    Then the response status should be 200
    And "tempInCelsius" field value should have zero digits
    And the response should have the "tempInCelsius" field with value "<tempInCelsius>"
    And the response should have the "description" field with value "<description>"

    Examples:
      | tempInFahrenheit | tempInCelsius | description             | #
      | 14               | -10           | The weather is freezing | # celsius <= 0 freezing
      | 32               | 0             | The weather is freezing | # celsius <= 0	freezing
      | 33               | 1             | The weather is cold     | # celsius < 10	cold -> 33 Fahrenheit => 0.555556 according to normal rounding rules to the nearest integer, becomes 1
      | 34               | 1             | The weather is cold     | # celsius < 10	cold -> 34 Fahrenheit => 1.11111  according to normal rounding rules to the nearest integer, becomes 1
      | 49               | 9             | The weather is cold     | # celsius < 10	cold -> 49 Fahrenheit => 9.44444  according to normal rounding rules to the nearest integer, becomes 9
      | 50               | 10            | The weather is mild     | # celsius < 20	mild
      | 59               | 15            | The weather is mild     | # celsius < 20	mild
      | 66               | 19            | The weather is mild     | # celsius < 20	mild -> 66 Fahrenheit => 18.8889  according to normal rounding rules to the nearest integer, becomes 19
      | 68               | 20            | The weather is warm     | # celsius < 25	warm
      | 76               | 24            | The weather is warm     | # celsius < 25	warm -> 76 Fahrenheit => 24.4444  according to normal rounding rules to the nearest integer, becomes 24
      | 77               | 25            | The weather is hot      | # celsius >= 25 hot
      | 86               | 30            | The weather is hot      | # celsius >= 25 hot









