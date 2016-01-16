package com.wiysoft.persistence.repository;

import com.wiysoft.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by weiliyang on 7/24/15.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByNameAndPassword(String name, String password);

    @Query("select COUNT(id) from User u where u.name = ?1")
    public long countByName(String name);

    @Query("select COUNT(id) from User u where u.email = ?1")
    public long countByEmail(String email);
}
