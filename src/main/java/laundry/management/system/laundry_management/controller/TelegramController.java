package laundry.management.system.laundry_management.controller;

import laundry.management.system.laundry_management.model.TelegramResponse;
import laundry.management.system.laundry_management.model.TransactionResponse;
import laundry.management.system.laundry_management.service.TelegramService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramController {
	
	private final TelegramService telegramService;
	
	public TelegramController(TelegramService telegramService) {
		this.telegramService = telegramService;
	}
	
	@GetMapping("/send-telegram-message")
	public String sendTelegramMessage(@RequestParam String orderId) {
		telegramService.sendMessage(orderId);
		return "Message sent!";
	}
	
	@GetMapping("/getChatId")
	public TelegramResponse getChatId(@RequestParam String orderId) {
		return telegramService.getChatIdByUsername(orderId);
	}
}
