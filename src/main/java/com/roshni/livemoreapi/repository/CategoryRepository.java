package com.roshni.livemoreapi.repository;

import com.roshni.livemoreapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);

    Boolean existsBySlug(String slug);

    Optional<Category> findByName(String name);

    Boolean existsByName(String name);

    @Query("Select c From Category c where lower(c.name) LIKE lower(concat('%', :searchTerm, '%') )")
    List<Category> searchByName(@Param("searchTerm") String searchTerm);

    @Query("select c from Category c where lower(c.name) LIKE lower(concat(:prefix, '%')) ORDER BY c.name")
    List<Category> findByNameStartingWith(@Param("prefix") String prefix);

}
