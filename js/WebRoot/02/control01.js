/**
 * 分支
 */
(function(){
	/**
		if(条件){
			//...
		}
		if(条件){
			//..
		}else if(条件){
			//..
		}else{
		}
	 */
	//实验
	var d = new Date();
	var time = d.getHours();
	if(time >= 18){
		document.write("<b>good evening</b>")
	}else{
		document.write(time)
	}
	//实验2
	if(time<10){
		document.write("<b>good ,morning</b>")
	}else if(time>=10 && time<16){
		document.write("<b>good ,day</b>")
	}else{
		document.write("<b>good evening</b>");
	}
	//实验3
	//三目运算符 (条件)?"成立":"失败"
	var str = "";
	str = (time>10)?"good ,day":"good evening";
	//alert(str);
	
	/**
	 * 	switch 后面的(n)可以是表达式,也可以是变量
		switch(n){
			case 1:
			    //代码
			   break;
			case 2:
			    //代码
			    break;
			default:
				//代码
				break;
		}
	 */
	document.write("<br>")
	theDay = d.getDay();
	switch(theDay){
		case 5:
			document.write("finally friday");
			break;
		case 6:
			document.write("super zhouliu");
			break;
		case 0:
			document.write("sleepy sunday");
			break ;
		default :
			document.write("xiwang 一个 zhoum");
	}
		
})()
































