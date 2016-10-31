(function () {
/**
args {
	size {
		minWidth
		minHeight
		width
		height
	}
	content		HTML/Node
	resizable	Boolean
	movable		Boolean
	closable	Boolean
	mask		Boolean
	maxable		Boolean
	title		HTML
	range		Node
	center		Boolean
}
*/
PopWindow.html='<div class="pop_window">'+
	'<h6 class="title_bar">窗口标题</h6>'+
	'<div class="window_content">'+
	'</div>'+
	'<a href="###" class="resize_knob" title="调整大小"></a>'+
	'<a href="###" class="maximize_button" title="最大化">□</a>'+
	'<a href="###" class="close_button" title="关闭">×</a>'+
'</div>';
var _div=document.createElement("div");
_div.innerHTML=PopWindow.html;
PopWindow.node=_div.firstChild;

PopWindow.mask=document.createElement("div");
PopWindow.mask.id="pop_window_mask";
PopWindow.zIndexCounter=200;
addEvent(window,"load",function () {
	document.body.appendChild(PopWindow.mask);
});
addEvent(window,"resize",function () {
	PopWindow.resizeMask();
});


function PopWindow(args) {
	DND.init.call(PopWindow,this,args);
	this.node=PopWindow.node.cloneNode(true);
	this.titleBar=getElementsByClassName("title_bar",this.node)[0];
	this.container=getElementsByClassName("window_content",this.node)[0];
	this.closeButton=getElementsByClassName("close_button",this.node)[0];
	this.resizeKnob=getElementsByClassName("resize_knob",this.node)[0];
	this.maximizeBtn=getElementsByClassName("maximize_button",this.node)[0];
	
	
	if (typeof this.content=="string") {
		this.container.innerHTML=this.content.trim();
	} else {
		this.container.appendChild(this.content);
	}
	
	this.titleBar.innerHTML=this.title;
	
	var $this=this;
	
	if (!this.closable) {
		this.closeButton.style.display="none";
	} else {
		addEvent(this.closeButton,"click",function () {
			$this.close();
		});
	}
	
	if (!this.maxable) {
		this.maximizeBtn.style.display="none";
	} else {
		var toggle=function () {
			$this.toggleSize();
		}
		addEvent(this.maximizeBtn,"click",toggle);
		addEvent(this.titleBar,"dblclick",toggle);
		
	}
	
	if (!this.resizable) {
		this.resizeKnob.style.display="none";
	} else {
		this.resizeDND=new DND({
			layer:this.resizeKnob,
			range:this.range,
			onDrag:function (evt,dnd,pos) {
				//return false;
				var w_pos=getOffset($this.node);
				evt.preventDefault();
				$this.resizeTo(pos.x-w_pos.x+dnd.layer.offsetWidth,
					pos.y-w_pos.y+dnd.layer.offsetHeight);
				return false;
				
			}
		});
	}
	
	if (!this.movable) {
		this.titleBar.style.cursor="default";
	} else {
		this.DNDInstance=new DND({
			layer:this.node,
			handle:this.titleBar,
			onDragStart:function (evt,dnd) {
				dnd.layer.style.zIndex=PopWindow.zIndexCounter++;
			}
		});
	}
	
	if (this.center) {
		addEvent(window,"resize",function () {
			if ($this.isVisible)$this.moveTo("center");
		});
	}
	
	this.width=this.width ||this.minWidth;
	this.height=this.height ||this.minHeight;
	
	this.node.style.display="none";
	this.node.style.width=typeof this.width=="number"?this.width+"px":this.width;
	this.node.style.height=typeof this.height=="number"?this.height+"px":this.height;
	
	
	
	document.body.appendChild(this.node);
	
}
extend(PopWindow,{
	defaultArgs:{
		resizable:true,
		closable:true,
		mask:true,
		movable:true,
		title:"Window",
		minWidth:400,
		minHeight:250,
		maxable:true,
		center:true
	},
	resizeMask:function () {
		var mask=PopWindow.mask;
		var de=document.documentElement;
		de.style.overflow="hidden";
		mask.style.width=de.clientWidth+"px";
		mask.style.height=de.clientHeight+"px";
		de.style.overflow="auto";
	}
});
PopWindow.prototype={
	close:function () {
		this.node.style.display="none";
		if (this.mask) {
			PopWindow.mask.style.display="none";
		}
		this.isVisible=false;
	},
	toggleSize:function () {
		if (this.isMaximize) {
			this.resizeBack();
		} else {
			this.resizeMax();
		}
	},
	hide:function () {
		this.close();
	},
	show:function () {
		if (this.mask) {
			var mask=PopWindow.mask;
			PopWindow.resizeMask();
			mask.style.display="block";
			mask.style.zIndex=PopWindow.zIndexCounter++;
		}
		this.node.style.zIndex=PopWindow.zIndexCounter++;
		this.node.style.display="block";
		this.isVisible=true;
		this.moveTo("center");
	},
	moveTo:function (x,y) {
		var de=document.documentElement,
			sl=de.scrollLeft,
			st=de.scrollTop,
			dw=de.clientWidth,
			dh=de.clientHeight;
		if (typeof x!="number") {
			switch(x) {
				case "left":
					x=sl;
					break;
				case "right":
					x=sl+dw-this.node.offsetWidth;
					break;
				case "center":
					x=parseInt((sl+dw-this.node.offsetWidth)/2);
					
					break;
			}
			
		}
		if (y===undefined) y="center";
		if (typeof y!="number")  {
			switch(y) {
				case "top":
					y=st;
					break;
				case "bottom":
					y=st+dh-this.node.offsetHeight;
					break;
				case "center":
					y=parseInt((st+dh-this.node.offsetHeight)/2);
					break;
			}
			
		}
		this.node.style.left=x+"px";
		
		this.node.style.top=y+"px";
	},
	resizeTo:function (w,h) {
		var de=document.documentElement,
			pos=getOffset(this.node),
			maxWidth=de.clientWidth+de.scrollLeft-pos.x-2,
			maxHeight=de.clientHeight+de.scrollTop-pos.y-2;
			

			
		
		w=Math.max(w,this.minWidth);
		h=Math.max(h,this.minHeight);
		
		w=Math.min(w,maxWidth);
		h=Math.min(h,maxHeight);
		
		
		
		this.node.style.width=w+"px";
		this.node.style.height=h+"px";
	},
	resizeMax:function () {
		this.sizeBackup={
			width:parseInt(getRealStyle(this.node,"width")) || this.minWidth,
			height:parseInt(getRealStyle(this.node,"height")) || this.minHeight,
			left:parseInt(getRealStyle(this.node,"left")),
			top:parseInt(getRealStyle(this.node,"top"))
		};
		
		this.moveTo(0,0);
		this.resizeTo(9999,9999);
		this.isMaximize=true;
	},
	resizeBack:function () {
	
		if (!this.sizeBackup)
			return false;
		
		var b=this.sizeBackup;
		this.moveTo(b.left,b.top);
		this.resizeTo(b.width,b.height);
		
		this.isMaximize=false;
	}
};


window.PopWindow=PopWindow;



})();