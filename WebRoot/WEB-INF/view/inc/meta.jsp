<!--META-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--JS-->
<script language="javascript" src="/WEB-INF/view/hrm/js/meizzDate.js"></script>
<script language="javascript" src="/WEB-INF/view/hrm/js/commFuncs.js"></script>
<!--CSS-->
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/xjos.css" rel="stylesheet" type="text/css">
<link href="../css/paging.css" rel="stylesheet" type="text/css">
<link href="../css/extremeTableStyles/tzone.css" rel="stylesheet" type="text/css">

<div id="loading">
	<div class="loading-indicator">
		loading...........
	</div>
</div>
<script type="text/javascript">
if (window.attachEvent) 
{   
   window.attachEvent("onload", delNode);   
} 
else if (window.addEventListener) 
{   
   window.addEventListener("load", delNode, false);    
}

function  delNode()
{   
  var nodeId = "loading";
  try
  {   
	  var div =document.getElementById(nodeId);  
	  if(div !==null)
	  {
		  document.body.removeChild(div);
		  div=null;    
 	  }  
  }
  catch(e)
  {   
  	   alert("loading..... Error!");
  }   
}
</script>



