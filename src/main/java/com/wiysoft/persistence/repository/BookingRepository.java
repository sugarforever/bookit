package com.wiysoft.persistence.repository;

import com.wiysoft.persistence.model.Booking;
import com.wiysoft.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * Created by weiliyang on 7/24/15.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    public List findAllByHolderAndBookedFor(User holder, Date bookedFor);

    @Query("select b from Booking b where b.holder = ?1 and b.bookedFor >= ?2 and b.bookedFor < ?3")
    public List findAllByHolderAndBookedForBetween(User holder, Date bookedForBegin, Date bookedForEnd);

    @Query("select b from Booking b where b.bookable.owner = ?1 and b.bookedFor >= ?2 and b.bookedFor < ?3")
    public List findAllByOwnerAndBookedForBetween(User owner, Date bookedForBegin, Date bookedForEnd);

    @Query("select b from Booking b where b.bookable.owner.id = ?1 and b.bookedFor >= ?2 and b.bookedFor < ?3")
    public List findAllByOwnerIdAndBookedForBetween(long ownerId, Date bookedForBegin, Date bookedForEnd);

    @Query("select b.bookedFor from Booking b where b.holder = ?1 group by b.bookedFor")
    public Page findBookedForByHolder(User holder, Pageable pageable);

    @Query("select count(b.id) from Booking b where b.bookable.id = ?2 and b.bookedFor = ?1")
    public Long findCountByBookedForAndBookable(Date bookedFor, long bookableId);

    @Query("select b from Booking b where b.bookable.owner = ?1 and b.bookedFor >= ?2 and b.bookedFor < ?3")
    public List<Booking> findBookingsByOwnerAndBookedFor(User owner, Date bookedForStart, Date bookedForEnd);

    @Modifying
    @Query("delete from Booking b where b.id=?1 and b.holder=?2")
    public Integer deleteByIdAndHolder(long id, User holder);
}
