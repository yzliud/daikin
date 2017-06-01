$(function(){
	// 数量减
	$(".minus").click(function() {
		var t = $(this).parent().find('.num');
		var strnum = parseInt(t.text()) - 1;
		if (strnum <= 0) {
			strnum = 0;
		}
		t.text(strnum);
		$(this).parent().find('.hidclass').val(strnum);
	});
	// 数量加
	$(".plus").click(function() {
		var t = $(this).parent().find('.num');
		var strnum = parseInt(t.text()) - 1;
		if (strnum <= 0) {
			strnum = 0;
		}
		t.text(strnum);
		$(this).parent().find('.hidclass').val(strnum);
	});
	/******------------分割线-----------------******/
	$.ajax({
		type: "post",
		url: "../../a/api/quotation/getAllProduct",
		data: {},
		dataType: "JSON",
		success: function(data) {
			alert(11111111);
					for(var i = 0; i < data.length; i++) {
						alert(data[i].name);
					var div = document.createElement('li');
				div.innerHTML=
					"<li><div class='shop-info'><div class='shop-info-text'>"+
						"<h4>"+data[i].name+"</h4>"+
					"<div class='shop-price'>"+
					"<div class='shop-pices'>￥<b class='price'>"+
						"￥"+data[i].price+
					"</b></div>"+
					"<div class='shop-arithmetic'><a href='javascript:;' class='minus'>-</a>"+
						"<span class='num' >1</span>  "+ 
						"<input id='"+data[i].id+"' type='hidden' class='hidclass' value='0'>"
						"<a href='javascript:;' class='plus'>+</a> "+
					"</div></div></div></div></li> ";
				$("#space").append(div);
			}
		},
		error: function() {}
	});
});
