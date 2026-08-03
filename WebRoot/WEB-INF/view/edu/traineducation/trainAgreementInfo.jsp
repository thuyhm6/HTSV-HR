<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function peixunchazhao(){
	var empid_name=$('#peixun').val();
	$('#souPeixun').attr('href','/edu/traineducation/queryPeixun?LOCAL_NAME='+empid_name);
}
function yanxiuTime(){
	var start=$('#STUDY_START_DATE').val();
	var end=$('#STUDY_END_DATE').val();
	if(start!=''&&end!=''){
		var e=parseInt(end.replace('-','').replace('-',''));
		var s=parseInt(start.replace('-','').replace('-',''));
		if(e>=s){
			var time=DateDiff(end, start);
			$('#STUDY_DAY').attr('value',time);
		}else{
			alert('<spring:message code="edu.trainAgreement.YANXIUJIESHUSHIJIANDAYUKAISHISHIJIAN.a"/>');//研修结束时间必须大于研修开始时间!
			$('#STUDY_END_DATE').attr('value','');
			$('#STUDY_DAY').attr('value','');
			return false;
		}
	}
	
	
}
//计算日期天数差
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式    
    var  aDate,  oDate1,  oDate2,  iDays;    
    aDate  =  sDate1.split("-");    
    oDate1  =  new  Date(aDate[2]  +  '-'  +  aDate[1]  +  '-'  +  aDate[0]);    //转换为12-18-2006格式    
    aDate  =  sDate2.split("-");    
    oDate2  =  new  Date(aDate[2]  +  '-'  +  aDate[1]  +  '-'  +  aDate[0]);    
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    /* //把相差的毫秒数转换为天数 */   
    return  iDays + 1;   
}
function weiyuejinMethod(){
	var HQ_FREE     = (parseFloat($('#HQ_FREE').val()) == null || parseFloat($('#HQ_FREE').val()) == '') ? 0 : parseFloat($('#HQ_FREE').val());
	var CGFY_FREE   = ($('#CGFY_FREE').val() == null || $('#CGFY_FREE').val() == '') ? 0 : parseFloat($('#CGFY_FREE').val());
	var JP_FREE     = ($('#JP_FREE').val() == null || $('#JP_FREE').val() == '') ? 0 : parseFloat($('#JP_FREE').val());
	var ZFBZ_FREE   = ($('#ZFBZ_FREE').val() == null || $('#ZFBZ_FREE').val() == '') ? 0 : parseFloat($('#ZFBZ_FREE').val());
	var CGBZ_FREE   = ($('#CGBZ_FREE').val() == null || $('#CGBZ_FREE').val() == '') ? 0 : parseFloat($('#CGBZ_FREE').val());
	var CGBZ_FREE_FACT=($('#CGBZ_FREE_FACT').val() == null ||  $('#CGBZ_FREE_FACT').val() == '') ? 0 : parseFloat($('#CGBZ_FREE_FACT').val());
	var SYBX_FREE   = ($('#SYBX_FREE').val() == null || $('#SYBX_FREE').val() == '') ? 0 : parseFloat($('#SYBX_FREE').val());
	var YX_PAY      = ($('#YX_PAY').val() == null || $('#YX_PAY').val() == '') ? 0 : parseFloat($('#YX_PAY').val());
	var JT_FREE     = ($('#JT_FREE').val() == null || $('#JT_FREE').val() == '') ? 0 : parseFloat($('#JT_FREE').val());
	var TX_FREE     = ($('#TX_FREE').val() == null || $('#TX_FREE').val() == '') ? 0 : parseFloat($('#TX_FREE').val());

	var money= Math.round(HQ_FREE + CGFY_FREE + JP_FREE + ZFBZ_FREE + CGBZ_FREE + CGBZ_FREE_FACT + SYBX_FREE + YX_PAY + JT_FREE + TX_FREE);
    $('#FACT_PAY').attr('value',money);
}

/**
 * 删除附件
 */
function deleteAttListAgree(id,val,callback){
	var fileNosStr="";
	var flag=false;
	$("input[name='FILE_NO']").each(function(){
		if($(this).attr("checked") == "checked"){
			fileNosStr = fileNosStr + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	fileNosStr = fileNosStr + "'empty'";
	if(flag == false){
		alertMsg.info("<spring:message code='edu.planManager.QINGXIANXUANZEYAOSHANCHUDEFUJIAN.a'/>");//请先选择要删除的附件
		return false;
	}

	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete'/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/org/orgManage/deleteFile',
  				data:{fileNos:fileNosStr,typeId : id,typeValue : val },
  				dataType:"json",
  				cache: false,
  				success: callback,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
<div class="pageContent" layoutH="10" id="trainAgreementInfo">
	<form method="post" action="/edu/traineducation/updateTrainAgree"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<table class="user_table" width="100%" border="1" cellpadding="2"
				cellspacing="1">
				<input type="hidden" name="PERSON_ID" id="PERSON_ID"
					value="${trainAgreementInfo.PERSON_ID }">
				<input type="hidden" name="AGREE_NO" id="AGREE_NO"
					value="${trainAgreementInfo.AGREE_NO }">
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.XIEYIMINGCHENG.a" />
						<!--协议名称--></td>
					<td class="td_type" width="20%"><input type="text"
						class="required" name="AGREE_NAME" id="AGREE_NAME"
						value="${trainAgreementInfo.AGREE_NAME }"></td>
				</tr>

				<!-- <tr>
		<td class="td_title" width="1%">请输入名字/工号:</td>
		<td class="td_type"  width="20%">
		<input type="text"  name="peixun" id="peixun" value="">
		<a class="buttonActive" href="/edu/traineducation/queryPeixun" id="souPeixun" onclick="peixunchazhao()" rel="souPeixun" target="dialog" mask="true" width="800" height="600" >
		<span>查找</span></a>
		</td>
		</tr> -->

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.XIEYIRENSHEHAO.a" />
						<!--协议人社号--></td>
					<td class="td_type" width="20%"><span id="xieyirenempid">${trainAgreementInfo.EMPID }</span>
						<input type="hidden" name="EMPID" id="EMPID"
						value="${trainAgreementInfo.EMPID }"></td>
				</tr>

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.XIEYIRENXINGMING.a" />
						<!--协议人姓名--></td>
					<td class="td_type" width="20%"><span id="xieyirenname">${trainAgreementInfo.LOCAL_NAME }</span>
						<input type="hidden" id="LOCAL_NAME" name="LOCAL_NAME"
						value="${trainAgreementInfo.LOCAL_NAME }"></td>
				</tr>

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE" />
						<!--合同开始日期--></td>
					<td class="td_type" width="20%"><input type="text"
						name="CON_START_DATE" id="CON_START_DATE"
						value="${trainAgreementInfo.CON_START_DATE }" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"></td>
				</tr>

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="zxc.hr.contract.CONTRACT_END_DATE" />
						<!--合同结束日期--></td>
					<td class="td_type" width="20%"><input type="text"
						name="CON_END_DATE" id="CON_END_DATE"
						value="${trainAgreementInfo.CON_END_DATE }" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"></td>
				</tr>

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.YANXIUKAISHIRI.a" />
						<!--研修开始日--></td>
					<td class="td_type" width="20%"><input type="text"
						name="STUDY_START_DATE" id="STUDY_START_DATE"
						value="${trainAgreementInfo.STUDY_START_DATE }"
						onchange="yanxiuTime()" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"></td>
				</tr>

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.YANXIUJIESHURI.a" />
						<!--研修结束日--></td>
					<td class="td_type" width="20%"><input type="text"
						name="STUDY_END_DATE" id="STUDY_END_DATE"
						value="${trainAgreementInfo.STUDY_END_DATE }"
						onchange="yanxiuTime()" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"></td>
				</tr>

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.YANXIUTIANSHU.a" />
						<!--研修天数--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" name="STUDY_DAY" id="STUDY_DAY"
						value="${trainAgreementInfo.STUDY_DAY }" min="0">
					<spring:message code="ar.viewitemparameter.title.dayofunit" />
						<!--天--></td>
				</tr>

				<%-- <tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.FUWUKAISHIRIQI.a" />
						<!--服务开始日期--></td>
					<td class="td_type" width="20%"><input type="text"
						name="SER_START_DATE" id="SER_START_DATE"
						value="${trainAgreementInfo.SER_START_DATE }" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"></td>
				</tr> --%>

				<%-- <tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.FUWUJIESHURIQI.a" />
						<!--服务结束日期--></td>
					<td class="td_type" width="20%"><input type="text"
						name="SER_END_DATE" id="SER_END_DATE"
						value="${trainAgreementInfo.SER_END_DATE }" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"></td>
				</tr>

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.FUWUTIANSHU.a" />
						<!--服务天数--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" name="SERVICE_YEAR" id="SERVICE_YEAR"
						value="${trainAgreementInfo.SERVICE_YEAR }" min="0">
					<spring:message code="inct.salesman.year" />
						<!--年--></td>
				</tr>

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.DANGYUEHUILV.a" />
						<!--当月汇率--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" name="EXCHANGE_RATE" id="EXCHANGE_RATE"
						value="${trainAgreementInfo.EXCHANGE_RATE }" min="0">%</td>
				</tr> --%>

				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.HUQIANFEI.a" />
						<!--护签费--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="HQ_FREE" id="HQ_FREE"
						value="${trainAgreementInfo.HQ_FREE }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.CHUGUOFANGYIFEI.a" />
						<!--出国防疫费--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="CGFY_FREE" id="CGFY_FREE"
						value="${trainAgreementInfo.CGFY_FREE }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.JIPIAOFEI.a" />
						<!--机票费--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="JP_FREE" id="JP_FREE"
						value="${trainAgreementInfo.JP_FREE }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.ZHUFANGBUZHU.a" />
						<!--住房补助--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="ZFBZ_FREE" id="ZFBZ_FREE"
						value="${trainAgreementInfo.ZFBZ_FREE }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.CHUGUOBUZHU.a" />
						<!--出国补助--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="CGBZ_FREE" id="CGBZ_FREE"
						value="${trainAgreementInfo.CGBZ_FREE }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.CHUGUOBUZHUSHIJI.a" />
						<!--出国补助实际--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="CGBZ_FREE_FACT" id="CGBZ_FREE_FACT"
						value="${trainAgreementInfo.CGBZ_FREE_FACT }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.SHANGYEBAOXIANFEI.a" />
						<!--商业保险费--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="SYBX_FREE" id="SYBX_FREE"
						value="${trainAgreementInfo.SYBX_FREE }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.YANXIUGONGZI.a" />
						<!--研修工资--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="YX_PAY" id="YX_PAY"
						value="${trainAgreementInfo.YX_PAY }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.JIAOTONGFEI.a" />
						<!--交通费--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="JT_FREE" id="JT_FREE"
						value="${trainAgreementInfo.JT_FREE }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.TONGXINFEI.a" />
						<!--通信费--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" onchange="weiyuejinMethod()" name="TX_FREE" id="TX_FREE"
						value="${trainAgreementInfo.TX_FREE }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<%-- <tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.XIEYIZONGFEIYONG.a" />
						<!--协议总费用--></td>
					<td class="td_type" width="20%"><input type="text"
						style="width: 80px;" name="TOTAL_FEE" id="TOTAL_FEE"
						value="${trainAgreementInfo.TOTAL_FEE }" min="0">
					<spring:message code="edu.trainAgreement.YUAN.a" />
						<!--元--></td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<tr>
						<td class="td_title" width="1%"><spring:message
								code="edu.trainAgreement.PEIXUNFEIYONG.a" />
							<!--培训费用--></td>
						<td class="td_type" width="20%"><input type="text"
							style="width: 80px;" name="TRAINING_FEE" id="TRAINING_FEE"
							value="${trainAgreementInfo.TRAINING_FEE }" min="0">
						<spring:message code="edu.trainAgreement.YUAN.a" />
							<!--元--></td>
					</tr>
				</c:if>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="display.pa.ecc.expectresigndate" />
						<!--预离职日期--></td>
					<td class="td_type" width="20%"><input type="text"
						name="LEFT_DATE" id="LEFT_DATE"
						value="${trainAgreementInfo.LEFT_DATE }"
						onchange="weiyuejinMethod()" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"></td>
				</tr> --%>
				<tr>
				<td class="td_title" width="1%"><spring:message
						code="edu.trainAgreement.SHIJIZHIFU.a" />
					<!--实际支付--></td>
				<td class="td_type" width="20%"><input type="text"
					style="width: 80px;" name="FACT_PAY" id="FACT_PAY"
					value="${trainAgreementInfo.FACT_PAY }" min="0">
				<spring:message code="edu.trainAgreement.YUAN.a" />
					<!--元--></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.XIEYIQIANDINGRIQI.a" />
						<!--协议签订日期--></td>
					<td class="td_type" width="20%"><input type="text"
						name="AGREE_START_DATE" id="AGREE_START_DATE"
						value="${trainAgreementInfo.AGREE_START_DATE }" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="edu.trainAgreement.XIEYIJIECHURIQI.a" />
						<!--协议解除日期--></td>
					<td class="td_type" width="20%"><input type="text"
						name="AGREE_END_DATE" id="AGREE_END_DATE"
						value="${trainAgreementInfo.AGREE_END_DATE }" class="Wdate"
						onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"></td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message
							code="org.title.Attached_File" />
						<!--附加文件--></td>
					<td class="td_type" width="20%"><a class="w_button" href="#"
						onclick="uploadAttDialog_new('trainAgreementInfo','/edu/traineducation/trainAgreementInfo?PERSON_ID=${PERSON_ID}$AGREE_NO=${trainAgreementInfo.AGREE_NO }','${trainAgreementInfo.AGREE_NO }','eduTrainAgreement','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message
									code="ess.empInfo.insert" />
								<!--添加--></span></a> <a class="w_button" href="#"
						onclick="deleteAttListAgree('trainAgreementInfo','/edu/traineducation/trainAgreementInfo?PERSON_ID=${PERSON_ID}&AGREE_NO=${trainAgreementInfo.AGREE_NO }',divAjaxDone)"><span><spring:message
									code="ess.empInfo.Delete" />
								<!--删除--></span></a> <c:forEach items="${trainAgreementInfo.fileList}"
							var="item" varStatus="i">
							<input type="checkbox" name="FILE_NO" value="${item.FILE_NO}" />
							<a
								href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>
						</c:forEach></td>

				</tr>

			</table>

		</div>

		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">	<!-- 提交 -->	<spring:message code="public.title.submit" /></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
