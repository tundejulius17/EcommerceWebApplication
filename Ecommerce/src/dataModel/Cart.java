package dataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CartItem> cartItems;
	private double cartTotal;

	public Cart() {
		cartItems = new ArrayList<CartItem>();
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartTotal(double cartTotal) {
		this.cartTotal = cartTotal;
	}

	public double getCartTotal() {
		return cartTotal;
	}

	public int getCount() {
		return cartItems.size();
	}

	// This adds product to the cart.
	public synchronized void addCartItem(Product product) {
		String code = product.getCode();
		double price = product.getPrice();
		boolean newCartItem = true;

		// check if the cartItem already exists
		for (CartItem item : cartItems) {
			if (item.getProduct().getCode().equals(code)) {
				newCartItem = false;
				item.increaseQuantity();
				item.setTotal(price * (item.getQuantity()));
				calCartTotal();
			}
		}

		if (newCartItem) {
			CartItem cartItem = new CartItem(product);
			cartItem.setTotal(price * (cartItem.getQuantity()));
			cartItems.add(cartItem);
			calCartTotal();
		}
	}

	// This updates cart item quantity already in the cart.
	public synchronized void updateCartItem(Product product, int quantity) {
		String code = product.getCode();
		double price = product.getPrice();
		double total = 0.0;

		for (CartItem item : cartItems) {
			if (item.getProduct().getCode().equals(code)) {
				item.setQuantity(quantity);
				total = (price * quantity);
				item.setTotal(total);
				calCartTotal();
				return;
			}
		}
	}

	// This removes an item from the cart.
	public synchronized void removeCartItem(Product product) {
		String code = product.getCode();
		for (CartItem item : cartItems) {
			if (item.getProduct().getCode().equals(code)) {
				cartItems.remove(item);
				calCartTotal();
				return;
			}
		}
	}

	// This removes all the items in the cart.
	public synchronized void clearCart() {
		cartItems.clear();
		setCartTotal(0);
	}

	// This calculates the total price of all items in the cart.
	protected void calCartTotal() {
		double total = 0.0;
		for (CartItem item : cartItems) {
			total += item.getTotal();
		}
		setCartTotal(total);

	}

	/*
	 * public String payment(){ return "payment"; }
	 */

}
