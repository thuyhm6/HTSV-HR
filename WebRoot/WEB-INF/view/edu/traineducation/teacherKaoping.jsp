<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	$('#yirongyibiao option[value='+"${teacherKaopingInfo.GROOMING}"+']').attr('selected','selected');
	$('#jijireqing option[value='+"${teacherKaopingInfo.POSITIVE}"+']').attr('selected','selected');
	$('#kechengneirong option[value='+"${teacherKaopingInfo.COURSE_ENRICH}"+']').attr('selected','selected');
	$('#hudongxing option[value='+"${teacherKaopingInfo.INTERACT}"+']').attr('selected','selected');
});

function baocuntea(){
	$('#teacherKaoping').submit();
	$('#weikao_'+"${teacherKaopingInfo.TEA_EMPID }").attr('style','color:red;');
	$('#weikaoping_'+"${teacherKaopingInfo.TEA_EMPID }").html("<spring:message code='edu.teacherEvaluate.YIKAOPING.a'/>");//已考评
	$.pdialog.closeCurrent();
}
function quxiaotea(){
	$.pdialog.closeCurrent();
}
function jisuanteacher(){
	var yirongyibiao=$('#yirongyibiao').attr('value');
	var jijireqing=$('#jijireqing').attr('value');
	var kechengneirong=$('#kechengneirong').attr('value');
	var hudongxing=$('#hudongxing').attr('value');
	var allscore=(20*parseInt(yirongyibiao)+20*parseInt(jijireqing)+30*parseInt(kechengneirong)+30*parseInt(hudongxing))*0.2;
	$('#allScoreTea').html(allscore);
	$('#ALLSCORETEA').attr('value',allscore);
}
</script>
		<form method="post" id="teacherKaoping" action="/edu/traineducation/updateTeacherKaoping" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageContent" layoutH="10">
		                <ul style="padding-left: 470px;">
							<li>
								<a class="buttonActive" href="#" onclick="baocuntea()">
									<span><spring:message code="ess.message.save"/><!--保存--></span>
								</a>
							</li>
							<li >
								<a class="buttonActive"  href="#" onclick="quxiaotea()">
									<span><spring:message code="ess.empInfo.cancel"/><!--取消--></span>
								</a>
							</li>
						</ul>
				<input type="hidden" name="TEA_EMPID" id="TEA_EMPID" value="${teacherKaopingInfo.TEA_EMPID }">
				<input type="hidden" name="TEA_LOCAL_NAME" id="TEA_LOCAL_NAME" value="${teacherKaopingInfo.TEA_LOCAL_NAME }">
				<input type="hidden" name="STU_EMPID" id="STU_EMPID" value="${curStudentEmpid }">
				<input type="hidden" name="STU_LOCAL_NAME" id="STU_LOCAL_NAME" value="${curStudentName }">
				<input type="hidden" name="CHECK_NO" id="CHECK_NO" value="${teacherKaopingInfo.CHECK_NO }">
				<input type="hidden" name="BASIC_NO" id="BASIC_NO" value="${BASIC_NO }">
				<table id="tearcherTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
				<tr>
				<td class="td_title" width="5%"><spring:message code="edu.studentKaoping.PINGFENXIANGMU.a"/><!--评分项目--></td>
				<td class="td_title" width="5%">${curStudentName }</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.teacherEvaluate.YIRONGYIBIAO.a"/><!--仪容仪表(20%)--></td>
				<td class="td_type"  width="5%" >
				<select name="GROOMING" id="yirongyibiao" onchange="jisuanteacher()">
				<option value="0"><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
				<option value="5">5</option>
				<option value="4">4</option>
				<option value="3">3</option>
				<option value="2">2</option>
				<option value="1">1</option>
				</select>
				</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.teacherEvaluate.JIJIREQING.a"/><!--积极热情(20%)--></td>
				<td class="td_type"  width="5%" >
				<select name="POSITIVE" id="jijireqing" onchange="jisuanteacher()">
				<option value="0"><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
				<option value="5">5</option>
				<option value="4">4</option>
				<option value="3">3</option>
				<option value="2">2</option>
				<option value="1">1</option>
				</select>
				</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.teacherEvaluate.KECHENGNEIRONGZHUNBEICHONGSHI.a"/><!--课程内容准备充实(30%)--></td>
				<td class="td_type"  width="5%" >
				<select name="COURSE_ENRICH" id="kechengneirong" onchange="jisuanteacher()">
				<option value="0"><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
				<option value="5">5</option>
				<option value="4">4</option>
				<option value="3">3</option>
				<option value="2">2</option>
				<option value="1">1</option>
				</select>
				</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.teacherEvaluate.HUDONGXING.a"/><!--互动性(30%)--></td>
				<td class="td_type"  width="5%" >
				<select name="INTERACT" id="hudongxing" onchange="jisuanteacher()">
				<option value="0"><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
				<option value="5">5</option>
				<option value="4">4</option>
				<option value="3">3</option>
				<option value="2">2</option>
				<option value="1">1</option>
				</select>
				</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.studentKaoping.ZONGGONGPINGFEN.a"/><!--总共评分--></td>
				<td class="td_type"  width="5%" >
				<span id="allScoreTea">${teacherKaopingInfo.ALLSCORE }</span>
				<input type="hidden" name="ALLSCORE" id="ALLSCORETEA" value="${teacherKaopingInfo.ALLSCORE }">
				</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.teacherEvaluate.JIANYI.a"/><!--建议--></td>
				<td class="td_type"  width="5%" >
				<input type="text" name="OTHER_ADVISE" id="OTHER_ADVISE" value="${teacherKaopingInfo.OTHER_ADVISE }">
				</td>
				</tr>
				
				</table>
		</div>
		</form>
