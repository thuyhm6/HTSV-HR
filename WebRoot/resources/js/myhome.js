$.fn.myhome=function(page){	
	$("#mainBody").hide();

	$.ajax({
    	type:'get',
    	cache:false,
    	contentType:'application/json',
    	dataType:'json',           			            	
    	url:'/myhome/getHomePurview?page='+page,
    	success:function(data){
			$("#infoForm").html(data.show);
			$("#appinfo").html(data.select);

			$.ajax({
            	type:'get',
            	cache:false,
            	contentType:'application/json',
            	dataType:'json',           			            	
            	url:'/myhome/getModel?page='+page,
            	success:function(data){	            	
					$.each(data.modelList, function(i, item){
						$("#"+item.MID).attr("style",item.MCONTENT);
						$("#"+item.MID+"Model").attr("value",item.MCONTENT);
					});
					
					if($("#backgroundModel").val()!=""){
						changebg($("#backgroundModel").val());
					}
				}            	           	
           	});
			
			$("#loading").hide();
			$("#mainBody").show();
		}            	           	
   	});
	
	(parent.$("#layout1").ligerGetLayoutManager()).setLeftCollapse(false);
	
	
	$("input[value^='Page']").css({"background":"#787E60","color":"#FFFFFF"});
	$("input[value='Page"+page+"']").css({"background":"#77AA55","color":"#FFE477"});
	

}

function edit(page){
	
	$("#edit").remove();
	$("#update").show();	
	(parent.$("#layout1").ligerGetLayoutManager()).setLeftCollapse(true);
	$("div[model]").ligerDrag();
	$("div[model]").ligerResizable();
	$("#righter").show();
	getSyMenu(page);
	$("#righter").ligerAccordion({ height: 400});	
	$("#footer").hide();
	
}
							
function update(page){
	var options = {	                        
             url:'/myhome/updateModel?page='+page,
             type:'POST',
             success: function(){ location.href = location.href }                                 
         };
    
    $('#infoForm').ajaxSubmit(options);
    return false;
}

function change(object){
	$('#'+object+'Model').val($('#'+object).attr('style'));
}

function check(object){
	var temp = object.value.split("Model")[0];
	if(object.checked){
		$("#"+temp).show();
	}else{
		$("#"+temp).hide();
	}

	$('#'+object.value).val($('#'+temp).attr('style'));
}

function changePage(a,msg,title){
	location.href ="/login/main?page="+a;
}
function addApp(c,title,msg){
	var temp= parseInt(c)+parseInt(1);
	if(temp>3){
		$.ligerDialog.warn(msg,title);
		return;
	}else{
		$.post("/myhome/updateapp?appcnt="+temp,{},function(){
			$("input[value='Page"+temp+"']").remove();
			$("#app").append("<input type=\"button\" calss=\"appbtn\" value='Page"+temp+"' onclick=\"changePage('"+temp+"');\"/>");
			location.href ="/login/main?page="+temp;
		});
	}
}
function delApp(b,c){
	var temp= parseInt(c)-parseInt(1);
	$.post("/myhome/updateapp?appcnt="+temp+"&page="+b,{},function(){				
		location.href ="/login/main?page="+temp;
	});
}

var treemanager="";
var datatree=[];
function getSyMenu(page){

	$.ajax({
    	type:'get',
    	cache:false,
    	contentType:'application/json',
    	dataType:'json',           			            	
    	url:'/myhome/getSyMenu?page='+page,
    	success:function(data){
    		$.each(data, function(i, item){
        		if(item.ISCHECKED=='true'){
        			if(item.MENU_PARENT_CODE.indexOf("0000")>0){
        				datatree.push({ id: item.MENU_CODE, pid: item.MENU_PARENT_CODE, text: item.MENU_INTRO,url:item.MENU_URL,isexpand:false, ischecked: true });
        			}else{
        				datatree.push({ id: item.MENU_CODE, pid: item.MENU_PARENT_CODE, text: item.MENU_INTRO,url:item.MENU_URL, ischecked: true });
        			}
        		}else{
        			if(item.MENU_PARENT_CODE.indexOf("0000")>0){
        				datatree.push({ id: item.MENU_CODE, pid: item.MENU_PARENT_CODE, text: item.MENU_INTRO,url:item.MENU_URL,isexpand:false });
        			}else{
        				datatree.push({ id: item.MENU_CODE, pid: item.MENU_PARENT_CODE, text: item.MENU_INTRO,url:item.MENU_URL });
        			}
            	}		
            });            		            	
			treemanager = $("#menutree").ligerTree({  
           	 	data:datatree, 
            	idFieldName :'id',
            	parentIDFieldName :'pid',
            	textFieldName:'text',
            	onCheck:getChecked
            });
            
		}            	           	
   	});

}

function getChecked(){
	var notes = treemanager.getChecked();
    var text = "";
    $("a[onclick^='goTab']").remove();
    $("#shortcutinfo").html("<p style='height:4px;'></p>");
    for (var i = 0; i < notes.length; i++){               
        if(notes[i].data.url != null){
       		$("#shortcutinfo").append("<p style='height:25px;'>"+notes[i].data.text+"</p>");
        	text += notes[i].data.id + ",";
        }
    }
    $("#short").attr("value",text);
    
}	

function goTab(code,name,url){
	parent.f_addTab(code, name, url);
}
function changebg(a){	
	$("#backgroundModel").attr("value",a);
	$("#right").css("background","url('/resources/images/homebg/"+a+".gif') repeat;"); 
}