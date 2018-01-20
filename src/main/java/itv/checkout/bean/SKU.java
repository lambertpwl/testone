package itv.checkout.bean;

public class SKU {

	private String name;
	private int unitPrice;
	private int offerUnits;
	private int offerPrice;
	private boolean onOffer = false;
	
	public SKU() { }
	
	public SKU(String name) {
		this.name = name;
	}
	
	public SKU(String name, int price) {
		this.name = name;
		this.unitPrice = price;
	}

	public SKU(String name, int unitPrice, int offerUnits, int offerPrice) {
		this.name = name;
		this.unitPrice = unitPrice;
		setOffer(offerUnits, offerPrice);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public int getOfferUnits() {
		return offerUnits;
	}
	
	public void setOffer(int offerUnits, int offerPrice) {
		this.offerUnits = offerUnits;
		this.offerPrice = offerPrice;
		if (offerPrice > 0 && offerUnits > 0) {
			this.onOffer = true;
		} else {
			this.onOffer = false;
		}
	}
	
	public int getOfferPrice() {
		return offerPrice;
	}
	
	public void update(int unitPrice, int offerPrice, int offerUnits) {
		setOffer(offerUnits, offerPrice);
		setUnitPrice(unitPrice);
	}
	public boolean isOnOffer() {
		return onOffer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SKU other = (SKU) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SKU [name=");
		builder.append(name);
		builder.append(", unitPrice=");
		builder.append(unitPrice);
		builder.append(", offerUnits=");
		builder.append(offerUnits);
		builder.append(", offerPrice=");
		builder.append(offerPrice);
		builder.append(", onOffer=");
		builder.append(onOffer);
		builder.append("]");
		return builder.toString();
	}
	
}