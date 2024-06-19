package laundry.management.system.laundry_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramResponse {
	private Integer status;
	private String username;
	private String chatId;
	private Boolean isTelegramIdExist;
}
