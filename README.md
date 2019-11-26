# rest-server-no-framework

Simple rest server without using any framework

## Getting started

This rest server has been implemented using Java8 (streams and lambda expressions) and vavr library to
add some functional programming. The main purpose of this project is to test the new java8 features
and the vavr library.

It simulates a way to make payments. It's mandatory to create an user and later on to create
an account assigned to this user.

See below a list of the valid operations

### User operations

* Add a user

```
curl -X POST   localhost:8001/v1/users-service -d '{"name": "test" , "surname" : "test" , "address" : "aaaa" , "city" : "fffff"}'
```

* Look for a specific user

```
curl -X GET    localhost:8001/v1/users-service/{userId}
```

* Get all the existing users

```
curl -X GET    localhost:8001/v1/users-service
```

* Drop an user

```
curl -X DELETE localhost:8001/v1/users-service/{userId}
```

* Change name or address of the user
```
curl -X PUT    localhost:8001/v1/users-service -d '{ "id": "test_test", "name": "test" , "surname" : "test" , "address" : "aaaa" , "city" : "fffff"}'
```

## Accounts operations

* Add an account
```
curl -X POST   localhost:8001/v1/accounts-service -d '{"userId": "test_test", "balance": "0"}'
```

* Look for a specific account
```
curl -X GET    localhost:8001/v1/accounts-service/{accountId}
```

* Get all existing accounts
```
curl -X GET    localhost:8001/v1/accounts-service
```

* Drop an existing account
```
curl -X DELETE localhost:8001/v1/accounts-service/{accountId}
```

* Add money to your account
```
curl -x UPDATE localhost:8001/v1/accounts-service -d '{"id": "idAccount", "balance": "2000"}'
```

## UserPayment operations

* Send money to another user
```
curl -X POST localhost:8001/v1/users-payments-service -d '{"srcAccountId": "srcId", "quantity": "300", "trgAccountId": "trgId"}"'
```

* Get user payment by userpaymentid
```
curl -X GET  localhost:8001/v1/users-payments-service/{userPaymentId}
```

* Get all existing user payments
```
curl -X GET  localhost:8001/v1/users-payments-service
```

* Get all the payments by accountSrc or accountTrg
```
curl -X GET  localhost:8001/v1/users-payments-service/src/{accountId}
curl -X GET  localhost:8001/v1/users-payments-service/trg/{accountId}
```

* Get all the payments by usrSrc or usrTrg
```
curl -X GET  localhost:8001/v1/users-payments-service/user_src/{accountId}
curl -X GET  localhost:8001/v1/users-payments-service/user_trg/{accountId}
```