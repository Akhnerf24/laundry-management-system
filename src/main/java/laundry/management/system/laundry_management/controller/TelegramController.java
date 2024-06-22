package laundry.management.system.laundry_management.controller;

import laundry.management.system.laundry_management.model.TelegramResponse;
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
	public TelegramResponse sendTelegramMessage(@RequestParam String orderId) {
		System.out.println("DEBUG contrller");
		return telegramService.sendMessage(orderId);
	}
	
	@GetMapping("/getChatId")
	public TelegramResponse getChatId(@RequestParam String orderId) {
		return telegramService.getChatIdByUsername(orderId);
	}
}
