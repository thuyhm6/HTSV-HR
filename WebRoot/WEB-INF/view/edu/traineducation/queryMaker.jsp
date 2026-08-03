<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
var a="";
var b="";
var c="";
var d="";
var e="";
var allnum=0;
var changecount=0;
$(function (){
	changecount=$('#changecount').val();
	if("${queryMakerListCount}"=="1"){
		$('#listJueCai_1').click();
		quedingJueCai();
	}
	
});

function xuanzhongJueCai(no,local_name,empid,person_id,deptname,position_no_name,language){
	var count="${queryMakerListCount}";
	for(var i=1;i<=count;i++){
		$('#listJueCai_'+i).attr('style','');
	}
	$('#listJueCai_'+no).attr('style','background:#aaccf6');
	a=local_name;
	b=empid;
	c=person_id;
	d=deptname;
	e=position_no_name;
	l=language;
}
function quedingJueCai(){
	if(a!=''){
		if (l == 'vi') {
			var str='<tr id="maker_'+(parseInt(changecount)+1)+'"><td class="td_title" width="2%" name="makerlevel" style="text-align: center;"><spring:message code="edu.courseApply.JIJUECAIZHE.a"/>&nbsp'+(parseInt(changecount)+1)+'</td>'//级决裁者
	        +'<td class="td_type" width="4%" style="text-align: center;">'+b+'</td>'
			+'<td class="td_type" width="4%" style="text-align: center;" name="local_name">'+a+'</td>'
			+'<td class="td_type" width="4%" style="text-align: center;">'+d+'</td>'
			+'<td class="td_type" width="4%" style="text-align: center;">'+e+'</td>'
			+'<td class="td_type" width="4%" id="delete_'+(parseInt(changecount)+1)+'"><a style="margin-left:150px;" class="buttonActive" href="#" onclick="deleteMaker('+(parseInt(changecount)+1)+')"><span><spring:message code="button.delete"/></span></a></td>'//删除
			+'<td class="td_type" width="4%" id="delete_second_'+(parseInt(changecount)+1)+'" style="display:none"></td>'
			+'<input type="hidden" name="person_id" value="'+c+'"></tr>';
		} else {
			var str='<tr id="maker_'+(parseInt(changecount)+1)+'"><td class="td_title" width="2%" name="makerlevel" style="text-align: center;">'+(parseInt(changecount)+1)+'<spring:message code="edu.courseApply.JIJUECAIZHE.a"/>&nbsp</td>'//级决裁者
	        +'<td class="td_type" width="4%" style="text-align: center;">'+b+'</td>'
			+'<td class="td_type" width="4%" style="text-align: center;" name="local_name">'+a+'</td>'
			+'<td class="td_type" width="4%" style="text-align: center;">'+d+'</td>'
			+'<td class="td_type" width="4%" style="text-align: center;">'+e+'</td>'
			+'<td class="td_type" width="4%" id="delete_'+(parseInt(changecount)+1)+'"><a style="margin-left:150px;" class="buttonActive" href="#" onclick="deleteMaker('+(parseInt(changecount)+1)+')"><span><spring:message code="button.delete"/></span></a></td>'//删除
			+'<td class="td_type" width="4%" id="delete_second_'+(parseInt(changecount)+1)+'" style="display:none"></td>'
			+'<input type="hidden" name="person_id" value="'+c+'"></tr>';
		}
	 
		$('#addMaker').append(str);
		$('#addjuecai').attr('style','display:none');
		$('#makername').attr('value','');
		$('#changecount').attr('value',parseInt(changecount)+1);
		$('#delete_'+parseInt(changecount)).attr('style','display:none');
		$('#delete_second_'+parseInt(changecount)).attr('style','');
		
		$.pdialog.closeCurrent();
	}else{
		alert("<spring:message code='edu.teacherManager.QINGXIANXUANZEYIGEREN.a'/>");//请先选择一个人!
	}
}
function traningQuery(){
	var empidname=$('#des_empid_name').val();
	var deptno=$('#des_Deptno').val();
	$('#des_sousuo').attr('href','/edu/traineducation/queryMaker?empidname='+empidname+'&DEPTNO='+deptno);
}
function quxiaoJueCai(){
	$.pdialog.closeCurrent();
}
</script>
<div>
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
<div class="subBar">
	  <ul style="padding-left: 550px;">
		<li>
			<a class="buttonActive" id="des_sousuo" href="#" onclick="traningQuery()" rel="juecai" target="dialog" mask="true" width="800" height="400">
				<span><spring:message code="display.paecc.sousuo"/><!--搜索--></span>
			</a>
		</li>
		<li>
			<a class="buttonActive" id="quedingJueCai" href="#" onclick="quedingJueCai()">
				<span><spring:message code="hrm.empinfo.CONFIRM"/><!--确定--></span>
			</a>
		</li>
		<li>
			<a class="buttonActive"  href="#" onclick="quxiaoJueCai()">
				<span><spring:message code="hrm.empinfo.CANCLE"/><!--取消--></span>
			</a>
		</li>
	</ul>
	</div>
</div>

<div class="pageContent" layoutH="10">
                 
   <span style="font-size: 15px;">Total:${queryMakerListCount }</span>
		<table class="list" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<thead>
		<tr>
		<td class="td_title" width="1%">NO.</td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.EMPID"/><!--社号--></td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.NAME"/><!--姓名--></td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.DEPT"/><!--部门--></td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.Rank"/><!--职级--></td>
		<td class="td_title" width="5%"><spring:message code="org.title.POSITION_NO"/><!--职责--></td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${queryMakerList}" var="t" varStatus="i">
		<tr id="listJueCai_${i.count }" onclick="xuanzhongJueCai('${i.count }','${t.LOCAL_NAME }','${t.EMPID }','${t.PERSON_ID }','${t.DEPTNAME }','${t.POSITION_NO_NAME }','${LoginUser.language }')">
		<td class="td_type"  width="1%" style="text-align: center;">${i.count }</td>
		<td class="td_type"  width="5%" style="text-align: center;">${t.EMPID }</td>
		<td class="td_type"  width="5%" style="text-align: center;">${t.LOCAL_NAME }</td>
		<td class="td_type"  width="5%" style="text-align: center;">${t.DEPTNAME }</td>
		<td class="td_type"  width="5%" style="text-align: center;">${t.POST_GRADE_NO_NAME }</td>
		<td class="td_type"  width="5%" style="text-align: center;">${t.POSITION_NO_NAME }</td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
</div>
