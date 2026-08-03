<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewTempEmpSalaryList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewTempEmpSalaryListForm",navTab.getCurrentPanel()).submit();
	});
	//保存
	$("#viewTempEmpSalaryList_Save",navTab.getCurrentPanel()).click(function(){	
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
	$('.orderList tbody tr td:[sysLog="autoDate"]',navTab.getCurrentPanel()).editable({type:'autoDate',
		onblur:function(val,settings){
			val=val.replace(/\D/g,'');
        	$(this).html(val);
			var index = $(this).attr("sysIndex");
			this.editing = false;
			if(val == ""){
				$("#EMP_OFFICE_" + index,navTab.getCurrentPanel()).html("<spring:message code='hrm.empinfo.JOB' />");//在职
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
	        	   $("#seach_PA_MONTH",navTab.getCurrentPanel())[0].focus();
		           return false; 
		       }else {
			        return true;
			   }
	      }  
	 }  
};
//导入数据
function importExcelTemp_ess3445(){
	$("#importExcelDialog_ess3445").attr('href','/pa/excelImport/importExcelData?importFunName=/importTempEmpSalary');
	$("#importExcelDialog_ess3445").click();
}
</script>
<a id="importExcelDialog_ess3445"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess3445" href="#" target="navTab" mask="true"><span style="display:none;"><spring:message code="ess.viewTempEmpSalaryList.LINSHIGONGGONGZIDAORUJIEGUO.a" /><!-- 临时工工资导入结果 --></span></a>
<div class="pageHeader">
<form id="viewTempEmpSalaryListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewTempEmpSalaryList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="ess.viewTempEmpSalaryList.GONGZIYUEFEN.a" /><!-- 工资月份 --></td>
		<td>
			<input type="text" id="seach_PA_MONTH" name="seach_PA_MONTH" class="Wdate" onClick="WdatePicker({dateFmt:'yyyyMM'})" value="${PA_MONTH}"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewTempEmpSalaryList_Serch" href="#"><span><spring:message code="button.search" /><!-- 查询 --></span></a></li>
		<li><a class="buttonActive" href="/pa/excelExport/downloadTempEmpSalExcelTemplate?file=tempEmpSalary"><span><spring:message code="hrm.contract.Download_templates" /><!-- 下载模板 --></span></a></li>
		<li><a class="buttonActive" onclick="importExcelTemp_ess3445()"> <span><spring:message
			code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> </a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="99%">
					<thead>
						<tr>
							<th>NO.</th>
							<th><spring:message code="ess.viewTempEmpSalaryList.GONGZIYUEFEN.a" /><!-- 工资月份 --></th>
							<th><spring:message code="alert.pa.pasalarycanshu.shehao" /><!-- 社号 --></th>
							<th><spring:message code="alert.pa.pasalarycanshu.xingming" /><!-- 姓名 --></th>
							<th><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName" /><!-- 部门 --></th>
							<th><spring:message code="ess.personalinfo.title.IDCardNo" /><!-- 身份证号--></th>
							<th><spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" /><!-- 手机号 --></th>
							<th><spring:message code="pa.low.adjust.yingfagongzi" /><!-- 应发工资 --></th>
							<th><spring:message code="pa.salary.canShu.beiZhu" /><!-- 备注 --></th>
							<th><spring:message code="ar.viewTempEmpConfirmList.CAOZUOREN.b" /><!-- 操作人 --></th>
							<th><spring:message code="ess.viewTempEmpSalaryList.CAOZUORIQI.a" /><!-- 操作日期 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewTempEmpSalaryList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.PA_MONTH}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td class='td_center'>${item.DEPT_NAME}</td>
								<td class='td_center'>${item.IDCARD_NO}</td>
								<td class='td_center'>${item.CELLPHONE}</td>
								<td class='td_center'>${item.NET_SALARY}</td>
								<td class='td_center'>${item.REMARK}</td>
								<td class='td_center'>${item.UPDATED_BY}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
