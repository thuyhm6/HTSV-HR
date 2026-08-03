<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>

$(document).ready(function(){
	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": false,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": true,
        "scrollX": true,
        "orderClasses": false
	});
	//查询
	$("#viewDeptManagerCheck_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewDeptManagerCheckForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewDeptManagerCheck_ResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewDeptManagerCheckForm",navTab.getCurrentPanel()).submit();
	});
    $('#dePtManageTree',navTab.getCurrentPanel()).treeTable();
	//保存
	$("#viewDeptManagerCheck_save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input[name='IS_PART_TIME']",navTab.getCurrentPanel()).each(function(i, obj){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var IS_PART_TIME = 0;
			if(obj.checked){
				IS_PART_TIME = 1;
			}
			jsonData += ' "DEPTNO": "' + $("#viewDeptManagerCheck_DEPTNO_" + i,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "IS_PART_TIME": "' + IS_PART_TIME + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ,';
			jsonData += ' "RESUME_NO": "' + '${RESUME_NO}' + '" ,';
			jsonData += ' "MANAGER_PERSON_ID": "' + $("#viewDeptManagerCheck_PERSON_ID_" + i,navTab.getCurrentPanel()).val() + '" ';
			jsonData += '}';
		});
		jsonData += ']';

		if (jsonData.length == 2) {
			alertMsg.info('<spring:message code="org.title.NOTSAVE_DATA" />');
			return;
		}

		alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/org/orgManage/saveDeptManager',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: divAjaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	$('.list tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$(this).html(val);
			submitKeyClick_org(val,index);
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
});


//显示下拉框
function viewDeptManagerCheck_display_no(index){
	$("input[name='IS_PART_TIME']",navTab.getCurrentPanel()).each(function(i, obj){
		if( i != index){
			viewDeptManagerCheck_display_name(i);
		}
	});
	$("#viewDeptManagerCheck_ADD_DIV_" + index,navTab.getCurrentPanel()).show();
	$("#viewDeptManagerCheck_DISPLAY_DIV_" + index,navTab.getCurrentPanel()).hide();
}
//隐藏下拉框
function viewDeptManagerCheck_display_name(index){
	$("#viewDeptManagerCheck_ADD_DIV_" + index,navTab.getCurrentPanel()).hide();
	$("#viewDeptManagerCheck_DISPLAY_DIV_" + index,navTab.getCurrentPanel()).show();
}

//人员弹框
var keyCodeInit=0;
function submitKeyClick_viewDeptManagerCheck(obj,index,event){
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid ));
			document.getElementById("onck").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
							if(jsonObject.perCnt != 1 ){
								document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
										+'&seach_KEY='+empid 
										+'&empidStr='+index 
										+'&personidStr=viewDeptManagerCheck' ));
								document.getElementById("onck").click();
							}
							if(jsonObject.perCnt==1){
								//隐藏文本框，显示文本
								viewDeptManagerCheck_display_name(index);
							  	$("[id='viewDeptManagerCheck_PERSON_ID_" + index + "']").val(jsonObject.personId);
							  	$("[id='viewDeptManagerCheck_EMP_NAME_" + index + "']").val(jsonObject.empName);
							  	$("[id='viewDeptManagerCheck_DISPLAY_DIV_" + index + "']").html(jsonObject.empName);
							  	$("[id='viewDeptManagerCheck_EMP_ID_DIV_" + index + "']").html(jsonObject.empId);
							  	$("#viewDeptManagerCheck_vacancy_" + index).removeAttr("checked");
							}
						},
				error: DWZ.ajaxError
			});
		}
    }
 }

function changeVacancy(index,obj){
	if(obj.checked){
	  	$("#viewDeptManagerCheck_PERSON_ID_" + index).val("");
	  	$("#viewDeptManagerCheck_EMP_NAME_" + index).html("<spring:message code='org.title.Vacancy' />");//Vacancy
	  	$("#viewDeptManagerCheck_DISPLAY_DIV_" + index).html("<spring:message code='org.title.Vacancy' />");
	  	$("#viewDeptManagerCheck_EMP_ID_DIV_" + index).html("<spring:message code='org.title.Vacancy' />");
	  	$("#viewDeptManagerCheck_POST_GRADE_NAME_" + index).html("");
	  	$("#viewDeptManagerCheck_S_BAND_" + index).html("");
	  	$("#viewDeptManagerCheck_BUSINESS_NAME_" + index).html("");
	  	$("#viewDeptManagerCheck_EMP_OFFICE_NAME_" + index ).html("");
	  	$("#viewDeptManagerCheck_IS_PART_TIME_" + index).removeAttr("checked");
	}else{
		$("#viewDeptManagerCheck_PERSON_ID_" + index).val("");
	  	$("#viewDeptManagerCheck_EMP_NAME_" + index).html("");
	  	$("#viewDeptManagerCheck_DISPLAY_DIV_" + index).html("");
	  	$("#viewDeptManagerCheck_EMP_ID_DIV_" + index).html("");
	  	$("#viewDeptManagerCheck_POST_GRADE_NAME_" + index).html("");
	  	$("#viewDeptManagerCheck_S_BAND_" + index).html("");
	  	$("#viewDeptManagerCheck_BUSINESS_NAME_" + index).html("");
	  	$("#viewDeptManagerCheck_EMP_OFFICE_NAME_" + index ).html("");
	  	$("#viewDeptManagerCheck_IS_PART_TIME_" + index).removeAttr("checked");
	}
}
function submitKeyClick_org(obj,index){
	var empid=obj.replace(/[ ]/g," ");
	var personIdStr="org";
	if(empid != ''){
	   	$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
				 if(jsonObject.perCnt != 1 ){
					document.getElementById("onckSche").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmEvsList?isEmployeement=1&limit=super&pageNum=1"
							+'&seach_KEY='+empid+'&personidStr='+personIdStr + '&index=' + index));
					document.getElementById("onckSche").click();
				}
				if(jsonObject.perCnt==1){
					$("#viewDeptManagerCheck_EMP_ID_DIV_"+ index ,navTab.getCurrentPanel()).html(jsonObject.empId);
					$("#viewDeptManagerCheck_BUSINESS_NAME_"+ index ,navTab.getCurrentPanel()).html(jsonObject.POST_GRADE_NAME);
					$("#viewDeptManagerCheck_POST_GRADE_NAME_"+ index ,navTab.getCurrentPanel()).html(jsonObject.POSITION_NAME);
					$("#viewDeptManagerCheck_EMP_NAME_"+ index ,navTab.getCurrentPanel()).html(jsonObject.empName);
					$("#viewDeptManagerCheck_EMP_OFFICE_NAME_"+ index ,navTab.getCurrentPanel()).html(jsonObject.STATUS_NAME);
					$("#viewDeptManagerCheck_EMP_NAME_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',jsonObject.personId);
					$("#viewDeptManagerCheck_PERSON_ID_"+ index ,navTab.getCurrentPanel()).val(jsonObject.personId);
					$("#viewDeptManagerCheck_vacancy_" + index).removeAttr("checked");
				} ;
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#viewDeptManagerCheck_EMP_ID_DIV_"+ index ,navTab.getCurrentPanel()).html("");
		$("#viewDeptManagerCheck_BUSINESS_NAME_"+ index ,navTab.getCurrentPanel()).html("");
		$("#viewDeptManagerCheck_POST_GRADE_NAME_"+ index ,navTab.getCurrentPanel()).html("");
		$("#viewDeptManagerCheck_EMP_NAME_"+ index ,navTab.getCurrentPanel()).html("");
		$("#viewDeptManagerCheck_EMP_OFFICE_NAME_"+ index ,navTab.getCurrentPanel()).html("");
		$("#viewDeptManagerCheck_EMP_NAME_"+ index ,navTab.getCurrentPanel()).attr('sysPersonId',"");
		$("#viewDeptManagerCheck_PERSON_ID_"+ index ,navTab.getCurrentPanel()).val("");
	}
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
}
</script>
<div class="pageHeader">
<form id="viewDeptManagerCheckForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewDeptManagerCheck" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
						
		<td><spring:message code="org.title.VERSION_NAME" /><!-- 版本名称 --></td>
		<td>
			<select id="viewResumeProcessResumeNo" name="RESUME_NO">
			<c:forEach items="${orgResumeList}" var="result">
				<option value="${result.NO}" <c:if test="${result.NO eq RESUME_NO}">selected</c:if>>${result.NO }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${result.RESUME_NAME}</option>
			</c:forEach>
				</select>
		</td>
				
		<td><!-- 部门： --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> 
		</td>
		<td>
			<ait:resumeDeptList name="seach_DEPTNO" resumeNo="${RESUME_NO}" id="viewDeptManagerCheck_deptList"/>
			<ait:resumeDeptTreeIcon name="seach_DEPTNO" resumeNo="${RESUME_NO}"  id="viewDeptManagerCheck_deptList" selected="${DEPTNO}"/>
		</td>
		<td><!-- 工号/姓名： --> <spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td><input type="text" name="seach_KEY" value="${KEY}" /></td>
		<td><spring:message code="org.title.ERROR_DISCRIMINATION" /><!-- 错误区分 --></td>
		<td>
			<select name="seach_ERROR_TYPE">
				<option value=""><spring:message code="org.title.PLEASE_SELECT" /></option>
				<option value="D" <c:if test="${ERROR_TYPE eq 'D' }">selected</c:if>><spring:message code="org.title.REPEAT_SERVICE" /><!-- 重复任职者 -->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;D</option>
				<option value="N" <c:if test="${ERROR_TYPE eq 'N' }">selected</c:if>><spring:message code="org.title.NOT_CONFIGURED_SECTOR" /><!-- 未配置部门 -->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;N</option>
				<option value="F" <c:if test="${ERROR_TYPE eq 'F' }">selected</c:if>><spring:message code="org.title.ONLY_EXIST_PARTTIME" /><!-- 只存在兼职 -->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;F</option>
			</select>
		</td>
	</tr>
</table>
</div>
</form>
</div>

<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" id="viewDeptManagerCheck_Serch" href="#">
					<span><spring:message code="org.title.SELECT" /><!-- 查询 --></span>
				</a>
			</li>
			<c:if test="${resumeActivityInfo.ACTIVITY != '14013947'}">
				<li>
					<a class="buttonActive" id="viewDeptManagerCheck_save" href="#"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
				</li>
			</c:if>
			<li>
				<a class="add" href="javascript:$.printBox()"><span><spring:message code="org.title.PRINT" /><!-- 打印 --></span></a>
			</li>
			<li>
				<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=6&RESUME_NO=${ RESUME_NO}"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>					
			</li>
			<li>
				<a class="buttonActive" href="/org/orgManage/viewResumeProcess?RESUME_NO=${RESUME_NO}" 
						target="navTab" rel="org0202" title='<spring:message code="org.title.ADAPTATION_PROCESS" />'><span><spring:message code="org.title.NEXT_STAGE" /><!-- 下阶段 --></span></a>
			</li>
	</ul>
</div>

<div class="pageContent" sysLong="printDiv">
				<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${deptManagerCheckCnt}</div>
				<table class="list" width="99%" id="dePtManageTree">
					<thead>
						<tr>
							<th width="160px"><spring:message code="org.title.dept" /><!-- 部门 --></th>
							<th width="80px" class="titleColor"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></th>
							<th width="40px"><spring:message code="org.title.Vacancy" /><!-- Vacancy --></th>
							<th width="80px"><spring:message code="org.title.EMPID" /><!-- 工号 --></th>
							<th width="80px"><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职务 --></th>
							<th width="80px"><spring:message code="org.title.POSITION_NO" /><!-- 职责 --></th>
							<th width="80px"><spring:message code="org.title.EMP_OFFICE_NAME" /><!-- 员工状态 --></th>
							<th width="80px"><spring:message code="org.title.IS_PART_TIME" /><!-- 兼职与否 --></th>
							<th width="160px"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
							<th width="80px"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${deptManagerCheckList}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}" <c:if test="${(item.DEPTNO ne DEPTNO and item.PARENT_DEPT_NO ne '0') and (ERROR_TYPE eq '' or ERROR_TYPE eq	 null)}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td>${item.DEPTNAME}</td>
								<%-- <td>
									<div id="viewDeptManagerCheck_ADD_DIV_${i.index}" style="display:none;">
										<input id="viewDeptManagerCheck_PERSON_ID_${i.index}" name="MANAGER_PERSON_ID" value="${item.MANAGER_EMP_ID}" type="hidden"/>
										<input id="viewDeptManagerCheck_EMP_NAME_${i.index}" name="EMP_NAME" type="text" value="${item.LOCAL_NAME}"  alt='<spring:message code="org.title.INPUT_KEY_SELECT"/>'
											onkeydown="submitKeyClick_viewDeptManagerCheck(this,${i.index},event)"/>
										<a class="btnLook" style="float:right;" lookupGroup="person" href="/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1&empidStr=${i.index}&personidStr=viewDeptManagerCheck">
										<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
		 							</div>
									<div id="viewDeptManagerCheck_DISPLAY_DIV_${i.index}" onclick="viewDeptManagerCheck_display_no(${i.index});">
										${item.LOCAL_NAME}
									</div>
								</td> --%>
								<td  style="text-align: center" id="viewDeptManagerCheck_EMP_NAME_${i.index}" sysLog="lookUp" sysIndex="${i.index}"  sysPersonId="${item.PERSON_ID}">${item.LOCAL_NAME}</td>
								<input id="viewDeptManagerCheck_PERSON_ID_${i.index}" name="MANAGER_PERSON_ID" value="${item.MANAGER_EMP_ID}" type="hidden"/>
								<td class='td_center'>
									<input type="hidden" id="viewDeptManagerCheck_DEPTNO_${i.index}" name="DEPTNO" value="${item.DEPTNO}">
									<input type="checkbox" id="viewDeptManagerCheck_vacancy_${i.index}" name="VACANCY" value="1" onclick="changeVacancy(${i.index},this);"
										<c:if test="${item.VACANCY eq 'VACANCY' }">checked</c:if>/>
								</td>
								<td class='td_center'>
									<div id="viewDeptManagerCheck_EMP_ID_DIV_${i.index}">
										${item.EMPID}
									</div>
								</td>
								<td class='td_center'>
									<div id="viewDeptManagerCheck_BUSINESS_NAME_${i.index}">
										${item.POST_GRADE_NAME}
									</div>
								</td>
								<td class='td_center'>
									<div id="viewDeptManagerCheck_POST_GRADE_NAME_${i.index}">
										${item.POSITION_NO_NAME}
									</div>
								</td>
								<td class='td_center'>
									<div id="viewDeptManagerCheck_EMP_OFFICE_NAME_${i.index}">
										${item.STATUS_CODE_NAME}
									</div>
								</td>
								<td class='td_center'>
									<input type="checkbox" id="viewDeptManagerCheck_IS_PART_TIME_${i.index}" name="IS_PART_TIME" value="1"
										<c:if test="${item.IS_PART_TIME eq '1' }">checked</c:if>/>
								</td>
								<td class='td_center'>${item.UPDATED_BY} ${item.UPDATED_IP}</td>
								<td class='td_center'>${item.UPDATE_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
<a id="onck" name="onck"  lookupGroup="person"  href="" rel="submitKeyClick_viewDeptManagerCheck_dialog"></a>
<a id="onckSche" name="onckSche"  href="" lookupGroup="person"></a>
