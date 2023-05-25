# automatic-irrigation-system

## Description
### The app handles adding, listing, editing and configuring plots of lands
### A plot is configured by setting a certain time slot (Cron) and water amount for irrigation
### A configuration thread fetched the plot configurations then schedules an irrigation thread for each plot
### The irrigation thread calls a mock API and returns success then updates the plot's status to isIrrigated
### There's a postman collection for the requests in the resources file

## Language and Frameworks

### SpringBoot Framework
### JPA
### Spring Cloud Circuit Breaker
### Lombok


## Future Enhancements

### Thread handling and creation
### Thread monitoring
### Reseting isIrrigated after a configured amount of time
### Unit tests
### Add design diagrams for documentation 
