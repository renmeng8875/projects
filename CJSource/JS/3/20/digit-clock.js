(function () {
	//��String�������һ��repeat��������չ	String����
	
	
	String.prototype.repeat=function (n) {
		//n��ʾ�ַ����صõĴ���
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
	DigitClock.clocks=[];//��������ʵ��
	DigitClock.redraw=function () {
		//�ػ����е�ʱ��ʵ��
		var d=new Date();
		for (var i=0;i<this.clocks.length;i++) {
			this.clocks[i].setTime(d.getHours(),d.getMinutes(),d.getSeconds());
		}
	};
	DigitClock.preZero=function (n,pos) {
		//����һ�����֣���ǰ��0
		//pos ��ʾ����������posλ��ʱ�򣬼�ǰ��0
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
			h ʱ
			i ��
			s ��
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