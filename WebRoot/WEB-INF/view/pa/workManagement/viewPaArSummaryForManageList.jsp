<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable({
			"bPaginate": true,    //分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
		    //"lengthMenu": [[20, 50, 100, -1], [20, 50, 100, "所有"]],
			//"bLengthChange": true,  //按多少条记录显示下拉框
			//"iDisplayLength": 20, //默认每页显示的记录数
			"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
         "searching": true,//本地搜索
			"bSort": true,   //排序功能
			"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		//"bScrollInfinite":true,
         "orderClasses": false,
         "order":[],//初始化不用自动排序
         "scrollY": $(document.body).height() - 300,
         "scrollCollapse": false,
         "deferRender":true,
         "scroller":true,
         "columnDefs": [//使某个字段不支持快速检索
                        { "searchable": false, "targets": [13,14] },
                        { "orderable": false, "targets": [1] },
                        { "orderDataType": "dom-text-numeric", "targets": [11,12] },
                        { "visible": false, "searchable": true, "targets": [0] }
                      ],
                      initComplete: function () {//列筛选
                          var api = this.api();
                          api.columns().indexes().flatten().each(function (i) {
                        	  if(i==1){//选中后，标记为需要提交的数据
                        		  var column = api.column(i);
                        		  column.on('change','tr',function(){
                        			  if($('#'+api.cell($(this).context._DT_RowIndex,1).node().children[0].id,navTab.getCurrentPanel()).attr('checked')=='checked'){
                        			  	api.cell($(this).context._DT_RowIndex,0).data('@willBeCommit@');
                        			     $(this).toggleClass('selected');
                        			  }else 
                        				api.cell($(this).context._DT_RowIndex,0).data(''); 
                        		  });
                        	  }
                              if (i==5||i==6||i==8) {//设定第几列有筛选框起始列是0
                                  var column = api.column(i);
                              	  //没有数据时，不需要列筛选
                              	  if(column.data().length == 0)
                              		  return;
                                  var $span = $('<span class="addselect"><div id="specailSele'+i+'" >▾</div></span>').appendTo($(column.header()));
                                  var select = $('<select><option value="">▾</option></select>')
                                          .appendTo($(column.header()))
                                          .on('click', function (evt) {
                                              evt.stopPropagation();
                                              var val = $.fn.dataTable.util.escapeRegex(
                                                      $(this).val()
                                              );
                                              column
                                                      .search(val ? '^' + val + '$' : '', true, false)
                                                      .draw();
                                          });
                                  select.on('change', function (evt) {
                                      var val = $.fn.dataTable.util.escapeRegex(
                                              $(this).val()
                                      );
                                      if(val == ''){
                                     	 $('#specailSele'+i,navTab.getCurrentPanel()).text("▾");
                                      }else{
                                     	 $('#specailSele'+i,navTab.getCurrentPanel()).text("*");
                                      }
                                  }); 
                                  column.data().unique().sort().each(function (d, j) {
                                      function delHtmlTag(str) {
                                          return str.replace(/<[^>]+>/g, "");//去掉html标签
                                      }
        
                                      d = delHtmlTag(d);
                                      select.append('<option value="' + d + '">' + d + '</option>');
                                      $span.append(select);
                                  });
        
                              }
                          });
        
                      },
         "language": {
                   	//正在加载中......
                    "sProcessing": "<spring:message code='ess.message.loading' />",
                    //查询不到相关数据！
                    "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
                    //表中无数据存在！
                    "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
                    //快速筛选
                    "sSearch": "<spring:message code='ess.message.rapid_screening' />",
                    //每页 _MENU_ 条记录
                    "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
                    //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
                    "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
                    //(从 _MAX_ 条记录过滤)
                    "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
                    "oPaginate": {
                         //上一页
                         "sPrevious": "<spring:message code='ess.message.previous_page' />",
                         //下一页
                         "sNext": "<spring:message code='ess.message.next_page' />"
             }
         },
         "sDom":'<"top"r<"clear">fB>t<"bottom"ip<"clear">>',
         "buttons": [
                   {//查看选中
                	   text: '<spring:message code="ar.viewPaArSummaryForManageList.CHAKANXUANZHONG.b" />',
                       action: function ( e, dt, node, config ) {
                    	   onlyCheckedInfo();
                       }
                   },
                   {//查看全部
                	   text: '<spring:message code="hr.viewPersonalInfo.title.chakanquanbu" />',
                       action: function ( e, dt, node, config ) {
                    	   allCheckedInfo();
                       }
                   }
               ]
		});
	$("#ViewPaArSummaryForManageTable tbody",navTab.getCurrentPanel()).on( 'click', 'tr', function () {
		if($('#'+$("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable().api()
				.cell($(this).context._DT_RowIndex,1).node().children[0].id,navTab.getCurrentPanel()).attr('checked')=='checked')
			return;
	     $(this).toggleClass('selected');
	 } );
	$("#searchViewPaArSuForMaFormBut",navTab.getCurrentPanel()).click(function(){
		//避免数据查出的太多，不选择以下条件时不可以查询
		if($('#seach_KEY',navTab.getCurrentPanel()).val()==''&&
				$('#viewPa1301_seachDept',navTab.getCurrentPanel()).val()==''&&
				$('input[name=seach_AR_SUMMARY_ITEM_NAME]',navTab.getCurrentPanel()).val()==''
				&&$('#isSpecialFlag',navTab.getCurrentPanel()).val()==''){
			alert('<spring:message code="ar.viewPaArSummaryForManageList.QINGXUANZERENYUANXIANGMUBUMEN.b" />');//请选择人员或者项目或者部门
			return;
		}
		$("#searchViewPaArSummaryForManageForm",navTab.getCurrentPanel()).submit();
	});
	//全选后，分页里面的所有数据都会被选中
	$("#viewCheckGroup",navTab.getCurrentPanel()).click(function(){
		if($("#viewCheckGroup",navTab.getCurrentPanel()).attr('checked')=='checked'){
			//$("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable().api().page.len(-1).draw();
			$('input[name=viewCheck]',$("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable()).each(function(){this.checked=true;});
			var tempRowInd = 0;
			$("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable().api().column(0).nodes().each(
				function(){
					this.cell(tempRowInd++,0).data('@willBeCommit@');
				}	
			);
			
		}else{
			//$("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable().api().page.len(20).draw();
			$('input[name=viewCheck]',navTab.getCurrentPanel()).each(function(){this.checked=false;});
			var tempRowInd = 0;
			$("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable().api().column(0).nodes().each(
				function(){
					this.cell(tempRowInd++,0).data('');
				}	
			);
		}
		
	}); 
});
	function onlyCheckedInfo(){
   	 	//点击查看选中后，筛选出所有要修改的记录
  		var column = $("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable().api().column(0);
  		column.search('@willBeCommit@', true, false).draw();
	}
	function allCheckedInfo(){
   	 	//点击查看选中后，筛选出所有要修改的记录
  		var column = $("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable().api().column(0);
  		column.search('', true, false).draw();
	}
	function pa1301_searchPop(flag){
		var name=encodeURI(encodeURI($("#seach_KEY",navTab.getCurrentPanel()).val()));
		$("#pa1301_searchPop",navTab.getCurrentPanel()).attr('href','/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='+name
				//+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName
				);
		if(flag == 'onkeyup')
			$("#pa1301_searchPop",navTab.getCurrentPanel()).click();
	}
	function doUpdateArSummaryForManageAjax(){
		
		onlyCheckedInfo();
		//$("#ViewPaArSummaryForManageTable",navTab.getCurrentPanel()).dataTable().api().page.len(-1).draw();
		
		var $form = $("#updatePaArSummaryForManageInfo");
		if (!$form.valid()) {
			return false;
		}
		var flag=false;
		$("input[name='viewCheck']").each(function(){
			if($(this).attr("checked") == "checked"){
				flag = true;
			}
		});
		if(flag == false){
			alertMsg.error("<spring:message code='ar.viewPaArSummaryForManageList.QINGXUANZEBAOCUNSHUJU.b' />");//请选择要保存的数据
			return false;
		}
		if($("#PAY_SCHEDULE_NO",navTab.getCurrentPanel()).find("option:selected").attr('syslong')=='1'){
			alert($("#PAY_SCHEDULE_NO",navTab.getCurrentPanel()).find("option:selected").text()+' 工资已经确认，不能修改！');
			return;
		}
		
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
	  				url:$form.attr("action"),
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: doAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}//,cancelCall:allCheckedInfo
	  	});

	}
	function arMonthCal() {
		if($("#PAY_SCHEDULE_NO",navTab.getCurrentPanel()).find("option:selected").attr('syslong')=='1'){
			alert($("#PAY_SCHEDULE_NO",navTab.getCurrentPanel()).find("option:selected").text()+' <spring:message code="ar.viewPaArSummaryForManageList.GONGZIYIQUERENBUNENGJISUAN.b" />');//工资已经确认，不能计算！
			return;
		}
		alertMsg.confirm("<spring:message code='org.title.IS_SELECT_EXECUTE' />",//确定要执行吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type:'POST',
	  				url:'/pa/workManagement/execPaWorkFlow?PAY_SCHEDULE_NO='+$("#PAY_SCHEDULE_NO",navTab.getCurrentPanel()).val(),
	  				//data:{type:processType,PAY_SCHEDULE_NO:$("#PAY_SCHEDULE_NO").val()},
	  				data:$('#updatePaArSummaryForManageInfo').serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: function(json){
	  			  		DWZ.ajaxDone(json);
	  			  		},
	  				error: DWZ.ajaxError
	  			});
	  	}});
		return false;
	}
	/**
	 * 禁用textArea以及Input框的enter键的自动提交
	 */
	document.onkeydown = function(event) {
		  var target, code, tag;  
		  if (!event) {  
		       event = window.event; //针对ie浏览器  
		       target = event.srcElement;  
		       code = event.keyCode;  
		       if (code == 13) {  
		           tag = target.tagName;  
		           if (tag == "TEXTAREA") {
			           return true;
			       }else{ 
				       return false;
				   }  
		       }  
		  }else {  
		       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
		       code = event.keyCode;  
		       if (code == 13) {  
		           tag = target.tagName;  
		           if (tag == "INPUT"){ 
			           return false; 
			       }else {
				        return true;
				   }   
		      }  
		 }  
	}
</script>
<div class="pageHeader">
	<form id="searchViewPaArSummaryForManageForm" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewPaArSummaryForManageList" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td> 
					<td><input
						type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)pa1301_searchPop('onkeyup');"/>
						</td>
						<td>
						<a class="btnLook" id="pa1301_searchPop" onclick="pa1301_searchPop()" href="#" lookupGroup="person">
						</a>
					</td>
						<td colspan="3">
							<input id="dwz.person.empInfo" name="empInfo" type="text" readonly lookupGroup="person" size="60" value="${empInfo}"/>
							<%-- ${empInfoShow } --%>
						</a>
					</td>
					</tr><tr>
					<td><!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" />:</td>
					<td>
						<select id="PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO">
							<c:forEach items="${paPayScheduleList}" var="paySchedule" varStatus="i">
								<c:choose>
									<c:when test="${PAY_SCHEDULE_NO == paySchedule.PAY_SCHEDULE_NO }">
										<option syslong="${paySchedule.PA_CONFIRM_FLAG }" value="${paySchedule.PAY_SCHEDULE_NO }" selected="selected">${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
									</c:when>
									<c:otherwise>
										<option syslong="${paySchedule.PA_CONFIRM_FLAG }"  value="${paySchedule.PAY_SCHEDULE_NO }" >${paySchedule.PAY_DATE } ${paySchedule.SALARY_DISTIN }</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td><!--考勤汇总项目--><spring:message code="ar.viewPaArSummaryForManageList.KAOQINHUIZONGXIANGMU.b" />:</td>
					<td>
						<ait:selectCodeMultiArSummary id="seach_AR_SUMMARY_ITEM" name="seach_AR_SUMMARY_ITEM_NAME"  selected="${AR_SUMMARY_ITEM}" selectedNm="${AR_SUMMARY_ITEM_NAME}"/>
						<img alt="clear" src="/resources/images/newImages/Modify_little.gif" style="vertical-align:middle ;"
						onclick="$('input[name=seach_AR_SUMMARY_ITEM_NAME]',navTab.getCurrentPanel()).attr('value','');$('input[name=seach_AR_SUMMARY_ITEM]',navTab.getCurrentPanel()).attr('value','');">
					</td>	
				</tr>
                <tr>
                    <td><!--部门--><spring:message code="ess.infoApply.DEPT" />:</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewPa1301_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewPa1301_seachDept" selected="${DEPTNO}"/>
					</td>
					<td><!--是否例外--><spring:message code="ar.viewPaArSummaryForManageList.SHIFOULIWAI.b" />:</td>
					<td>
						<select id="isSpecialFlag" name="isSpecialFlag">
							<option value=""><!--全部--><spring:message code="org.title.ALL" /></option>
							<option value="Y" <c:if test="${isSpecialFlag == 'Y' }">selected</c:if> ><!--例外--><spring:message code="ar.viewPaArSummaryForManageList.LIWAI.b" /></option>
						</select>
					</td>
                </tr>
			</table>
			<div class="subBar">
				<ul>
					<%-- <li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div>
					</li> --%>
					<li>
						<a class="buttonActive" id="searchViewPaArSuForMaFormBut" href="#" >
							<span><!--检索--><spring:message code="ar.viewPaArSummaryForManageList.JIANSUO.b" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"  onclick="doUpdateArSummaryForManageAjax();" href="#" >
							<span><!--保存--><spring:message code="ess.message.save" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"  onclick="arMonthCal();" href="#" >
							<span><!--考勤汇总计算--><spring:message code="ar.viewPaArSummaryForManageList.KAOQINHUIZONGJISUAN.b" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="downloadExcel('searchViewPaArSummaryForManageForm','/pa/workManagement/viewPaArSummaryForManageExcel','/pa/workManagement/viewPaArSummaryForManageList')" href="#" >
							<span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="downloadExcel('searchViewPaArSummaryForManageForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=292&CPNY=${LoginUser.cpnyId}','/pa/workManagement/viewPaArSummaryForManageList')" href="#" >
							<span><!--导出例外数据--><spring:message code="ar.viewPaArSummaryForManageList.DAOCHULIWAISHUJU.b" /></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
<form id="updatePaArSummaryForManageInfo" method="post"
	action="/pa/workManagement/updatePaArSummaryForManageInfo"
	class="pageForm required-validate" style="overflow: hidden;">
	<input type="checkbox" id="arMonthCal_pa1301" name="processType" value="arMonthCal" checked="checked" style="display: none;"/>
	<table id="ViewPaArSummaryForManageTable" class="orderList"  width="100%">
		<thead>
			<tr>
				<th></th>
				<th width="10px;">
					<input type="checkbox" id="viewCheckGroup" />
				</th>
				<th width="30px;">No.</th>
				<th width="50px;"><!--工号--><spring:message code="ess.infoApply.EMP_ID" /></th>
				<th width="50px;"><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
				<th width="120px;"><!--部门--><spring:message code="ess.infoApply.DEPT" /></th>
				<th width="120px;"><!--职级--><spring:message code="ess.infoApply.Rank" /></th>
				<th width="60px;"><!--入社日期--><spring:message code="ess.empInfo.date_of_agency" /></th>
				<th width="150px;"><!--考勤汇总项目--><spring:message code="ar.viewPaArSummaryForManageList.KAOQINHUIZONGXIANGMU.b" /></th>
				<th width="60px;"><!--开始日期--><spring:message code="ar.viewcycleparameter.title.kaishiriqi" /></th>
				<th width="50px;"><!--系统值--><spring:message code="ar.viewPaArSummaryForManageList.XITONGZHI.b" /></th>
				<th width="50px;"><!--例外值--><spring:message code="ar.viewPaArSummaryForManageList.LIWAIZHI.b" /></th>
				<th width="100px;"><!--备注--><spring:message code="ess.empInfo.remarks" /></th>
				<th width="110px;"><!--变更者--><spring:message code="org.title.UPDATED_IP" /></th>
				<th width="110px;"><!--变更时间--><spring:message code="org.title.UPDATE_DATE" /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${PaArSummaryForManageList}" var="arSummary"
			varStatus="i">
			
			<tr>
				<td style="text-align: center"></td>
				<td style="text-align: center;padding-top:7px;">
					<input type="checkbox" id="viewCheck_${i.count }" name="viewCheck" value="${i.count}"/>
					<input type="hidden" name="AR_SUMMARY_MANAGE_NO_${i.count }" value="${arSummary.AR_SUMMARY_MANAGE_NO }"/>
				</td>
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">${arSummary.EMPID}</td>
				<td style="text-align: center">${arSummary.LOCAL_NAME}</td>
				<td style="text-align: center">${arSummary.DEPTNAME}</td>
				<td style="text-align: center">${arSummary.POST_GRADE}</td>
				<td style="text-align: center">${arSummary.DATE_STARTED}</td>
				<td style="text-align: center">${arSummary.ITEM_NAME}</td>
				<td style="text-align: center">${arSummary.AR_START_DATE}</td>
				<td style="text-align: center">${arSummary.CAL_VALUE}</td>
				<td style="text-align: center">
					<input type="text"  name="FINAL_VALUE_${i.count }" onchange="$('#viewCheck_${i.count }',navTab.getCurrentPanel()).attr('checked','checked');"
						min="-9999" class="textInputNew" style="width: 100%"
						value="${arSummary.FINAL_VALUE }" size="4"/>
				</td>
				<td style="text-align: center">
					<input type="text"  name="REMARK_${i.count }" onchange="$('#viewCheck_${i.count }',navTab.getCurrentPanel()).attr('checked','checked');"
					value="${arSummary.REMARK }" class="textInputNew" style="width: 100%"/>
				</td>
				<td style="text-align: center">${arSummary.UPDATED_BY}</td>
				<td style="text-align: center">${arSummary.UPDATE_DATE}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>

</div>
