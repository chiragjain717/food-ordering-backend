package com.foodorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodOrderingApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodOrderingApplication.class, args);
    }
}

//POST http://localhost:8080/api/customers
//{
//  "customerId": 4,
//  "items": [
//    { "foodItemId": 2, "quantity": 1 },
//    { "foodItemId": 7, "quantity": 2 }
//  ],
//  "deliveryAddress": "Scheme 54, Indore"
//}

//{
//  "name": "Sonu Singh",
//  "email": "sonu@example.com",
//  "phone": "9999999999",
//  "address": "Scheme 54, Indore"
//}
