package itv.checkout.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CheckoutSession {
	private HashMap<SKU, Integer> items = null;
	private int totalCost = 0;
	private boolean active;
		
	public CheckoutSession() {}
	
	public CheckoutSession(CheckoutSession checkoutSession) {
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
			setActive(true);
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

	public int getTotalCost() {
		return this.totalCost;
	}

	private void setActive(boolean active) {
		this.active = active;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckoutSession [items=");
		builder.append(items);
		builder.append(", totalCost=");
		builder.append(totalCost);
		builder.append(", active=");
		builder.append(active);
		builder.append("]");
		return builder.toString();
	}
}