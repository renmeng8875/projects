(function () {
/**
args {
	items NodeList 节点列表 要进行拖动排序的元素
	range Node 拖动活动范围
	mode "H"/"V" 水平拖动还是垂直拖动
}
*/
function DragSort(args) {
	Base.init(DragSort,this,args);
	var $this=this;
	addEvent(this.range,"mousedown",function (evt) {
		var target=evt.target,item;
		for (var i=0;i<$this.items.length;i++) {
			if (target==$this.items[i]) {
				item=$this.items[i];
				break;
			}
		}
		if (!item) {
			return;
		}
		
		var newItem=item.cloneNode(true),
				oldOffset=getOffset(item),
				ml=parseInt(getRealStyle(item,"margin-left")) || 0,
				mt=parseInt(getRealStyle(item,"margin-top")) || 0;
		newItem.style.position="absolute";
		newItem.style.left=oldOffset.x-ml+"px";
		newItem.style.top=oldOffset.y-mt+"px";
		item.style.visibility="hidden";
		
		
		$this.range.appendChild(newItem);
		var dnd=new DND({
			layer:newItem,
			range:$this.range,
			mode:$this.mode,
			onMove:function (evt) {
				var lo=getOffset(dnd.layer),offset,
						half,a;
				
				if ($this.mode=="H") {
					a="x";
					half=dnd.layer.clientWidth/2;
				} else {
					half=dnd.layer.clientHeight/2;
					a="y";
				}
				for (var i=0;i<$this.items.length;i++) {
					if ($this.items[i]==item || $this.items[i]==newItem) {
						continue;
					}
					offset=getOffset($this.items[i]);
					if (Math.abs(lo[a]-offset[a])<half) {
						Base.swapNode(item,$this.items[i]);
						
						break;
					}
				}
			},
			onDrop:function () {
				Base.delNode(newItem);
				item.style.visibility="visible";
				if ($this.onChange) {
					$this.onChange($this);
				}
			}
		});
		dnd.startDrag(evt);
		
	});
}
DragSort.defaultArgs={
	mode:"H"
};
DragSort.prototype={
	onChange:function () {
		
	}
};

window.DragSort=DragSort;
})();