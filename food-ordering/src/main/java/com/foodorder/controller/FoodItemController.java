package com.foodorder.controller;

import com.foodorder.model.FoodItem;
import com.foodorder.service.FoodItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/food-items")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    // GET all food items
    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    // GET available items only
    @GetMapping("/available")
    public ResponseEntity<List<FoodItem>> getAvailableItems() {
        return ResponseEntity.ok(foodItemService.getAvailableItems());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long id) {
        return ResponseEntity.ok(foodItemService.getFoodItemById(id));
    }

    // GET by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<FoodItem>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(foodItemService.getByCategory(category));
    }

    // GET search by name
    @GetMapping("/search")
    public ResponseEntity<List<FoodItem>> search(@RequestParam String name) {
        return ResponseEntity.ok(foodItemService.searchByName(name));
    }

    // POST add new food item
    @PostMapping
    public ResponseEntity<FoodItem> addFoodItem(@Valid @RequestBody FoodItem foodItem) {
        return new ResponseEntity<>(foodItemService.addFoodItem(foodItem), HttpStatus.CREATED);
    }

    // PUT update food item
    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable Long id,
                                                    @Valid @RequestBody FoodItem foodItem) {
        return ResponseEntity.ok(foodItemService.updateFoodItem(id, foodItem));
    }

    // PATCH toggle availability
    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<FoodItem> toggleAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(foodItemService.toggleAvailability(id));
    }

    // DELETE food item
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.ok("Food item deleted successfully");
    }
}
