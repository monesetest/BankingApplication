# BankingApplication

BankingApplication is a Spring boot REST API project for demonstrating transactions and account statments.

## Running
To start the application:

```bash
mvn spring-boot:run
```

## Testing

To run all tests:

```bash
mvn test
```

## Usage

A Swagger UI is available at:
``` 
http://localhost:8080/swagger-ui.html
```

A Swagger specification is available at: 
```
http://localhost:8080/v2/api-docs
```
## Endpoints

### Make a transaction

```
POST /transactions
```
#### Response
```201 Created```
```UUID of the transaction```

### Get an account statement 

```
GET /accounts/{id}/statement
```
#### Response
```200 OK```
```
{
  "accountCurrency": "string",
  "balance": 0,
  "id": 0,
  "transactions": [
    {
      "amount": 0,
      "comment": "string",
      "createdDate": "2019-10-29T17:20:16.532Z",
      "resultingBalance": 0,
      "sourceId": 0,
      "targetAccountId": 0,
      "transactionId": "string",
      "transactionType": "CREDIT"
    }
  ]
}
```

## License
[MIT](https://choosealicense.com/licenses/mit/)