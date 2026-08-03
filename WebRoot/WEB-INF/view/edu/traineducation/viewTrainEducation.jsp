<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div style="height:882px;line-height:529px;overflow:auto;overflow-x:hidden;background:url('/resources/images/trainEdu_index.jpg') no-repeat;">
		<div style="width:170px;padding-left:370px;margin-top:136px;height:300px;overflow:auto;overflow-x:hidden;float:left;">
			<div style="height:120px;overflow:auto;overflow-x:hidden;">
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/systemManager','pageNum=1&amp;menuNo=14014455&amp;navTabId=edu0101','edu0101','<spring:message code="edu.viewTrainEducation.TIXIGUANLI.a"/>');"><spring:message code="edu.viewTrainEducation.TIXIGUANLI.a"/><!--体系管理--></a></div>
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/courseManager','pageNum=1&amp;menuNo=14014456&amp;navTabId=edu0102','edu0102','<spring:message code="edu.viewTrainEducation.KECHENGGUANLI.a"/>');"><spring:message code="edu.viewTrainEducation.KECHENGGUANLI.a"/><!--课程管理--></a></div>
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/planManager','pageNum=1&amp;menuNo=14014457&amp;navTabId=edu0103','edu0103','<spring:message code="edu.viewTrainEducation.JIHUAGUANLI.a"/> ');"><spring:message code="edu.viewTrainEducation.JIHUAGUANLI.a"/><!--计划管理--></a></div>
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/teacherManager','pageNum=1&amp;menuNo=14014458&amp;navTabId=edu0104','edu0104','<spring:message code="edu.viewTrainEducation.JIANGSHIGUANLI.a"/>');"><spring:message code="edu.viewTrainEducation.JIANGSHIGUANLI.a"/><!--讲师管理--></a></div>
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/trainAgreement','pageNum=1&amp;menuNo=14014459&amp;navTabId=edu0105','edu0105','<spring:message code="edu.viewTrainEducation.XIEYIGUANLI.a"/>');"><spring:message code="edu.viewTrainEducation.XIEYIGUANLI.a"/><!--协议管理--></a></div>
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/trainOrgan','pageNum=1&amp;menuNo=14014460&amp;navTabId=edu0106','edu0106','<spring:message code="edu.viewTrainEducation.PEIXUNJIGOU.a"/>');"><spring:message code="edu.viewTrainEducation.PEIXUNJIGOU.a"/><!--培训机构--></a></div>
			</div>
			<div style="margin-top:80px;">
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/trainfile/trainCalendar','pageNum=1&amp;menuNo=14014468&amp;navTabId=edu0301','edu0301','<spring:message code="edu.viewTrainEducation.PEIXUNRILI.a"/>');"><spring:message code="edu.viewTrainEducation.PEIXUNRILI.a"/><!--培训日历--></a></div>
				<%-- <div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/trainfile/personalTrainCalendar?actionType=personal','pageNum=1&amp;menuNo=14014469&amp;navTabId=edu0302','edu0302','<spring:message code="edu.viewTrainEducation.GERENPEIXUNRILI.a"/>');"><spring:message code="edu.viewTrainEducation.GERENPEIXUNRILI.a"/><!--个人培训日历--></a></div> --%>
				<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/trainArchives','pageNum=1&amp;menuNo=14014470&amp;navTabId=edu0303','edu0303','<spring:message code="edu.viewTrainEducation.PEIXUNDANGAN.a"/>');"><spring:message code="edu.viewTrainEducation.PEIXUNDANGAN.a"/><!--培训档案--></a></div>
			</div>
		</div>
		<div style="width:170px;margin-left:190px;margin-top:135px;height:300px;overflow:auto;overflow-x:hidden;float:left;">
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/trainBasicInformation','pageNum=1&amp;menuNo=14014462&amp;navTabId=edu0201','edu0201','<spring:message code="hr.viewCondSql.title.JIBENXINXI"/>');"><spring:message code="hr.viewCondSql.title.JIBENXINXI"/><!--基本信息--></a></div>
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/studentEvaluate','pageNum=1&amp;menuNo=14014463&amp;navTabId=edu0202','edu0202','<spring:message code="edu.teacherEvaluate.XUEYUANKAOPING.a"/>');"><spring:message code="edu.teacherEvaluate.XUEYUANKAOPING.a"/><!--学员考评--></a></div>
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/teacherEvaluate','pageNum=1&amp;menuNo=14014464&amp;navTabId=edu0203','edu0203','<spring:message code="edu.planManager.JIANGSHIPINGJIA.a"/>');"><spring:message code="edu.planManager.JIANGSHIPINGJIA.a"/><!--讲师评价--></a></div>
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/trainResult','pageNum=1&amp;menuNo=14014465&amp;navTabId=edu0204','edu0204','<spring:message code="liang.hr.viewTraining.title.TRAINING_RESULT"/>');"><spring:message code="liang.hr.viewTraining.title.TRAINING_RESULT"/><!--培训结果--></a></div>
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/trainCostManager','pageNum=1&amp;menuNo=14014466&amp;navTabId=edu0205','edu0205','<spring:message code="edu.viewTrainEducation.FEIYONGGUANLI.a"/>');"><spring:message code="edu.viewTrainEducation.FEIYONGGUANLI.a"/><!--费用管理--></a></div>
			<div style="padding:3px;margin-top:110px;"><a href="#" onclick="navTabNum('/edu/traineducation/courseApply','pageNum=1&amp;menuNo=14014472&amp;navTabId=edu0401','edu0401','<spring:message code="edu.viewTrainEducation.KECHENGSHENQING.a"/>');"><spring:message code="edu.viewTrainEducation.KECHENGSHENQING.a"/><!--课程申请--></a></div>
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/courseMaker','pageNum=1&amp;menuNo=14014473&amp;navTabId=edu0402','edu0402','<spring:message code="edu.viewTrainEducation.KECHENGJUECAI.a"/>');"><spring:message code="edu.viewTrainEducation.KECHENGJUECAI.a"/><!--课程决裁--></a></div>
		    <div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/courseConfirm','pageNum=1&amp;menuNo=14014474&amp;navTabId=edu0403','edu0403','<spring:message code="edu.viewTrainEducation.KECHENGQUEREN.a"/>');"><spring:message code="edu.viewTrainEducation.KECHENGQUEREN.a"/><!--课程确认--></a></div>
		    <div style="padding:3px;"><a href="#" onclick="navTabNum('/edu/traineducation/makerSituation','pageNum=1&amp;menuNo=14014475&amp;navTabId=edu0404','edu0404','<spring:message code="edu.viewTrainEducation.KECHENGJINXINGQINGKUANG.a"/>');"><spring:message code="edu.viewTrainEducation.KECHENGJINXINGQINGKUANG.a"/><!--课程进行情况--></a></div>
		</div>
		<div style="width:170px;margin-left:140px;margin-top:260px;height:120px;overflow:auto;overflow-x:hidden;float:left;">
			<div style="padding:3px;"><a href="#" onclick="navTabNum('/report/ar/viewTrainReport','pageNum=1&amp;menuNo=14014477&amp;navTabId=edu0501','edu0501','<spring:message code="edu.viewTrainEducation.BAOBIAOLIEBIAO.a"/>');"><spring:message code="edu.viewTrainEducation.BAOBIAOLIEBIAO.a"/><!--报表列表--></a></div>
		</div>
	</div>
</div>
