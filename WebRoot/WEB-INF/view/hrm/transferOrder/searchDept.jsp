<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<html>
 <head> 
 	<title></title>
 	
 	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
 	
	<link href="/resources/js/ligerUI/skins/Silvery/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>
	<script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script> 
	<SCRIPT type='text/javascript'> 
		$(function(){ 		
			var depttree = [];		
			$.ajax({			
				type:'get',
				cache:false,
				contentType:'application/json',			
				url:'/hrm/getDeptTree/hr',
				dataType:'json',	        
				success:function(data){	
					$.each(data, function(i, item){		
						depttree.push({ id: item.DEPTID, pid: item.PARENTDEPTID, 
							text: item.DEPTNAME ,isexpand : item.ISEXPAND});	
						});
					
					$("#tree").ligerTree({  
			            data:depttree, 
			            idFieldName :'id',
			            parentIDFieldName :'pid',
			            checkbox:false,
			            onSelect: function(note){
								$("#deptname").val(note.data.text);
								$("#deptid").val(note.data.id);
							}			            
			            }); 
					} 
			});
		});
		function send(){
			parent.$("#"+parent.$("#tempDept").val()).val($("#deptid").val());
			parent.$(".l-dialog-close").click(); 
		}
	</SCRIPT>
			
	
	<style type="text/css">           
       	.l-button{width:60px; float:right;margin-right:15px;margin-top: 5px;}
       	.l-text2{width:200px; float:left;margin-left:15px;margin-top: 5px;}        	
	</style>
	</head>	
	<body>	
		<input id="deptname" type="text" value='' readonly="readonly" class="l-text2"/>
		<input type="button" value='确认' 
			onclick="send();"
			class="l-button"/>
			
		<div style="width:400px; height:300px; margin:10px; float:left; border:1px solid #ccc; overflow:auto;  ">
		    <ul id="tree"></ul>
		    <input id="deptid" type="hidden" value=''/>
		</div> 
	</body>
</html>
