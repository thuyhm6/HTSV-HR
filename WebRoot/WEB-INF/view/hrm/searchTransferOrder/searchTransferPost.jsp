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
    	name : '#transferPost',
    	url : '/hrm/searchTransferOrder/rollbackUpgrade',
    };
    // 初始调用
    $(function ()
    {
		$("#mainBody").hide();
		$("#ENDp_CONTRACT_DATE").ligerDateEditor();
		$("#ENDf_CONTRACT_DATE").ligerDateEditor();
        f_initGrid(); 
		setTimeout('$("#loading").hide()',250);
		setTimeout('$("#mainBody").show()',250);
    });
	
	function showEmpHistory(empid,transCode) {          
 		 $.ligerDialog.open({ url: '/hrm/searchTransferOrder/searchEmpHistory?EMPID='+empid+'&TRANSCODE='+transCode,left : 20,top : 0, width : 700 , height: 400, isResize: true });
} 
    function f_initGrid()
    {$grid = $("#transferPost").ligerGrid({
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
    		{ display: '生效日期', name: 'STARTDATE', type:'checkbox' },
    		{ display: '行号', name: 'EMPID', type:'checkbox' },
    		{ display: '姓名', name: 'CHINESENAME', type:'checkbox' },
    		{ display: '部门', name: 'DEPARTMENT', type:'checkbox' },
    		{ display: '职群', name: 'POSTGROUP', type:'checkbox' },
    		{ display: '职务', name: 'POSITION', type:'checkbox' },
    		{ display: '职级', name: 'POSTGRADENAME', type:'checkbox' },
    		{ display: '职位', name: 'POSITION', type:'checkbox' },
    		{ display: '转职说明', name: 'PROMOTIONDATE', type:'checkbox' },
    		{ display: '历史记录 ', name: 'UPGRADE', type:'checkbox',render: function(item,index)
				{
				  var html="<font color=\"red\"><a onclick=\"showEmpHistory('"+item.EMPID+"','"+item.TRANSCODE+"');\"  style=\"cursor:hand\" >历史记录</a></font>"             	
				  return html;
				} 
			},
    		{ display: '是否生效', name: 'UPGRADE', type:'checkbox',render: function(item,index)
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
            url: '/hrm/searchTransferOrder/getTransferPostList',
            width: '99%', height: '410',
            heightDiff : 0
        });
        
	$grids=$.ligerExpandGrid.CRUD.initSubmit('#transferPost');
    }

	</script>

</head>
<body style="padding:0px" >
		<div id="loading" style="width:90%;text-align:center;padding-top: 200px;position: absolute;">
			<img src="/resources/images/loading.gif">
		</div>
		<div id="mainBody">
		<%@include file="searchTransferCondition.jsp"%>
      <div position="center" id='transferPost'></div>
            
            </div>
</body>
</html>
