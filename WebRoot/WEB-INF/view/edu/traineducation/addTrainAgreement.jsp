<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function peixunchazhao(){
	var empid_name=$('#peixun').val();
	$('#souPeixun').attr('href','/edu/traineducation/queryPeixun?empidname='+empid_name);
}
function yanxiuTime(){
	var start=$('#STUDY_START_DATE').val();
	var end=$('#STUDY_END_DATE').val();
	if(start!=''&&end!=''){
		var e=parseInt(end.replace('/','').replace('/',''));
		var s=parseInt(start.replace('/','').replace('/',''));
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
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是18-12-2006格式    
    var  aDate,  oDate1,  oDate2,  iDays;    
    aDate  =  sDate1.split("/");    
    oDate1  =  new  Date(aDate[2]  +  '/'  +  aDate[1]  +  '/'  +  aDate[0]);    //转换为12-18-2006格式    
    aDate  =  sDate2.split("/");    
    oDate2  =  new  Date(aDate[2]  +  '/'  +  aDate[1]  +  '/'  +  aDate[0]);    
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
  
</script>
<div class="pageContent" layoutH="10">
	<form method="post" action="/edu/traineducation/addTrainAgreementInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		<table  class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<input type="hidden" name="PERSON_ID" id="PERSON_ID" value="">
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.XIEYIMINGCHENG.a"/><!--协议名称--></td>
		<td class="td_type"  width="20%">
		<input type="text" class="required" name="AGREE_NAME" id="AGREE_NAME" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.QINGSHURUXINGMINGGONGHAO.a"/><!--请输入名字/工号:--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="peixun" id="peixun" value="">
		<a class="buttonActive" href="/edu/traineducation/queryPeixun" id="souPeixun" onclick="peixunchazhao()" rel="souPeixun" target="dialog" mask="true" width="600" height="400" >
		<span><spring:message code="edu.teacherManager.CHAZHAO.a"/><!--查找--></span></a>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.XIEYIRENSHEHAO.a"/><!--协议人社号--></td>
		<td class="td_type"  width="20%">
		<span id="xieyirenempid"></span>
		<input type="hidden"  name="EMPID" id="EMPID" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.XIEYIRENXINGMING.a"/><!--协议人姓名--></td>
		<td class="td_type"  width="20%">
		<span id="xieyirenname"></span>
		<input type="hidden" id="LOCAL_NAME" name="LOCAL_NAME" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE"/><!--合同开始日期--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="CON_START_DATE" id="CON_START_DATE" value="" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/><!--合同结束日期--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="CON_END_DATE" id="CON_END_DATE" value="" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.YANXIUKAISHIRI.a"/><!--研修开始日--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="STUDY_START_DATE" id="STUDY_START_DATE" value="" onchange="yanxiuTime()" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.YANXIUJIESHURI.a"/><!--研修结束日--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="STUDY_END_DATE" id="STUDY_END_DATE" value="" onchange="yanxiuTime()" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.YANXIUTIANSHU.a"/><!--研修天数--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;"  name="STUDY_DAY" id="STUDY_DAY" value="" min="0"><spring:message code="ar.viewitemparameter.title.dayofunit"/><!--天-->
		</td>
		</tr>
		
		<%-- <tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.FUWUKAISHIRIQI.a"/><!--服务开始日期--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="SER_START_DATE" id="SER_START_DATE" value="" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})">
		</td>
		</tr> --%>
		
		<%-- <tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.FUWUJIESHURIQI.a"/><!--服务结束日期--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="SER_END_DATE" id="SER_END_DATE" value="" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})">
		</td>
		</tr> --%>
		
		<%-- <tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.FUWUTIANSHU.a"/><!--服务天数--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;"  name="SERVICE_YEAR" id="SERVICE_YEAR" value="" min="0"><spring:message code="inct.salesman.year"/><!--年-->
		</td>
		</tr> --%>
		
		<%-- <tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.DANGYUEHUILV.a"/><!--当月汇率--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;"  name="EXCHANGE_RATE" id="EXCHANGE_RATE" value="" min="0">%
		</td>
		</tr> --%>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.HUQIANFEI.a"/><!--护签费--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="HQ_FREE" id="HQ_FREE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.CHUGUOFANGYIFEI.a"/><!--出国防疫费--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="CGFY_FREE" id="CGFY_FREE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.JIPIAOFEI.a"/><!--机票费--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="JP_FREE" id="JP_FREE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.ZHUFANGBUZHU.a"/><!--住房补助--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="ZFBZ_FREE" id="ZFBZ_FREE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.CHUGUOBUZHU.a"/><!--出国补助--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="CGBZ_FREE" id="CGBZ_FREE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.CHUGUOBUZHUSHIJI.a"/><!--出国补助实际--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="CGBZ_FREE_FACT" id="CGBZ_FREE_FACT" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.SHANGYEBAOXIANFEI.a"/><!--商业保险费--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="SYBX_FREE" id="SYBX_FREE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.YANXIUGONGZI.a"/><!--研修工资--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="YX_PAY" id="YX_PAY" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.JIAOTONGFEI.a"/><!--交通费--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="JT_FREE" id="JT_FREE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.TONGXINFEI.a"/><!--通信费--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;" onchange="weiyuejinMethod()" name="TX_FREE" id="TX_FREE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<%-- <tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.XIEYIZONGFEIYONG.a"/><!--协议总费用--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;"  name="TOTAL_FEE" id="TOTAL_FEE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr> --%>
		<%-- <c:if test="${LoginUser.cpnyId eq 'HAE'}">
		   <tr>
			<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.PEIXUNFEIYONG.a"/><!--培训费用--></td>
			<td class="td_type"  width="20%">
			<input type="text" style="width:80px;"  name="TRAINING_FEE" id="TRAINING_FEE" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
			</td>
		</tr>
		</c:if> --%>
		<%-- <tr>
		<td class="td_title" width="1%"><spring:message code="display.pa.ecc.expectresigndate"/><!--预离职日期--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="LEFT_DATE" id="LEFT_DATE" value="" onchange="weiyuejinMethod()" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})">
		</td>
		</tr> --%>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.SHIJIZHIFU.a"/><!--实际支付--></td>
		<td class="td_type"  width="20%">
		<input type="text" style="width:80px;"  name="FACT_PAY" id="FACT_PAY" value="" min="0"><spring:message code="edu.trainAgreement.YUAN.a"/><!--元-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.XIEYIQIANDINGRIQI.a"/><!--协议签订日期--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="AGREE_START_DATE" id="AGREE_START_DATE" value="" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainAgreement.XIEYIJIECHURIQI.a"/><!--协议解除日期--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="AGREE_END_DATE" id="AGREE_END_DATE" value="" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="ar.viewarcardrecord.title.beizhu"/><!--备注--></td>
	    <td class="td_type" width="20%">
				<textarea id="REMARK" name="REMARK" style="width:300px;height:80px"></textarea>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="hr.viewBadArchives.title.FILE"/><!--附件--></td>
		<td class="td_type"  width="20%">
		<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message code="hrm.empinfo.upload"/><!--上传--></span></a>
		<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');"><span><spring:message code="button.delete"/><!--删除--></span></a>
		<table id="fileTable" class="list" width="100%">
						<thead>
						</thead>
						<tbody>
						</tbody>
					</table>
		</td>
		</tr>
		</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
