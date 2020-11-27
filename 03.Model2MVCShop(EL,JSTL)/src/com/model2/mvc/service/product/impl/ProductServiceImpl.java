package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;

public class ProductServiceImpl implements ProductService {
	
	private ProductDAO productDao;

	public ProductServiceImpl() {
		productDao = new ProductDAO();
	}

	@Override
	public void addProduct(Product product) throws Exception {
		productDao.insertProduct(product);
		
	}


	@Override
	public Product getProduct(int prodNo) throws Exception {
		return productDao.getProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		return productDao.getProductList(search);
		
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}	
	public boolean checkDuplication(int prodNo) throws Exception {
		boolean result=true;
		Product product=productDao.getProduct(prodNo);
		if(product != null) {
			result=false;
		}
		return result;
	}
}