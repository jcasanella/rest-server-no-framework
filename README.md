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
curl -X GET localhost:8001/v1/accounts-service'
curl -X GET localhost:8001/v1/accounts-service/sffsfsssfs33
curl -X DELETE localhost:8001/v1/accounts-service/sffsfsssfs33
```