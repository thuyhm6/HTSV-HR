<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	var quanAGRno="";
	var quanAGR="";
	function xiugaiAGR(){
			if(quanAGRno!=""){
				$('#updateAGR').attr('href','/edu/traineducation/trainAgreementInfo?AGREE_NO='+quanAGRno);
		}else{
			alert("<spring:message code='edu.systemManager.QINGXUANZEQIZHONGYIXIANG.a'/>");//请选择其中一项!
		}
	}
	function xuanzhongAGR(no,nono){
		var count="${trainAgreementListCount}";
		for(var i=1;i<=count;i++){
			$('#listAGR_'+i).attr('style','');
		}
		$('#listAGR_'+no).attr('style','background:#aaccf6');
		$('#updateAGR').attr('href','/edu/traineducation/trainAgreementInfo?AGREE_NO='+nono);
		$('#yinAGR').attr('style','display:none');
		$('#xianAGR').attr('style','');
		quanAGR=no;
		quanAGRno=nono;
	}
	function shanchuAGR(){
		if(quanAGRno!=""){
			$('#deleteAGR').attr('href','/edu/traineducation/deleteTrainAgreement?AGREE_NO='+quanAGRno);
	}else{
		$('#deleteAGR').attr('href','/edu/traineducation/deleteTrainAgreement?{AGR}');
	}
  }
	function sousuoAGR(){
		$('#trainAgreement').submit();
	}
	function trainAgreeExport(){
		var AGREE_DEPTNO=$('#agreeDeptno').val();
		var AGREE_NAME_EMPID=$('#AGREE_NAME_EMPID').val();
		var AGREE_START_TIME=$('#AGREE_START_TIME').val();
		var AGREE_END_TIME=$('#AGREE_END_TIME').val();
		window.location.href="/edu/traineducation/trainAgreeImportDemoLoad?flag=export&AGREE_DEPTNO="+AGREE_DEPTNO+"&AGREE_NAME_EMPID="+AGREE_NAME_EMPID+"&AGREE_START_TIME="+AGREE_START_TIME+"&AGREE_END_TIME="+AGREE_END_TIME;
	}
	function trainAgreeImport(){
		$("#importExcelDialogTrainAgree").attr('href','/pa/excelImport/importExcelData?importFunName=/importTrainAgreement');
		$("#importExcelDialogTrainAgree").click();
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
	    "scrollX": true,
	    "orderClasses": false,
	    "order":[],//初始化不用自动排序
	    "scrollY": $(document.body).height() - 300,
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
<form id="trainAgreement" onsubmit="return navTabSearch(this);" action="/edu/traineducation/trainAgreement" method="post">
<div class="pageHeader" >
<div class="searchBar" >
<div  style="width: 97%;margin-left:auto;margin-right:auto;" >
<table class="user_table" style="text-align:center;" width="100%" border="1" cellpadding="2" cellspacing="1">
		<tr>
		<td class="td_title"  width="10%"><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/></td><!--部门-->
		<td class="td_type" width="20%">
		<ait:deptList name="AGREE_DEPTNO" cpnyId="${defaultCpny}" limit="super" id="AGREE_DEPTNO"/>
		<input type="hidden" id="agreeDeptno" value="" syslong="AGREE_DEPTNO">
        <ait:deptTreeIcon name="AGREE_DEPTNO" cpnyId="${defaultCpny}" limit="super" id="AGREE_DEPTNO" selected="${AGREE_DEPTNO}"/></td>
		<td class="td_title" width="5%"><spring:message code="hrm.empinfo.nameAndEmpid"/></td><!--社号/姓名-->
		<td class="td_type" width="20%">
		<input type="text" name="AGREE_NAME_EMPID" id="AGREE_NAME_EMPID" value="${AGREE_NAME_EMPID }">
		</td>
		<td class="td_title" width="10%"><spring:message code="edu.trainAgreement.XIEYIQIANDINGSHIJIANDUAN.a"/></td><!--协议签订时间段-->
		<td class="td_type" width="40%">
		<input type="text" id="CON_START_DATE" name="CON_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"
		  value="${CON_START_DATE}" style="float:left;"/>
		<div style="float:left;">~</div>
		<input type="text" id="CON_END_DATE" name="CON_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})"
		   value="${CON_END_DATE}" style="float:left;"/>
		</td>
</table>
</div>
</div>
<a id="importExcelDialogTrainAgree"  href="" target="dialog" mask="true" width="500" height="200"></a>
<div class="subBar" style="margin-top: 10px; float: right; padding-right: 10px;">
		<ul style="width: 450px;">
            <li>
				<a class="buttonActive" href="#" onclick="sousuoAGR()" width="800" height="250" >
					<span><spring:message code="hrm.empinfo.SEARCH"/></span><!--搜索
				--></a>
			</li>
			<li>
				<a class="buttonActive" href="/edu/traineducation/addTrainAgreement" target="dialog" mask="true" width="800" height="600" >
					<span><spring:message code="hrm.contract.add"/></span><!--添加
				--></a>
			</li>
			<li id="yinAGR" style="">
				<a class="buttonActive"  onclick="xiugaiAGR()" href="#"  width="800" height="250">
					<span><spring:message code="zxc.hrm.affirmConfig.update"/></span><!--修改
				--></a>
			</li>
			<li id="xianAGR" style="display:none">
				<a class="buttonActive" id="updateAGR" onclick="xiugaiAGR()" href="#" target="dialog" mask="true"  width="800" height="600">
					<span><spring:message code="zxc.hrm.affirmConfig.update"/></span><!--修改
				--></a>
			</li>
			<li>
				<a class="buttonActive" title="<spring:message code="edu.systemManager.QUEDINGSHIFOUSHANCHU.a"/>"   id="deleteAGR" onclick="shanchuAGR()" href="#" callback="doAjaxDoneWithForm" target="ajaxTodo"><!--
					                               确定是否删除?
					--><span><spring:message code="org.title.DELETE"/></span><!--删除
				--></a>
			</li>
			<li>
				<a class="buttonActive" href="#" onclick="trainAgreeImport()">
					<span><spring:message code="ess.infoApply.EXCEL_IN"/></span><!--Excel导入
				--></a>
			</li>
			<li>
				<a class="buttonActive" href="/edu/traineducation/trainAgreeImportDemoLoad?flag=load" >
					<span><spring:message code="pa.button.message.specialempimportmodeldown"/></span><!--导入模板下载
				--></a>
			</li>
			<li>
				<a class="buttonActive" href="#" onclick="trainAgreeExport()">
					<span><spring:message code="inct.salesman.downloadToExcel"/></span><!--Excel导出
				--></a>
			</li>
			
		</ul>
</div>
<div style="width: 100%; padding-top: 30px;">
	<table id="viewInfoTable" class="list" >
	<thead>
		<tr >
		    <th  width="1%">NO.</th>
		    <th  width="5%"><spring:message code="edu.trainAgreement.XIEYIBIANHAO.a"/></th><!--协议编号-->
			<th  width="5%"><spring:message code="edu.trainAgreement.XIEYIMINGCHENG.a"/></th><!--协议名称	-->
			<th  width="5%"><spring:message code="edu.trainAgreement.XIEYIRENSHEHAO.a"/></th><!--协议人社号-->
			<th  width="5%"><spring:message code="edu.trainAgreement.XIEYIRENXINGMING.a"/></th><!--协议人姓名	-->
			<th  width="5%"><spring:message code="edu.trainAgreement.XIEYIRENBUMEN.a"/></th><!--协议人部门-->
			<th  width="5%"><spring:message code="hrm.empinfo.CONTRACT_START_STOP_DATE.Z"/></th><!--合同起止时间-->
			<th  width="5%"><spring:message code="liang.hr.viewTraining.title.TRAINING_TIME"/></th><!--培训时间-->
			<%-- <th  width="5%"><spring:message code="edu.trainAgreement.XIEYIZONGFEIYONG.a"/></th><!--协议总费用-->
			<th  width="5%"><spring:message code="display.pa.ecc.expectresigndate"/></th><!--预离职日期-->
			<th  width="5%"><spring:message code="edu.trainAgreement.WEIYUEJINFEIYONG.a"/></th><!--违约金费用(元)	--> --%>
			<th  width="5%"><spring:message code="edu.trainAgreement.SHIJIZHIFU.a"/></th><!--实际支付	-->
			<th  width="5%"><spring:message code="edu.trainAgreement.XIEYIQIANDINGRIQI.a"/></th><!--协议签订日期-->
			<th  width="5%"><spring:message code="edu.trainAgreement.XIEYIJIECHURIQI.a"/></th><!--协议解除日期-->
			<th  width="5%"><spring:message code="org.title.enclosure"/></th><!--附件-->
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${trainAgreementList}" var="s" varStatus="i">
		<tr target="AGR" id="listAGR_${i.count }" onclick="xuanzhongAGR('${i.count }','${s.AGREE_NO }')" >
		   <td class="td_type" width="1%" style="text-align:center;">${i.count }.</td>
           <td class="td_type" width="5%">${s.AGREE_ID }</td>
           <td class="td_type" width="5%" >${s.AGREE_NAME }</td>
           <td class="td_type" width="5%">${s.EMPID }</td>
           <td class="td_type" width="5%">${s.LOCAL_NAME }</td>
           <td class="td_type" width="5%">${s.DEPART_NAME }</td>
           <td class="td_type" width="5%">${s.CON_START_DATE }~${s.CON_END_DATE }</td>
           <td class="td_type" width="5%">${s.STUDY_START_DATE }~${s.STUDY_END_DATE }</td>
           <%-- <td class="td_type" width="5%">${s.TOTAL_FEE }</td>
           <td class="td_type" width="5%">${s.LEFT_DATE }</td>
           <td class="td_type" width="5%">${s.WEIYUEJIN }</td> --%>
           <td class="td_type" width="5%">${s.FACT_PAY }</td>
           <td class="td_type" width="5%">${s.AGREE_START_DATE }</td>
           <td class="td_type" width="5%">${s.AGREE_END_DATE }</td>
           <td class="td_type" width="5%">
           <c:forEach items="${s.fileList}" var="t" varStatus="i">
			<span style="color:blue;"></span>
			<a style="color:blue;" href="/ess/infoApplyLeave/downloadFile?fileName=${t.FILE_URL }&file=${t.FILE_NAME}" >${i.count }.${t.FILE_NAME}&nbsp&nbsp</a></br>
		    </c:forEach>
           </td>
		   </td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
</div>
</form>