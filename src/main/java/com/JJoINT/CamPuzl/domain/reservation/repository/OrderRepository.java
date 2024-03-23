package com.JJoINT.CamPuzl.domain.reservation.repository;

;
import com.JJoINT.CamPuzl.domain.pub.domain.Menu;
import com.JJoINT.CamPuzl.domain.reservation.domain.DishOrder;
import com.JJoINT.CamPuzl.domain.reservation.domain.PubTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<DishOrder,Long> {
    Optional<DishOrder> findByMenuAndPubTable(Menu menu, PubTable pupTable);
    List<DishOrder> findByPubTable(PubTable pupTable);
}
