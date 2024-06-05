package laundry.management.system.laundry_management.service;

import laundry.management.system.laundry_management.entity.Customers;
import laundry.management.system.laundry_management.entity.User;
import laundry.management.system.laundry_management.model.CreateCustomerRequest;
import laundry.management.system.laundry_management.model.CustomerResponse;
import laundry.management.system.laundry_management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Transactional
	public CustomerResponse create(User user, CreateCustomerRequest request) {
		validationService.validate(request);
		
		Customers customers = new Customers();
		customers.setId(UUID.randomUUID().toString());
		customers.setName(request.getName());
		customers.setPhoneNumber(request.getPhoneNumber());
		
		customerRepository.save(customers);
		
		return toCustomerResponse(customers);
	}
	
	private CustomerResponse toCustomerResponse(Customers customers) {
		return CustomerResponse.builder()
			.id(customers.getId())
			.name(customers.getName())
			.phoneNumber(customers.getPhoneNumber())
			.build();
	}
}
