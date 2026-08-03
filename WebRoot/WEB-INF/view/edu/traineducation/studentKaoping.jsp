<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	$('#chuqinlv option[value='+"${studentKaopingInfo.ATTEN_RATES}"+']').attr('selected','selected');
	$('#jijixing option[value='+"${studentKaopingInfo.ENTHUSIASM}"+']').attr('selected','selected');
	$('#kechengzhangwo option[value='+"${studentKaopingInfo.COURSE_CROL}"+']').attr('selected','selected');
});

function baocun3(){
	$('#studentKaoping').submit();
	$('#weikao_'+"${curStudentEmpid}").attr('style','color:red;');
	$('#weikaoping_'+"${curStudentEmpid}").html("<spring:message code='edu.teacherEvaluate.YIKAOPING.a'/>");//已考评
	$.pdialog.closeCurrent();
}
function quxiao3(){
	$.pdialog.closeCurrent();
}
function jisuan(){
	var chuqinlv=$('#chuqinlv').attr('value');
	var jijixing=$('#jijixing').attr('value');
	var kechengzhangwo=$('#kechengzhangwo').attr('value');
	var allscore=(30*parseInt(chuqinlv)+30*parseInt(jijixing)+40*parseInt(kechengzhangwo))*0.2;
	$('#allScore').html(allscore);
	$('#ALLSCORE').attr('value',allscore);
}
</script>
		<form method="post" id="studentKaoping" action="/edu/traineducation/addStudentKaoping" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageContent" layoutH="10">
		                <ul style="padding-left: 470px;">
							<li>
								<a class="buttonActive" href="#" onclick="baocun3()">
									<span><spring:message code="ess.message.save"/><!--保存--></span>
								</a>
							</li>
							<li >
								<a class="buttonActive"  href="#" onclick="quxiao3()">
									<span><spring:message code="ess.empInfo.cancel"/><!--取消--></span>
								</a>
							</li>
						</ul>
				<input type="hidden" name="TEA_PERSON_ID" id="TEA_PERSON_ID" value="${curTeacherPersonid }">
				<input type="hidden" name="TEA_LOCAL_NAME" id="TEA_LOCAL_NAME" value="${curTeacherName }">
				<input type="hidden" name="STU_EMPID" id="STU_EMPID" value="${curStudentEmpid }">
				<input type="hidden" name="STU_LOCAL_NAME" id="STU_LOCAL_NAME" value="${curStudentName }">
				<input type="hidden" name="CHECK_NO" id="CHECK_NO" value="${checkno }">
				<input type="hidden" name="BASIC_NO" id="BASIC_NO" value="${BASIC_NO }">
				<table id="tearcherTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
				<tr>
				<td class="td_title" width="5%"><spring:message code="edu.studentKaoping.PINGFENXIANGMU.a"/><!--评分项目--></td>
				<td class="td_title" width="5%">${curTeacherName }</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.studentChakan.CHUQINLV.a"/><!--出勤率(30%)--></td>
				<td class="td_type"  width="5%" >
				<select name="ATTEN_RATES" id="chuqinlv" onchange="jisuan()">
				<option value="0"><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!--请选择--></option>
				<option value="5">5</option>
				<option value="4">4</option>
				<option value="3">3</option>
				<option value="2">2</option>
				<option value="1">1</option>
				</select>
				</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.studentChakan.JIJIXING.a"/><!--积极性(30%)--></td>
				<td class="td_type"  width="5%" >
				<select name="ENTHUSIASM" id="jijixing" onchange="jisuan()">
				<option value="0"><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!--请选择--></option>
				<option value="5">5</option>
				<option value="4">4</option>
				<option value="3">3</option>
				<option value="2">2</option>
				<option value="1">1</option>
				</select>
				</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.studentChakan.KETANGZHANGWO.a"/><!--课程掌握(40%)--></td>
				<td class="td_type"  width="5%" >
				<select name="COURSE_CROL" id="kechengzhangwo" onchange="jisuan()">
				<option value="0"><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!--请选择--></option>
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
				<span id="allScore">${studentKaopingInfo.ALLSCORE }</span>
				<input type="hidden" name="ALLSCORE" id="ALLSCORE" value="${studentKaopingInfo.ALLSCORE }">
				</td>
				</tr>
				
				<tr>
				<td class="td_type"  width="5%" ><spring:message code="edu.studentChakan.QITAJIANYI.a"/><!--其它建议--></td>
				<td class="td_type"  width="5%" >
				<input type="text" name="OTHER_ADVISE" id="OTHER_ADVISE" value="${studentKaopingInfo.OTHER_ADVISE }">
				</td>
				</tr>
				
				</table>
		</div>
		</form>
