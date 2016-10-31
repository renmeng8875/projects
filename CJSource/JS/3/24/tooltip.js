(function () {


function ToolTip(args) {
	//args 一个对象
	/*
	{
		title:"",
		node:node,
		className:"redStyle",
		maxWidth:200,//最大宽度
	}
	*/
	//title具有默认值
	//className是可选属性
	this.node=args.node;
	this.title=args.title || args.node.getAttribute("title");
	this.node.removeAttribute("title");
	
	
	this.layer=document.createElement("div");
	this.layer.innerHTML=this.title;
	this.layer.className=["tooltip",args.className].join(" ");
	if (args.maxWidth) {
		this.layer.style.maxWidth=args.maxWidth+"px";
	}
	
	this.hide();
	document.body.appendChild(this.layer);
	
	var _this=this;
	addEvent(this.node,"mouseover",function (evt) {
		_this.show(evt);
	});
	
	addEvent(this.node,"mouseout",function (evt) {
		_this.hide();
	});
}

ToolTip.offset=20;
ToolTip.prototype={
	show:function (evt) {
		var x=evt.pageX,
				y=evt.pageY,
				//页面视口大小,viewportSize
				dw=document.documentElement.clientWidth+document.documentElement.scrollLeft,
				dh=document.documentElement.clientHeight+document.documentElement.scrollLeft;
				
		x+=ToolTip.offset;
		y+=ToolTip.offset;
		
	//	afdsa
		
		this.layer.style.left=x+"px";
		this.layer.style.top=y+"px";
		
		if (x+this.layer.clientWidth >dw) {
			x-=this.layer.clientWidth-ToolTip.offset*2;
		}
		
		
		if (y+this.layer.clientHeight>dh) {
			y-=this.layer.clientHeight-ToolTip.offset*2;
		}
		
		this.layer.style.left=x+"px";
		this.layer.style.top=y+"px";
		
		//document.body.appendChild(this.layer);
		this.layer.style.visibility="visible";
	},
	hide:function () {
		//this.layer.style.display="none";//因为页面布局被改变,页面就会重绘
		this.layer.style.visibility="hidden";//只重绘部分元素
		
		//this.layer.parentNode.removeChild(this.layer);
	}
};

addEvent(window,"load",function () {
	var link=$("tipLink");
	var tip2=$("link2");
	var t=new ToolTip({
				node:link,
				title:"<h3>ToolTip</h3><img style='width:200px' src='images/mj.jpg' />"
			});
			
			
			
			
	var t2=new ToolTip({
			node:tip2,
			className:"redStyle"
		});
		
	var t3=new ToolTip({
			node:$("link3"),
			maxWidth:100
		});
});
	
	
	
	
	
})();