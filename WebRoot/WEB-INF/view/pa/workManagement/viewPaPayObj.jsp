<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function downloadExl(url){
		$('#searchViewPaPayObjForm').attr("action",url) ;
		$('#searchViewPaPayObjForm').attr("onsubmit",'') ;
		$('#searchViewPaPayObjForm').submit() ;
		$('#searchViewPaPayObjForm').attr("action",'/pa/workManagement/viewPaPayObj') ;
		$('#searchViewPaPayObjForm').attr("onsubmit",'return navTabSearch(this);') ;
	}
	function searchPop(flag){
		var name=encodeURI(encodeURI($("#seach_KEY",navTab.getCurrentPanel()).val()));
		var scheduleNo=$('#PAY_SCHEDULE_NO',navTab.getCurrentPanel()).val();
		var INCLUDE_TYPE=$('#seach_INCLUDE_TYPE',navTab.getCurrentPanel()).val();
		var CREATE_TYPE=$('#seach_CREATE_TYPE',navTab.getCurrentPanel()).val();
		var refreshUrl = '/pa/workManagement/viewPaPayObj?PAY_SCHEDULE_NO='+scheduleNo+'&INCLUDE_TYPE='+INCLUDE_TYPE+'&CREATE_TYPE='+CREATE_TYPE ;
		var refreshMenuCode = 'pa0812' ;
		var refreshMenuName = encodeURI(encodeURI('<spring:message code="pa.viewPaMain.DUIXIANGTIAOZHENG.C" />')) ; //对象调整
		$("#searchPop",navTab.getCurrentPanel()).attr('href','/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='+name
				//+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName
				);
		if(flag == 'onkeyup')
			$("#searchPop",navTab.getCurrentPanel()).click();
	}
	function addPayInfo(url){
		$.pdialog.open(url+"?PAY_SCHEDULE_NO="+$('#PAY_SCHEDULE_NO',navTab.getCurrentPanel()).val()+"&schedualName="+encodeURI(jQuery("#PAY_SCHEDULE_NO  option:selected",navTab.getCurrentPanel()).text()), 
				"pa0812_add", "<spring:message code='pa.viewPaPayObj.DUIXIANGTIAOZHENGTIANJIA.C' />", {width:800,height:320,mask:true});//对象调整添加
	}
	function fillValueForUpdate(personId,includeType,empId){
		$("#personId",navTab.getCurrentPanel()).attr('value',personId);
		$("#includeType",navTab.getCurrentPanel()).attr('value',includeType);
		$("#empIdName",navTab.getCurrentPanel()).attr('value',empId);
	}
	function doUpdatePayObjAjax(url){
		var flag = false;
		var ids= document.getElementsByName("viewCheck");

		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				flag=true;
			}
		}
		if(flag == false){//请选择要修改的内容
			alertMsg.info("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QINGXUANZEXIUGAINEIRONG.b'/>");
			return false;
	    }
		
		var $form = $("#updatePaPayObjInfo");
		alertMsg.confirm("<spring:message code='hrm.contractInfo.SURE_UPDATE.Z' />",//确定要修改吗
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
	  				url:$form.attr("action"),
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: doAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});

	}
	
	function doDeletePayObjAjax(url){
		var flag = false;
		var ids= document.getElementsByName("viewCheck");

		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				flag=true;
			}
		}
		if(flag == false){//请选择要删除的数据
			alertMsg.info("<spring:message code='ar.alert.message.viewArAnnualStandard.chooseinfo'/>");
			return false;
	    }
		
		var $form = $("#updatePaPayObjInfo");
		alertMsg.confirm("<spring:message code='hrm.contractInfo.SURE_UPDATE.Z' />",//确定要修改吗
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
	  				url:'/pa/workManagement/doDeletePaPayObjInfo',
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: doAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});

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
			           return false; 
			       }else {
				        return true;
				   }   
		      }  
		 }  
	}
</script>
<div class="pageHeader">
	<form id="searchViewPaPayObjForm" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewPaPayObj" method="post" rel="pagerForm">
		<input type="hidden" id="personId" />
		<input type="hidden" id="empIdName" />
		<input type="hidden" id="includeType" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td><input
						type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)searchPop('onkeyup');"/>
						</td>
						<td>
						<a class="btnLook" id="searchPop" onclick="searchPop()" href="#" lookupGroup="person">
						</a>
					</td>
						<td colspan="3">
							<input id="dwz.person.empInfo"  type="text" readonly lookupGroup="person" size="60" value="${empInfo}"/> 
							<%-- ${empInfoShow } --%>
						</a>
					</td>
					</tr><tr>
					<td><!-- 工资支付计划--> <spring:message code="ess.empInfo.pay_plan" />:</td>
					<td>
						<select id="PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule" varStatus="i">
								<c:choose>
									<c:when test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option value="${paySchedule.PAY_SCHEDULE_NO }" selected="selected">${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
									</c:when>
									<c:otherwise>
										<option value="${paySchedule.PAY_SCHEDULE_NO }" >${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td><!-- 区分 --> <spring:message code="display.emp.ben.or.benhs67" />:</td>
					<td>
						<select id="seach_INCLUDE_TYPE" name="seach_INCLUDE_TYPE" >
							<option value="" ><!-- 全部 --> <spring:message code="pa.salary.canShu.quanBu" /></option>
							<option value="1"  <c:if test="${INCLUDE_TYPE eq '1' }">selected</c:if>><!--包括 --> <spring:message code="pa.viewPaPayObj.BAOKUO.C" /></option>
							<option value="0"  <c:if test="${INCLUDE_TYPE eq '0' }">selected</c:if>><!-- 不包括 --> <spring:message code="pa.viewPaPayObj.BUBAOKUO.C" /></option>
						</select>
					</td>
					<td class="text"><spring:message code="hrm.empinfo.EMP_OFFICE_NAME" /> <!-- 员工状态 --> </td>
                    <td class="td_type">
                        <select id="seach_EMP_OFFICE" name="seach_EMP_OFFICE" >
                            <option value="" ><!-- 全部 --> <spring:message code="pa.salary.canShu.quanBu" /></option>
                            <option value="15119"  <c:if test="${EMP_OFFICE eq '15119' }">selected</c:if>><!--在职 --> <spring:message code="hrm.empinfo.Jobemp_number" /></option>
                            <option value="15120"  <c:if test="${EMP_OFFICE eq '15120' }">selected</c:if>><!-- 离职 --> <spring:message code="hrm.empinfo.retiredemp_number" /></option>
                        </select>
                    </td>
					<!--<td> 工资类型  <spring:message code="hrm.recruitManage.Wage_type" />:</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_WAGE_TYPE" selected="${WAGE_TYPE}" parentNo="14643" limit="all" />
					</td>-->
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<!-- <a class="buttonActive"  href="/pa/workManagement/addPaPayObj?PAY_SCHEDULE_NO=" target="dialog" mask="true" 
							width="800" 
							height="400"> -->
						<a class="buttonActive" onclick="addPayInfo('/pa/workManagement/addPaPayObj')" href="#" >
							<span><!-- 添加--> <spring:message code="button.add" /></span>
						</a>
					</li>
					<li>
						<!--<a class="buttonActive" title="<spring:message code='pa.viewPaPayObj.QUEDINGSHIFOUSHANCHU.C' />?" callback="doAjaxDoneWithForm" href="/pa/workManagement/doDeletePaPayObjInfo?PAY_SCHEDULE_NO=${PAY_SCHEDULE_NO}&PERSON_ID={PERSON_ID}" target="ajaxTodo">
							<span> 删除  <spring:message code="button.delete" /></span>
						</a>-->
						<a class="buttonActive"  onclick="doDeletePayObjAjax();" href="#" >
							<span><!--删除--><spring:message code="button.delete" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"  onclick="doUpdatePayObjAjax();" href="#" >
							<span><!--保存--><spring:message code="button.sys.affirm.save" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=7')" href="#" >
							<span><!-- 导出到Excel--> <spring:message code="hrm.empinfo.EXPORT" /></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
<form id="updatePaPayObjInfo" method="post"
	action="/pa/workManagement/updatePaPayObjInfo"
	class="pageForm required-validate">
	<table class="table"  id="daTable" width="100%" layoutH="171">
		<thead>
			<tr>
				<th width="20">
				    <input type="checkbox" class="checkboxCtrl" group="viewCheck" />
				</th>
			
				<th>No.</th>
				<th><!-- 姓名--> <spring:message code="alert.pa.pasalarycanshu.xingming" /></th>
				<th><!--工号--> <spring:message code="ess.infoApply.EMP_ID" /></th>
				<th><!-- 部门名 --> <spring:message code="ess.infoApply.DEPT_NAME" /></th>
				<!--<th> 工资类型  <spring:message code="hrm.recruitManage.Wage_type" /></th>-->
				<th><!-- 区分 --> <spring:message code="display.emp.ben.or.benhs67" /></th>
				<th><!-- 创建者--> <spring:message code="sys.basic.title.createBy" /></th>
				<th><!-- 创建时间--> <spring:message code="sys.basic.title.createDate" /></th>
				<th><!-- 变更者--> <spring:message code="hrm.empinfo.UPDATED_BY" /></th>
				<th><!-- 变更时间--> <spring:message code="hrm.empinfo.UPDATE_DATE" /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${PaPayObjList}" var="PayObj"
			varStatus="i">
			
			<tr target="PERSON_ID" rel="${PayObj.PERSON_ID}">
				<td style="text-align: center;padding-top:7px;">
					<input id="viewCheck${i.index}" type="checkbox" name="viewCheck" value="${i.index}"/>
					<input type="hidden" name="PERSON_ID_${i.index}" value="${PayObj.PERSON_ID}" />
					<input type="hidden" name="PAY_SCHEDULE_NO_${i.index}" value="${PAY_SCHEDULE_NO}" />
				</td>
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">${PayObj.EMPNAME}</td>
				<td style="text-align: center">${PayObj.EMPID}</td>
				<td style="text-align: center">${PayObj.DEPTNAME}</td>
				<!--<td style="text-align: center">
					<ait:SelectSyCodeByCpnyID name="WAGE_TYPE_${i.index}" selected="${PayObj.WAGE_TYPE}" parentNo="14643" limit="all" onChangeName="$('#viewCheck${i.index}',navTab.getCurrentPanel()).attr('checked','checked');"/>
				</td>-->
				<td style="text-align: center">
					<select id="INCLUDE_TYPE" name="INCLUDE_TYPE_${i.index}" onchange="$('#viewCheck${i.index}',navTab.getCurrentPanel()).attr('checked','checked');fillValueForUpdate('${PayObj.PERSON_ID}',this.value,'[${PayObj.EMPID}]${PayObj.EMPNAME}')">
						<option value="1"  <c:if test="${PayObj.INCLUDE_TYPE == 1 }">selected</c:if>><!-- 包括 --> <spring:message code="pa.viewPaPayObj.BAOKUO.C" /></option>
						<option value="0"  <c:if test="${PayObj.INCLUDE_TYPE == 0 }">selected</c:if>><!--不包括--> <spring:message code="pa.viewPaPayObj.BUBAOKUO.C" /></option>
					</select>
				</td>
				<td style="text-align: center">${PayObj.CREATED_BY}</td>
				<td style="text-align: center">${PayObj.CREATE_DATE}</td>
				<td style="text-align: center">${PayObj.UPDATED_BY}</td>
				<td style="text-align: center">${PayObj.UPDATE_DATE}</td>
			</tr>
		</c:forEach>
		<c:if test="${totalCount == 0 }">
			<tr>
				<td style="text-align: left;" colspan="9"><!-- 没有查找的数据--> <spring:message code="pa.viewPaPayObj.MEIYOUCHAZHAODESHUJU.C" /></td>
			</tr>
		</c:if>
		</tbody>
	</table>
	</form>
	<c:set value="/pa/workManagement/viewPaPayObj" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>

</div>
