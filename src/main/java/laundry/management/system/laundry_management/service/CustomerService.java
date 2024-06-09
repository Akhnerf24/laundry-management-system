package laundry.management.system.laundry_management.service;

import io.swagger.v3.oas.models.info.Contact;
import laundry.management.system.laundry_management.entity.Customers;
import laundry.management.system.laundry_management.entity.User;
import laundry.management.system.laundry_management.model.CreateCustomerRequest;
import laundry.management.system.laundry_management.model.CustomerResponse;
import laundry.management.system.laundry_management.model.UpdateCustomerRequest;
import laundry.management.system.laundry_management.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
	
	@Transactional(readOnly = true)
	public CustomerResponse get(String id) {
		Customers customers = customerRepository.findFirstById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customers not found"));
		
		return toCustomerResponse(customers);
	}
	
	@Transactional
	public CustomerResponse update(String customersId, UpdateCustomerRequest request) {
		validationService.validate(request);
		
		Customers customers = customerRepository.findFirstById(customersId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customers not found"));
		
		customers.setName(request.getName());
		customers.setPhoneNumber(request.getPhoneNumber());
		customerRepository.save(customers);
		
		return toCustomerResponse(customers);
	}
	
	@Transactional
	public void delete(String customerId) {
		Customers customers = customerRepository.findFirstById(customerId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customers not found"));
		
		customerRepository.delete(customers);
	}
	
	public Page<Customers> findPaginatedCustomers(int page, int size, String sortField, String sortDirection) {
		Sort sort = Sort.by(sortField);
		sort = "asc".equalsIgnoreCase(sortDirection) ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return customerRepository.findAll(pageable);
	}
	
	private CustomerResponse toCustomerResponse(Customers customers) {
		return CustomerResponse.builder()
			.id(customers.getId())
			.name(customers.getName())
			.phoneNumber(customers.getPhoneNumber())
			.build();
	}
}
