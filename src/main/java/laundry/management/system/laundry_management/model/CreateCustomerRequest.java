package laundry.management.system.laundry_management.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {
	@NotBlank
	@Size(max = 100)
	private String name;
	
	@Size(max = 100)
	private String phoneNumber;
}
