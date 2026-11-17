package com.twilightchime.blog.dao;

import com.twilightchime.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select u from User u where (:username is null or :username = '' or u.username like %:username%)")
    Page<User> findPageByNameLike(String username, Pageable pageable);
}
