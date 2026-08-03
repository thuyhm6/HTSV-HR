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
		var index="";
		$(function(){
			index = parent.$("#tempIndex").val();
			var data = parent.$grid.getRowByRowIndex(index);
			var code = parent.$("#BUSINESSALL_"+index).val().split(",");
		
			$.ajax({			
				type:'get',
				cache:false,
				contentType:'application/json',			
				url:'/hrm/getBusiness?post='+data.POSTCODE,
				dataType:'json',	        
				success:function(data){	

					var temp = "<tr>";
					$.each(data, function(i, item){		
						if(i%2==0){ 
							temp+="</tr><tr>";
						}
						temp+="<td><input type='checkbox' tn='"+item.BIZNAME
						+"' value='"+item.BIZID+"'>"+item.BIZNAME+"<td>";	
					}); 

					$("#tree").html(temp+"</tr>");

					$.each(code,function(i,id){
						$(":checkbox[value='"+id+"']").attr("checked","true");
					});
					
				} 
			});
		});
		function send(){
			var flag=0;		
			$(":checkbox").each(function(){
				if(this.checked){				
					flag=1;
					return false;			
				}
		    });

			if(flag == 0){
				$.ligerMessageBox.alert(' ','尚未选择任何信息','error');
				$(".l-messagebox").css({ left: '15%'});
				return;
			}else{	
				var codetemp=$(":checkbox").map(function(){
						if(this.checked){
						  return this.value;
						}
					}).get().join(",");
				var nametemp=$(":checkbox").map(function(){
					if(this.checked){
					  return this.tn;
					}
				}).get().join(","); 

				parent.$("#BUSINESSALL_"+index).val(codetemp);
				parent.$("#business_"+index).text(nametemp);
				parent.$(".l-dialog-close").click(); 
			}
		}
	</SCRIPT>
			
	
	<style type="text/css">           
       	.l-button{width:60px; float:right;margin-right:15px;margin-top: 5px;}       	
	</style>
	</head>	
	<body>	
		<input type="button" value='确认' 
			onclick="send();"
			class="l-button"/>
			
		<div style="width:370px; height:260px; margin:10px; float:left; border:1px solid #ccc; overflow:auto;padding: 10px;">
		    <table id="tree" width="100%">
		    </table>
		    <input id="deptid" type="hidden" value=''/>
		</div> 
	</body>
</html>
