(function () {
	String.prototype.trim=function () {
		return this.replace(/^\s+/,"").replace(/\s+$/,"");
	};
	
	function SortableTable(table) {
		this.table=table;
		this.headers=getByClass("sortableCol",table.tHead.rows[0]);
		this.tBody=table.tBodies[0];
		this.rows=this.tBody.rows;
		var _this=this;
		for (var i=0,vt,format;i<this.headers.length;i++) {
			vt=this.headers[i].getAttribute("valuetype") || "string";
			this.headers[i].format=this.headers[i].getAttribute("param");
			//ÿ����ͷ���Լ���compare(�Ƚ�)����
			this.headers[i].compare=SortableTable.compareMethods[vt];
			addEvent(this.headers[i],"click",function () {
				_this.sortCol(this.cellIndex,-this.currentDir);
			});
		}
		var img=document.createElement("img");
		img.className="sortDirImage";
		this.sortDirImage=img;
	}
	
	//compareMethods����Ԥ����ıȽϷ���
	SortableTable.compareMethods={
		"string":function (a,b) {
			return a.localeCompare(b);
		},
		"number":function (a,b) {
			if (a>b) return 1;
			if (a<b) return -1;
			return 0;
		},
		"date":function (a,b,param) {
			a=dateDecode(a,param).getTime();
			b=dateDecode(b,param).getTime();
			if (a>b) return 1;
			if (a<b) return -1;
			return 0;
		}
	};
	
	SortableTable.prototype={
		sortCol:function (colIndex,dir) {
			//����ָ������
			//dir ָ���Ե����Ż������ţ�ȡֵ 1 ,-1,Ĭ��Ϊ1
			var ary=[];
			dir = dir || 1;
			//��ֵ ��ڵ�(trԪ��)����
			for (var i=0;i<this.rows.length;i++) {
				ary.push({
					node:this.rows[i],
					value:getInnerText(this.rows[i].cells[colIndex])
				});
			}
			//var header=this.headers[colIndex];
			for (var i=0,header;i<this.headers.length;i++) {
				if (this.headers[i].cellIndex==colIndex) {
					header=this.headers[i];
					break;
				}
				
			}
			ary.sort(function (a,b) {
				return header.compare(a.value,b.value,header.format)*dir;
			});
			var frag=document.createDocumentFragment();
			for (i=0;i<ary.length;i++) {
				frag.appendChild(ary[i].node);
			}
			this.tBody.appendChild(frag);
			header.currentDir=dir;
			var img=this.sortDirImage;
			img.src="images/"+(dir>0?"asc":"desc")+".gif";
			header.appendChild(img);
		}
	};
	
	addEvent(window,"load",function () {
		var tables=getByClass("sortableTable");
		for (var i=0;i<tables.length;i++) {
			new SortableTable(tables[i]);
		}
		
	});
	
	function getInnerText(node) {
		//��ȡָ���ڵ�������ı�
		if (node.nodeType==3)//�ı��ڵ�
			return node.nodeValue.trim();
		else if (node.nodeType==1) {//Ԫ�ؽڵ�
			var ret="";
			for (var i=0;i<node.childNodes.length;i++) {
				ret+=getInnerText(node.childNodes[i]);
			}
			return ret;
		}
	}
	
	


	function dateDecode(s,format) {
		var nums=s.match(/\d+/g);
		var d=new Date();
		format=format.split("");
		for (var i=0;i<format.length;i++) {
			switch(format[i]) {
				case "Y":d.setFullYear(nums[i]);break;
				case "m":d.setMonth(nums[i]-1);break;
				case "d":d.setDate(nums[i]);break;
				case "H":d.setHours(nums[i]);break;
				case "i":d.setMinutes(nums[i]);break;
				case "s":d.setSeconds(nums[i]);break;
			}
		}
		return d;
	}

})();