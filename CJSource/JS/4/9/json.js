var obj={
	name:"CJ",
	age:18,
	items:[1,2,3,4]
};

//HTML DOM
var img={
	title:"ABC",
	src:"a.jpg",
	alt:"ABC"
};
//<a t="1" n="北京辖区" id="A2" own="A1"/>
var a={
	t:1,
	n:"北京辖区",
	id:"A2",
	own:"A1"
};




var Students=[];
Students[1]={name:"CJ",age:18};

//JSON String
Base.ajax({
	url:"json.php",
	data:{name:"CJ",age:18},
	success:function (txt) {
		/*var data=eval("("+txt+")");
		alert(data.name);*/
		alert(txt);
	}
});


//{} 语句块
//{} 对象字面量
var json='{"name":"CJ","age":18}';
/*//var data=eval("("+json+")");
data =(new Function("","return "+json))();
alert(data.name);*/
/*var data=JSON.parse(json);
alert(data.name);*/
alert(JSON.stringify(a));