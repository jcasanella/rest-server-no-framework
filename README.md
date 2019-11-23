# rest-server-no-framework

curl -X POST localhost:8001/v1/users-service -d '{"name": "test" , "surname" : "test" , "address" : "aaaa" , "city" : "fffff"}'
curl -X GET localhost:8001/v1/users-service/test_test
curl -X GET localhost:8001/v1/users-service/
curl -X GET localhost:8001/v1/users-service
