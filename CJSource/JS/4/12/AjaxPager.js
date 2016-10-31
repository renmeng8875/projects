(function () {


/**
AjaxPager ���ݲ��ϵķ�ҳ
�����������ӷ������˻�ȡ��һҳ����һҳ����ָ��ҳ������(JSON��ʽ)
					���÷�ҳ��С
					
					
���������ص�JSON�ļ���ʽ�涨

{
	"totalCount":100,  //��������
	"rows":	[
		{"cms_title":"Title","cms_date":"2009-9-9"},
		{"cms_title":"Title","cms_date":"2009-9-9"}
	]
}


*/

/**
args {
	handler 	Function  �ص���������ָ��������Ⱦ��ʾ��ҳ���У�
	proxy 		URL				�������ű�λ��
	pageSize	Number		��ҳ
	cols			String		��ȡ�ı��е�����(�ö��ŷָ�)
	order			String		���ĸ��н�������
	dir				ASC/DESC	������
	urlParam	Hashmap		����Ĳ���
}



current		��ǰҳ��
pageCount	��ҳ��
*/
function AjaxPager(args) {
	Base.init(AjaxPager,this,args);
	this.loadPage(1);
}
AjaxPager.defaultArgs={
	pageSize:10,
	cols:"*",
	dir:"ASC"
};


AjaxPager.prototype={
	loadNextPage:function () {
		this.loadPage(this.current+1);
	},
	loadPrevPage:function () {
		this.loadPage(this.current-1);
	},
	loadPage:function (n) {
		if (!this.hasPage(n)) {
			return false;
		}
		var $this=this,start=(n-1)*this.pageSize;
		var queryData={
			pageSize:this.pageSize,
			cols:this.cols,
			order:this.order,
			dir:this.dir,
			start:start
		};
		if (this.urlParam) Base.extend(this.urlParam,queryData);
		Base.ajax({
			url:this.proxy,
			data:queryData,
			success:function (txt) {
				var data;
				try {
					data=eval("("+txt+")");
				} catch(e) {}
				$this.totalCount=data.totalCount;
				$this.pageCount=Math.ceil(data.totalCount/$this.pageSize);
				$this.current=n;
				$this.handler(data);
			}
		});
	},
	hasPage:function (n) {
		return n.inter(1,this.pageCount);
	},
	hasNextPage:function () {
		return this.hasPage(this.current+1);
	},
	hasPrevPage:function () {
		return this.hasPage(this.current-1);
	},
	setPageSize:function (pageSize) {
		this.pageSize=pageSize;
	}
};


var page=new AjaxPager({
	proxy:"proxy.php",
	cols:"cms_id,cms_title,cms_date",
	pageSize:2,
	urlParam:{
		table:"cms_article"
	},
	handler:function (data) {
		var newsList=$("newsList");
		//var li=newsList.getElementsByTagName("LI");
		newsList.innerHTML="";
		/*for (var i=0;i<li.length;i++) {
			li[i].getElementsByTagName("EM")[0].innerHTML=data.rows[i].cms_date;
			li[i].getElementsByTagName("A")[0].innerHTML=data.rows[i].cms_id+"."+data.rows[i].cms_title;
		}*/
		
		for (var i=0,html=[];i<data.rows.length;i++) {
			html.push("<li><em>"+htmlEncode(data.rows[i].cms_date)+"</em><a href='#'>"+
			data.rows[i].cms_id+"."+htmlEncode(data.rows[i].cms_title)+"</a></li>");
		}
		newsList.innerHTML=html.join("");
		$("currentPage").value=page.current;
		$("pageSize").value=page.pageSize;
		$("totalCount").innerHTML=data.totalCount;
		if (!page.hasNextPage()) {
			$("nextPage").className="emptyLink";
		} else {
			$("nextPage").className="";
		}
		if (!page.hasPrevPage()) {
			$("prevPage").className="emptyLink";
		} else {
			$("prevPage").className="";
		}
	}
});
addEvent(window,"load",function () {
	var prevPage=$("prevPage"),
			nextPage=$("nextPage");
			
	prevPage.onclick=function () {
		page.loadPrevPage();
	};
	nextPage.onclick=function () {
		page.loadNextPage();
	};
	var cp=$("currentPage");
	addEvent(cp,"keyup",function (evt) {
		if (evt.keyCode===13) {
			
			var n=parseInt(cp.value);
			if (!page.hasPage(n)) {
				n=cp.value=page.pageCount;
			}
			page.loadPage(n);
			
		}
	});
	var ps=$("pageSize");
	addEvent(ps,"keyup",function (evt) {
		
		if (evt.keyCode===13) {
			var n=parseInt(ps.value);
			
			if (!n.inter(1,99)) {
				n=10;
			}
			page.setPageSize(n);
			page.loadPage(1);
		}
	});
});

})();