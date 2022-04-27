package com.model2.mvc.web.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

//==> ÆÇ¸Å RestController
@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {

	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService = new PurchaseServiceImpl();
		
	public PurchaseRestController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value = "json/getPurchase/{tranNo}", method = RequestMethod.GET)
	public Purchase getPurchase(@PathVariable int tranNo) throws Exception{
		
		System.out.println("purchase/json/getPurchase : GET");
		System.out.println("tranNo : " + tranNo);
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		return purchase;
	}

	@RequestMapping(value = "json/addPurchase", method = RequestMethod.POST)
	public Purchase addPurchase(@RequestBody Purchase purchase) throws Exception{
		
		System.out.println("purchase/json/addPurchase : POST");
		System.out.println(purchase);
		purchaseService.addPurchase(purchase);
		
		return purchase;
	}
}