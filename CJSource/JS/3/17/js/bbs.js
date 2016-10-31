window.onload=function () {
	var contentList=$("contentList");
	var li=contentList.getElementsByTagName("LI")[0];
	contentList.removeChild(li);//移除预告放在HTML中的LI
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
			msgDateSpan.innerHTML=date("Y年m月d日 H:i:s");//2009年9月10日 15:12:10
			//TODO: 实现date("Y-m-d H:i:s") 这样的函数
			msgContentDiv.innerHTML=htmlEncode(content);
			//TODO: 实现htmlEncode("<html>") &lt; &gt;
			contentList.appendChild(newLi);
			
		} else {
			alert("请将表单填写完整！");
		}
	};
	
	$("sortASC").onclick=function () {
		sortBBS(1);
	};
	$("sortDESC").onclick=function () {
		sortBBS(-1);
	};
	
	function sortBBS(dir) {
		//将贴子排序
		//dir 1 表示正序 -1表示倒序
		var list=contentList.getElementsByTagName("LI");
		var a=[],dateSpan,tuple,d,
		re=/(\d{4})年(\d{1,2})月(\d{1,2})日 (\d{1,2}):(\d{1,2}):(\d{1,2})/;
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
				node:list[i],//帖子对应的DOM元素
				date:d.getTime() //发表时间
			});
			//2010年5月10日 16:1:51  解析出其中的Y m d H i s
		}
		a.sort(function ($1,$2) {
			if ($1.date>$2.date) return dir;
			else if ($1.date<$2.date) return -dir;
			else return 0;
		});
		contentList.innerHTML="";
		
		var frag=document.createDocumentFragment();
		//文档片段
		//将一些元素放入其中，组合成一个节点
		for (i=0;i<a.length;i++) {
			frag.appendChild(a[i].node);
			alert(i);
		}
		
		contentList.appendChild(frag);
		
	}
	
};


