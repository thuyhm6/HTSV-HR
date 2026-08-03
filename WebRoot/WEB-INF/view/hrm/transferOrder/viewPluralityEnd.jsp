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
        $("#PluralityType").ligerComboBox({width:"100",selectBoxWidth:"100",
        	onSelected:function(val,text){
        		var type='';
        		if(val == 'TransCode_150_02'){
            		type='End';	
        		}
        		location.href = 'viewPlurality'+type;
        	}
    	});        
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
			        +"<input type='hidden' name='PLUNO_"+rowindex+"' value='"+rowdata.PLUNO+"'>";
			        return html;
			    }
			    
			},
    		{ display: '行号', name: 'EMPID',width:70 },
    		{ display: '姓名', name: 'CHINESENAME',width:70 },
    		{ display: '部门', name: 'DEPTFULLNAME',width:100 },
    		{ display: '职位', name: 'POSITION',width:70},
    		{ display: '职级', name: 'POSTGRADE',width:70 },
    		{ display: '兼职信息', name: 'DISINFO',width:70,isAllowHide: false, isSort: false,
			    render: function (rowdata,rowindex){                        
			        var html = "<font style='cursor:hand;' onclick=showInfo('"+rowindex+"')>点击查看</font>";
			        return html;
			    } 
		    },
    		{ display: '兼职解除日期', name: 'ENDDATE',width:110 },
    		{ display: '兼职解除事由', name: 'CONTENTS',width:270 }
            ],
            enabledEdit: true,
            dataAction: 'server', 
            sortName: 'EMPID',
            pageSize: 10,
            url: '/hrm/transferOrder/getTransferOrderList?pluralityend=1',
            width: '100%', 
            height: '400'
        });
    }

    function search(){
		$("#l-search").hide();
		$.ligerWindow.show( { url:'/hrm/transferOrder/searchEmp?type=pluralityend',
			name:'upgrade',width:800,height:500,left:90,top:10,showMax:false });
		     
	}

    function getSelected(obj,rowindex){				
		var rowobj=$("TR[class^='l-grid-row'][rowindex='"+rowindex+"']");
		var rowdata=$grid.getRowByRowIndex(rowindex);

		if(obj.checked){
			
			$(rowobj).children("TD[columnname='ENDDATE']").children("div").html("<input id='ENDDATE_"+rowindex
					+"' name='ENDDATE_"+rowindex+"' type='text' value=''>");
			
			$(rowobj).children("TD[columnname='CONTENTS']").children("div").html("<input id='CONTENTS_"+rowindex
					+"' name='CONTENTS_"+rowindex+"' type='text' value=''>");
			
			$("#ENDDATE_"+rowindex).attr({validate:"true"});
			$("#ENDDATE_"+rowindex).ligerDateEditor({width:"100"});

			$("#CONTENTS_"+rowindex).ligerTextBox({width:"260"});
	
		}else{
			$(rowobj).children("TD[columnname='ENDDATE']").children("div").html('');
			$(rowobj).children("TD[columnname='CONTENTS']").children("div").html('');
		}	 
    }
    
    function showInfo(rowindex){
    	var back=$grid.getRowByRowIndex(rowindex); 
       	var temp="<table border='2' bordercolor='#CCCCCC' width='90%'>"
           +"<tr><td width='30%'>行号</td><td>"+back.EMPID+"</td></tr>"
           +"<tr><td>姓名</td><td>"+back.CHINESENAME+"</td></tr>"
           +"<tr><td>兼职部门</td><td>"+back.PLUDEPT+"</td></tr>"
           +"<tr><td>兼职职位</td><td>"+back.PLUPOSITION+"</td></tr>"
           +"<tr><td>兼职事由</td><td>"+back.REASON+"</td></tr>"
       	+"</table>";
   		$.ligerMessageBox.alert(back.EMPID,temp);
   		$(".l-messagebox").css({ left: '30%'});
		
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
            url:'/hrm/transferOrder/saveTransferOrder?type=pluralityend',
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
		  		<div>
			  		<table><tr>
			  		<td>发令类型：&nbsp;&nbsp;<td>
					<td><ait:selectSyCode parentNo="TransCode_150" name="PluralityType"
						selected="TransCode_150_02"/></td>
			  		</tr></table>
		  		</div>
		  	</div>
		
			<form id="applyForm">
			   	<div position="center" id="transfer"></div>
			   	<input type="hidden" name="TRANSCODE" value="TransCode_150_02">
		   	</form>       
      	</div>

</body>
</html>
