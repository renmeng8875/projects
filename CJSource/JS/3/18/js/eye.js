(function () {
	
	addEvent(window,'load',initAll);
	
	function initAll() {
		new GEye($("eyeLeft"));
		new GEye($("eyeRight"));
		
		
		
	}
	
	function GEye(eye) {
		//�۾�����
		this.eye=eye;//span �ۿ�
		this.pupil=eye.getElementsByTagName("IMG")[0];//pupil imgԪ��ͫ��
		GEye.eyes.push(this);
	}
	addEvent(document,'mousemove',function (evt) {
		GEye.init(evt.clientX,evt.clientY);
	});
	GEye.init=function (x,y) {
		//ʵ��
		for (var i=0;i<this.eyes.length;i++) {
			this.eyes[i].redraw(x,y);
		}
	};
	GEye.eyes=[];//�������е�ʵ��
	GEye.eyeRadius=58;//��̬����
	//ͫ�׻��Χ�뾶
	GEye.pupilRange=40;
	GEye.pupilRadius=6;
	GEye.calcHyp=function (hside,vside) {//��̬����
		//����ֱ֪����������ֱ�Ǳߵ�б��
		return Math.sqrt(Math.pow(hside,2)+Math.pow(vside,2));
	};
	//GEye����Щ����
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
				//�������eye����ʱ
				var sin=(eyeHPos.y-mouseY)/l;
				var cos=(eyeHPos.x-mouseX)/l;
				this.setPupilAngle(sin,cos);
			} else {
				//��eye����ʱ
				this.setPupilPos(
				mouseX-this.eye.offsetLeft-GEye.pupilRadius,
				mouseY-this.eye.offsetTop-GEye.pupilRadius);
			}
			
			
			
		},
		setPupilPos:function (left,top) {
			//����pupil������
			this.pupil.style.left=left+"px";
			this.pupil.style.top=top+"px";
		},
		setPupilAngle:function (sin,cos) {
			//����pupil�ĽǶ�
			var left=GEye.eyeRadius-cos*GEye.pupilRange-GEye.pupilRadius;  //y
			var top=GEye.eyeRadius-sin*GEye.pupilRange-GEye.pupilRadius; //x
			this.setPupilPos(left,top);
		}
	};
	
	
//Eye��һ������,������һ��Pupil

	
	
})();

