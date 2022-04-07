package com.example.ecommerce_a.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.service.OrderHistoryService;

@Controller
@RequestMapping("/orderHistory")
public class OrederHistoryController {
    @Autowired
    private HttpSession session;

    @Autowired
    private OrderHistoryService service;

    @RequestMapping("")
    public  String  findPastOrder(Model model){
        Integer userId = (Integer) session.getAttribute("userId");
            if(userId==null){
                return  "forward:/login";
            }else{
                List<Order> orderList=service.findOrderHistory(userId);
                if(orderList.size()==0){
                    model.addAttribute("emptyMessage","注文履歴がありません");
                }else if(orderList.size() !=0){
                    model.addAttribute("orderHistory",orderList) ;
                }
                return "order_history";
            }
    }
}
