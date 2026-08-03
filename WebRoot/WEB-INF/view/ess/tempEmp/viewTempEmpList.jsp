<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewTempEmpList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewTempEmpListForm",navTab.getCurrentPanel()).submit();
	});
	//保存
	$("#viewTempEmpList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(obj).html() == "modify"){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(obj).attr("sysIndex");
				jsonData += ' "SEQ": "' + $("#SEQ_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "DATE_LEFT": "' + $("#DATE_LEFT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				
				jsonData += '}';
			}
		});
		jsonData += ']';
		
		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='ess.infoApply.NO_NEED_TO_APPLY_DATA' />");//没有需要申请的数据
			return;
		}
		alertMsg.confirm("<spring:message code='ess.message.confirm_apply' />",//确定要申请吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/tempEmp/updateTempEmp',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});

	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[15,20,35, 50], [15,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 15, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 280,
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
	                     ],
	    //"fixedColumns":{leftColumns: 3},
        "oLanguage": {//多语言配置
	   	 //正在加载中......
        	"sProcessing": "<spring:message code='ess.message.loading' />",
            //查询不到相关数据！
            "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
            //表中无数据存在！
            "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
            //快速筛选
            "sSearch": "<spring:message code='ess.message.rapid_screening' />",
            //每页 _MENU_ 条记录
            "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
            //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
            "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
            //(从 _MAX_ 条记录过滤)
            "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
            "oPaginate": {
                //上一页
                "sPrevious": "<spring:message code='ess.message.previous_page' />",
                //下一页
                "sNext": "<spring:message code='ess.message.next_page' />"
            }
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [] 
	});
	
	$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_ess3421();
	});
	initEditFun_ess3421();
});

function initEditFun_ess3421(){
	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			this.editing = false;
			if(val == ""){
				$("#EMP_OFFICE_" + index,navTab.getCurrentPanel()).html("<spring:message code='ar.viewAddAffirmEvsList.ZAIZHI' />");//在职
				$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("");
			}else{
				$("#EMP_OFFICE_" + index,navTab.getCurrentPanel()).html("<spring:message code='ar.menu.title.resign' />");//离职
				$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			}
		}
	});
}
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
	        	   $("#seach_KEY",navTab.getCurrentPanel())[0].focus();
		           return false; 
		       }else {
			        return true;
			   }
	      }  
	 }  
};
</script>
<div class="pageHeader">
<form id="viewTempEmpListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewTempEmpList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /><!--社号/姓名--></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}">
		</td>
		<td><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!--部门--></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewTempEmpList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewTempEmpList_deptList" selected="${DEPTNO}"/>
		</td>
		<td><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态--></td>
		<td>
			<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" selected="${EMP_OFFICE}" parentNo="15118" limit="all"/>
		</td>
		<td><spring:message code="ess.workgroup.title.duration" /><!--期间--></td>
		<td>
			<input type="text" id="START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${START_DATE}"/>~
			<input type="text" id="END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${END_DATE}"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewTempEmpList_Serch" href="#"><span><spring:message code="hrm.empinfo.QUERY" /><!--查询--></span></a></li>
		<li><a class="buttonActive" href="/ess/tempEmp/viewAddTempEmp" target="dialog" height="250" width='500' mask="true" title="<spring:message code='ess.empInfo.insert' />"><span><spring:message code="ess.empInfo.insert" /><!--添加--></span></a></li>
		<li><a class="buttonActive" href="/pa/excelExport/downloadTempEmpExcelTemplate?file=MassNewAction_Hire_Temp"><span><spring:message code="hrm.contract.Download_templates" /><!--下载模板--></span></a></li>
		<li><a class="add" href="/pa/excelImport/importExcelData?importFunName=/importTempEmp" target="dialog" mask="true"><span><spring:message code="hrm.contract.Excel_import" /><!--Excel导入--></span></a></li>
		<li><a class="buttonActive" id="viewTempEmpList_Save" href="#"><span><spring:message code="ar.viewAdjustLeaveTSTOBatchList.SHENQING.b" /><!--申请--></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="99%">
					<thead>
						<tr>
							<th>NO.</th>
							<th><spring:message code="alert.pa.pasalarycanshu.xingming" /><!--姓名--></th>
							<th><spring:message code="ess.infoApply.EMP_ID" /><!--工号--></th>
							<th><spring:message code="org.title.dept" /><!--部门--></th>
							<th><spring:message code="ess.personalinfo.title.IDCardNo" /><!--身份证号--></th>
							<th><spring:message code="hrm.empinfo.FAM_BORNDATE" /><!--出生日期--></th>
							<th><spring:message code="hrm.empinfo.AGE" /><!--年龄--></th>
							<th><spring:message code="empsubject.sexName" /><!--性别--></th>
							<c:if test="${LoginUser.cpnyId eq 'SPC_HZ'}">
							<th><spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" /><!--手机号--></th>
							</c:if>
							<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
							<th><spring:message code="ess.viewTempEmpList.YINHANGZHANGHAO.a" /><!--银行帐号--></th>
							</c:if>
							<th><spring:message code="ess.trans.title.employeeStatus" /><!--员工状态--></th>
							<th><spring:message code="ess.trans.title.entryJobDate" /><!--入职日期--></th>
							<th><spring:message code="ess.trans.title.resignDate" /><!--离职日期--></th>
							<th><spring:message code="ess.infoApply.confirm_status" /><!--确认状态--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewTempEmpList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
								<td class='td_center'>${item.EMPID}</td>
							 	<td class='td_center'>${item.DEPT_NAME}</td>
								<td class='td_center'>${item.ID_CARD_NO}</td>
								<td class='td_center'>${item.DOB}</td>
								<td class='td_center'>${item.AGE}</td>
								<td class='td_center'>${item.SEXCODE_NAME}</td>
								
								<c:if test="${LoginUser.cpnyId eq 'SPC_HZ'}">
								<td class='td_center'>${item.PHONE}</td>
								</c:if>
								<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
								<td class='td_center'>${item.BANK_NO}</td>
								</c:if>
							
								<td class='td_center' id="EMP_OFFICE_${i.index}">${item.EMP_OFFICE_NAME}</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td <c:if test="${item.EMP_OFFICE eq '15119' and item.ACTIVITY eq 1}">sysLog="autoDate" sysIndex="${i.index}" id="DATE_LEFT_${i.index}"</c:if> class='td_center'>${item.DATE_LEFT}</td>
								<td class='td_center'>
									<c:if test="${item.ACTIVITY eq 1}"><spring:message code="ess.infoApply.adopt" /><!--通过--></c:if>
									<c:if test="${item.ACTIVITY eq 0}"><spring:message code="ess.title.WEIQUEREN" /><!--未确认--></c:if>
									<c:if test="${item.ACTIVITY eq 2}"><spring:message code="ess.infoApply.veto" /><!--否决--></c:if>
									<div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
