 $(function() {
	$('.itemTable').paginathing({//親要素のclassを記述
		perPage: 2,//1ページあたりの表示件数
		prevText:'前へ',//1つ前のページへ移動するボタンのテキスト
		nextText:'次へ',//1つ次のページへ移動するボタンのテキスト
	})
});