package itv.checkout.bean;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSession {
	private int checkoutSessionId;
	private boolean active;
	private HashMap<SKU, Integer> items = null;
	private int totalCost = 0;

	public CheckoutSession(int checkoutSessionId) {
		this.checkoutSessionId = checkoutSessionId;
		setActive(true);
	}
	
	public CheckoutSession(CheckoutSession checkoutSession) {
		this.checkoutSessionId = checkoutSession.getCheckoutSessionId();
		setItems((HashMap<SKU, Integer>)checkoutSession.getItems());
		setTotalCost(checkoutSession.getTotalCost());
		setActive(checkoutSession.isActive());
	}

	public Map<SKU, Integer> getItems() {
		return items;
	}
	
	public void addItem(SKU item) {
		Integer quantity = null;
		if (items == null) {
			items = new HashMap<>();
			quantity = 0;
		} else {
			quantity = (items.get(item) != null ? items.get(item) : 0);
		}
		items.put(item, Integer.sum(quantity, 1));
		setTotalCost();
	}
	
	private void setTotalCost( ) {
		if (getItems() != null) {
			int cost = 0;
			for (Map.Entry<SKU, Integer> item : items.entrySet()) {
				if (item.getKey().isOnOffer()) {
					cost += item.getKey().getUnitPrice() * (item.getValue().intValue() % item.getKey().getOfferUnits());
					cost += item.getKey().getOfferPrice() * (item.getValue().intValue() / item.getKey().getOfferUnits());
				} else {
					cost += item.getKey().getUnitPrice() * item.getValue().intValue();
				}
			}
			setTotalCost(cost);
		}
	}
	
	private void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	
	private void setActive(boolean active) {
		this.active = active;
	}
	
	public int getCheckoutSessionId() {
		return checkoutSessionId;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	public void reset() {
		setItems(null);
		setTotalCost(0);
		setActive(false);
	}	
	
	private void setItems(HashMap<SKU, Integer> items) {
		this.items = items;
	}
	
	public void checkout() {
		setActive(false);
	}

	public int getTotalCost() {
		return this.totalCost;
	}
	
	public String getReceipt() {
		StringBuilder receipt = new StringBuilder();
		if (getItems() != null) {
			
			for (Map.Entry<SKU, Integer> item : items.entrySet()) {
				for (int i=0; i < item.getValue(); i++) {
					receipt.append((item.getKey().getName() + " - " + String.format("%.2f", (double)item.getKey().getUnitPrice() / 100)));
					receipt.append(",");
				}
				if (item.getKey().isOnOffer()) {
					for (int i=0; i < (item.getValue().intValue() / item.getKey().getOfferUnits()); i++) {
						receipt.append("Offer " + item.getKey().getName() + " - " + item.getKey().getOfferUnits() + " @ " + String.format("%.2f", (double)item.getKey().getOfferPrice() / 100));
						receipt.append(",");
					}
				} 
			}
			receipt.append("Total - " + String.format("%.2f", (double)this.getTotalCost() / 100));
		}		
		return receipt.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckoutSession [items=");
		builder.append(items);
		builder.append(", totalCost=");
		builder.append(totalCost);
		builder.append(", active=");
		builder.append(active);
		builder.append(", checkoutSessionId=");
		builder.append(checkoutSessionId);
		builder.append("]");
		return builder.toString();
	}
	
}