<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submitPersonalSendDataFlag(flag){
  	var $form = $("#viewPromiseSendDataToKR_form");
	$.ajax({
  		type: 'POST',
  		url:$form.attr("action"),
  		data:$form.serializeArray(),
  		dataType:"json",
  		cache: false,
  		success: function(){
  				$.pdialog.closeCurrent();
  		} ,
  		error: DWZ.ajaxError
  	});
}
function logoutComfirm(){
	if(confirm('Bạn từ chối với điều kiện trên?')){
		location.href = '/login/out' ;
	}
}
$("a.close").click(function(){

		location.href = '/login/out' ;

});
</script>

<div class="pageContent" sysLong='printDiv' layoutH="50" style="text-align:left;">
	<div style="font:bold 14px/20px arial,sans-serif;text-align:center;width:100%;padding-top:20px;padding-bottom:20px;">
	<span style="color: #034ea1;font-size: 18px;">Thông tin cá nhân được chuyển đến và quản lý tại trụ sở chính của Công ty tại Hàn Quốc.</span></div>
	<form id="viewPromiseSendDataToKR_form" method="post" action="/login/updatePromiseSendDataToKR" class="pageForm required-validate">
		<div style="padding-left:20px;padding-right:20px;">
						<!-- <table class="user_table" width="100%" style="border: 0;">
							<tr>
								<td style="text-align:left;height: 30px;font-size: 15px;color: #034ea1;border: 0;"> - Xác minh danh tính, lý lịch.</td>
							</tr>
							<tr>
								<td style="text-align:left;height: 30px;font-size: 15px;color: #034ea1;border: 0;"> - Giao kết, thực hiện các hợp đồng, thỏa thuận, văn bản.</td>
							</tr>
							<tr>
								<td style="text-align:left;height: 30px;font-size: 15px;color: #034ea1;border: 0;"> - Thống kê, quản lý, báo cáo.</td>
							</tr>
							<tr>
								<td style="text-align:left;height: 30px;font-size: 15px;color: #034ea1;border: 0;"> - Phục vụ yêu cầu về bảo hiểm, thuế, kế toán, kiểm toán, kiểm soát rủi ro, kiểm soát nội bộ.</td>
							</tr>
							<tr>
								<td style="text-align:left;height: 30px;font-size: 15px;color: #034ea1;border: 0;"> - Giải quyết khiếu nại, giải quyết tranh chấp.</td>
							</tr>
							<tr>
								<td style="text-align:left;height: 30px;font-size: 15px;color: #034ea1;border: 0;"> - Thực hiện theo quy định của pháp luật và yêu cầu của cơ quan chức năng.</td>
							</tr>
						</table>
						<br></br> -->
						
		</div>
		<div class="formBar" style="border-bottom: 0;">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="submitPersonalSendDataFlag(1)">
								Đồng ý
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 否决 -->
							<button type="button" onclick="logoutComfirm()">
								<!--否决--><spring:message code="ess.infoApply.veto" />
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
	<br></br>
	

</div>