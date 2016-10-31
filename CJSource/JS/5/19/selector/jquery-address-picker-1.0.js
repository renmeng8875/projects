
(function ($) {
/**
args 所有的配置
{
	input						DOMElement			要绑定的input元素
	initValue				Array<AreaID>		初始值
	maxCount				Number					最多选择的个数
	onOverMaxCount	Function				当选择多于指定个数时回调函数
	onClose					Function
	onShow					Function
	onPick					Function
	onUnPick				Function
	autoHide				Boolean					是否自动隐藏
}
*/

var AP=AddressPicker;
AP.html="<div class='MultiSelector' style='display:none'><h3><a name='MultiSelector' class='closeButton'>[关闭]</a><em class='title'>城市选择器</em></h3><h4><a name='SelectorBack' class='subTitle'>返回</a></h4><dl class='SelectorArea'><dd class='itemsContainer'></dd></dl><h4><span class='bottomTitle'>您已经选择了:</span><em>(点击可以取消选择)</em></h4><div class='selectedItemsContainer'></div><h3><a name='MultiSelector' class='closeButton'>[关闭]</a></h3></div>";
AP.node=$(AddressPicker.html);

function AddressPicker(args) {
	$.extend(this,args);
	var _this=this;
	this.layer=AP.node.clone();
	this.layer.appendTo(document.body);
	this.closeButton=this.layer.find(".closeButton");
	this.items={};
	
	this.closeButton.click(function () {
		_this.close();
	});
	
	this.container=this.layer.find(".itemsContainer");
	this.selectedContainer=this.layer.find(".selectedItemsContainer");
	$("input",this.selectedContainer[0]).live("click",function () {
		_this.removeSelectedItem(this);
	});
		
	this.initArea(AreaData.getProvinces());
	$("input:checkbox",this.container[0]).live("click",function (evt) {
		var tn=this.parentNode.parentNode.tagName;
		
		if (/dt/i.test(tn)) {
			var otherCheckboxs=$(this).parents("dt").next().find("input");
			if (this.checked) {
				otherCheckboxs.attr("disabled","disabled");
				_this.addSelectedItem(this);
			} else {
				otherCheckboxs.attr("disabled","");
			}
			return;
			
		}
		if (/dd/i.test(tn)) {
			
			if (this.checked) {
				_this.onPick && _this.onPick(evt);
				var subArea=AreaData.getSubArea(this.value);
				if (subArea) {
					_this.initArea(subArea,$(this.parentNode));
					this.checked=false;
				} else {
					_this.addSelectedItem(this);
				}
			} else  {
				_this.onUnPick && _this.onUnPick(evt);
				_this.removeSelectedItem(this);
			}
			
		}
	});
}


AddressPicker.prototype={
	show:function () {
		if (this.isVisible) return;
		var inputOffset=this.input.offset();
		this.layer.offset({left:inputOffset.left,top:inputOffset.top+this.input.outerHeight()});
		
		this.layer.show();
		AP.current=this;
		this.isVisible=true;
	},
	close:function () {
		if (!this.isVisible) return; 
		if (this.onClose) this.onClose();
		this.layer.hide();
		this.isVisible=false;
		var a=[];
		for (var i in this.items) {
			this.items[i] && a.push(this.items[i]);
		}
		this.input.val(a.join(","));
	},
	initArea:function (adata,label) {
		var _this=this;
		
		_this.container.empty();
		var dl=$("<dl><dt></dt><dd></dd></dl>");
		dl.find("dt").append(label);
		if (!label) dl.find("dt").remove();
		_this.container.append(dl);
		_this.container=dl.find("dd");
		$.each(adata,function () {
			if (!this[1]) return;
			var checkbox=$("<label><input type='checkbox' value='"+this[1]+"' /> "+this[0]+"</label>");
			checkbox.find("input").data("AreaText",this[0]);
			_this.container.append(checkbox);
		});
	},
	addSelectedItem:function (checkbox) {
		if (this.items[$(checkbox).val()]) {
			
			return;
		}
		this.items[$(checkbox).val()]=$(checkbox).data("AreaText");
		var c=this.selectedContainer;
		var label=$(checkbox.parentNode).clone();
		label.find("input").data("OriginalInput",checkbox);
		c.append(label);
	},
	removeSelectedItem:function (checkbox) {
		delete this.items[checkbox.value];
		var c=this.selectedContainer;
		var checkboxs=c.find("input");
		checkboxs.each(function () {
			if (this.value===checkbox.value) {
				var oinput=$(this).data("OriginalInput");
				oinput.checked=false;
				
				$(this.parentNode).remove();
				
				return false;
			}
		});
	}
};
$(document).click(function (evt) {
	var node=evt.target;
	if (!AP.current.autoHide) return;
	if (!jQuery.contains(document.body,node)) return;
	if (!AP.current) return;
	if (node==AP.current.input[0]) return;
	if (jQuery.contains(AP.current.layer[0],node) || node==AP.current.layer[0]) return;
	 
	AP.current.close();
});
$.fn.extend({
	addressPicker:function (args) {
		this.each(function () {
			var $this=$(this);
			args.input=$this;
			var ap=new AddressPicker(args);
			$this.data("AddressPickerInstance",ap);
			$this.click(function () {
				ap.show();
			});
		});
		
		
		return this;
	}
});


var ad=_AddressData,AreaData={//查询数据
	getProvinces:function () {
		return ad.p;
	},
	getSubArea:function (aid) {
		return ad.a[aid];
	},
	getById:function (aid) {
		
	}
};

})(jQuery);