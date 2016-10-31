(function ($) {
	var eyeLeft,eyeRight,pupilLeft,pupilRight;
	/**���庯��,��ҳ�����ʱ�Զ�ִ��
	 * eyeLeft,��ߵ��۾� span
	 * eyeRight,�ұߵ��۾� span
	 * pupilLeft,��ߵ�ͫ��ͼƬ
	 * pupilRight,�ұߵ�ͫ��ͼƬ
	 */
	
	//��ҳ�������ɺ�,��ʼ��
	addEvent(window,"load",function () {
		//�ֱ��ȡԪ��
		eyeLeft=$("eyeLeft");
		eyeRight=$("eyeRight");
		pupilLeft=$("pupilLeft");
		pupilRight=$("pupilRight");
		
		//�������ҳ�����ƶ�ʱ,ִ��movePupil
		addEvent(document,"mousemove",movePupil);
	});
	
	function movePupil(evt) {
		/**movePupil,��ȡ���λ��,������setPupilPos����
		 * 
		 * @param evt Event ��֧��W3C��׼�¼����Ƶ��������,�¼��������Ϊ��һ���������ݸ��¼�������
		 * 
		 * ��IE��,�¼�������Ϊȫ��event����
		 */
		evt = evt || window.event;//��ȡ�¼�����
		var MousePos = {//��ȡ���λ��
			x:evt.clientX,
			y:evt.clientY
		};
		
		//����setPupilPos����,ȥ���㲢����ͫ��ͼƬӦ�ڵ�λ��
		setPupilPos(eyeLeft,pupilLeft,MousePos);
		setPupilPos(eyeRight,pupilRight,MousePos);
	}
	
	function setPupilPos(eye,pupil,MousePos) {
		/**setPupilPos ���㲢����ͫ��ͼƬ��λ��
		 * 
		 * @param eye Object ͫ�������۾� DOM����
		 * @param pupil Obejct ���۾���Ӧ��ͫ��ͼƬ
		 * @param MousePos Object �������λ����Ϣ�Ķ���
		 */
		
		var eyePos=getPos(eye);//��ȡ�۾������λ��
		var eyeHPos={//��ȡ�۾����ĵ�λ��
			x:eyePos.x+parseInt(eye.offsetWidth/2),
			y:eyePos.y+parseInt(eye.offsetHeight/2)
		};
		
		//�����۾�������������ڵ�ľ���
		var distance=calcHyp(eyeHPos.x-MousePos.x,eyeHPos.y-MousePos.y);
		if (Math.abs(distance)<=34 ) {//������С��34,�����۾��ڲ�ʱ,ֱ�ӽ�ͫ�׵�λ����Ϊ����λ��
			pupil.style.left = (MousePos.x-eyePos.x-6)+"px";//6��ͫ�׵İ뾶,����ʹͫ������λ�������λ�����غ�
			pupil.style.top = (MousePos.y-eyePos.y-6)+"px";
		} else {//��������۾�����ʱ,��Ҫ����Ƕ�
			var sin= (eyeHPos.y-MousePos.y)/distance;//�Ƕ�����ֵ
			var cos=(eyeHPos.x-MousePos.x)/distance;//�Ƕ�����ֵ
			//ͫ��ͼƬ�ǲ���������۾�(�丸Ԫ��)�Ķ�λ��ʽ,40��ͫ�׻��Χ�뾶,58���۾��뾶
			pupil.style.left= 58-cos*40-6+"px";
			pupil.style.top=58-sin*40-6+"px";
		}
	}
	
	function calcHyp(x,y) {
		/**calcHyp ͨ��ֱ�������ε���֪���߼���б�ߵĳ���
		 * 
		 * @param x Number ֱ�������εĵױ�
		 * @param y Number ֱ�������εĸ�
		 * @return Number ֱ��������б�ߵĳ���
		 * 
		 */
		return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
	}
	function getPos(obj) {
		/**getPos ��ȡDOM������ҳ���еľ�������
		 * 
		 * @param obj Object DOM����
		 * @return pos Object ����������Ϣ�Ķ���
		 * 
		 * ��ȡDOMԪ������ڶ�λ�ο�����Ԫ�ص�λ����ʹ�ö����offsetLeft��offsetTop����
		 * ��ȡDOMԪ�صĶ�λ�ο�����Ԫ����ʹ�ö����offsetParent����
		 * ͨ���ݹ�,���Ի�ȡԪ�������ҳ��(document.body,document.body.offsetParentΪnull)������
		 */
		var pos={//��ʼֵΪ0
			x:0,
			y:0
		};
		do {
			pos.x+= obj.offsetLeft;
			pos.y+=obj.offsetTop;
		} while(obj=obj.offsetParent);//������offsetParentʱ(��û�е��ﶥ��bodyʱ)һֱѭ��,������obj
		return pos;
	}
	
	function addEvent(obj,evtype,fn) {
		/**addEvent ��Ԫ������¼�����
		 * 
		 * @param obj Object DOMԪ�ػ�window�����
		 * @param evtype String ����"on"ǰ׺���¼�����
		 * @param fn Function �¼�������
		 * @return Object ��Ԫ��
		 */
		//���ȿ���W3C�¼�����
		if (obj.addEventListener) obj.addEventListener(evtype,fn,false);
		//����,�ٿ���IE�����,IE���¼�������Ҫ����"on"ǰ׺
		else if (obj.attachEvent) obj.attachEvent("on"+evtype,fn);
		//������������¼�ע�᷽������֧��,��ʹ�ô�ͳ�ķ���(������������������)
		else obj["on"+evtype]=fn;
		return obj;
	}
})(function (id) {
	/**DOMԪ�� IDѡ����
	 * 
	 * @param id String Ԫ�ص�ID
	 * @return Object DOMԪ��
	 * 
	 * ���������Ϊһ���������ݸ�����һ����������,��������д��������Ϊ��ϰ��^0^
	 * ��ΪjQuery��Ҳ��ѡ������Ϊ��һ���������ݸ�jQuery(function ($){})���е���������
	 */
	return document.getElementById(id);
});