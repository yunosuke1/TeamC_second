package com.example.ecommerce_a.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.service.ItemService;
 
@Controller
@RequestMapping("/shoppingList")
public class ItemController {
    @Autowired
    private ItemService service;
 
    //スタート
    @RequestMapping("")
    public String index(Model model){
            List<Item> itemList=service.findAll();
        model.addAttribute("itemList", itemList);
            model.addAttribute("itemList", itemList);
    //    System.out.println(itemList);
        return "item_list_coffee";
    }
 
    @RequestMapping("/orderBy")
    public String index(String select,Model model){
        if(select.equals("low")){
            List<Item> itemList=service.findAll();
        model.addAttribute("itemList", itemList);
        }else if(select.equals("high")){
            List<Item> itemList=service.findAllDesc();
            model.addAttribute("itemList", itemList);
        }
    //    System.out.println(itemList);
        return "item_list_coffee";
    }
 
    @RequestMapping("/searchWord")
    public String findByLikeWord(String searchWord,String select,Model model){
    	if(select==null) {
    		return "forward:/shoppingList";
    	}
        if(select.equals("low")){
            List<Item> itemList=service.findByLikeName(searchWord);
            if(itemList.size()==0){
                String nullMessage="該当する商品がありません";
                model.addAttribute("nullMessage", nullMessage);
        		model.addAttribute("searchWord",searchWord);
                return index(model);
            }else{
                System.out.println("low");
                model.addAttribute("itemList", itemList);
        		model.addAttribute("searchWord",searchWord);
                return "item_list_coffee";
 
            }
        }else if(select.equals("high")){
            List<Item> itemListD=service.findByLikeNameDesc(searchWord);
            if(itemListD.size()==0){
                String nullMessage="該当する商品がありません";
                model.addAttribute("nullMessage", nullMessage);
        		model.addAttribute("searchWord",searchWord);
                return index(model);
            }else{
                System.out.println("high");
                model.addAttribute("itemList", itemListD);
        		model.addAttribute("searchWord",searchWord);
                return "item_list_coffee";
            }
        }else{
            return "forward:/shoppingList";
        }
       
       
    }
}
