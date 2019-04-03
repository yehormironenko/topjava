Curl requests
=====
***

## Meals

Method | Description | Curl example
|---|---|---|
GET | Get user's meal by id| curl -G http://localhost:8080/topjava/rest/meals/100002 |
GET| Get all meals for user| curl -G http://localhost:8080/topjava/rest/meals/ | 
GET| Get meals between dates | curl -G http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&startTime=09:00&endDate=2015-05-31&endTime=11:00 |
POST| Create new meal | curl -X POST -d '{"dateTime":"2015-05-31T12:00","description":"Newlunch","calories":5000}' -H 'Content-Type:application/json;charset=UTF-8'  [http:/localhost:8080/topjava/rest/meals](http:/localhost:8080/topjava/rest/meals) | 
DELETE | Delete meal | curl -X DELETE http://localhost:8080/topjava/rest/meals/100002 |
|PUT | Update meal | curl -X PUT -d '{"dateTime":"2015-05-31T09:00","description":"New breakfast", "calories":300}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/100002 |


 
 ## Users
 Method | Description | Curl example
|---|---|---|
GET | Get user| curl -G http://localhost:8080/topjava/rest/admin/users/100000 |
GET | Get all users | curl -G http://localhost:8080/topjava/rest/admin/users
GET | Get by email | curl -G http://localhost:8080/topjava/rest/admin/users/by?email=user@yandex.ru|
DELETE | Delete user | curl -X DELETE http://localhost:8080/topjava/rest/admin/users/100000
POST | Create new user |  curl -X POST -d '{"name": "New777","email": "new777@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/admin/users/ |
PUT | Update user | curl -X PUT -d '{"name": "UserUpdated","email": "user@yandex.ru","password": "passwordNew","roles": ["ROLE_USER"]}' -H 'Content-Type:application/json' http://localhost:8080/topjava/rest/admin/users/100000