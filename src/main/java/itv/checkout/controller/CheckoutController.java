package itv.checkout.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
	
	@Autowired
    private CheckoutSession checkoutSession;
	
	@Autowired
    private SKUManager skuManager;

    
    @RequestMapping(method = RequestMethod.GET, value = "/pricing")
    @ResponseBody
    public Map<String, SKU> getCurrentSKUPricing(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("START getCurrentSKUPricing()");
    	}
    	try {
        	return skuManager.getSKUMap();
	    } finally {
    		if (logger.isDebugEnabled()) {
    			logger.debug("END getCurrentSKUPricing(skuList=" + skuManager.getSKUMap() + ")");
    		}
    	}    	
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateSKU")
    @ResponseBody
    public String updateSKU(	@RequestParam("name") String name, @RequestParam("unitPrice") Optional<Integer> unitPrice, 
    							@RequestParam("offerPrice") Optional<Integer> offerPrice, 
    							@RequestParam("offerUnits") Optional<Integer> offerUnits) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("START updateSKU(name=" + name + ", unitPrice=" + unitPrice + ", offerPrice=" + offerPrice + ", offerUnits=" + offerUnits + ")");
    	}
    	boolean updateUnit = false;
    	boolean updateOffer = false;
    	boolean updated = false;
    	if (!hasActiveSession() && (name != null && !name.isEmpty())) {
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
    	if (logger.isDebugEnabled()) {
    		logger.debug("END updateSKU(updated=" + updated + ")");
    	}
    	return (updated ? "UPDATED" : "NO UPDATE");
    }

	@RequestMapping(method = RequestMethod.POST, value = "/scanItem")
    @ResponseBody
    public SKU scanItem(@RequestParam("name") String name) {
		if (logger.isDebugEnabled()) {
			logger.debug("START scanItem(name=" + name + ")");
		}
    	SKU sku = skuManager.getSKU(name);
    	if (sku != null) {
    		checkoutSession.addItem(sku);
    	} 
    	if (logger.isDebugEnabled()) {
    		logger.debug("END scanItem(sku=" + sku + ")");
    	}
    	return sku;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/status")
    @ResponseBody
    public CheckoutSession getCheckoutSessionStatus() {
    	if (logger.isDebugEnabled()) {
    		logger.debug("START getCheckoutSessionStatus()");
    	}
    	try {
	    	return this.checkoutSession;
	    } finally {
    		if (logger.isDebugEnabled()) {
    			logger.debug("END getCheckoutSessionStatus(checkoutSession=" + this.checkoutSession + ")");
    		}
    	}
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/checkout")
    @ResponseBody
    public CheckoutSession checkout() {
    	if (logger.isDebugEnabled()) {
    		logger.debug("START checkout()");
    	}
    	CheckoutSession coSession = new CheckoutSession(this.checkoutSession);
		
		this.checkoutSession.reset();
		
		if (logger.isDebugEnabled()) {
			logger.debug("END checkout(checkoutSession=" + coSession + ")");
		}
		return coSession;
    }

    private boolean hasActiveSession() {
    	boolean activeSession = false;
    	if (logger.isDebugEnabled()) {
    		logger.debug("START getCheckoutSessionStatus()");
    	}
		activeSession = (this.checkoutSession != null ? this.checkoutSession.isActive() : false);
		if (logger.isDebugEnabled()) {
			logger.debug("END getCheckoutSessionStatus()");
		}
    	return activeSession;
	}
}