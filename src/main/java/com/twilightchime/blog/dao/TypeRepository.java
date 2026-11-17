package com.twilightchime.blog.dao;

import com.twilightchime.blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);

    @Query("Select t From Type t Where (:name is null or :name = '' or t.name Like %:name%)")
    Page<Type> findPageByNameLike(String name, Pageable pageable);

    boolean existsByName(String name);
}
