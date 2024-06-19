package laundry.management.system.laundry_management.service;

import com.fasterxml.jackson.databind.JsonNode;
import laundry.management.system.laundry_management.entity.Transaction;
import laundry.management.system.laundry_management.model.TelegramResponse;
import laundry.management.system.laundry_management.model.TransactionResponse;
import laundry.management.system.laundry_management.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramService {
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Value("${telegram.bot.token}")
	private String botToken;
	
	private final RestTemplate restTemplate;
	
	public TelegramService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public JsonNode getUpdates() {
		String url = "https://api.telegram.org/bot" + botToken + "/getUpdates";
		return restTemplate.getForObject(url, JsonNode.class);
	}
	
	public TelegramResponse getChatIdByUsername(String orderId) {
		Transaction transaction = transactionRepository.findFirstByOrderId(orderId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
		
		JsonNode updates = getUpdates();
		try{
			if (updates != null && updates.has("result")) {
				for (JsonNode update : updates.get("result")) {
					JsonNode message = update.get("message");
					if (message != null) {
						JsonNode from = message.get("from");
						if (from != null && transaction.getCusName().equals(from.get("username").asText())) {
							Long telegramChatId = message.get("chat").get("id").asLong();
							
							// Insert Telegram Chat ID
							transaction.setIsIdTelegramExist(true);
							transaction.setTelegramChatId(String.valueOf(telegramChatId));
							transactionRepository.save(transaction);
							
							return toTelegramResponse(transaction);
						}
					}
				}
			}
		} catch (Exception e){
			transaction.setIsIdTelegramExist(false);
			transactionRepository.save(transaction);
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
		}
		
		return null; // or throw an exception if not found
	}
	
	public void sendMessage(String orderId) {
		String message = null;
		
		Transaction transaction = transactionRepository.findFirstByOrderId(orderId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
		
		String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		
		String header = "Halo Bro/Sis :)";
		
		// Example multiline message
		String multilineMessage = "Terima kasih sudah mempercayakan Cucian Pakaian Anda Pada Fresh Fold, Berikut data order anda:\n\n"
			+ "Nama: " + transaction.getCusName() + "\n"
			+ "No HP: " + transaction.getPhoneNumber() + "\n"
			+ "Finish date: " + transaction.getDoneDate() +  "\n\n"
			+ "Terima Kasih,  " + "\n\n"
			+ "FreshFold Management";
		
		// Append the additional message to the original message
		message =header + "\n\n" + multilineMessage; // Assuming 'message' is your original message
		
		Map<String, Object> body = new HashMap<>();
		body.put("chat_id", transaction.getTelegramChatId());
		body.put("text", message);
		
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		
		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Failed to send message: " + response.getBody());
		}
	}
	
	private TelegramResponse toTelegramResponse(Transaction transaction) {
		return TelegramResponse.builder()
			.status(200)
			.username(transaction.getCusName())
			.chatId(transaction.getTelegramChatId())
			.isTelegramIdExist(true)
			.build();
	}
}
