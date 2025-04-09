package com.example.messmanagement.repository;

import com.example.messmanagement.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    // Removed findByDate and findByMealType since those fields are no longer in the Menu model
}