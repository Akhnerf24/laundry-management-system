package laundry.management.system.laundry_management.controller;

import laundry.management.system.laundry_management.entity.Customers;
import laundry.management.system.laundry_management.entity.User;
import laundry.management.system.laundry_management.model.CreateCustomerRequest;
import laundry.management.system.laundry_management.model.CustomerResponse;
import laundry.management.system.laundry_management.model.UpdateCustomerRequest;
import laundry.management.system.laundry_management.model.WebResponse;
import laundry.management.system.laundry_management.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@PostMapping(
		path = "/api/customers",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<CustomerResponse> create(User user, @RequestBody CreateCustomerRequest request) {
		CustomerResponse contactResponse = customerService.create(user, request);
		return WebResponse.<CustomerResponse>builder().data(contactResponse).build();
	}
	
	@GetMapping(
		path = "/api/customers/{customersId}",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<CustomerResponse> get(User user, @PathVariable("customersId") String customersId) {
		CustomerResponse customerResponse = customerService.get(user, customersId);
		return WebResponse.<CustomerResponse>builder().data(customerResponse).build();
	}
	
	@PutMapping(
		path = "/api/customers/{customersId}",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<CustomerResponse> update(
	User user,
	 @RequestBody UpdateCustomerRequest request,
	 @PathVariable("customersId") String customersId) {
		
		request.setName(request.getName());
		request.setPhoneNumber(request.getPhoneNumber());
		
		CustomerResponse customerResponse = customerService.update(user, customersId, request);
		return WebResponse.<CustomerResponse>builder().data(customerResponse).build();
	}
	
	@DeleteMapping(
		path = "/api/customers/{customerId}",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<String> delete(User user, @PathVariable("customerId") String customerId) {
		customerService.delete(user, customerId);
		return WebResponse.<String>builder().data("Berhasil Menghapus Data").build();
	}
	
	@GetMapping("/api/customers/paginate")
	public Page<Customers> getCustomers(
		User user,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortField,
		@RequestParam(defaultValue = "asc") String sortDirection,
		@RequestParam(required = false) String name,
		@RequestParam(required = false) String phoneNumber){
		return customerService.findPaginatedCustomers(user, page, size, sortField, sortDirection, name, phoneNumber);
	}
}
