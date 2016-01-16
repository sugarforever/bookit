package com.wiysoft.persistence.repository;

import com.wiysoft.persistence.model.Bookable;
import com.wiysoft.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by weiliyang on 7/24/15.
 */
@Repository
public interface BookableRepository extends JpaRepository<Bookable, Long> {

    public Page<Bookable> findAllByOwnerOrderByLastModifiedDesc(User owner, Pageable pageable);

    public List<Bookable> findAllByOwnerOrderByLastModifiedDesc(User owner);

    @Query("select b from Bookable b where b.owner.id=?1 order by b.id asc")
    public List<Bookable> findAllByOwnerId(long ownerId);
}
