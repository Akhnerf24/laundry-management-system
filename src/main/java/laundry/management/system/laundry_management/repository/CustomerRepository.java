package laundry.management.system.laundry_management.repository;

import laundry.management.system.laundry_management.entity.Customers;
import laundry.management.system.laundry_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository  extends JpaRepository<Customers, String> {
	Optional<Customers> findFirstById(String id);
}
