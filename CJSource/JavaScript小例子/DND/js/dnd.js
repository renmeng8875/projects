(function () {
	
	
	
	
	/**
	args {
		layer
		handle
		range {
			minX
			minY
			maxX
			maxY
		}
		
	}
	*/
	
	function DND(args) {
		DND.init(this,args);
		var $this=this;
		this.mousedownHandle=function (evt) {
			$this.startDrag(evt);
		};
		this.ieDragHandle=function (evt) {//IE
			evt.preventDefault();
			return false;
		};
		if (this.range.nodeType) {
			this.range=DND.defaultArgs.range.valueOf(this);
		}
		this.enable();
		
	}
	
	extend(DND,{
		MODE_H:"H",
		MODE_V:"V",
		MODE_BOTH:"BOTH",
		UNLIMIT_RANGE:{//不受限制的区域
			minX:-Infinity,
			minY:-Infinity,
			maxX:Infinity,
			maxY:Infinity
		}
			
	});
	extend(DND,{
		defaultArgs:{
			range:{
				valueOf:function ($this) {
					return {
						valueOf:function ($this) {
							var rangeNode=$this.originalArgs.range || document.documentElement,
								offset=getOffset(rangeNode),
								o={minX:offset.x,minY:offset.y},
								layer=$this.layer,
								ml=parseInt(getRealStyle(layer,"margin-left")) || 0,
								mr=parseInt(getRealStyle(layer,"margin-right")) || 0,
								mt=parseInt(getRealStyle(layer,"margin-top")) || 0,
								mb=parseInt(getRealStyle(layer,"margin-bottom")) || 0,
								w=layer.offsetWidth+ml+mr,
								h=layer.offsetHeight+mt+mb;
							o.maxX=rangeNode.clientWidth+o.minX-w;
							o.maxY=rangeNode.clientHeight+o.minY-h;
							return o;
						}
					};
				}
			},
			className:"moving",
			handle:{			
				valueOf:function ($this) {
					return $this.layer;
				}
			},
			mode:DND.MODE_BOTH
		},
		drag:function (evt) {
			DND.current.drag(evt);
		},
		drop:function (evt) {
			DND.current.drop(evt);
		},
		init:function ($this,args) {
			$this.originalArgs=args;
			for (var i in args) {
				$this[i]=args[i];
			}
			for (i in this.defaultArgs) {
				if ($this[i]===undefined) 
					$this[i]=this.defaultArgs[i].valueOf($this);
			}
		}
	});
	
	DND.prototype={
		enable:function () {
			addEvent(this.handle,"mousedown",this.mousedownHandle);
			addEvent(this.handle,"dragstart",this.ieDragHandle);
		},
		disable:function() {
			this.drop();
			delEvent(this.handle,"mousedown",this.mousedownHandle);
			delEvent(this.handle,"dragstart",this.ieDragHandle);
		},
		startDrag:function (evt) {
			
			var ret;
			if (this.onDragStart) 
				ret=this.onDragStart(evt,this);
			
			if (ret===false)
				return false;
		
			evt.preventDefault();
			if (document.selection && document.selection.empty) {
				document.selection.empty();  //IE
			} else if (window.getSelection) {
				window.getSelection().removeAllRanges(); //火狐
			}
		
		
			var layerOffset=getOffset(this.layer);
			this.offset={
				x:evt.pageX-layerOffset.x,
				y:evt.pageY-layerOffset.y
			};
			
			
			addClass(this.handle,this.className);
			addClass(this.layer,this.className);
			DND.current=this;
			
			
			addEvent(document,"mousemove",DND.drag);
			addEvent(document,"mouseup",DND.drop);
			addEvent(window,"blur",DND.drop);
			
			
			if (this.handle.setCapture) {//IE
				this.handle.setCapture(true);
				addEvent(this.handle,"losecapture",DND.drop);
			} else if(window.captureEvents) {//FX
				window.captureEvents(this.handle);
			}
				
			
		},
		drag:function (evt) {
			var ret;
			
				
			var mx=parseInt(getRealStyle(this.layer,"margin-left")) || 0,
				my=parseInt(getRealStyle(this.layer,"margin-top")) || 0,
				x=evt.pageX-this.offset.x-mx,
				y=evt.pageY-this.offset.y-my,
				range=this.range.valueOf(this),
				parentOffset=getOffset(this.layer.offsetParent);
				
			
			x=Math.min(x,range.maxX);
			x=Math.max(x,range.minX);
			
			y=Math.min(y,range.maxY);
			y=Math.max(y,range.minY);
				
			if (this.onDrag)
				ret=this.onDrag(evt,this,{x:x,y:y});
			
			if (ret===false)
				return false;
			
				
			if (this.mode!=DND.MODE_V) {
				x=x-parentOffset.x;
				this.layer.style.left=x+"px";
			}
			if (this.mode!=DND.MODE_H) {
				y=y-parentOffset.y;
				this.layer.style.top=y+"px";
			}
			
			
			
		},
		drop:function (evt) {
			delClass(this.handle,this.className);
			delClass(this.layer,this.className);
			delEvent(document,"mousemove",DND.drag);
			delEvent(document,"mouseup",DND.drop);
			delEvent(window,"blur",DND.drop);
			DND.current=null;
			if (this.onDrop)
				this.onDrop(evt,this);
				
			if (this.handle.releaseCapture) {//IE
				delEvent(this.handle,"losecapture",DND.drop);
				this.handle.releaseCapture(true);
				
			} else if (window.releaseEvents) {//FX
				window.releaseEvents(this.handle);
			}
		},
		getRange:function () {
			return this.range.valueOf(this);
		}
	};
	
	

	window.DND=DND;


	
	
})();