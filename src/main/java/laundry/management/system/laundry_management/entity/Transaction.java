package laundry.management.system.laundry_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Transaction")
public class Transaction {
	@Id
	private String orderId;
	
	private String cusName;
	
	@NotBlank
	private String phoneNumber;
	
	@NotNull
	private Double weight;
	
	private Integer totalPrice;
	
	@NotBlank
	private String serviceType;
	
	private String telegramChatId;
	
	private Boolean isIdTelegramExist = false;
	
	@NotNull
	private Boolean isPaid;
	
	private Boolean done = false;
	
	private Boolean pickedUp = false;
	
	private Date paidDate;
	private Date doneDate;
	private Date pickedUpDate;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", updatable = false)
	private Date createdAt;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated")
	private Date lastUpdated;
}
