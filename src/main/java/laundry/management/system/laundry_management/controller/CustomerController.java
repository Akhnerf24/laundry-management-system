package laundry.management.system.laundry_management.controller;

import laundry.management.system.laundry_management.entity.User;
import laundry.management.system.laundry_management.model.CreateCustomerRequest;
import laundry.management.system.laundry_management.model.CustomerResponse;
import laundry.management.system.laundry_management.model.WebResponse;
import laundry.management.system.laundry_management.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
	public WebResponse<CustomerResponse> get(@PathVariable("customersId") String customersId) {
		System.out.println("CUSTOMER ID" + customersId);
		CustomerResponse customerResponse = customerService.get(customersId);
		return WebResponse.<CustomerResponse>builder().data(customerResponse).build();
	}
}
