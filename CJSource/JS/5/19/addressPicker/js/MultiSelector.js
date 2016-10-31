//import jQuery
/**
MultiSelector.js 多级选择器
*/


/**
opt 构造参数
	title String 大标题
	subTitle String 第二个标题
	bottomTitle String 第三个标题
	style Object 应用到最外的容器层上的样式
	colWidth Number 列宽
	items Array/Function 主区域元素,
	结构:[{text:"文本",value:"值",style:"CSS样式",items:[子元素,如为空则不往下移]}....] 
	没有value时,将使用text作为value
	可以使用更简单的结构["文本","Text"],以用于text和value相同的情况
	如果为函数,则其应该返回和上述结构一样的对象
	
	autoHide Boolean 当失去焦点时(指鼠标在页面其它地方点击时)自动隐藏
	buttonText String 关闭按钮的文字
	maxSelectCount Number 最多能选择的个数
	onPick Function 选定时的事件处理函数 接收参数->1.evt对象 evt.data为MultiSelector实例
	onClose Function 关闭时的事件处理函数 接收参数->1.evt对象 evt.data为MultiSelector实例
*/
(function ($) {
//Start Scope
if (!window.MultiSelector) window.MultiSelector=MultiSelector;
$(document).click(function (evt) {
	if (!$(evt.target).parents().is("div.MultiSelector")) {
		var id=$(evt.target).parents('div.MultiSelector').attr('id');
		autoHideEles(id);
	}
});

function autoHideEles(beside) {
	var eles=MultiSelector.autoHideElements;
	for (var i in eles) {
		if (eles[i] instanceof MultiSelector && eles[i].id!=beside) {
			eles[i].hide();
		}
	}
}
function MultiSelector(opt) {
	$.extend(this,opt);
	this.originalItems=opt.items;
	this[0]=$(MultiSelector.Element);
	this[0].find('MultiSelector').attr('id',this.id=MultiSelector.gid());
	this.initContainer()
	.setTitle()
	.setSubTitle()
	.setBottomTitle()
	.setButtonText()
	.setStyle()
	.renderItems()
	.autoHide && (MultiSelector.autoHideElements[this.id]=this);
	$(document.body).append(this[0]);
	
}
$.extend(MultiSelector,{
	autoHideElements:[],
	gidCounter:0,
	gid:function () {return 'MultiSelector_'+(this.gidCounter++);},
	Element:"<div class='MultiSelector'>\
	<h3><a name='MultiSelector' class='closeButton'></a><em class='title'></em></h3>\
	<h4><a name='SelectorBack' class='subTitle'> </a></h4>\
	<dl class='SelectorArea'><dd class='itemsContainer'>\
	</dd></dl>\
	<h4><span class='bottomTitle'></span><em>(点击可以取消选择)</em></h4>\
	<div class='selectedItemsContainer'>\
	</div>\
	<h3><a name='MultiSelector' class='closeButton'></a></h3>\
	</div>"
});


MultiSelector.EmptyFn=function () {};
MultiSelector.prototype={
	onPick:MultiSelector.EmptyFn,
	onClose:MultiSelector.EmptyFn,
	originalItems:null,
	title:"选择器",
	subTitle:"&lt;&lt; 返回",
	bottomTitle:"您已经选择了:",
	autoHide:true,
	buttonText:"[确定]",
	maxSelectCount:3,
	selectedItems:{},
	itemsChildren:{},
	colWidth:100,
	cols:4,
	hide:function () {
		$('select').show();
		if (!this[0].position().left) return;
		
		this.onClose(this.selectedItems)!==false && this[0].hide();
		return this;
	},
	show:function () {$('select').hide();this[0].show();return this;},
	setTitle:function (t) {this.titleContainer.html(t || this.title);return this;},
	setSubTitle:function (t) {this.subTitleContainer.html(t || this.subTitle);return this;},
	setBottomTitle:function (t) {this.bottomTitleContainer.html(t || this.bottomTitle);return this;},
	setButtonText:function (t) {this.closeButtons.html(t || this.buttonText);return this;},
	setStyle:function (s) {this[0].css(s || this.style);return this;},
	appendItem:function (items) {
		this.itemsContainer.remove('br');
		this.itemsContainer.append(items);
		this.itemsContainer.append("<br style='float:none;clear:both;' />");
	},
	appendSelectedItem:function (index,item) {
		var sitems=this.selectedItems,c=this.selectedItemsContainer;
		if (c.find("label").length >= this.maxSelectCount) {
			if (item.parent()[0].tagName=='DT') {
				item.parent().next('DD').find('input').attr('disabled','');
			}
			return alert("最多只能选择"+this.maxSelectCount+"项!");
		} else if (!sitems[index]) {
			sitems[index]=item.clone();
			sitems[index].find("input")[0].checked=true;
			sitems[index].relatedItem=item;
			sitems[index].bind("click",{item:sitems[index],index:index,_this:this},cancelSelectItem);
			c.remove('BR');
			c.find('br').remove();
			c.append(sitems[index]);
			c.append('<br style="float:none;clear:both;" />');
		}
		return true;
	},
	delSelectedItem:function (index,item) {
		var sitems=this.selectedItems,p;
		if (sitems[index]) {
			sitems[index].relatedItem.find('INPUT')[0].checked=false;
			p=sitems[index].relatedItem.parent();
			if (p[0].tagName=='DT') {
				p.next('DD').find('input').attr('disabled','');
			}
			sitems[index].remove();
			delete sitems[index];
		}
		return true;
	},
	renderItems:function (extra) {
		var nodes=document.createDocumentFragment(),one,txt,val,items=this.items,itemsChildren=this.itemsChildren,
		extra=extra || '',colWidth=this.colWidth;
		for (var i=0,len=items.length;i<len;i++) {
			txt=items[i].text || items[i];
			val=(items[i].value || txt );
			if (this.selectedItems[val]) {
				this.selectedItems[val].relatedItem=$("<label style='width:"+colWidth+"px'><input name='MultiSelectorInput' type='checkbox' checked='checked' "+extra+" value='"+val+"' /> "+txt+"</label>");
				nodes.appendChild(this.selectedItems[val].relatedItem[0]);
			} else {
				nodes.appendChild($("<label style='width:"+colWidth+"px'><input name='MultiSelectorInput'  type='checkbox' "+extra+" value='"+val+"' /> "+txt+"</label>")[0]);
			}
			itemsChildren[val]={children:items[i].items,own:i};
		}
		this.appendItem(nodes);
		return this;
	},
	initContainer:function () {
		this.selectedItems={};
		this[0].width(this.colWidth*this.cols+20);
		this.selectedItemsContainer=this[0].find('div.selectedItemsContainer');
		this.titleContainer=this[0].find("em.title");
		(this.subTitleContainer=this[0].find("a.subTitle")).bind('click',this,selectorBack);
		this.bottomTitleContainer=this[0].find("span.bottomTitle");
		this.selectorArea=this[0].find('dl.SelectorArea');
		
		(this.closeButtons=this[0].find("a.closeButton")).bind('click',this,hideSelector);
		
		(this.itemsContainer=this[0].find('dd.itemsContainer')).bind('click',this,checkItems);
		

		
		
		return this;
	}
};
function hideSelector(evt) {
	evt.data.hide();
}
function checkItems(evt) {
	var selector=evt.data,input=evt.target,items;
	selector.onPick.apply(this,arguments);
	if (input.tagName!='INPUT') return;
	var label=$(input).parent(),val=input.value;
	if (label.parent()[0].tagName=='DT') {
		label.parent().next('dd').find('label input').each(function () {
			if (this.checked) {
				selector.delSelectedItem(this.value,$(this).parent());
			}
			this.checked=false;
			this.disabled=input.checked;
			
		});
		if (input.checked) {
			!selector.appendSelectedItem(val,label) && evt.preventDefault();
		} else {
			selector.delSelectedItem(val,label);
		}
	} else if (selector.itemsChildren[val].children) {
	
		if (!window.XMLHttpRequest) {
			//input.checked=!input.checked;//CJ MOD
		}
	
		
		
		
		if ($.isFunction(selector.itemsChildren[val].children)) {
			items=selector.itemsChildren[val].children(selector.items[selector.itemsChildren[val].own]);
		} else {
			items=selector.itemsChildren[val].children;
		}
		
		if (!items.length) {
			if (input.checked) {
				!selector.appendSelectedItem(val,label) && evt.preventDefault();
			} else {
				selector.delSelectedItem(val,label);
			}
			return;
		}
		evt.preventDefault();
		selector.itemsChildren[val].children=items;
		var dl=$("<dl><dt><em>(选择此大类，将包括以下所有小类)</em></dt><dd></dd></dl>");
		
		var beforeChecked=input.checked;
		label.parent().empty().append(dl);
		dl.find('dt').prepend(label);
		label.find('input')[0].checked=beforeChecked;
		
		selector.items=selector.itemsChildren[val].children;
		selector.itemsContainer=dl.find('dd');
		selector.renderItems(!input.checked && 'disabled="disabled"');
		label.data('itemsDD',selector.itemsContainer);
		label.data('items',selector.items);
	} else {
		if (input.checked) {
			!selector.appendSelectedItem(val,label) && evt.preventDefault();
		} else {
			selector.delSelectedItem(val,label);
		}
		
	}

}
function cancelSelectItem(evt) {
	evt.data._this.delSelectedItem(evt.data.index,evt.data.item);
}
function selectorBack(evt) {
	var selector=evt.data,extra='',
	area=selector.selectorArea,
	c=selector.itemsContainer,
	dt=area.find('dt');
	if (dt.length==1) {
		selector.items=selector.originalItems;
		selector.itemsContainer=area.children().empty();
		selector.renderItems();
	} else if (dt.length > 1){
		label=c.parent().parent().prev('dt').find('label');
		selector.items=label.data('items');
		selector.itemsContainer=c.parent().parent().empty();
		//selector.itemsContainer.replaceWith(label.data('items'));
		if (label.find('input')[0].checked) extra='disabled="disabled"';
		selector.renderItems(extra);
	}
	return false;
}



//End Scope
})(jQuery);
