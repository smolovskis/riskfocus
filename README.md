# Currency conversion service

1. Technologies used: Java 11, SpringBoot, H2, QueryDsl, Liquibase, Hibernate, Swagger
2. Project uses generated sources, do `mvn clean install` before launching the service
3. Since this is a POC, H2 in memory db is used.

To concert currencies:
1. Add supported currencies via `POST api/v1/currency/{code}`
1. Add conversion rate currencies via `POST api/v1/currency-conversion/add`
1. Convert currencies with `POST api/v1/currency-conversion/convert`

Swagger url: `http://localhost:8080/swagger-ui/`

TODO:
1. Add @ExceptionHandler which adds more descriptive error messages
2. Since currencies are classifiers which will not change often, add 2nd level cache with TTL 5 min
3. Add api descriptions with `@ApiParam` etc.
