<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
var zTree;
var demoIframe;
var setting = {

	view : {
		dblClickExpand : true,
		showLine : true,
		selectedMulti : false,
		expandSpeed : "fast"
	},
	check : {
		autoCheckTrigger : false,
		chkboxType : {
			"Y" : "s",
			"N" : "s"
		},
		chkStyle : "checkbox",
		enable : true,
		nocheckInherit : true,
		radionType : "level"
	},
	data : {
		key : {
			checked : "CHECKED",
			name : "DEPTNAME",
			open : "true"
		},
		simpleData : {
			enable : true,
			idKey : "DEPTNO",
			pIdKey : "PARENT_DEPT_NO",
			rootPId : ""
		}
	},
	callback : {
		onCheck : function(treeId, treeNode) {
			var t = $.fn.zTree
					.getZTreeObj("deptTree1_updateattendancekeeperdepttree");
			var nodes = t.getCheckedNodes();
			if (nodes.length > 0) {
				for ( var i = 0; i < nodes.length; i++) {
					if (i == 0) {
						document.getElementById("deptNosEdu").value = nodes[i].DEPTNO;
					} else {
						document.getElementById("deptNosEdu").value += ","
								+ nodes[i].DEPTNO;
					}
				}
			} else {
				document.getElementById("deptNosEdu").value = "";
			}

		}
	}
};

var zNodes;
$
		.ajax( {
			async : false,
			cache : false,
			type : 'POST',
			dataType : "json",
			url : "/edu/traineducation/getDeptTree?DES_DEPARTMENT=${planManagerInfo.DES_DEPARTMENT}",//请求的action路径  
			error : function() {//请求失败处理函数  
				//请求失败
				alert('<spring:message code="ar.alert.message.viewattendencekeeper.error"/>');
			},
			success : function(data) { //请求成功后处理函数。    
				zNodes = data; //把后台封装好的简单Json格式赋给treeNodes
			}
		});
// 初始调用
$(document)
		.ready(function() {
			//布局
				$("#layout1").ligerLayout( {
					leftWidth : 180
				});
				var t = $("#deptTree1_updateattendancekeeperdepttree");
				t = $.fn.zTree.init(t, setting, zNodes);
				var nodes = t.getCheckedNodes();
				if (nodes.length > 0) {
					for ( var i = 0; i < nodes.length; i++) {
						if (i == 0) {
							document.getElementById("deptNosEdu").value = nodes[i].DEPTNO;
						} else {
							document.getElementById("deptNosEdu").value += ","
									+ nodes[i].DEPTNO;
						}
					}
				} else {
					document.getElementById("deptNosEdu").value = "";
				}

				//是否需要评价
				var isnot_evaluate = "${planManagerInfo.ISNOT_EVALUATE}";
				if (isnot_evaluate != '0' && isnot_evaluate != '') {
					var str = '';
					var array = isnot_evaluate.split(",");
					for ( var i = 0; i < array.length; i++) {
						if (array[i] == '1') {
							str = str + '<spring:message code="edu.planManager.XUEYUANPINGJIA.a"/>' + ",";//学员评价
						} else if (array[i] == '2') {
							str = str + '<spring:message code="edu.planManager.JIANGSHIPINGJIA.a"/>' + ",";//讲师评价
						} else if (array[i] == '3') {
							str = str + '<spring:message code="edu.planManager.PEIXUNPINGJIA.a"/>' + ",";//培训评价
						}
					}
					str = str.substring(0, str.length - 1);
					$('#ISNOT_EVALUATE').html(str);
				} else {
					$('#ISNOT_EVALUATE').html('<spring:message code="ar.viewcycle.content.no"/>');//否
				}
			});
</script>
<div class="pageContent" layoutH="10">
	<input type="hidden" name="PLAN_NO" id="PLAN_NO"
		value="${planManagerInfo.PLAN_NO }">
	<div class="pageFormContent nowrap">
		<table id="eduTable" class="user_table" width="100%" border="1"
			cellpadding="2" cellspacing="1">
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="empsubject.subjectNm"/><!--课程名称
		-->
				</td>
				<td class="td_type" width="20%">
					<span>${planManagerInfo.TRAIN_TYPE_CODE_NAME
						}&nbsp&nbsp${planManagerInfo.COURSE_NUMBER
						}&nbsp&nbsp${planManagerInfo.COURSE_NAME_CODE }</span>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.JIHUAKAISHISHIJIAN.a"/><!--计划开始时间-->
				</td>
				<td class="td_type" width="20%">
					<span>${planManagerInfo.PLAN_STARTDATE }</span>
				</td>
			</tr>

			<tr>
				<td class="td_title" width="1%">
						<spring:message code="edu.planManager.JIHUAJIESHUSHIJIAN.a"/><!--计划结束时间-->

				</td>
				<td class="td_type" width="20%">
					<span>${planManagerInfo.PLAN_ENDDATE }</span>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时
			-->
				</td>
				<td class="td_type" width="20%">

					<c:if test="${planManagerInfo.CLASS_UNIT=='0' }">
						<span>${planManagerInfo.CLASS_HOUR }&nbsp<spring:message code="display.mutual.month"/><!--月--></span>
					</c:if>
					<c:if test="${planManagerInfo.CLASS_UNIT=='1' }">
						<span>${planManagerInfo.CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.day"/><!--天--></span>
					</c:if>
					<c:if test="${planManagerInfo.CLASS_UNIT=='2' }">
						<span>${planManagerInfo.CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.hour"/><!--小时--></span>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="hrm.empinfo.Training_form"/><!--培训形式
			-->
				</td>
				<td class="td_type" width="20%">
					<span>${planManagerInfo.TRAIN_FORM_CODE_NAME }</span>
				</td>
			</tr>
			<%-- <tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.SHIFOUXUYAOKAOSHI.a"/><!--是否需要考试
			-->
				</td>
				<td class="td_type" width="20%">
					<c:if test="${planManagerInfo.ISNOT_TEST=='Y' }">
						<span><spring:message code="ar.viewcycle.content.yes"/><!--是
			--></span>
					</c:if>
					<c:if test="${planManagerInfo.ISNOT_TEST=='N' }">
						<span><spring:message code="ar.viewcycle.content.no"/><!--否
			--></span>
					</c:if>
				</td>
			</tr> --%>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.SHIFOUKESHENQING.a"/><!--是否可申请
			-->
				</td>
				<td class="td_type" width="20%">
					<c:if test="${planManagerInfo.ISNOT_APPLY=='Y' }">
						<span><spring:message code="ar.viewcycle.content.yes"/><!--是
			--></span>
					</c:if>
					<c:if test="${planManagerInfo.ISNOT_APPLY=='N' }">
						<span><spring:message code="ar.viewcycle.content.no"/><!--否
			--></span>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.ZHIDINGBUMEN.a"/><!--指定部门
			-->
				</td>
				<td class="td_type" width="20%">
					<ul id="deptTree1_updateattendancekeeperdepttree" class="ztree"></ul>
					<input type="hidden" name="DES_DEPARTMENT" id="deptNosEdu"
						value="${planManagerInfo.DES_DEPARTMENT}" />
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.ZHIDINGRENYUAN.a"/><!--指定人员
			-->
				</td>
				<td class="td_type" width="1%">
					<span>${DES_EMPLOYEE_NAME}</span>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.YUSUANFEIYONG.a"/><!--预算费用
			-->	
				</td>
				<td class="td_type" width="20%">
					<c:if test="${planManagerInfo.ISNOT_APPLY=='Y' }">
						<span>${planManagerInfo.BUDGET }(<spring:message code="edu.planManager.KEJIAN.a"/><!--可见
			-->	)</span>
					</c:if>
					<c:if test="${planManagerInfo.ISNOT_APPLY=='N' }">
						<span>${planManagerInfo.BUDGET }(<spring:message code="edu.planManager.BUKEJIAN.a"/><!--不可见
			-->)</span>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.ZHUGUANBUMEN.a"/><!--主管部门
			-->
				</td>
				<td class="td_type" width="20%">
					<c:if test="${planManagerInfo.DEPART_MANA_CODE_NAME==null }">
						<span>${planManagerInfo.DEPART_MANA_CODE }</span>
					</c:if>
					<c:if test="${planManagerInfo.DEPART_MANA_CODE_NAME!=null }">
						<span>${planManagerInfo.DEPART_MANA_CODE_NAME }</span>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.JIANGSHI.a"/><!--讲师
			-->
				</td>
				<td class="td_type" width="20%">
					<span>${TEACHER_NAME }</span>
				</td>
			</tr>
			<td class="td_title" width="1%">
				<spring:message code="empsubject.eduRm"/><!--培训地点
			-->
			</td>
			<td class="td_type" width="20%">
				<span>${planManagerInfo.TRAIN_ADDRESS }</span>
			</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.PEIXUNRENSHU.a"/><!--培训人数
			-->
				</td>
				<td class="td_type" width="20%">
					<span>${planManagerInfo.TRAIN_PERSON_COUNT }</span>
				</td>
			</tr>
		    <tr>
				<td class="td_title" width="1%"><spring:message code="ar.viewarcardrecord.title.beizhu"/><!--备注
			--></td>
				<td class="td_type"  width="20%" >
				     <span>${planManagerInfo.TRAIN_PERSON_REMARK }</span>
				</td>
		    </tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.SHIFOUXUYAOPINGJIA.a"/><!--是否需要评价
			-->
				</td>
				<td class="td_type" width="20%">
					<span id="ISNOT_EVALUATE"></span>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.SHIFOUXUYAOPEIXUNBAOGAO.a"/><!--是否需要培训报告
			-->
				</td>
				<td class="td_type" width="20%">
					<c:if test="${planManagerInfo.ISNOT_REPORT=='Y' }">
						<span><spring:message code="ar.viewcycle.content.yes"/><!--是
			--></span>
					</c:if>
					<c:if test="${planManagerInfo.ISNOT_REPORT=='N' }">
						<span><spring:message code="ar.viewcycle.content.no"/><!--否
			--></span>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.SHIFOUXUYAOPEIXUNXIEYI.a"/><!--是否需要培训协议
			-->
				</td>
				<td class="td_type" width="20%">
					<c:if test="${planManagerInfo.ISNOT_AGREEMENT=='Y' }">
						<span><spring:message code="ar.viewcycle.content.yes"/><!--是
			--></span>
					</c:if>
					<c:if test="${planManagerInfo.ISNOT_AGREEMENT=='N' }">
						<span><spring:message code="ar.viewcycle.content.no"/><!--否
			--></span>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!--附加文件
			-->
				</td>
				<td class="td_type" width="20%">
					<c:forEach items="${planManagerInfo.fileList}" var="item"
						varStatus="i">
						<span style="color: blue;">${i.count }.</span>
						<a style="color: blue;"
							href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}&nbsp&nbsp</a>
					</c:forEach>

				</td>

			</tr>

			<tr>
				<td class="td_title" width="1%">
					<spring:message code="edu.planManager.DAORUKECHENGBIAO.a"/><!--导入课程表
			-->
				</td>
				<td class="td_type" width="20%">
				   <a style="color:blue;" id="course_id"  href="/edu/traineducation/queryCourseSyllabus2?PLAN_NO=${planManagerInfo.PLAN_NO }" target="dialog" mask="true"  rel="course_id" width="600" height="400"><spring:message code="edu.planManager.KECHENGBIAODAORUQINGKUANG.a"/><!--课程表导入情况
			--></a>
				</td>
			</tr>

			
		</table>
	</div>
	<%-- <div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div> --%>
</div>
