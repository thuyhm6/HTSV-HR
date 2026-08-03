<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	
function addJuecai(){
    var encodeURImakername = $('#makername').val();
	var makername=encodeURI(encodeURImakername);
	 $('#juecai').attr('href','/edu/traineducation/queryMaker?makername='+makername);
}
document.onkeydown=function(){
	if(event.keyCode==13){
		addJuecai();
		$('#juecai').click();
	}
}
function xinzengjuecaizhe(){
	$('#addjuecai').attr('style','');
}
$('#applyAllCheck').click(function(){  
    $('input[name="applyCheck"]').prop("checked",this.checked);  
});
function xuanzhongApp(no){
	var count="${courseApplyListCount}";
	for(var i=1;i<=count;i++){
		$('#listApp_'+i).attr('style','');
	}
	$('#listApp_'+no).attr('style','background:#aaccf6');
	var checkno=$('#applyCheck_'+no).prop('checked');
	if(checkno==true){
		$('#applyCheck_'+no).removeAttr('checked','checked');
	}else{
		$('#applyCheck_'+no).attr('checked','checked');
	}
	
}
function applyshenqing(){
	//遍历学生对应课程的信息
	var arraybasicno="";
	var arraytask="";
	$('#applyTable input[name=applyCheck]').each(function(){
		  var checkno=$(this).prop('checked');
		   if(checkno==true){
			   arraybasicno=arraybasicno+$(this).val()+",";
			   var encodeURIapplytask = $('#APPLY_TASK_'+$(this).val()).val();
			   var applytask=encodeURI(encodeURIapplytask);
			   if(applytask!=''){
				   arraytask=arraytask+applytask+";";
			   }
		   }
	});
	arraybasicno=arraybasicno.substring(0,arraybasicno.length-1);
	if(arraytask!=''){
		arraytask=arraytask.substring(0,arraytask.length-1);
	}
	//遍历决裁者的信息
	var arraylocalname="";
	var arraypersonid="";
	$('#addMaker td[name=local_name]').each(function(){
		arraylocalname=arraylocalname+$(this).html()+",";
	});
	$('#addMaker input[name=person_id]').each(function(){
		arraypersonid=arraypersonid+$(this).val()+",";
	});
	arraylocalname=arraylocalname.substring(0,arraylocalname.length-1);
	arraypersonid=arraypersonid.substring(0,arraypersonid.length-1);
	//遍历决裁者等级信息
	var arraymakerlevel="";
	$('#addMaker td[name=makerlevel]').each(function(){
		var ml=$(this).html();
		    ml=ml.replace(/[^0-9]/ig,"");
		arraymakerlevel=arraymakerlevel+ml+",";
	});
	arraymakerlevel=arraymakerlevel.substring(0,arraymakerlevel.length-1);
	if(arraybasicno!=''&&arraypersonid!=''){
		$('#shenqing').attr('href','/edu/traineducation/addCourseApply?basicno='+arraybasicno+'&arraylocalname='+encodeURI(encodeURI(arraylocalname))+'&arraypersonid='+arraypersonid+'&arraytask='+encodeURI(arraytask)+'&arraymakerlevel='+arraymakerlevel);
	}else{
		$('#shenqing').attr('href','/edu/traineducation/addCourseApply?{App}');
	}
}
function sousuoApp(){
	$('#courseApply').submit();
}
function deletechaxun(){
	$('#addjuecai').attr('style','display:none');
}
function deleteMaker(num){
	$('#maker_'+num).remove();
	var numcount=$('#changecount').val();
	$('#changecount').attr('value',parseInt(numcount)-1);
	$('#delete_'+(parseInt(numcount)-1)).attr('style','');
	$('#delete_second_'+(parseInt(numcount)-1)).attr('style','display:none');
}

$("#applyTable",navTab.getCurrentPanel()).dataTable({
	"bPaginate": true,    //分页
    "bAutoWidth":false,//表格宽度自动变化
    "bProcessing":true,
	"bLengthChange": true,  //按多少条记录显示下拉框
	"iDisplayLength": 50, //默认每页显示的记录数
	"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
 	"searching": true,//本地搜索
	"bSort": true,   //排序功能
	"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
     "orderClasses": false,
     "order":[],//初始化不用自动排序
     "scrollY": $(document.body).height() - 390,
     "scrollCollapse": false,
     "deferRender":true,
     "fixedColumns":false,
    "oLanguage": {//多语言配置
    	"sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
        "oPaginate": {
            "sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
            "sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
        }
    },
    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
    "buttons": [] 
});

function changeURL_courseApply_queryCourse(planNo){
	var href = "/edu/traineducation/queryCourseSyllabus?PLAN_NO=" + planNo;
	$.pdialog.open(href,"edu0401", "<spring:message code='edu.planManager.CHAKANKECHENGBIAO.a' />", {width:600,height:400,mask:true});//查看课程表
}

</script>
<form id="courseApply" onsubmit="return navTabSearch(this);" action="/edu/traineducation/courseApply" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center" width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type" width="4%">
		<input type="text" name="applyname" id="applyname" value="${applyname }"  >
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.START_DATE1"/><!--开始日期--></td>
		<td class="td_type" width="4%">
		<input name="applystartdate" id="applystartdate"  onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${applystartdate}" class="Wdate"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.END_DATE1"/><!--结束日期--></td>
		<td class="td_type" width="4%">
		<input name="appleenddate" id="appleenddate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${appleenddate}" class="Wdate"/>
		</td>
</table>
</div>
</div>
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 50px;">
	<ul>        
        <li>
			<a class="buttonActive" onclick="xinzengjuecaizhe()">
				<span><spring:message code="edu.courseApply.XINZENGJUECAIZHE.a"/><!--新增决裁者--></span>
			</a>
		</li>
	</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table class="user_table" width="100%" border="0" cellpadding="2" cellspacing="1" id="addMaker">
		<tr>
		<td class="td_title" width="2%" style="text-align: center;"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></td>
		<td class="td_title" width="4%" style="text-align: center;"><spring:message code="hr.enpinfo.title.EMP.EMPNUMBER"/><!--社号--></td>
		<td class="td_title" width="4%" style="text-align: center;"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></td>
		<td class="td_title" width="4%" style="text-align: center;"><spring:message code="edu.teacherManager.BUMEN.a"/><!--部门--></td>
		<td class="td_title" width="4%" style="text-align: center;"><spring:message code="org.title.POST_GRADE_NAME"/><!--职级--></td>
		<td class="td_title" width="4%" style="text-align: center;"><spring:message code="edu.courseApply.SHIFOUSHANCHU.a"/><!--是否删除--></td>
		</tr>
		<c:forEach items="${makerList}" var="s" varStatus="i">
		<tr>
		<td class="td_title" width="2%" style="text-align: center;" name="makerlevel">
		<c:choose>
		    <c:when test="${LoginUser.language eq 'vi' }">
		        <spring:message code="edu.courseApply.JIJUECAIZHE.a"/>&nbsp<!--级决裁者-->${i.count }
		    </c:when>
		    <c:otherwise>
		        ${i.count }&nbsp<spring:message code="edu.courseApply.JIJUECAIZHE.a"/><!--级决裁者-->
		    </c:otherwise>
		</c:choose>
		
		
		</td>
		<td class="td_title" width="4%" style="text-align: center;">${s.EMPID }</td>
		<td class="td_title" width="4%" style="text-align: center;" name="local_name">${s.LOCAL_NAME }</td>
		<td class="td_title" width="4%" style="text-align: center;">${s.DEPTNAME }</td>
		<td class="td_title" width="4%" style="text-align: center;">${s.POST_GRADE_NAME}</td>
		<td class="td_title" width="4%" style="text-align: center;"></td>
		<input type="hidden" name="person_id" value="${s.PERSON_ID }">
		
		</tr>
		</c:forEach>
		
	</table>
</div>
<input type="hidden" id="changecount" value="${makerListCount }">
<div style="width: 100%; padding-top: 30px;">
	<table class="user_table" width="37%" border="0" cellpadding="2"
		cellspacing="1">
		<tr id="addjuecai" style="display:none">
		<td class="td_title" width="4%"><spring:message code="edu.courseApply.XINZENGJUECAIZHE.a"/><!--新增决裁者--></td>
		<td class="td_type" width="5%">
		<input style="float:left;" type="text" name="makername" id="makername" value="">
		<a  style="float:left;" class="buttonActive" href="#" id="juecai" onclick="addJuecai()"  rel="juecai" target="dialog" mask="true" width="800" height="400" >
		<span><spring:message code="org.title.SELECT"/><!--查询--></span></a><a style="margin-left:15px;" class="buttonActive" href="#" onclick="deletechaxun()"><span><spring:message code="org.title.DELETE"/><!--删除--></span></a>
		</td>
		</tr>
	</table>
</div>
<div class="subBar" style="margin-top: 5px; float: right; padding-right: 10px;">
		<ul style = "width: 150px;">        
            <li>
				<a class="buttonActive" href="#" onclick="sousuoApp()" >
					<span><spring:message code="hrm.empinfo.SEARCH"/><!--搜索--></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" title="<spring:message code="edu.courseApply.QUEDINGSHIFOUSHENQING.a"/><!--确定是否申请?-->" id="shenqing" onclick="applyshenqing()" href="#" callback="doAjaxDoneWithForm" target="ajaxTodo">
					<span><spring:message code="ess.infoApply.title.apply"/><!--申请--></span>
				</a>
			</li>
			
		</ul>
</div>
<div style="width: 100%; padding-top: 20px;">
	<table class="list" id="applyTable"> 
	<thead>
		<tr >
		    <td  class="td_title" width="1%"><input type="checkbox" name="applyAllCheck" id="applyAllCheck" style="margin-top:7px;"></td>
		    <th  width="1%">NO.</th>
		    <th  width="5%"><spring:message code="edu.courseApply.SHENQINGZHESHEHAO.a"/><!--申请者社号--></th>
			<th  width="5%"><spring:message code="edu.courseApply.SHENQINGZHEXINGMING.a"/><!--申请者姓名--></th>
		    <th  width="5%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></th>
			<th  width="5%"><spring:message code="edu.trainArchives.KECHENGMINGCHENGQICI.a"/><!--课程名称(期次)--></th>
			<th  width="5%"><spring:message code="edu.trainBasicInformation.PEIXUNSHISHIQIJIAN.a"/><!--培训实施期间--></th>
			<th  width="5%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></th>
			<th  width="5%"><spring:message code="edu.planManager.KECHENGBIAO.a"/><!--课程表--></th>	
			<th  width="5%"><spring:message code="pa.salarycode.affirm.reason"/><!--申请事由--></th>
			<th  width="5%"><spring:message code="edu.courseApply.SHENQINGZHUANGTAI.a"/><!--申请状态--></th>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${courseApplyList}" var="s" varStatus="i">
		<tr target="App" id="listApp_${i.count }" onclick="xuanzhongApp('${i.count }')" >
		   <td class="td_type" width="1%" style="text-align:center;"><input type="checkbox" name="applyCheck" id="applyCheck_${i.count }" value="${s.BASIC_NO }" onclick="xuanzhongApp('${i.count }')"></td>
           <td class="td_type" width="1%">${i.count  }</td>
           <td class="td_type" width="5%">${adminEmpid }</td>
           <td class="td_type" width="5%">${adminLocalName }</td>
           <td class="td_type" width="5%">${s.TRAIN_TYPE_CODE_NAME }</td>
           <td class="td_type" width="5%">${s.COURSE_NAME_CODE }&nbsp&nbsp(<spring:message code="edu.planManager.QI.a"/><!--期-->&nbsp<spring:message code="ar.alert.message.excelimport.title.di"/>&nbsp<!--第-->${s.PERIOD_TIME })</td>
           <td class="td_type" width="5%">${s.IMPLE_START_DATE }~${s.IMPLE_END_DATE }</td>
           <td class="td_type" width="5%">${s.IMPLE_CLASS_HOUR }&nbsp
			<c:if test="${s.IMPLE_CLASS_UNIT eq '0' }"><spring:message code="display.mutual.month"/><!--月--></c:if>
			<c:if test="${s.IMPLE_CLASS_UNIT eq '1' }"><spring:message code="display.mutual.day"/><!--天--></c:if>
			<c:if test="${s.IMPLE_CLASS_UNIT eq '2' }"><spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></c:if>
           </td>
           <c:if test="${s.SYLLABUSCOUNT=='0' }">
           <td class="td_type" width="5%"></td>
           </c:if>
           <c:if test="${s.SYLLABUSCOUNT!='0' }">
           <td class="td_type" width="5%" style="text-align:center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL_courseApply_queryCourse(${s.PLAN_NO });'>
           <span style = "color:blue;"><spring:message code="edu.planManager.CHAKANKECHENGBIAO.a"/><!--查看课程表--></span>
           </td>
           </c:if>
           <td class="td_type" width="5%"><input type="text" name="APPLY_TASK" id="APPLY_TASK_${s.BASIC_NO }" value=""></td>
           <td class="td_type" width="5%">${s.alreadycountnum }</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
</div>

</div>
	
</form>