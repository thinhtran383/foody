package com.example.foodordering.repositories;

import com.example.foodordering.entities.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    @EntityGraph(value = "menuItemWithCategory")
    Page<MenuItem> findAll(@NonNull Pageable pageable);


    Page<MenuItem> findMenuItemByCategory_CategoryName(@NonNull Pageable pageable, String category);

    MenuItem findByImage(String image);

    Optional<MenuItem> findByItemNameAndCategory_CategoryName(String item, String category);

    @EntityGraph(value = "menuItemWithOrderDetailsAndCategory")
    Optional<MenuItem> findMenuItemById(int id);
}
