<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
var a="";
var b="";
var c="";
var d="";
var e="";
$(function(){
	if("${queryTeacherListCount}"=="1"){
		$('#listQuerTea_1').click();
		quedingTea();
	}
});
function xuanzhongQuerTea(no,local_name,empid,deptnoname,post_grade_no_name,position_no_name,person_id){
	var count="${queryTeacherListCount}";
	for(var i=1;i<=count;i++){
		$('#listQuerTea_'+i).attr('style','');
	}
	$('#listQuerTea_'+no).attr('style','background:#aaccf6');
	a=local_name;
	b=empid;
	c=deptnoname;
	d=post_grade_no_name;
	e=position_no_name;
	f=person_id;
}
function quedingTea(){
	if(a!=''&&b!=''&&f!=''){
		$('#teach_local_name').html(a);
		$('#TEACHER_NAME').attr('value',a);
		$('#teach_empid').html(b);
		$('#EMPID').attr('value',b);
		$('#teach_deptno').html(c);
		$('#DEPTNO').attr('value',c);
		$('#teach_post_grade_no').html(d);
		$('#POST_GRADE_NO').attr('value',d);
		$('#teach_position_no').html(e);
		$('#POSITION_NO').attr('value',e);
		$('#PERSON_ID').attr('value',f);
		$.pdialog.closeCurrent();
	}else{
		alert("<spring:message code='edu.teacherManager.QINGXIANXUANZEYIGEREN.a'/>");//请先选择一个人!
	}
	
}
function quxiaoTea(){
	$.pdialog.closeCurrent();
}
</script>
<div class="pageContent" layoutH="10">
                 <ul style="padding-left: 470px;">
					<li>
						<a class="buttonActive" id="quedingTea" href="#" onclick="quedingTea()">
							<span><spring:message code="hrm.empinfo.CONFIRM"/><!--确定--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"  href="#" onclick="quxiaoTea()">
							<span><spring:message code="hrm.empinfo.CANCLE"/><!--取消
			--></span>
						</a>
					</li>
				</ul>
   <span style="font-size: 15px;">Total:${queryTeacherListCount }</span>
		<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="1%">NO.</td>
		<td class="td_title" width="5%"><spring:message code="alert.pa.pasalarycanshu.shehao"/><!--社号--></td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.NAME"/><!--姓名--></td>
		<td class="td_title" width="5%"><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门--></td>
		<td class="td_title" width="5%"><spring:message code="ess.trans.title.postGradeName"/><!--职级--></td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.title.dutyName"/><!--职责--></td>
		</tr>
		<c:forEach items="${queryTeacherList}" var="t" varStatus="i">
		<tr id="listQuerTea_${i.count }" onclick="xuanzhongQuerTea('${i.count }','${t.LOCAL_NAME }','${t.EMPID }','${t.ORG_NAME_LOCAL }','${t.POST_GRADE_NO_NAME }','${t.POSITION_NO_NAME }','${t.PERSON_ID }')">
		<td class="td_type"  width="1%" style="text-align: center;">${i.count }</td>
		<td class="td_type"  width="5%" >${t.EMPID }</td>
		<td class="td_type"  width="5%" >${t.LOCAL_NAME }</td>
		<td class="td_type"  width="5%" >${t.ORG_NAME_LOCAL }</td>
		<td class="td_type"  width="5%" >${t.POST_GRADE_NO_NAME }</td>
		<td class="td_type"  width="5%" >${t.POSITION_NO_NAME }</td>
		</tr>
		</c:forEach>
		</table>
		<div class="subBar" style="padding-left: 650px;">
		
				</div>
</div>
