(function () {
	
	addEvent(window,'load',initAll);
	
	function initAll() {
		new GEye($("eyeLeft"));
		new GEye($("eyeRight"));
		
		
		
	}
	
	function GEye(eye) {
		//眼睛对象
		this.eye=eye;//span 眼框
		this.pupil=eye.getElementsByTagName("IMG")[0];//pupil img元素瞳孔
		GEye.eyes.push(this);
	}
	addEvent(document,'mousemove',function (evt) {
		GEye.init(evt.clientX,evt.clientY);
	});
	GEye.init=function (x,y) {
		//实现
		for (var i=0;i<this.eyes.length;i++) {
			this.eyes[i].redraw(x,y);
		}
	};
	GEye.eyes=[];//保存所有的实例
	GEye.eyeRadius=58;//静态属性
	//瞳孔活动范围半径
	GEye.pupilRange=40;
	GEye.pupilRadius=6;
	GEye.calcHyp=function (hside,vside) {//静态方法
		//求已知直角三角形两直角边的斜边
		return Math.sqrt(Math.pow(hside,2)+Math.pow(vside,2));
	};
	//GEye有哪些方法
	GEye.prototype={
		getEyeHPos:function () {
			return {
				x:this.eye.offsetLeft+GEye.eyeRadius,
				y:this.eye.offsetTop+GEye.eyeRadius
			};
		},
		redraw:function (mouseX,mouseY) {
			
			var eyeHPos=this.getEyeHPos();
			var l=GEye.calcHyp(mouseX-eyeHPos.x,mouseY-eyeHPos.y);
			if (l>GEye.pupilRange) {
				//当鼠标在eye外面时
				var sin=(eyeHPos.y-mouseY)/l;
				var cos=(eyeHPos.x-mouseX)/l;
				this.setPupilAngle(sin,cos);
			} else {
				//在eye里面时
				this.setPupilPos(
				mouseX-this.eye.offsetLeft-GEye.pupilRadius,
				mouseY-this.eye.offsetTop-GEye.pupilRadius);
			}
			
			
			
		},
		setPupilPos:function (left,top) {
			//设置pupil的坐标
			this.pupil.style.left=left+"px";
			this.pupil.style.top=top+"px";
		},
		setPupilAngle:function (sin,cos) {
			//设置pupil的角度
			var left=GEye.eyeRadius-cos*GEye.pupilRange-GEye.pupilRadius;  //y
			var top=GEye.eyeRadius-sin*GEye.pupilRange-GEye.pupilRadius; //x
			this.setPupilPos(left,top);
		}
	};
	
	
//Eye是一个对象,它包含一个Pupil

	
	
})();

