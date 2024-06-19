package laundry.management.system.laundry_management.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionRequest {
	@NotNull
	private Boolean paidStatus;
	private Boolean done;
	private Boolean pickedUp;
	private Boolean isThereChatId;
}
