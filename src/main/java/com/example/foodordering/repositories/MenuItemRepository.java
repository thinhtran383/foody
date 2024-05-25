package com.example.foodordering.repositories;

import com.example.foodordering.entities.MenuItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer>{
}
