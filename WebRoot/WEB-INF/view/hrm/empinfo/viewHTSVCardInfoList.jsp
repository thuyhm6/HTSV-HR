<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style type="text/css">
#t{
table-layout:fixed;word-break:break-all;
}
.aaa td
{
	height:400px;
	overflow:hidden;
	display:block;
}
.ctltable{border-collapse: collapse;
table-layout:fixed;}

.ctltable td {
text-overflow:ellipsis;
overflow:hidden;
white-space: nowrap;
border:1px solid #000000;}

</style>
<script>
$(document).ready(function(){
	//查询
	$("#viewCardInfoList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewCardInfoListForm",navTab.getCurrentPanel()).submit();
	});
	
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewHTSVCardInfoList&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewHTSVCardInfoList&seach_KEY='+name);
    });

});

function changeZhijiCARD(status, id, aid) {
	var idvalue = $('#' + id).val();
	<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
		var parentnoCARD = "'14015813','14015815'";
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
	var parentnoCARD = "'14015814','14015815'";
</c:if>
	var idhref = '/hrm/empinfo/searchTanchu?PARENT_CODE_NO='+ parentnoCARD + '&firstFlag=N&status='+status+'&nameid=' + id + '&typeFlag=Y&idvalue=' + idvalue;
	$('#' + aid).attr('href', idhref);
}

function printData() {

	$("#viewHTSVCardInfoList_pageContent").jqprint( {
		debug : false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
		importCSS : true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
		printContainer : false, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
		operaSupport : true //表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
			});

}
</script>
<div>
<form id="viewCardInfoListForm" onsubmit="return navTabSearch(this);" action="/hrm/empinfo/viewHTSVCardInfoList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td width="7%"><spring:message code="hrm.empinfo.nameAndEmpid"/><!-- 社号/姓名 --></td>
		<td width="23%">
			<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
			<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
		</td>
		<td width="50%" colspan="3">
			<c:if test="${not empty personInfo}">
				<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.STATUS_CODE_NAME }</span>
			</c:if>
		</td>
		<td width="20%"></td>
	</tr>
	<tr>
		<td><spring:message code="hrm.recruitManage.DATE_STARTED"/><!-- 入职日期 --></td>
		<td>
			<input type="text" id="seach_START_DATE_JOIN" name="seach_START_DATE_JOIN" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE_JOIN }"/>~
			<input type="text" id="seach_END_DATE_JOIN" name="seach_END_DATE_JOIN" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE_JOIN }"/>
		</td>
		<td><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!-- 部门 --></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewHTSVCardInfoList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewHTSVCardInfoList_seachDept" selected="${DEPTNO}"/>
			<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
			<spring:message code="hrm.empinfo.Department_include"/><!-- 下位部门包括 -->
		</td>
		<td><spring:message code="ess.infoApply.renzhizhuangtai"/><!-- 任职状态 --></td>
		<td><ait:selectCodeMulti id="seach_EMP_OFFICE" name="seach_EMP_OFFICE_NAME" parentNo="15118" selected="${EMP_OFFICE}" selectedNm="${EMP_OFFICE_NAME}"/></td>
	</tr>
	<tr>
		<td><spring:message code="hrm.recruitManage.LEAVE_DATE"/><!-- 离职日期 --></td>
		<td>
			<input type="text" id="seach_START_DATE_LEFT" name="seach_START_DATE_LEFT" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE_LEFT }"/>~
			<input type="text" id="seach_END_DATE_LEFT" name="seach_END_DATE_LEFT" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE_LEFT }"/>
		</td>
		<td><spring:message code="ess.trans.title.postGradeName"/><!-- 职级 --></td>
		<td>
			<!--<ait:selectCodeMulti id="seach_POST_GRADE_NO" name="seach_POST_GRADE_NO_NAME" 
				parentNo="14015578" selected="${POST_GRADE_NO}" selectedNm="${POST_GRADE_NO_NAME}"/>
			--><input type="text" id="zhijiCARD" name="zhijiCARD"
						value="${zhijiCARD }">
			<a id="jiCARD" class="" href="#"
				onclick="changeZhijiCARD('zhijiCARD','GRADE_NO_CARD','jiCARD')"
				lookupGroup="person"> <input type="button" value="......">
			</a>
			<input type="hidden" id="GRADE_NO_CARD" name="GRADE_NO" value="${GRADE_NO }">
		</td>
		<td><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/><!-- 员工类型 --></td>
		<td><ait:selectCodeMulti id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE_NAME" parentNo="13864" selected="${EMP_TYPE_CODE}" selectedNm="${EMP_TYPE_CODE_NAME}"/></td>
		<td><div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent" align="center">
								<button class="button" id="viewCardInfoList_Serch">
									<!--onkeydown="javascript:if(event.keyCode == 13)return false;"-->
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>

					<li>
						<div>
							<div class="buttonContent" align="center">
								<a class="button" onclick="printData()"><span><!--印刷--><spring:message code="hrm.approve.PRINTING" /></span> </a>
							</div>
						</div>
					</li>
				</ul>
			</div></td>
	</tr>
</table>
</div>
</form>
</div>


<div class="pageContent" id="viewHTSVCardInfoList_pageContent"
		sysLong='printDiv'
		style="width: 850px;  padding-left: 10px; text-align: left;overflow: hidden;" >
		<c:if test="${empty viewCardInfoList}">
			<div style="height: 800px;">
			</div>
		</c:if>
<c:forEach items="${viewCardInfoList}" var="info">
<div style="height:1254px;">
		<table width="100%" style="height: 40px;">
			<tr >
				<td width="40%">
				  <img src='/resources/images/HR_Card_logo.jpg'>
				</td>
				<td style="font-size: 20px; text-align: center;font-family:Calibri;" width="20%">
					<b>HR CARD</b></td>
				<td width="40%"></td>
			</tr>
		</table>
		<br />
		<c:forEach items="${info.viewBaseInfoList}" var="item">
		<table width="100%" class="user_table" style="table-layout:fixed;word-break:break-all;">
			<tr>
				<td class="aaa" width="113px" rowspan="7" style="padding-top:0px;padding-bottom:0px;padding-left:1px;"><img src='${item.PHOTO_PATH }' height="170px" width="113px"></td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%">Department</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.DEPTNO} </td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Contract start</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%" >${item.DATE_STARTED}</td>---
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >REG place</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%" >${item.REG_PLACE}</td>---
			</tr>
			<tr>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%">Rank</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.POST_GRADE_NAME}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Contract end</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.DATE_LEFT}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Political</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.LANGUAGE_2}</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%">Mainbusiness</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.MAIN_BUSINESS}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Final edu</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.DEGREE_CODE}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Birthday</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.DOB}</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%">Cost center</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.COST_CENTER}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Grad school</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.GRADUATE_SCHOOL}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Gender</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.GENDER}</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%">State service</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.EMP_OFFICE}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Major</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.MAJOR}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Home phone</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.HOME_PHONE}</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%">Division entry</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.POST_GRADE_NAME}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Grad date</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.GRADUATION_DATE}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Tel</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.CELLPHONE}</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="10.25%">Date entry</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.DATE_STARTED}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="10.25%" >National</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.NATION_NAME}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Married</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.MARITAL_STATUS_CODE}</td>
			</tr>
			<tr>
				<td class="aaa" width="14.5%" rowspan="2" style="text-align: center;font-family:Calibri;">${item.LOCAL_NAME}<br>${item.EMPID}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%">Person email</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.PERSON_EMAIL}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >C.email</td>
				<td class="aaa" style="text-align:center;font-family:Calibri; font-size: 11px;" width="20.5%">${item.COMPANY_EMAIL}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >Marry date</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.WEDDING_DATE}</td>
			</tr>
			<tr>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%">Present add</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%" colspan="3">${item.CURRENT_ADDRESS}</td>
				<td class="td_title" style="text-align:left;font-family:Calibri;" width="11%" >EagleM ID</td>
				<td class="aaa" style="text-align:center;font-family:Calibri;" width="20.5%">${item.EAGLEM_ID}</td>
			</tr>
		</table>
		</c:forEach>
		
		<br>
		
		<table width="100%" cellspacing="0" style="table-layout:fixed;word-break:break-all;">
			<tr>
				<td width="55%">
					<table width="100%" class="user_table" style="table-layout: fixed;word-break: break-all;">
					<span style="font-size: 14px; font-family: Calibri;"><b>Education Information</b></span>
					<tr>
						<td class="td_title" width="12%" style="font-family:Calibri;">Admissions</td>
						<td class="td_title" width="12%" style="font-family:Calibri;">Graduation</td>
						<td class="td_title" width="13%" style="font-family:Calibri;">Education</td>
						<td class="td_title" width="14%" style="font-family:Calibri;">Graduate school</td>
						<td class="td_title" width="14%" style="font-family:Calibri;">Major</td>
					</tr>
					<c:forEach items="${info.viewJiaoyuInfoList }" var="item">
					<tr>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="12%" height="20">${item.START_DATE }</td>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="12%" height="20">${item.END_DATE }</td>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="13%" height="20">${item.DEGREE_NAME }</td>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="14%" height="20">${item.INSTITUTION_NAME }</td>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="14%" height="20">${item.SUBJECT }</td>
					</tr>
					</c:forEach>
					</table>
				</td>
				<td>&nbsp;</td>
				<td width="45%">
					<table width="100%" class="user_table" style="table-layout: fixed;word-break: break-all;">
					<span style="font-size: 14px; font-family: Calibri;"><b>Family Information</b></span>
					<tr>
						<td class="td_title" width="10%" style="font-family:Calibri;">Relation</td>
						<td class="td_title" width="10%" style="font-family:Calibri;">Name</td>
						<td class="td_title" width="10%" style="font-family:Calibri;">Birthday</td>
						<td class="td_title" width="14%" style="font-family:Calibri;">Education</td>
						<td class="td_title" width="14%" style="font-family:Calibri;">Tel</td>
					</tr>
					<c:forEach items="${info.viewJiatingInfoList }" var="item">
					<tr>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="10%" height="20">${item.FAM_TYPE_NAME }</td>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="10%" height="20">${item.FAM_NAME }</td>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="10%" height="20">${item.FAM_BORNDATE }</td>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="14%" height="20">${item.FAM_EDUCATION }</td>
						<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="14%" height="20">${item.FAM_PHONE }</td>
					</tr>
					</c:forEach>
					</table>
				</td>
				
			
			</tr>
		</table>
		
		<br>
		
		<table width="100%" class="user_table" style="table-layout:fixed;word-break:break-all;">
		 <span style="font-size:14px;font-family:Calibri;"><b>Experience Information</b></span>
			<tr>
				<td class="td_title" width="10%" style="font-family:Calibri;">Start date</td>
				<td class="td_title" width="10%" style="font-family:Calibri;">End date</td>
				<td class="td_title" width="20%" style="font-family:Calibri;">Corporate name</td>
				<td class="td_title" width="14%" style="font-family:Calibri;">Department</td>
				<td class="td_title" width="14%" style="font-family:Calibri;">Remarks</td>
			</tr>
			<c:forEach items="${info.viewJingliInfoList}" var="item">
			<tr>
				<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="10%" height="20">${item.START_DATE }</td>
				<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="10%" height="20">${item.END_DATE }</td>
				<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="20%" height="20">${item.CPNY_NAME }</td>
				<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="14%" height="20">${item.DEPT_NAME }</td>
				<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" width="14%" height="20">${item.REMARK }</td>
			</tr>
			</c:forEach>
		</table>
		
		
		<br>
		
		
		<table width="100%" cellspacing="0" style="table-layout:fixed;word-break:break-all;">
			<tr>
				<td width="55%">
					<table width="100%" class="user_table" style="table-layout:fixed;word-break:break-all;">
						<span style="font-size:14px;font-family:Calibri;"><b>Main Business</b></span>
						<tr>
							<td class="td_title" width="2%" style="font-family:Calibri;">Start date</td>
							<td class="td_title" width="2%" style="font-family:Calibri;">End date</td>
							<td class="td_title" width="4%" style="font-family:Calibri;">Mainbusiness</td>
						</tr>
						<c:forEach items="${info.viewFalingInfoList}" var="item">
						<tr>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.START_DATE }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.END_DATE }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.MAIN_BUSINESS }</td>
						</tr>
						</c:forEach>
					</table>
					
				</td>
				
				<td>&nbsp;</td>
				
				<td width="45%">
					<table width="100%" class="user_table" style="table-layout:fixed;word-break:break-all;">
						  <span style="font-size:14px;font-family:Calibri;"><b>Qualification Information</b></span>
						<tr>
							<td class="td_title" width="7%" style="font-family:Calibri;">Qualification</td>
							<td class="td_title" width="7%" style="font-family:Calibri;">Grade</td>
							<td class="td_title" width="7%" style="font-family:Calibri;">Issuing </td>
							<td class="td_title" width="7%" style="font-family:Calibri;">Evidence</td>
							<td class="td_title" width="7%" style="font-family:Calibri;">Effective</td>
						</tr>
						<c:forEach items="${info.viewZigeInfoList}" var="item">
						<tr>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.QUAL_NAME }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.QUAL_LEVEL }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.QUAL_INSTITUTE }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.DATE_OBTAINED }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.VALIDITY_DATE }</td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
		
		<br>
		
		<table width="100%" cellspacing="0" style="table-layout:fixed;word-break:break-all;">
			<tr>
			
				<td width="45%">
					<table width="100%" class="user_table" style="table-layout:fixed;word-break:break-all;">
						<span style="font-size:14px;font-family:Calibri;"><b>Training Content</b></span>
						<tr >
							<td class="td_title" width="7%" style="font-family:Calibri;">Start date</td>
							<td class="td_title" width="7%" style="font-family:Calibri;">End date</td>
							<td class="td_title" width="7%" style="font-family:Calibri;">Training course</td>
							<td class="td_title" width="7%" style="font-family:Calibri;">Fraction</td>
						</tr>
						<c:forEach items="${info.viewPeixunInfoList}" var="item">
						<tr>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.IMPLE_START_DATE }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.IMPLE_END_DATE }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.COURSE_NAME_CODE }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVA_RESULT }</td>
						</tr>
						</c:forEach>
					</table>
				</td>
				<td>&nbsp;</td>
				
				<td width="55%">
				
					<table width="100%" class="user_table" style="table-layout:fixed;word-break:break-all;">
						<span style="font-size:14px;font-family:Calibri;"><b>Evaluation Record</b></span>
						<tr >
							<td class="td_title" width="2%" style="font-family:Calibri;">Year</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">1</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">2</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">3</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">4</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">5</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">6</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">7</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">8</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">9</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">10</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">11</td>
							<td class="td_title" width="1%" style="font-family:Calibri;">12</td>
							<td class="td_title" width="2%" style="font-family:Calibri;">Ability</td>
						</tr>
						<c:forEach items="${info.viewPingjiaInfoList}" var="item">
						<tr>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_YEAR }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH1 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH2 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH3 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH4 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH5 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH6 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH7 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH8 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH9 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH10 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH11 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH12 }</td>
							<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EVS_MONTH13 }</td>
						</tr>
						</c:forEach>
					</table>
					
				</td>
				
				
			</tr>
		</table>
		
		<br>
		
		
		<table width="100%" class="user_table" style="table-layout:fixed;word-break:break-all;">
		 <span style="font-size:14px;font-family:Calibri;"><b>Order Information</b></span>
		<tr>
			<td class="td_title" width="14%" style="font-family:Calibri;">Order date</td>
			<td class="td_title" width="30%" style="font-family:Calibri;">Order distinguish</td>
			<td class="td_title" width="20%" style="font-family:Calibri;">Department</td>
			<td class="td_title" width="14%" style="font-family:Calibri;">Position</td>
			<td class="td_title" width="10%" style="font-family:Calibri;">Rank</td>
			<td class="td_title" width="14%" style="font-family:Calibri;">Mainbusiness</td>
			<td class="td_title" width="14%" style="font-family:Calibri;">State service</td>
			<td class="td_title" width="14%" style="font-family:Calibri;">Employee type</td>
		</tr>
		<c:forEach items="${info.viewFalingInfoList}" var="item">
		<tr>
			<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.START_DATE }</td>
			<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.TRANS_REASON }</td>
			<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.DEPT_NAME }</td>
			<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.POSITION_NAME }</td>
			<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.POST_GRADE_NAME }</td>
			<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.MAIN_BUSINESS }</td>
			<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EMP_OFFICE }</td>
			<td class="aaa" style="text-align:center;color:#4B4746;font-family:Calibri;" height="20">${item.EMP_TYPE_CODE }</td>
		</tr>
		</c:forEach>
		</table>
		</div>
	</c:forEach>
	</div>
