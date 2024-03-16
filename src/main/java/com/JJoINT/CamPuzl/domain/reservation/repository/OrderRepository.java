package com.JJoINT.CamPuzl.domain.reservation.repository;

import com.JJoINT.CamPuzl.domain.reservation.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {
}
