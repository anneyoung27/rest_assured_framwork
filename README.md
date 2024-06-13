# Basic framework for API Automation with REST Assured 
## What's in this repository?
This repository contains a Rest Assured Framework designed for API automation testing. 
## What's the purpose of this framework?
This framework aims to simplify the development, maintenance, and execution of API tests using the popular Rest Assured library in Java.
## Key Features:
- **_Ease of Use_**: Utilizes Rest Assured to write readable and easy-to-write API tests.
- **_Modular Project Structure_**: Separates test code, test data, and configuration for easier maintenance.
- **_Robust Reporting_**: Integration with reporting tools to generate test reports.
- **_Extensibility_**: Easily extendable and customizable to meet specific project needs.
## Tech stacks used:
Use the following:
- Java > 21
- RestAssured
- Maven
- TestNG
- Payload management: POJO (Lombok), Gson, Jackson API
- ExtentReports
- Hamcrest
- Data Faker
- XML
## Project Details
Restful API Automation with Java and Rest Assured
### Project URL
```
endpoint: https://fakeapi.platzi.com/en/rest/users/
```
### Project Structure
![Screenshot 2024-06-13 141706](https://github.com/anneyoung27/rest_assured_framwork/assets/63891089/d07b17de-f41a-419e-8508-58bb6f3ba013)
### Details
#### CRUD operation
  - GET Users
  - CREATE Users
  - UPDATE Users
  - DELETE - Delete Users
#### Utils
  - Assertions utility to validate actual output and expected output
  - JSON utility uses the Jackson library to read JSON data from a file and return it as a Map.
  - Random Data utiliy uses the Data Faker library to generate fake data (random)
  - Rest utils to perform CRUD operation and perform request and response report. 
  - Any string manipulation code and logging
#### Reporting
This framework supports integration with reporting tools like ExtentReports to generate test reports.

## How to run?
- Add/Edit configuration templates
- Choose Maven, then name it to `MavenRunner` then in run name it `test -Denv=qa_data_test`
- Make sure your testng.xml location is in path from repository root. 
