
function checkAll(theForm) { // check all the checkboxes in the list
  for (var i=0;i<theForm.elements.length;i++) {
    var e = theForm.elements[i];
		var eName = e.name;
    	if (eName != 'allbox' && 
            (e.type.indexOf("checkbox") == 0)) {
        	e.checked = theForm.allbox.checked;		
		}
	} 
}
//elemName checkbox elements index checked true
function setCheckboxChecked(elemName,index){
  var ckElems = document.getElementsByName(elemName);
  if (ckElems != null && ckElems.length != null &&
    index >=0 && index < ckElems.length){
    ckElems(index).checked=true;
  }
}

//dateStr
//dateStr YYYY-MM-DD
//YYYY-MM-DD
function addMonth(dateStr,offset){
  var year = parseInt(dateStr.substring(0,4));
  var month = dateStr.substring(5,7);
  if (month.substring(0,1)=='0')
  	month = parseInt(month.substring(1,2));
  else
    month = parseInt(month);
  var day = dateStr.substring(8,10);
  year = (month+offset>12? year+Math.floor((month+offset)/12):year).toString();
  month = month+offset>12?(month+offset)%12:month+offset;
  if (month<10)
    month = '0'+month.toString();
  else
    month = month.toString();
  return year + '-' + month + '-' + day;
}




// validate repetition
function validateRept(name, separator, message)
{
	validateRept(name, separator, message, null);
}

// validate repetition
function validateRept(name, separator, message, specialSeptr)
{
	var arrayData = getArray(name, separator);
	var existData = null;
	
	// get exist data
	if(specialSeptr != null)
		existData = getArray(name, separator+specialSeptr);

	if(arrayData == null || arrayData.length == 0)
		return true;

	for(i=1;i<=arrayData.length;i++){

		if(document.getElementById(name + separator + i).value == "")
			continue;
						
		// delete current element
		var tempArray = arrayData.del(i-1);

		// search index in other array element
		var result = searchIndex(tempArray, document.getElementById(name + separator + i).value);

		if (result == -1 && existData != null && existData.length != 0) {
			
			result = searchIndex(existData, document.getElementById(name + separator + i).value);
		}

		if( result > -1) {
			
			document.getElementById(name + separator + i).focus();
			alert(message);
			return false;
		}
		
	}
	
	return true;
}

// get array in form
function getArray(name, separator)
{
	var arrayData = new Array();
	for(i=1;;i++){
		
		if(document.getElementById(name + separator + i) == null)
			break;
		arrayData[i-1] = document.getElementById(name + separator + i).value;
	}
	return arrayData;
}

// search index of key in array 
function searchIndex(arrayData, key)       
{
  var reg = new RegExp(key,[""]);
  var strData = arrayData.toString().replace(reg,"┢").replace(/[^,┢]/g,"");

  return strData.indexOf("┢");

}

//n表示第几项,从0开始算起
Array.prototype.del=function(n) {  
  
  //prototype为对象原型,注意这里为对象增加自定义方法的方法
  if(n<0)  //如果n<0,则不进行任何操作
    return this;
  else
    return this.slice(0,n).concat(this.slice(n+1,this.length));
    /*
      concat方法：返回一个新数组，这个新数组是由两个或更多数组组合而成的。
      　　　　　　这里就是返回this.slice(0,n)/this.slice(n+1,this.length)
     　　　　　　组成的新数组，这中间，刚好少了第n项。
      slice方法： 返回一个数组的一段，两个参数，分别指定开始和结束的位置。
    */
}

function efocus(event) {
    var elem = Event.element(event);
    elem.style.border = '1 solid #9e9e9e';
}

function eblur(event) {
    var elem = Event.element(event);
    elem.style.border = '1px solid #cccccc';
}

function ekeydown(event) {
    if (event.keyCode == Event.KEY_RETURN)
        event.keyCode = Event.KEY_TAB;
}
  String.prototype.trim = function(){ return this.replace(/^\s+|\s+$/g,"")}
  String.prototype.ltrim = function(){ return this.replace(/^\s+/g,"")}
  String.prototype.rtrim = function(){ return this.replace(/\s+$/g,"")}
  
/*整除函数*/
function Div(exp1, exp2)
{
    var n1 = Math.round(exp1); //四舍五入
    var n2 = Math.round(exp2); //四舍五入
    
    var rslt = n1 / n2; //除
    
    if (rslt >= 0)
    {
        rslt = Math.floor(rslt); //返回值为小于等于其数值参数的最大整数值。
    }
    else
    {
        rslt = Math.ceil(rslt); //返回值为大于等于其数字参数的最小整数。
    }
    
    return rslt;
}

 /**
得到浏览器视区的高度
 */
function getViewportHeight() {
	if (window.innerHeight!=window.undefined) return window.innerHeight;
	if (document.compatMode=='CSS1Compat') return document.documentElement.clientHeight;
	if (document.body) return document.body.clientHeight; 
	return window.undefined; 
}

/*设置DIV区域的高度*/
function setDivHeight(id, td_size, top_size, bottom_size) {

	var height_all = getViewportHeight();
	var temp = Div((height_all - top_size - bottom_size), td_size);
	document.getElementById(id).style.height = td_size * temp;
}

/////////////////////////////////KEB//////////////////////////////////////////

function hidechoose(){
window.setTimeout("hide()",100);
}
function hide(obj){
obj.style.visibility = "hidden";
}

function ShowBlankObject(obj) 
{ 
 if(obj.style.visibility == "hidden") 
 { 
  obj.style.visibility = "visible"; 
 } 
 else 
 { 
  obj.style.visibility = "hidden"; 
 } 
 }

function Show(obj) 
{ 
   obj.style.visibility = "visible"; 
}
function cssChange(node) {
    var ul = document.getElementById("nav_menuul");
    var children = ul.childNodes;
    for (var i=0;i<children.length;i++) {
        children[i].className = "lioff";
    }
    while (!node.className || node.className != "lioff") {
        node = node.parentNode;
    }
    node.className = "lion";
}
function cssChange1(node) {
    var ul = document.getElementById("main_nav");
    var children = ul.childNodes;
    for (var i=0;i<children.length;i++) {
        children[i].className = "lioff";
    }
    while (!node.className || node.className != "lioff") {
        node = node.parentNode;
    }
    node.className = "lion";
}
function showtype(id,obj) 
{
	var div = document.getElementById(id);
    var children = div.childNodes;
    for (var i=0;i<children.length;i++) {
        children[i].style.display = "none";
    } 
	document.getElementById(obj).style.display = "";
}

////////////////////////////////
function band(backColor,textColor,i,idName)
{
	var t;
	if(typeof(preEl)!='undefined')
	{
	preEl.className=orgBColor;

	try{ChangeTextColor(preEl,orgTColor);}catch(e){;}
	}
	var el = event.srcElement;
	el = el.parentElement;
	orgBColor = el.className;
	orgTColor = backColor;
	el.className = backColor;
	try{ChangeTextColor(el,textColor);}catch(e){;}
	preEl = el;
	if(document.getElementById(idName)){
		document.getElementById(idName).value = i;	
	}
}

function ChangeTextColor(a_obj,a_color)
{
	for (i=0;i<a_obj.cells.length;i++){
		a_obj.cells(i).className=a_color;
	}
}
//身份证正则表达式(15位) 
isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/; 
//身份证正则表达式(18位) 
isIDCard2=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
var 
//aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"} 
aCity={
11:"100000",
12:"300000",
13:"hebei",
14:"shanxi1",
15:"neimenggu",
21:"liaoning",
22:"jilin",
23:"heilongjiang",
31:"200000",
32:"jiangsu",
33:"zhejiang",
34:"anhui",
35:"fujian",
36:"jiangxi",
37:"shangdong",
41:"henan",
42:"hubei",
43:"hunan",
44:"guangdong",
45:"guangxi",
46:"hainan",
50:"400000",
51:"sichuan",
52:"guizhou",
53:"yunnan",
54:"xizang",
61:"shanxi3",
62:"gansu",
63:"qinghai",
64:"ningxia",
65:"xinjiang",
71:"taiwan",
81:"xianggang",
82:"aomeng",
91:"guowai"
}
var sBirthday;
var Errors=new Array(
        "身份证号码错误!", 
        "身份证地区非法!" ,
        "身份证号码出生日期超出范围或含有非法字符!", 
        "身份证号码校验错误!"
);
function checkIdCard(sId){
var iSum=0 ;
var info="";
if(!/^\d{17}(\d|x)$/i.test(sId))return 0; 
sId=sId.replace(/x$/i,"a"); 
if(aCity[parseInt(sId.substr(0,2))]==null)return 1; 
sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
var d=new Date(sBirthday.replace(/-/g,"/")) 
if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return 2; 
for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) 
if(iSum%11!=1)return 3; 
return 5;
}
function trim(str) {
    if (str != null) {
        var i; 
        for (i=0; i<str.length; i++) {
            if (str.charAt(i)!=" ") {
                str=str.substring(i,str.length); 
                break;
            } 
        } 
    
        for (i=str.length-1; i>=0; i--) {
            if (str.charAt(i)!=" ") {
                str=str.substring(0,i+1); 
                break;
            } 
        } 
        
        if (str.charAt(0)==" ") {
            return ""; 
        } else {
            return str; 
        }
    }
}
