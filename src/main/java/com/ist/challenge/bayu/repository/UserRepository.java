package com.ist.challenge.bayu.repository;

import com.ist.challenge.bayu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
    List<User> searchByUsernameLike(@Param("username") String username);

//    @Query(value = "SELECT * FROM tutorials t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
//    Page<Tutorial> findByTitleLike(String title, Pageable pageable);
//
//    @Query(value = "SELECT * FROM tutorials t WHERE t.published=?1", nativeQuery = true)
//    Page<Tutorial> findByPublished(boolean isPublished, Pageable pageable);
}
