package com.roshni.livemoreapi.repository;

import com.roshni.livemoreapi.entity.User;
import com.roshni.livemoreapi.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 1. Used for Login (Ignores deleted accounts)
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    // 2. Used for Registration (Ensures active users don't have this email)
    boolean existsByEmailAndIsDeletedFalse(String email);

    // 3. Helpful for Admin Dashboards
    List<User> findByRoleAndIsDeletedFalse(Role role);

//    @Query("SELECT u FROM User u WHERE u.isDeleted = false " + "AND (LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " + "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    @Query("""
        SELECT u FROM User u 
        WHERE u.isDeleted = false 
        AND (LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) 
             OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
    """)
    List<User> searchActiveUsers(@Param("searchTerm") String searchTerm);
}
