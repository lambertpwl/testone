package itv.checkout.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import itv.checkout.bean.SKU;

@Component
public class SKUManager {

	private HashMap<String, SKU> skuMap;
	
	public SKUManager() {
		super();
		// Default entries
		skuMap = new HashMap<>();
		SKU itemA = new SKU("A", 50, 3, 130);
		skuMap.put("A", itemA);
		SKU itemB = new SKU("B", 30, 2, 45);
		skuMap.put("B", itemB);
		SKU itemC = new SKU("C", 20);
		skuMap.put("C", itemC);
		SKU itemD = new SKU("D", 15);
		skuMap.put("D", itemD);
	}

	public SKU getSKU(String name) {
		return (skuMap != null ? skuMap.get(name) : null);
	}

	public boolean updateSKU(SKU sku, boolean updateUnitPrice, boolean updateOffer) {
		boolean updated = false;
		// Ensure the map exists
		if (skuMap == null) {
			skuMap = new HashMap<>();
		}
		// Look up the entry in the map
		if ((updateUnitPrice || updateOffer) && skuMap.get(sku.getName()) != null) {
			SKU mapSKU = skuMap.get(sku.getName());
			if (updateUnitPrice) mapSKU.setUnitPrice(sku.getUnitPrice());
			if (updateOffer) mapSKU.setOffer(sku.getOfferUnits(), sku.getOfferPrice());
			skuMap.put(sku.getName(), mapSKU);
			updated = true;
		} else if (updateUnitPrice){
			skuMap.put(sku.getName(), sku);
			updated = true;
		}
		return updated;
	}

	public Map<String, SKU> getSKUMap() {
		return skuMap;
	}
}