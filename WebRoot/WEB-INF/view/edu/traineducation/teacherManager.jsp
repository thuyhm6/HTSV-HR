<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	var quanTeano="";
	var quanTea="";
	function xiugaiTea(){
			if(quanTeano!=""){
				$('#updateTea').attr('href','/edu/traineducation/teacherManagerInfo?TEACHER_NO='+quanTeano);
		}else{
			alert("<spring:message code='edu.systemManager.QINGXUANZEQIZHONGYIXIANG.a'/>");//请选择其中一项!
		}
	}
	function xuanzhongTea(no,nono){
		var count="${teacherManagerListCount}";
		for(var i=1;i<=count;i++){
			$('#listTea_'+i).attr('style','');
		}
		$('#listTea_'+no).attr('style','background:#aaccf6');
		$('#updateTea').attr('href','/edu/traineducation/teacherManagerInfo?TEACHER_NO='+nono);
		$('#yinTea').attr('style','display:none');
		$('#xianTea').attr('style','');
		quanTea=no;
		quanTeano=nono;
	}
	function shanchuTea(){
		if(quanTeano!=""){
			$('#deleteTea').attr('href','/edu/traineducation/deleteTeacherManager?TEACHER_NO='+quanTeano);
	}else{
		$('#deleteTea').attr('href','/edu/traineducation/deleteTeacherManager?{Tea}');
	}
  }
	function sousuoTea(){
		$('#teacherManager').submit();
	}
	
	
	$("#viewInfoTable",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
	    "bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	    "bSort": true,   //关闭排序功能
	    "bInfo": true,   //不显示datatables的信息（底部的页数，条目数信息）
	    "bScrollInfinite":true,
	    "scrollY": true,
	    "scrollX": true,
	    "orderClasses": false,
	    "order":[],//初始化不用自动排序
	    "scrollY": $(document.body).height() - 270,
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
	    }
});	
	
</script>
<form id="teacherManager" onsubmit="return navTabSearch(this);" action="/edu/traineducation/teacherManager" method="post">
<div class="pageHeader" >
<div class="searchBar">
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
    <table class="user_table" style="text-align:center" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title" width="4%"><spring:message code="ess.infoApply.NAME_EMPID"/></td><!--姓名/社号
		--><td class="td_type" width="4%">
		<input type="text" name="TEACH_NAME_EMPID" id="TEACH_NAME_EMPID" value="${TEACH_NAME_EMPID }">
		</td>
		<td class="td_title" width="4%"><spring:message code="edu.teacherManager.JIANGKELINGYU.a"/></td><!--讲课领域
		--><td class="td_type" width="4%">
		<ait:SelectSyCodeByCpnyID name="TEACH_FIELD_CODE" id="TEACH_FIELD_CODE"
                    parentNo="14015148" cnpyID="${defaultCpny}" selected="${TEACH_FIELD_CODE }" limit="all"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="edu.teacherManager.JIANGSHIJIBIE.a"/></td><!--讲师级别
		--><td class="td_type" width="4%">
		<ait:SelectSyCodeByCpnyID name="TEACH_LEVEL_CODE" id="TEACH_LEVEL_CODE"
                    parentNo="14015140" cnpyID="${defaultCpny}" selected="${TEACH_LEVEL_CODE }" limit="all"/>
		</td>
		<td class="td_title" width="4%"><spring:message code="ar.attendanceView.viewNoSwipingCard.status"/></td><!--状态
		--><td class="td_type" width="4%">
		<ait:SelectSyCodeByCpnyID name="TEACH_STATUS_CODE" id="TEACH_STATUS_CODE"
                    parentNo="14015155" cnpyID="${defaultCpny}" selected="${TEACH_STATUS_CODE }" limit="all"/>
		</td>
		</tr>
	</table>
</div>
</div>
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
		<ul style="width: 250px;">
		            <li>
						<a class="buttonActive" href="#" onclick="sousuoTea()" width="800" height="250" >
							<span><spring:message code="ar.viewempcalender.title.search"/></span><!--搜索
						--></a>
					</li>
					<li>
						<a class="buttonActive" href="/edu/traineducation/addTeacherManager" target="dialog" mask="true" width="800" height="600" >
							<span><spring:message code="button.add"/></span><!--添加
						--></a>
					</li>
					<li id="yinTea" style="">
						<a class="buttonActive"  onclick="xiugaiTea()" href="#"  width="800" height="250">
							<span><spring:message code="button.update"/></span><!--修改
						--></a>
					</li>
					<li id="xianTea" style="display:none">
						<a class="buttonActive" id="updateTea" onclick="xiugaiTea()" href="#" target="dialog" mask="true"  width="800" height="600">
							<span><spring:message code="button.update"/></span><!--修改
						--></a>
					</li>
					<li>
						<a class="buttonActive" title="<spring:message code="edu.systemManager.QUEDINGSHIFOUSHANCHU.a"/>"   id="deleteTea" onclick="shanchuTea()" href="#" callback="doAjaxDoneWithForm" target="ajaxTodo">
							                                <!--确定是否删除?-->
							
							<span><spring:message code="button.delete"/></span><!--删除
						--></a>
					</li>
				</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table id="viewInfoTable" class="list" >
		<thead>
		<tr >
		    <th  width="1%">NO.</th>
		    <th  width="5%"><spring:message code="edu.teacherManager.JIANGSHISHEHAO.a"/></th><!--讲师社号
			--><th  width="5%"><spring:message code="empsubject.tcrNm"/></th><!--讲师姓名
			--><th  width="5%"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/></th><!--部门
			--><th  width="5%"><spring:message code="edu.teacherManager.JIANGKELINGYU.a"/></th><!--讲课领域
			--><th  width="5%"><spring:message code="edu.teacherManager.JIANGSHIJIBIE.a"/></th><!--讲师级别
			--><th  width="5%"><spring:message code="edu.teacherManager.PINYONGSHIJIAN.a"/></th><!--聘用时间
			--><th  width="5%"><spring:message code="edu.teacherManager.JIEPINSHIJIAN.a"/></th><!--解聘时间
			--><th  width="5%"><spring:message code="ar.attendanceView.viewNoSwipingCard.status"/></th><!--状态
		--></tr>
		</thead>
		<tbody>
		<c:forEach items="${teacherManagerList}" var="s" varStatus="i">
		<tr target="Tea" id="listTea_${i.count }" onclick="xuanzhongTea('${i.count }','${s.TEACHER_NO }')" >
		   <td class="td_type" width="1%" style="text-align:center;">${i.count }.</td>
		   <td class="td_type" width="5%">${s.EMPID }</td>
           <td class="td_type" width="5%">${s.TEACHER_NAME }</td>
           <td class="td_type" width="5%">${s.ORG_NAME_LOCAL }</td>
           <td class="td_type" width="5%">${s.TEACH_FIELD_CODE_NAME }</td>
           <td class="td_type" width="5%">${s.TEACH_LEVEL_CODE_NAME }</td>
           <td class="td_type" width="5%">${s.HIRE_TIME }</td>
           <td class="td_type" width="5%">${s.FIRING_TIME }</td>
           <td class="td_type" width="5%">${s.TEACH_STATUS_CODE_NAME }</td>
		   </td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>