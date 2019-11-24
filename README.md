# rest-server-no-framework

## User operations

```
curl -X POST localhost:8001/v1/users-service -d '{"name": "test" , "surname" : "test" , "address" : "aaaa" , "city" : "fffff"}'
curl -X GET localhost:8001/v1/users-service/test_test
curl -X GET localhost:8001/v1/users-service/
curl -X GET localhost:8001/v1/users-service
curl -X DELETE localhost:8001/v1/users-service/test_test
curl -X PUT localhost:8001/v1/users-service -d '{ "id": "test_test", "name": "test" , "surname" : "test" , "address" : "aaaa" , "city" : "fffff"}'
```

## Accounts operations

```
curl -X POST localhost:8001/v1/accounts-service -d '{"userId": "test_test", "balance": "0"}'
curl -X GET localhost:8001/v1/accounts-service
curl -X GET localhost:8001/v1/accounts-service/sfsfsfsfsfsfs
curl -X DELETE localhost:8001/v1/accounts-service/sfsfsfsfsfsfs
curl -x UPDATE localhost:8001/v1/accounts-service -d '{"id": "idAccount", "balance": "2000"}'
```

## UserPayment operations

```
curl -X POST localhost:8001/v1/users-payments-service -d '{"srcAccountId": "srcId", "quantity": "300", "trgAccountId": "trgId"}"'
```