window.onload=function () {
	var contentList=$("contentList");
	var li=contentList.getElementsByTagName("LI")[0];
	contentList.removeChild(li);//�Ƴ�Ԥ�����HTML�е�LI
	var fm=$("fm1");
	var msgTitle=$("msgTitle"),
			userName=$("userName"),
			msgContent=$("msgContent");
	fm.onsubmit=function (evt) {
		if (evt) {//W3C
			evt.preventDefault();
		} else {//IE
			window.event.returnValue=false;
		}
		var title=msgTitle.value,
				user=userName.value,
				content=msgContent.value;
		if (title && user && content) {
			var newLi=li.cloneNode(true),
					msgTitleSpan=getByClass('msgTitle',newLi)[0],
					userNameSpan=getByClass('userName',newLi)[0],
					msgDateSpan=getByClass('msgDate',newLi)[0],
					msgContentDiv=getByClass('msgContent',newLi)[0];
			msgTitleSpan.innerHTML=title;
			userNameSpan.innerHTML=user;
			msgDateSpan.innerHTML=date("Y��m��d�� H:i:s");//2009��9��10�� 15:12:10
			//TODO: ʵ��date("Y-m-d H:i:s") �����ĺ���
			msgContentDiv.innerHTML=htmlEncode(content);
			//TODO: ʵ��htmlEncode("<html>") &lt; &gt;
			contentList.appendChild(newLi);
			
		} else {
			alert("�뽫����д������");
		}
	};
	
	$("sortASC").onclick=function () {
		sortBBS(1);
	};
	$("sortDESC").onclick=function () {
		sortBBS(-1);
	};
	
	function sortBBS(dir) {
		//����������
		//dir 1 ��ʾ���� -1��ʾ����
		var list=contentList.getElementsByTagName("LI");
		var a=[],dateSpan,tuple,d,
		re=/(\d{4})��(\d{1,2})��(\d{1,2})�� (\d{1,2}):(\d{1,2}):(\d{1,2})/;
		for (var i=0;i<list.length;i++) {
			dateSpan=getByClass("msgDate",list[i])[0];
			tuple=re.exec(dateSpan.innerHTML);
			d=new Date();
			d.setFullYear(tuple[1]);
			d.setMonth(tuple[2]-1);
			d.setDate(tuple[3]);
			d.setHours(tuple[4]);
			d.setMinutes(tuple[5]);
			d.setSeconds(tuple[6]);
			a.push({
				node:list[i],//���Ӷ�Ӧ��DOMԪ��
				date:d.getTime() //����ʱ��
			});
			//2010��5��10�� 16:1:51  ���������е�Y m d H i s
		}
		a.sort(function ($1,$2) {
			if ($1.date>$2.date) return dir;
			else if ($1.date<$2.date) return -dir;
			else return 0;
		});
		contentList.innerHTML="";
		
		var frag=document.createDocumentFragment();
		//�ĵ�Ƭ��
		//��һЩԪ�ط������У���ϳ�һ���ڵ�
		for (i=0;i<a.length;i++) {
			frag.appendChild(a[i].node);
			alert(i);
		}
		
		contentList.appendChild(frag);
		
	}
	
};


