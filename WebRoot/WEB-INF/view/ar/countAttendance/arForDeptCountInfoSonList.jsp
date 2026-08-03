<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function() {
	$("#arForDeptCountInfoList_currentIndex").val('${currentIndex}');
});
</script>
<link href=”/resources/css/ztree/zTreeStyle/zTreeStyle.css” rel=”stylesheet” type=”text/css” />
<style type="text/css"></style>
<!-- ztree -->
<script src="/resources/js/ztree/jquery.ztree.all-3.1.js" type="text/javascript"></script>
<script type="text/javascript">
// 初始调用
$(document).ready(function() {
	//布局
		$(".tabsContent").css("height", $(document.body).height() - 210);
		$('#demoTree1').treeTable( {
			expandLevel : 2
		});
		$('#demoTree2').treeTable( {
			expandLevel : 2
		});
	});
	
function arForDeptCountInfoList_changeURL(name,DEPT_NO) {
	var STIMESS = $("#arForDeptCountInfoList_seach_STIME").attr("value");
	STIMESS = STIMESS.substring(6,10)+ "/" +STIMESS.substring(3,5)+ "/" +STIMESS.substring(0,2);
	var ETIMESS = $("#arForDeptCountInfoList_seach_ETIME").attr("value");
	ETIMESS = ETIMESS.substring(6,10)+ "/" +ETIMESS.substring(3,5)+ "/" +ETIMESS.substring(0,2);
	var GROUP = $("#arForDeptCountInfoList_GROUP").attr("value");
	var KEY = $("#arForDeptCountInfoList_seachKey").attr("value");
	var href = "/ess/viewDept/viewOtApplySingleList?ITEM_NO=" + name
			+ "&AR_SUPERVISIOR_INFO=${LoginUser.personId}" + "&STIME=" + STIMESS + "&ETIME="
			+ ETIMESS + "&DEPT_NO=" + DEPT_NO +  "&KEY="
			+ KEY;
	//个人加班现况		
	$.pdialog.open(href,"ar0151", "<spring:message code='ar.viewArNavigationPage.BUMENXIANKUANG.b'/>", {width:1000,height:400,mask:true});
}
function changeURL2(name,DEPT_NO) {
	var STIMESS = $("#arForDeptCountInfoList_seach_STIME").attr("value");
	STIMESS = STIMESS.substring(6,10)+ "/" +STIMESS.substring(3,5)+ "/" +STIMESS.substring(0,2);
	var ETIMESS = $("#arForDeptCountInfoList_seach_ETIME").attr("value");
	ETIMESS = ETIMESS.substring(6,10)+ "/" +ETIMESS.substring(3,5)+ "/" +ETIMESS.substring(0,2);
	var GROUP = $("#arForDeptCountInfoList_GROUP").attr("value");
	var KEY = $("#arForDeptCountInfoList_seachKey").attr("value");
	var href = "/ess/viewDept/viewArPersonalSingleList?ITEM_NO=" + name
			+ "&AR_SUPERVISIOR_INFO=${LoginUser.personId}" + "&STIME=" + STIMESS + "&ETIME="
			+ ETIMESS + "&DEPT_NO=" + DEPT_NO  + "&KEY="
			+ KEY;
    //个人考勤现况
	$.pdialog.open(href,"ar0151", "<spring:message code='ar.viewArNavigationPage.BUMENXIANKUANG.b'/>", {width:800,height:400,mask:true});
}
</script>
</head>
<div class="pageContent">
	<c:if test="${currentIndex eq '0'}">
			<table class="user_table" width="2000px" layoutH="130" id="demoTree1">
				<tr>
					<td width="7%" class="td_title">
						<!-- 部门 --><spring:message code="ess.infoApply.DEPT" />
					</td>
					<td width="3%" class="td_title">
						<!-- 人员 --><spring:message code="ar.arForDeptCountInfoList.RENYUAN.b" />
					</td>
					<td width="3%" class="td_title">
						<!--年假 --><spring:message code="ar.viewArNavigationPage.NIANJIA.b" />
					</td>
					<td width="3%" class="td_title">
						<!--事假 --><spring:message code="ess.viewpersonalpainfo.shijia" />
					</td>
					<td width="3%" class="td_title">
						<!--一般病假 --><spring:message code="ess.viewArPersonalList.YIBANBINGJIA.b" />
					</td>
					<td width="3%" class="td_title">
						<!--孩子病假 --><spring:message code="ess.viewArPersonalList.HAIZIBINGJIA.b" />
					</td>
					<td width="3%" class="td_title">
						<!--长期病假 --><spring:message code="ess.viewArPersonalList.CHANGQIBINGJIA.b" />
					</td>
					<td width="3%" class="td_title">
						<!--个人婚假 --><spring:message code="ess.viewArPersonalList.GERENHUNJIA.b" />
					</td>
					<td width="3%" class="td_title">
						<!--孩子婚假 --><spring:message code="ess.viewArPersonalList.HAIZIHUNJIA.b" />
					</td>
					<td width="3%" class="td_title">
						<!--工伤假 --><spring:message code="ar.menu.title.gongshangjia" />
					</td>
					<td width="3%" class="td_title">
						<!--有薪丧假 --><spring:message code="ess.viewArPersonalList.YOUXINSANGJIA.b" />
					</td>
					<td width="2%" class="td_title">
						<!--产假 --><spring:message code="ar.menu.title.chanjia" />
					</td>
					<td width="3%" class="td_title">
						<!--陪产假 --><spring:message code="ar.menu.title.peichanjia" />
					</td>
					<td width="3%" class="td_title">
						<!--哺乳假--><spring:message code="ar.menu.title.purujia" />
					</td>
					<td width="3%" class="td_title">
						<!--产前检查假--><spring:message code="ar.arForDeptCountInfoList.CHANQIANJIANCHAJIA.b" />
					</td>
					<td width="3%" class="td_title">
						<!--出差--><spring:message code="ar.menu.title.chuchai" />
					</td>
					<td width="3%" class="td_title">
						<!--旷工--><spring:message code="ar.monthwork.title.kuanggong" />
					</td>
					<td width="3%" class="td_title">
						<spring:message code="ar.menu.title.keepHelth" />
					</td>
					<td width="3%" class="td_title">
						<spring:message code="ar.menu.title.chumen" />
					</td>
					<td width="3%" class="td_title">
						<!--迟到--><spring:message code="ar.monthwork.title.Lateness" />
					</td>
					<td width="3%" class="td_title">
						<!--早退--><spring:message code="ess.infoApply.leave_early" />
					</td>
				</tr>
				
						<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
							<tr id="${item.DEPTNO}"
								<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
								<td width="7%" class="td_type">
									<span controller="true">${item.ORG_NAME_LOCAL}</span>
								</td>
								<c:forEach items="${deptListCount[item.DEPTNO]}" var="it" varStatus="i">
									<td style="text-align: center" width="3%" class="td_type">
										${it.PERSON_NUMBER}
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141456","${item.DEPTNO}");'>
										<span style="color: blue">${it.NIANJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141454","${item.DEPTNO}");'>
										<span style="color: blue">${it.SHIJIA}</span> 
									</td>							
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141464","${item.DEPTNO}");'>
										<span style="color: blue">${it.YIBANBINGJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000286","${item.DEPTNO}");'>
										<span style="color: blue">${it.HAIZIBINGJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000287","${item.DEPTNO}");'>
										<span style="color: blue">${it.CHANGQIBINGJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000289","${item.DEPTNO}");'>
										<span style="color: blue">${it.GERENHUNJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000290","${item.DEPTNO}");'>
										<span style="color: blue">${it.HAIZIHUNJIA}</span>
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000288","${item.DEPTNO}");'>
										<span style="color: blue">${it.GONGSHANGJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000291","${item.DEPTNO}");'>
										<span style="color: blue">${it.YOUXINSANGJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141461","${item.DEPTNO}");'>
										<span style="color: blue">${it.CHANJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000292","${item.DEPTNO}");'>
										<span style="color: blue">${it.PEICHANJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000293","${item.DEPTNO}");'>
										<span style="color: blue">${it.BURUJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141460","${item.DEPTNO}");'>
										<span style="color: blue">${it.CHANQIANJIANCHAJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("90000294","${item.DEPTNO}");'>
										<span style="color: blue">${it.CHUCHAI}</span>
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141443","${item.DEPTNO}");'>
										<span style="color: blue">${it.KUANGGONG}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("14013784","${item.DEPTNO}");'>
										<span style="color: blue">${it.HOUBINGJIA}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141468","${item.DEPTNO}");'>
										<span style="color: blue">${it.CHUMEN}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141441","${item.DEPTNO}");'>
										<span style="color: blue">${it.CHIDAO}</span> 
									</td>
									<td style="text-align: center; cursor: pointer;" id="codeChange" onclick='javascript:changeURL2("141442","${item.DEPTNO}");'>
											<span style="color: blue">${it.ZAOTUI}</span> 
									</td>
									
								</c:forEach>
							</tr>
						</c:forEach>
					
			</table>
	</c:if>
	<c:if test="${currentIndex eq '1'}">
		<table class="user_table" border="1" width="100%" id="demoTree2">
			<tr>
				<td width="20%" class="td_title">
					<!-- 部门 --><spring:message code="ess.infoApply.DEPT" />
				</td>
				<td width="15%" class="td_title">
					<!-- 合计 --><spring:message code="ess.viewpersonalpainfo.heji" />
				</td>
				<td width="15%" class="td_title">
					<!-- 总人数 --><spring:message code="empsubject.totalCnt" />
				</td>
				<td width="10%" class="td_title">
					<!-- 部门平均OT --><spring:message code="ar.arForDeptCountInfoList.BUMENPINGJUNOT.b" />
				</td>
				<td width="10%" class="td_title">
					<!-- 平日 --><spring:message code="ar.viewitemparameter.title.pingshi" />
				</td>
				<td width="10%" class="td_title">
					<!-- 周末 --><spring:message code="ar.viewitemparameter.title.zhoumo" />
				</td>
				<td width="10%" class="td_title">
					<!-- 法定节假日 --><spring:message code="ar.arForDeptCountInfoList.FADINGJIEJIARI.b" />
				</td>
			</tr>
					<c:forEach items="${codeInfoTreeList22}" var="item" varStatus="i">
						<tr id="${item.DEPTNO}"
							<c:if test="${item.DEPTNO ne item.MAXID}">pid="${item.PARENT_DEPT_NO}"</c:if>>
							<td width="20%" class="td_type">
								<span controller="true">${item.ORG_NAME_LOCAL}</span>
							</td>
							<c:forEach items="${deptListCount[item.DEPTNO]}" var="it"
								varStatus="i">
								<td style="text-align: center" class="td_type" width="15%">
									${it.HOILDAY_OT_TOTAIL+it.WEEKDAY_OT_TOTAIL+it.WEEKEND_OT_TOTAIL}
								</td>
								<td style="text-align: center" class="td_type" width="15%">
									${it.PERSON_NUMBER}
								</td>
								<td style="text-align: center" class="td_type" width="10%">
									<c:if test="${it.PERSON_NUMBER>0}">
	                               		${(it.HOILDAY_OT_TOTAIL+it.WEEKDAY_OT_TOTAIL+it.WEEKEND_OT_TOTAIL)/it.PERSON_NUMBER}
	                               	</c:if>
								</td>
								<td style="text-align: center; cursor: pointer;" id="codeChange" 
									onclick='javascript:arForDeptCountInfoList_changeURL("90000295,90000296,90000297","${item.DEPTNO}");'>
										<span style="color: blue">${it.WEEKDAY_OT_TOTAIL}</span>
								</td>
								<td style="text-align: center;cursor: pointer;" id="codeChange" 
									onclick='javascript:arForDeptCountInfoList_changeURL("90000298,90000299","${item.DEPTNO}");'>
										<span style="color: blue">${it.WEEKEND_OT_TOTAIL}</span>
								</td>
								<td style="text-align: center;cursor: pointer;" id="codeChange" 
									onclick='javascript:arForDeptCountInfoList_changeURL("90000300,90000301","${item.DEPTNO}");'>
										<span style="color: blue"> ${it.HOILDAY_OT_TOTAIL}</span>
							</c:forEach>
						</tr>
					</c:forEach>
		</table>
	</c:if>
</div>