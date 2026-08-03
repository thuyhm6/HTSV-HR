<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewTxEmpList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewTxEmpListForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewTxEmpList_SEQ").change(function(){
		$("#viewTxEmpListForm",navTab.getCurrentPanel()).submit();
	});
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	 	var AR_MONTH=encodeURI(encodeURI($('#seach_AR_MONTH',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewTxEmpList&seach_KEY='+name+'&seach_AR_MONTH='+AR_MONTH);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	 var AR_MONTH=encodeURI(encodeURI($('#seach_AR_MONTH',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewTxEmpList&seach_KEY='+name+'&seach_AR_MONTH='+AR_MONTH);
   });
	if($("#seach_AR_MONTH",navTab.getCurrentPanel()).val() == ''){
		$.ajax({
			type: 'POST',
			url: '/hrm/recruitManage/doSql',
			data:{sql:"select GET_AR_MONTH_HR(SYSDATE,'${LoginUser.cpnyId}') DATE_STR from dual"},
			dataType:"json",
			cache: false,
			success: function(data){
				$("#seach_AR_MONTH",navTab.getCurrentPanel()).val(data.result[0].DATE_STR);
			},
			error: DWZ.ajaxError
		});
	}
	
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[15,25,35, 50], [15,25, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 15, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 315,
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [] }
	                     ],
	    "fixedColumns":false,
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
		initEditFun_ar0233();
	});

	initEditFun_ar0233();
	
	//保存
	$("#viewTxEmpList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("div[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(this).html() == "modify"){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(this).attr("sysIndex");
				jsonData += ' "ADJUST_TX": "' + $("#ADJUST_TX_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "SEQ": "' + $("#SEQ_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';
		
		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='org.title.NOTSAVE_DATA' />");//没有需要保存的数据
			return;
		}

		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ar/attendanceSettings/saveEmpTxInfo2',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
});

function initEditFun_ar0233(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			var format = $(this).attr("format");
    		if( typeof(format) != "undefined" && format != ""){
    			if(isNaN(val)){
    	        	$(this).html(0);
    			}else{
    	        	$(this).html(val);
    			}
    			this.editing   = false;
				var value = $(this).attr("sysOldValue");
    			$("#TOTAL_TX_" + index,navTab.getCurrentPanel()).html($("#TOTAL_TX_" + index,navTab.getCurrentPanel()).html() - parseFloat(value) + parseFloat(val));
    			$("#LEAVE_TX_" + index,navTab.getCurrentPanel()).html($("#LEAVE_TX_" + index,navTab.getCurrentPanel()).html() - parseFloat(value) + parseFloat(val));
				$(this).attr("sysOldValue",val);
    		}else{
	        	$(this).html(val);
				this.editing = false;
    		}
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
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
<form id="viewTxEmpListForm" onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewTxEmpList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 工号/姓名： --><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /></td>
		<td>
			<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
			<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
		</td>
		<td>
			<c:if test="${not empty personInfo}">
			<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
			</c:if>
		</td>
	</tr>
	<tr>
		<td><!-- 期间 --><spring:message code="ess.infoApply.Period" /></td>
		<td>
			<input type="text" id="seach_AR_MONTH" name="seach_AR_MONTH" class="Wdate" value="${AR_MONTH }" onClick="WdatePicker({dateFmt:'yyyyMM'})"/>
		</td>
		<td><!--部门--><spring:message code="ess.infoApply.DEPT" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="hr" id="viewTxEmpList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewTxEmpList_seachDept" selected="${DEPTNO}"/>
			<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
		</td>
		<td><!--任职状态--><spring:message code="ess.infoApply.renzhizhuangtai" /></td>
		<td>
		 <ait:SelectSyCodeByCpnyID id="seach_EMP_OFFICE" name="seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}"  limit="all"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewTxEmpList_Serch" href="#"><span><!--查询--><spring:message code="ess.infoApply.SELECT" /></span></a></li>
		<li><a class="buttonActive" id="viewTxEmpList_Save" href="#"><span><!--保存--><spring:message code="ess.message.save" /></span></a></li>
		<li><a class="delete" href="#" onclick="downloadExcel('viewTxEmpListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=200','/ar/attendanceSettings/viewTxEmpList')"><span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span></a></li>
	</ul>
</div>

<div class="pageContent">
				<table class="orderList" width="100%">
					<thead>
						<tr>
							<th width="30px" rowspan="2">No.</th>
							<th width="50px" rowspan="2"><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
							<th width="50px" rowspan="2"><!--工号--><spring:message code="ess.infoApply.EMP_ID" /></th>
							<th width="150px" rowspan="2"><!--部门名--><spring:message code="ess.infoApply.DEPT_NAME" /></th>
							<th width="80px" rowspan="2"><!--职级--><spring:message code="ess.infoApply.Rank" /></th>
							<th width="70px" rowspan="2"><!--入社日--><spring:message code="ar.viewTxEmpList.RUSHERI.b" /></th>
							<th width="80px" rowspan="2"><!--工作地--><spring:message code="org.title.WORD_AREA_NAME" /></th>
							<th width="90px" colspan="3"><!--上上月--><spring:message code="ess.infoApply.SHANGSNANGYUE" /></th>
							
							<th width="90px" colspan="3"><!--上月--><spring:message code="ess.infoApply.LASTMONTH" /></th>
							
							<th width="290px" colspan="4"><!--本月--><spring:message code="display.emp.ben.or.benhs69" /></th>
							
							
							<th width="150px" rowspan="2"><!--变更者--><spring:message code="org.title.UPDATED_IP" /></th>
							<th width="100px" rowspan="2"><!--变更时间--><spring:message code="org.title.UPDATE_DATE" /></th>
						</tr>
						<tr>
							<th width="30px"><!--总数--><spring:message code="ar.viewTxEmpList.ZONGSHU.b" /></th>
							<th width="30px"><!--使用--><spring:message code="empsubject.useY" /></th>
							<th width="30px"><!--剩余--><spring:message code="ess.infoApply.Remainder" /></th>
							
							<th width="30px"><!--总数--><spring:message code="ar.viewTxEmpList.ZONGSHU.b" /></th>
							<th width="30px"><!--使用--><spring:message code="empsubject.useY" /></th>
							<th width="30px"><!--剩余--><spring:message code="ess.infoApply.Remainder" /></th>
							
							<th width="30px"><!--总数--><spring:message code="ar.viewTxEmpList.ZONGSHU.b" /></th>
							<th width="30px"><!--使用--><spring:message code="empsubject.useY" /></th>
							<th width="30px"><!--剩余--><spring:message code="ess.infoApply.Remainder" /></th>
							<th width="30px"><!--调整--><spring:message code="ar.viewTxEmpList.TIAOZHENG.b" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewTxEmpList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td>${item.LOCAL_NAME}</td>
								<td>${item.EMPID}</td>
								<td>${item.DEPTNAME}</td>
								<td style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden;">${item.POST_GRADE_NAME}</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td>${item.WORK_AREA_NAME}</td>
								
								<td>${item.TOTAL_TX_PP}</td>
								<td>${item.USE_TX_PP}</td>
								<td>${item.TOTAL_TX_PP - item.USE_TX_PP}</td>
								
								<td>${item.TOTAL_TX_P}</td>
								<td>${item.USE_TX_P}</td>
								<td>${item.TOTAL_TX_P - item.USE_TX_P}</td>
								
								<td id="TOTAL_TX_${i.index}">${item.TOTAL_TX}</td>
								<td>${item.USE_TX}</td>
								<td id="LEAVE_TX_${i.index}">${item.TOTAL_TX - item.USE_TX}</td>
								
								<td sysLog="text" sysIndex="${i.index}" format="number" id="ADJUST_TX_${i.index}" sysOldValue="${item.ADJUST_TX}">${item.ADJUST_TX}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
