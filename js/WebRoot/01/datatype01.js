/**
 * 数据类型和类型转换
 */
(function(){
/**
 * 基本数据类型(3种)
 * (1)数字 number
 *   例如 3.1415927 ,0.251,.2,100,1.478E
 * (2)字符串
 *   string
 * (3)布尔 booble
 */
 //数字型转字符串
 var num1 = 3.1415927;
 var str1 = Number.toString(num1); 
 document.write(typeof str1 == "string");//true
 document.write("<br>")
 //四舍五入
 var num2 = num1.toFixed(2);
 document.write(num2);
 document.write("<br>")
 //返回指定的为数的数字
 var num3 = num1.toPrecision(4);
 document.write(num3);
 document.write("<br>")
 //(Math) 介绍一点方法
 //四舍五入round
 document.write(Math.round(4.7));
 document.write("<br>")
 //随机出处理0~1
 document.write(Math.random());
 document.write("<br>")
 //0~10的随机数
 document.write(Math.floor((Math.random()*11)));
 document.write("<br>")
 document.write("-------------------------------<br>")
 
 //字符串
 //注意(转义) pca't  要输入 pca\'t \n 换行 
 /**
	\' \" \& 和好+ \\ \n \r 回车 
	\t \b \f 换页
  */
 //属性 length indexof substring chartAt(整数)
 //如何转成数字
 var str2 = "USPCAT.COM";
 var str3 = "3.14";
 var number = Number(str3);
 document.write(typeof number == "number");
 document.write("<br>")
 document.write((str2 - 0)+"<br>");//NaN 非数值
 document.write((str3 - 1)+"<br>");//如果是减法他回自动将字符串转成数字
 document.write((str3 + 1)+"<br>");//加法会当成字符串的拼接操作
 //布尔类型(boolean)
 //true | false
 var s = "";
 var o = {};//true
 var l = [];//true
 var n = null;
 var f = false;
 var u = undefined;
 document.write("-------------------------------<br>")
 if(!s){
 	document.write("s is false<br>")
 }
 if(!o){
 	document.write("o is false<br>")
 }
 if(!l){
 	document.write("l is false<br>")
 }
 if(!n){
 	document.write("n is false<br>")
 } 
 if(!f){
 	document.write("f is false<br>")
 }
 if(!u){
 	document.write("u is false<br>")
 }
 /**
s is false
f is false
u is false
n is false
  */
 if(str != "" && str != null && str != undefined){
 	//...
 }
 if(str){
 	//...
 }
 /**
  * 2复合类型
  * (1)数组-->有序的集合(array):下标(index) 是从0开始的
  * 例子
  * var arr = new Array();
  * (2)特殊的对象-->函数(function)
  */
 /**
  * 特殊值
  * (1)null 不是有效的对象\数组\数组\字符串  他们为空 
  * (2)undefined 他是代表没有定义 和空不是一个概念
  * [没有] 但是有容易 有一个盒子但是盒子里卖没有东西
  * undefined 连盒子也没有
  */
 /**
  * 内置特殊对象
  * Data对象
  * Error错误对象
  * ReExp对象
  */
})()






