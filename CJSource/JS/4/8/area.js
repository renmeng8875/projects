addEvent(window,"load",function () {
	var psel=$("province");//Ê¡
	var csel=$("city");//ÊÐ
	var tsel=$("town");//ÏØ
	var citySpan=$("citySpan");
	var townSpan=$("townSpan");
	var waitImg=$("waitPic");
	
	psel.onchange=function () {
		if (!this.value) return;
		var aid=this.value;
		townSpan.style.display="none";
		waitImg.style.display="inline";
		csel.options.length=1;
		Base.ajax({
			url:"action.php",
			data:{aid:aid},
			success:function (t,xml) {
				if (xml) {
					citySpan.style.display="inline";
				}
				csel.options.length=1;
				var nodes=xml.getElementsByTagName("a");
				for (var i=0,opt;i<nodes.length;i++) {
					opt=new Option(nodes[i].getAttribute("n"),nodes[i].getAttribute("id"));
					csel.add(opt,null);
				}
				waitImg.style.display="none";
			}
		});
	};
	psel.onchange();
	csel.onchange=function () {
		if (!this.value) return;
		var aid=this.value;
		waitImg.style.display="inline";
		Base.ajax({
			url:"action.php",
			data:{aid:aid},
			success:function (t,xml) {
				if (xml) {
					townSpan.style.display="inline";
				}
				tsel.options.length=1;
				var nodes=xml.getElementsByTagName("a");
				for (var i=0,opt;i<nodes.length;i++) {
					opt=new Option(nodes[i].getAttribute("n"),nodes[i].getAttribute("id"));
					tsel.add(opt,null);
				}
				waitImg.style.display="none";
			}
		});
	};
});