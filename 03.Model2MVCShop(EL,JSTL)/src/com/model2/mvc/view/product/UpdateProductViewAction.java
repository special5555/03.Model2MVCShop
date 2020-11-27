package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

//!!!ERROR을 잡아라!!! CAPSLOCK은 왜 걸려있죠??

public class UpdateProductViewAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int ProdNo=(Integer.parseInt(request.getParameter("prodNo")));
		
		ProductService service=new ProductServiceImpl();
		Product vo=service.getProduct(ProdNo);
		
		request.setAttribute("vo", vo);
		
		return "forward:/product/updateProduct.jsp";// webContent 하위 애들 찾는 녀석
		
		
		//getProduct.do?prodNo=10000&menu=manage servlet 으로 보내주는놈 클래시스 안에있는녀석 찾는곳
	}
}
