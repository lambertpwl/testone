package itv.checkout.bean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import itv.checkout.TestConstants;

public class SKUTests {
	
	private final SKU offerItem = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE, TestConstants.SKU_ITEM_A_OFFER_UNITS, TestConstants.SKU_ITEM_A_OFFER_PRICE);
	private final SKU plainItem = new SKU(TestConstants.SKU_ITEM_NAME_B, TestConstants.SKU_ITEM_B_UNIT_PRICE);
	
	@Test
	public void testSKUStringInt() {
		SKU item = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE);
		assertThat(item.getName(), is(TestConstants.SKU_ITEM_NAME_A));
		assertThat(item.getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
	}

	@Test
	public void testSKUStringIntIntInt() {
		SKU item = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE, TestConstants.SKU_ITEM_A_OFFER_UNITS, TestConstants.SKU_ITEM_A_OFFER_PRICE);
		assertThat(item.getName(), is(TestConstants.SKU_ITEM_NAME_A));
		assertThat(item.getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(item.getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(item.getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
	}

	@Test
	public void testGetName() {
		assertThat(plainItem.getName(), is(TestConstants.SKU_ITEM_NAME_B));
	}

	@Test
	public void testSetName() {
		SKU item = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE);
		item.setName(TestConstants.SKU_ITEM_NAME_A_UPDATE);
		
		assertThat(item.getName(), is(TestConstants.SKU_ITEM_NAME_A_UPDATE));
	}

	@Test
	public void testGetPrice() {
		assertThat(plainItem.getUnitPrice(), is(TestConstants.SKU_ITEM_B_UNIT_PRICE));
	}

	@Test
	public void testSetPrice() {
		SKU item = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE);
		item.setUnitPrice(TestConstants.SKU_ITEM_A_UNIT_PRICE_UPDATE);
		
		assertThat(item.getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE_UPDATE));
	}

	@Test
	public void testGetOfferUnits() {
		assertThat(plainItem.getOfferUnits(), is(0));
		assertThat(offerItem.getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
	}

	@Test
	public void testSetOffer() {
		SKU item = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE);
		assertThat(item.isOnOffer(), is(false));
		
		item.setOffer(TestConstants.SKU_ITEM_A_OFFER_UNITS, TestConstants.SKU_ITEM_A_OFFER_PRICE);
		
		assertThat(item.getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
		assertThat(item.getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(item.isOnOffer(), is(true));
	}

	@Test
	public void testUpdateOffer() {
		SKU item = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE, TestConstants.SKU_ITEM_A_OFFER_UNITS, TestConstants.SKU_ITEM_A_OFFER_PRICE);
		assertThat(item.isOnOffer(), is(true));
		item.setOffer(TestConstants.SKU_ITEM_A_OFFER_UNITS_UPDATE, TestConstants.SKU_ITEM_A_OFFER_PRICE_UPDATE);
		
		assertThat(item.getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS_UPDATE));
		assertThat(item.getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE_UPDATE));
		assertThat(item.isOnOffer(), is(true));
	}

	@Test
	public void testGetOfferPrice() {
		assertThat(plainItem.getOfferPrice(), is(0));
		assertThat(offerItem.getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
	}

	@Test
	public void testUpdateUnitPrice() {
		SKU item = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE);
		item.update(TestConstants.SKU_ITEM_A_UNIT_PRICE_UPDATE, 0, 0);
		
		assertThat(item.getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE_UPDATE));
	}

	@Test
	public void testIsOnOffer() {
		assertThat(plainItem.isOnOffer(), is(false));
		assertThat(offerItem.isOnOffer(), is(true));
	}
}