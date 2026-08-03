var ajaxGet_add;
function ajaxAdd() {
	if (ajaxGet_add != null) {
		ajaxGet_add.abort();
	}
	$.ajaxSettings.global = false;
	ajaxGet_add = $
			.ajax( {
				type : "POST",
				url : "/pa/tempsale/getSpmsShopList",
				data : {
					CONTENT : $("#shop_name_add").val()
				},
				dataType : "json",
				success : function(data) {
					$('#shopTree_add').html("");
					var html = '<tr><th>Code</th><th>门店名称</th></tr>';
					if (typeof (data['shopList']) != "undefined") {
						$.each(data['shopList'],
										function(commentIndex, comment) {
											html += '<tr onclick="selectedIt(\''
													+ comment['SHOP_NAME']
													+ '\',\'' + comment['SHOP_CD'] + '\')"><td style="text-align:left;width:30%">'
													+ comment['SHOP_CD']
													+ '</td><td style="text-align:left;width:70%">'
													+ comment['SHOP_NAME']
													+ '</td></tr>';
										});
					}
					$('#shopTree_add').html(html);
					$("#shopContent_add").css("display", "block");
				}
			});
	$.ajaxSettings.global = true;
}
function selectedIt(shopName,shopNo) {
	$('#shop_name_add').val(shopName);
	$('#shop_cd_add').val(shopNo);
	$('#shopContent_add').css('display', 'none');
}