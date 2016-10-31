(function () {
/**
args  {
	items NodeList
	mode DND.MODE_H DND.MODE_V
	container  Ul
	onChange Function
}
*/
function DragOrder(args) {
	DND.init.call(DragOrder,this,args);
	var $this=this;
	for (var i=0;i<this.items.length;i++) {
		this.items[i].isOrderItem=true;
	}
	
	
	
	addEvent(this.container,"mousedown",function (evt) {
		$this.startDrag(evt);
	});
}
extend(DragOrder,{
	defaultArgs:{
		mode:DND.MODE_V
	}
});

DragOrder.prototype={
	startDrag:function (evt) {
		var item=evt.target,ul=this.container;
		if (!item.isOrderItem)
			return false;
		/*while (item!=this.container && !item.isOrderItem) {
			item=item.parentNode;
		}*/
		
			
		var cItem=item.cloneNode(true);
		var offset=getOffset(item);
		
		item.style.visibility="hidden";
		
		cItem.style.position="absolute";
		cItem.style.left=offset.x+"px";
		cItem.style.top=offset.y+"px";
		cItem.style.margin="0px";
		
		ul.appendChild(cItem);
		var $this=this,
			ca=this.mode==DND.MODE_H?"x":"y",
			sizea=ca=="x"?"clientWidth":"clientHeight",
			half=cItem[sizea]/2;
		
		this.current=new DND({
			mode:this.mode,
			layer:cItem,
			range:ul,
			onDrag:function (evt,d) {
				var lpos=getOffset(d.layer),
					opos;
				for (var i=0;i<$this.items.length;i++) {
					if ($this.items[i]==item || $this.items[i]==cItem) continue;
					opos=getOffset($this.items[i]);
					if (lpos[ca].inter(opos[ca]+half,opos[ca]-half)) {
						swapNode(item,$this.items[i]);
						if ($this.onChange) {
							$this.onChange(item,$this.items[i],$this);
						}
						break;
					}
						
				}
			},
			onDrop:function () {
				item.style.visibility="visible";
				delNode(cItem);
				$this.current=null;
			}
		});
		this.current.startDrag(evt);
	}
};



addEvent(window,"load",function () {
	var containerH=$("dragOrderH"),
		containerV=$("dragOrderV");
	new DragOrder({
		items:containerH.getElementsByTagName("LI"),
		container:containerH,
		mode:DND.MODE_H
	});
	new DragOrder({
		items:containerV.getElementsByTagName("LI"),
		container:containerV,
		mode:DND.MODE_V
	});
});
	
})();