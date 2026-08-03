<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function() {
	var myDate = new Date();
	var year = myDate.getFullYear();
	var birthyear = "${personInfo.DOB}".substring(0, 4);
	var time = parseInt(year) - parseInt(birthyear);
	$('#age').html(time);

});
function fangdajing() {
	var name = $('#seach_KEY').val();
	var deptid = $("input[name='seach_DEPTNO']", dialog.getCurrentPanel())
			.val();
	$('#fangda').attr(
			'href',
			'/hrm/empinfo/viewEmpInfoListTanchu?firstFlag=N&seach_KEY=' + name
					+ '&seach_DEPTNO=' + deptid);
}
</script>
<!-- 资格信息  -->

<div class="panel">
	<h1>
	<!--资格信息--><spring:message code="ess.empInfo.qualification_information" />
	</h1>

	<div>
		<%@ include
			file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess1.jsp"%>
	</div>




	<div id="edudiv">

		
		<div style="margin-bottom: 10px;"><table width="99%">
			<tr >
				<td width="90%">
					<h1>
					<!--学历信息--><spring:message code="ess.empInfo.education_information" />
					</h1>

				</td>
				<td  >
					<a mask="true" class="buttonActive"
						href="/ess/empinfo/essViewEducationInfo?APPLY_TYPE=1"
						target="dialog" height="500" width='600'> <span><!--添加 --><spring:message code="ess.empInfo.insert" /></span> </a>
				</td>
			</tr>
		</table></div>
		

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title">
					<!--学历--><spring:message code="ess.empInfo.Education" />
				</td>
				<td class="td_title">
					<spring:message code="hrm.recruitManage.START_DATE" /><!--入学日期-->
				</td>
				<td class="td_title">
					<spring:message code="hrm.recruitManage.END_DATE" /><!--毕业日期-->
				</td>
				<td class="td_title">
					<spring:message code="hr.viewPersonalInfo.title.SCHOOL" /><!--毕业学校-->
				</td>
				<td class="td_title">
					<!--专业--><spring:message code="ess.empInfo.major" />
				</td>
			</tr>

			<c:forEach items="${educationList}" var="item">
				<tr>
					<td class="td_type">
						<a mask="true" style="right: 2px; color: blue;" class='add'
							href='/ess/empinfo/updateEducationInfo?APPLY_TYPE=2&EDUC_NO=${item.EDUC_NO}'
							target="dialog" title="<spring:message code='ess.empInfo.education_information' />" height="580" width='600'>${item.DEGREE_CODE}</a><!-- 学历信息 -->
					</td>
					<td class="td_type">
						${item.START_DATE}
					</td>
					<td class="td_type">
						${item.END_DATE}
					</td>
					<td class="td_type">
						${item.INSTITUTION_NAME}
					</td>
					<td class="td_type">
						${item.SUBJECT}
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="edudiv">

		</br>
		<table width="99%">
			<tr>
				<td width="90%">
					<h1>
					<!--资格事项--><spring:message code="ess.empInfo.qualifications_matter" />
					</h1>

				</td>
				<td >
					<a mask="true" class="buttonActive"
						href="/ess/empinfo/essViewQualificationInfo?APPLY_TYPE=1"
						target="dialog" height="470" width='600'> <span width="50px"><!--添加 --><spring:message code="ess.empInfo.insert" /></span> </a>
				</td>
			</tr>
		</table>
		</br>


		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title">
					<spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU" /><!--证书名称-->
				</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.Qualification_grade" /><!--等级-->
				</td>
				<td class="td_title">
					<!--获证日期--><spring:message code="ess.empInfo.certified_date" />
				</td>
				<td class="td_title">
					<!--有效日期--><spring:message code="ess.empInfo.effective_date" />
				</td>
				<td class="td_title">
					<!--证书编号--><spring:message code="ess.empInfo.certificate_number" />
				</td>
				<td class="td_title">
					<spring:message code="hrm.empinfo.Issuing_authority" /><!--发证机关-->
				</td>
			</tr>

			<c:forEach items="${qualificationList}" var="item">
				<tr>
					<td class="td_type">
						<a mask="true" style="right: 2px; color: blue;" class='add' 
							href='/ess/empinfo/updateQualificationInfo?APPLY_TYPE=2&QUAL_NO=${item.QUAL_NO}'
							target="dialog" height="580" width='600' title="<spring:message code='ess.empInfo.qualifications_matter' />"> ${item.QUAL_NAME}</a><!-- 资格事项 -->
					</td>
					<td class="td_type">
						 ${item.QUAL_LEVEL}
					</td>
					<td class="td_type">
						${item.DATE_OBTAINED}
					</td>
					<td class="td_type">
						${item.VALIDITY_DATE}
					</td>
					<td class="td_type">
						${item.QUAL_CARD_NO}
					</td>
				    <td class="td_type">
						${item.QUAL_INSTITUTE}
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div id="edudiv">
		</br>
		<table width="99%">
			<tr>
				<td width="90%">
					<h1>
					<!--表彰事项--><spring:message code="ess.empInfo.commend_matter" />
					</h1>

				</td>
<!-- 				<td  > -->
<!-- 					<a mask="true" class="buttonActive" -->
<!-- 						href="/ess/empinfo/essViewRewardInfo?APPLY_TYPE=1" target="dialog"> -->
<!-- 						<span>添加</span> </a> -->
<!-- 				</td> -->
			</tr>
		</table>
		</br>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" width="20%">
				<!--表扬/得奖--><spring:message code="ess.empInfo.Praise_Award" />
				</td>
				<td class="td_title" width="20%">
				<!--表扬日/得奖日--><spring:message code="ess.empInfo.Praise_day_award_date" />
				</td>
				<td class="td_title" width="20%">
				<!--授予机关--><spring:message code="ess.empInfo.awarding_authority" />
				</td>
				<td class="td_title" width="20%">
				<!--奖金--><spring:message code="ess.empInfo.bonus" />
				</td>
			</tr>

			<c:forEach items="${rewardList}" var="item">
				<tr>
					<td class="td_type">
<!-- 						<a mask="true" style="right: 2px; color: blue;" -->
<%-- 							href='/ess/empinfo/updateRewardInfo?APPLY_TYPE=2&REWARD_NO=${item.REWARD_NO}' --%>
<%-- 							target="dialog"> ${item.REWARD_TYPE}</a> --%>
						${item.REWARD_TYPE}
					</td>
					<td class="td_type">
						${item.REWARD_DATE}
					</td>
					<td class="td_type">
						${item.REWARD_CNPY}
					</td>
					<td class="td_type">
						${item.REWARD}
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
