<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
 //var type=$(#"activity_type").attr("value");
// $("input[name=ACTIVITY]:eq()").attr("checked",'checked');
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 290,
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
	     //"scroller":true,
        "oLanguage": {//多语言配置
            //正在加载中......
        	"sProcessing": "<spring:message code='ess.message.loading' />",
            //查询不到相关数据！
            "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
            //表中无数据存在
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
        "buttons": [
              ] 
	});
});
function changeURL_ess3004(href,title){
	$.pdialog.open(href,"ess3004", title, {width:900,height:420,mask:true});
}
</script>
<div class="panel">
	<h1>
		<spring:message code="ess.empInfo.change_detail_query" />
		<!-- 变更明细查询 -->
	</h1>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/empinfo/viewEssApplyInfo" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="ess.workgroup.title.duration"/>
						<!--期间-->
						：
					</td>
					<td>
						<input type="text" name="seach_sDate" class="Wdate"
							value="${sDate}" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"readonly="true" />
					</td>
					<td>
						~
					</td>
					<td>
						<input type="text" name="seach_eDate" class="Wdate"
							value="${eDate}" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"readonly="true" />
					</td>
					<input type="hidden" id="activity_type" value="${activity_type}"/>
					<td><!--进行状态： --><spring:message code="ess.empInfo.carry_on_state" /></td>
					<td>
						<ul >
							<li style="float:left">
							<input type="radio" name="seach_ACTIVITY" ${ACTIVITY eq '' ? 'checked':''} value="" ><spring:message code="ess.infoApply.whole" /><!-- 全部 -->&nbsp;
							</li>
							<li style="float:left">
						<input type="radio" name="seach_ACTIVITY" ${ACTIVITY eq '1' ? 'checked':''}  value="${1}" >&nbsp;<!--提交 --><spring:message code="ess.empinfo.SUBMIT.Z" />&nbsp;<!-- 提交状态  -->
							</li>
							<li style="float:left">
						<input type="radio" name="seach_ACTIVITY" ${ACTIVITY eq '2' ? 'checked':''} value="${2}" >&nbsp;<!--通过 --><spring:message code="ess.infoApply.adopt" />&nbsp;<!-- 审批状态  -->
							</li>
							<li style="float:left">
						<input type="radio" name="seach_ACTIVITY"  ${ACTIVITY eq '3' ? 'checked':''} value="${3}" >&nbsp;<!--驳回 --><spring:message code="ess.empInfo.Reject" />&nbsp;<!-- 退回状态  -->
							</li>
							<li style="float:left">
<%-- 						<input type="radio" name="seach_ACTIVITY" ${ACTIVITY eq '4' ? 'checked':''} value="${4}" >&nbsp;Expired&nbsp;<!-- 取消 状态 -->
 --%>							</li>
						</ul>
					</td>
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
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<table class="orderList" width="99%" >
		<thead>
			<tr>
				<th width="15%">
				
					<!--NO-->
					<!--申请内容--><spring:message code="ess.empInfo.apply_content" />
				</th>
				<th width="10%">
				<!--申请日期--><spring:message code="ess.empInfo.date_application" />
					<!--日期-->
				</th>
				<th width="10%">
					<!--班次-->
					<!--申请区分--><spring:message code="ess.empInfo.apply_distinction" />
				</th>
				<th width="10%">
				<!--进行状态--><spring:message code="ess.empInfo.conduct_state" />
					<!--工作时间-->
				</th>
				<th width="15%">
				<!--结束日期--><spring:message code="ess.empInfo.end_date" />
					<!--申请开始时间-->
				</th>
				<th width="10%">
				<!--担当者--><spring:message code="ess.empInfo.bear_person" />
					<!--申请接收时间-->
				</th>
				<th width="15%">
				<!--回复信息--><spring:message code="ess.empInfo.reply_message" />
				</th>
				<th width="15%">
				<!--错误内容--><spring:message code="ess.empInfo.error_content" />
				</th>
			</tr>
		</thead>
		<tbody>
		
		<!-- 个人信息 -->
		<c:forEach items="${personalApplyList}" var="item" varStatus="i">
				<tr>
					<td>
					<a style="color:blue;cursor: pointer;" href="#" onclick="changeURL_ess3004('/ess/empinfo/viewApplyPersonalInfo?PERSON_NO=${item.PERSON_NO}','<spring:message code="org.title.PERSON_INFO" />')"><%-- 个人信息 --%><spring:message code="org.title.PERSON_INFO" /></a>
					</td>
					<td>
					${item.CREATE_DATE}
					</td>
					<td>
				   <%--  ${item.APPLY_TYPE} --%>
				   <!--修改--><spring:message code="ess.empInfo.modify" />
					</td>
					<td>
					<c:if test="${item.ACTIVITY==1}"><!--提交状态 --><spring:message code="pa.salary.canShu.tiJiao" /></c:if>
					<c:if test="${item.ACTIVITY==2}"><!--通过状态 --><spring:message code="ess.infoApply.adopt" /></c:if>
					<c:if test="${item.ACTIVITY==3}"><!--驳回状态 --><spring:message code="ess.empInfo.Reject" /></c:if>
					<c:if test="${item.ACTIVITY==4}"><!--取消状态 --><spring:message code="ess.empInfo.cancel_state" /></c:if>
					</td>
					<td>
					${item.UPDATE_DATE}
					</td>
					<td>
					${item.CREATED_IP}&nbsp;${getLocalName}
					</td>
					<td>
					${item.CALLBACK}
					</td>
					<td>
					${item.EARROR}
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${addressApplyList}" var="item" varStatus="i">
				<tr>
					<td>
					<a style="color:blue;cursor: pointer;" href="#" onclick="changeURL_ess3004('/ess/empinfo/viewApplyAddressInfo?ADDRESS_NO=${item.ADDRESS_NO}','${item.ESS_TYPE_CODE}')">${item.ESS_TYPE_CODE}</a>
					</td>
					<td>
					${item.CREATE_DATE}
					</td>
					<td>
				    ${item.APPLY_TYPE}
					</td>
					<td>
					${item.ACTIVITY}
					</td>
					<td>
					${item.UPDATE_DATE}
					</td>
					<td>
					${item.CREATED_IP}&nbsp;${getLocalName}
					</td>
					<td>
					${item.CALLBACK}
					</td>
					<td>
					${item.EARROR}
					</td>
				</tr>
			</c:forEach>
			<!--  -->
			<c:forEach items="${emergencyList}" var="item" varStatus="i">
				<tr>
					<td>
					<a style="color:blue;cursor: pointer;" href="#" onclick="changeURL_ess3004('/ess/empinfo/viewApplyEmergencyAddress?EMERGENCY_NO=${item.EMERGENCY_NO}','${item.ESS_TYPE_CODE}')">${item.ESS_TYPE_CODE}</a>
					</td>
					<td>
					${item.CREATE_DATE}
					</td>
					
					<td>
				    ${item.APPLY_TYPE}
					</td>
					<td>
					${item.ACTIVITY}
					</td>
					<td>
					${item.UPDATE_DATE}
					</td>
					<td>
					${item.CREATED_IP}&nbsp;${getLocalName}
					</td>
					<td>
					${item.CALLBACK}
					</td>
					<td>
					${item.EARROR}
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${homeRelationApplyList}" var="item" varStatus="i">
				<tr>
					<td>
					<a style="color:blue;cursor: pointer;" href="#" onclick="changeURL_ess3004('/ess/empinfo/viewApplyHomeRelation?FAMILY_NO=${item.FAMILY_NO}','${item.ESS_TYPE_CODE}')">${item.ESS_TYPE_CODE}</a>
					</td>
					<td>
					${item.CREATE_DATE}
					</td>
					
					<td>
				    ${item.APPLY_TYPE}
					</td>
					<td>
					${item.ACTIVITY}
					</td>
					<td>
					${item.UPDATE_DATE}
					</td>
					<td>
					${item.CREATED_IP}&nbsp;${getLocalName}
					</td>
					<td>
					${item.CALLBACK}
					</td>
					<td>
					${item.EARROR}
					</td>
				</tr>
			</c:forEach>
		
			<c:forEach items="${workApplyList}" var="item" varStatus="i">
				<tr>
					<td>
					<a style="color:blue;cursor: pointer;" href="#" onclick="changeURL_ess3004('/ess/empinfo/viewApplyWorkInfo?WORK_EXPER_NO=${item.WORK_EXPER_NO}','${item.ESS_TYPE_CODE}')">${item.ESS_TYPE_CODE}</a>
					</td>
					<td>
					${item.CREATE_DATE}
					</td>
					
					<td>
				    ${item.APPLY_TYPE}
					</td>
					<td>
					${item.ACTIVITY}
					</td>
					<td>
					${item.UPDATE_DATE}
					</td>
					<td>
					${item.CREATED_IP}&nbsp;${getLocalName}
					</td>
					<td>
					${item.CALLBACK}
					</td>
					<td>
					${item.EARROR}
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${productApplyList}" var="item" varStatus="i">
				<tr>
					<td>
					<a style="color:blue;cursor: pointer;" href="#" onclick="changeURL_ess3004('/ess/empinfo/viewApplyProductInfo?PRODUCT_NO=${item.PRODUCT_NO}','${item.ESS_TYPE_CODE}')">${item.ESS_TYPE_CODE}</a>
					</td>
					<td>
					${item.CREATE_DATE}
					</td>
					
					<td>
				    ${item.APPLY_TYPE}
					</td>
					<td>
					${item.ACTIVITY}
					</td>
					<td>
					${item.UPDATE_DATE}
					</td>
					<td>
					${item.CREATED_IP}&nbsp;${getLocalName}
					</td>
					<td>
					${item.CALLBACK}
					</td>
					<td>
					${item.EARROR}
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${educationApplyList}" var="item" varStatus="i">
				<tr>
					<td>
					<a style="color:blue;cursor: pointer;" href="#" onclick="changeURL_ess3004('/ess/empinfo/viewApplyEducationInfo?EDUC_NO=${item.EDUC_NO}','${item.ESS_TYPE_CODE}')">${item.ESS_TYPE_CODE}</a>
					</td>
					<td>
					${item.CREATE_DATE}
					</td>
					
					<td>
				    ${item.APPLY_TYPE}
					</td>
					<td>
					${item.ACTIVITY}
					</td>
					<td>
					${item.UPDATE_DATE}
					</td>
					<td>
					${item.CREATED_IP}&nbsp;${getLocalName}
					</td>
					<td>
					${item.CALLBACK}
					</td>
					<td>
					${item.EARROR}
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${qualificationApplyList}" var="item" varStatus="i">
				<tr>
					<td>
					<a style="color:blue;cursor: pointer;" href="#" onclick="changeURL_ess3004('/ess/empinfo/viewApplyQualificationInfo?QUAL_NO=${item.QUAL_NO}','${item.ESS_TYPE_CODE}')">${item.ESS_TYPE_CODE}</a>
					</td>
					<td>
					${item.CREATE_DATE}
					</td>
					
					<td>
				    ${item.APPLY_TYPE}
					</td>
					<td>
					${item.ACTIVITY}
					</td>
					<td>
					${item.UPDATE_DATE}
					</td>
					<td>
					${item.CREATED_IP}&nbsp;${getLocalName}
					</td>
					<td>
					${item.CALLBACK}
					</td>
					<td>
					${item.EARROR}
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${rewardList}" var="item" varStatus="i">
				<tr>
					<td>
					<a style="color:blue;cursor: pointer;" href="#" onclick="changeURL_ess3004('/ess/empinfo/viewApplyRewardInfo?REWARD_NO=${item.REWARD_NO}','${item.ESS_TYPE_CODE}')">${item.ESS_TYPE_CODE}</a>
					</td>
					<td>
					${item.CREATE_DATE}
					</td>
					
					<td>
				    ${item.APPLY_TYPE}
					</td>
					<td>
					${item.ACTIVITY}
					</td>
					<td>
					${item.UPDATE_DATE}
					</td>
					<td>
					${item.CREATED_IP}&nbsp;${getLocalName}
					</td>
					<td>
					${item.CALLBACK}
					</td>
					<td>
					${item.EARROR}
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	</div>
<div style="visibility: hidden;">
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
