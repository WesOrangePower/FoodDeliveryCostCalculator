# FoodDeliveryCostCalculator

*This application was made as a part of Fujitsu Estonia internship program requirements.*

### Introduction
Food delivery cost calculation is made based on three major factors: the region, the vehicle type and the weather.

The application collects weather data from Estonian weather service and maps it to regions of interest (by default those are Tallinn, Tartu and Pärnu).
From the weather data, we are mostly interested in **air temperature**, **wind speed** and **weather phenomenon**.
Based on those attributes and the **regional base fee**, which may be different for different vehicle types, the delivery fees are calculated.

### Business rules
By the business rules drawn in trial task specification provided by Fujitsu, regional base fees are set as follows:

| *RBF*   | Tallinn | Tartu | Pärnu |
|---------|---------|-------|-------|
| Car     | 4.0     | 3.5   | 3.0   |
| Scooter | 3.5     | 3.0   | 2.5   |
| Bike    | 3.0     | 2.5   | 2.5   |

Also, additional fees are applied based on the weather conditions, as follows:

| *Air temperature* | < -10C | -10C ... 0C | 0C >= |
|-------------------|--------|-------------|-------|
| Car               | 0.0    | 0.0         | 0.0   |
| Scooter           | + 1.0  | + 0.5       | 0.0   |
| Bike              | + 1.0  | + 0.5       | 0.0   |

| *Wind speed* | < 10 m/s | 10 m/s ... 20 m/s | 20 m/s >= |
|--------------|----------|-------------------|-----------|
| Car          | 0.0      | 0.0               | 0.0       |
| Scooter      | 0.0      | 0.0               | 0.0       |
| Bike         | 0.0      | + 0.5             | Forbidden |


| *Weather Phenomenon* | Others | Rain  | Snow or Sleet | Glaze, Hail or Thunder |
|----------------------|--------|-------|---------------|------------------------|
| Car                  | 0.0    | 0.0   | 0.0           | 0.0                    |
| Scooter              | 0.0    | + 0.5 | + 1.0         | Forbidden              |
| Bike                 | 0.0    | + 0.5 | + 1.0         | Forbidden              |


### Interface

By default, Spring application start on port 8080. If that is the case **Swagger-UI** is available [here on localhost](http://localhost:8080/swagger-ui.html).
Information on REST endpoints is available there.
