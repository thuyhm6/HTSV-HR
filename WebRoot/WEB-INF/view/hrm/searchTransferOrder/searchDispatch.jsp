<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <html>
 <title></title>
 <head>   
   <meta http-equiv="X-UA-Compatible" content="IE=edge" >

    
    <!-- CSS -->
    <link href="/resources/js/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
     <!-- JS -->
    <script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>  
    <script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script>  
    <script src="/resources/js/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script> 
    <script src="/resources/js/common.js" type="text/javascript"></script> 
    <script src="/resources/js/json2.js" type="text/javascript"></script>
	
    <script type="text/javascript">
    var $grid ;
     var $grids={};
     var historyParms={
    	name : '#dispatch',
    	url : '/hrm/searchTransferOrder/rollbackUpgrade?rollbackTyep=Dispatch',
    };
    // 初始调用
    $(function ()
    {
    	$("#TransferTypeCode").html("<ait:selectSyCode parentCode='TransDispatchType' name='TRANSCODE' limit='all'/>");
		$("#mainBody").hide();
		$("#ENDp_CONTRACT_DATE").ligerDateEditor();
		$("#ENDf_CONTRACT_DATE").ligerDateEditor();
        f_initGrid(); 
		setTimeout('$("#loading").hide()',250);
		setTimeout('$("#mainBody").show()',250);
    });
	
    function f_initGrid()
    {$grid = $("#dispatch").ligerGrid({
    		columns: [
    		{ display: '请选择', name: 'EMPID', minWidth: 65, 
                	render: function (item)
	                    {
	                     var toDate=new Date();
	                     var createDate=new Date(item.CREATEDATE.replace("-","/"));
	                     var passDates=(toDate-createDate)/86400000;
	                     if(item.ACTIVITY==1&&passDates>15)
	                     	return "";
	                     else
	                        return "<input type='checkbox'/>"
	                    } 
            },
    		{ display: '开始日期', name: 'STARTDATE', type:'checkbox' },
    		{ display: '预计结束日期', name: 'PROPOSEENDDATE', type:'checkbox' },
    		{ display: '结束日期', name: 'ENDDATE', type:'checkbox' },
    		{ display: '行号', name: 'EMPID', type:'checkbox' },
    		{ display: '姓名', name: 'CHINESENAME', type:'checkbox' },
    		{ display: '部门', name: 'DEPARTMENT', type:'checkbox' },
    		{ display: '职位', name: 'POSITION', type:'checkbox' },
    		{ display: '职级', name: 'POSTGRADE', type:'checkbox' },
    		{ display: '员工状态', name: 'STATUSNAME', type:'checkbox' },
    		{ display: '派遣类型', name: 'TRANSCODENAME1', type:'checkbox' },
    		{ display: '派遣区分', name: 'DISDIFFENTNAME', type:'checkbox' },
    		{ display: '派遣处', name: 'CONTENTS', type:'checkbox' },
    		{ display: '派遣目的', name: 'DISTARGET', type:'checkbox' },
    		{ display: '备注', name: 'UPGRADE', type:'checkbox' },
    		{ display: '是否生效', name: 'UPGRADE', type:'checkbox', render: function(item,index)
				{
				  var html="";
				  if(item.ACTIVITY==1)
				  html="<img src=\"${pageContext.request.contextPath}/resources/images/a_1.gif\" width=\"22\" height=\"25\">" ;            	
				  if(item.ACTIVITY==0)
				   html="<img src=\"${pageContext.request.contextPath}/resources/images/a_0.gif\" width=\"22\" height=\"25\">";             	
				  return html;
				} 
			}
            ],
            enabledEdit: true,
            dataAction: 'server', 
            sortName: 'EMPID',
            pageSize: 10,
            url: '/hrm/searchTransferOrder/getDispatchList',
            width: '99%', height: '410',
            heightDiff : 0
        });
        $grids=$.ligerExpandGrid.CRUD.initSubmit('#dispatch');
    }
	
  
	</script>
	
	
</head>
<body style="padding:0px" >
		<div id="loading" style="width:90%;text-align:center;padding-top: 200px;position: absolute;">
			<img src="/resources/images/loading.gif">
		</div>
		<div id="mainBody">
		<%@include file="searchTransferCondition.jsp"%>
      <div position="center" id='dispatch'></div>
            
            </div>
</body>
</html>
