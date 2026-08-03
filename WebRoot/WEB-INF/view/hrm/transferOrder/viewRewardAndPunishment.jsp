<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style type="text/css">
.showTable{display:block;}
.hiddenTable{display:none;}
</style>
<script src="/resources/js/selectTransferEmpInfo.js" type="text/javascript"></script>
<script type="text/javascript">
//增加一行
function addOneRow(flag,selectName){
	var rewardContentsId=$("#rewardContentsId").val();
	if(flag == 'reward'){
		var rewardCount = parseInt($("#rewardCount").val());
		var i = rewardCount ;
		i++;
	    var htm="";
	   		htm+='<tr id="tr_' + i + '">';
	   		
			//“减号”
			htm+='<td id="devide_' + i + '"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="removeOneRow(\'tr_'+i+'\',\'reward\')" style="cursor:hand;"/> </td>';

	   		htm+='<td class="td_type"><input type="checkbox" id="reward_' + i + '" name="reward" value=""></td><!--选择-->';
	   		
	   		htm+='<td class="td_type" id="reward_EMPID_TEXT_' + i + '">';

	   		htm+='<input type="text" id="reward_EMPID_' + i + '"name="reward_EMPID_' + i + '" class="textInput alphanumeric required" maxlength="50" onkeydown="getCurrentEmp(event, this,\'reward\',\'' + i + '\')"/>';
			htm+='<input type="hidden" id="reward_PERSONID_' + i + '" name="reward_PERSONID_' + i + '" />';
			htm+='<input type="hidden" id="reward_DEPTNO_' + i + '" name="reward_DEPTNO_' + i + '" />';
			htm+='<input type="hidden" id="reward_POSITION_NO_' + i + '" name="reward_POSITION_NO_' + i + '" />';
			htm+='<input type="hidden" id="reward_POST_NO_' + i + '" name="reward_POST_NO_' + i + '" />';
			
			htm+='</td>';
	   		
			htm+='<td class="td_type" id="reward_LOCAL_NAME_' + i + '"></td><!--姓名-->';

			htm+='<td class="td_type" id="reward_DEPTNAME_' + i + '"></td><!--部门-->';

			htm+='<td class="td_type" id="reward_POSITION_NAME_' + i + '"></td><!--职位-->';

			htm+='<td class="td_type" id="reward_POST_NAME_' + i + '"></td><!--职级名称-->';

			//奖励日期
			htm+='<td class="td_type"><input type="text" id="REWARD_DATE_' + i + '" name="REWARD_DATE_' + i + '" class="date required textInput readonly" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);selectCheckboxOfCurrentRow(this,\'reward\');" /></td><!--奖励日期-->';

			//奖励类型
			htm+='<td class="td_type" id="REWARD_TYPE_' + i + '" >';
				$.ajax({
					 cache: false,
					 type: 'get',
					 url: "/hrm/transferOrder/selectTagForTransferOrder?parentNo=641&id=" + i + "&flag=reward&selectName="+selectName,
					 dataType:'html',
					 success: function(data) {
						$("#REWARD_TYPE_"+i).html(data);
					 }
				});
			htm+='</td><!--奖励类型-->';

			//奖励金额
			htm+='<td class="td_type"><input type="text" id="REWARD_MONEY_' + i + '"name="REWARD_MONEY_' + i + '" size="10" class="number" maxlength="7"/></td>';

			//功绩内容
			htm+='<td class="td_type"><input type="text" id="REWARD_CONTENTS_' + i + '"name="REWARD_CONTENTS_' + i + '" size="10" maxlength="60" value="'+rewardContentsId+'"/></td>';
	   		
	   		htm+='</tr>';

	   	$("#createRow_reward").append(htm) ;

	   	rewardCount++;  
	    $("#rewardCount").attr("value",rewardCount) ;

	}else{
		var punishmentCount = parseInt($("#punishmentCount").val());
		var j = punishmentCount ;
		j++;
	    var htm="";
	   		htm+='<tr id="tr_' + j + '">';
	   		
	   		//“减号”
			htm+='<td id="devide_' + j + '"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="removeOneRow(\'tr_'+j+'\',\'punishment\')" style="cursor:hand;"/> </td>';

	   		htm+='<td class="td_type"><input type="checkbox" id="punishment_' + j + '" name="punishment" value=""></td><!--选择-->';
	   		
	   		htm+='<td class="td_type" id="punishment_EMPID_TEXT_' + j + '" >';

	   		htm+='<input type="text" id="punishment_EMPID_' + j + '"name="punishment_EMPID_' + j + '" class="textInput alphanumeric required" maxlength="50" onkeydown="getCurrentEmp(event, this,\'punishment\',\'' + j + '\')"/>';
			htm+='<input type="hidden" id="punishment_PERSONID_' + j + '" name="punishment_PERSONID_' + j + '" />';
			htm+='<input type="hidden" id="punishment_DEPTNO_' + j + '" name="punishment_DEPTNO_' + j + '" />';
			htm+='<input type="hidden" id="punishment_POSITION_NO_' + j + '" name="punishment_POSITION_NO_' + j + '" />';
			htm+='<input type="hidden" id="punishment_POST_NO_' + j + '" name="punishment_POST_NO_' + j + '" />';
			
			htm+='</td>';
	   		
			htm+='<td class="td_type" id="punishment_LOCAL_NAME_' + j + '"></td><!--姓名-->';

			htm+='<td class="td_type" id="punishment_DEPTNAME_' + j + '"></td><!--部门-->';

			htm+='<td class="td_type" id="punishment_POSITION_NAME_' + j + '"></td><!--职位-->';

			htm+='<td class="td_type" id="punishment_POST_NAME_' + j + '"></td><!--职级名称-->';

			//惩戒日期
			htm+='<td class="td_type"><input type="text" id="PUNISHMENT_DATE_' + j + '" name="RPUNISHMENT_DATE_' + j + '" class="date required textInput readonly" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);selectCheckboxOfCurrentRow(this,\'punishment\');" /></td><!--惩戒日期-->';

			//惩戒类型
			htm+='<td class="td_type" id="PUNISHMENT_TYPE_' + j + '" >';
				$.ajax({
					 cache: false,
					 type: 'get',
					 url: "/hrm/transferOrder/selectTagForTransferOrder?parentNo=642&id=" + j + "&flag=punishment&selectName="+selectName,
					 dataType:'html',
					 success: function(data) {
						$("#PUNISHMENT_TYPE_"+j).html(data);
					 }
				});
			htm+='</td><!--惩戒类型-->';

			//惩戒金额
			htm+='<td class="td_type"><input type="text" id="PUNISHMENT_MONEY_' + j + '"name="PUNISHMENT_MONEY_' + j + '" size="10" class="number" maxlength="7"/></td>';

			//惩戒内容
			htm+='<td class="td_type"><input type="text" id="PUNISHMENT_CONTENTS_' + j + '"name="PUNISHMENT_CONTENTS_' + j + '" size="10" maxlength="60" value="'+rewardContentsId+'"/></td>';

	   		htm+='</tr>';

	   	$("#createRow_punishment").append(htm) ;

	   	punishmentCount++;  
	    $("#punishmentCount").attr("value",punishmentCount) ;
	}
}
//删除一行
function removeOneRow(rowNumStr,type){
	parameters = {};

	if(confirm ('<spring:message code="zxc.hrm.transferOrder.CONFIRM_DELETE"/>')){
		//获得当前行的行号
		var rowNum=parseInt(rowNumStr.substring(rowNumStr.indexOf("_")+1,rowNumStr.length));
		
		var expInsideNo = $("input[type='hidden'][id='" + type + "_EXP_INSIDE_NO_" + rowNum + "']").val();
		
		if(expInsideNo != null && expInsideNo != ""){
			parameters["EXP_INSIDE_NO"] = expInsideNo;
			parameters["TRANSFER_ORDER_TYPE"] = type.toUpperCase();
			
			$.ajax({
				type: 'POST',
				url: "/hrm/transferOrder/deleteCurrentRewardOrPunishment",
				data: parameters,
				dataType: "json",
				cache: false,
				success: function(){

				},
				error: DWZ.ajaxError
			});
		}
		
		//获得当前行之后的所有显示行并遍历赋值
		$("tr[id='tr_"+rowNum+"']").nextAll().each(function(i){
			//行号减一
			$("tr[id^='tr_"+(rowNum+i+1)+"']").attr("id","tr_"+(rowNum+i));
			//重构“工号”
			var empId_value=$("input[type='text'][id="+type+"_EMPID_"+(rowNum+i+1)+"]").val();
			if(empId_value != ''){
		   		var empId_text='<input type="text" id="' + type + '_EMPID_' + (rowNum+i+1) + '"name="' + type + '_EMPID_' + (rowNum+i+1) + '" class="textInput alphanumeric required" maxlength="50" value="'+empId_value+'" onkeydown="getCurrentEmp(event, this,\'' + type + '\',\'' + (rowNum+i) + '\')"/>';
			}else{
		   		var empId_text='<input type="text" id="' + type + '_EMPID_' + (rowNum+i+1) + '"name="' + type + '_EMPID_' + (rowNum+i+1) + '" class="textInput alphanumeric required" maxlength="50" onkeydown="getCurrentEmp(event, this,\'' + type + '\',\'' + (rowNum+i) + '\')"/>';
			}
	   		$("td[id='"+type+"_EMPID_TEXT_"+(rowNum+i+1)+"']").children(":first").replaceWith(empId_text);
			//重构“减一行”
			var img='<img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="removeOneRow(\'tr_'+(rowNum+i)+'\',\''+type+'\')" style="cursor:hand;"/>';
			$("td[id^='devide_"+(rowNum+i+1)+"']").children(":first").replaceWith(img);
			//将所有"input"的"id"编号减一
			$("input[id$='_"+(rowNum+i+1)+"']").each(function(index){
				var id=this.id.substring(0,this.id.lastIndexOf("_")+1)+(rowNum+i);
				this.id=id;
			});
			//将所有"input"的"name"编号减一
			$("input[name$='_"+(rowNum+i+1)+"']").each(function(index){
				var name=this.name.substring(0,this.name.lastIndexOf("_")+1)+(rowNum+i);
				this.name=name;
			});
			//将所有"td"的"id"编号减一
			$("td[id$='_"+(rowNum+i+1)+"']").each(function(){
				var id=this.id.substring(0,this.id.lastIndexOf("_")+1)+(rowNum+i);
				this.id=id;
			});
			//将所有"select"的"id"编号减一
			$("select[id='"+type+"_OrderType_"+(rowNum+i+1)+"']").attr("id",type+"_OrderType_"+(rowNum+i));
			if(i == $("tr[id='tr_"+rowNum+"']").nextAll().size()){
				false;
			}
		});
		if(type == 'reward'){//奖励
			$("#"+rowNumStr+"").remove();
			if($("#rewardCount").val()!=1){
				$("#rewardCount").attr("value",($("#rewardCount").val()-1));
			}
		}else{//惩罚
			$("#"+rowNumStr+"").remove();
			if($("#punishmentCount").val()!=1){
				$("#punishmentCount").attr("value",($("#punishmentCount").val()-1));
			}
		}
	}
}

//文本框输入员工编号获得员工信息
function getCurrentEmp(event,obj,flag,row){
	if(event.keyCode==13){
		$.ajax({
			type: 'POST',
			url:"/hrm/transferOrder/getEmployeeByEmpId",
			data:{"EMPID":obj.value},
			cache: false,
			dataType:"json",
			success: function(result){

				if (result.perCnt==0){//没有查到数据
					alert("<spring:message code='alert.message.noBody'/>");
				}
				
				if(result.perCnt==1){//查到一条数据
					$("input[type='checkbox'][id='"+flag+"_"+row+"']").val(result.empinfo.EMPID);//选择
					$("#"+flag+"_EMPID_"+row).attr("name",flag+"_EMPID_"+result.empinfo.EMPID).val(result.empinfo.EMPID);//工号
					//遍历隐藏域并赋值
					$("#tr_"+row).find("input[type='hidden'][name^='"+flag+"_']").each(function(index){
						this.name=this.name.substring(0,this.name.lastIndexOf("_"))+"_"+result.empinfo.EMPID;
						if(index == 0){
							this.value=result.empinfo.PERSON_ID;//编号
						}else if(index == 1){
							this.value=result.empinfo.DEPTNO;//部门编号
						}else if(index == 2){
							this.value=result.empinfo.POSITION_NO;//只为编号
						}else if(index == 3){
							this.value=result.empinfo.POST_NO;//职级名称编号
						}
					});
					$("#"+flag+"_LOCAL_NAME_"+row).html(result.empinfo.LOCAL_NAME);//姓名
					$("#"+flag+"_DEPTNAME_"+row).html(result.empinfo.DEPTNAME);//部门
					$("#"+flag+"_POSITION_NAME_"+row).html(result.empinfo.POSITION_NAME);//职位
					$("#"+flag+"_POST_NAME_"+row).html(result.empinfo.POST_NAME);//职级名称
					$("select[id='"+flag+"_OrderType_"+row+"']").attr("name","TRANS_CODE_"+result.empinfo.EMPID);//奖励方式     或     惩戒方式
					$("#"+flag.toUpperCase()+"_DATE_"+row).attr("name",flag.toUpperCase()+"_DATE_"+result.empinfo.EMPID);//奖励日期     或     惩戒日期
					$("#"+flag.toUpperCase()+"_MONEY_"+row).attr("name",flag.toUpperCase()+"_MONEY_"+result.empinfo.EMPID);//奖励金额     或     惩戒金额
					$("#"+flag.toUpperCase()+"_CONTENTS_"+row).attr("name",flag.toUpperCase()+"_CONTENTS_"+result.empinfo.EMPID);//功绩内容     或     惩戒事由
				}

				if(result.perCnt>1){//多于一条数据
					//15119设置默认查找在职员工
					document.getElementById("search_emp").href=encodeURI(encodeURI("/hrm/transferOrder/viewTransferOrderEmpList?pageNum=1&seach_EMPID="+obj.value+'&FLAG='+flag+'&ROW='+row+'&seach_EMP_OFFICE=15119'));
					document.getElementById("search_emp").click();
				}
				
			} 
		});
	}
}

//从模糊查询列表中获取选择的员工信息
function grabSelectedEmpBack(EMPID,PERSON_ID,LOCAL_NAME,DEPTNO,DEPTNAME,POSITION_NO,POSITION_NAME,POST_NO,POST_NAME,flag,row){
	$("input[type='checkbox'][id='"+flag+"_"+row+"']").val(EMPID);//选择
	$("#"+flag+"_EMPID_"+row).attr("name",flag+"_EMPID_"+EMPID).val(EMPID);//工号
	//遍历隐藏域并赋值
	$("#tr_"+row).find("input[type='hidden'][name^='"+flag+"_']").each(function(index){
		this.name=this.name.substring(0,this.name.lastIndexOf("_"))+"_"+EMPID;
		if(index == 0){
			this.value=PERSON_ID;//编号
		}else if(index == 1){
			this.value=DEPTNO;//部门编号
		}else if(index == 2){
			this.value=POSITION_NO;//只为编号
		}else if(index == 3){
			this.value=POST_NO;//职级名称编号
		}
	});
	$("#"+flag+"_LOCAL_NAME_"+row).html(LOCAL_NAME);//姓名
	$("#"+flag+"_DEPTNAME_"+row).html(DEPTNAME);//部门
	$("#"+flag+"_POSITION_NAME_"+row).html(POSITION_NAME);//职位
	$("#"+flag+"_POST_NAME_"+row).html(POST_NAME);//职级名称
	$("select[id='"+flag+"_OrderType_"+row+"']").attr("name","TRANS_CODE_"+EMPID);//奖励方式     或     惩戒方式
	$("#"+flag.toUpperCase()+"_DATE_"+row).attr("name",flag.toUpperCase()+"_DATE_"+EMPID);//奖励日期     或     惩戒日期
	$("#"+flag.toUpperCase()+"_MONEY_"+row).attr("name",flag.toUpperCase()+"_MONEY_"+EMPID);//奖励金额     或     惩戒金额
	$("#"+flag.toUpperCase()+"_CONTENTS_"+row).attr("name",flag.toUpperCase()+"_CONTENTS_"+EMPID);//功绩内容     或     惩戒事由
	$.pdialog.closeCurrent();
}

//“奖励”保存和发令
function validateCallbackReward(form,sign,callback) {	
	var $form = $("#rewardForm");
	
	if (!$form.valid()) {
		return false;
	}

	var checked = false ;

	$form.find(":checkbox[id^='reward_']").each(function(index, checkBoxObj){
	    
	    if(checkBoxObj.checked){
	      checked = true ;      
	    }
	    
	  });
	 if(!checked){
		 	//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
			return false;
		}
	 //checkbox框
	$form.find(":checkbox[id^='reward_']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      checked = true ;

	      var empid = $(checkBoxObj).val() ;
      
		  if($form.find("[name='REWARD_DATE_" + empid + "']").val()==''){
				alertMsg.error('<spring:message code="hr.alert.message.viewHortation.checkNotNullRewardDate"/>');
				$form.find("[name='REWARD_DATE_" + empid + "']").focus();
				checked=false;
			}  
	    }
	  });
	//奖励类型
	$form.find("select[id^='reward_OrderType_']").each(function(index,selectObj){
		if(selectObj.options[selectObj.options.selectedIndex].value == ''){
			$(this).css('border-color','Red');
			alertMsg.error('<spring:message code="zxc.hr.alert.message.viewHortation.checkNotNullRewardType"/>');
			checked=false;
		}
	});

	if(checked){

		if(sign=='storeReward'){
			//确定要保存吗？
			if (confirm ('<spring:message code="zxc.hr.viewEvaluate.title.SAVE_CONFIRM"/>')){			
			  	$.ajax({
					type: form.method || 'POST',
					url:"/hrm/transferOrder/storeReward",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){
				  		if(data.statusCode == 200){
			  				alertMsg.correct(data.message);
						}else{
			  				alertMsg.error(data.message);
						}
						$("#searchForm").submit();
					},
					error: DWZ.ajaxError
				});	
				
				return false;
			}
		}

		if(sign=='saveReward'){
			//确定要提交吗？
			if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){			
			  	$.ajax({
					type: form.method || 'POST',
					url:"/hrm/transferOrder/saveReward",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){
				  		if(data.statusCode == 200){
			  				alertMsg.correct(data.message);
						}else{
			  				alertMsg.error(data.message);
						}
						$("#searchForm").submit();
					},
					error: DWZ.ajaxError
				});

				return false;
			}
		}
	}
	
	return false ;
}

//“惩罚”保存和发令
function validateCallbackPunishment(form,sign,callback) {	
	var $form = $("#punishmentForm");
	
	if (!$form.valid()) {
		return false;
	}

	var checked = false ;

	$form.find(":checkbox[id^='punishment_']").each(function(index, checkBoxObj){
	    
	    if(checkBoxObj.checked){
	      checked = true ;      
	    }
	    
	  });
	 if(!checked){
		 	//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
			return false;
		}
	//checkbox框
	$form.find(":checkbox[id^='punishment_']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      checked = true ;

	      var empid = $(checkBoxObj).val() ;
	      
		  if($form.find("[name='DATE_PUNISHED_" + empid + "']").val()==''){
				alertMsg.error('<spring:message code="hr.alert.message.viewPunishMent.checkNotNullPunishMentDate"/>');
				$form.find("[name='DATE_PUNISHED_" + empid + "']").focus();
				checked=false;
			}  
	    }
	  });
	//惩戒类型
	$form.find("select[id^='punishment_OrderType_']").each(function(index,selectObj){
		if(selectObj.options[selectObj.options.selectedIndex].value == ''){
			alertMsg.error('<spring:message code="zxc.hr.alert.message.viewPunishMent.checkNotNullPunishMentType"/>');
			checked=false;
		}
	});

	if(checked){

		if(sign == 'storePunishment'){
			//确定要保存吗？
			if (confirm ('<spring:message code="zxc.hr.viewEvaluate.title.SAVE_CONFIRM"/>')){				
			  	$.ajax({
					type: form.method || 'POST',
					url:"/hrm/transferOrder/storePunishment",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){
				  		if(data.statusCode == 200){
			  				alertMsg.correct(data.message);
						}else{
			  				alertMsg.error(data.message);
						}
						$("#searchForm").submit();
					},
					error: DWZ.ajaxError
					
				});	

				return false;
			}
		}
		
		if(sign == 'savePunishment'){
			//确定要提交吗？
			if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){				
			  	$.ajax({
					type: form.method || 'POST',
					url:"/hrm/transferOrder/savePunishMent",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){
						if(data.statusCode == 200){
			  				alertMsg.correct(data.message);
						}else{
			  				alertMsg.error(data.message);
						}
						$("#searchForm").submit();
					},
					error: DWZ.ajaxError
				});
				
				return false;
			}
		}
	}
	return false ;
}

//调令类型变化时发生
$(document).ready(function(){
	$("#transferOrder_type").change(function(){
		
		$("#searchForm").submit();
	});
});

//确定按钮
function falingOkleftBtnNameForRewardAndPunishment() {
	var paramTransOrder={};

	//已选择员工总数
	paramTransOrder["selectedEmpSize"]=$("#select2Name1").children("option").size();

	//调令类型
	var transferOrder_type=$("#transferOrder_type :selected").val();
	paramTransOrder["transferOrder_type"]=transferOrder_type;

	if(transferOrder_type ==null || transferOrder_type == ""){
		alertMsg.error("请选择调令类型");
	}
	//select2Name1 已选员工不能为空
	var rightop=document.getElementById("select2Name1");
	if(rightop.length<=0){
		alertMsg.error("'请选择需要发令的员工'  不能为空");
		return false;
	}
	$("#select2Name1").children("option").each(function(index){
		paramTransOrder["PERSONID_"+index] = this.value.substring(0,this.value.indexOf(","));
	});

	if(project_type == '1'){
		$("input[type='hidden'][id^='reward_PERSONID_']").each(function(index){
			//页面下部已有员工
			paramTransOrder["hasCurrentPersonId_"+index]=this.value;
		});
		paramTransOrder["hasCurrentPersonIdSize"]=$("input[type='hidden'][id^='reward_PERSONID_']").size();
	}else{
		$("input[type='hidden'][id^='punishment_PERSONID_']").each(function(index){
			//页面下部已有员工
			paramTransOrder["hasCurrentPersonId_"+index]=this.value;
		});
		paramTransOrder["hasCurrentPersonIdSize"]=$("input[type='hidden'][id^='punishment_PERSONID_']").size();
	}
	
	$.ajax({
		type: "POST",
		url:  "/hrm/transferOrder/getSelectedEmpList",
		data: paramTransOrder,
		dataType:"json",
		cache: false,
		success: function(result){
			
			var flag="";
			var row=0;
			if(transferOrder_type == '1'){
				flag="reward";
			}else{
				flag="punishment";
			}
			for(var i = 0; i < result.perCnt; i++){
				var selectName="TRANS_CODE_"+result.empinfo[i].EMPID;
				//增加一行
				addOneRow(flag,selectName);
				if(transferOrder_type == '1'){
					row=$("#rewardCount").val();
				}else{
					row=$("#punishmentCount").val();
				}
				$("input[type='checkbox'][id='"+flag+"_"+row+"']").val(result.empinfo[i].EMPID);//选择
				$("#"+flag+"_EMPID_"+row).attr("name",flag+"_EMPID_"+result.empinfo[i].EMPID).val(result.empinfo[i].EMPID);//工号
				//遍历隐藏域并赋值
				$("#tr_"+row).find("input[type='hidden'][name^='"+flag+"_']").each(function(index){
					this.name=this.name.substring(0,this.name.lastIndexOf("_"))+"_"+result.empinfo[i].EMPID;
					if(index == 0){
						this.value=result.empinfo[i].PERSON_ID;//编号
					}else if(index == 1){
						this.value=result.empinfo[i].DEPTNO;//部门编号
					}else if(index == 2){
						this.value=result.empinfo[i].POSITION_NO;//只为编号
					}else if(index == 3){
						this.value=result.empinfo[i].POST_NO;//职级名称编号
					}
				});
				$("#"+flag+"_LOCAL_NAME_"+row).html(result.empinfo[i].LOCAL_NAME);//姓名
				$("#"+flag+"_DEPTNAME_"+row).html(result.empinfo[i].DEPTNAME);//部门
				$("#"+flag+"_POSITION_NAME_"+row).html(result.empinfo[i].POSITION_NAME);//职位
				$("#"+flag+"_POST_NAME_"+row).html(result.empinfo[i].POST_NAME);//职级名称
				$("select[id='"+flag+"_OrderType_"+row+"']").attr("name","TRANS_CODE_"+result.empinfo[i].EMPID);//奖励方式     或     惩戒方式
				$("#"+flag.toUpperCase()+"_DATE_"+row).attr("name",flag.toUpperCase()+"_DATE_"+result.empinfo[i].EMPID);//奖励日期     或     惩戒日期
				$("#"+flag.toUpperCase()+"_MONEY_"+row).attr("name",flag.toUpperCase()+"_MONEY_"+result.empinfo[i].EMPID);//奖励金额     或     惩戒金额
				$("#"+flag.toUpperCase()+"_CONTENTS_"+row).attr("name",flag.toUpperCase()+"_CONTENTS_"+result.empinfo[i].EMPID);//功绩内容     或     惩戒事由
				$("#"+flag.toUpperCase()+"_CONTENTS_"+row).val($("#rewardContentsId").val());//功绩内容     或     惩戒事由
				$("#select2Name1").empty();//清空"已选员工"
			}
		},
		error: DWZ.ajaxError
	});	
}

//点击日期时选中当前行的checkbox
function selectCheckboxOfCurrentRow(obj,type){
	var id = obj.id.substring(obj.id.lastIndexOf("_")+1,obj.id.length);
	$("input[type='checkbox'][id='" + type + "_" + id + "']").attr("checked","checked");
}

//
function querenleixing(){
	if($("#transferOrder_type").val()==""){
		//alert("请先选择调令类型");
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewHortation.selectActionType"/>');
	}
}
</script>
<a id="search_emp" name="search_emp"  href="/hrm/empinfo/viewEmpIdList?pageNum=1" lookupGroup="person" width="950"></a>
<div class="pageContent">
    <a id="onck" name="onck" href="/hrm/empinfo/viewEmpIdList?pageNum=1"
        lookupGroup="person" width="950"></a>
	<div class="panel">
		<h1></h1>
	<div>
	<form id="searchForm" onsubmit="return navTabSearch(this);" action="/hrm/transferOrder/viewRewardAndPunishment" method="post">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			height="20" class="user_table">
			<tr>
				<td class="td_type" width="6%">
					<spring:message code="heran.examineType.title"/><!-- 调令类型 -->
				</td>
				<td class="td_type" width="15%">
					<select id="transferOrder_type" name="transferOrder_type">
						<option value="">
							<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!--请选择-->
						</option>
						<option value="1" <c:if test="${tag eq '1' }">selected</c:if> >
							<spring:message code="hr.viewReward.title.REWARD"/><!-- 奖励 -->
						</option>
						<option value="2" <c:if test="${tag eq '2' }">selected</c:if> >
							<spring:message code="hr.viewReward.title.PUNISH"/><!-- 惩罚 -->
						</option>
					</select>
				</td>
				<td width="5%">
					<spring:message code="hr.viewCondSql.title.transNo"/><!--调令编号 --> 
				</td>
				<td class="td_type"  width="15%">
					<input type="input" value="${maxTransNo }" name="transferOrder_no" />
				</td>
				<td width="5%">
					<spring:message code="hr.viewReward.title.REWARD_CONTENTS"/><!-- 具体内容 -->
				</td>
				<td class="td_type">
					<input type="input" value="${REWARD_CONTENTS }" id="rewardContentsId"  onclick="querenleixing();"/>
				</td>
			
				
			</tr>
		</table>
	</form>
	
	<ait:selTransferEmpInfo select1Name="select1Name1" searchPersonName="searchPersonName1" rightBtnName="rightBtnName1" 
			searchPostGradeName="searchPostGradeName1" leftBtnName="leftBtnNameForRewardAndPunishment" searchDeptName="searchDeptName1" 
			select2Name="select2Name1" select3Name="transferOrder_type"/>
	</div>
	</div>
</div>
<br/>

<!-- 奖励 -->
<c:if test="${tag eq '1'}">
	<div class="pageContent" id="rewardDiv">
		<form style="margin:0px;padding:0px;" id="rewardForm" method="post"  class="pageForm required-validate">
			<div class="formBar">
				
				<label style="float: left; margin-right:15px;">
					<input type="checkbox" class="checkboxCtrl" group="reward"/>
					<spring:message code="hr.viewUpgrade.title.CHECKALL"/><!--全选-->
				</label>
				
				<input type="hidden" value="${maxTransNo }" name="transferOrder_no" /><!-- 调令编号 -->
				<ul>
					<li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="button" onclick="return validateCallbackReward(this,'saveReward',navTabAjaxDone);">
                                    <spring:message code="zxc.hr.transferOrder.TRANSFER_ORDER"/><!--发令-->
                                </button>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="button" onclick="return validateCallbackReward(this,'storeReward',navTabAjaxDone);">
                                    <spring:message code="hr.viewUpgrade.title.SAVE"/><!--保存-->
                                </button>
                            </div>
                        </div>
                    </li>
                    <li>
                        <a class="buttonActive" href="/pa/excelImport/importExcelData1?importFunName=/importRewardExcel&transferOrder_type=1&navTabId=hr0220" 
                                target="dialog" mask="true" width="400" height="200" >
                            <span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
                        </a>
                    </li>
                    <li>
						<a class="buttonActive" href="/pa/excelExport/exportRewardOrPunishment?transferOrderType=reward&parentNo=641" ><span>
							<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
						</a>
					</li>
					
					<%--
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="checkboxCtrl" group="reward"
									selectType="invert">
									<spring:message code="hr.viewUpgrade.title.CTRLSHIFT"/><!--反选-->
								</button>
							</div>
						</div>
					</li>
					--%>
					
					
				</ul>
			</div>
			<table id="reward_table" width="100%" class="tablea" layoutH="0" id="reward_tab">
				<thead>
					<tr>
						<th width="5%">
							<img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand;" onclick="addOneRow('reward')"/>
							<!-- 加一行 -->
						</th>
						<th width="5%">
							<spring:message code="public.title.choose"/>
							<!--选择-->
						</th>
						<th width="13%">
							<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
							<!--社号-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
							<!--姓名-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
							<!--部门-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
							<!--职位-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
							<!--职级名称-->
						</th>
						<th width="13%">
							<spring:message code="hr.viewReward.title.REWARD_DATE"/>
							<!--奖励日期-->
						</th>
						<th width="10%">
							<spring:message code="hr.viewReward.title.REWARD_TYPE_NAME"/>
							<!--奖励类型-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewReward.title.REWARD_BONUS"/>
							<!--奖励金额-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewReward.title.REWARD_CONTENTS"/>
							<!--功绩内容-->
						</th>
					</tr>
				</thead>
				<tbody id="createRow_reward">
					<c:forEach items="${rewardList}" var="item" varStatus="status">
						<tr id="tr_${status.index+1 }">
							<td id="devide_${status.index+1}">
								<img onclick="removeOneRow('tr_${status.index+1}','reward')" src="/resources/css/ligerUI/skins/icons/delete.gif" style="cursor:hand;"/>
							</td><!-- 删除一行 -->
							<td class="td_type">
								<input type="checkbox" id="reward_${status.index+1 }" name="reward" value="${item.EMPID }"/>
							</td><!-- checkbox -->
							<td class="td_type" id="reward_EMPID_TEXT_${status.index+1 }">
								<!-- 社号 -->
								<input type="text" id="reward_EMPID_${status.index+1 }" name="reward_EMPID_${item.EMPID }" value="${item.EMPID }" class="textInput alphanumeric required" maxlength="50" onkeydown="getCurrentEmp(event, this,'reward','${status.index+1}')"/>
								<!-- 员工编号 -->
								<input type="hidden" id="reward_PERSONID_${status.index+1 }" name="reward_PERSONID_${item.EMPID }" value="${item.PERSON_ID }"/>
								<!-- 部门编号 -->
								<input type="hidden" id="reward_DEPTNO_${status.index+1 }" name="reward_DEPTNO_${item.EMPID }" value="${item.DEPTNO }"/>
								<!-- 职位编号 -->
								<input type="hidden" id="reward_POSITION_NO_${status.index+1 }" name="reward_POSITION_NO_${item.EMPID }" value="${item.POSITION_NO }"/>
								<!-- 职级名称编号 -->
								<input type="hidden" id="reward_POST_NO_${status.index+1 }" name="reward_POST_NO_${item.EMPID }" value="${item.POST_NO }"/>
								<!-- 序列 -->
								<input type="hidden" id="reward_EXP_INSIDE_NO_${status.index+1 }" name="reward_EXP_INSIDE_NO_${item.EMPID }"  value="${item.EXP_INSIDE_NO }"/>
							</td>
							<td class="td_type" id="reward_LOCAL_NAME_${status.index+1 }">${item.LOCAL_NAME }</td><!-- 姓名 -->
							<td class="td_type" id="reward_DEPTNAME_${status.index+1 }">${item.DEPTNAME }</td><!-- 部门名称 -->
							<td class="td_type" id="reward_POSITION_NAME_${status.index+1 }">${item.POSITION_NAME }</td><!-- 职位 -->
							<td class="td_type" id="reward_POST_NAME_${status.index+1 }">${item.POST_NAME }</td><!-- 职级名称 -->
							<td class="td_type">
								<input type="text" id="REWARD_DATE_${status.index+1 }" name="REWARD_DATE_${item.EMPID }" value="${item.REWARDED_DATE }" class="date required textInput readonly" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);selectCheckboxOfCurrentRow(this,'reward');" />
							</td><!--奖励日期-->
							<td class="td_type" id="REWARD_TYPE_${status.index+1 }">
								<ait:SelectSyCodeByCpnyID id="reward_OrderType_${status.index+1}" name="TRANS_CODE_${item.EMPID}" parentNo="641" cnpyID="${CPNY_ID}" limit="all" selected="${item.TRANS_CODE}"
									onChangeName="titleName(this.value)" />
							</td><!--奖励类型-->
							<td class="td_type">
								<input name="REWARD_MONEY_${item.EMPID }" class="number" id="REWARD_MONEY_${status.index+1 }" type="text" size="10" maxLength="7" value="${item.REWARDED_BONUS }"/>
							</td><!-- 奖励金额 -->
							<td class="td_type">
								<input name="REWARD_CONTENTS_${item.EMPID }" id="REWARD_CONTENTS_${status.index+1 }" type="text" size="10" maxLength="60" value="${item.REWARDED_CONTENTS }"/>
							</td><!-- 功绩内容 -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="createRewardRow" width="100%"></div>
			<input type="hidden" name="count" id="rewardCount" 
				<c:choose>
					<c:when test="${rewardListCnt == 0 }">
						value="0"
					</c:when>
					<c:when test="${rewardListCnt > 0 }">
						value="${rewardListCnt }"
					</c:when>
					<c:otherwise>
						value="0"
					</c:otherwise>
				</c:choose>
			/>
		</form>
	</div>
</c:if>
	
	
<!-- 惩罚 -->
<c:if test="${tag eq '2'}">
	<div class="pageContent" id="punishmentDiv">	
		<form style="margin:0px;padding:0px;" id="punishmentForm" method="post"  class="pageForm required-validate">
			<div class="formBar">
				<label style="float: left; margin-right:15px;">
					<input type="checkbox" class="checkboxCtrl" group="punishment"/>
					<spring:message code="hr.viewUpgrade.title.CHECKALL"/><!--全选-->
				</label>
				<input type="hidden" value="${maxTransNo }" name="transferOrder_no" /><!-- 调令编号 -->
				<ul>
					<li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="button" onclick="return validateCallbackPunishment(this,'savePunishment',navTabAjaxDone);">
                                    <spring:message code="zxc.hr.transferOrder.TRANSFER_ORDER"/><!--发令-->
                                </button>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="button" onclick="return validateCallbackPunishment(this,'storePunishment',navTabAjaxDone);">
                                    <spring:message code="hr.viewUpgrade.title.SAVE"/><!--保存-->
                                </button>
                            </div>
                        </div>
                    </li>
                    <li>
                        <a class="buttonActive" href="/pa/excelImport/importExcelData1?importFunName=/importPunishmentExcel&transferOrder_type=2&navTabId=hr0220" 
                            target="dialog" mask="true" width="400" height="200" >
                            <span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
                        </a>
                    </li>
                    <li>
						<a class="buttonActive" href="/pa/excelExport/exportRewardOrPunishment?transferOrderType=punishment&parentNo=642"><span>
							<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
						</a>
					</li>
					
					<%--
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="checkboxCtrl" group="punishment"
									selectType="invert">
									<spring:message code="hr.viewUpgrade.title.CTRLSHIFT"/><!--反选-->
								</button>
							</div>
						</div>
					</li>
					--%>
					
					
				</ul>
			</div>
			<table id="punishment_table" width="100%" class="tablea" layoutH="0" id="punishment_tab">
				<thead>
					<tr>
						<th width="5%">
							<img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand;" onclick="addOneRow('punishment')"/>
							<!-- 加一行 -->
						</th>
						<th width="5%">
							<spring:message code="public.title.choose"/>
							<!--选择-->
						</th>
						<th width="13%">
							<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
							<!--社号-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
							<!--姓名-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
							<!--部门-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
							<!--职位-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
							<!--职级名称-->
						</th>
						<th width="13%">
							<spring:message code="hr.viewReward.title.DATE_PUNISHED"/>
							<!--惩戒日期-->
						</th>
						<th width="10%">
							<spring:message code="hr.viewReward.title.PUN_TYPE_NAME"/>
							<!--惩戒方式-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewReward.title.PUN_BONUS"/>
							<!--惩戒金额-->
						</th>
						<th width="9%">
							<spring:message code="hr.viewReward.title.REWARD_CONTENTS"/>
							<!--具体内容-->
						</th>
					</tr>
				</thead>
				<tbody id="createRow_punishment">
					<c:forEach items="${punishmentList}" var="item" varStatus="status">
						<tr id="tr_${status.index+1 }">
							<td id="devide_${status.index+1}">
								<img onclick="removeOneRow('tr_${status.index+1}','punishment')" src="/resources/css/ligerUI/skins/icons/delete.gif" style="cursor:hand;"/>
							</td><!-- 删除一行 -->
							<td class="td_type">
								<input type="checkbox" id="punishment_${status.index+1 }" name="punishment" value="${item.EMPID }"/>
							</td><!-- checkbox -->
							<td class="td_type" id="punishment_EMPID_TEXT_${status.index+1 }" >
								<!-- 社号 -->
								<input type="text" id="punishment_EMPID_${status.index+1 }" name="punishment_EMPID_${item.EMPID }" value="${item.EMPID }" class="textInput alphanumeric required" maxlength="50" onkeydown="getCurrentEmp(event, this,'punishment','${status.index+1}')"/>
								<!-- 员工编号 -->
								<input type="hidden" id="punishment_PERSONID_${status.index+1 }" name="punishment_PERSONID_${item.EMPID }" value="${item.PERSON_ID }"/>
								<!-- 部门编号 -->
								<input type="hidden" id="punishment_DEPTNO_${status.index+1 }" name="punishment_DEPTNO_${item.EMPID }" value="${item.DEPTNO }"/>
								<!-- 职位编号 -->
								<input type="hidden" id="punishment_POSITION_NO_${status.index+1 }" name="punishment_POSITION_NO_${item.EMPID }" value="${item.POSITION_NO }"/>
								<!-- 职级名称编号 -->
								<input type="hidden" id="punishment_POST_NO_${status.index+1 }" name="punishment_POST_NO_${item.EMPID }" value="${item.POST_NO }"/>
								<!-- 序列 -->
								<input type="hidden" id="punishment_EXP_INSIDE_NO_${status.index+1 }" name="punishment_EXP_INSIDE_NO_${item.EMPID }"  value="${item.EXP_INSIDE_NO }"/>
							</td>
							<td class="td_type" id="punishment_LOCAL_NAME_${status.index+1 }">${item.LOCAL_NAME }</td><!-- 姓名 -->
							<td class="td_type" id="punishment_DEPTNAME_${status.index+1 }">${item.DEPTNAME }</td><!-- 部门名称 -->
							<td class="td_type" id="punishment_POSITION_NAME_${status.index+1 }">${item.POSITION_NAME }</td><!-- 职位 -->
							<td class="td_type" id="punishment_POST_NAME_${status.index+1 }">${item.POST_NAME }</td><!-- 职级名称 -->
							<td class="td_type">
								<input type="text" id="PUNISHMENT_DATE_${status.index+1 }" name="PUNISHMENT_DATE_${item.EMPID }" value="${item.PUNISHED_DATE }" class="date required textInput readonly" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);selectCheckboxOfCurrentRow(this,'punishment');" />
							</td><!--惩罚日期-->
							<td class="td_type" id="PUNISHMENT_TYPE_${status.index+1 }">
								<ait:SelectSyCodeByCpnyID id="punishment_OrderType_${status.index+1}" name="TRANS_CODE_${item.EMPID}" parentNo="642" cnpyID="${CPNY_ID}" limit="all" selected="${item.TRANS_CODE}"
									onChangeName="titleName(this.value)" />
							</td><!--惩罚类型-->
							<td class="td_type">
								<input name="PUNISHMENT_MONEY_${item.EMPID }" class="number" id="PUNISHMENT_MONEY_${status.index+1 }" type="text" size="10" maxLength="7" value="${item.PUNISHED_BONUS }"/>
							</td><!-- 惩罚金额 -->
							<td class="td_type">
								<input name="PUNISHMENT_CONTENTS_${item.EMPID }" id="PUNISHMENT_CONTENTS_${status.index+1 }" type="text" size="10" maxLength="60" value="${item.PUNISHED_CONTENTS }"/>
							</td><!-- 惩罚内容 -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="createPunishmentRow" width="100%"></div>
			<input type="hidden" name="count" id="punishmentCount" 
				<c:choose>
					<c:when test="${punishmentListCnt == 0 }">
						value="0"
					</c:when>
					<c:when test="${punishmentListCnt > 0 }">
						value="${punishmentListCnt }"
					</c:when>
					<c:otherwise>
						value="0"
					</c:otherwise>
				</c:choose>
			/>
		</form>
	</div>
</c:if>
