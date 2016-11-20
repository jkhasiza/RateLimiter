# RateLimiter
Environment Details:
Maven : 3.3 +
Java : 1.8
Framework: Spring boot

Project Details:
This is a spring boot project, which serves request for searching hotels by city. Configuration properties saved in application.properties file.
hotel.csv.file defines the path of hoteldb.csv file
apikey.csv.file defines the path of api keys file. File format should be key,noOfrequests,timeInSecs
Command to run server: mvn spring-boot:run 

Following endpoints are exposed from server side :
getHotelsByCity?city=bangkok&apiKey=key1&&priceSort=asc
Type: GET
Response:   {"success":"OK","message":null,"data":[{"city":"Bangkok","room":"Deluxe","hotelId":11,"price":60},{"city":"Bangkok","room":"Deluxe","hotelId":15,"price":900},{"city":"Bangkok","room":"Deluxe","hotelId":1,"price":1000},{"city":"Bangkok","room":"Superior","hotelId":6,"price":2000},{"city":"Bangkok","room":"Superior","hotelId":8,"price":2400},{"city":"Bangkok","room":"Sweet Suite","hotelId":18,"price":5300},{"city":"Bangkok","room":"Sweet Suite","hotelId":14,"price":25000}]}
This api checks the validity of api key and searches the hotels based upon city and sorts by hotel prices
