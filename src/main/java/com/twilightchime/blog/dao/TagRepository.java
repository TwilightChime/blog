package com.twilightchime.blog.dao;

import com.twilightchime.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query("Select t From Tag t Where (:name is null or :name = '' or t.name Like %:name%)")
    Page<Tag> findPageByNameLike(String name, Pageable pageable);

    @Query(value = "Select t.* from t_tag t where (:name is null or :name = '' or t.name like %:name%) limit :limit", nativeQuery = true)
    List<Tag> findListByNameLike(String name, int limit);

    boolean existsByName(String name);

    @Query("select count(t) from Tag t where t.name = name")
    long countByName(String name);
}
