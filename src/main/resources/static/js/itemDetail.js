"use strict";

$(function () {
	let total = 0;
	let total2 = 0;
	let total3=0;
	$('input:radio[name="size"]').change(function() {
		if($('input:radio[name="size"]:checked').val()=="M"){
			total=$("#priceM").val();
		}else{
			total=$("#priceL").val();
		}
		total=parseInt(total);
		let totalAll = (total+total2)*total3;
		$("#total-price").text(totalAll+"円");
	}).trigger('change');
	
	
	let cnt2 =0;
	$('input:checkbox[name="orderToppings"]').change(function() {
        let cnt = $('#topping input:checkbox:checked').length;
        if($('input:radio[name="size"]:checked').val()=="M"){
			if(cnt2>cnt){
				total2 = total2 - 200;
			}else{
				total2 = total2 + 200;
			}
		}else if($('input:radio[name="size"]:checked').val()=="L"){
			if(cnt2>cnt){
				total2 = total2 - 300;
			}else{
				total2 = total2 + 300;
			}
		}
		cnt2=cnt;
		
		let totalAll = (total+total2)*total3;
		$("#total-price").text(totalAll+"円");
    });

    $('#suryo').change(function() {
		total3 = $("#suryo").val();
		
		let totalAll = (total+total2)*total3;
		$("#total-price").text(totalAll+"円");
	
	}).trigger('change');
	
	
	
});