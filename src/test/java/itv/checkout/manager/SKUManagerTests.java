package itv.checkout.manager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;

import org.junit.Test;

import itv.checkout.TestConstants;
import itv.checkout.bean.SKU;

public class SKUManagerTests {
	
	@Test
	public void testSKUManager() {
		SKUManager skuManager = new SKUManager();
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getName(), is(TestConstants.SKU_ITEM_NAME_A));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_B).getName(), is(TestConstants.SKU_ITEM_NAME_B));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_C).getName(), is(TestConstants.SKU_ITEM_NAME_C));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_D).getName(), is(TestConstants.SKU_ITEM_NAME_D));
	}

	@Test
	public void testGetSKU() {
		SKUManager skuManager = new SKUManager();
		
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
	}

	@Test
	public void testUpdateSKUNoUnitOrOfferUpdate() {
		SKUManager skuManager = new SKUManager();
		
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));

		SKU updatedSKU = new SKU(TestConstants.SKU_ITEM_NAME_A);
		assertThat(skuManager.updateSKU(updatedSKU, false, false), is(false));

	}

	@Test
	public void testUpdateSKUUnitUpdate() {
		SKUManager skuManager = new SKUManager();
		
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(true));

		SKU updatedSKU = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE_UPDATE);
		
		assertThat(skuManager.updateSKU(updatedSKU, true, false), is(true));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE_UPDATE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(true));
	}

	@Test
	public void testUpdateSKUUnitZeroPrice() {
		SKUManager skuManager = new SKUManager();
		
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(true));

		SKU updatedSKU = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.ZERO_VALUE);
		
		assertThat(skuManager.updateSKU(updatedSKU, true, false), is(true));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.ZERO_VALUE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(true));
	}

	@Test
	public void testUpdateSKUOfferUpdate() {
		SKUManager skuManager = new SKUManager();
		
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(true));

		SKU updatedSKU = new SKU(TestConstants.SKU_ITEM_NAME_A);
		updatedSKU.setOffer(TestConstants.SKU_ITEM_A_OFFER_UNITS_UPDATE, TestConstants.SKU_ITEM_A_OFFER_PRICE_UPDATE);
		
		assertThat(skuManager.updateSKU(updatedSKU, false, true), is(true));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE_UPDATE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS_UPDATE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(true));
	}

	@Test
	public void testUpdateSKURemoveOfferUpdate() {
		SKUManager skuManager = new SKUManager();
		
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(true));

		SKU updatedSKU = new SKU(TestConstants.SKU_ITEM_NAME_A);
		updatedSKU.setOffer(TestConstants.ZERO_VALUE, TestConstants.ZERO_VALUE);
		
		assertThat(skuManager.updateSKU(updatedSKU, false, true), is(true));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.ZERO_VALUE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.ZERO_VALUE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(false));
		
	}

	@Test
	public void testUpdateSKUUnitAndOfferUpdate() {
		SKUManager skuManager = new SKUManager();
		
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(true));

		SKU updatedSKU = new SKU(TestConstants.SKU_ITEM_NAME_A, TestConstants.SKU_ITEM_A_UNIT_PRICE_UPDATE);
		updatedSKU.setOffer(TestConstants.SKU_ITEM_A_OFFER_UNITS_UPDATE, TestConstants.SKU_ITEM_A_OFFER_PRICE_UPDATE);
		
		assertThat(skuManager.updateSKU(updatedSKU, true, true), is(true));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getUnitPrice(), is(TestConstants.SKU_ITEM_A_UNIT_PRICE_UPDATE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferPrice(), is(TestConstants.SKU_ITEM_A_OFFER_PRICE_UPDATE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).getOfferUnits(), is(TestConstants.SKU_ITEM_A_OFFER_UNITS_UPDATE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_A).isOnOffer(), is(true));
	}

	@Test
	public void testUpdateSKUAddSKU() {
		SKUManager skuManager = new SKUManager();
		HashMap<String, SKU> itemMap = (HashMap<String, SKU>)skuManager.getSKUMap();
		
		assertThat(itemMap.containsKey(TestConstants.SKU_ITEM_NAME_E), is(false));

		SKU updatedSKU = new SKU(TestConstants.SKU_ITEM_NAME_E, TestConstants.SKU_ITEM_E_UNIT_PRICE);
		
		assertThat(skuManager.updateSKU(updatedSKU, true, false), is(true));
		assertThat(itemMap.containsKey(TestConstants.SKU_ITEM_NAME_E), is(true));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_E).getUnitPrice(), is(TestConstants.SKU_ITEM_E_UNIT_PRICE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_E).getOfferPrice(), is(TestConstants.ZERO_VALUE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_E).getOfferUnits(), is(TestConstants.ZERO_VALUE));
		assertThat(skuManager.getSKU(TestConstants.SKU_ITEM_NAME_E).isOnOffer(), is(false));
	}

	@Test
	public void testGetSKUMap() {
		SKUManager skuManager = new SKUManager();
		HashMap<String, SKU> itemMap = (HashMap<String, SKU>)skuManager.getSKUMap();
		
		assertThat(itemMap.containsKey(TestConstants.SKU_ITEM_NAME_A), is(true));
		assertThat(itemMap.containsKey(TestConstants.SKU_ITEM_NAME_B), is(true));
		assertThat(itemMap.containsKey(TestConstants.SKU_ITEM_NAME_C), is(true));
		assertThat(itemMap.containsKey(TestConstants.SKU_ITEM_NAME_D), is(true));
	}
}