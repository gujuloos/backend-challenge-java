# Welcome to the Tiko Shopping Cart


## Building the application

Please ensure that you have Java JDK 17 on your machine and maven. Navigate to the root of the project folder and build the application with `mvn clean install`

## Running the application

To run the application after it has been successfully built. Please run the following command `mvn spring-boot:run` and the application should start up with the following message

> Started Application in x.xxx seconds (process running for x.xxx)

## Using the application

The application currently only has 1 endpoint which can be accessed. Please open an application like Postman or something similar which allows you to execute HTTP requests. If you are familiar with curl then that should be fine as well.

You need to access the following endpoint [http://localhost:8080/api/cart/scan-and-checkout](http://localhost:8080/api/cart/scan-and-checkout)

The endpoint above will need to be executed as a **POST** request with the content-type set to *application/json* and the following request body

```
    [
	    {
	        "itemCode": "MUG"
	    },
	    {
	        "itemCode": "TSHIRT"
	    },
	    {
	        "itemCode": "USBKEY"
	    }
	]
```
You can modify the above request with additional items, remove some items or change the keys. Once you execute the above request. You should see a response coming back with something along the lines of

```
    {
	    "value": 35.00
	}
```
## The end
