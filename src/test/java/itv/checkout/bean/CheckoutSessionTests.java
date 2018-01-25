package itv.checkout.bean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;

import java.util.HashMap;

import org.junit.Test;

import itv.checkout.TestConstants;

public class CheckoutSessionTests {
	
	private CheckoutSession checkoutSession;

	@Test
	public void testCheckoutSession() {
		checkoutSession = new CheckoutSession(1);
		
		assertThat(checkoutSession.getItems(), nullValue(null));
		assertThat(checkoutSession.getTotalCost(), is(TestConstants.ZERO_VALUE));
		assertThat(checkoutSession.isActive(), is(false));
	}

	@Test
	public void testCheckoutSessionCopyConstructor() {
		checkoutSession = new CheckoutSession(1);
		checkoutSession.addItem(new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(checkoutSession.getItems().size(), is(1));
		assertThat(checkoutSession.getTotalCost(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(checkoutSession.isActive(), is(true));
		
		CheckoutSession cloneCheckoutSession = new CheckoutSession(checkoutSession);
		assertThat(cloneCheckoutSession.getItems().size(), is(1));
		assertThat(cloneCheckoutSession.getTotalCost(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(cloneCheckoutSession.isActive(), is(true));
	}

	@Test
	public void testAddItems() {
		checkoutSession = new CheckoutSession(1);
		checkoutSession.addItem(new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE));
		checkoutSession.addItem(new SKU(TestConstants.SKU_ITEM_NAME_B, TestConstants.SKU_ITEM_B_UNIT_PRICE));

		assertThat(checkoutSession.getItems().size(), is(2));
		assertThat(checkoutSession.getTotalCost(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE + TestConstants.SKU_ITEM_B_UNIT_PRICE));
		assertThat(checkoutSession.isActive(), is(true));
	}

	@Test
	public void testAddItemsToMatchOffer() {
		checkoutSession = new CheckoutSession(1);
		SKU itemA = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE, TestConstants.SKU_ITEM_A_OFFER_UNITS, TestConstants.SKU_ITEM_A_OFFER_PRICE);
		for (int i=0; i < TestConstants.SKU_ITEM_A_OFFER_UNITS; i++) {
			checkoutSession.addItem(itemA);
		}

		assertThat(checkoutSession.getItems().size(), is(1));
		assertThat(checkoutSession.getTotalCost(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(checkoutSession.isActive(), is(true));
	}

	@Test
	public void testAddItemsToMatchOfferUnitsTwice() {
		checkoutSession = new CheckoutSession(1);
		SKU itemA = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE, TestConstants.SKU_ITEM_A_OFFER_UNITS, TestConstants.SKU_ITEM_A_OFFER_PRICE);
		for (int i=0; i < (TestConstants.SKU_ITEM_A_OFFER_UNITS * 2); i++) {
			checkoutSession.addItem(itemA);
		}

		assertThat(checkoutSession.getItems().size(), is(1));
		assertThat(checkoutSession.getTotalCost(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE * 2));
		assertThat(checkoutSession.isActive(), is(true));
	}


	@Test
	public void testGetItems() {
		SKU skuItem = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE);
		checkoutSession = new CheckoutSession(1);
		checkoutSession.addItem(skuItem);

		HashMap<SKU, Integer> items = (HashMap<SKU, Integer>)checkoutSession.getItems();
		assertThat(items.size(), is(1));
		assertThat(items.keySet().contains(skuItem), is(true));
	}

	@Test
	public void testGetTotalCost() {
		checkoutSession = new CheckoutSession(1);
		checkoutSession.addItem(new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE));

		assertThat(checkoutSession.getTotalCost(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
	}

	@Test
	public void testIsActiveFalse() {
		checkoutSession = new CheckoutSession(1);
		assertThat(checkoutSession.isActive(), is(false));
	}

	@Test
	public void testIsActiveTrue() {
		checkoutSession = new CheckoutSession(1);
		checkoutSession.addItem(new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(checkoutSession.isActive(), is(true));
	}

	@Test
	public void testReset() {
		checkoutSession = new CheckoutSession(1);		
		checkoutSession.addItem(new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(checkoutSession.isActive(), is(true));
		
		checkoutSession.reset();
		
		assertThat(checkoutSession.getItems(), nullValue(null));
		assertThat(checkoutSession.getTotalCost(), is(TestConstants.ZERO_VALUE));
		assertThat(checkoutSession.isActive(), is(false));
	}
}