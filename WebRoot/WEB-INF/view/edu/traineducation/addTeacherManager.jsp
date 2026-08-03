<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function chazhao(){
	var name=$('#teach_search').val();
	$('#souTeacher').attr('href','/edu/traineducation/queryTeacher?LOCAL_NAME='+name);
}
function chooseNeiWai(a){
	if(a=='nei'){
		$('#shenei').attr('style','');
		$('#shewai').attr('style','display:none');
	}else{
		$('#shenei').attr('style','display:none');
		$('#shewai').attr('style','');
	}
	
}
function copeName(){
	var name=$('#shewainame').val();
	$('#teach_local_name').html(name);
	if(name!=''){
		$('#TEACHER_NAME').attr('value',name);
	}
	
}

document.onkeydown=function(){
	if(event.keyCode==13){
		chazhao();
		$('#souTeacher').click();
	}
};
function addTeacherManagerTijiao(){
	$('#addTeacherManagerInfo').submit();
}
</script>
<div class="pageContent" layoutH="10">
	<form id="addTeacherManagerInfo" method="post" action="/edu/traineducation/addTeacherManagerInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		<table  class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<input type="hidden" class="required" name="PERSON_ID" id="PERSON_ID" value="">
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.QINGSHURUXINGMING.a"/><!--请输入姓名:
			--></td>
		<td class="td_type"  width="20%">
		<div id="shenei" style="">
		<input type="text" name="teach_search" id="teach_search" value="">
		<a class="buttonActive" href="/edu/traineducation/queryTeacher" id="souTeacher" onclick="chazhao()" rel="souTeacher" target="dialog" mask="true" width="600" height="400" >
		<span><spring:message code="edu.teacherManager.CHAZHAO.a"/><!--查找
			--></span></a>
		</div>
		<div id="shewai" style="display:none">
		<input type="text" name="shewainame" id="shewainame" value="" class="required" onChange="copeName()">
		</div>
		<input type="radio" name="fenlei"  style="margin-top:6px;margin-left:10px;" checked="checked" onclick="chooseNeiWai('nei')"><spring:message code="edu.teacherManager.SHENEIRENYUAN.a"/><!--社内人员
			-->
		<input type="radio" name="fenlei"  onclick="chooseNeiWai('wai')"><spring:message code="edu.teacherManager.SHEWAIRENYUAN.a"/><!--社外人员
			-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="empsubject.tcrNm"/><!--讲师姓名
			--></td>
		<td class="td_type"  width="20%" >
		<span id="teach_local_name" class="required"></span>
		<input type="hidden" name="TEACHER_NAME" id="TEACHER_NAME" value="">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="alert.pa.pasalarycanshu.shehao"/><!--社号
			--></td>
		<td class="td_type"  width="20%" >
		 <span id="teach_empid"></span>
		 <input type="hidden" name="EMPID" id="EMPID" value="">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门
			--></td>
		<td class="td_type"  width="20%" >
		 <span id="teach_deptno"></span>
		 <input type="hidden" name="DEPTNO" id="DEPTNO" value="">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="ess.trans.title.postGradeName"/><!--职级
			--></td>
		<td class="td_type"  width="20%" >
		 <span id="teach_post_grade_no"></span>
		 <input type="hidden" name="POST_GRADE_NO" id="POST_GRADE_NO" value="">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="inct.salesman.position"/><!--职务
			--></td>
		<td class="td_type"  width="20%" >
		 <span id="teach_position_no"></span>
		 <input type="hidden" name="POSITION_NO" id="POSITION_NO" value="">
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.JIANGSHIJIBIE.a"/><!--讲师级别
			--></td>
		<td class="td_type"  width="20%" >
		 <ait:SelectSyCodeByCpnyID name="TEACH_LEVEL_CODE" id="TEACH_LEVEL_CODE"
                    parentNo="14015140" cnpyID="${defaultCpny}" selected="" limit="all"/>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.JIANGKELINGYU.a"/><!--讲课领域
			--></td>
		<td class="td_type"  width="20%" >
		 <ait:SelectSyCodeByCpnyID name="TEACH_FIELD_CODE" id="TEACH_FIELD_CODE"
                    parentNo="14015148" cnpyID="${defaultCpny}" selected="" limit="all"/>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.YEWUDANDANGSHIJIAN.a"/><!--业务担当时间
			--></td>
		<td class="td_type"  width="20%" >
		 <input type="text" style="width:60px;" name="BUSINESS_ACT_TIME_YEAR" id="BUSINESS_ACT_TIME_YEAR" value="" min="0">
		 <span style="float:left;margin-top:4px;"><spring:message code="inct.salesman.year"/><!--年
			--></span>
		 <input type="text" style="width:60px;" name="BUSINESS_ACT_TIME_MONTH" id="BUSINESS_ACT_TIME_MONTH" value="" min="1"><spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月
			-->
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.PINYONGSHIJIAN.a"/><!--聘用时间
			--></td>
		<td class="td_type"  width="20%" >
		 <input type="text" name="HIRE_TIME"  id="HIRE_TIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="" />
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.JIEPINSHIJIAN.a"/><!--解聘时间
			--></td>
		<td class="td_type"  width="20%" >
		 <input type="text" name="FIRING_TIME"  id="FIRING_TIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="" />
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="ar.attendanceView.viewNoSwipingCard.status"/><!--状态
			--></td>
		<td class="td_type"  width="20%" >
		 <ait:SelectSyCodeByCpnyID name="TEACH_STATUS_CODE" id="TEACH_STATUS_CODE"
                    parentNo="14015155" cnpyID="${defaultCpny}" selected="" limit="all"/>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="pa.salary.canShu.beiZhu"/><!--备注
			--></td>
		<td class="td_type"  width="20%" >
		 <textarea name="REMARK"  id="REMARK" style="width:300px;height:80px"></textarea>
		</td>
		</tr>	
		</table>
		
		</div>
		
		<div class="formBar">
			<ul>
				<li><a href="#" onclick="addTeacherManagerTijiao()"><div class="buttonActive"><div class="buttonContent"><spring:message code="public.title.submit"/></div></div></a></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
