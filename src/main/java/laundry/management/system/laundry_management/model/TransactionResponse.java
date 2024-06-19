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
public class TransactionResponse {
	private String order_id;
	private String name;
	private String phoneNumber;
	private Double weight;
	private Integer totalPrice;
	private String serviceType;
	private Boolean isPaid;
	private Boolean done;
	private Boolean pickedUp;
	private Date paidDate;
	private Date doneDate;
	private Date pickedUpDate;
}
