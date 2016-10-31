(function () {
	//给String对象添加一个repeat方法，扩展	String对象
	
	
	String.prototype.repeat=function (n) {
		//n表示字符串重得的次数
		return new Array(n+1).join(this);
	};
	
	
	function preloadImage(src) {
		var img=document.createElement("img");
		img.src=src;
		preloadImage.images.push(img);
	}
	preloadImage.images=[];
	
	for (var i=0;i<10;i++) {
		preloadImage("digits/"+i+".gif");
	}
	
	
	function DigitClock(container) {
		this.images=getByClass("clockImage",container);
		this.container=container;
		DigitClock.clocks.push(this);
		DigitClock.redraw();
	}
	DigitClock.clocks=[];//保存所有实例
	DigitClock.redraw=function () {
		//重绘所有的时钟实例
		var d=new Date();
		for (var i=0;i<this.clocks.length;i++) {
			this.clocks[i].setTime(d.getHours(),d.getMinutes(),d.getSeconds());
		}
	};
	DigitClock.preZero=function (n,pos) {
		//接受一个数字，加前导0
		//pos 表示当数字少于pos位的时候，加前导0
		n=""+n;
		//n=12,pos=4; 0012
		if (n.length<pos) {
			n="0".repeat(pos-n.length)+n;
		}
		return n;
	};
	setInterval(function () {
		DigitClock.redraw();
	},1000);
	DigitClock.prototype={
		setTime:function (h,i,s) {
			/**
			h 时
			i 分
			s 秒
			*/
			h=DigitClock.preZero(h,2);
			i=DigitClock.preZero(i,2);
			s=DigitClock.preZero(s,2);
			var a=(h+i+s).split("");
			for (var i=0;i<a.length;i++) {
				this.images[i].src="digits/"+a[i]+".gif";
			}
		}
	};
	
	addEvent(window,"load",function () {
		var c=new DigitClock($("digitClock"));
	});
})();