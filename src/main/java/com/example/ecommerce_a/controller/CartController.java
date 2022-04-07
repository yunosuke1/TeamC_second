package com.example.ecommerce_a.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.service.CartService;

@Controller
@RequestMapping("/shoppingCart")
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
    private HttpSession session;
	
	@RequestMapping("")
	public String findOrderItemList(Model model){
		Integer userId = (Integer) session.getAttribute("userId");
    	Integer preId =  (Integer) session.getAttribute("preId");
    	List<OrderItem>orderItemList=null;
		HashMap<Integer,Integer>totalMap = new HashMap<>();
		
		if(userId==null&&preId==null) {
			orderItemList=null;
			model.addAttribute("emptyMessage","カートに商品がありません");
			return "cart_list.html";
		}
		
		if(userId==null) {
			orderItemList = cartService.findOrderItemList(preId);
		}else if(preId==null) {
			orderItemList = cartService.findOrderItemList(userId);
		}
		
		for(OrderItem orderItem : orderItemList) {
			totalMap.put(orderItem.getId(),orderItem.getSubTotal());
		}
		Order order	= new Order();
		order.setOrderItemList(orderItemList);
		
		if(orderItemList.size()==0) {
			orderItemList=null;
			model.addAttribute("emptyMessage","カートに商品がありません");
		}
		model.addAttribute("orderItemList",orderItemList);
		model.addAttribute("totalMap",totalMap); 
		model.addAttribute("taxTotal",order.getTax()); 
		model.addAttribute("CalcTotalPrice",order.getCalcTotalPrice()); 
		
		return "forward:/shoppingCart/url";
	}
	
	@RequestMapping("/url")
	public String index(){
		return "cart_list.html";
	}
	
	@RequestMapping("/delete")
	public String deleteCart(String deleteId,Model model){
		int itemId = Integer.parseInt(deleteId);
		cartService.deleteCart(itemId);
		return "redirect:/shoppingCart";
	}
	
}
