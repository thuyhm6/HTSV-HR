<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	CreateTree();
});

var myTree = null;			
function CreateTree() {
	myTree = new ECOTree('myTree','myTreeContainer');	
	myTree.config.colorStyle = ECOTree.CS_LEVEL;
	myTree.config.nodeFill = ECOTree.NF_FLAT;
	myTree.config.selectMode = ECOTree.SL_NONE;				
	//是否允许给节点加链接，是否允许给节点加图片
	myTree.config.useTarget = false;                
	myTree.config.useImg = true;       

	//设置节点的大小和间隔
	myTree.config.defaultNodeWidth = 95;
	myTree.config.defaultNodeHeight = 140;
	myTree.config.iSubtreeSeparation = 50;
	myTree.config.iSiblingSeparation = 15;
	myTree.config.iLevelSeparation = 30;

	//此处通过从数据库或其它地方读取节点信息，生成添加节点的代码
	//参数前三位是必须的；
	//第一位是本节点id，第二位是父节点id、根节点的父节点为-1，第三位为节点文本；
	//第四位为节点上显示的图片/照片、图片放到img下并在数据库中记录名称即可，未设参数则取默认图片；
	//第五位为超链接、最好是访问统一程序传入本节点id；
	//第六、七位为节点的个性化宽、高。
	myTree.add('01',-1,'<spring:message code="org.title.CEO" />','./img/0.jpg','http://www.jq-school.com');//总裁
	
	myTree.add('02','01','<spring:message code="org.title.technology_CEO" />','/resources/js/ecc/img/1.jpg');//技术副总裁
	myTree.add('03','01','<spring:message code="org.title.CEO_Assistant" />','/resources/js/ecc/img/2.jpg','http://www.jq-school.com',95,130);//总裁助理
	myTree.add('04','01','<spring:message code="org.title.branch_office" />','/resources/js/ecc/img/3.jpg','http://www.jq-school.com',95,130);//分公司

	myTree.add('0201','02','<spring:message code="org.title.TECH_MANAGER" />','/resources/js/ecc/img/4.jpg','http://www.jq-school.com',95,130);//技术经理
	myTree.add('0202','02','<spring:message code="org.title.SMT" />','/resources/js/ecc/img/5.jpg','http://www.jq-school.com',95,130);//技术员
	myTree.add('0301','03','<spring:message code="org.title.SEC" />','/resources/js/ecc/img/5.jpg','http://www.jq-school.com',95,130);//秘书
	myTree.add('0302','03','<spring:message code="org.title.PDA" />','/resources/js/ecc/img/6.jpg','http://www.jq-school.com',95,130);//助理
	myTree.add('0401','04','<spring:message code="org.title.general_manager" />','/resources/js/ecc/img/6.jpg','http://www.jq-school.com',95,130);//总经理
	myTree.add('0402','04','<spring:message code="org.title.finance" />','/resources/js/ecc/img/7.jpg','http://www.jq-school.com',95,130);//财务

	myTree.UpdateTree();
}		
</script>
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" onclick="openOnRight('/org/orgManage/viewAddResumeInfo?SEQ=0','viewResumeList_unit');" href="#">
					<span><spring:message code="org.title.INSERT" /><!-- 添加 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('viewAddResumeInfo',navTabAjaxDone)" href="#"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('viewAddResumeInfo',navTabAjaxDone)" href="#"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
			</li>
			<li>
				<a class="add" onclick="print();" href="#"><span><spring:message code="org.title.PRINT" /><!-- 打印 --></span></a>
			</li>
			<li>
				<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=5"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>					
			</li>
	</ul>
</div>
<div class="pageContent">
	<div id="myTreeContainer"></div>
</div>