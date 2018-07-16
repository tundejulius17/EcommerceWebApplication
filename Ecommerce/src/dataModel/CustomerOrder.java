package dataModel;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Long;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "customer_order")
public class CustomerOrder implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "order_amount")
	private Double totalPrice;

	@Column(name = "order_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@Column(name = "order_status")
	private String status;

	@JoinColumn(name = "customer_id")
	@ManyToOne
	private Customer customer;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CartItem> lineItems;

	private static final long serialVersionUID = 1L;

	public CustomerOrder() {
		this.status = "";
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		return sdf.format(this.orderDate);
	}

	public void setOrderDate(Date orderDate) {

		this.orderDate = orderDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CartItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<CartItem> lineItems) {
		this.lineItems = lineItems;
	}

}
