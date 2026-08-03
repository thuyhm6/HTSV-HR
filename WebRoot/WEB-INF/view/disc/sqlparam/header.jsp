<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="javascript">
    
	function showDetailWindow(url){
	    var URL=encodeURI(url);
		window.open(URL, "preview", 'toolbar=no,location=no,directories=no,status=no, menubar=no, scrollbars=no, resizable=no, width=150, height=150, top=150, left=170');
	}	
	
	function retrieveParamDesc(paramType,index,param){
		hidden.location = "/ait.disc.retrieveParamDesc.laf?SQL_PARAM_TP="+paramType+"&paramIndex="+index+"&param="+param;
	}
	
	function formSubmit(){
		 var returnVal = window.showModalDialog("/autoExcel/encryptExcel.jsp", null,"dialogHeight:200px;dialogWidth:300px;scroll:no;status:yes;help:no");
  	    if(returnVal==undefined ||returnVal=='')
  	    	{
  	    		return;
  	    	}
        document.form1.action = "/ait.disc.setSqlParamAndRun"+"?pwd="+returnVal;
        document.form1.submit();
	}
	
	function showOrganizationSelectDialog(input){
	    window.showModalDialog("/common/sys/organizationTree.jsp", input, "dialogHeight: 385px; dialogWidth: 318px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
	}
	
	function showDateSelectDialog(input){
	    window.showModalDialog("/common/calendar.jsp", input, "dialogHeight: 213px; dialogWidth: 171px; edge: Raised; center: Yes; help: No; resizable: No; status: No;");
	}
	
	function showEmployeeSelectWindow(url){
	    window.open(url, "employee", 'toolbar=no,location=no,directories=no,status=no, menubar=no, scrollbars=yes, resizable=no, width=830, height=300, top=150, left=170');
	}	
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="form1" method="post"> 
<table  class="table" width="754" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="7" colspan="3"></td>
  </tr>
	<tr>
    <td height="3" colspan="3" ></td>
  </tr>
	<tr>
	<td width="20">&nbsp;</td>
    <td height="11" colspan="3"></td>
  </tr>
  <tr> 
    <td width="20">&nbsp;</td>
   <!--Page Title -->
  <td width="734" height="15" ></td>
    <td width="20">&nbsp;</td>
  </tr>
  <tr> 
    <td width="20"></td>
  <td width="734" align="right" valign="top">
  		
   <table width="100%" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="14"></td>
        </tr>
        <tr> 
          <td height="2" ></td>
        </tr>
        <tr> 
          <td>
          
    <table width="100%" border="0" cellspacing="1" cellpadding="0"  >
    	<thead>
       <tr> 
        <th width="100" >"SQL Seq </td>
        <td>
        	<c:out value="${request_SQL_SEQ}" />
        	<input type="hidden" name="SQL_SEQ" value="<c:out value="${request_SQL_SEQ}" />">
        </td>
       </tr>
       <tr> 
        <th >Program Name <br></td>
        <td >
			<c:out value="${request_PGM_NM}" />
        </td>
       </tr>
       <tr> 
        <th>SQL Name<br></td>
        <td >
			<c:out value="${request_SQL_NM}" />
        </td>
       </tr>
        <tr>
	        <th >Parameters</td>
	        <td >
		        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tr> 
						<td width="15%" align="center" >Param</td>
						<td width="35%" align="center" >Desc Eng</td>
						<td width="35%" align="center" >Desc Chn></td>
						<td width="15%" align="center" >Value"</td>
		        </tr>
		        <tr>
		          <td colspan="6" class="table_header_line"></td>
		        </tr>
		        	