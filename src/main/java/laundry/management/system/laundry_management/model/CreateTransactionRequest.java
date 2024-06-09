package laundry.management.system.laundry_management.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionRequest {
	@NotBlank
	@Size(max = 100)
	private String phoneNumber;
	
	@NotNull
	private Double weight;
	
	@NotBlank
	@Size(max = 100)
	private String serviceType;
	
	@NotNull
	private Boolean paidStatus;
}
