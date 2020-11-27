package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;



public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		System.out.println("execute ");
												
												
		int prodNo=(Integer.parseInt(request.getParameter("prodNo")));
		System.out.println("prodNo :" + prodNo);
		
		ProductService service=new ProductServiceImpl();
		System.out.println("service :" + service);
		Product vo=service.getProduct(prodNo);
		System.out.println("vo" +vo.getProdName());
		
		request.setAttribute("vo", vo);
		System.out.println(" GetProductAction  vo받아옴"+vo);
		
//		String navigation = "";
		System.out.println(" GetProductAction  vo 보낼때"+vo);
//		if (request.getParameter("menu") != null) {
			if (request.getParameter("menu").equals("manage")) {
				return "forward:/product/updateProduct.jsp"; 
			}else {
				return "forward:/product/getProduct.jsp";
			}
		}
}
	
