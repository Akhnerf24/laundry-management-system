package laundry.management.system.laundry_management.controller;

import laundry.management.system.laundry_management.entity.Customers;
import laundry.management.system.laundry_management.entity.Transaction;
import laundry.management.system.laundry_management.entity.User;
import laundry.management.system.laundry_management.model.*;
import laundry.management.system.laundry_management.service.CustomerService;
import laundry.management.system.laundry_management.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping(
		path = "/api/create-order",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<TransactionResponse> create(@RequestBody CreateTransactionRequest request) {
		TransactionResponse transactionResponse = transactionService.create(request);
		return WebResponse.<TransactionResponse>builder().data(transactionResponse).build();
	}
	
	@GetMapping(
		path = "/api/get-order-detail/{orderId}",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<TransactionResponse> get(@PathVariable("orderId") String orderId) {
		TransactionResponse transactionResponse = transactionService.get(orderId);
		return WebResponse.<TransactionResponse>builder().data(transactionResponse).build();
	}
	
	@GetMapping(
		path = "/api/get-onProgress-order",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<CountResponse> getCountOnProgress() {
		CountResponse countResponse = transactionService.getOnProgress();
		return WebResponse.<CountResponse>builder().data(countResponse).build();
	}
	
	@GetMapping(
		path = "/api/get-pickedUp-order",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<CountResponse> getCountPickUp() {
		CountResponse countResponse = transactionService.getPickUp();
		return WebResponse.<CountResponse>builder().data(countResponse).build();
	}
	
	@PutMapping(
		path = "/api/update-order/{orderId}",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public WebResponse<TransactionResponse> update(
		@RequestBody UpdateTransactionRequest request,
		@PathVariable("orderId") String orderId) {
		
		request.setIsThereChatId(request.getIsThereChatId());
		request.setPaidStatus(request.getPaidStatus());
		request.setDone(request.getDone());
		request.setPickedUp(request.getPickedUp());
		
		TransactionResponse transactionResponse = transactionService.update(orderId, request);
		return WebResponse.<TransactionResponse>builder().data(transactionResponse).build();
	}
	
	@GetMapping("/api/get-order/paginate")
	public Page<Transaction> getCustomers(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "orderId") String sortField,
		@RequestParam(defaultValue = "asc") String sortDirection,
		@RequestParam(required = false) Long orderId,
		@RequestParam(required = false) String cusName,
		@RequestParam(required = false) String phoneNumber){
		return transactionService.findPaginatedTransaction(page, size, sortField, sortDirection, orderId, cusName, phoneNumber);
	}
}
