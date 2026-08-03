<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
			"bPaginate": true,    //分页
		    "bAutoWidth":true,//表格宽度自动变化
		    "bProcessing":true,
		    "lengthMenu": [[20,30, 40, 50], [20,30, 40, 50]],
			"bLengthChange": true,  //按多少条记录显示下拉框
			"iDisplayLength": 20, //默认每页显示的记录数
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	     	"searching": true,//本地搜索
			"bSort": true,   //排序功能
			"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
		     "orderClasses": false,
		     "order":[],//初始化不用自动排序
		     "scrollY": $(document.body).height() - 250,
		     "scrollX": $(document.body).width(),
		     "scrollCollapse": false,
		     "deferRender":true,
		     //"scroller":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
	                     ],
	         //"fixedColumns":{leftColumns : 2},
            "oLanguage": {//多语言配置
            	"sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
                "sZeroRecords": "<spring:message code='hem.alert.empinfo.not_find_relevant_data'/>",//查询不到相关数据！
                "sEmptyTable": "<spring:message code='hrm.alert.empinfo.No_data_in_table'/>",//表中无数据存在！
                "sSearch": "<spring:message code='hrm.alert.contractInfo.Rapid_screening'/>",//快速筛选
                "sLengthMenu": "<spring:message code='hrm.alert.contractInfo.Record_page'/>",//每页 _MENU_ 条记录
                "sInfo": "<spring:message code='hrm.alert.contractInfo.START_END_TOTAL'/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
                "sInfoFiltered": "(<spring:message code='hrm.alert.contractInfo.Record_filter'/>)",//从 _MAX_ 条记录过滤
                "oPaginate": {
                    "sPrevious": "<spring:message code='hrm.alert.contractInfo.Previous_page'/>",//上一页
                    "sNext": "<spring:message code='hrm.alert.contractInfo.NEXT_PAGE'/>"//下一页
                }
            },
            "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
            "buttons": [] 
		});
	initEditFun_ess3460();
	$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_ess3460();
	} );
});
function initEditFun_ess3460(){
	$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$(this).html(val);
			submitKeyClick_fixOt(val,index);
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			var sysType = $(this).attr("sysType");
			var reg = new RegExp("^[0-9]*$");
			if(!reg.test(val)){
				alertMsg.error("请输入有效数字");
				val = '';
			}
			if(!reg.test(val) && sysType=='date'){
				alertMsg.error("请输入六位有效日期");
				val = '';
			}
			if(val.length != '6' && sysType=='date'){
				alertMsg.error("请输入六位有效日期");
				val = '';
			}
			$(this).html(val);
			this.editing = false;
		}
	});
}

function submitKeyClick_fixOt(obj,index){
	var empid=obj.replace(/[ ]/g," ");
	var personIdStr="fixOt";
	if(empid != ''){
	   	$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
				 if(jsonObject.perCnt != 1 ){
					document.getElementById("onckSche").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmEvsList?limit=ar&pageNum=1"
							+'&seach_KEY='+empid+'&personidStr='+personIdStr + '&index=' + index));
					document.getElementById("onckSche").click();
				}
				if(jsonObject.perCnt==1){
					$("#EMPID_"+ index ,navTab.getCurrentPanel()).html(jsonObject.empId);
					$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html(jsonObject.deptName);
					$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html(jsonObject.empName);
					$("#PERSON_ID_"+ index ,navTab.getCurrentPanel()).val(jsonObject.empName);
				} ;
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#EMPID_"+ index ,navTab.getCurrentPanel()).html("");
		$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html("");
		$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html("");
		$("#PERSON_ID_"+ index ,navTab.getCurrentPanel()).val("");
	}
}
function saveFixOtInfo() {
		var checked=false;
		var ids= document.getElementsByName("c1");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
				var index = ids[i].id.substring(3);
				var START_DATE = $("#START_DATE_" + index,navTab.getCurrentPanel()).html();
				var END_DATE = $("#END_DATE_" + index,navTab.getCurrentPanel()).html();
				var FIX_OT_LENGTH = $("#FIX_OT_LENGTH_" + index,navTab.getCurrentPanel()).html();
				if(START_DATE == '' || START_DATE == null){
					alertMsg.error("开始日期不能为空");
					return false;
				}
				if(END_DATE == '' || END_DATE == null){
					alertMsg.error("结束日期不能为空（无限大请填999912）");
					return false;
				}
				if(FIX_OT_LENGTH == '' || FIX_OT_LENGTH == null){
					alertMsg.error("固定加班时数不能为空");
					return false;
				}
			}
		}

		//获取页面的值
		var jsonData = '[';
		$("input[name='c1']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = obj.id.substring(3);
				jsonData += ' "SEQ": "' + $("#SEQ_" + index,navTab.getCurrentPanel()).val() + '" ,';
				jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_" + index,navTab.getCurrentPanel()).val() + '" ,';
				jsonData += ' "START_DATE": "' + $("#START_DATE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "END_DATE": "' + $("#END_DATE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "FIX_OT_LENGTH": "' + $("#FIX_OT_LENGTH_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';
		if (jsonData.length == 2) {
			alertMsg.info("请先选择信息在进行保存");
			return;
		}
		alertMsg.confirm("确定保存么？", {
			okCall : function() {
				$.ajax({
					type: 'POST',
					url:"/ess/tempEmp/saveFixOtInfo",
					data: [{ name: 'jsonData', value: jsonData }],
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch($("#viewSearchFixOtInfo"));
							alertMsg.correct(data.message);
						}else{
							if(data.result=="2"){
								alertMsg.info(data.message);
							}else{
								alertMsg.error(data.message);
							}
						}   
			   	 	}  ,
					error: DWZ.ajaxError
				});
			}
		});
		return false;
}
		
function validateCallbackDeleteFixOt(OP_FLAG,form,callback) {
	var $form=null;
	if($('#'+form).length>0){
		$form=$('#'+form);
	}else{
 		$form = $(form);
	}
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('请选择一条记录'); 
		return false;
	}
	alertMsg.confirm("确定要批量删除吗", {
		okCall : function() {
			$.ajax({
				type: 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success : doAjaxDoneWithForm,
				error: DWZ.ajaxError
			});
		}
	})
	return false;
}
function addFixOtInfo(){
	 $.ajax({
			type:'post',
			url:'/ess/tempEmp/addFixOtInfo',
			dataType:null,
			success: function(data){ //请求成功后处理函数。
				navTab.reload('/ess/tempEmp/viewTempEmpFixOTList?firstFlag=N&type=add');
	   	 	}  ,
			error: DWZ.ajaxError});
}
</script>
<div class="pageHeader">
	<form id="viewSearchFixOtInfo" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewTempEmpFixOTList?firstFlag=N" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 社号/姓名： -->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
				</td>
				<td>
					<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/>
				</td>
				<td><!--部门--><spring:message code="ess.infoApply.DEPT" /></td>
				<td>
					<ait:deptList name="seach_DEPTNO" limit="manager" id="viewSearchFixOtInfo_deptList" />
					<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewSearchFixOtInfo_deptList" selected="${DEPTNO}"/>
				</td>
				<td>基准月</td>
				<td>
					<input type="text" id="START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyyMM'})" value="${START_DATE}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<li><a class="buttonActive" onclick="addFixOtInfo()"><span><spring:message code="button.add"/><!--添加--></span></a></li>
				</li>
				<li><a class="buttonActive" onclick="saveFixOtInfo()"><span><!--保存--><spring:message code="ar.viewempcalender.title.save" /></span></a></li>
				<li>
					<a class="buttonActive" onclick="validateCallbackDeleteFixOt(0,'deleteFixOtForBatch',DWZ.ajaxDone);" href="#"> 
						<span><spring:message code="hrm.contract.delete" /><!-- 删除 --></span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<form name="deleteFixOtFrom" id="deleteFixOtForBatch" method="post" action="/ess/tempEmp/deleteFixOtInfo" > 
	<table class="orderList" width="99%" id="deleteFixOtTable">
		<thead>
			<tr>
				<th>NO.</th>
				<th><input type="checkbox"  class="checkboxCtrl" group="c1"/></th>
				<th  class="titleColor">社号</th>
				<th>姓名</th>
				<th>部门</th>
				<th  class="titleColor">开始时间</th>
				<th  class="titleColor">结束时间</th>
				<th  class="titleColor">固定加班小时数</th>
				<th>创建者</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${viewFixOtList}" var="item" varStatus="i">
				<tr>
					<td class='td_center'>${i.count}</td>
					<td class='td_center'>
						<input type="checkbox" id="c1_${i.index}" name="c1" value="${item.SEQ}" />
						<input type="hidden" id="PERSON_ID_${i.index}" value="${item.PERSON_ID}">
						<input type="hidden" id="SEQ_${i.index}" value="${item.SEQ}" >
					</td>
					<td style="text-align: center" id="EMPID_${i.index}" <c:if test='${item.PERSON_ID eq null}'>sysLog="lookUp"</c:if> sysIndex="${i.index}" sysPersonId="${item.PERSON_ID}" >${item.EMPID}</td>
					<td class='td_center' id="LOCAL_NAME_${i.index}">${item.LOCAL_NAME}</td>
					<td class='td_center' id="DEPT_NAME_${i.index}">${item.DEPT_NAME}</td>
					<td sysLog="text" sysIndex="${i.index}" id="START_DATE_${i.index}" systype="date">${item.START_DATE}</td> 
					<td sysLog="text" sysIndex="${i.index}" id="END_DATE_${i.index}" sysType="date">${item.END_DATE}</td> 
					<td sysLog="text" sysIndex="${i.index}" id="FIX_OT_LENGTH_${i.index}" >${item.FIX_OT_LENGTH}</td> 
					<td class='td_center'>${item.CREATED_BY}</td>
					<td class='td_center'>${item.CREATE_DATE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<a id="onckSche" name="onckSche"  href="" lookupGroup="person"></a>
</div>
