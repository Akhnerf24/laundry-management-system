package laundry.management.system.laundry_management.repository;

import laundry.management.system.laundry_management.entity.Customers;
import laundry.management.system.laundry_management.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
	Optional<Transaction> findFirstByOrderId(String orderId);
}
