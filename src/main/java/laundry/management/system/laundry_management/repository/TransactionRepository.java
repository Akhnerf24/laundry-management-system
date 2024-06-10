package laundry.management.system.laundry_management.repository;

import laundry.management.system.laundry_management.entity.Customers;
import laundry.management.system.laundry_management.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
	Optional<Transaction> findFirstByOrderId(String orderId);
	
	@Query("SELECT COUNT(t) FROM Transaction t WHERE t.done = false")
	long countByDoneTrue();
	
	@Query("SELECT COUNT(t) FROM Transaction t WHERE t.pickedUp = false and t.done = true")
	long countByPendingPickedUp();
	
	@Query("SELECT t FROM Transaction t WHERE t.orderId = :orderId")
	Page<Transaction> searchByOrderId(@Param("orderId") Long orderId, Pageable pageable);
	
	@Query("SELECT t FROM Transaction t WHERE t.cusName LIKE %:cusName%")
	Page<Transaction> searchByCusName(@Param("cusName") String cusName, Pageable pageable);
	
	@Query("SELECT t FROM Transaction t WHERE t.phoneNumber LIKE %:phoneNumber%")
	Page<Transaction> searchByPhoneNumber(@Param("phoneNumber") String phoneNumber, Pageable pageable);
}
