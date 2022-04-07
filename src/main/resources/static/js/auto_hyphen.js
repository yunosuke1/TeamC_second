
$(function(){

  function insertZipcode(input){
    return input.slice(0, 3) + '-' + input.slice(3,input.length);
  }

  $("#inputZipcode").on('keyup',function(){
    var input = $("#inputZipcode").val();


    var key = event.keyCode || event.charCode;
    if( key == 8 || key == 46 ){
      return false;
    }

    if(input.length === 3){
      $("#inputZipcode").val(insertZipcode(input));
    }
  });
  
  $("#inputZipcode").on('blur',function(){
    var input = $("#inputZipcode").val();

    if(input.length >= 3 && input.substr(3,1) !== '-'){
      $("#inputZipcode").val(insertStr(input));
    }
  });
}
)