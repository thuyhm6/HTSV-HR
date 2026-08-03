<%@ page contentType="text/html; charset=UTF-8" %>
    <script type="text/javascript">
   	$(function ()
    {
		$("#ENDp_CONTRACT_DATE").ligerDateEditor();
		$("#ENDf_CONTRACT_DATE").ligerDateEditor();
		$("#CREATEDATE").ligerDateEditor();
		$("#ACTIVITY").ligerComboBox(); 
		if($("#TRANSCODE").ligerComboBox()==null)
		$("#TransferTypeHeardName").html("");
		try{
		$("#"+historyParms.tagName).ligerComboBox();
		}catch(e){
		$("#TRANSCODE").ligerComboBox();
		}
    });
    function f_search()
    {
     	try{
     		transcode=$("#"+historyParms.tagName).attr('value');
     		tagName=historyParms.tagName;
     		if(typeof(tagName)=='undefined'||typeof(transcode)=='undefined')
     			throw new Error("transcode!");
     	}
     	catch(e)
     	{	tagName='TRANSCODE';
     		if(typeof($("#TRANSCODE").val())=='undefined')transcode="";
     		else
     		transcode=$("#TRANSCODE").attr('value');
     	}
    	$grid.setOptions({ parms: [	
    	                        	{ name: 'CONDITION', value: $("#EMPIDORCHINESENAME").attr('value')},
    	                        	{ name: 'DEPTID', value: $("#DEPTID").attr('value')},
    	                        	{ name: 'PERIOD_START', value: $("#ENDp_CONTRACT_DATE").attr('value')},
    	                        	{ name: 'PERIOD_END', value: $("#ENDf_CONTRACT_DATE").attr('value')},
    	                        	{ name: tagName , value : transcode},
    	                        	{ name: 'ACTIVITY', value : $("#ACTIVITY").attr('value')},
    	                        	{ name: 'CREATEDATE', value : $("#CREATEDATE").attr('value')}
    	                           ],
    	                   newPage: 1
    	                 }); //设置数据参数
        $grid.loadData(true); //加载数据
    }
    	function rallBackTransfer()
	{
		var manager=$(historyParms.name).ligerGetGridManager();
		var lineTemp=new Array();
		$grids.delRow();
		$.each($grids.delIndex[historyParms.name],function(i,val){
			lineTemp.push(manager.getRowByRowIndex(parseInt(val)));
	   	 });
	   	jsonData=JSON2.stringify(lineTemp);
	   	if(jsonData=="[]"||jsonData=="[null]")return;
	   	$grids.delIndex[historyParms.name]=new Array();
	   	$.ligerDialog.waitting("删除中...");
		$.post(historyParms.url, 
		       [	
		           { name: 'jsonData', value: jsonData }
		       ]
		        , function (result)
		        {
		            if (result == "Y")
		            {
		                $.ligerDialog.closeWaitting();
		                $.ligerDialog.success('撤销成功!', function ()
		                {
		                	$grid.loadData(true); //加载数据
		                });
		            }
		            else
		            {
		                $.ligerMessageBox.error('提示', result);
		            }
		        });
	}
	</script>
	<style type="text/css"> 
        #layout1{ padding:5px; margin:0; padding-bottom:15px; width:99%;margin:0; padding:0;  }  
        .l-page-top{ height:80px; background:#f8f8f8; margin-bottom:3px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;
        h4{ margin:20px;}
    </style>

	<div class="l-page-top" onkeydown="if(event.keyCode==13) f_search();">
	       <table cellpadding="2" cellspacing="2" class="l-table-edit" >
	           <tr>
	               <td align="right" class="l-table-edit-td"><spring:message code="hrm.dept"/>:</td>
	               <td align="left" class="l-table-edit-td"><ait:deptTree name="DEPTID" limit="hr"/></td>
	               <td align="right" class="l-table-edit-td"><spring:message code="hrm.empid"/>|<spring:message code="hrm.name"/>:</td>
	               <td align="left" class="l-table-edit-td"><input name="EMPIDORCHINESENAME" type="text" id="EMPIDORCHINESENAME" /></td>
	               <td align="right" class="l-table-edit-td" id="TransferTypeHeardName">类型:</td>
	               <td align="left" class="l-table-edit-td" id="TransferTypeCode">
	               
	         
	               <td align="right">
	               		<a class="l-button"  style="width:60px; float:right; margin-left:10px;" onclick=" f_search()">搜索</a>
	               </td>
	                <td align="right">
	               		<a class="l-button"  style="width:90px; float:right; margin-left:10px;" onclick="rallBackTransfer();">取消发令</a>
	               </td>
	           </tr>
	             <tr>
	               <td align="left" class="l-table-edit-td">发令日期:</td>
	               <td align="left" class="l-table-edit-td"  ><input name="CREATEDATE" type="text" id="CREATEDATE" readonly/></td>
	               <td align="left" class="l-table-edit-td">当前状态:</td>
	               <td align="left" class="l-table-edit-td"  ><select id="ACTIVITY" name="ACTIVITY"><option value ="" selected>全部</option><option value ="1">已生效</option><option value ="0">未生效</option></select></td>
	               <td align="left" class="l-table-edit-td"><spring:message code="hrm.endDate"/>:</td>
	               <td align="left" class="l-table-edit-td"  ><input name="ENDp_CONTRACT_DATE" type="text" id="ENDp_CONTRACT_DATE" readonly/></td>    
	           	   <td align="left" class="l-table-edit-td" style="width:60px" ><input name="ENDf_CONTRACT_DATE" type="text" id="ENDf_CONTRACT_DATE"  readonly/></td>    
	           </tr>
	       </table>
	  </div>