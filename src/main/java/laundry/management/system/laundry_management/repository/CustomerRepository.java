package laundry.management.system.laundry_management.repository;

import laundry.management.system.laundry_management.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customers, String> {
}
