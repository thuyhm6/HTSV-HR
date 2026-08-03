<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
			"bPaginate": true,    //分页
		    "bAutoWidth":true,//表格宽度自动变化
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
		     "scrollY": $(document.body).height() - 250,
		     "scrollX": $(document.body).width(),
		     "scrollCollapse": false,
		     "deferRender":true,
		     //"scroller":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [0] }
	                     ],
	    	"fixedColumns":false,
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
		initEditFun_hr0307();
	
		$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
			initEditFun_hr0307();
		} );

});
function initEditFun_hr0307(){
	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$("#hr0307Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
        	$(this).html(val);
        	$("#hr0307Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
        	$(this).html(val);
        	$("#hr0301Check_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$(this).html(val);
			submitKeyClick_contract(val,index);
			 $("#hr0301Check_"+index,navTab.getCurrentPanel()).attr("checked","checked"); 
			this.editing = false;
		}
	});
}
function submitKeyClick_contract(obj,index){
	var empid=obj.replace(/[ ]/g," ");
	var personIdStr="contract";
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
					$("#POSITION_NAME_"+ index ,navTab.getCurrentPanel()).html(jsonObject.POSITION_NAME);
					$("#POST_GRADE_"+ index ,navTab.getCurrentPanel()).html(jsonObject.POST_GRADE_NAME);
					$("#DATE_STARTED_"+ index ,navTab.getCurrentPanel()).html(jsonObject.DATE_STARTED);
					$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',jsonObject.personId);
				} ;
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#EMPID_"+ index ,navTab.getCurrentPanel()).html("");
		$("#DEPT_NAME_"+ index ,navTab.getCurrentPanel()).html("");
		$("#LOCAL_NAME_"+ index ,navTab.getCurrentPanel()).html("");
		$("#POSITION_NAME_"+ index ,navTab.getCurrentPanel()).html("");
		$("#POST_GRADE_"+ index ,navTab.getCurrentPanel()).html("");
		$("#DATE_STARTED_"+ index ,navTab.getCurrentPanel()).html("");
		$("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',"");
	}
}

//计算合同时长
function calContractLength(index) {
	var date1 = $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
	var date2 = $("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
	var sql = "SELECT CASE " +
				"        WHEN TRUNC(MOD(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'YYYY.MM.DD') + 1, "  +
				"                TO_DATE('" + date1 + "', 'YYYY.MM.DD')), "  +
				" 12)) = 0 THEN "  +
				" TRUNC(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'YYYY.MM.DD') + 1, "  +
				"         TO_DATE('" + date1 + "', 'YYYY.MM.DD')) / 12) || '<spring:message code="hrm.empinfo.YEAR"/>' "  +//年
				" ELSE "  +
				" TRUNC(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'YYYY.MM.DD') + 1, "  +
				"         TO_DATE('" + date1 + "', 'YYYY.MM.DD')) / 12) || '<spring:message code="hrm.empinfo.YEAR"/>' || "  +//年
				" TRUNC(MOD(MONTHS_BETWEEN(TO_DATE('" + date2 + "', 'YYYY.MM.DD') + 1, "  +
				"             TO_DATE('" + date1 + "', 'YYYY.MM.DD')), "  +
				" 12)) || '<spring:message code="hrm.empinfo.MONTH"/>' "  +//月
				" END AS CONTRACT_LEN "  +
				" FROM DUAL " ;
	if(date1 != '' && date2 != ''){
		$.ajax({
			type: 'POST',
			url: '/hrm/recruitManage/doSql',
			data:{sql:sql},
			dataType:"json",
			cache: false,
			success: function(data){
				$("#CONTRACT_LEN_" + index,navTab.getCurrentPanel()).html(data.result[0].CONTRACT_LEN);
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#CONTRACT_LEN_" + index,navTab.getCurrentPanel()).html('');
	}
}

function validateCallbackUpdateContract() {
		var checked=false;
		var ids= document.getElementsByName("hr0307Check");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
				var index = ids[i].id.substring(12);
			var CONTRACT_TYPE_CODE_NAME = $("#CONTRACT_TYPE_CODE_" + index,navTab.getCurrentPanel()).html()
			if(CONTRACT_TYPE_CODE_NAME != '上海无固定合同' && CONTRACT_TYPE_CODE_NAME != '上海固定合同' && CONTRACT_TYPE_CODE_NAME != '无固定期限合同' && CONTRACT_TYPE_CODE_NAME != '非全日制合同'){	
				var date2 = $("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html();
				if (date2 == '') {
					alertMsg.error('<spring:message code="hrm.alert.empinfo.contract_endtime_notnull"/>');//合同结束日期不能为空!
					return;
				}
			}
			}
		}

		//获取页面的值
		var jsonData = '[';
		$("input[name='hr0307Check']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = obj.id.substring(12);
				jsonData += ' "CONTRACT_NO": "' + $("#CONTRACT_NO_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "PERSON_ID": "' + $("#EMPID_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId') + '" ,';
				jsonData += ' "CONTRACT_TYPE_CODE": "' + $("#CONTRACT_TYPE_CODE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "CONTRACT_TYPE": "' + $("#CONTRACT_TYPE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "TOTAL_PERIOD": "' + $("#TOTAL_PERIOD_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "TOTAL_PERIOD08": "' + $("#TOTAL_PERIOD08_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "START_CONTRACT_DATE": "' + $("#START_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "END_CONTRACT_DATE": "' + $("#END_CONTRACT_DATE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "REMARK": "' + $("#REMARK_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';
		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='hr.alert.message.viewPersonalInfo.checkBoxForChecked.xiugai' />");//请先选择信息在进行修改
			return;
		}
		alertMsg.confirm("<spring:message code='hrm.contractInfo.SURE_UPDATE.Z' />", {//确定要修改吗？
			okCall : function() {
				$.ajax({
					type: 'POST',
					url:"/hrm/contractInfo/updateContractInfo1",
					data: [{ name: 'jsonData', value: jsonData }],
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTab.reload('/hrm/contractInfo/viewContractUpdateList');
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
		
function validateCallbackAddContract(){
	 $.ajax({
			type:'post',
			url:'/hrm/contractInfo/addContractInfo1',
			dataType:null,
			success: function(data){ //请求成功后处理函数。
				navTab.reload('/hrm/contractInfo/viewContractUpdateList?firstFlag=N&type=add');
	   	 	}  ,
			error: DWZ.ajaxError});
}
		
function validateCallbackDeleteContract(OP_FLAG,form,callback) {
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
	var ids= document.getElementsByName("hr0307Check");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error("<spring:message code='display.alert.selectone' />"); //请选择一条记录
		return false;
	}
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete' />", {//确定要删除吗
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
/* 
function downloadExl(url) {
	$('#viewUpdateContractInfo', navTab.getCurrentPanel()).attr("action", url);
	$('#viewUpdateContractInfo', navTab.getCurrentPanel()).attr("onsubmit", '');
	$('#viewUpdateContractInfo', navTab.getCurrentPanel()).submit();
	$('#viewUpdateContractInfo', navTab.getCurrentPanel()).attr("action",'/hrm/contractInfo/viewContractUpdateList');
	$('#viewUpdateContractInfo', navTab.getCurrentPanel()).attr("onsubmit",'return navTabSearch(this);');
} */

function searchPop_hr0307(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	var refreshUrl = '/hrm/contractInfo/viewContractUpdateList?seach_FIRST_FLAG=1';
	var refreshMenuCode = 'hr0307';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="hrm.empinfo.contract_change"/>'));//合同变更
	$("#searchPop_hr0307", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='
							+ name
							+ '&refreshUrl='
							+ refreshUrl
							+ '&refreshMenuCode='
							+ refreshMenuCode
							+ '&refreshMenuName=' + refreshMenuName);
	if (flag == 'onkeyup')
		$("#searchPop_hr0307", navTab.getCurrentPanel()).click();
}
</script>
<div class="pageHeader">
	<form id="viewUpdateContractInfo" onsubmit="return navTabSearch(this);" action="/hrm/contractInfo/viewContractUpdateList?firstFlag=N" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 社号/姓名： -->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
				</td>
				<td>
					<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/>
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
					<a class="buttonActive" onclick="validateCallbackUpdateContract();" href="#"> 
						<span><spring:message code="hrm.contract.save" /><!-- 修改 --></span>
					</a>
				</li>
				<!--<li>
					<a class="buttonActive" onclick="validateCallbackAddContract();" href="#"> 
						<span><spring:message code="hrm.contract.add" /> 添加 </span>
					</a>				</li> -->
				<li>
					<a class="buttonActive" onclick="validateCallbackDeleteContract(0,'deleteContractInBatchForBatch',DWZ.ajaxDone);" href="#"> 
						<span><spring:message code="hrm.contract.delete" /><!-- 删除 --></span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<form name="deleteContractInBatchFrom" id="deleteContractInBatchForBatch" method="post" action="/hrm/contractInfo/deleteContractInfo1" > 
		<table class="orderList" width="99%">
			<thead>
				<tr>
					<th>
						<input type="checkbox" class="checkboxCtrl" group="hr0307Check" />
					</th>
					<th  class="titleColor">
						<!--合同次数-->
						<spring:message code="hr.viewPersonalInfo.title.TOTAL_PERIOD" />
					</th>
					<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">
						<th  class="titleColor" style="width: 120px">
							<spring:message code="hrm.contract.08_Sign_number"/><!-- 08年以后签订次数 -->
						</th>
					</c:if>
					<th>
						<!-- 姓名 -->
						<spring:message code="inct.salesman.Name" />
					</th>
					<%-- <th>
						<!-- 性别 -->
						<spring:message code="hrm.empinfo.SEXCODE" />
					</th>
					<th>
						<!-- 年龄 -->
						<spring:message code="hrm.empinfo.AGE" />
					</th> --%>
					<th  class="titleColor">
						<!-- 社号 -->
						<spring:message code="display.emp.ben.serviceno" />
					</th>
					<th  style="width: 150px">
						<!-- 部门 -->
						<spring:message code="public.title.deptName" />
					</th>
					<th>
						<!-- 职种 -->
						<spring:message code="hrm.contract.POSITION" />
					</th>
					<th>
						<!-- 职级 -->
						<spring:message code="hrm.contract.Rank" />
					</th>
					<th>
						<!-- 入社日期-->
						<spring:message code="hrm.empinfo.DATE_STARTED" />
					</th>
					<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">
						<th  class="titleColor"><spring:message code="hrm.contract.CONTRACT_Nature"/><!-- 合同性质 --></th>
					</c:if>
					<th class="titleColor">
						<spring:message code="hrm.contract.CONTRACT_TYPE" /><!-- 合同类型 -->
					</th>
					<th class="titleColor" style="width: 120px"><!--起始日期-->
						<spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/>
					</th>
					<th class="titleColor" style="width: 120px"><!--终止日期-->
						<spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/>
					</th>
					<th><spring:message code="hrm.contract.CHANGE_DATE" /><!-- 变更日期 --></th>
					<th>
						<spring:message code="hrm.contract.number_of_years" /><!-- 年数 -->
					</th>
					<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">
						<th><spring:message code="hrm.contract.jibengongzi"/><!-- 基本工资 --></th>
					</c:if>
					<th>
						<spring:message code="hrm.contract.WORK_TIME" />
						<!-- 工时 -->
					</th>
					<th class="titleColor">
						<spring:message code="hrm.empinfo.REMARK" /><!-- 备注 -->
					</th>
					<c:if test="${LoginUser.cpnyId eq 'SPC_SH'}">
						<th><spring:message code="hrm.contractInfo.QIANDING_REN.Z" /><!-- 签订人 --></th>
						<th><spring:message code="hrm.contractInfo.QIANDING_DATE.Z" /><!-- 签订时间 --></th>
					</c:if>
					<th>
						<spring:message code="is.joininstance.title.statement" /><!-- 状态 -->
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${itemList}" var="contractInfo" varStatus="i">
					<tr>
						<td class="td_center" style="text-align: center; padding-top: 7px;">
							<input type="checkbox" id="hr0307Check_${i.index}" name="hr0307Check" value="${contractInfo.CONTRACT_NO}" />
							<div id="CONTRACT_NO_${i.index}" sysIndex="${i.index}" name="hr0307Check1" style="display:none;">${contractInfo.CONTRACT_NO}</div>
						</td>
						<td sysLog="text"  sysIndex="${i.index}" id="TOTAL_PERIOD_${i.index}">${contractInfo.TOTAL_PERIOD}</td>
						<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">
							<td sysLog="text" sysIndex="${i.index}"  style="width: 120px" id="TOTAL_PERIOD08_${i.index}">${contractInfo.TOTAL_PERIOD08}</td>
						</c:if>
						<td style="text-align: center" id="LOCAL_NAME_${i.index}" >${contractInfo.LOCAL_NAME}</td>
<%-- 						<td style="text-align: center" id="SEXNAME_${i.index}">${contractInfo.SEXNAME}</td>
						<td style="text-align: center" id="AGE_${i.index}">${contractInfo.AGE}</td> --%>
						<td style="text-align: center" id="EMPID_${i.index}" <c:if test='${contractInfo.PERSON_ID eq null}'>sysLog="lookUp"</c:if> sysIndex="${i.index}" sysPersonId="${contractInfo.PERSON_ID}" >${contractInfo.EMPID}</td>
						<td style="text-align: center ;width: 150px" id="DEPT_NAME_${i.index}">${contractInfo.DEPARTMENT_NAME}</td>
						<td style="text-align: center" id="POSITION_NAME_${i.index}">${contractInfo.POSITION_NAME}</td>
						<td style="text-align: center" id="POST_GRADE_${i.index}">${contractInfo.POST_GRADE}</td>
						<td style="text-align: center" id="DATE_STARTED_${i.index}">${contractInfo.DATE_STARTED}</td>
						<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">
							<!-- <td></td> --><td sysLog="select" sysValue='${htxz}' id="CONTRACT_TYPE_${i.index}">${contractInfo.CONTRACT_TYPE_NAME}</td><!-- 合同性质 -->
						</c:if>
						<!-- <td>${contractInfo.CONTRACT_TYPE_CODE_NAME}</td> -->
						<td sysLog="select" sysValue='${htlx}' sysIndex="${i.index}" id="CONTRACT_TYPE_CODE_${i.index}">${contractInfo.CONTRACT_TYPE_CODE_NAME}</td>
						<td style="text-align:center;width: 120px;" sysLog="date"  sysFlag="contract" format="yyyy.MM.dd" sysIndex="${i.index}" id="START_CONTRACT_DATE_${i.index}">${contractInfo.START_CONTRACT_DATE}</td>
						<td style="text-align:center;width: 120px;" sysLog="date"  sysFlag="contract" format="yyyy.MM.dd" sysIndex="${i.index}" id="END_CONTRACT_DATE_${i.index}">${contractInfo.END_CONTRACT_DATE}</td>
						<td>${contractInfo.CHANGE_DATE}</td>
						<td style="text-align:center" id="CONTRACT_LEN_${i.index}">${contractInfo.CONTRACT_LEN}</td>
						<c:if test="${LoginUser.cpnyId eq 'SPC_SH' || LoginUser.cpnyId eq 'SPC_HZ'}">
							<td>${contractInfo.BASE_PAY}</td>
						</c:if>
						<td>${contractInfo.WORK_HOUR_NAME}</td>
						<td sysLog="text" sysIndex="${i.index}" id="REMARK_${i.index}">${contractInfo.REMARK}</td>
						<c:if test="${LoginUser.cpnyId eq 'SPC_SH'}">
							<td>${contractInfo.QIANDING_REN}</td>
							<td>${contractInfo.QIANDING_DATE}</td>
						</c:if>
						<td class='td_center'><img src="/resources/images/${contractInfo.ACTIVITY}.gif"></img></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<a id="onckSche" name="onckSche"  href="" lookupGroup="person"></a>
</div>
