<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

$(function(){
	var oldemp = $("#ISNOT_EVALUATE").val().split(",");
	for (var i = 0; i < oldemp.length; i++) {
		if (oldemp[i] == '1'){
			$("#isnoteva1").text("<spring:message code='ar.viewcycle.content.yes'/>");//是
			}
		if (oldemp[i] == '2'){
			$("#isnoteva2").text("<spring:message code='ar.viewcycle.content.yes'/>");//是
			}
		if (oldemp[i] == '3'){
			$("#isnoteva3").text("<spring:message code='ar.viewcycle.content.yes'/>");//是
			}
	}

	
});

function checkSyllabusDetail(){
	$("#syllabusDetail").attr('href','/edu/trainfile/syllabusInfo?PLAN_NO=${planManagerInfo.PLAN_NO }');
	$("#syllabusDetail").click();
}

</script>
<div class="pageContent" layoutH="10" >
		<input type="hidden" name="PLAN_NO" id="PLAN_NO" value="${planManagerInfo.PLAN_NO }">
		<input type="hidden" name="ISNOT_EVALUATE" id="ISNOT_EVALUATE" value="${planManagerInfo.ISNOT_EVALUATE }">
		<input type="hidden" name="TEACHER_NAME" id="TEACHER_NAME" value="${TEACHER_NAME }">
		<div class="pageFormContent nowrap">
		<table id="" class="" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainResult.KECHENGFENLEI.a"/><!--课程分类--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.TRAIN_TYPE_CODE_NAME }</span>  
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.PEIXUNNEIRONG.a"/><!--培训内容--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.TRAIN_CONTENT } </span>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.COURSE_NAME_CODE }</span>
		</td>
		<td class="td_title" width="1%"><spring:message code="empsubject.eduRm"/><!--培训地点--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.TRAIN_ADDRESS }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.ZHUGUANBUMEN.a"/><!--主管部门--></td>
		<td class="td_type"  width="20%" >
		<c:if test="${planManagerInfo.DEPART_MANA_CODE_NAME==null }">
		<span>${planManagerInfo.DEPART_MANA_CODE }</span>
		</c:if>
		<c:if test="${planManagerInfo.DEPART_MANA_CODE_NAME!=null }">
		<span>${planManagerInfo.DEPART_MANA_CODE_NAME }</span>
		</c:if>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.PEIXUNRENSHU.a"/><!--培训人数--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.TRAIN_PERSON_COUNT }</span>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.PEIXUNFANGSHI.a"/><!--培训方式--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.TRAIN_FORM_CODE_NAME }</span>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></td>
		<td class="td_type"  width="20%" >
		<c:if test="${planManagerInfo.CLASS_UNIT=='0' }">
		<span>${planManagerInfo.CLASS_HOUR }<spring:message code="display.mutual.month"/><!--月--></span>
		</c:if>
		<c:if test="${planManagerInfo.CLASS_UNIT=='1' }">
		<span>${planManagerInfo.CLASS_HOUR }<spring:message code="ar.viewitemparameter.title.dayofunit"/><!--天--></span>
		</c:if>
		<c:if test="${planManagerInfo.CLASS_UNIT=='2' }">
		<span>${planManagerInfo.CLASS_HOUR }<spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></span>
		</c:if>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.JIANGSHI.a"/><!--讲师--></td>
		<td class="td_type"  width="20%" >
		<span>${TEACHER_NAME }</span>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.SHIFOUXUYAOKAOSHI.a"/><!--是否需要考试--></td>
		<td class="td_type"  width="20%" >
		<c:if test="${planManagerInfo.ISNOT_TEST=='Y' }">
		<span><spring:message code="ar.viewcycle.content.yes"/><!--是--></span>
		</c:if>
		<c:if test="${planManagerInfo.ISNOT_TEST=='N' }">
		<span><spring:message code="ar.viewcycle.content.no"/><!--否--></span>
		</c:if>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.YUSUANFEIYONG.a"/><!--预算费用--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.BUDGET }</span>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainResult.SHIFOUXUYAOXUEYUANPINGJIA.a"/><!--是否需要学员评价--></td>
		<td class="td_type"  width="20%" id="isnoteva1" >
		<c:if test="${planManagerInfo.ISNOT_EVALUATE=='0' }">
		<span><spring:message code="ar.viewcycle.content.no"/><!--否--></span>
		</c:if>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainResult.KESHENQINGBUMEN.a"/><!--可申请部门--></td>
		<td class="td_type"  width="20%" >
		<span>  </span>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainResult.SHIFOUXUYAOJIANGSHIPINGJIA.a"/><!--是否需要讲师评价--></td>
		<td class="td_type"  width="20%" id="isnoteva2" >
		<c:if test="${planManagerInfo.ISNOT_EVALUATE=='0' }">
		<span><spring:message code="ar.viewcycle.content.no"/><!--否--></span>
		</c:if>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.JIHUAKAISHISHIJIAN.a"/><!--计划开始时间--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.PLAN_STARTDATE }</span>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.SHIFOUXUYAOPEIXUNPINGJIA.a"/><!--是否需要培训评价--></td>
		<td class="td_type"  width="20%" id="isnoteva3" >
		<c:if test="${planManagerInfo.ISNOT_EVALUATE=='0' }">
		<span><spring:message code="ar.viewcycle.content.no"/><!--否--></span>
		</c:if>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.JIHUAJIESHUSHIJIAN.a"/><!--计划结束时间--></td>
		<td class="td_type"  width="20%" >
		<span>${planManagerInfo.PLAN_ENDDATE }</span>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.SHIFOUXUYAOPEIXUNBAOGAO.a"/><!--是否需要培训报告--></td>
		<td class="td_type"  width="1%" >
		<c:if test="${planManagerInfo.ISNOT_REPORT=='Y' }">
		<span><spring:message code="ar.viewcycle.content.yes"/><!--是--></span>
		</c:if>
		<c:if test="${planManagerInfo.ISNOT_REPORT=='N' }">
		<span><spring:message code="ar.viewcycle.content.no"/><!--否--></span>
		</c:if>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.KECHENGBIAO.a"/><!--附加文件--></td>
		<td class="td_type"  width="20%" >
		  <a class="buttonActive" href="#" onclick="checkSyllabusDetail()"><span><spring:message code="button.sys.view"/><!--查看--></span>
		   </a>
		   <a  style="padding-left: 100px;color:blue;"  id="clander_id_u"  href="/edu/traineducation/queryCourseSyllabus3?PLAN_NO=${planManagerInfo.PLAN_NO }&AR_DATE_STR=${AR_DATE_STR}" target="dialog" mask="true"  rel="clander_id_u" width="600" height="400"><spring:message code="edu.planManager.KECHENGBIAO.a"/><!--课程表--></a>
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.planManager.SHIFOUYUNXUSHENQING.a"/><!--是否允许申请--></td>
		<td class="td_type"  width="20%" >
		<c:if test="${planManagerInfo.ISNOT_APPLY=='Y' }">
		<span><spring:message code="ar.viewcycle.content.yes"/><!--是--></span>
		</c:if>
		<c:if test="${planManagerInfo.ISNOT_APPLY=='N' }">
		<span><spring:message code="ar.viewcycle.content.no"/><!--否--></span>
		</c:if>
		</td>
		</tr>
	
		</table>
		</div>
		
		<a id="syllabusDetail"  href="" target="dialog" rel="addplancourse" mask="true" width="500" height="200"></a>
		
</div>
