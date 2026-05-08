package com.foodorder.service;

import com.foodorder.model.FoodItem;
import com.foodorder.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public List<FoodItem> getAvailableItems() {
        return foodItemRepository.findByAvailableTrue();
    }

    public FoodItem getFoodItemById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found with ID: " + id));
    }

    public List<FoodItem> getByCategory(String category) {
        return foodItemRepository.findByCategoryIgnoreCase(category);
    }

    public List<FoodItem> searchByName(String name) {
        return foodItemRepository.findByNameContainingIgnoreCase(name);
    }

    public FoodItem addFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public FoodItem updateFoodItem(Long id, FoodItem updatedItem) {
        FoodItem existing = getFoodItemById(id);
        existing.setName(updatedItem.getName());
        existing.setDescription(updatedItem.getDescription());
        existing.setPrice(updatedItem.getPrice());
        existing.setCategory(updatedItem.getCategory());
        existing.setAvailable(updatedItem.isAvailable());
        return foodItemRepository.save(existing);
    }

    public void deleteFoodItem(Long id) {
        getFoodItemById(id); // validates existence
        foodItemRepository.deleteById(id);
    }

    public FoodItem toggleAvailability(Long id) {
        FoodItem item = getFoodItemById(id);
        item.setAvailable(!item.isAvailable());
        return foodItemRepository.save(item);
    }
}
