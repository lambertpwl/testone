package itv.checkout.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import itv.checkout.bean.CheckoutSession;
import itv.checkout.bean.SKU;
import itv.checkout.manager.CheckoutManager;
import itv.checkout.manager.SKUManager;

@RestController
@RequestMapping("/")
public class CheckoutController {

	final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
	
	@Autowired
    private CheckoutManager checkoutManager;
	
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

    @RequestMapping(method = RequestMethod.GET, value = "/sessions")
    @ResponseBody
    public Collection<CheckoutSession> getCheckoutSessions(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("START getCheckoutSessions()");
    	}
    	try {
        	return checkoutManager.getCheckoutSessions();
	    } finally {
    		if (logger.isDebugEnabled()) {
    			logger.debug("END getCheckoutSessions(checkoutSessions=" + checkoutManager.getCheckoutSessions() + ")");
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
    public CheckoutSession scanItem(@RequestParam("name") String name, @RequestParam("checkoutSessionId") Optional<Integer> checkoutSessionId) {
		CheckoutSession checkoutSession = null;
		if (logger.isDebugEnabled()) {
			logger.debug("START scanItem(name=" + name + ") checkoutSessionId(" + checkoutSessionId + ")");
		}
    	SKU sku = skuManager.getSKU(name);
    	if (sku != null) {
			if (!checkoutSessionId.isPresent()) {
				checkoutSession = checkoutManager.addCheckoutSession();
			} else {
				checkoutSession = checkoutManager.getCheckoutSession(checkoutSessionId.get());
			}
    		if (checkoutSession != null && checkoutSession.isActive()) {
    			checkoutSession.addItem(sku);
    		} else {
    			throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
    		}
    	} else {
    		throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
    	}
    	if (logger.isDebugEnabled()) {
    		logger.debug("END scanItem(checkoutSessionId=" + checkoutSession.getCheckoutSessionId() + ")");
    	}
    	return checkoutSession;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/status")
    @ResponseBody
    public CheckoutSession getCheckoutSessionStatus(@RequestParam("checkoutSessionId") Integer checkoutSessionId) {
    	CheckoutSession checkoutSession = null;
    	if (logger.isDebugEnabled()) {
    		logger.debug("START getCheckoutSessionStatus(checkoutSessionId=" + checkoutSessionId + ")");
    	}
    	checkoutSession = this.checkoutManager.getCheckoutSession(checkoutSessionId);
		if (logger.isDebugEnabled()) {
			logger.debug("END getCheckoutSessionStatus(checkoutSession=" + checkoutSession + ")");
		}
		return checkoutSession;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getReceipt")
    @ResponseBody
    public String getCheckoutSessionReceipt(@RequestParam("checkoutSessionId") Integer checkoutSessionId) {
    	CheckoutSession checkoutSession = null;
    	if (logger.isDebugEnabled()) {
    		logger.debug("START getCheckoutSessionReceipt(checkoutSessionId=" + checkoutSessionId + ")");
    	}
    	checkoutSession = this.checkoutManager.getCheckoutSession(checkoutSessionId);
		if (logger.isDebugEnabled()) {
			logger.debug("END getCheckoutSessionReceipt(checkoutSession=" + checkoutSession + ")");
		}
		return checkoutSession.getReceipt();
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/checkout")
    @ResponseBody
    public CheckoutSession checkout(@RequestParam("checkoutSessionId") Integer checkoutSessionId) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("START checkout(checkoutSessionId=" + checkoutSessionId + ")");
    	}
    	CheckoutSession checkoutSession = checkoutManager.getCheckoutSession(checkoutSessionId);
		
		checkoutSession.checkout();
		
		if (logger.isDebugEnabled()) {
			logger.debug("END checkout(checkoutSession=" + checkoutSession + ")");
		}
		return checkoutSession;
    }

    private boolean hasActiveSession() {
    	boolean activeSession = false;
    	if (logger.isDebugEnabled()) {
    		logger.debug("START hasActiveSession()");
    	}
		activeSession = checkoutManager.hasActiveSession();
		if (logger.isDebugEnabled()) {
			logger.debug("END hasActiveSession(" + activeSession + ")");
		}
    	return activeSession;
	}

	@ExceptionHandler({HttpServerErrorException.class, NullPointerException.class})
	void handleBadRequests(HttpServletResponse response) throws IOException {
	    response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}