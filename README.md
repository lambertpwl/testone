# ITVTechTest
Tech Test submission for ITV

QUERY PRICES (GET) - returns the current list of all the SKU items and their prices.<br/>
http://localhost:8080/pricing 

UPDATE PRICING (POST) - this API will update unit price, offer details, unit and offer details for the provided item name. If the item does not exist in the catalogue then a new item will be added. If there is an active checkout session then the pricing will not be updated.

The parameter list:
- name (mandatory) this will be used to look up the SKU item in the catalogue and therefore if not provided will result in a 400 error. If this parameter is provided and empty it will result in a response of "NO UPDATE"<br/>
- unitPrice (optional) - this will update the unit price of the SKU item<br/>
- offerUnits (optional)
- offerPrice (optional) - an update will result only if both the offer parameters are present<br/>

Example successful calls:

http://localhost:8080/updateSKU?name=A&unitPrice=60&offerUnits=2&offerPrice=100<br/>
http://localhost:8080/updateSKU?name=A&unitPrice=60<br/>
http://localhost:8080/updateSKU?name=A&&offerUnits=2&offerPrice=100<br/>
http://localhost:8080/updateSKU?name=E&unitPrice=60&offerUnits=2&offerPrice=100<br/>

Calls that will result in NO UPDATE response:<br/>

http://localhost:8080/updateSKU?name=&unitPrice=60&offerUnits=2&offerPrice=100<br/>
http://localhost:8080/updateSKU?name=A<br/>
http://localhost:8080/updateSKU?name=A&offerUnits=2<br/>
http://localhost:8080/updateSKU?name=A&offerPrice=100<br/>

Error scenario when the name parameter is not present<br/>
http://localhost:8080/updateSKU?unitPrice=60&offerUnits=2&offerPrice=100<br/>

SCAN ITEM (POST) - An item can be scanned by passing only the name parameter to the API. If the item scans successfully then the item details will be returned to the calling service. If the value of the name parameter is empty or does not exist in the catalogue no item will be added to the checkout session. If the name parameter is not passed it will result in a 400 error.<br/>

http://localhost:8080/scanItem?name=A

STATUS (GET) - This returns the checkout session in its current state<br/>
http://localhost:8080/status

CHECKOUT (GET) - This returns the checkout session on completion and resets the checkout for the next customer checkout session<br/>
http://localhost:8080/checkout

# Execute from Command Line

java -jar target/CheckoutKata-0.1.0.jar
