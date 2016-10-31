(function () {


/**
AjaxPager 数据层上的分页
功能描述：从服务器端获取上一页，下一页或者指定页的数据(JSON格式)
					设置分页大小
					
					
服务器返回的JSON文件格式规定

{
	"totalCount":100,  //数据总数
	"rows":	[
		{"cms_title":"Title","cms_date":"2009-9-9"},
		{"cms_title":"Title","cms_date":"2009-9-9"}
	]
}


*/

/**
args {
	handler 	Function  回调函数（将指定数据渲染显示在页面中）
	proxy 		URL				服务器脚本位置
	pageSize	Number		分页
	cols			String		获取的表中的列名(用逗号分隔)
	order			String		对哪个列进行排序
	dir				ASC/DESC	排序方向
	urlParam	Hashmap		额外的参数
}



current		当前页码
pageCount	总页数
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