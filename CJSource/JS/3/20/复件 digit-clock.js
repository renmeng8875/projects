(function () {

	String.prototype.repeat=function (n) {
		return new Array(n+1).join(this);
	};
	function DigitClock(container) {
		this.images=getElementsByClassName("clockImage",container);
		DigitClock.clocks.push(this);
		DigitClock.redraw();
	}
	DigitClock.clocks=[];
	DigitClock.preZero=function (n,pos) {
		return ("0".repeat(pos-1)+n).slice(-pos);
	};
	DigitClock.redraw=function () {
		var d=new Date();
		for (var i=0;i<this.clocks.length;i++) {
			this.clocks[i].setTime(d.getHours(),d.getMinutes(),d.getSeconds());
		}
	};
	setInterval(function () {
		DigitClock.redraw();
	},1000);
	DigitClock.prototype={
		setTime:function (h,i,s) {
			this.currentTime=[h,i,s];
			this.setImage(
				DigitClock.preZero(h,2)+
				DigitClock.preZero(i,2)+
				DigitClock.preZero(s,2));
		},
		setImage:function (s) {
			s=s.split("");
			for (var i=0;i<this.images.length;i++) {
				this.images[i].src="digits/"+s[i]+".gif";
			}
		},
		getTime:function () {
			return this.currentTime;
		}
	};
	
	
	function getElementsByClassName(name,context) {
		context=context || document;
		if (context.getElementsByClassName) {
			return context.getElementsByClassName(name);
		} else {
			var nodes=context.getElementsByTagName("*"),ret=[];
			for (var i=0;i<nodes.length;i++) {
				if (hasClass(nodes[i],name)) ret.push(nodes[i]);
			}
			return ret;
		}
	}
	function hasClass(node,name) {
		var names=node.className.split(/\s+/);
		for (var i=0;i<names.length;i++)  {
			if (names[i]==name) return true;
		}
		return false;
	}
	
	
	
	
	window.onload=function () {
		new DigitClock(document.getElementById("digitClock"));
	};
	
	
	
	
	
	
	
	
	
	
	
	
})();