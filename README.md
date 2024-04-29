# Courier Application
Small Distance Courier Service

### Run the project

Firstly, let's build it with 
    
    mvn package

Or use _clean_, to remove previous build files

    mvn clean package

It compiles, runs unit tests and packages the project into a _jar_ file and puts it into the _CourierApp/target_ folder.

To run the Application, run

    java -jar target/CourierApp-1.0-SNAPSHOT.jar <inputfile>

Input file should be given as an argument, application reads inputs from the specified .txt file. The file should be placed in _**src/main/resources/input_files**_ folder.

### Tests

Tests are created in the **src/test/java/com.courier** location. Tests are created before functionalities, covering edge cases.
Run tests with below command:

    mvn test

### Models

Create models to group respective fields for each Object.
Namely, **Offer** model, **Package** model, **Vehicle** model in a model package.

### Services

Create **PackageService** to include operations such as calculating discount for applicable offers, total cost for each package, loading packages into vehicles, and estimating time for each package.
Implementation of these functionalites only by passing respective tests.