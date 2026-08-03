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
    <script src="/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	
    <script type="text/javascript">
    var $grid ;
    // 初始调用
    $(function ()
    {
		$("#mainBody").hide();
        f_initGrid(); 
		setTimeout('$("#loading").hide()',250);
		setTimeout('$("#mainBody").show()',250);
    });
	
    function f_initGrid()
    {
    	$grid = $("#transfer").ligerGrid({
    		columns: [
			{ display: '编辑', width: 30, isAllowHide: false, name: 'checkbox', isSort: false,
			    render: function (rowdata,rowindex){                        
			        var html = "<input type ='checkbox' name='checkTag' value='"+rowindex+"' onclick=\"getSelected(this,"+rowindex+")\">"
			        +"<input type='hidden' name='EMPID_"+rowindex+"' value='"+rowdata.EMPID+"'>";
			        return html;
			    }
			    
			},
    		{ display: '行号', name: 'EMPID',width:70 },
    		{ display: '姓名', name: 'CHINESENAME',width:70 },
    		{ display: '部门', name: 'DEPTFULLNAME',width:100 },
    		{ display: '职位', name: 'POSITION',width:70},
    		{ display: '职级', name: 'POSTGRADE',width:70 },
    		{ display: '奖励日期', name: 'STARTDATE',width:110 },
    		{ display: '奖励类型', name: 'REWARDTYPE',width:160 },
    		{ display: '授奖人', name: 'REWARDBY',width:150 },
    		{ display: '功绩内容', name: 'REWARDCONTENT',width:180 }
            ],
            enabledEdit: true,
            dataAction: 'server', 
            sortName: 'EMPID',
            pageSize: 10,
            url: '/hrm/transferOrder/getTransferOrderList?rewawrd=1',
            width: '100%', 
            height: '400'
        });
    }

    function search(){
		$("#l-search").hide();
		$.ligerWindow.show( { url:'/hrm/transferOrder/searchEmp?type=reward',
			name:'upgrade',width:800,height:500,left:90,top:10,showMax:false });
		     
	}
	
    function getSelected(obj,rowindex){				
		var rowobj=$("TR[class^='l-grid-row'][rowindex='"+rowindex+"']");
		var rowdata=$grid.getRowByRowIndex(rowindex);

		if(obj.checked){

			$(rowobj).children("TD[columnname='STARTDATE']").children("div").html("<input id='STARTDATE_"+rowindex
					+"' name='STARTDATE_"+rowindex+"' type='text' value=''>");
			
			$(rowobj).children("TD[columnname='REWARDTYPE']").children("div").html("<div><ait:selectSyCode parentCode='RewardTypeCode' name='REWARDTYPE_"
					+rowindex+"' limit='all'/></div>");
			
			$(rowobj).children("TD[columnname='REWARDBY']").children("div").html("<input id='REWARDBY_"+rowindex
					+"' name='REWARDBY_"+rowindex+"' type='text' value=''>");

			$(rowobj).children("TD[columnname='REWARDCONTENT']").children("div").html("<input id='REWARDCONTENT_"+rowindex
					+"' name='REWARDCONTENT_"+rowindex+"' type='text' value=''>");
			
			$("#STARTDATE_"+rowindex).attr({validate:"true"});
			$("#STARTDATE_"+rowindex).ligerDateEditor({width:"100"});
			
			$("#REWARDTYPE_"+rowindex).attr({validate:"true"});
			$("#REWARDTYPE_"+rowindex).ligerComboBox({width:"150",selectBoxWidth:"150"});

			$("#REWARDBY_"+rowindex).ligerTextBox({width:"140"});

			$("#REWARDCONTENT_"+rowindex).ligerTextBox({width:"170"});
	
		}else{
			$(rowobj).children("TD[columnname='STARTDATE']").children("div").html('');
			$(rowobj).children("TD[columnname='REWARDTYPE']").children("div").html('');
			$(rowobj).children("TD[columnname='REWARDBY']").children("div").html('');
			$(rowobj).children("TD[columnname='REWARDCONTENT']").children("div").html('');
		}	 
    }

	function save(){
		var flag = 2;

		$("input[name='checkTag']").each(function (){
			if(this.checked){				
				flag=1;
				return false;
			}
	    });

	 	if(flag==2){
	 		$.ligerMessageBox.alert(' ','请先选择人员','error');
			$(".l-messagebox").css({ left: '30%'});
			return;
	 	}
		
		$("*[validate='true']").each(function (){
			if($(this).val()==''){
				$(this).parent().parent().attr("style","border:red 1px solid");
				flag=0;
			}else{
				$(this).parent().parent().attr("style","border:none");
			}
	    });
		if(flag == 0){
			$.ligerMessageBox.alert(' ','必填项','error');
			$(".l-messagebox").css({ left: '30%'});
		}else{
        	$.ligerMessageBox.show({type:'warn',content: '正在保存中...'});
    		$(".l-messagebox-close").remove();
    		$(".l-messagebox").css({ left: '30%'});

            var options = {	                        
            url:'/hrm/transferOrder/saveTransferOrder?type=reward',
            type:'POST',	                        
            success: function(){
            	$.ligerMessageBox.alert('', '成功', 'success', function(){
            		$(".l-messagebox").remove();
            		$grid.setOptions({newPage: 1}); 
            		$grid.loadData(true);
                });
            	$(".l-messagebox-close").remove();
            	$(".l-messagebox").css({ left: '30%'});		
               }                                           
            }; 
           $('#applyForm').ajaxSubmit(options); 
           return false;
        }	
	}
	</script>
	
	<style type="text/css"> 
       .l-page-top{ height:50px; width:100%;}        
       .l-table-edit-td{ padding:4px;}
       .l-button{width:60px; float:right; margin-right:10px;}
    </style>
</head>
<body style="padding:5px" >
		<div id="loading" style="width:90%;text-align:center;padding-top: 200px;position: absolute;">
			<img src="/resources/images/loading.gif">
		</div>
		<div id="mainBody">
			<div class="l-page-top">
			 	<input type="button" id="l-save" class="l-button" value="保存" onclick="save();">
		     	<input type="button" id="l-search" class="l-button" value="搜索" onclick="search();">	     
		  	</div>
			<form id="applyForm">
			   	<div position="center" id="transfer"></div>
		   	</form>       
      	</div>
</body>
</html>
