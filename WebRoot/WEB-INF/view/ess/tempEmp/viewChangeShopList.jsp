<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewChangeShopList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewChangeShopListForm",navTab.getCurrentPanel()).submit();
	});
	//保存
	$("#viewChangeShopList_Save",navTab.getCurrentPanel()).click(function(){	
		var emptyFlag = 0;
		//获取页面的值
		var jsonData = '[';
		$("input[name='changeShopCheck']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(obj).attr("sysIndex");
				var CHANGE_DATE = $("#CHANGE_DATE_" + index,navTab.getCurrentPanel()).html();
				var DEPTNAME = $("#DEPTNAME_" + index,navTab.getCurrentPanel()).html();
				if(CHANGE_DATE == ''){
					emptyFlag = 1;
					return false;
				}
				if(DEPTNAME == ''){
					emptyFlag = 2;
					return false;
				}
				jsonData += ' "CHANGE_DATE": "' + CHANGE_DATE + '" ,';
				jsonData += ' "CHANGE_TYPE": "' + $("#CHANGE_TYPE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "SHIFT_TO_TIME": "' + $("#SHIFT_TO_TIME_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "PERSON_ID": "' + obj.value + '" ,';
				jsonData += ' "DEPTNAME": "' + DEPTNAME + '" ,';
				jsonData += ' "OLD_DEPTNO": "' + $("#DEPTNAME_" + index,navTab.getCurrentPanel()).attr('sysOldDept') + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				
				jsonData += '}';
			}
		});
		jsonData += ']';

		if(emptyFlag == 1){
			alertMsg.info("<spring:message code='ar.alert.message.excelimport.datenotnull' />");//日期不能为空
			return;
		}
		if(emptyFlag == 2){
			alertMsg.info("<spring:message code='ess.viewChangeShopList.DIAORUDIANBUNENGWEIKONG.a' />");//调入店不能为空
			return;
		}
		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='hrm.empinfo.NOTSAVE_DATA' />");//没有需要保存的数据
			return;
		}
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/tempEmp/addChangeShopInfo',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
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
		initEditFun_ess3425();
	});

	initEditFun_ess3425();
});

function initEditFun_ess3425(){

	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$("#changeShopCheck_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
		onblur:function(val,settings){
			$(this).html(val);
			var index = $(this).attr("sysIndex");
			$("#changeShopCheck_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
}
function fillItem(){
	var checked=false;
	var ids= document.getElementsByName("changeShopCheck");
	var dept_name = $("#viewCheckShopList_deptList",navTab.getCurrentPanel()).val();
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			var index = ids[i].id.substring(16);
			$("#DEPTNAME_"+index,navTab.getCurrentPanel()).html(dept_name);
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="ar.viewApplyAttenanceManagentInfoList.QINGXUANZEXIUGAINEIRONG.b" />'); //请选择要修改的内容
		return false;
	}
}
</script>
<div class="pageHeader">
<form id="viewChangeShopListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewChangeShopList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid" /></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}">
		</td>
		<td><!--部门--><spring:message code="ess.infoApply.DEPT" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewChangeShopList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewChangeShopList_deptList" selected="${DEPTNO}"/>
		</td>
		<td><!--员工状态--><spring:message code="ess.empInfo.employee_status" /></td>
		<td>
			<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" selected="${EMP_OFFICE}" parentNo="15118" limit="all"/>
		</td>
		<td><!--期间--><spring:message code="ess.infoApply.Period" /></td>
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
		<li><a class="buttonActive" id="viewChangeShopList_Serch" href="#"><span><!--查询--><spring:message code="org.title.SELECT" /></span></a></li>
		<li><a class="add" href="/pa/excelImport/importExcelData?importFunName=/importTempEmp" target="dialog" mask="true"><span><!--导出到Excel--><spring:message code="hrm.empinfo.EXPORT" /></span></a></li>
		<li><a class="buttonActive" id="viewChangeShopList_Save" href="#"><span><!--保存--><spring:message code="ar.viewempcalender.title.save" /></span></a></li>
	</ul>
</div>
<div class="pageHeader" >
    <div class="searchBar">
			
			<table class="searchContent">
			    <tr>
			       <td><!--选择部门--><spring:message code="org.title.SELECT_DEPT" /></td>
				   <td>
				  		<ait:deptList name="seach_DEPTNO" limit="all" id="viewCheckShopList_deptList" />
						<ait:deptTreeIcon name="seach_DEPTNO" limit="all" id="viewCheckShopList_deptList" selected="${DEPTNO}" />
				   </td>
			    </tr>
			</table><div class="subBar">
				<ul class="toolBar">
	             <li>
	            	 <a class="buttonActive" onclick="fillItem();"><span><!--填充--><spring:message code="ess.infoApply.title.fillItem" /></span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>
<div class="pageContent">
				<table class="orderList" width="99%">
					<thead>
						<tr>
							<th width="3%">NO.</th>
				    	    <th width="3%"><input type="checkbox" class="checkboxCtrl" group="changeShopCheck" /></th>
							<th width="6%"><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
							<th width="6%"><!--社号--><spring:message code="ess.infoApply.EMPID" /></th>
							<th width="12%"><!--岗位--><spring:message code="rp.report.title.dutyinfo" /></th>
							<th width="8%" class="titleColor"><!--开始日期--><spring:message code="org.title.STARTDATE" /></th>
							<th width="8%" class="titleColor"><!--结束日期--><spring:message code="ess.empInfo.end_date" /></th>
							<th width="8%" class="titleColor"><!--调店区分--><spring:message code="ess.title.DIAODIANQUFEN" /></th>
							<th width="12%" class="titleColor"><!--店铺--><spring:message code="ess.title.DIAPU" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewChangeShopList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>
						        	<input type="checkbox" id="changeShopCheck_${i.index}" name="changeShopCheck" sysIndex="${i.index}" value="${item.PERSON_ID}" />
					         	</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.DUTY_NO_NAME}</td>
							 	<td class='td_center' sysLog="date" sysIndex="${i.index}" format="yyyy.MM.dd" id="CHANGE_DATE_${i.index}"></td>
							 	<td class='td_center' sysLog="date" sysIndex="${i.index}" format="yyyy.MM.dd" id="SHIFT_TO_TIME_${i.index}"></td>
								<td class='td_center' sysLog="select" sysIndex="${i.index}" sysValue='[{"CODE_NO":"1","CODENAME":"临时"},{"CODE_NO":"2","CODENAME":"正式"}]' id="CHANGE_TYPE_${i.index}"><!--临时--><spring:message code="ess.title.LINSHI" /></td>
							 	<td class='td_center' sysLog="select" sysIndex="${i.index}" sysValue='${dept}' sysOldDept="${item.DEPTNO }" id="DEPTNAME_${i.index}">${item.DEPT_NAME}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>