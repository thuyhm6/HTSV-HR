<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link href="/resources/css/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/util/StringUtil.js"></script>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>个人保险查看</title>
	</head>
	<script type="text/javascript">
	function Search(){
		document.getElementById('logtype').value="READ" ; 
		document.form.action="/paBenControlServlet?operation=paBen_benchmark_lookPersonal&menu_code=${menu_code}";
		showHidObje(maskArray,"","数据加载中");
		document.form.submit();
	}
	var time=null;
	function textCounter(v){
	       var j = 0;
	       var i = 0;
	       var len = v.length;
	       for(i = 0 ; i < len; i ++){
	           if(v.charCodeAt(i)> 255){
	                j += 2;
	            }else{
	                j++;
	            }
	       }
	       return j;
	}

	function SearchContent(condition,id){ 
	    if(condition == '')return ;
		if(time!=null){
			clearTimeout(time);
			time=null;  
		}
		
		if(textCounter(condition)>0){
		time=setTimeout(function(){
						//alert(condition);
							SearchC(condition,id);
						},500);  
		}
	}

	function layerClose()
	{
		$('emp_list').innerHTML = "" ;
		layer.style.visibility = 'hidden';
		layer.style.zIndex = -1;
	}
	//搜索职员信息
	function SearchC(condition,id){

		if(condition == null || $.StringUtil.trim(condition).length == 0){
			return ;
		}
		
			var url = "/is/insurancesystem/viewEmpInfo" ;
			//var pars = "operation=SearchEmployeeAllCpny&condition=" + encodeURIComponent(condition);
			var pars = "condition=" + encodeURIComponent(condition); 
			
			var inputBox = $('#' + id).get(0);
			var iBtop  = inputBox.offsetTop;     //文本框的定位点高
			var iBheight  = inputBox.clientHeight;  //文本框本身的高
			var iBleft = inputBox.offsetLeft - 80;    //文本框的定位点宽	
			while (inputBox = inputBox.offsetParent){iBtop+=inputBox.offsetTop;iBleft+=inputBox.offsetLeft;}
			layer = $('#emp_list').get(0);
			
			layer.style.top = iBtop+iBheight+6;
			layer.style.left = iBleft;      
	        $.post(url,pars,function(data){

				var jObj = $(data) ; 
	        	if(jObj.find("#empListSize").val() == 1){
	        		var trObj = jObj.find("tr")[1] ;
	            	$(trObj).click() ;
	            	return ;
	        	}
	            layer.innerHTML=data;
	            layer.style.width = 500;
	            layer.style.height = 200;
	            layer.style.visibility = 'visible';
	            layer.style.zIndex = 2;
	        });	
	}
	 
	function updateValue(cell) {

			$('#empID').val(cell.childNodes[0].firstChild.nodeValue);
			$('#personID').val(cell.childNodes[1].firstChild.nodeValue); 
			layerClose();
			
			if(Search){
				 Search();
			}
	}
    </script>
	<body>
	<form name="form" method="post">
	 <input type="hidden" name="logflag" id="logflag" value="true">
                <input type="hidden" name="logtype" id="logtype" value="READ">
                <input type="hidden" name="logcode" id="logcode" value="RE,PA">
				
					<br>
					<table width="100%" border="1" bordercolor="#A8A8A8" cellpadding="0" cellspacing="0" class="table_list">
				<tr>
					<td width="11" height="33" valign="TOP" align="RIGHT">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button>搜索</button>
					</td>
				</tr>
				
				<tr>
					
					<td class="info_title_000" width="10%">
						查询条件</td>
  				</tr>
	  			<tr align="center">
	  				<td>
		  				<table width="100%"  bordercolor="#A8A8A8" cellpadding="0" cellspacing="0" class="table_list">
			  				<tr>
								<td class="info_title_01" width="10%">
									职号
								</td>
								<td class="info_content_01" width="15%">
								<input id="personID" name="personID" size="8" value="${searchObj.EMPID}" onkeyup="SearchContent(this.value,this.id)" title='请输入职号查找' required/>
								<%-- <c:choose>
									<c:when test="${menu_code ne 'paBen0109'}">
										<c:out value='${empID}'/>
										<input type="hidden" id="empID" name="empID" value="<c:out value='${empID}'/>"/>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="empID" id="empID" size="8" value="<c:out value='${searchObj.EMPID}'/>" />
										<input id="personID" name="personID" size="8" value="<c:out value='${searchObj.EMPID}'/>" onkeyup="SearchContent(this.value,this.id)" title='<ait:message messageID="alert.emp.staff_info.basic_info.search_emp_id" module="hrm"/>' required/>
									</c:otherwise>
								</c:choose> --%>
								</td>
								<td class="info_title_01" width="10%">
								姓名
								</td>
								<td class="info_content_01" width="15%">
									${searchObj.CHINESENAME}&nbsp;
								</td>
								<td class="info_title_01" width="10%">
									部门
								</td>
								<td class="info_content_01" width="15%">
									 ${searchObj.DEPTNAME}&nbsp;
								</td>
								<td class="info_title_01" width="10%">
									身份证号码
								</td>
								<td class="info_content_01" width="15%">
									${searchObj.IDCARD_NO}&nbsp;
								</td>
			  				</tr>
			  				<tr>
							 	<td class="info_title_01" width="10%">
									开始年月 
								</td>
								<td class="info_content_01" width="15%">
									<ait:date yearName="startYear" monthName="startMonth" yearSelected="${startYear}" monthSelected="${startMonth}" yearPlus="10" yearMinus="10"/>
								</td>
								<td class="info_title_01" width="10%">
								结束年月 
								</td>
								<td class="info_content_01" width="15%">
									<ait:date yearName="endYear" monthName="endMonth" yearPlus="10" yearSelected="${endYear}" monthSelected="${endMonth}" yearMinus="10"/>
								</td>
								<td class="info_title_01" width="10%">
									入社日期 
								</td>
								<td class="info_content_01" width="15%">
									${searchObj.DATE_STARTED}&nbsp;
								</td>
								<td class="info_title_01" width="10%">
									离职日期 
								</td>
								<td class="info_content_01" width="15%">
									${searchObj.DATE_LEFT}&nbsp;
								</td>
			  				</tr>
		  				</table>
	  				</td>
				</tr>
			</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" height="30">
						<tr><td>&nbsp;</td></tr>
					</table>
					<table align="left" width="40%" border="0" cellpadding="0" cellspacing="0" class="table_list">
			  				<tr>
								<td class="info_title_000" width="10%">
									保险名称 
								</td>
								<td class="info_content_01" width="10%">
									<select>
										<option>全部</option>
										<option>养老保险</option>
										<option>医疗保险</option>
										<option>生育保险</option>
										<option>工伤保险</option>
										<option>失业保险</option>
										<option>重大疾病</option>
									</select>
								</td>
			  				</tr>
		  			</table>
		  			<table width="100%" border="0" cellspacing="0" cellpadding="0" height="15">
						<tr><td>&nbsp;</td></tr>
					</table>
					<table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td colspan="8" class="info_content_01"></td>
  						</tr>
					</table>
		  			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list">
						<tr>
					      <td class="info_title_01" colspan="2">社保账号 </td>
					      <td class="info_content_01" colspan="4">${searchObj.SOCIAL_NO}&nbsp;</td>
					      <td class="info_title_01" colspan="2">户口性质 </td>
					      <td class="info_content_01" colspan="2">${searchObj.REG_TYPE_NAME}&nbsp;</td>
					    </tr>
					    <tr>
					      <td class="info_title_01" colspan="2">扣款/缴纳开始 </td>
					      <td class="info_content_01" colspan="4">${searchObj.START_DATE}&nbsp;</td>
					      <td class="info_title_01" colspan="2">缴纳结束</td>
					      <td class="info_content_01" colspan="2">${searchObj.END_DATE}&nbsp;</td>
					    </tr>
					    <tr>
					      <td rowspan="2" class="info_title_01" colspan="2"> 缴纳年月 </td>
					      <td rowspan="2" class="info_title_01" colspan="2">保险名称</td>
					      <td rowspan="2" class="info_title_01" colspan="2">缴纳基数</td>
					      <td colspan="2" class="info_title_01"> 缴纳比例</td>
					      <td colspan="2" class="info_title_01"> 缴纳金额 </td>
					    </tr>
					    <tr>
					      <td class="info_title_01" colspan="1">公司</td>
					      <td class="info_title_01" colspan="1">个人</td>
					      <td class="info_title_01" colspan="1">公司 </td>
					      <td class="info_title_01" colspan="1">个人 </td>
					    </tr>
					    <c:set value="0" var="n1"></c:set>
					    <c:set value="0" var="n2"></c:set>
					    <c:forEach items="${st}" var="info">
					    <tr>
					      <td class="info_content_01" colspan="2">${info.PA_MONTH}&nbsp;</td>
					      <td class="info_content_01" colspan="2">${info.INSURANCE_NAME}&nbsp;</td>
					      <td class="info_content_01" colspan="2">${info.INSURANCE_BASE}&nbsp;</td>
					      <td class="info_content_01" colspan="1">
					      	<fmt:formatNumber value="${info.COR_RATE}" type="percent" maxFractionDigits="2"/>&nbsp;
					      </td>
					      <td class="info_content_01" colspan="1">
					      	<fmt:formatNumber value="${info.PER_RATE}" type="percent" maxFractionDigits="2"/>&nbsp;
					      </td>
					      <td class="info_content_01" colspan="1">${info.INSURANCE_COR_PAY}&nbsp;</td>
					      <td class="info_content_01" colspan="1">${info.INSURANCE_PER_PAY}&nbsp;</td>
					    </tr>
					    	<c:set value="${info.INSURANCE_COR_PAY + n1}" var="n1"></c:set>
					    	<c:set value="${info.INSURANCE_PER_PAY + n2}" var="n2"></c:set>
					    </c:forEach>
					    <tr>
					      <td colspan="8" class="info_title_01">合计</td>
					      <td class="info_content_01" colspan="1">
					      	<fmt:formatNumber type="number" value="${n1}" maxFractionDigits="2"/>
					      </td>
					      <td class="info_content_01" colspan="1">
					      	<fmt:formatNumber type="number" value="${n2}" maxFractionDigits="2"/>
					      </td>
					    </tr>
					  </table>
					  <table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
						<tr><td>※&nbsp;员工缴纳社保的数据，以社会保险经办机构掌握数据为准。</td></tr>
						<c:if test="${companyID eq 'CompanyId02'}">
							<tr>
								<td>
									※&nbsp;自2012年1月起，大额医疗（重大疾病）保险征收方式发生变化：
								</td>
							</tr>
							<tr>
								<td>
									由原来的每月从员工工资中代扣代缴改为每年一月从员工医疗保险个人账户金中一次性扣除，详细信息，请咨询开发区社保中心。
								</td>
							</tr>
						</c:if>
					  </table>
					<table width="100%" border="0" cellspacing="0" cellpadding="1">
						<c:forEach var="i" begin="1" end="8"
							step="1">
							<tr>
								<td class="info_content_01" height="30"></td>
								<td class="info_content_01"></td>
								<td class="info_content_01"></td>
								<td class="info_content_01"></td>
								<td class="info_content_01"></td>
								<td class="info_content_01"></td>
							</tr>
						</c:forEach>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" height="15">
						<tr><td>&nbsp;</td></tr>
					</table>
		</td>
		<td background="/img/tablbk01_r4_c26.gif" width="10">&nbsp;</td>
	</tr>
</table>
<div id="emp_list"  style="position:absolute;overflow:auto; top:200;width:500; height:210; z-index:-2;visibility: hidden;">   
	</div>
</form>
</body>
</html>