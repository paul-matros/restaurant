# RECRUITMENT TEST (JAVA DEVELOPER) FOR X-FORMATION

## Task 1

### How to run this application

1. Run with ide or go to root directory, build jar(./mvnw package) and run jar
2. type "order" to start ordering lunch

### Design choices and comments

1. As job requires Spring knowledge I've chosen to use Spring shell
2. For database I've chosen in memory database populated by data.sql to fulfill "out of the box" requirement
3. Also for "out of the box" experience I didn't divide app to client and server app, although I implemented is so it
   can be easly divided into two parts
4. Placing order now only saves total price and "stringified" order to database, depending on other requirements in real
   world application it would look "slightly" different
5. all things you can order are MenuItems for expandability(fe more fields like kcal per portion, weight)
6. adding field "isAddition" to MenuItem could allow for more universal and straightforward
   InteractiveWaiter.selectAdditions(String menuItemName, MenuItemType additionType)(you could just pass in menuItem
   and "autosearch" for entries with same type and isAddition=true)
7. I could have write more robust tests but its late;) and i believe I've shown myself from best side - taking under
   consideration its my first spring shell app

## Task 2(SQL)

### Query 1: Select countries where the total number of inhabitants (population) in all cities is greater than 400.

```
SELECT c.Name
FROM Country c
JOIN City ci ON c.CountryID = ci.CountryID
GROUP BY c.CountryID, c.Name
HAVING SUM(ci.Population) > 400;
```

### Query 2: Select names of the countries that have no buildings at all.

```
SELECT c.Name
FROM Country c
LEFT JOIN City ci ON c.CountryID = ci.CountryID
LEFT JOIN Building b ON ci.CityID = b.CityID
WHERE b.BuildingID IS NULL
```