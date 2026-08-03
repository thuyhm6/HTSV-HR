
var pee = -326
var drec = 10;
var speed = 15;
var l = pee;

function Proj7GlideBack () {
	l += drec;
	if(document.layers){
		document.gbar.right = l;
	}else if(document.all){
		document.all.gbar.style.pixelRight = l;
	}else if(document.getElementById){
		document.getElementById('gbar').style.right = l + 'px';
	}
	if (l < 0){
    setTimeout('Proj7GlideBack()', speed);
	}else{
	  if (document.layers) {
      var html = '';
      html += '<A HREF="javascript:;"';
      html += 'onClick="Proj7GlideOut(); return false;"';
      html += 'Class="glideText"';
      html += 'close'+ '<br>';
      html += 'close'+ '<Br>';
      html += '<\/A>';
      var a = window.document.gbar.document.glider;
      a.document.open();
      a.document.write(html);
      a.document.close();
    }else if (document.all){
    	document.all.glidetextLink.innerHTML = '<img src="/resources/images/button/close.jpg" style="cursor:hand;">';
    	document.all.glidetextLink.onclick = moveIn;
    }else if(document.getElementById) {
      document.getElementById('glidetextLink').firstChild.nodeValue ='<img src="/resources/images/button/close.jpg" style="cursor:hand;">';
      document.getElementById('glidetextLink').onclick = moveIn;
    }
  }
}

function Proj7GlideOut () {
	l -= drec;
	if(document.layers){
		document.gbar.right = l;
	}else if(document.all){
		document.all.gbar.style.pixelRight = l;
	}else if(document.getElementById){
		document.getElementById('gbar').style.right = l + 'px';
	}
  if(l > pee){
	  setTimeout('Proj7GlideOut()', speed);
  }else {
    if (document.layers) {
      var html = '';
      html += '<A HREF="javascript:;"';
      html += 'onclick="Proj7GlideBack(); return false;"';
      html += 'Class="glideText"';
      html += 'menu'+ '<Br>';
      html += 'menu'+ '<Br>';
      html += '<\/A>';
      var a = window.document.gbar.document.glider;
      a.document.open();
      a.document.write(html);
      a.document.close();
    }
    else if (document.all) {document.all.glidetextLink.innerHTML = '<img src="/resources/images/button/open.jpg" style="cursor:hand;">';
      document.all.glidetextLink.onclick = moveOut;
    }
    else if (document.getElementById) {
      document.getElementById('glidetextLink').firstChild.nodeValue ='<img src="/resources/images/button/open.jpg" style="cursor:hand;">';
      document.getElementById('glidetextLink').onclick = moveOut;
    }
  }
}
function moveIn(){
	Proj7GlideOut();
	return false;
}
function moveOut(){
	Proj7GlideBack();
	return false;
}

if(document.layers) {
	origWidth = innerWidth;
	origHeight = innerHeight;
}
function reDo() {
	if(innerWidth != origWidth || innerHeight != origHeight)
	location.reload();
}
if(document.layers){ 
	onresize = redo;
} 