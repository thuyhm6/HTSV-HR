<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
$(function(){
	var cpnyid="${defaultCpny}";
});

function getToDay(){//获得今天的时间
	   var now = new Date();
	   var nowYear = now.getFullYear();
	   var nowMonth = now.getMonth()+1;
	   var nowDate = now.getDate();
	   if(nowMonth<10){
		   nowMonth="0"+nowMonth;
	   }
	   if(nowDate<10){
		   nowDate="0"+nowDate;
	   }
	   return nowYear+""+nowMonth+""+nowDate;
	  }
//验证 部门,职群,职级,员工类型是否为空方法
function isNotEmpty(){
	var deptname=$('#viewSingleStartPoint_deptList').val();
	var emptypecode=$('#EMP_TYPE_CODE').val();
	var postfamily=$('#POST_FAMILY').val();
	var postgradeno=$('#GRADE_NO').val();
	if(deptname==''||deptname==null){
		alert("<spring:message code='pa.salary.canShu.bmbnwk' />");//部门不能为空!
		return false;
	}
	if(emptypecode==""||emptypecode==null){
		alert("<spring:message code='hr.alert.message.viewHire.checkNotNullEmpTypeCode' />");//员工类型不能为空!
		return false;
	}
	if(postfamily==""||postfamily==null){
		alert("<spring:message code='hr.hrm.empinfo.zhiqunbunengweikong.a' />");//职群不能为空!
		return false;
	}
	if(postgradeno==""||postgradeno==null){
		alert("<spring:message code='hr.hrm.empinfo.zhijibunengweikong.a' />");//职级不能为空!
		return false;
	}
	if(deptname!=""&&emptypecode!=""&&postfamily!=""&&postgradeno!=""){
		return true;
	}else{
		return false;
	}
	
}


</script>
	<div>
		<form id="editRegPage" method="post" action="/hrm/recruit/addEditRecPageInfoHub" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
			<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }">
			<input type="hidden" name="currentCount" id="currentCount" value="${currentCount }">
			<input type="hidden" name="REC_EMPLOYEE_NO" id="REC_EMPLOYEE_NO" value="${recPageInfo.REC_EMPLOYEE_NO }"/>
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" >
								<tr>
							      <td class="td_title" width="15%" style="text-align:right">
							            <!-- 姓名 --> <spring:message code="empsubject.userNm" />
							      </td>
							      <td class="td_type" width="15%">
							         ${recPageInfo.EMP_NAME }
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							            <!-- 证件类型 --> <spring:message code="hr.viewCondSql.title.ZHENGJIANLEIXING" />   
							      </td>
							      <td class="td_type" width="15%">
							        ${recPageInfo.CERT_TYPE_CODE } 
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							       <!-- 证件号码 --> <spring:message code="hrm.addRecPage.IdCardNo.k" />   
							      </td>
							      <td class="td_type" width="15%">
							         ${recPageInfo.CERT_NUMBER }
							      </td>
							   </tr>
				               <tr>
				                  <td class="td_title" width="15%" style="text-align:right">
							      <!-- 电话 --> <spring:message code="empsubject.officePhone" />
							      </td>
							      <td class="td_type" width="15%">
							          ${recPageInfo.EMP_TELPHONE }
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							          <!-- 住址  --> <spring:message code="hrm.empinfo.FAM_ADDRESS" />   
							      </td>
							      <td class="td_type" width="15%" colspan=3>
							          ${recPageInfo.EMP_ADDRESS }
							      </td>
				               </tr>
							   <tr>
								  <td class="td_title" width="15%" style="text-align:right">
							           <!-- 性别 --> <spring:message code="empsubject.sexName" />
							      </td>
							      <td class="td_type" width="15%">
							           ${recPageInfo.SEX_NAME }
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							           <!-- 国籍 --><spring:message code="hr.viewCondSql.title.GUOJI" />  
							      </td>
							      <td class="td_type" width="15%">
							           ${recPageInfo.NATIONALITY_NAME }
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							           <!-- 出生日期 --> <spring:message code="main.home.message.chushengriqi" />
							      </td>
							      <td class="td_type" width="15%">
							           ${recPageInfo.EMP_BIRTHDAY }
							      </td>
							   </tr>
							   <tr id="empoffice" style="">
							     <td class="td_title" width="15%" style="text-align:right">
							     <!-- 最终学校--><spring:message code="hr.viewPersonalInfo.title.FINAL_SCHOOL" />  
							      </td>
							      <td class="td_type" width="15%" colspan=3>
							          ${recPageInfo.FINAL_SCHOOL }
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							     <!--  最终学历--><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME" />  
							      </td>
							      <td class="td_type" width="15%">
							          ${recPageInfo.FINAL_EDU_NAME }
							      </td>
							   </tr>
							   <tr>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!--  毕业日期--><spring:message code="hr.viewPersonalInfo.title.END_DATE" />  
							      </td>
							      <td class="td_type" width="15%">
							           ${recPageInfo.FINAL_GRAD_DATE }
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							  <!--  Email--><spring:message code="ess.personalinfo.title.email" />   
							      </td>
							      <td class="td_type" width="15%" colspan=3>
							           ${recPageInfo.EMP_EMAIL }
							      </td>
							   </tr>
							   <tr>
							      <td class="td_title" width="15%" style="text-align:right">
							      <!-- 岗位区分--><spring:message code="hrm.addRecPage.postDivision.k" />   
							      </td>
							      <td class="td_type" width="15%">
							           ${recPageInfo.POST_TYPE_NAME }
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							       <!-- 工作经验--><spring:message code="hrm.addRecPage.workExperience.k" />   
							      </td>
							      <td class="td_type" width="15%">
							           ${recPageInfo.WORK_EXPERIENCE } <!-- 年--><spring:message code="rp.report.title.year" />    
							      </td>
							      <td class="td_title" width="15%" style="text-align:right">
							  
							      </td>
							      <td class="td_type" width="15%">
							      
							      </td>
								</tr>
							  </table>
							  
							 <div>
								
								<table id="fileTable" class="list" width="100%">
									<tbody>
									    <c:if test="${recPageInfo.FILE_PATH ne null && recPageInfo.FILE_PATH ne ''}">
											<td class="td_center">
											<input type="checkbox" name="FILE_NO">
											<input type="hidden" name="fileUrl" value="${recPageInfo.FILE_PATH }">
											<input type="hidden" name="fileName" value="${recPageInfo.FILE_NAME }">
											</td>
											<td>
											<a href="/ess/infoApplyLeave/downloadFile?fileName=${recPageInfo.FILE_PATH }&file=${recPageInfo.FILE_NAME }">
											   ${recPageInfo.FILE_NAME }
											</a>
											</td>
										</c:if>
									</tbody>
								</table>
						     </div>
						</td>
					</tr>
				</table>	
			</div>
	  	</form>	
	</div>
