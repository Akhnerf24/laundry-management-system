package laundry.management.system.laundry_management.service;

import laundry.management.system.laundry_management.constant.Price;
import laundry.management.system.laundry_management.entity.Customers;
import laundry.management.system.laundry_management.entity.Transaction;
import laundry.management.system.laundry_management.entity.User;
import laundry.management.system.laundry_management.model.*;
import laundry.management.system.laundry_management.repository.CustomerRepository;
import laundry.management.system.laundry_management.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Transactional
	public TransactionResponse create(CreateTransactionRequest request) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		// Convert LocalDateTime to Date
		Date date = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		validationService.validate(request);
		
		Customers customers = customerRepository.findFirstByPhoneNumber(request.getPhoneNumber())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customers Phone Number not found, please register first!"));
		
		Transaction transaction = new Transaction();
		transaction.setOrderId(String.valueOf(Instant.now().getEpochSecond()));
		transaction.setCusName(customers.getName());
		transaction.setPhoneNumber(request.getPhoneNumber());
		transaction.setWeight(request.getWeight());
		transaction.setServiceType(request.getServiceType());
		transaction.setIsPaid(request.getPaidStatus());
		transaction.setTotalPrice((int) (Price.PRICE_KG * request.getWeight()));
		
		if(request.getPaidStatus()){
			transaction.setPaidDate(date);
		}
		
		
		transactionRepository.save(transaction);
		
		return toTransactionResponse(transaction);
	}
	
	@Transactional(readOnly = true)
	public TransactionResponse get(String orderId) {
		Transaction transaction = transactionRepository.findFirstByOrderId(orderId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
		
		return toTransactionResponse(transaction);
	}
	
	public CountResponse getOnProgress() {
		Long counting = transactionRepository.countByDoneTrue();
		return toCountResponse(counting);
	}
	
	public CountResponse getPickUp() {
		Long counting = transactionRepository.countByPendingPickedUp();
		return toCountResponse(counting);
	}
	
	@Transactional
	public TransactionResponse update(String orderId, UpdateTransactionRequest request) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		// Convert LocalDateTime to Date
		Date date = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		validationService.validate(request);
		
		Transaction transaction = transactionRepository.findFirstByOrderId(orderId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
		
		
		System.out.println("LOG-1" + request.getPaidStatus());
		System.out.println("LOG-2" + transaction.getIsPaid());
		System.out.println("LOG-3" + !transaction.getIsPaid());
		
		// PAID STATUS HANDLING
		if(request.getPaidStatus()){
			if(!transaction.getIsPaid()){
				transaction.setIsPaid(true);
				transaction.setPaidDate(date);
			}
		}else {
			transaction.setIsPaid(false);
			transaction.setPaidDate(null);
		}
		
		// DONE STATUS HANDLING
		if(request.getDone()){
			if(!transaction.getDone()){
				transaction.setDone(true);
				transaction.setDoneDate(date);
			}
		} else {
			transaction.setDone(false);
			transaction.setDoneDate(null);
		}
		
		// PICKED UP STATUS HANDLING
		if(request.getPickedUp()){
			if(transaction.getIsPaid() && transaction.getDone()){
				transaction.setPickedUp(true);
				transaction.setPickedUpDate(date);
			}else {
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Please Paid And Done The Transaction First");
			}
		} else {
			transaction.setPickedUp(false);
			transaction.setPickedUpDate(null);
		}
		
		
		transactionRepository.save(transaction);
		
		return toTransactionResponse(transaction);
	}
	
	public Page<Transaction> findPaginatedTransaction(
		Integer page, Integer size, String sortField, String sortDirection,
		Long orderId, String cusName, String phoneNumber
	) {
		Sort sort = Sort.by(sortField);
		sort = "asc".equalsIgnoreCase(sortDirection) ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		
		if (orderId != null) {
			return transactionRepository.searchByOrderId(orderId, pageable);
		} else if (cusName != null && !cusName.isEmpty()) {
			return transactionRepository.searchByCusName(cusName, pageable);
		} else if (phoneNumber != null && !phoneNumber.isEmpty()) {
			return transactionRepository.searchByPhoneNumber(phoneNumber, pageable);
		} else {
			return transactionRepository.findAll(pageable);
		}
	}
	
	private TransactionResponse toTransactionResponse(Transaction transaction) {
		return TransactionResponse.builder()
			.order_id(transaction.getOrderId())
			.name(transaction.getCusName())
			.phoneNumber(transaction.getPhoneNumber())
			.serviceType(transaction.getServiceType())
			.weight(transaction.getWeight())
			.totalPrice(transaction.getTotalPrice())
			.isPaid(transaction.getIsPaid())
			.done(transaction.getDone())
			.pickedUp(transaction.getPickedUp())
			.paidDate(transaction.getPaidDate())
			.doneDate(transaction.getDoneDate())
			.pickedUpDate(transaction.getPickedUpDate())
			.build();
	}
	
	private CountResponse toCountResponse(Long counting) {
		return CountResponse.builder()
			.total(counting)
			.build();
	}
}
