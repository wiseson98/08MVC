package com.model2.mvc.web.product;

import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==> ªÛ«∞ RestController
@RestController
@RequestMapping("/product/*")
public class ProductRestController {

	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Resource(name="uploadPath")
	String uploadPath;
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping(value = "json/addProduct", method = RequestMethod.POST)
	public Product addProduct(@RequestBody Product product, MultipartFile file) throws Exception{
		
		System.out.println("/product/json/addProduct : POST");
		System.out.println("Product : " + product.getProdName());
		
		if(file != null && !file.isEmpty()) {
			String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			
			FileCopyUtils.copy(file.getBytes(), new File(uploadPath, savedName));
					
			product.setFileName(savedName);
		}else {
			System.out.println("else");
		}
		
		productService.addProduct(product);
		
		System.out.println("addProduct");
		return product;
	}
	
	@RequestMapping(value = "json/getProduct/{prodNo}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable int prodNo) throws Exception{
		
		System.out.println("product/json/getProduct : GET");
		
		return productService.getProduct(prodNo);
	}

}
