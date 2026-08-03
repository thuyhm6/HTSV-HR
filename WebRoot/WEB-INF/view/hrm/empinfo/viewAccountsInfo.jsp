<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<html>
<head>

<title>Translate information</title>
<%@ include file="../../inc/initMeta.jsp"%>
</head>
<SCRIPT type="text/javascript">
  var $grids={};
/**
	工资
*/
  $(function () {
  			  $(this).loginWait('mainBody','loading',250);
		  	  $(this).initStyle('/sys/getModel?mtype=hrm&mjsp=accountsInfo','/sys/updateModel?mtype=hrm&mjsp=accountsInfo');
 			  $("#sinfoGrid").ligerGrid({
                columns: [
                { display: '<spring:message code="hrm.salaryAdjustmentsDate"/>', name: 'RECTIFYDATE', align: 'center', width: 140, minWidth: 60 },
                { display: '<spring:message code="hrm.salaryAdjustmentsSubject"/>', name: 'RECTIFYREASON', minWidth: 100 },
                { display: '<spring:message code="hrm.basicSalary"/>', name: 'ANNUALSALARY', minWidth: 110 }, 
                { display: '<spring:message code="hrm.salary"/>', name: 'MONTHSALARY', minWidth: 110 }, 
                { display: '<spring:message code="hrm.bonus"/>', name: 'BONUS', minWidth: 110 }, 
                { display: '<spring:message code="hrm.achievementAward"/>', name: 'FRUITBONUS', minWidth: 110 }, 
                { display: '<spring:message code="hrm.mealSupplement"/>', name: 'EATERYALLOWANCE', minWidth: 110 },
                { display: '<spring:message code="hrm.trafficUp"/>', name: 'TRAFFICALLOWANCE', render: function(item,index)
		                {
		                	if(item.REASONS==null)
		                	{
		                		 item.REASONS='';
		                		 return item.REASONS;
		                	}
		                	 return item.REASONS;
		                }
                },
                { display: '<spring:message code="hrm.stableLifeCosts"/>', name: 'HOUSEHOLDALLOWANCE', minWidth: 110 },
                { display: '<spring:message code="hrm.housingSubsidies"/>', name: 'TENEMENTALLOW', minWidth: 110 },
                { display: '<spring:message code="hrm.total"/>', name: 'TOTAL', minWidth: 110 }
                ], 
                url: "/hrm/empinfo/getSinfoList?empid=${basicInfo.EMPID }",  
                sortName: 'EMPID', 
		        dataAction: 'server',	               
			    pageSize: 5,
			    pageSizeOptions: [5,4,3,2,1], 
			    width: '92%',
			    height: '33%'
            });  
  /**
  		账户
  */
  			$("#paEmpInfoGrid").ligerGrid({
                columns: [
                { display: '<spring:message code="hrm.wageMark"/>', name: 'CALCFLAG', align: 'center', width: 140, minWidth: 60,editor:
            	{ type:'select',data: {"Rows":[{"CODE_ID":"Y","CODE_NAME":"是"},{"CODE_ID":"N","CODE_NAME":"否"}],"Total":2}.Rows, dataValueField: 'CODE_ID', dataDisplayField: 'CODE_NAME', valueColumnName: 'CALCFLAG'},
	              render: function (item)
	                    {
	                        return girdChooseSelect(this,item);
	                    }
	            },
                { display: '<spring:message code="hrm.bank"/>', name: 'BANKNAMECODE', minWidth: 110,editor:
            	{ type:'select',data: ${bankNameCodeMap}.Rows, dataValueField: 'CODE_ID', dataDisplayField: 'CODE_NAME', valueColumnName: 'BANKNAMECODE'},
	              render: function (item)
	                    {
	                        return girdChooseSelect(this,item);
	                    }
	            },
                { display: '<spring:message code="hrm.accountNumber"/>', name: 'CARDNO', minWidth: 130,editor:{type : 'string'} }
                ],
                enabledEdit: true, 
                url: "/hrm/empinfo/getPaEmpInfoList?empid=${basicInfo.EMPID }",  
                sortName: 'EMPID', 
		        dataAction: 'server',	               
			    pageSize: 5,
			    pageSizeOptions: [5,4,3,2,1], 
			    width: '92%',
			    height: '33%'
            });    
            $grids=$.ligerExpandGrid.CRUD.initSubmit('#paEmpInfoGrid');
        });
function save()
	   {
	   	
	   	$grids.gridSubmit();
	   	var input="<input type='hidden' name='#paEmpInfoGrid' value='"+$grids.jsonData['#paEmpInfoGrid']+"'/>";
			$("#updateGrid").append(input);
	     	$("#updateGrid").submit();
	     return false;  
	   }
			
</SCRIPT>
<style type="text/css">           
        	.l-button-update,.l-button-edit{width:80px; float:right; 
        	margin-right:10px;margin-top: 10px;}    
    	</style>
<body>


<div id="loading" style="width:90%;text-align:center;padding-top: 200px;position: absolute;">
			<img src="/resources/images/loading.gif">
</div>
<div id="mainBody">
		<input type="button" value='保存' id="save" onclick="save()" class="l-button l-button-update"/>
		<input type="button" value='<spring:message code="submit"/>' id="update" class="l-button l-button-update"/>
		<input type="button" value='<spring:message code="edit"/>' onclick="edit();" class="l-button l-button-edit" />		
<form id="infoForm">
	<%@include file="viewBasicInfo.jsp"%>

	<div id="sinfoinfo" onmouseout="change('sinfoinfo');"
					style="border: dashed 1px #cccccc;position: absolute;">
					<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
						<tr>
							<td align="right">
							<img src="/resources/images/title/top_1.gif" align="center"/>
							</td>
							<td align="left"><!-- 工资 -->
								<font size="2"><spring:message code="hrm.wage"/></font>
							</td>						
						</tr>
					</table>
	</div>
    <div id="sinfoGrid" onmouseout="change('sinfoGrid');"
					style="border: dashed 1px #cccccc;position: absolute;"></div>
    <div id="paEmpInfoinfo" onmouseout="change('paEmpInfoinfo');"
					style="border: dashed 1px #cccccc;position: absolute;">
					<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
						<tr>
							<td align="right">
							<img src="/resources/images/title/top_1.gif" align="center"/>
							</td>
							<td align="left"><!-- 账户 -->
								<font size="2"><spring:message code="hrm.account"/></font>
							</td>						
						</tr>
					</table>
	</div>
    <div id="paEmpInfoGrid" onmouseout="change('paEmpInfoGrid');"
					style="border: dashed 1px #cccccc;position: absolute;"></div>
</form>
</div>
<form id="updateGrid" method="post" action="/hrm/empinfo/updatePaEmpInfoGrid?updateBy=${basicInfo.EMPID}"></form>
</body>
</html>
