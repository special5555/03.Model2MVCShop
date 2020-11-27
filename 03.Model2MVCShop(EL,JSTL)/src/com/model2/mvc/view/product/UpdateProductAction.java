package com.model2.mvc.view.product;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;



public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		//int ProdNo=(Integer.parseInt(request.getParameter("prodNo")));
		int prodNo=(Integer.parseInt(request.getParameter("prodNo")));
		System.out.println("update Product ProdNo까지 넘어옴");
		Product product = new Product();
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));  
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		System.out.println("update Product fileName까지 넘어옴"+product);
		System.out.println("reg_date"+request.getParameter("REG_DATE"));
		product.setRegDate(Date.valueOf(request.getParameter("REG_DATE")));
//		productVO.setProdNo(ProdNo);
//		System.out.println("update Product regDate까지 넘어옴"+productVO);
		ProductService productservice=new ProductServiceImpl();
		productservice.updateProduct(product);
		request.setAttribute("vo",product);
		
		HttpSession session=request.getSession();
		
		String navigation = "";
		System.out.println("UpdateProductAction vo값" +product);
		
//		String sessionId=((ProductVO)session.getAttribute("product")).getProdNo();
//	
//		if(sessionId.equals(ProdNo)){
//			session.setAttribute("product", productVO)
		if (request.getParameter("menu") != null) {
			if (request.getParameter("menu").equals("manage")) {
				navigation= "manage";
			}else {
				navigation= "search";
			}
		}
			session.setAttribute("menu", navigation);
		
	//여기서 다시  jsp로 쏴준다.
		//hidden 을 받아온다 <input type="hidden" name="prodNo" value="<%=vo.getProdNo() %>">
			
//		if (request.getParameter("menu") != null) {
//			if (request.getParameter("menu").equals("manage")) {
				return "forward:/product/getProduct.jsp";
//			}else {
//				return "forward:/product/updateProduct.jsp";
			}
		}
	






	
		
				
		
