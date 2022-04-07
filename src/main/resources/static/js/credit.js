function ShowCheckResult()
{
   in_card_num = document.getElementById("card_num").value;
   //カード番号チェック
   if(in_card_num == "" || NumCheck(in_card_num) == false){
      alert("カード番号には半角数値を入力して下さい"); 
      return;
   }
   //チェックデジット判定
   if(luhn_checksum(in_card_num) == false){
      alert("クレジットカードの番号が不正です"); 
      return;
   }
   //チェックOK
   alert("クレジットカードの番号は正常です"); 
}

//数値チェック
function NumCheck(val) {
   if (val.match(/[^z0-9]+/)) {
   return false;
   }
}

//チェックデジット判定
function luhn_checksum(card_number) {
  var a, s, i, x;
  a = card_number.split("").reverse();
  s = 0;
  for (i = 0; i < a.length; i++) {
    x = a[i] * (1 + i % 2);
    s += x > 9 ? x - 9 : x;
  }
  return s % 10 == 0;
}