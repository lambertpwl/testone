package itv.checkout.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import itv.checkout.bean.CheckoutSession;
import itv.checkout.bean.SKU;
import itv.checkout.manager.SKUManager;

@RestController
@RequestMapping("/")
public class CheckoutController {

	@Autowired
    private CheckoutSession checkoutSession;
	
	@Autowired
    private SKUManager skuManager;

    
    @RequestMapping(method = RequestMethod.GET, value = "/pricing")
    @ResponseBody
    public Map<String, SKU> getCurrentSKUPricing(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	return skuManager.getSKUMap();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateSKU")
    @ResponseBody
    public String updateSKU(	@RequestParam("name") String name, @RequestParam("unitPrice") Optional<Integer> unitPrice, 
    							@RequestParam("offerPrice") Optional<Integer> offerPrice, 
    							@RequestParam("offerUnits") Optional<Integer> offerUnits) {
    	boolean updateUnit = false;
    	boolean updateOffer = false;
    	boolean updated = false;
    	
    	if (!hasActiveSession() && (name != null & !name.isEmpty())) {
    		SKU updatedSKU = new SKU(name);
    		if (unitPrice.isPresent()) {
    			updatedSKU.setUnitPrice(unitPrice.get());
    			updateUnit = true;
    		} 
    		if (offerPrice.isPresent() && offerUnits.isPresent()) {
    			updatedSKU.setOffer(offerUnits.get(), offerPrice.get());
    			updateOffer = true;
    		}
    		if (updateUnit || updateOffer) {
    			updated = skuManager.updateSKU(updatedSKU, updateUnit, updateOffer);
    		}
    	} 
    	return (updated ? "UPDATED" : "NO UPDATE");
    }

	@RequestMapping(method = RequestMethod.POST, value = "/scanItem")
    @ResponseBody
    public SKU scanItem(@RequestParam("name") String name) {
    	SKU sku = skuManager.getSKU(name);
    	if (sku != null) {
    		checkoutSession.addItem(sku);
    	} 
    	return sku;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/status")
    @ResponseBody
    public CheckoutSession getCheckoutSessionStatus() {
    	return this.checkoutSession;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/checkout")
    @ResponseBody
    public CheckoutSession checkout() {
		CheckoutSession coSession = new CheckoutSession(this.checkoutSession);
		
		this.checkoutSession.reset();
		
		return coSession;
    }

    private boolean hasActiveSession() {
		return (this.checkoutSession != null ? this.checkoutSession.isActive() : false);
	}
}