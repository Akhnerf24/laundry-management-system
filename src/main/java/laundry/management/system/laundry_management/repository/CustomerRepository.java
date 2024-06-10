package laundry.management.system.laundry_management.repository;

import laundry.management.system.laundry_management.entity.Customers;
import laundry.management.system.laundry_management.entity.Transaction;
import laundry.management.system.laundry_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customers, String> {
	Optional<Customers> findFirstById(String id);
	Optional<Customers> findFirstByPhoneNumber(String phoneNumber);
	
	@Query("SELECT t FROM Customers t WHERE t.name LIKE %:name%")
	Page<Customers> searchByName(@Param("name") String name, Pageable pageable);
	
	@Query("SELECT t FROM Customers t WHERE t.phoneNumber LIKE %:phoneNumber%")
	Page<Customers> searchByPhoneNumber(@Param("phoneNumber") String phoneNumber, Pageable pageable);
}
