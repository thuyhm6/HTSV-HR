<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
	function pageFromSea(a){                         
		
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_START_DATE=$("#seach_START_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_START_DATE",navTab.getCurrentPanel()).val();
	var seach_END_DATE=$("#seach_END_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_END_DATE",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_STATUS=$("#seach_AFFIRM_STATUS",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_AFFIRM_STATUS",navTab.getCurrentPanel()).val();
	var seach_DOOR_TYPE=$("#seach_DOOR_TYPE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DOOR_TYPE",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/recordApply/viewArMacRecordInfoList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_START_DATE="+seach_START_DATE+"&seach_END_DATE="+seach_END_DATE+"&seach_AFFIRM_STATUS="+seach_AFFIRM_STATUS+"&seach_DOOR_TYPE="+seach_DOOR_TYPE);
}


function delArMacApplyCallback(form,callback) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	
		//选中检查
	var checked=false;
	var ids= document.getElementsByName("AR_MAC_DEL");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行添加操作!
		alert("<spring:message code='ar.alert.message.viewdynamicgroup.choosepersonforadd'/>");
		return false;
	}
	$form.attr("action","/ess/recordApply/delArMacRecordApplyInfo?APPLY_TYPE=218294");

	alertMsg.confirm("确定要删除吗?",{okCall:function(){
				$.ajax({
					type: form.method || 'POST',
					url:$form.attr("action"), 
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch("viewArMacRecordInfoList");
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
	        }});
	return false;
}

function cancelCardApply(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	});
	alertMsg.confirm("确定要取消吗?",{okCall:function(){
		$.ajax({
		  url: '/ess/recordApply/cancelCardApply',
		  data: params,
		  cache: false,
		  success: function(responseText){
			if (responseText == "Y"){
				alertMsg.info("取消成功！");
				//页面重载
				navTabSearch("viewArMacRecordInfoList");
			}else{
				alertMsg.info("取消失败！");
			}
		  }
		});
    }});
}
</script>
<div class="pageHeader">
	<form id="viewArMacRecordInfoList" onsubmit="return navTabSearch(this);" action="/ess/recordApply/viewArMacRecordInfoList" rel="pagerForm" method="post">
	<div class="searchBar">
		<table class="searchContent">
		    <tr>
					<c:if test="${authority ne '1'}">
					<td>社号/姓名</td>
					<td>
						${LoginUser.empID }/${LoginUser.localName }
						<input type="hidden" id="seach_PERSON_ID" name="seach_PERSON_ID" value="${LoginUser.personId }"/>
					</td>
					<td>
						部门
					</td>
					<td>
						${LoginUser.content }
					</td>
					</c:if>
					<c:if test="${authority eq '1'}">
					<td><!-- 社号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td> 
						<c:if test="${FLAG eq '1'}">
							<input type="text" name="seach_KEY" value="${KEY}" />
						</c:if>
						<c:if test="${FLAG ne '1'}">
							<input type="text" name="seach_KEY" value="${LoginUser.empID}" />
						</c:if>
						<input type="hidden" id="seach_FLAG" name="seach_FLAG" value="1"/>
					</td>
					<td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyMacInfoList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyMacInfoList_seachDept" selected="${DEPT_NO}"/></td>
					</c:if>
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="ess0204_limit" name="limit" value="ar">
						<input type="hidden" id="ess0204_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="ess0204_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,ess0204_seach_JobTypeGroupNo,ess0204_seach_EmpTypeCodeNo,ess0204_seach_CPNY,ess0204_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="ess0204_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>
			</tr>
			<tr>
					
                <td>
                    <spring:message code="public.title.startDate"/><!-- 开始日期 -->
                </td>			
			    <td>
			        <input type="text" id="seach_START_DATE" name="seach_START_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${START_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                     <spring:message code="public.title.endDate"/><!-- 结束日期 -->
                </td>			     
				<td>
				    <input type="text" id="seach_END_DATE" name="seach_END_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${END_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 
			    <td>在职状态</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
				<td>进/出门</td>				
				<td>
					<select id="seach_DOOR_TYPE" name="seach_DOOR_TYPE">
						<option value=""><!--请选择:-->
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="IN" <c:if test="${DOOR_TYPE eq 'IN' }">selected</c:if>>进门(IN)</option>
						<option value="OUT" <c:if test="${DOOR_TYPE eq 'OUT' }">selected</c:if>>出门(OUT)</option>
					</select>       
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="ess.viewApply.title.affirmStatus"/><!-- 决裁状态 -->
				</td>				
				<td>
				     <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>未决裁</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>决裁中</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>已通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>已否决</option>
					</select>
				</td>
			</tr>
			
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent">
				    <button type="submit">
				       <spring:message code="public.title.search"/><!-- 检索 -->
				    </button>
			        </div>
			        </div>
			    </li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent" >
	<div class="formBar">
			<ul>
				<c:if test="${toolbarInfo.INSERTR == '1'}">
					<li>				    
					    <a class="button" href="/ess/recordApply/addArCardRecordApplyView?APPLY_TYPE_NO=218294&&BIAO=A&&PERSON_ID=${LoginUser.personId}" 
					     target="dialog"  width="1100" height="500" mask="true" rel="arMacRecordApply_window">
					    <span><spring:message code="ess.infoApply.title.apply"/><!-- 申请 --></span>
					    </a>
				    </li>
			    </c:if>
				<c:if test="${toolbarInfo.DELETER == '1'}">
				    <li>
						<a class="button" onclick="delArMacApplyCallback('delArMacRecordApplyInfoFrom',DWZ.ajaxDone)" href="#" ><span>删除</span></a>			
				   	</li>
			   </c:if>
			</ul>
		</div>
		
		</div>


<div class="pageContent" >
	<form name="delArMacRecordApplyInfoFrom" id="delArMacRecordApplyInfoFrom" method="post" action="/ess/recordApply/delArMacRecordApplyInfo?APPLY_TYPE=218294" 
	  onsubmit="return delLOtApplyCallback(this, navTabAjaxDone);"> 
		<table class="table" width="100%" layoutH="260" nowrapTD="false">
			<thead>
				<tr>
					<th width="30">
				    	<input type="checkbox" class="checkboxCtrl" group="AR_MAC_DEL" />
				    </th>
				   	<th width="60"><!-- 社号 -->
						社号
					</th>
				    <th width="60"><!-- 姓名 -->
						姓名
					</th>
					<th width="80"><!-- 部门 -->
						部门
					</th>
					<th width="80"> <!--漏刷卡时间-->
						漏刷卡时间
					</th>
					<th width="60"><!--打卡时间-->
						打卡时间
					</th>
					<th width="60"><!--进出门类型-->
						进出门类型
					</th>
					
					<th width="80"><!--申请事由-->
						申请事由
					</th>
					<th width="60"><!--申请日期-->
						申请日期
					</th>
					<th width="60"><!--附件查看-->
						附件查看
					</th>
					<th width="60"><!--详细查看-->
						详细查看
					</th>
					<th width="60"><!--决裁状态-->
						决裁状态
					</th>
					<th width="60"><!--是否取消-->
						是否取消
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${arMacRecordList}" var="arMacRecord" varStatus="i">		
				    
					<tr target="sid" rel="${arMacRecord.PERSON_ID }">
					    <td style="text-align: center">
					    	<c:if test="${arMacRecord.AFFIRM_FLAG eq '-1' || arMacRecord.AFFIRM_FLAG eq '0'}">
					        	<input type="checkbox" id="AR_MAC_DEL" name="AR_MAC_DEL" value="${arMacRecord.APPLY_NO }" />
					        </c:if>
					    </td>
					    <td style="text-align: center">
					    	 ${arMacRecord.EMPID }
					    </td>
					    <td style="text-align: center">${arMacRecord.LOCAL_NAME}</td>
					    <td style="text-align: center">${arMacRecord.DEPT_NAME}</td>
					    <td style="text-align: center">${arMacRecord.R_TIME_LD}</td>
					    	
						<td style="text-align: center">${arMacRecord.R_TIME_LT}</td>
						<td style="text-align: center">${arMacRecord.DOOR_TYPE}</td>
						
						<td style="text-align: left">
							${arMacRecord.REMARK}
						</td>
						<td style="text-align: center">${arMacRecord.APPLY_DATE}</td>
						<td style="text-align: center">
							<c:forEach items="${arMacRecord.fileList}" var="file" varStatus="j">	
								<div style="display:block;line-height:30px;"><a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a></div>
							</c:forEach>
						</td>
						<td style="text-align: center">
							<a rel="otApplyAffirm" href="/ess/recordApply/viewFullApplyAffirmInfo?seach_APPLY_TYPE_NO=218294&seach_APPLY_NO=${arMacRecord.APPLY_NO }" title="详细查看"
					          target="dialog" mask="true" width="950" height="450" id="otApplyRemarkHref" >查看</a>
						</td>
						<td style="text-align: center">
							<c:if test="${arMacRecord.AFFIRM_FLAG eq '-1'}">
								<font color="blue">暂存</font>
							</c:if>
							<c:if test="${arMacRecord.AFFIRM_FLAG eq '0'}">
								<font color="back">未审批</font>
							</c:if>
							<c:if test="${arMacRecord.AFFIRM_FLAG eq '1'}">
								<font color="green">通过</font>
							</c:if>
							<c:if test="${arMacRecord.AFFIRM_FLAG eq '2'}">
								<font color="red">否决</font>
							</c:if>
							<c:if test="${arMacRecord.AFFIRM_FLAG eq '3'}">
								<font color="grey">取消</font>
							</c:if>
							<c:if test="${arMacRecord.AFFIRM_FLAG eq '4'}">
								<font color="grey">审批中</font>
							</c:if>
						</td>	
						<td style="text-align: center">
						 	<c:if test="${arMacRecord.AFFIRM_FLAG eq '1' and arMacRecord.LASTMONTH_YN eq 'N'}">
								<a href="#" title="取消" onclick="cancelCardApply('${arMacRecord.APPLY_NO}')" style="cursor: hand">
									<font color="red">取消</font>
								</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
    <div id="arMacRecordApplyFullDescpView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
    <c:set value="/ess/recordApply/viewArMacRecordInfoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>