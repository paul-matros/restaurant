package pl.matros.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.matros.restaurant.model.PlacedOrder;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, Long> {
}
