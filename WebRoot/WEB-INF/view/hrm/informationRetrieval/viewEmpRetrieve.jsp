<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

 <script type="text/javascript">
	String.prototype.replaceAll = function(s1,s2) {
    	return this.replace(new RegExp(s1,"gm"),s2);
	}
  	function up(){
	   	if($("#displayItem").get(0).selectedIndex > 0){
	    	var index = $("#displayItem").get(0).selectedIndex;  
	     	var pretext = $("#displayItem").get(0).options[index-1].text;  
	     	var preval = $("#displayItem").get(0).options[index-1].value;  
	     
	     	$("#displayItem").get(0).options[index-1].text=$("#displayItem").get(0).options[index].text;
	     	$("#displayItem").get(0).options[index-1].value=$("#displayItem").get(0).options[index].value;
	     	$("#displayItem").get(0).options[index].text=pretext;
	     	$("#displayItem").get(0).options[index].value=preval;
	    }
	}
	function down(){
	   	var length=$("#displayItem option").length-1;
	   	if($("#displayItem").get(0).selectedIndex != length ){
	       var index = $("#displayItem").get(0).selectedIndex; 
	       var aftertext = $("#displayItem").get(0).options[index+1].text;  
	       var afterval = $("#displayItem").get(0).options[index+1].value;  
	       $("#displayItem").get(0).options[index+1].text=$("#displayItem").get(0).options[index].text;
		   $("#displayItem").get(0).options[index+1].value=$("#displayItem").get(0).options[index].value;
		   $("#displayItem").get(0).options[index].text=aftertext;
		   $("#displayItem").get(0).options[index].value=afterval;
	    }
	}
	function add(checkbox){
    	var $form=$("#hr0404");
     	var title=checkbox.title;
     	var lang=checkbox.lang;
     	var content=$(checkbox).val();
     	var alt=checkbox.alt;
     	if(checkbox.checked){
 	       	$form.find("#displayItem").append("<option value='"+content+"' title='"+alt+"' lang='"+lang+"' >"+title+"</option>");  //添加
	    }else{
 	    	$("#displayItem option[value='"+content+"']").remove();  //删除
 	    }
 	}
	function del(){
   		var $form=$("#hr0404");
   		var sid = $("#displayItem").val();
  		$form.find(":checkbox[value='" + sid + "']").attr("checked",false);
     	$("#displayItem").find("option:selected").remove();
	}
	function addSpan(obj,sid1,sid2,sid3){
     	var $form=$("#hr0404");
     	// sid 1 = 单个控件or开始时间
     	// sid 2 = 结束时间
     	// sid 3 = 有开始和结束时间的 id列名
      	var sid1Value="";
     	if(obj.alt=="SEL"){
        	try{
            	sid1Value = getCheckedTreeKey("tree_"+sid1);
           	}catch(e){
            	sid1Value="";
            }
       		$form.find("#div_"+sid1).hide();
       	}else{
          	sid1Value= document.getElementById(sid1).value;
        }
   		if(obj.checked && (sid1Value!="")){
      		//选中
	  		var rowNumInit=document.getElementById('addTable_hr0404').rows.length;
	  		var nTr = document.getElementById('addTable_hr0404').insertRow(rowNumInit);
	   		//alert("rowNumInit="+rowNumInit);
	  		var cell0=nTr.insertCell(0);
	  		
			if(rowNumInit>1){
				//逻辑关系 插入
				var select1=document.createElement("select");
				select1.setAttribute("id","logic_"+rowNumInit);
				var option1=document.createElement("option");
				var txtNode1=document.createTextNode('<spring:message code="hr.viewCondSql.title.YU"/>');  // 与
				option1.appendChild(txtNode1);
				option1.setAttribute("selected","selected");
				option1.setAttribute("value","AND");
				//var option2=document.createElement("option");
				//var txtNode2=document.createTextNode('<spring:message code="hr.viewCondSql.title.HUO"/>');   // 或
				//option2.appendChild(txtNode2);
				//option2.setAttribute("value","OR");		
				select1.appendChild(option1);
				//select1.appendChild(option2);
				cell0.appendChild(select1);
			}else{
				var txtNode=document.createTextNode("");				
		        cell0.appendChild(txtNode);
			}
		  	var cell1=nTr.insertCell(1);  
		    //项目名称  列名
		    var txtNodeName=document.createTextNode(obj.title);
		    
		    cell1.appendChild(txtNodeName);  
		  	/*var Input = document.createElement("input"); 
		  	Input.setAttribute("id", sid3+"_"+rowNumInit) ;
		    Input.setAttribute("name", sid3) ;
		    Input.setAttribute("type", "hidden") ;
		    Input.setAttribute("value", sid3) ;
		    cell1.appendChild(Input);*/      
		    var Input = document.createElement("input"); 
		    Input.setAttribute("id", "name"+"_"+rowNumInit) ;
		    Input.setAttribute("name", "name"+"_"+rowNumInit) ;
		    Input.setAttribute("type", "hidden") ;  //hidden
	        if(obj.alt=="DATE"){
				Input.setAttribute("value", sid3) ;  
	        }else if(obj.alt== ("YEAR")){
			    Input.setAttribute("value", sid3) ; 
			}else{
			    Input.setAttribute("value", sid1) ; 
	        }
       		cell1.appendChild(Input);
	      
		  	var cell2=nTr.insertCell(2);
		    //关系 插入
		    var select2=document.createElement("select");
			select2.setAttribute("id","relation_"+rowNumInit);
			select2.setAttribute("name","relation_"+rowNumInit);
			        
		    if(obj.alt=="DATE"){   //日期类型的条件所插入的运算关系
				if(document.getElementById(sid2).value==""){
			    	var option3=document.createElement("option");
					var txtNode3=document.createTextNode('<spring:message code="hr.viewCondSql.title.DENGYU"/>');//等于
					option3.appendChild(txtNode3);
					option3.setAttribute("selected","selected");
					option3.setAttribute("value"," = ");
				  	var option5=document.createElement("option");
				  	var txtNode5=document.createTextNode('<spring:message code="hr.viewCondSql.title.DAYU"/>');   //大于
					option5.appendChild(txtNode5);
					option5.setAttribute("value"," > ");	
				  	var option6=document.createElement("option");
				  	var txtNode6=document.createTextNode('<spring:message code="hr.viewCondSql.title.XIAOYU"/>');  //小于
					option6.appendChild(txtNode6);
					option6.setAttribute("value"," < ");	
				 	select2.appendChild(option3);
				 	select2.appendChild(option5);
				 	select2.appendChild(option6);
				}else{  
			    	var option10=document.createElement("option");
			        var txtNode10=document.createTextNode('<spring:message code="hr.viewCondSql.title.ZAICIQUJIANZHONG"/>');   //在此区间中
			        option10.appendChild(txtNode10);
					option10.setAttribute("selected","selected");
					option10.setAttribute("value"," BETWEEN ");	
					//var option11=document.createElement("option");		
					//var txtNode11=document.createTextNode('不在在此区间中');
					//option11.appendChild(txtNode11);
					//option11.setAttribute("value"," NOT BETWEEN ");	
					select2.appendChild(option10);
					//select2.appendChild(option11);
				}
		    }else if(obj.alt=="MONTH"){   //日期类型的条件所插入的运算关系
		    	var option3=document.createElement("option");
				var txtNode3=document.createTextNode('<spring:message code="hr.viewCondSql.title.DENGYU"/>');   // 等于
				option3.appendChild(txtNode3);
				option3.setAttribute("selected","selected");
				option3.setAttribute("value"," = ");
				//var option4=document.createElement("option");
				//var txtNode4=document.createTextNode('<spring:message code="hr.viewCondSql.title.BUDENGYU"/>');  // 不等于
				//option4.appendChild(txtNode4);
				//option4.setAttribute("value"," <> ");		
				//var option5=document.createElement("option");
				//var txtNode5=document.createTextNode('<spring:message code="hr.viewCondSql.title.DAYU"/>');  // 大于
				//option5.appendChild(txtNode5);
				//option5.setAttribute("value"," > ");	
				//var option6=document.createElement("option");
				//var txtNode6=document.createTextNode('<spring:message code="hr.viewCondSql.title.XIAOYU"/>');   //小于
				//option6.appendChild(txtNode6);
				//option6.setAttribute("value"," < ");	
				select2.appendChild(option3);
				//select2.appendChild(option4);
				//select2.appendChild(option5);
				//select2.appendChild(option6);
		    }else if(obj.alt=="YEAR"){  
		    	//年限类型的条件所插入的运算关系
				if(document.getElementById(sid2).value==""){
				  	var option3=document.createElement("option");
				  	var txtNode3=document.createTextNode('<spring:message code="hr.viewCondSql.title.DENGYU"/>');   // 等于
					option3.appendChild(txtNode3);
					option3.setAttribute("selected","selected");
					option3.setAttribute("value"," = ");
			  		var option4=document.createElement("option");
				  	var txtNode4=document.createTextNode('<spring:message code="hr.viewCondSql.title.BUDENGYU"/>');  // 不等于
					option4.appendChild(txtNode4);
					option4.setAttribute("value"," <> ");		
			  		var option5=document.createElement("option");
			  		var txtNode5=document.createTextNode('<spring:message code="hr.viewCondSql.title.DAYU"/>');  // 大于
					option5.appendChild(txtNode5);
					option5.setAttribute("value"," > ");	
			  		var option6=document.createElement("option");
			  		var txtNode6=document.createTextNode('<spring:message code="hr.viewCondSql.title.XIAOYU"/>');   //小于
					option6.appendChild(txtNode6);
					option6.setAttribute("value"," < ");	
				 	select2.appendChild(option3);
				 	select2.appendChild(option4);
				 	select2.appendChild(option5);
				 	select2.appendChild(option6);
				}else {		
		        	var option10=document.createElement("option");
				  	var txtNode10=document.createTextNode('在此区间中');   // 在此区间中
					option10.appendChild(txtNode10);
					option10.setAttribute("selected","selected");
					option10.setAttribute("value"," BETWEEN ");	
					//var option11=document.createElement("option");		
					//var txtNode11=document.createTextNode('不在在此区间中');
					//option11.appendChild(txtNode11);
					//option11.setAttribute("value"," NOT BETWEEN ");	
					select2.appendChild(option10);
					//select2.appendChild(option11);
			 	}
			} else if(obj.alt=="BASE"){
	        	if(obj.id=="isDEPT_NO_BASE"){
		            var option9=document.createElement("option");
				  	var txtNode9=document.createTextNode('包含');
					option9.appendChild(txtNode9);
					option9.setAttribute("value"," IN ");	
				  	var option12=document.createElement("option");
				  	var txtNode12=document.createTextNode('不包含');
					option12.appendChild(txtNode12);
					option12.setAttribute("value"," NOT IN ");	
		            select2.appendChild(option9);
				 	select2.appendChild(option12);
		        } else{
			    	//基本类型的条件所插入的运算关系
					var option3=document.createElement("option");
					var txtNode3=document.createTextNode('等于');
					option3.appendChild(txtNode3);
					option3.setAttribute("selected","selected");
					option3.setAttribute("value"," = ");
					var option4=document.createElement("option");
					var txtNode4=document.createTextNode('不等于');
					option4.appendChild(txtNode4);
					option4.setAttribute("value"," <> ");	
				  	var option9=document.createElement("option");
				  	var txtNode9=document.createTextNode('包含');
					option9.appendChild(txtNode9);
					option9.setAttribute("value"," IN ");	
				  	var option12=document.createElement("option");
				  	var txtNode12=document.createTextNode('不包含');
					option12.appendChild(txtNode12);
					option12.setAttribute("value"," NOT IN ");		
			  
			        var option7=document.createElement("option");		
			        var txtNode7=document.createTextNode('类似');
					option7.appendChild(txtNode7);
					option7.setAttribute("value"," LIKE ");	
					var option8=document.createElement("option");
					var txtNode8=document.createTextNode('不类似');
					option8.appendChild(txtNode8);
					option8.setAttribute("value"," NOT LIKE ");
					 
				 	select2.appendChild(option3);
				 	select2.appendChild(option4);
				 	select2.appendChild(option9);
				 	select2.appendChild(option12);		 
				 	select2.appendChild(option7);
			     	select2.appendChild(option8);
				}             
		   	}else if(obj.alt=="SEL")    {     
		   		//多选的情况，不出 = ，<>		   	
		    	if( sid1Value.indexOf(",") ==-1)  {
			  		var option3=document.createElement("option");
			  		var txtNode3=document.createTextNode('等于');
					option3.appendChild(txtNode3);
					option3.setAttribute("selected","selected");
					option3.setAttribute("value"," = ");
			  		var option4=document.createElement("option");
			  		var txtNode4=document.createTextNode('不等于');
					option4.appendChild(txtNode4);
					option4.setAttribute("value"," <> ");	
					
			  		select2.appendChild(option3);
			  		select2.appendChild(option4);	
				}else{
					var option9=document.createElement("option");
				  	var txtNode9=document.createTextNode('包含');
					option9.appendChild(txtNode9);
					option9.setAttribute("value"," IN ");	
				  	var option12=document.createElement("option");
				  	var txtNode12=document.createTextNode('不包含');
					option12.appendChild(txtNode12);
					option12.setAttribute("value"," NOT IN ");
					
				 	select2.appendChild(option9);
			     	select2.appendChild(option12);
			    }
			}
			cell2.appendChild(select2);
		
	  		var cell3=nTr.insertCell(3); 
	      	var txtNodeValue1="";
	      	var txtNodeValue2="";
	      	var txtNodeValue3="";
	      	var txtNodeValue4="";
	     	if(obj.alt=="DATE"){
	      		//日期条件 
	       		//alert("日期条件-内容")
	            txtNodeValue1=document.createElement("input"); 
		  		txtNodeValue1.setAttribute("id","start_"+rowNumInit);
		  		txtNodeValue1.setAttribute("name","start_"+rowNumInit);
		  		txtNodeValue1.setAttribute("type","text");
		  		txtNodeValue1.setAttribute("size","8");
		  		txtNodeValue1.setAttribute("value",document.getElementById(sid1).value);
		  		txtNodeValue1.setAttribute("readonly","true");
		  		cell3.appendChild(txtNodeValue1);
			  	
				if(document.getElementById(sid2).value!=""){	
			        txtNodeValue2=document.createTextNode("  ~  ");
	                cell3.appendChild(txtNodeValue2);	
		  	    
			  	    txtNodeValue3=document.createElement("input"); 
			  		txtNodeValue3.setAttribute("id","end_"+rowNumInit);
			  		txtNodeValue3.setAttribute("name","end_"+rowNumInit);
			  		txtNodeValue3.setAttribute("type","text");
			  		txtNodeValue3.setAttribute("size","8");
			  		txtNodeValue3.setAttribute("value",document.getElementById(sid2).value);
			  		txtNodeValue3.setAttribute("readonly","true");
			  		cell3.appendChild(txtNodeValue3);		
	            }    
			//(sid1.indexOf("YEAR")==(sid1.length-4))
			}else if(obj.alt=="YEAR"){
		    	//年限条件   
	            txtNodeValue1=document.createElement("input"); 
		  		txtNodeValue1.setAttribute("id","start_"+rowNumInit);
		  		txtNodeValue1.setAttribute("name","start_"+rowNumInit);
		  		txtNodeValue1.setAttribute("type","text");
		  		txtNodeValue1.setAttribute("size","3");
		  		txtNodeValue1.setAttribute("value",document.getElementById(sid1).value);
		  		txtNodeValue1.setAttribute("readonly","true");
		  		cell3.appendChild(txtNodeValue1);
			  		
				if(document.getElementById(sid2).value!=""){
			    	txtNodeValue2=document.createTextNode("  ~  ");
	                cell3.appendChild(txtNodeValue2);	
			  	    
			  	    txtNodeValue3=document.createElement("input"); 
			  		txtNodeValue3.setAttribute("id","end_"+rowNumInit);
			  		txtNodeValue3.setAttribute("name","end_"+rowNumInit);
			  		txtNodeValue3.setAttribute("type","text");
			  		txtNodeValue3.setAttribute("size","3");
			  		txtNodeValue3.setAttribute("value",document.getElementById(sid2).value);
			  		txtNodeValue3.setAttribute("readonly","true");
			  		cell3.appendChild(txtNodeValue3);
			  	}
			 //(sid1.indexOf("YEAR")==(sid1.length-4))           
		     }else if(obj.alt=="MONTH"){
		     	//年限条件   
	            txtNodeValue1=document.createElement("input"); 
		  		txtNodeValue1.setAttribute("id","start_"+rowNumInit);
		  		txtNodeValue1.setAttribute("name","start_"+rowNumInit);
		  		txtNodeValue1.setAttribute("type","text");
		  		txtNodeValue1.setAttribute("size","3");
		  		txtNodeValue1.setAttribute("value",document.getElementById(sid1).value);
		  		txtNodeValue1.setAttribute("readonly","true");
		  		cell3.appendChild(txtNodeValue1);  		
		     }else if(obj.alt=="BASE"){  //基本条件
		     	//  alert('基本条件');
		     	if(sid1=="DEPT_NO_BASE"){   //部门特殊
		            txtNodeValue1=document.createElement("input"); 
			  		txtNodeValue1.setAttribute("id",sid1+"_NO_"+rowNumInit);
			  		txtNodeValue1.setAttribute("name",sid1+"_NO_"+rowNumInit);
			  		txtNodeValue1.setAttribute("type","text");
			  		txtNodeValue1.setAttribute("size","8");
			  	    txtNodeValue1.setAttribute("value",document.getElementById("deptName_"+document.getElementById(sid1).sysLong).value);
			  		txtNodeValue1.setAttribute("readonly","true");
			  	  	cell3.appendChild(txtNodeValue1);  
			  	  	
			  	  	txtNodeValue3=document.createElement("input"); 
			  		txtNodeValue3.setAttribute("id","dept_"+rowNumInit);
			  		txtNodeValue3.setAttribute("name","dept_"+rowNumInit);
			  		txtNodeValue3.setAttribute("type","hidden");
			  	    txtNodeValue3.setAttribute("value",document.getElementById(sid1).value);
			  	    txtNodeValue3.setAttribute("size","8");
			  		txtNodeValue3.setAttribute("readonly","true");
			  		cell3.appendChild(txtNodeValue3);	
			  	
			  		if(document.getElementById(sid2).checked){
						txtNodeValue2=document.createTextNode("   ("+ document.getElementById(sid2).title +")");
			            cell3.appendChild(txtNodeValue2);
			                
			            var	txtNodeValue4=document.createElement("input"); 
				  		txtNodeValue4.setAttribute("id","dept2_"+rowNumInit);
				  		txtNodeValue4.setAttribute("name","dept2_"+rowNumInit);
				  		txtNodeValue4.setAttribute("type","hidden");
				  	    txtNodeValue4.setAttribute("value","Y");
				  		txtNodeValue4.setAttribute("readonly","true");
				  		txtNodeValue4.setAttribute("size","8");
				  		cell3.appendChild(txtNodeValue4);	
		            }         
				}else{
	                txtNodeValue1=document.createElement("input"); 
			  		txtNodeValue1.setAttribute("id","value_"+rowNumInit);
			  		txtNodeValue1.setAttribute("name","value_"+rowNumInit);
			  		txtNodeValue1.setAttribute("type","text");
			  		txtNodeValue1.setAttribute("size","8");
			  	    txtNodeValue1.setAttribute("value",document.getElementById(sid1).value);
			  		txtNodeValue1.setAttribute("readonly","true");
			  	  	cell3.appendChild(txtNodeValue1);    
				}
			} else if(obj.alt=="SEL"){  //下拉菜单条件
		       	//alert('下拉菜单条件');      
				/*txtNodeValue3=document.createElement("input"); 
		  		txtNodeValue3.setAttribute("id","value_"+rowNumInit);
		  		txtNodeValue3.setAttribute("name","value_"+rowNumInit);
		  		txtNodeValue3.setAttribute("type","hidden"); //hidden
		  		txtNodeValue3.setAttribute("size","8");
		  	    txtNodeValue3.setAttribute("value",document.getElementById(sid1).value);
		  		txtNodeValue3.setAttribute("readonly","true");
		  		cell3.appendChild(txtNodeValue3);	
		  		
		  	    txtNodeValue4=document.createElement("input"); 
		  		txtNodeValue4.setAttribute("id","valueName_"+rowNumInit);
		  		txtNodeValue4.setAttribute("name","valueName_"+rowNumInit);
		  		txtNodeValue4.setAttribute("type","text"); 
		  		txtNodeValue4.setAttribute("size","8");
		  	    txtNodeValue4.setAttribute("value", document.getElementById(sid1).options[document.getElementById(sid1).selectedIndex].text );
		  		txtNodeValue4.setAttribute("readonly","true");
		  		cell3.appendChild(txtNodeValue4);
				*/  		
			  		
		  	    txtNodeValue3=document.createElement("input"); 
		  		txtNodeValue3.setAttribute("id","value_"+rowNumInit);
		  		txtNodeValue3.setAttribute("name","value_"+rowNumInit);
		  		txtNodeValue3.setAttribute("type","hidden"); //hidden
		  		txtNodeValue3.setAttribute("size","8");
		  		var tempvalue1=getCheckedTreeKey("tree_"+sid1);
		  	 	//alert(tempvalue1);
		  	    txtNodeValue3.setAttribute("value",tempvalue1);
		  		txtNodeValue3.setAttribute("readonly","true");
		  		cell3.appendChild(txtNodeValue3);
		  		
		  		txtNodeValue3=document.createElement("input"); 
		  		txtNodeValue3.setAttribute("id","value_"+rowNumInit);
		  		txtNodeValue3.setAttribute("name","value_"+rowNumInit);
		  		txtNodeValue3.setAttribute("type","text"); //hidden
		  		txtNodeValue3.setAttribute("size","8");
		  		var tempvalue2=getCheckedTreeContent("tree_"+sid1);
		  		//alert(tempvalue2);
		  	    txtNodeValue3.setAttribute("value",tempvalue2);
		  		txtNodeValue3.setAttribute("readonly","true");
		  		cell3.appendChild(txtNodeValue3);			  		
	       	}
			var cell4=nTr.insertCell(4); 
		  	cell4.innerHTML="<span id=row_"+obj.id +"_"+rowNumInit+"  onclick=\"deleteClick_hr0404(this);\"><img src=\"/resources/images/0.gif\"/></span>";
	    } 
	    if(obj.alt=="SEL"){
	       getCheckedIsFalseTree("tree_"+sid1);
	       obj.checked=false; //下拉菜单隐藏
	    }
	 } 
	 
	function deleteClick_hr0404(obj){
    	//alert(obj.id) ;
   		var $form=$("#hr0404");
    	var nowid=(obj.id).substring(0,(obj.id).lastIndexOf('_'));
    	var rowIndex=obj.parentElement.parentElement.rowIndex;
		document.getElementById('addTable_hr0404').deleteRow(rowIndex);
    	var rowNumInit=document.getElementById('addTable_hr0404').rows.length;
    	var cnt=0;
    	for(var i=1;i<=rowNumInit;i++){
        	//alert("row_"+ nowid.substring(4)+"_"+i);
           	//alert( document.getElementById("row_"+ nowid.substring(4)+"_"+i));
          	if(document.getElementById("row_"+ nowid.substring(4)+"_"+i)){           
             	cnt++;  
          	}
     	}
    	//alert("cnt = " + cnt);
    	if(cnt==0){
     		//document.getElementById( nowid.substring(4)).checked=false;
        	$form.find(":checkbox[value='" + nowid.substring(4)+ "']").attr("checked",false);
    	}
 	}  
      
 	function createSql(){
   		var $form=$("#hr0404");
   		var cpny= $form.find("#defaultCpny").val();
   		var createtable= document.getElementById('addTable_hr0404');
   		var allrows = createtable.rows ;
   		var sqlStr="  FROM HR_EMPLOYEE_V,HR_PERSONAL_INFO_V,HR_POST_GRADE  ";
    	var wai_biao= $("#Code_type").val();
     	if(wai_biao!=""){
         	sqlStr+=" ,"+wai_biao;
         }
    	sqlStr+=" WHERE HR_EMPLOYEE_V.PERSON_ID=HR_PERSONAL_INFO_V.PERSON_ID(+) AND HR_EMPLOYEE_V.POST_GRADE_NO=HR_POST_GRADE.POST_GRADE_NO AND HR_POST_GRADE.CPNY_ID='"+cpny+"' AND  HR_EMPLOYEE_V.CPNY_ID='"+cpny+"'   ";
    	if(wai_biao!=""){
         	sqlStr+=" AND HR_EMPLOYEE_V.PERSON_ID="+wai_biao+".PERSON_ID(+) ";
         }
   		if(allrows.length > 1 ){
      		for(var i = 1 ; i < allrows.length ; i++){
         		var $row = $(createtable.rows[i].innerHTML) ;
        		if($row.find("[id='logic_" + i + "']").val()!=undefined){
              		sqlStr+=$row.find("[id='logic_" + i + "']").val()+" ";  //逻辑运算符
            	}else{
                	sqlStr+= "  AND ";
            	}
        		if($row.find("[id='name_" + i + "']").val()!=undefined) {
           			if($row.find("[id='name_" + i + "']").val()=='DEPT_NO_BASE'){
                 		sqlStr+=" HR_EMPLOYEE_V.DEPTNO ";
                	}else if($row.find("[id='name_" + i + "']").val()=='START_DOB_MONTH'){
                		sqlStr+="TO_NUMBER(NVL(TO_CHAR(HR_PERSONAL_INFO_V.DOB,'MM'),0))"+" ";  //列名
                	}else{
                		sqlStr+=$row.find("[id='name_" + i + "']").val()+" ";  //列名
                	}
         		}
         		if($row.find("[id='relation_" + i + "']").val()!=undefined) {
          			sqlStr+=$row.find("[id='relation_" + i + "']").val()+" ";  //关系运算
          		}
          		if($row.find("[id='value_" + i + "']").val()!=undefined) {
                  	if($row.find("[id='relation_" + i + "']").val()==" LIKE " || $row.find("[id='relation_" + i + "']").val()==" NOT LIKE "){
                    	sqlStr+= " '%";   
                    }else if($row.find("[id='relation_" + i + "']").val()==" IN " || $row.find("[id='relation_" + i + "']").val()==" NOT IN "){
                    	sqlStr+= " ";   
                    }else{
                    	sqlStr+=" '";
                    }
                    sqlStr+=$row.find("[id='value_" + i + "']").val();
                    if($row.find("[id='relation_" + i + "']").val()==" LIKE " || $row.find("[id='relation_" + i + "']").val()==" NOT LIKE "){
                    	sqlStr+= "%' ";   
                    }else if($row.find("[id='relation_" + i + "']").val()==" IN " || $row.find("[id='relation_" + i + "']").val()==" NOT IN "){
                        sqlStr+= " ";   
                    } else{
                        sqlStr+="' "; 
                    }
           		}
           		if($row.find("[id='start_" + i + "']").val()!=undefined) {
                	if($row.find("[id='start_" + i + "']").val().indexOf('-')!=-1){
                    	sqlStr+=" TO_DATE('"+$row.find("[id='start_" + i + "']").val()+"','YYYY-MM-DD') ";  
                    }else{
                        sqlStr+="'"+$row.find("[id='start_" + i + "']").val()+"' ";   
                    }
           		}
	            if($row.find("[id='relation_" + i + "']").val()==" BETWEEN "){
	            	sqlStr+="  AND  ";
	            }
           		if($row.find("[id='end_" + i + "']").val()!=undefined) {    
                	if($row.find("[id='end_" + i + "']").val().indexOf('-')!=-1){
                         sqlStr+=" TO_DATE('"+$row.find("[id='end_" + i + "']").val()+"','YYYY-MM-DD') ";  
                     }else{
                        sqlStr+="'"+$row.find("[id='end_" + i + "']").val()+"' ";   
                     }
           		}
            	if($row.find("[id='dept_" + i + "']").val()!=undefined) {
                	var tempval=$row.find("[id='dept_" + i + "']").val();
                    if($row.find("[id='dept2_" + i + "']").val()!=undefined && $row.find("[id='dept2_" + i + "']").val()=="Y"){
                    	sqlStr+=" (   SELECT  HR_DEPARTMENT.DEPTNO"
							        +"  FROM HR_DEPARTMENT  START WITH HR_DEPARTMENT.DEPTNO = '"+tempval+"' "
							        +"  CONNECT BY PRIOR  HR_DEPARTMENT.DEPTNO = HR_DEPARTMENT.PARENT_DEPT_NO   ) ";
                      }else{
                         sqlStr+="'"+tempval+"' ";   
                      }
           		}
      		}
   		}
   		sqlStr= sqlStr.replaceAll("-",".");
   		sqlStr= sqlStr.replaceAll("YYYY.MM.DD","YYYY-MM-DD");
   		var disp= createItem();
   		var sql=disp+" "+sqlStr;
   		var sqlCnt=" SELECT COUNT(*)  "+sqlStr; 
   		var colname=createZHItem();
   		var  SqlKey=createItemKey();
   		
   		$form.find("#SqlKey").attr("value",SqlKey);
   		$form.find("#CondSql").attr("value",sql);
    	$form.find("#CondSql").attr("rel",sql);
   		$form.find("#CondSqlCnt").attr("value",sqlCnt);
   		$form.find("#ColName").attr("value",colname);
 	}  
 
	function createItem(){
		var length=$("#displayItem option").length-1;
    	var item="";
    	var $form=$("#hr0404");
    	var language= $form.find("#defaultLanguage").val();
    	var zhItem="";
    	zhItem="工号,姓名,部门,";
    	item ="SELECT HR_EMPLOYEE_V.EMPID EMP_EMPID , HR_EMPLOYEE_V.LOCAL_NAME EMP_LOCAL_NAME , GET_DEPT_NAME(HR_EMPLOYEE_V.DEPTNO ,'"+ language +"') " +"  EMP_DEPTNO  ,";
     	for(var i=0;i<=length;i++){   
        	var index = $("#displayItem").get(0).options[i].value.indexOf("-");
          	var bieName=($("#displayItem").get(0).options[i].value).substring(index+1);
          	var shotAsTable=$("#displayItem").get(0).options[i].lang;
        	//  alert(shotAsTable);
         	var temp="";
          	if($("#displayItem").get(0).options[i].title=="G"){
            	temp=" get_global_name( "+$("#displayItem").get(0).options[i].value+" ,'"+ language +"')  "+shotAsTable+"_"+bieName+" ,";
          	}else if($("#displayItem").get(0).options[i].title=="D"){
              	temp=" TO_CHAR("+$("#displayItem").get(0).options[i].value+",'YYYY-MM-DD')  "+shotAsTable+"_"+bieName+" ,";
          	}else if($("#displayItem").get(0).options[i].title=="N"){
               	temp=" "+$("#displayItem").get(0).options[i].value+" as "+shotAsTable+"_"+bieName+",";
          	}else if($("#displayItem").get(0).options[i].title=="DEPT"){
                temp=" GET_DEPT_NAME( "+$("#displayItem").get(0).options[i].value+" ,'"+ language +"')  "+shotAsTable+"_"+bieName+" ,";
          	}
          	item+=temp;
     	}
     	var displayItem= item.substring(0,item.length-1);
     	displayItem+="  ";
     	displayItem=displayItem.replaceAll("-",".");
     	displayItem=displayItem.replaceAll("YYYY.MM.DD","YYYY-MM-DD");
   		//  alert(displayItem);
   		//  $form.find("#displayItemSql").attr("value",displayItem);
     	return displayItem;
 	}
	function createItemKey(){
 		var length=$("#displayItem option").length-1;
    	var item="";
    	var $form=$("#hr0404");
    	var language= $form.find("#defaultLanguage").val();
    	item ="EMP_EMPID , EMP_LOCAL_NAME ,  EMP_DEPTNO  ,";
      	for(var i=0;i<=length;i++){   
        	var index = $("#displayItem").get(0).options[i].value.indexOf("-");
          	var bieName=($("#displayItem").get(0).options[i].value).substring(index+1);
          	var shotAsTable=$("#displayItem").get(0).options[i].lang;
          	var temp="";
       		//   temp=" "+$("#displayItem").get(0).options[i].value+"."+shotAsTable+"_"+bieName+",";
       		temp=" "+shotAsTable+"_"+bieName+",";
          	item+=temp;
     	}
     	var displayItem= item.substring(0,item.length-1);
   		return displayItem;
 	}
 	function createZHItem(){
   		var length=$("#displayItem option").length-1;
   		var $form=$("#hr0404");
   		var zhItem="";
    	zhItem="工号,姓名,部门,";
    	for(var i=0;i<=length;i++){   
        	var temp=   $("#displayItem").get(0).options[i].text +"," ;
         	zhItem+=temp;
       	}
     	var displayItem= zhItem.substring(0,zhItem.length-1);
     	return displayItem;
 	}

 	function navTabSearch_hr0404(from){
    	//  createSql()  ;
	 	var $this = $(from);
	 	var title = "<spring:message code='hr.viewCondSql.title.SOUSUOJIEGUO'/>";//$this.attr("title") || $this.text(); 搜索结果
	 	var tabid = "" ;
	 	var fresh = eval($this.attr("fresh") || "true");
	 	var external = eval($this.attr("external") || "false");
     	var data = $this.serializeArray() ;
     	var url = $this.attr("action") ;
	 	if($this.find("#CondSql").val()=="" && $this.find("#CUST_TABLE").val()==""){
	  		alertMsg.warn("<spring:message code='hr.viewCondSql.title.QINGSHENGCHENGHUOXUANZE'/>");//请生成语句或是选择自定义报表！
	  	return false;
	 	}
		navTab.openTabPost(tabid, url, {title:title, fresh:fresh, external:external, data:data});

	 	return false ;
 	}
 
 	function  Code_type_Change(selectedValue){
    	var length=$("#displayItem option").length;
    	var $form=$("#hr0404");
    	var last_id_value= $("#hr0404").find("#now_selectValue").val();
  
   		$.ajax({
			type:'POST',
			url:'/hrm/informationRetrieval/codeTypeChange?selectedValue='+selectedValue,
	    	dataType: 'json',
			cache: false,
			success: function(responseStr){ //请求成功后处理函数。 
				$("#Code_type_HTML").html(responseStr);
			 	for(var i=0;i<$("#displayItem option").length;i++){ 
					var temp= $("#displayItem").get(0).options[i].value ;
			        temp=temp.substring(0,$("#displayItem").get(0).options[i].value.indexOf("-"));
			        if(temp==last_id_value){
				    	$("#displayItem").get(0).remove(i);
				       	i--;
				    }
			     }      
				 $("#hr0404").find("#now_selectValue").attr("value", selectedValue);
	    	}  ,
			error: DWZ.ajaxError
		});
 	}
 	function  delete_the_Table(callback){
    	var $form = $("#hr0404");
		var temp =$form.find("#CUST_TABLE").val(); 
		if (temp=="") {
			alertMsg.warn("<spring:message code='hr.viewCondSql.title.QINGXUANZESHANCHUDINGXIBIAO'/>"); //请选择要删除的自定义表！
			return false;
		}
		//确定删除这张报表？
	    alertMsg.confirm("<spring:message code='hr.viewCondSql.title.QUERENSHANCHUBIAO'/>", {
			okCall: function(){
				$.ajax({
				type: $form.method || 'POST',
				url:"/hrm/informationRetrieval/deleteCustTable?NO="+temp,
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});
		}}); 
 	}
	function clearSql(){
  		var $form = $("#hr0404");
  		var cnt=$form.find("#addTable_hr0404 tr").length-1 ;
  		for(i=cnt;i>0;i--){
  			document.getElementById('addTable_hr0404').deleteRow(i);
  		}
 		$form.find('#CondSql').val("") ;
 		//   var rowIndex=obj.parentElement.parentElement.rowIndex;
		//	document.getElementById('addTable_hr0404').deleteRow(rowIndex);
	}
</script>
<!-- 
<form id="hr0404" onsubmit="return navTabSearch_hr0404(this,'${request_navTabId}');" 
action="/hrm/informationRetrieval/viewEmpRetrieveShow" method="post" rel="pagerForm" targetType="dialog">
--> 
<form id="hr0404" onsubmit="return navTabSearch_hr0404(this,'${request_navTabId}');" 
	action="/hrm/informationRetrieval/viewEmpRetrieveShow?pageNum=1&numPerPage=10" method="post" rel="pagerForm" target="navTab"> 	  
	<input type="hidden" id="defaultLanguage" name="defaultLanguage" value="${defaultLanguage }"/>
	<input type="hidden" id="defaultCpny" name="defaultCpny" value="${defaultCpny }"/>	
	<div class="pageHeader">
		<div class="searchBar"> 
	 		<div class="subBar">
				<ul>
					<li>
						<select  class="combox" id="CUST_TABLE" name="CUST_TABLE">
							 <option value=""><!-- 请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
							  <c:forEach items="${getCustomerTableList}" var="cust" varStatus="i">
							     <option value="${cust.TABLE_NO }" >${cust.TABLE_NAME }</option>
							  </c:forEach>
				    	</select>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="delete_the_Table(navTabAjaxDone)">
									<spring:message code="hr.viewCondSql.title.SHANCHUDANGQIANBIAO"/><!-- 删除当前表-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><!-- 检索  -->
									<spring:message code="hr.viewCondSql.title.JIANSUO"/>
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<%-- <table >
			<tr>
				<td>&nbsp;</td>
				<td>
				    <select class="combox" id="CUST_TABLE" name="CUST_TABLE">
						<option value=""><!-- 请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
				  		<c:forEach items="${getCustomerTableList}" var="cust" varStatus="i">
				     		<option value="${cust.TABLE_NO }" >${cust.TABLE_NAME }</option>
						</c:forEach>
				    </select>&nbsp;
				</td>
				<td>
				    <div class="buttonContent"> <button type="button" onclick="delete_the_Table(navTabAjaxDone)">
				    <spring:message code="hr.viewCondSql.title.SHANCHUDANGQIANBIAO"/><!-- 删除当前表--></button>&nbsp;</div>
				</td>
				<td>
					<div class="buttonContent"> <button type="submit"><!-- 检索  -->
						<spring:message code="hr.viewCondSql.title.JIANSUO"/> </button>
					</div> 
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>--%>	
	</div>
	<div class="pageContent">
		<table width="100%" height="100%">
   			<tr>
     			<td width="39%">&nbsp;</td>
     			<td widt="0.5%">&nbsp;</td>
     			<td width="20%">&nbsp;</td>
     			<td widt="0.5%">&nbsp;</td>
     			<td width="40%">&nbsp;</td>
   			</tr>
    		<tr>
     		<td> <!-- 显示项目 --> 
		    	<div class="tabs" currentIndex="0" eventType="click">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;"><span><!-- 基本信息--><spring:message code="hr.viewCondSql.title.JIBENXINXI"/></span></a></li>
								<li><a href="javascript:;"><span><!-- 个人信息--><spring:message code="hr.viewCondSql.title.GERENXINXI"/></span></a></li>
								<li><a href="javascript:;"><span><!-- 其他信息  --><spring:message code="hr.viewCondSql.title.QITAXINXI"/></span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:200px;" id="displaycheckbox">
						<div>
							<div style="display:block;" id="displaycheckbox_1">
								<table border="0" cellspacing="0" cellpadding="0" style="display:block;" width="100%" class="hr04_table">
							    	<tr><!--基本信息  -->
							        	<c:forEach items="${HR_EMPLOYEE_V_LIST}" var="ListCols" varStatus="i">   
							          		<td width="33%"  valign="middle">
								         		<input type="checkbox" name="checkbox" id="checkbox_${ListCols.FIELD_ID }" title="${ListCols.FIELD_NAME }" 
								         			value="${ListCols.FIELD_ID } " onClick="add(this)" alt="${ListCols.CODE_TYPE }" 
								         			lang="${ListCols.SHOT_TABLE }"/>&nbsp;${ListCols.FIELD_NAME }
								      		</td>
								    		<c:if test="${i.count%3==0}"></tr><tr valign="middle" ></c:if>								     
								  		</c:forEach> 
								   	</tr>
		                     	</table>
		                    </div>
		             	</div>
		             	<div><!--个人信息  -->
		                	<div style="display:block;" id="displaycheckbox_2">
			                	<table border="0" cellspacing="0" cellpadding="0" style="display:block;" width="100%" class="hr04_table">
									<tr>
								    	<c:forEach items="${HR_PERSONAL_INFO_V_LIST}" var="ListCols" varStatus="i">   
								        	<td width="33%" valign="middle">
									        	<input type="checkbox" name="checkbox" id="checkbox_${ListCols.FIELD_ID }" title="${ListCols.FIELD_NAME }" 
									        		value="${ListCols.FIELD_ID } " onClick="add(this)" alt="${ListCols.CODE_TYPE }" 
									        		lang="${ListCols.SHOT_TABLE }"/>&nbsp;${ListCols.FIELD_NAME }
									      	</td>
									    	<c:if test="${i.count%3==0}"></tr><tr valign="middle" ></c:if>
									  	</c:forEach> 
									</tr>
			                     </table>
		                     </div>
		                 </div> 
				     	<div>    
	                    	<div style="display:block;" id="displaycheckbox_3">
	                    		<input type="hidden" id="now_selectValue" name="now_selectValue" value=""/>
	                        	<table border="0" cellspacing="0" cellpadding="0" style="display:block;" width="100%" class="hr04_table">
									<tr align="center">
								    	<td width="33%" valign="middle"> <!-- 其他信息 -->
								        	<select id="Code_type" name="Code_type" onchange="Code_type_Change(this.value);" style="display:block;">
							                	<option value=""><!-- 请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
						                    	<option value="HR_EXPERIENCE_INSIDE_V"><!-- 发令信息 --><spring:message code="hr.viewCondSql.title.FALINGXINXI"/></option>
						                     	<option value="HR_CONTRACT_V"><!-- 合同信息--><spring:message code="hr.viewCondSql.title.HETONGXINXI"/></option>
						                     	<option value="HR_TRAINING_INFO_V"><!-- 培训信息--><spring:message code="hr.viewCondSql.title.PEIXUNXINXI"/></option>
						                     	<option value="HR_EVS_INFO_V"><!-- 评价信息--><spring:message code="hr.viewCondSql.title.PINGJIAXINXI"/></option>
						                     	<!--<option value="HR_RESIGNATION_V"> 离职信息<spring:message code="hr.viewCondSql.title.LIZHIXINXI"/></option>-->
						                     	<option value="HR_REWARD_V"><!-- 奖励信息--><spring:message code="hr.viewCondSql.title.JIANGLIXINXI"/></option>
						                     	<option value="HR_PUNISHMENT_V"><!-- 惩戒信息--><spring:message code="hr.viewCondSql.title.CHENGJIEXINXI"/></option>
						                     	<option value="HR_LANGUAGE_LEVEL_V"><!-- 外国语--><spring:message code="hr.viewCondSql.title.WAIGUOYU"/></option>
						                     	<option value="HR_QUALIFICATION_V"><!-- 资格证书--><spring:message code="hr.viewCondSql.title.ZIGEZHENGSHU"/></option>
						                     	<option value="HR_WORK_EXPERIENCE_V"><!-- 工作经历--><spring:message code="hr.viewCondSql.title.GONGZUOJINGLI"/></option>
						                     	<!--<option value="HR_PLURALITY_V"> 兼职信息<spring:message code="hr.viewCondSql.title.JIANZHIXINXI"/></option>-->
						                     	<option value="HR_FAMILY_V"><!-- 社会关系--><spring:message code="hr.viewCondSql.title.SHEHUIGUANXI"/></option>
						                     	<!--<option value="HR_HEALTH_V"> 健康关系<spring:message code="hr.viewCondSql.title.JIANKANGGUANXI"/></option>-->
						                     	<!--<option value="HR_ADDITIONAL_INFO_V"> 特殊信息<spring:message code="hr.viewCondSql.title.TESHUXINXI"/></option>-->
						                     	<option value="HR_EMP_PA_INFO_V"><!-- 账户信息--><spring:message code="hr.viewCondSql.title.ZHANGHUXINXI"/></option>
						                     	<!--<option value="HR_FILE_V"> 档案信息<spring:message code="hr.viewCondSql.title.DANGANXINXI"/></option>-->
						                     	<!--<option value="HR_GO_ABROAD_INFO_V"> 出国信息<spring:message code="hr.viewCondSql.title.CHUGUOXINXI"/></option>-->
						                     	<!--<option value="HR_CREDENTIAL_V"> 证照信息<spring:message code="hr.viewCondSql.title.ZHANGZHAOXINXI"/></option>-->
						                     	<option value="HR_EDUCATION"><!-- 学习经历--><spring:message code="hr.viewPersonalInfo.title.SCHOOLTAG"/></option>
						                     	<option value="HR_DISABILITYINFO"><!--  残疾证--><spring:message code="hr.viewDisabled.title.DISABLED_INFO"/></option>
						                     	<option value="HR_TRADEUNION"> <!-- 工会--><spring:message code="hr.viewHealth.title.TRADEUNIONTITLE"/></option>
						                     	<option value="HR_FAMILY"> <!--家庭成员--><spring:message code="hr.viewRelation.title.FAMILY_RELATIONS"/></option>
								          	</select>
										</td>
									</tr>
			          			</table>
			                 	<br>
			                 	<div id="Code_type_HTML"></div>
	                     	</div>
	                	</div>  
			   		</div>
					<div class="tabsFooter">
						<div class="tabsFooterContent"></div>
					</div>
				</div>
				&nbsp;	 
	 		</td>
	 		<td>&nbsp;</td>
     		<td>
           		<div class="tabs" currentIndex="0" eventType="click">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li>
									<a href="javascript:;"><span><!--  基本条件--><spring:message code="hr.viewCondSql.title.JIBENTIAOJIAN"/></span></a>
								</li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:200px;" >
				    	<div style="display:block;" id="baseCondition">
							<table border="0" cellspacing="0" cellpadding="0" width="100%" class="hr04_table">
					        	<tr>
					            	<td width="60%" valign="middle">
					            		<input type="checkbox" id="isDEPT_NO_BASE" name="isDEPT_NO_BASE" 
					            			onClick="addSpan(this,'DEPT_NO_BASE','isIncludeSonDept_BASE')"  
					            			title="<spring:message code='hr.viewPersonalInfo.title.DEPTNAME'/>"  
					            			alt="BASE"/>&nbsp;<!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					            	</td>
					            	<td width="40%" valign="middle" class="emp"><ait:deptTree name="DEPT_NO_BASE" limit="hr" selected="${deptNO}"/></td>
					        	</tr>
					         	<tr>
					            	<td valign="middle"></td>
					            	<td valign="middle">
					            		<input type="checkbox" id="isIncludeSonDept_BASE" name="isIncludeSonDept_BASE"  
					            			title="<spring:message code='hr.viewCondSql.title.BAOHANZIBUMEN'/>"/><!-- 包含子部门  -->
					            			<spring:message code="hr.viewCondSql.title.BAOHANZIBUMEN"/> 
					            	</td>
					        	</tr>
					         	<tr>
					         		<td align="left" valign="middle">
					         			<input type="checkbox" id="isHR_EMPLOYEE_V-EMPID" name="isHR_EMPLOYEE_V-EMPID" 
					         				onClick="addSpan(this,'HR_EMPLOYEE_V-EMPID')"  title="<spring:message code='hr.viewPersonalInfo.title.EMPID'/>" 
					         				alt="BASE" />&nbsp;<!-- 社号  --><spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					         		</td>
					            	<td valign="middle"> <input type="text" id="HR_EMPLOYEE_V-EMPID" name="HR_EMPLOYEE_V-EMPID" class="new_width" /></td>
					        	</tr>
					        	<tr>
					            	<td valign="middle">
					            		<input type="checkbox" id="isHR_EMPLOYEE_V-LOCAL_NAME" name="isHR_EMPLOYEE_V-LOCAL_NAME" 
					            			onClick="addSpan(this,'HR_EMPLOYEE_V-LOCAL_NAME')"  title="<spring:message code='hr.viewPersonalInfo.title.LOCAL_NAME'/>" 
					            			alt="BASE"/>&nbsp;<!--姓名--><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					            	</td>
					            	<td valign="middle"> <input type="text" id="HR_EMPLOYEE_V-LOCAL_NAME" name="HR_EMPLOYEE_V-LOCAL_NAME" class="new_width" /></td>
					        	</tr>
					         	<tr>
					            	<td valign="middle">
					            		<input type="checkbox" id="isHR_PERSONAL_INFO_V-IDCARD_NO" name="isHR_PERSONAL_INFO_V-IDCARD_NO" 
						            		onClick="addSpan(this,'HR_PERSONAL_INFO_V-IDCARD_NO')"  title="<spring:message code='hr.viewPersonalInfo.title.IDCARD_NO'/>" 
					            			alt="BASE"/>&nbsp;<!-- 身份证 --><spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
					            	</td>
					            	<td valign="middle"> <input type="text" id="HR_PERSONAL_INFO_V-IDCARD_NO" name="HR_PERSONAL_INFO_V-IDCARD_NO" class="new_width" /></td>
					        	</tr>
					      	</table>
			        	</div>
			   		</div>
					<div class="tabsFooter">
						<div class="tabsFooterContent"></div>
					</div>
				</div>	
				&nbsp;
     		</td>
     		<td>&nbsp;</td>
     		<td><!-- 条件  -->
		    	<div class="tabs" currentIndex="0" eventType="click">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;"><span><!--职别条件--><spring:message code="hr.viewCondSql.title.ZHIBIETIAOJIAN"/></span></a></li>
								<li><a href="javascript:;"><span><!--常用条件--><spring:message code="hr.viewCondSql.title.CHANGYONGTIAOJIAN"/></span></a></li>
								<li><a href="javascript:;"><span><!--年限条件--><spring:message code="hr.viewCondSql.title.NIANXINATIAOJIAN"/></span></a></li>
								<li><a href="javascript:;"><span><!--日期条件--><spring:message code="hr.viewCondSql.title.RIQITIAOJIAN"/></span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:200px;">
						<div><!--职别条件  -->
					   		<div style="display:block;" id="specialCondition_1">
						 		<table border="0" cellspacing="0" cellpadding="0" width="100%" class="hr04_table">
					        		<!--<tr>
					            		<td width="40%" align="center" valign="middle"> 
					            			<input type="checkbox" id="isHR_EMPLOYEE_V-STATUS_CODE" name="isHR_EMPLOYEE_V-STATUS_CODE" 
					            				value="isHR_EMPLOYEE_V-STATUS_CODE" onClick="addSpan(this,'HR_EMPLOYEE_V-STATUS_CODE')"  
					            				title="<spring:message code='hr.viewPersonalInfo.title.STATUS_NAME'/>"  
					            				alt="SEL"/>&nbsp; 员工状态 <spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/>
					            		</td>
					            		<td width="60%" align="center" valign="middle">
											<%--<ait:SelectSyCodeByCpnyID name="HR_EMPLOYEE_V-STATUS_CODE" parentNo="1372" cnpyID="${defaultCpny}" limit="all" />
				              				<input id="btn"  type="button"  onclick="createTree('1372')" value="点击'1372'"/>
				              				<input id="btn2"  type="button"  onclick="getTree()" value="获取"/>
					              			<div id="btndiv1" style="display:none;position: absolute;z-index:100;border:solid 1px #CCC; line-height:21px; background:#FFF;" >
						                 	<div class="zTreeDemoBackground left">
			                                	<ul id="treeDemo" class="ztree"></ul>
		                                	</div>
					             		</div>--%>         
					    					<ait:CheckBoxTreeSyCode name="HR_EMPLOYEE_V-STATUS_CODE"  parentNo="1372" 
					    					divName="div_HR_EMPLOYEE_V-STATUS_CODE" formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-STATUS_CODE" funcName="STATUS_CODE"/>
					            		</td>
					        		</tr>
					         		--><tr>
					            		<td width="40%" align="center" valign="middle"> 
					            			<input type="checkbox" id="isHR_EMPLOYEE_V-EMP_TYPE_CODE" name="isHR_EMPLOYEE_V-EMP_TYPE_CODE" 
					            				value="isHR_EMPLOYEE_V-EMP_TYPE_CODE"  onClick="addSpan(this,'HR_EMPLOYEE_V-EMP_TYPE_CODE')"  
					            				title="<spring:message code='liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH'/>" 
					            				alt="SEL"/>&nbsp;<!-- 员工类型 --><spring:message code="liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH"/></td>
					            		<td  width="60%" align="center" valign="middle">
					            			<%--<ait:SelectSyCodeByCpnyID name="HR_EMPLOYEE_V-EMP_TYPE_CODE" parentNo="1368" cnpyID="${defaultCpny}" limit="all"/>--%>
					            	 		<ait:CheckBoxTreeSyCode name="HR_EMPLOYEE_V-EMP_TYPE_CODE"  parentNo="1368" divName="div_HR_EMPLOYEE_V-EMP_TYPE_CODE" 
					            	 			formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-EMP_TYPE_CODE"  funcName="EMP_TYPE_CODE"/>   
					            		</td>
					        		</tr>
					         		<tr>
					            		<td width="40%" align="center" valign="middle">  
					            			<input type="checkbox" id="isHR_EMPLOYEE_V-POST_GRADE_NO" name="isHR_EMPLOYEE_V-POST_GRADE_NO"  
					            				value="isHR_EMPLOYEE_V-POST_GRADE_NO" onClick="addSpan(this,'HR_EMPLOYEE_V-POST_GRADE_NO')"  
					            				title="<spring:message code='hr.viewPersonalInfo.title.POST_GRADE_NAME'/>" 
					            				alt="SEL"/>&nbsp;<!-- 职级(GGS) --><spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
					            		</td>
					            		<td  width="60%" align="center" valign="middle">
						          			<%--<select class="combox" name="HR_EMPLOYEE_V-POST_GRADE_NO" ref="HR_EMPLOYEE_V-POST_NO" 
						          				refUrl="/hrm/transferOrder/getPostNoByPostGradeNo?POST_GRADE_NO={value}" id="HR_EMPLOYEE_V-POST_GRADE_NO" 
						          				ref2="HR_EMPLOYEE_V-DUTY_NO" refUrl2="/hrm/transferOrder/getDutyNoByPostGradeNo?POST_GRADE_NO={value}">
												<option value="">请选择</option>
												<c:forEach items="${postGradeList}" var="postGrade">
													<option value="${postGrade.POST_GRADE_NO}" 
														<c:if test="${postGrade.POST_GRADE_NO eq upGrade.POST_GRADE_NO}">selected</c:if>>
														${postGrade.POST_GRADE_NAME}
													</option>
												</c:forEach>
											</select>
									 		<select name="HR_EMPLOYEE_V-POST_GRADE_NO"   id="HR_EMPLOYEE_V-POST_GRADE_NO" >
												<option value=""><!-- 请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
												<c:forEach items="${postGradeList}" var="postGrade">
													<option value="${postGrade.POST_GRADE_NO}"  >${postGrade.POST_GRADE_NAME}</option>
												</c:forEach>
											</select>--%> 
											<ait:CheckBoxTreeTable name="HR_EMPLOYEE_V-POST_GRADE_NO"  tableName="HR_POST_GRADE" 
											divName="div_HR_EMPLOYEE_V-POST_GRADE_NO" formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-POST_GRADE_NO"/>																	
					            		</td>
					        		</tr>
					        		<tr>
					            		<td width="40%" align="center" valign="middle">
					            			<input type="checkbox" id="isHR_EMPLOYEE_V-POST_NO" name="isHR_EMPLOYEE_V-POST_NO" 
					            				value="isHR_EMPLOYEE_V-POST_NO" onClick="addSpan(this,'HR_EMPLOYEE_V-POST_NO')"  
					            				title="<spring:message code='hr.viewPersonalInfo.title.POST_NAME'/>" 
					            				alt="SEL" />&nbsp;<!-- 职级名称(职务) --><spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
					            		</td>
					            		<td width="60%" align="center" valign="middle">
					            			<%--<select class="combox" name="HR_EMPLOYEE_V-POST_NO" id="HR_EMPLOYEE_V-POST_NO">
												<c:forEach items="${postList}" var="post">
													<option value="${post.POST_NO}" disabled="true">${postGrade.POST_NAME}</option>
												</c:forEach>
											</select>
									 		<select name="HR_EMPLOYEE_V-POST_NO" id="HR_EMPLOYEE_V-POST_NO">
                                            	<option value=""><!-- 请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
												<c:forEach items="${postList}" var="post">
													<option value="${post.POST_NO}" >${post.POST_NAME}</option>
												</c:forEach>
	 										</select>--%>	
											<ait:CheckBoxTreeTable name="HR_EMPLOYEE_V-POST_NO" tableName="HR_POST" 
											divName="div_HR_EMPLOYEE_V-POST_NO" formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-POST_NO"/>																	
					            		</td>
					        		</tr>
					         		<tr>
					            		<td width="40%" align="center" valign="middle">
					            			<input type="checkbox" id="isHR_EMPLOYEE_V-DUTY_NO" name="isHR_EMPLOYEE_V-DUTY_NO"  
						            			value="isHR_EMPLOYEE_V-DUTY_NO" onClick="addSpan(this,'HR_EMPLOYEE_V-DUTY_NO')"  
						            			title="<spring:message code='hr.viewPersonalInfo.title.DUTY_NAME'/>" 
					            				alt="SEL" />&nbsp;<!-- 职责 --><spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
					            		</td>
					            		<td width="60%" align="center" valign="middle"> 
					            			<%--<select name="HR_EMPLOYEE_V-DUTY_NO" id="HR_EMPLOYEE_V-DUTY_NO">
												<option value=""><!-- 请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
												<c:forEach items="${dutyList}" var="duty">
													<option value="${duty.DUTY_NO}">${duty.DUTY_NAME}</option>
												</c:forEach>
											</select>--%>
											<ait:CheckBoxTreeTable name="HR_EMPLOYEE_V-DUTY_NO" tableName="HR_DUTY" divName="div_HR_EMPLOYEE_V-DUTY_NO" formName="hr0404" 
												treeDivName="tree_HR_EMPLOYEE_V-DUTY_NO"/>
					            		</td>
					        		</tr>
					        		<tr>
					            		<td width="40%" align="center" valign="middle">
					            			<input type="checkbox" id="isHR_EMPLOYEE_V-POSITION_NO" name="isHR_EMPLOYEE_V-POSITION_NO"   
					            				value="isHR_EMPLOYEE_V-POSITION_NO" onClick="addSpan(this,'HR_EMPLOYEE_V-POSITION_NO')"  
					            				title="<spring:message code='hr.viewPersonalInfo.title.POSITION_NAME'/>"  
					            				alt="SEL"/>&nbsp;<!-- 职(岗)位 --><spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
					            		</td>
					            		<td width="60%" align="center" valign="middle">
					                 		<%--<select name="HR_EMPLOYEE_V-POSITION_NO" id="HR_EMPLOYEE_V-POSITION_NO">
												<option value=""><!-- 请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
												<c:forEach items="${positionList}" var="position">
													<option value="${position.POSITION_NO}" 
														<c:if test="${position.POSITION_NO eq upGrade.POSITION_NO}">selected</c:if>>
														${position.POSITION_NAME}</option>
												</c:forEach>
											</select>--%>	
								 			<ait:CheckBoxTreeTable name="HR_EMPLOYEE_V-POSITION_NO" tableName="HR_POSITION" divName="div_HR_EMPLOYEE_V-POSITION_NO" 
								 				formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-POSITION_NO"/>							
					            		</td>
					        		</tr>
					         		<tr>
					            		<td width="40%" align="center" valign="middle">
					            			<input type="checkbox" id="isHR_EMPLOYEE_V-MAN_HOUR_SYSTEM" name="isHR_EMPLOYEE_V-MAN_HOUR_SYSTEM" 
					            				value="isHR_EMPLOYEE_V-MAN_HOUR_SYSTEM"  onClick="addSpan(this,'HR_EMPLOYEE_V-MAN_HOUR_SYSTEM')"  
					            				title="<spring:message code='hr.viewPersonalInfo.title.MAN_HOUR_SYSTEM_NAME'/>"   
					            				alt="SEL"/>&nbsp;<!-- 工时制度 --><spring:message code="hr.viewPersonalInfo.title.MAN_HOUR_SYSTEM_NAME"/>
					            		</td>
					            		<td width="60%" align="center" valign="middle">
					         				<%--<ait:SelectSyCodeByCpnyID name="HR_EMPLOYEE_V-MAN_HOUR_SYSTEM" parentNo="3301" cnpyID="${defaultCpny}" limit="all"/>
					             			<ait:CheckBoxTreeSyCode  name="HR_EMPLOYEE_V-MAN_HOUR_SYSTEM"  parentNo="123200" 
					             				divName="div_HR_EMPLOYEE_V-MAN_HOUR_SYSTEM" formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-MAN_HOUR_SYSTEM" 
					             				funcName="MAN_HOUR_SYSTEM"/> --%>
					             			<ait:CheckBoxTreeSyCode  name="HR_EMPLOYEE_V-WORKING_TIME"  parentNo="123200" 
					             				divName="div_HR_EMPLOYEE_V-WORKING_TIME" formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-WORKING_TIME" 
					             				funcName="WORKING_TIME"/>
					            		</td>
					        		</tr>
					         		<tr>
						            	<td width="40%" align="center" valign="middle"> 
						            		<input type="checkbox" id="isHR_EMPLOYEE_V-EMP_OFFICE" name="isHR_EMPLOYEE_V-EMP_OFFICE"  
						            			value="isHR_EMPLOYEE_V-EMP_OFFICE" onClick="addSpan(this,'HR_EMPLOYEE_V-EMP_OFFICE')"  
						            			title="<spring:message code='hr.viewPersonalInfo.title.EMP_OFFICE_NAME'/>" 
						            			alt="SEL"/>&nbsp;<!-- 员工状态 --><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/>
						            	</td>
						            	<td width="60%" align="center" valign="middle">
						       				<%--<ait:SelectSyCodeByCpnyID name="HR_EMPLOYEE_V-EMP_OFFICE" parentNo="15118" cnpyID="${defaultCpny}" limit="all"/>   --%>
						              		<ait:CheckBoxTreeSyCode  name="HR_EMPLOYEE_V-EMP_OFFICE" parentNo="15118" divName="div_HR_EMPLOYEE_V-EMP_OFFICE" 
						              			formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-EMP_OFFICE" funcName="EMP_OFFICE"/>
						            	</td>
						        	</tr>
					        		<tr class="end">
					            		<td width="40%" align="center" valign="middle">
					            			<input type="checkbox" id="isHR_EMPLOYEE_V-WORK_AREA" name="isHR_EMPLOYEE_V-WORK_AREA"  
					            				value="isHR_EMPLOYEE_V-WORK_AREA" onClick="addSpan(this,'HR_EMPLOYEE_V-WORK_AREA')"  
					            				title="<spring:message code='hr.viewPersonalInfo.title.WORK_AREA_NAME'/>" 
					            				alt="SEL"/>&nbsp;<!-- 工作地 --><spring:message code="hr.viewPersonalInfo.title.WORK_AREA_NAME"/>
					            		</td>
					            		<td width="60%" align="center" valign="middle">
					           				<%--<ait:SelectSyCodeByCpnyID name="HR_EMPLOYEE_V-WORK_AREA" parentNo="4604" cnpyID="${defaultCpny}" limit="all"/>    --%>
					             			<ait:CheckBoxTreeSyCode name="HR_EMPLOYEE_V-WORK_AREA" parentNo="4604" divName="div_HR_EMPLOYEE_V-WORK_AREA" 
					             				formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-WORK_AREA" funcName="WORK_AREA"/>
					            		</td>
					        		</tr>
					      		</table>
			           		</div>
						</div>
						<div><!--常用条件  -->
					    	<div style="display:block;" id="specialCondition_2">
								<table border="0" cellspacing="0" cellpadding="0" width="100%" class="hr04_table">
						        	<tr>
						            	<td  width="40%" align="center" valign="middle"> 
						            		<input type="checkbox" id="isHR_PERSONAL_INFO_V-SEXCODE" name="isHR_PERSONAL_INFO_V-SEXCODE" 
						            			value="isHR_PERSONAL_INFO_V-SEXCODE" onClick="addSpan(this,'HR_PERSONAL_INFO_V-SEXCODE')"  
						            			title="<spring:message code='hr.viewPersonalInfo.title.SEX'/>" 
						            			alt="SEL"/>&nbsp;<!-- 性别 --><spring:message code="hr.viewPersonalInfo.title.SEX"/>
						            	</td>
						            	<td width="60%" align="center" valign="middle">
						          			<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-SEXCODE" parentNo="1324" cnpyID="${defaultCpny}" limit="all"/>  --%>
						            		<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-SEXCODE"  parentNo="1324" divName="div_HR_PERSONAL_INFO_V-SEXCODE" 
						            			formName="hr0404" treeDivName="tree_HR_PERSONAL_INFO_V-SEXCODE" funcName="SEXCODE"/>
						            	</td>
						        	</tr>
						        	<tr>
						            	<td width="40%" align="center" valign="middle"> 
						            		<input type="checkbox" id="isHR_PERSONAL_INFO_V-NATION_CODE" name="isHR_PERSONAL_INFO_V-NATION_CODE" 
						            			value="isHR_PERSONAL_INFO_V-NATION_CODE"  onClick="addSpan(this,'HR_PERSONAL_INFO_V-NATION_CODE')"  
						            			title="<spring:message code='hr.viewPersonalInfo.title.NATION_NAME'/>" 
						            			alt="SEL"/>&nbsp;<!-- 民族 --><spring:message code="hr.viewPersonalInfo.title.NATION_NAME"/>
						            	</td>
						            <td width="60%" align="center" valign="middle">
						           		<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-NATION_CODE" parentNo="210942" cnpyID="${defaultCpny}" limit="all"/>  --%>
						             	<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-NATION_CODE"  parentNo="210942" divName="div_HR_PERSONAL_INFO_V-NATION_CODE" 
						             		formName="hr0404" treeDivName="tree_HR_PERSONAL_INFO_V-NATION_CODE" funcName="NATION_CODE"/>
						            </td>
						        </tr>
						        <tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-POLITY_CODE" name="isHR_PERSONAL_INFO_V-POLITY_CODE" 
						            		value="isHR_PERSONAL_INFO_V-POLITY_CODE" onClick="addSpan(this,'HR_PERSONAL_INFO_V-POLITY_CODE')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.POLITY_NAME'/>" 
						            		alt="SEL"/>&nbsp;<!-- 政治面貌 --><spring:message code="hr.viewPersonalInfo.title.POLITY_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						           		<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-POLITY_CODE" parentNo="876" cnpyID="${defaultCpny}" limit="all"/> --%>
						             	<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-POLITY_CODE"  parentNo="876" 
						             		divName="div_HR_PERSONAL_INFO_V-POLITY_CODE" formName="hr0404" 
						             		treeDivName="tree_HR_PERSONAL_INFO_V-POLITY_CODE" funcName="POLITY_CODE"/>   
						           </td>
						        </tr>
						        <!--<tr>
						        	<td width="40%" align="center" valign="middle"> 
						        		<input type="checkbox" id="isHR_PERSONAL_INFO_V-BEFORE_DEGREE_CODE" name="isHR_PERSONAL_INFO_V-BEFORE_DEGREE_CODE" 
						        			value="isHR_PERSONAL_INFO_V-BEFORE_DEGREE_CODE" onClick="addSpan(this,'HR_PERSONAL_INFO_V-BEFORE_DEGREE_CODE')"  
						        			title="<spring:message code='hr.viewPersonalInfo.title.BEFORE_DEGREE_NAME'/>" 
						        			alt="SEL" />&nbsp; 入职学历 <spring:message code="hr.viewPersonalInfo.title.BEFORE_DEGREE_NAME"/>
						            </td>
						            <td  width="60%" align="center" valign="middle">
						           		<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-BEFORE_DEGREE_CODE" parentNo="1665" cnpyID="${defaultCpny}" limit="all"/>--%>
						           		<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-BEFORE_DEGREE_CODE"  parentNo="1665" 
						           			divName="div_HR_PERSONAL_INFO_V-BEFORE_DEGREE_CODE" formName="hr0404" 
						           			treeDivName="tree_HR_PERSONAL_INFO_V-BEFORE_DEGREE_CODE" funcName="BEFORE_DEGREE_CODE"/>   
                                    </td>
						        </tr>
						           --><tr>
						            <td  width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-FINAL_DEGREE_CODE" name="isHR_PERSONAL_INFO_V-FINAL_DEGREE_CODE" 
						            		value="isHR_PERSONAL_INFO_V-FINAL_DEGREE_CODE"  onClick="addSpan(this,'HR_PERSONAL_INFO_V-FINAL_DEGREE_CODE')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.FINAL_DEGREE_NAME'/>" 
						            		alt="SEL"/>&nbsp;<!-- 最终学历 --><spring:message code="hr.viewPersonalInfo.title.FINAL_DEGREE_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						           		<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-FINAL_DEGREE_CODE" parentNo="1665" cnpyID="${defaultCpny}" limit="all"/> --%>
						            	<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-FINAL_DEGREE_CODE"  parentNo="1665" 
						            		divName="div_HR_PERSONAL_INFO_V-FINAL_DEGREE_CODE" formName="hr0404" 
						            		treeDivName="tree_HR_PERSONAL_INFO_V-FINAL_DEGREE_CODE" funcName="FINAL_DEGREE_CODE"/>   
						            </td>
						         </tr>
						         <!--<tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-MARITAL_STATUS_CODE" name="isHR_PERSONAL_INFO_V-MARITAL_STATUS_CODE" 
						            		value="isHR_PERSONAL_INFO_V-MARITAL_STATUS_CODE" onClick="addSpan(this,'HR_PERSONAL_INFO_V-MARITAL_STATUS_CODE')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.MARITAL_STATUS_NAME'/>"  
						            		alt="SEL"/>&nbsp; 婚否 <spring:message code="hr.viewPersonalInfo.title.MARITAL_STATUS_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						            	<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-MARITAL_STATUS_CODE" parentNo="1709" cnpyID="${defaultCpny}" limit="all"/>--%>
						             	<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-MARITAL_STATUS_CODE"  parentNo="1709" 
						             		divName="div_HR_PERSONAL_INFO_V-MARITAL_STATUS_CODE" formName="hr0404" treeDivName="tree_HR_PERSONAL_INFO_V-MARITAL_STATUS_CODE" 
						             		funcName="MARITAL_STATUS_CODE"/>   
						            </td>
						        </tr>
						        --><!--<tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_EMPLOYEE_V-ENTRY_AREA" name="isHR_EMPLOYEE_V-ENTRY_AREA"  value="isHR_EMPLOYEE_V-ENTRY_AREA" 
						            		onClick="addSpan(this,'HR_EMPLOYEE_V-ENTRY_AREA')" title="<spring:message code='hr.viewPersonalInfo.title.ENTRY_AREA_NAME'/>" 
						            		alt="SEL"/>&nbsp; 入职地 <spring:message code="hr.viewPersonalInfo.title.ENTRY_AREA_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						            	<%--<ait:SelectSyCodeByCpnyID name="HR_EMPLOYEE_V-ENTRY_AREA" parentNo="4604" cnpyID="${defaultCpny}" limit="all"/>--%>
						            	<ait:CheckBoxTreeSyCode  name="HR_EMPLOYEE_V-ENTRY_AREA"  parentNo="4604" divName="div_HR_EMPLOYEE_V-ENTRY_AREA" formName="hr0404" 
						            		treeDivName="tree_HR_EMPLOYEE_V-ENTRY_AREA" funcName="ENTRY_AREA"/>   
						            </td>
						        </tr>
						        --><tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_EMPLOYEE_V-SOCIAL_SECURITY_AREA" name="isHR_EMPLOYEE_V-SOCIAL_SECURITY_AREA" 
						            		value="isHR_EMPLOYEE_V-SOCIAL_SECURITY_AREA"  onClick="addSpan(this,'HR_EMPLOYEE_V-SOCIAL_SECURITY_AREA')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.SOCIAL_SECURITY_AREA_NAME'/>" 
						            		alt="SEL" />&nbsp;<!-- 社保地 --><spring:message code="hr.viewPersonalInfo.title.SOCIAL_SECURITY_AREA_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						           		<%--<ait:SelectSyCodeByCpnyID name="HR_EMPLOYEE_V-SOCIAL_SECURITY_AREA" parentNo="4604" cnpyID="${defaultCpny}" limit="all"/> --%> 
						            	<ait:CheckBoxTreeSyCode  name="HR_EMPLOYEE_V-SOCIAL_SECURITY_AREA"  parentNo="4604" divName="div_HR_EMPLOYEE_V-SOCIAL_SECURITY_AREA" 
						            		formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-SOCIAL_SECURITY_AREA" funcName="SOCIAL_SECURITY_AREA"/>   
						            </td>
						        </tr>
						        <!--<tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_EMPLOYEE_V-TAX_AREA" name="isHR_EMPLOYEE_V-TAX_AREA"  value="isHR_EMPLOYEE_V-TAX_AREA" 
						            		onClick="addSpan(this,'HR_EMPLOYEE_V-TAX_AREA')"  title="<spring:message code='hr.viewPersonalInfo.title.TAX_AREA_NAME'/>" 
						            		alt="SEL" />&nbsp; 纳税地区 <spring:message code="hr.viewPersonalInfo.title.TAX_AREA_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						            	<%--<ait:SelectSyCodeByCpnyID name="HR_EMPLOYEE_V-TAX_AREA" parentNo="4604" cnpyID="${defaultCpny}" limit="all"/>--%>
						            	<ait:CheckBoxTreeSyCode  name="HR_EMPLOYEE_V-TAX_AREA"  parentNo="4604" divName="div_HR_EMPLOYEE_V-TAX_AREA" formName="hr0404" 
						            		treeDivName="tree_HR_EMPLOYEE_V-TAX_AREA" funcName="TAX_AREA"/>  
						            </td>
						        </tr>
						        --><tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-NATIONALITY_CODE" name="isHR_PERSONAL_INFO_V-NATIONALITY_CODE"   
						            		value="isHR_PERSONAL_INFO_V-NATIONALITY_CODE" onClick="addSpan(this,'HR_PERSONAL_INFO_V-NATIONALITY_CODE')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.NATIONALITY_NAME'/>" 
						            		alt="SEL"/>&nbsp;<!-- 国籍 --><spring:message code="hr.viewPersonalInfo.title.NATIONALITY_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						         		<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-NATIONALITY_CODE" parentNo="870" cnpyID="${defaultCpny}" limit="all"/>  --%>
						             	<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-NATIONALITY_CODE"  parentNo="870" 
						             		divName="div_HR_PERSONAL_INFO_V-NATIONALITY_CODE" 
						             		formName="hr0404" treeDivName="tree_HR_PERSONAL_INFO_V-NATIONALITY_CODE" funcName="NATIONALITY_CODE"/>
						            </td>
						        </tr>
						        <tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_EMPLOYEE_V-JOIN_TYPE_CODE" name="isHR_EMPLOYEE_V-JOIN_TYPE_CODE" 
						            		value="isHR_EMPLOYEE_V-JOIN_TYPE_CODE"  onClick="addSpan(this,'HR_EMPLOYEE_V-JOIN_TYPE_CODE')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.JOIN_TYPE_NAME'/>" 
						            		alt="SEL"/>&nbsp;<!-- 入职类型 --><spring:message code="hr.viewPersonalInfo.title.JOIN_TYPE_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						           		<%--<ait:SelectSyCodeByCpnyID name="HR_EMPLOYEE_V-JOIN_TYPE_CODE" parentNo="1359" cnpyID="${defaultCpny}" limit="all"/> --%>
						            	<ait:CheckBoxTreeSyCode  name="HR_EMPLOYEE_V-JOIN_TYPE_CODE"  parentNo="1359" 
						            		divName="div_HR_EMPLOYEE_V-JOIN_TYPE_CODE" formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-JOIN_TYPE_CODE" 
						            		funcName="JOIN_TYPE_CODE"/>
						            </td>
						        </tr>
						        <tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-REG_TYPE_CODE" name="isHR_PERSONAL_INFO_V-REG_TYPE_CODE" 
						            		value="isHR_PERSONAL_INFO_V-REG_TYPE_CODE" onClick="addSpan(this,'HR_PERSONAL_INFO_V-REG_TYPE_CODE')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.REG_TYPE_NAME'/>" 
						            		alt="SEL" />&nbsp;<!-- 户口性质 --><spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						        		<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-REG_TYPE_CODE" parentNo="937" cnpyID="${defaultCpny}" limit="all"/> --%>
						            	<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-REG_TYPE_CODE"  parentNo="937" 
						            		divName="div_HR_PERSONAL_INFO_V-REG_TYPE_CODE" formName="hr0404" treeDivName="tree_HR_PERSONAL_INFO_V-REG_TYPE_CODE" funcName="REG_TYPE_CODE"/>
						            </td>
						        </tr>
						        <tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-BORNPLACE_CODE" name="isHR_PERSONAL_INFO_V-BORNPLACE_CODE"  
						            		value="isHR_PERSONAL_INFO_V-BORNPLACE_CODE" onClick="addSpan(this,'HR_PERSONAL_INFO_V-BORNPLACE_CODE')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.BORNPLACE_NAME'/>" 
						            		alt="SEL"/>&nbsp;<!-- 籍贯 --><spring:message code="hr.viewPersonalInfo.title.BORNPLACE_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						           		<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-BORNPLACE_CODE" parentNo="774" cnpyID="${defaultCpny}" limit="all"/>--%>
						             	<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-BORNPLACE_CODE"  parentNo="774" divName="div_HR_PERSONAL_INFO_V-BORNPLACE_CODE" 
						             		formName="hr0404" treeDivName="tree_HR_PERSONAL_INFO_V-BORNPLACE_CODE" funcName="BORNPLACE_CODE"/>
						            </td>
						        </tr>
						        <!--<tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_EMPLOYEE_V-SERVICES_BELONG" name="isHR_EMPLOYEE_V-SERVICES_BELONG"  
						            		value="isHR_EMPLOYEE_V-SERVICES_BELONG" onClick="addSpan(this,'HR_EMPLOYEE_V-SERVICES_BELONG')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.SERVICES_BELONG_NAME'/>" 
						            		alt="SEL"/>&nbsp; 劳务所属 <spring:message code='hr.viewPersonalInfo.title.SERVICES_BELONG_NAME'/>
						            </td>
						            <td width="60%" align="center" valign="middle">
						             <ait:CheckBoxTreeSyCode name="HR_EMPLOYEE_V-SERVICES_BELONG" parentNo="3319" divName="div_HR_EMPLOYEE_V-SERVICES_BELONG" 
						             	formName="hr0404" treeDivName="tree_HR_EMPLOYEE_V-SERVICES_BELONG" funcName="SERVICES_BELONG"/>
						            </td>
						        </tr>
						         --><tr class="end">
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-INSURANCE_TYPE_CODE" name="isHR_PERSONAL_INFO_V-INSURANCE_TYPE_CODE" 
						            		value="isHR_PERSONAL_INFO_V-INSURANCE_TYPE_CODE" onClick="addSpan(this,'HR_PERSONAL_INFO_V-INSURANCE_TYPE_CODE')"  
						            		title="<spring:message code='hr.viewPersonalInfo.title.INSURANCE_TYPE_NAME'/>" 
						            		alt="SEL"/>&nbsp;<!-- 保险类型  --><spring:message code="hr.viewPersonalInfo.title.INSURANCE_TYPE_NAME"/>
						            </td>
						            <td width="60%" align="center" valign="middle"> 
						          		<%--<ait:SelectSyCodeByCpnyID name="HR_PERSONAL_INFO_V-INSURANCE_TYPE_CODE" parentNo="483" cnpyID="${defaultCpny}" limit="all"/> --%>
						            	<ait:CheckBoxTreeSyCode  name="HR_PERSONAL_INFO_V-INSURANCE_TYPE_CODE"  parentNo="483" divName="div_HR_PERSONAL_INFO_V-INSURANCE_TYPE_CODE" 
						            		formName="hr0404" treeDivName="tree_HR_PERSONAL_INFO_V-INSURANCE_TYPE_CODE" funcName="INSURANCE_TYPE_CODE"/>
						            </td>
						        </tr>
						     </table>
					     </div>
					</div>
					<div><!-- 年限条件 -->
						<div style="display:block;" id="specialCondition_3">
							<table border="0" cellspacing="0" cellpadding="0" width="100%" class="hr04_table">
						        <tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-AGE" name="isHR_PERSONAL_INFO_V-AGE" value="isHR_PERSONAL_INFO_V-AGE" 
						            		onClick="addSpan(this,'START_AGE_YEAR','END_AGE_YEAR','HR_PERSONAL_INFO_V-AGE')" 
						            		title="<spring:message code='hr.viewCondSql.title.YUANGONGNIANLING'/>" 
						            		alt="YEAR" />&nbsp;<!-- 员工年龄 --><spring:message code="hr.viewCondSql.title.YUANGONGNIANLING"/>
						            </td>
						            <td width="60%" align="center" valign="middle"> 
						            	<input type="text" id="START_AGE_YEAR" name="START_AGE_YEAR" value="" size="3"/> &nbsp;&nbsp;~&nbsp;&nbsp;    
						            	<input type="text" id="END_AGE_YEAR" name="END_AGE_YEAR" value="" size="3"/>                               
						            </td>
						        </tr>
						        <tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_EMPLOYEE_V-INNER_WORK_YEAR" name="isHR_EMPLOYEE_V-INNER_WORK_YEAR" value="isHR_EMPLOYEE_V-INNER_WORK_YEAR" 
						            		onClick="addSpan(this,'START_SINEI_WORK_YEAR','END_SINEI_WORK_YEAR','HR_EMPLOYEE_V-INNER_WORK_YEAR')" 
						            		title="<spring:message code='hr.viewPersonalInfo.title.INNER_WORK_YEAR'/>" 
						            		alt="YEAR" />&nbsp;<!-- 司内工作年资 --><spring:message code="hr.viewPersonalInfo.title.INNER_WORK_YEAR"/>
						            </td>
						            <td width="60%" align="center" valign="middle"> 
						            	<input type="text" id="START_SINEI_WORK_YEAR" name="START_SINEI_WORK_YEAR" value="" size="3"/> &nbsp;&nbsp;~&nbsp;&nbsp;    
						            	<input type="text" id="END_SINEI_WORK_YEAR" name="END_SINEI_WORK_YEAR" value="" size="3"/>                               
						            </td>
						        </tr>
						        <tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_EMPLOYEE_V-OUTER_WORK_YEAR" name="isHR_EMPLOYEE_V-OUTER_WORK_YEAR" value="isHR_EMPLOYEE_V-OUTER_WORK_YEAR" 
						            		onClick="addSpan(this,'START_SIWAI_WORK_YEAR','END_SIWAI_WORK_YEAR','HR_EMPLOYEE_V-OUTER_WORK_YEAR')" 
						            		title="<spring:message code='hr.viewPersonalInfo.title.OUTER_WORK_YEAR'/>" 
						            		alt="YEAR" />&nbsp;<!-- 司外工作年资 --><spring:message code="hr.viewPersonalInfo.title.OUTER_WORK_YEAR"/>
						            </td>
						            <td width="60%" align="center" valign="middle"> 
						            	<input type="text" id="START_SIWAI_WORK_YEAR" name="START_SIWAI_WORK_YEAR" value="" size="3"/> &nbsp;&nbsp;~&nbsp;&nbsp;    
						            	<input type="text" id="END_SIWAI_WORK_YEAR" name="END_SIWAI_WORK_YEAR" value="" size="3"/>                               
						            </td>
						        </tr>
						        <tr>
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_EMPLOYEE_V-POST_GRADE_CHANGE_DATE_YEAR" name="isHR_EMPLOYEE_V-POST_GRADE_CHANGE_DATE_YEAR" 
						            		value="isHR_EMPLOYEE_V-POST_GRADE_CHANGE_DATE_YEAR" 
						            		onClick="addSpan(this,'START_GGS_YEAR','END_GGS_YEAR','HR_EMPLOYEE_V-POST_GRADE_CHANGE_YEAR')" 
						            		title="<spring:message code='hr.viewCondSql.title.XIANZHIJIGONGLING'/>" 
						            		alt="YEAR"/>&nbsp;<!-- 现职级工龄 --><spring:message code="hr.viewCondSql.title.XIANZHIJIGONGLING"/>
						            </td>
						            <td width="60%" align="center" valign="middle"> 
						            	<input type="text" id="START_GGS_YEAR" name="START_GGS_YEAR" value="" size="3"/> &nbsp;&nbsp;~&nbsp;&nbsp;    
						            	<input type="text" id="END_GGS_YEAR" name="END_GGS_YEAR" value="" size="3"/>                               
						            </td>
						        </tr>
						         <tr class="end">
						            <td width="40%" align="center" valign="middle"> 
						            	<input type="checkbox" id="isHR_EMPLOYEE_V-NOW_DEPT_YEAR" name="isHR_EMPLOYEE_V-NOW_DEPT_YEAR" 
						            		value="isHR_EMPLOYEE_V-NOW_DEPT_YEAR" onClick="addSpan(this,'START_DEPT_YEAR','END_DEPT_YEAR','HR_EMPLOYEE_V-NOW_DEPT_YEAR')" 
						            		title="<spring:message code='hr.viewCondSql.title.XIANBUMENGONGLING'/>" 
						            		alt="YEAR"/>&nbsp;<!--  现部门工龄--><spring:message code="hr.viewCondSql.title.XIANBUMENGONGLING"/>
						            </td>
						            <td width="60%" align="center" valign="middle"> 
						            	<input type="text" id="START_DEPT_YEAR" name="START_DEPT_YEAR" value="" size="3"/> &nbsp;&nbsp;~&nbsp;&nbsp;    
						            	<input type="text" id="END_DEPT_YEAR" name="END_DEPT_YEAR" value="" size="3"/>                               
						            </td>
						        </tr>
					       </table>
					   </div>
					
					</div>
					<div><!--   日期条件 -->
						  <div style ="display:block;" id="specialCondition_4">
								 <table border="0" cellspacing="0" cellpadding="0" width="100%" class="hr04_table">
							        <!--<tr>
							            <td width="38%" valign="middle"> 
							            	<input type="checkbox" id="isHR_EMPLOYEE_V-JOIN_COMPANY_DATE" name="isHR_EMPLOYEE_V-JOIN_COMPANY_DATE" 
							            		value="isHR_EMPLOYEE_V-JOIN_COMPANY_DATE" onClick="addSpan(this,'START_RUSI_DATE','END_RUSI_DATE','HR_EMPLOYEE_V-JOIN_COMPANY_DATE')" 
							            		title="<spring:message code='hr.viewContractInfoForSearch.title.JOIN_COMPANY_DATE'/>" 
							            		alt="DATE" />&nbsp; 入司日期 <spring:message code="hr.viewContractInfoForSearch.title.JOIN_COMPANY_DATE"/> 
							            </td>
							            <td width="30%" valign="middle"> 
							            	<input type="text" id="START_RUSI_DATE" name="START_RUSI_DATE" size="10" class="required date"/>
							            </td>										 
		    							<td width="30%" valign="middle"> 
		    								<input type="text" id="END_RUSI_DATE" name="END_RUSI_DATE" size="10" class="required date"/>
		    							</td>
							            <td width="2%">&nbsp;</td>
							        </tr>
							         --><tr>
							            <td valign="middle"> 
							            	<input type="checkbox" id="isHR_EMPLOYEE_V-DATE_STARTED" name="isHR_EMPLOYEE_V-DATE_STARTED"  value="isHR_EMPLOYEE_V-DATE_STARTED" 
							            		onClick="addSpan(this,'START_RUZHI_DATE','END_RUZHI_DATE','HR_EMPLOYEE_V-DATE_STARTED')" 
							            		title="<spring:message code='hr.viewContractInfoForSearch.title.JOINDATE'/>" 
							            		alt="DATE"  />&nbsp;<!-- 入职日期 --><spring:message code="hr.viewContractInfoForSearch.title.JOINDATE"/>
							            </td>
							            <td valign="middle"> 
							            	<input type="text" id="START_RUZHI_DATE" name="START_RUZHI_DATE" size="10" class="required date"/>
							           	</td>			 
		    							<td valign="middle"> 
		    								<input type="text" id="END_RUZHI_DATE" name="END_RUZHI_DATE" size="10" class="required date"/>
		    							</td>
							            <td>&nbsp;</td>
							        </tr>
							         <tr>
							            <td valign="middle"> 
							            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-DOB" name="isHR_PERSONAL_INFO_V-DOB"  value="isHR_PERSONAL_INFO_V-DOB"  
							            		onClick="addSpan(this,'START_CHUSHENG_DATE','END_CHUSHENG_DATE','HR_PERSONAL_INFO_V-DOB')" 
							            		title="<spring:message code='hr.viewPersonalInfo.title.DOB'/>"  
							            		alt="DATE" />&nbsp;<!-- 出生日期 --><spring:message code="hr.viewPersonalInfo.title.DOB"/>
							            </td>
							            <td valign="middle"> 
							            	<input type="text" id="START_CHUSHENG_DATE" name="START_CHUSHENG_DATE" yearstart="-80" yearend="5" size="10" class="required date"/>
							            </td>								 
		    							<td valign="middle"> 
		    								<input type="text" id="END_CHUSHENG_DATE" name="END_CHUSHENG_DATE" yearstart="-80" yearend="5" size="10" class="required date"/></td>
							            <td>&nbsp;</td>
							        </tr>
							         <tr>
							            <td valign="middle"> 
							            	<input type="checkbox" id="isHR_EMPLOYEE_V-END_PROBATION_DATE" name="isHR_EMPLOYEE_V-END_PROBATION_DATE" 
							            		value="isHR_EMPLOYEE_V-END_PROBATION_DATE" 
							            		onClick="addSpan(this,'START_ZHUANZHENG_DATE','END_ZHUANZHENG_DATE','HR_EMPLOYEE_V-END_PROBATION_DATE')" 
							            		title="<spring:message code='hr.viewPersonalInfo.title.END_PROBATION_DATE'/>"  
							            		alt="DATE"  />&nbsp;<!-- 转正日期 --><spring:message code="hr.viewPersonalInfo.title.END_PROBATION_DATE"/>
							            </td>
							            <td valign="middle"> 
							            	<input type="text" id="START_ZHUANZHENG_DATE" name="START_ZHUANZHENG_DATE" size="10" class="required date"/>
							            </td>										 
		    							<td valign="middle"> 
		    								<input type="text" id="END_ZHUANZHENG_DATE" name="END_ZHUANZHENG_DATE" size="10" class="required date"/>
		    							</td>
							            <td>&nbsp;</td>
							        </tr>
							         <tr>
							            <td valign="middle"> 
							            	<input type="checkbox" id="isHR_EMPLOYEE_V-BEFORE_END_PROBATION_DATE" name="isHR_EMPLOYEE_V-BEFORE_END_PROBATION_DATE" 
							            		value="isHR_EMPLOYEE_V-BEFORE_END_PROBATION_DATE"   
							            		onClick="addSpan(this,'START_YUZHUANZHENG_DATE','END_YUZHUANZHENG_DATE','HR_EMPLOYEE_V-BEFORE_END_PROBATION_DATE')" 
							            		title="<spring:message code='hr.viewPersonalInfo.title.BEFORE_END_PROBATION_DATE'/>"  
							            		alt="DATE" />&nbsp;<!--  预转正日期--><spring:message code="hr.viewPersonalInfo.title.BEFORE_END_PROBATION_DATE"/>
							            </td>
							            <td valign="middle"> 
							            	<input type="text" id="START_YUZHUANZHENG_DATE" name="START_YUZHUANZHENG_DATE" size="10" class="required date"/>
							            </td>							 
		    							<td valign="middle"> 
		    								<input type="text" id="END_YUZHUANZHENG_DATE" name="END_YUZHUANZHENG_DATE" size="10" class="required date"/>
		    							</td>
							            <td>&nbsp;</td>
							        </tr>
							         <tr>
							            <td valign="middle"> 
							            	<input type="checkbox" id="isHR_EMPLOYEE_V-DATE_LEFT" name="isHR_EMPLOYEE_V-DATE_LEFT"  value="isHR_EMPLOYEE_V-DATE_LEFT"  
							            		onClick="addSpan(this,'START_LIZHI_DATE','END_LIZHI_DATE','HR_EMPLOYEE_V-DATE_LEFT')" 
							            		title="<spring:message code='hr.viewPromote.title.RESIGN_DATE'/>"  
							            		alt="DATE" />&nbsp;<!-- 离职日期 --><spring:message code="hr.viewPromote.title.RESIGN_DATE"/>
							            </td>
							            <td valign="middle"> 
							            	<input type="text" id="START_LIZHI_DATE" name="START_LIZHI_DATE" size="10" class="required date"/>
							            </td> 											 
		    							<td valign="middle"> 
		    								<input type="text" id="END_LIZHI_DATE" name="END_LIZHI_DATE" size="10" class="required date"/>
		    							</td>
							            <td>&nbsp;</td>
							        </tr>
							         <!--<tr>
							            <td valign="middle"> 
							            	<input type="checkbox" id="isHR_EMPLOYEE_V-JOIN_BLOC_DATE" name="isHR_EMPLOYEE_V-JOIN_BLOC_DATE" 
							            		value="isHR_EMPLOYEE_V-JOIN_BLOC_DATE"  
							            		onClick="addSpan(this,'START_JITUANRUZHI_DATE','END_JITUANRUZHI_DATE','HR_EMPLOYEE_V-JOIN_BLOC_DATE')" 
							            		title="<spring:message code='hr.viewPersonalInfo.title.JOIN_BLOC_DATE'/>"  
							            		alt="DATE" />&nbsp; 集团入职日期 <spring:message code="hr.viewPersonalInfo.title.JOIN_BLOC_DATE"/>
							            </td>
							            <td valign="middle"> 
							            	<input type="text" id="START_JITUANRUZHI_DATE" name="START_JITUANRUZHI_DATE" size="10" class="required date"/>
							            </td>										 
		    							<td valign="middle"> 
		    								<input type="text" id="END_JITUANRUZHI_DATE" name="END_JITUANRUZHI_DATE" size="10" class="required date"/>
		    							</td>
							            <td>&nbsp;</td>
							        </tr>
							         --><tr>
							            <td valign="middle"> 
							            	<input type="checkbox" id="isHR_EMPLOYEE_V-NOW_DEPARTMENT_DATE" name="isHR_EMPLOYEE_V-NOW_DEPARTMENT_DATE"  
							            		value="isHR_EMPLOYEE_V-NOW_DEPARTMENT_DATE" 
							            		onClick="addSpan(this,'START_DEPTYIDONG_DATE','END_DEPTYIDONG_DATE','HR_EMPLOYEE_V-NOW_DEPARTMENT_DATE')" 
							            		title="<spring:message code='hr.viewPersonalInfo.title.NOW_DEPARTMENT_DATE'/>"  
							            		alt="DATE" />&nbsp;<!--  现部门异动日期 --><spring:message code="hr.viewPersonalInfo.title.NOW_DEPARTMENT_DATE"/>
							            </td>
							            <td valign="middle"> 
							            	<input type="text" id="START_DEPTYIDONG_DATE" name="START_DEPTYIDONG_DATE" size="10" class="required date"/>
							           	</td>										 
		    							<td valign="middle"> 
		    								<input type="text" id="END_DEPTYIDONG_DATE" name="END_DEPTYIDONG_DATE" size="10" class="required date"/>
		    							</td>
							           <td>&nbsp;</td>
							        </tr>
							        <tr class="end">
							            <td valign="middle"> 
							            	<input type="checkbox" id="isHR_EMPLOYEE_V-POST_GRADE_CHANGE_DATE" name="isHR_EMPLOYEE_V-POST_GRADE_CHANGE_DATE" 
							            		value="isHR_EMPLOYEE_V-POST_GRADE_CHANGE_DATE" 
							            		onClick="addSpan(this,'START_GGSLEVELUP_DATE','END_GGSLEVELUP_DATE','HR_EMPLOYEE_V-POST_GRADE_CHANGE_DATE')"  
							            		title="<spring:message code='hr.viewCondSql.title.XIANZHIJISHENGJIRI'/>"  
							            		alt="DATE" />&nbsp; <!-- 现职级升级日期 --><spring:message code="hr.viewCondSql.title.XIANZHIJISHENGJIRI"/>
							            </td>
							            <td valign="middle"> 
							            	<input type="text" id="START_GGSLEVELUP_DATE" name="START_GGSLEVELUP_DATE" size="10" class="required date"/>
							            </td>									 
		    							<td valign="middle"> 
		    								<input type="text" id="END_GGSLEVELUP_DATE" name="END_GGSLEVELUP_DATE" size="10" class="required date"/>
		    							</td>
							            <td>&nbsp;</td>
							        </tr>
							          <tr>
							            <td  valign="middle"> 
							            	<input type="checkbox" id="isHR_EMPLOYEE_V-CREATE_DATE" name="isHR_EMPLOYEE_V-CREATE_DATE" 
							            		value="isHR_EMPLOYEE_V-CREATE_DATE"  
							            		onClick="addSpan(this,'START_CREATE_DATE','END_CREATE_DATE','HR_EMPLOYEE_V-CREATE_DATE')" 
							            		title="创建日期"  alt="DATE" />&nbsp;<!-- 创建日期 -->创建日期
							            </td>
							            <td valign="middle"> 
							            	<input type="text" id="START_CREATE_DATE" name="START_CREATE_DATE" size="10" class="required date"/>
							            </td>										 
		    							<td valign="middle"> 
		    								<input type="text" id="END_CREATE_DATE" name="END_CREATE_DATE" size="10" class="required date"/>
		    							</td>
							            <td>&nbsp;</td>
							        </tr>
							        </tr>
							          <tr>
							            <td valign="middle"> 
							            	<input type="checkbox" id="isHR_PERSONAL_INFO_V-DOB_MONTH" name="isHR_PERSONAL_INFO_V-DOB_MONTH" 
							            		value="isHR_PERSONAL_INFO_V-DOB"  
							            		onClick="addSpan(this,'START_DOB_MONTH','END_DOB_MONTH','HR_PERSONAL_INFO_V-DOB')" title="生日月份"  
							            		alt="MONTH" />&nbsp;<!-- 生日月份 -->生日月份
							            </td>
							            <td valign="middle"> 
							            	<input onkeyup="value=value.replace(/[^\d]/g,'') " 
							            		onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" 
							            		ID="START_DOB_MONTH" NAME="START_DOB_MONTH" size="10">
							            </td>
							            <td>&nbsp;</td>
							        </tr>
						        </table>
						  	</div>
						</div>
					</div>
					<div class="tabsFooter">
						<div class="tabsFooterContent"></div>
		    		</div>
		    	</div>
		   		&nbsp;
	 		</td>
   		</tr>
  		<tr>
   			<td>
       			<div class="tabs" currentIndex="0" eventType="click">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;">
									<span><!--已选项目--><spring:message code="hr.viewCondSql.title.YIXUANXIANGMU"/></span></a>
								</li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:160px;"> 
						<table border="0" cellspacing="0" cellpadding="0">
					    	<tr> 
						    	<td width="90%" valign="top" class="l-table-edit-td1" >
						        	<select name="displayItem" id="displayItem" size="10" style="width:100% "></select>
						      	</td>
						      	<td width="10%" align="center" class="l-table-edit-td1" valign="middle"><!-- 上移   删除 下移  -->
					          		<input class="l-button" style="margin-bottom:10px;margin-left:10px; margin-right:10px;" type="button" 
					          	  		value="<spring:message code='hr.viewCondSql.title.SHANGYI'/>" id="Button2" onClick="up()"/><!-- 上移 -->
					          	  	<input class="l-button" style="margin-bottom:10px;margin-left:10px; margin-right:10px;" type="button" 
						          	  	value="<spring:message code="hr.viewCondSql.title.SHANCHU"/>" id="Button4" onClick="del()"/><!--  删除 -->
						          	<input class="l-button" style="margin-bottom:10px;margin-left:10px; margin-right:10px;" type="button" 
						          		value="<spring:message code="hr.viewCondSql.title.XIAYI"/>" id="Button5" onClick="down()"/><!-- 下移-->
					          	</td>
					        </tr>
					     </table>
			        </div>
					<div class="tabsFooter">
						<div class="tabsFooterContent"></div>
					</div>
				</div>
   			</td>
   			<td>&nbsp;</td>
    		<td colspan="3" valign="top"><!--生成条件  -->
		    	<div class="tabs" currentIndex="0">
		        	<DIV class=tabsHeader>
						<DIV class=tabsHeaderContent>
					   		<UL>
								<LI class=selected><A href="javascript:;"><SPAN><!-- 生成语句 -->
									<spring:message code="hr.viewCondSql.title.SHENGCHENGYUJU"/>
								</SPAN></A></LI>
							</UL>
						</DIV>
					</DIV>
					<DIV style="HEIGHT: 160px" class=tabsContent>
						<table border="0" cellspacing="0" cellpadding="0" width="100%" style="DISPLAY: table">
		        			<tr>
		          				<td valign="top" height="160">
		          					<div class="hr0404">
										<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" id="addTable_hr0404" class="hr04_table">
											<TBODY>
		                 						<tr>
								                    <th width="20%"><!-- 逻辑关系 --><spring:message code="hr.viewCondSql.title.LUOJIGUANXI"/></th>
								                    <th width="28%"><!--项目--><spring:message code="hr.viewCondSql.title.XIANGMU"/> </th>
								                    <th width="15%"><!--关系--><spring:message code="hr.viewCondSql.title.GUANXI"/> </th>
								                    <th width="28%"><!--条件--><spring:message code="hr.viewCondSql.title.TIAOJIAN"/> </th>
								                    <th width="14%" nowrap="nowrap"><!--操作--><spring:message code="hr.viewCondSql.title.CAOZUO"/> </th>
								                 </tr>
								     		</TBODY>
								     	</TABLE>
		           					</div>		     
		          				</td>
		          				<td width="50"><!-- 清空所有条件 -->
		           					<input class="l-button" style="float:left; margin-left:5px; margin-right:5px;" type="button" value="清空条件" 
		           						id="Button7" onClick="clearSql()"/>
		          					<br><br><!-- 生成 -->
		            				<input class="l-button" style="float:left; margin-left:5px; margin-right:5px;" type="button" 
		            					value="<spring:message code='hr.viewCondSql.title.SHENGCHENG'/>" id="Button5" onClick="createSql()"/>
		          				</td>
		          				<td width="20%" height="160">
		                  			<input type="hidden" id="tablename" name="tablename" value=" " />
		                			<input type="hidden" id="SqlKey" name="SqlKey" value="" target="SqlKey" rel=""/>	 
		                			<input type="hidden" id="ColName" name="ColName" value="" />	 
		                 			<input type="hidden" id="CondSqlCnt" name="CondSqlCnt" value="" />	 
	                   				<textarea style="width:96%;height:146px;"  id="CondSql" name="CondSql" value="" readonly="true" target="CondSql" rel=""/>	
	               				</td>
		        			</tr>
		  				</table>
					</DIV>
					<DIV class=tabsFooter>
						<DIV class=tabsFooterContent></DIV></DIV>
					</DIV>
				</td>
   			</tr>
 		</table>
 	</div>
</form>	  
<form id="pagerForm" method="post" action="${pageUrl}">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>	