# Goodfish UI testing

This project was created to show my ability to use different AT tools and approaches in UI testing.
Goodfish.by is a service with complex structure, dynamically loaded pages, performed calculations in cart and big variety of JS code. At the same time this service allows create order without registration and phone confirmation.
So I have chosen it to test in different ways and demonstrate my results without need to create an account.

What could be more important than correct cart forming, money and quantity calculations? Therefore I made decision to test catalog and cart sections.
I have not developed all possible test scenarios. But even that amount of scenarios helped me to **FIND SEVERAL BUGS** there:
1. Total order weight is counted incorrect. Because of some product items do not contain weight description.
2. Some catalog category links names are different from category header name. So if you remember category header name you may not find it in catalog.


## I used following tools for project:

* **Selenium WebDriver** - to automate web application for testing purposes
* **TestNG** - to run tests using flexible configuration
* **Maven** and its **Surefire plugin** - to download the project dependency libraries, run project and generate test report

I also used **Page Object model** design pattern. Web pages in my project are represented as classes, and the various elements on the page are defined as 
variables on the class. All possible user interactions are implemented as methods.
Automated tests are situated separately and may use different pages elements and its interactions.


## How to run:

Download project to your local machine. Put chromedriver.exe into resources\drivers\ subfolder.
Open up a terminal and move to the project. Run the maven test command:
`mvn test`.
Test report is generated automatically by surefire-plugin after tests running. Look for report in the following folder:
target\surefire-reports\.

You may configure tests using testng.xml file in main project directory.

*In this project I have not used RemoteWebDriver and Selenium Grid for easy project configuration and run possibility.*