// JavaScript Document
window.onload=function(){
	//index_topcn1
	var content1= $('.index_topcn1').height();
    var bar1=$('.index_topcn2').height();
    if(content1>bar1){
        $('.index_topcn2').height(content1);
    }else{
        $('.index_topcn1').height(bar1);    
    }
	//index_topcn2
	var content2= $('.index_topcn2').height();
    var bar2=$('.index_topcn3').height();
    if(content2>bar2){
        $('.index_topcn3').height(content2);
    }else{
        $('.index_topcn2').height(bar2);    
    }
	//index_topcn3
	var content3= $('.index_topcn3').height();
    var bar3=$('.index_topcn1').height();
    if(content3>bar3){
        $('.index_topcn1').height(content3);
    }else{
        $('.index_topcn3').height(bar3);    
    }
	//index_centercn1
	var content4= $('.index_centercn1').height();
    var bar4=$('.index_centercn2').height();
    if(content4>bar4){
        $('.index_centercn2').height(content4);
    }else{
        $('.index_centercn1').height(bar4);    
    }
};