package itv.checkout.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import itv.checkout.bean.CheckoutSession;

@Component
public class CheckoutManager {
	private final Logger logger = LoggerFactory.getLogger(CheckoutManager.class);
	
	Map<Integer, CheckoutSession> checkoutSessions;
	private int sessionId = 0;
	
	public CheckoutManager() {
		checkoutSessions = new HashMap<>();
	}
	
	public CheckoutSession getCheckoutSession(Integer checkoutSessionId) {
		CheckoutSession checkoutSession = null;
		if (!checkoutSessions.isEmpty()) {
			checkoutSession = checkoutSessions.get(checkoutSessionId);
		}
		return checkoutSession;
	}

	public CheckoutSession addCheckoutSession() {
		sessionId++;
		CheckoutSession checkoutSession = new CheckoutSession(sessionId);
		checkoutSessions.put(checkoutSession.getCheckoutSessionId(), checkoutSession);
		
		return checkoutSession;
	}
	
	public boolean hasActiveSession() {
		if (logger.isDebugEnabled()) {
			logger.debug("START hasActiveSession()");
		}
		boolean hasActiveSession = false;
		if (!checkoutSessions.isEmpty()) {
			for (CheckoutSession checkoutSession : checkoutSessions.values()) {
				if (checkoutSession.isActive()) {
					hasActiveSession = true;
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("END hasActiveSession(" + hasActiveSession + ")");
		}
		return hasActiveSession;
	}
	
	public Collection<CheckoutSession> getCheckoutSessions() {
		return checkoutSessions.values();
	}
}