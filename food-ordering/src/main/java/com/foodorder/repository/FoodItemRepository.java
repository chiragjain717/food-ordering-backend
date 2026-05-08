package com.foodorder.repository;

import com.foodorder.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByAvailableTrue();
    List<FoodItem> findByCategoryIgnoreCase(String category);
    List<FoodItem> findByNameContainingIgnoreCase(String name);
}
