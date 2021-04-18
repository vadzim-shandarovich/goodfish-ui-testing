# Goodfish UI testing

This project was created to show my ability to use different AT tools and approaches in UI testing.
Goodfish.by is a service with complex structure, dynamically loaded pages, performed calculations 
in cart and big variety of JS code. At the same time, this service allows you to create order 
without registration and phone confirmation.
So I have chosen it to test in different ways and demonstrate my results without need to create 
an account.

What could be more important than correct cart forming, money and quantity calculations? 
That's why I made decision to test catalog and cart sections. I have not developed all possible 
tests. But even that amount of scenarios helped me to **FIND SEVERAL BUGS** there:
1. The total order weight is calculated incorrectly. Because some product items do not contain 
weight description.
2. Some catalog category links have a different name from the category header name. So if you 
remember category header name you may not find it in the catalog.

## I used following tools for project:

* **Selenium WebDriver** - to automate web application for testing purposes
* **TestNG** - to execute tests using flexible configuration
* **Maven** and its **Surefire plugin** - to download the project dependency libraries, run project 
and generate test report

I also used the **Page Object model** design pattern. Web pages in my project are represented as 
classes, and the various elements on the page are defined as variables on the class. All possible 
user interactions are implemented as methods.
Automated tests (which are located separately) may use different pages elements and its interactions.

## How to run:

Download project to your local machine. Put chromedriver into `resources\drivers\` subfolder.
Open up a terminal and move to the project. Run the maven test command:
`mvn test`.
Test report is generated automatically by surefire-plugin after tests running. Look for report in 
the following folder: `target\surefire-reports\`.

You may configure tests using testng.xml file in the main project directory.

*In this project I have not used RemoteWebDriver and Selenium Grid for easy project configuration 
and run possibility.*