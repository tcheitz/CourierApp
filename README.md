# Courier Service
Small Distance Courier Service

### Run the project

Build the project with 
    
    mvn package

Or use _clean_, to remove previous build files

    mvn clean package

It compiles, runs unit tests and packages the project into a _jar_ file and puts it into the _CourierApp/target_ folder.

To run the Application, run

    java -jar target/CourierApp-1.0-SNAPSHOT.jar <inputfile>

### Inputs
Input file should be given as an argument, application reads inputs from the specified .txt file. The file should be placed in _**src/main/resources/**_ folder.

< input_file.txt > should contain the input format as described in the problem statement.
First line has Base delivery cost and number of packages N. Following N packages details, each package with its id, weight, distance, and offer code. 

**OfferCodes.txt** is a resource file that contains Offers details. To add/remove Offers one must edit the file. **Header line should not be removed**. An offer format is: Each offer should contain Offer code, minimum and maximum distance, minimum and maximum weights, and discount. These are all space separated fields.

### Tests

Tests are created in the **src/test/java/com.courier** location. Tests are created before functionalities, covering edge cases.
Run tests with below command:

    mvn test

### Models

Create models to group respective fields for each Object.
Namely, **Offer** model, **Package** model, **Vehicle** model in a model package.

### Services

**PackageService**: to include operations such as calculating discount for applicable offers, total cost for each package, and filtering packages by more packages, more weights, less distance for efficiency of delivery.

**OfferService**: to register offers from OfferCodes.txt file

**VehicleService** to ship packages by tagging to vehicles, and estimating time for each package.
