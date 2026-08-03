<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	/* $("#onlyForm").attr("action",'${request_actionUrl}'
								  +"?SUBSD_CD="+'${request_SUBSD_CD}'
	                              +"&JOB_TP="+'${request_JOB_TP}'
	                              +"&ATT_MON="+'${request_ATT_MON}'
	                              +"&reportName="+'${request_reportName}'
	                              +"&SUBSD_NAME="+'${request_SUBSD_NAME}'
	                              +"&ORG_ID="+'${request_ORG_ID}'
	                              +"&suffix="+'${request_suffix}'); */
	var params = '${request_params}';
	params = params.replaceAll("@", "&");
	
	$("#onlyForm").attr("action",'${request_actionUrl}'
	    								  +"?params="+params);
	$("#onlyForm").attr("target","result");
	$("#onlyForm").submit(); 
});
</script>
<form action="" id="onlyForm" rel="htmlReport" method="post"></form>
<iframe name=result id=result width=100% height=100% frameborder=0 scrolling=auto src="/resources/reportFile/blank.html"></iframe>
