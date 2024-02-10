@ui @regressionTest  @smokeTest
Feature: Verify the OpenWeather desktop app functions

#  Acceptance Criteria
#  Verify the main page's search field contains correct placeholder text

  @placeHolder  @mainPage
  Scenario: User sees the correct placeholder text in the search field on the main page
    Given I am on the main page
    Then I should see the "placeholder" attribute text as a "Weather in your city"



#  Acceptance Criteria
#  Search for Sydney, and select Sydney, AU from the list
#  Verify the selected city's title is correct
#  Verify that the date shown is correct
#  Verify that the time shown is correct
  @wip @selectCity @dateValidation @timeValidation
  Scenario: Select Sydney, AU and Verify Information
    Given I am on the main page
    When I search "Sydney" in the search box
    When I select "Sydney, AU" from the list
    Then the title should be "Weather forecast - OpenWeatherMap"
    Given the selected city time zone is "+11"
    And the displayed date and time should be the match the current time with a tolerance of 5 minutes

