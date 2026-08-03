<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
 
	function  f_apply_confirm_two(a){
 			
		var flag = a;
 		var arMonth = document.form_viewarmonthcalculate_confirm.arYearConfirm.value + document.form_viewarmonthcalculate_confirm.arMonthConfirm.value;
		var STAT_NO = document.form_viewarmonthcalculate_confirm.STAT_NO_CONFIRM.value;
		var CPNY_ID = document.form_viewarmonthcalculate_confirm.CPNY_ID.value;
		var deptStr="";
		if(CPNY_ID == 'TSTO'){
	  
            $("input[name='isChecked_confirm']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    deptStr += $(this).val()+"!";
                }
            })
        if(deptStr.length == 0){    
		        alertMsg.error("请选择大区");
		        return ;
        }
       
        deptStr=deptStr.substring(0, deptStr.lastIndexOf('!'));
        }
       
		 
		$("#monthCalculate_confirm").hide();
			$.ajax( {
					type : 'post',
					cache : false,
					contentType : 'application/json',
					url : '/ar/attendanceMintenance/monthCalculateConfirm?arMonth=' + arMonth + '&STAT_NO='+STAT_NO+'&AR_DEPT_NO='+deptStr+'&flag='+flag,
					dataType : "json",
					
					success : function(responseStr) {
						$("#armonth_calculateResult_confirm").html(responseStr);
						 
						$("#monthCalculate_confirm").show();
					}
			});

 		
 	}
						 
 
</script>
<form name="form_viewarmonthcalculate_confirm" method="post" action="" id="form_viewarmonthcalculate_confirm">

	 
	<!-- 汇总计算 -->
	<div id='monthCalculate_confirm' title="<spring:message code='ar.viewararmonthcalculate.title.huizongjisuan'/>" style="padding-top: 10px;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" style="display:none" >
			<tr>
				<td align="left" style="font-family: 'Arial','simsun';
				font-size: 14px;color:#333333;font-weight: bold;
				background-image: url(/resources/images/title/top_1.gif);
				background-repeat: no-repeat;
				padding-right: 0px;
				padding-left: 18px;
				padding-top: 3px;
				padding-bottom: 2px;">
					<!-- 月考勤汇总 --><spring:message code="ar.viewararmonthcalculate.title.yuekaoqinhuizong"/></td>
			</tr>
		</table>
		<div class="formBar">
		
			 
			<ul class="toolBar">
				<li>
					<a onclick="f_apply_confirm_two(1)">
						<span><spring:message code="pa.salary.title.kaoqinconfirm"/><!-- 考勤确认 --></span>
					</a>
				</li>
			</ul>
			
			 <ul class="toolBar">
				<li>
					<a onclick="f_apply_confirm_two(2)">
						<span><spring:message code="pa.salary.title.onfirmCancel"/><!-- 取消确认 --></span>
					</a>
				</li>
			</ul>
		</div>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="lge_table">
			<tr>
				<td width="15%" class="td_title">
					<!-- 考勤月 --><spring:message code="ar.excelexport.title.armonth"/>:
				</td>
				<td class="td_type">
					<ait:date yearName="arYearConfirm" monthName="arMonthConfirm" />
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_title">
					<!-- 区间 --><spring:message code="ar.viewcycleparameter.title.qujian"/>:
				</td>
				<td class="td_type">
					<select class="combox" name="STAT_NO_CONFIRM">
						<c:forEach items="${statnoList}" var="list">
							<option value="${list.STAT_NO}">${list.STAT_NAME}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		    <input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${CPNY_ID }"/>
		   <c:if test="${CPNY_ID eq 'TSTO' }"> 
			<tr>
				<td width="15%" class="td_title">
					地域区分:
				</td>
				<td class="td_type">  
					    <c:forEach items="${deptList}" var="vlist" varStatus="i">
						     <input name="isChecked_confirm" id="isChecked_confirm_${vlist.DEPTNO}" value="${vlist.DEPTNO}"  type="checkbox"  />
							   ${vlist.DEPTNAME} 
							  <c:if test="${i.count % 5 == 0}">  
							    
							   </c:if>
					    </c:forEach>
				</td>
			</tr>
		  </c:if>	
			<tr>
				<td width="15%" class="td_title">
					<!-- 确认结果 --><spring:message code="ar.viewararmonthcalculate.title.querenjieguo"/>:
				</td>
				<td class="td_type">
					<div id="armonth_calculateResult_confirm"></div>
				</td>
			</tr>
		</table>
	</div>
</form>
