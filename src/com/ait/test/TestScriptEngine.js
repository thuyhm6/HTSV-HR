var out = java.lang.System.out;
out.println("abc");
var str = java.lang.String;
str = ["abc","def","ghi"];
for(var i=0;i<str.length;i++){
	out.println(str[i]);
}
for(var s in str){
	out.println(s) ;
}
