<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
var a="";
var b="";
var c="";
function xuanzhongPeiXun(no,local_name,empid,person_id){
	var count="${queryPeixunListCount}";
	for(var i=1;i<=count;i++){
		$('#listPeiXun_'+i).attr('style','');
	}
	$('#listPeiXun_'+no).attr('style','background:#aaccf6');
	a=local_name;
	b=empid;
	c=person_id;
}
function quedingPeixun(){
	if(a!=''&&b!=''&&c!=''){
		$('#xieyirenname').html(a);
		$('#LOCAL_NAME').attr('value',a);
		$('#xieyirenempid').html(b);
		$('#EMPID').attr('value',b);
		$('#PERSON_ID').attr('value',c);
		$.pdialog.closeCurrent();
	}else{
		alert("<spring:message code='edu.teacherManager.QINGXIANXUANZEYIGEREN.a'/>");//请先选择一个人!
	}
}
function traningQuery(){
	var empidname=$('#des_empid_name').val();
	var deptno=$('#des_Deptno').val();
	$('#des_sousuo').attr('href','/edu/traineducation/queryPeixun?empidname='+empidname+'&DEPTNO='+deptno);
}

function quxiaoPeixun(){
	$.pdialog.closeCurrent();
}
</script>
	<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
	  <td class="td_title" width="10%"><spring:message code="edu.planManager.GONGHAOXINGMING.a"/><!--工号/姓名--></td>
	  <td class="td_type"  width="20%" ><input type="text" name="des_empid_name" id="des_empid_name"  value="${empidname}"></td>
	  <td class="td_title" width="10%"><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门--></td>
	  <td class="td_type"  width="20%" >
	     <ait:deptList name="DESDEPTNO" cpnyId="${defaultCpny}" limit="super" id="desemployeeDeptno" selected="${DEPTNO}"/>
	       <input type="hidden" id="des_Deptno" value="" syslong="desemployeeDeptno">
	     <ait:deptTreeIcon name="DESDEPTNO" cpnyId="${defaultCpny}" limit="super" id="desemployeeDeptno" selected="${DEPTNO}"/>
	  </td> 
	</table>
<div class="pageContent" layoutH="10">
                 <ul style="padding-left: 420px;">
                     <li >
						<a class="buttonActive" id="des_sousuo" href="#" onclick="traningQuery()" rel="souPeixun" target="dialog" mask="true" width="600" height="400">
							<span><spring:message code="display.paecc.sousuo"/><!--搜索--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" id="quedingPeixun" href="#" onclick="quedingPeixun()">
							<span><spring:message code="hrm.empinfo.CONFIRM"/><!--确定--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"  href="#" onclick="quxiaoPeixun()">
							<span><spring:message code="hrm.empinfo.CANCLE"/><!--取消--></span>
						</a>
					</li>
				</ul>
   
		<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<span style="font-size: 15px;">Total:${queryPeixunListCount }</span>
		<tr>
		<td class="td_title" width="1%">NO.</td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.EMPID"/><!--社号--></td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.NAME"/><!--姓名--></td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.Rank"/><!--职级--></td>
		<td class="td_title" width="5%"><spring:message code="org.title.POSITION_NO"/><!--职责--></td>
		</tr>
		<c:forEach items="${queryPeixunList}" var="t" varStatus="i">
		<tr id="listPeiXun_${i.count }" onclick="xuanzhongPeiXun('${i.count }','${t.LOCAL_NAME }','${t.EMPID }','${t.PERSON_ID }')">
		<td class="td_type"  width="1%" style="text-align: center;">${i.count }</td>
		<td class="td_type"  width="5%" >${t.EMPID }</td>
		<td class="td_type"  width="5%" >${t.LOCAL_NAME }</td>
		<td class="td_type"  width="5%" >${t.POST_GRADE_NO_NAME }</td>
		<td class="td_type"  width="5%" >${t.POSITION_NO_NAME }</td>
		</tr>
		</c:forEach>
		</table>
</div>
