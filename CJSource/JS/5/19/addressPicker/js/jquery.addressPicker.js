//jQuery.addressPicker.js
/**
 * 一个简单的jQuery AddressPicker 插件
 * @category PL4CJ.ORG
 * @author CJ szcjlssx@gmail.com
 * @link http://www.pl4cj.org
 * 
 */

 
/**
参数说明
	
	maxSelectCount Number 最多能选择的个数
	style Object 应用到最外的容器层上的样式
	
	title String 大标题
	subTitle String 第二个标题
	bottomTitle String 第三个标题
	
	colWidth Number 列宽
	
	autoHide Boolean 当失去焦点时(指鼠标在页面其它地方点击时)自动隐藏
	buttonText String 关闭按钮的文字
	maxSelectCount Number 最多能选择的个数

*/
(function ($) {

	//ADDRESS_DATA_PATH String 存储省市县数据的JSON文件的路径
	var ADDRESS_DATA_PATH='js/address_db.js',undefined,
	//ADB为以ID为下标的数组,ADBA为以名字为下标的数组
	ADB=null,ADBA={},AddressPickerElements=null;
	
	function getAddressData(data) {
		if (ADB || (ADB=data)) {
			return ADB;
		}
		$.getJSON(ADDRESS_DATA_PATH,arguments.callee);
	}
	getAddressData();
	
	//$('#id').addressPicker({opt:optValue});形式调用
	$.fn.extend({
		addressPicker:function (opt) {
			this.each(function () {
				var $this=$(this);
				!opt && (opt={});
				var pos=$this.position();
				!opt.style && (opt.style={});
				opt.style.left=opt.style.left || pos.left;
				opt.style.top=opt.style.top || pos.top;
				opt.style.top+= $this.height()+6;
				opt.colWidth=100;
				if (!$this.data('addressPickerInstance')) {
					$this.addClass('MultiSelectorInput');
					$this.attr('readonly','readonly');
					$this.click(function (evt) {
						evt.stopPropagation();
						var $this=$(this);
						if ($this.data('addressPickerInstance')) {
							return $this.data('addressPickerInstance').show();
						}
						var picker=new AddressPicker(opt,$this);
						$this.data('addressPickerInstance',picker);
						picker.show();
						
					});
				}
			});
			
			return this;
		}
	});
	

	
	function AddressPicker(opt,element) {

		$.extend(this,opt);
		
		opt.onClose=function (items) {
			singleOnClose(items,element);
		};
		$.extend(this.originalOpt,opt);
		this.initAll();
	}
	AddressPicker.getChildren=function (data) {
		var root=new Address(data.cid);
		var children=root.getChildren(),
		items=[];
		for (var i in children) {
			items.push({
				text:children[i].name,
				cid:children[i].id,
				own:children[i].own,
				items:children[i].type==2?null:AddressPicker.getChildren
			});
		}
		return items;
	}
	function singleOnClose(items,element) {
		var values=[];
		for (var i in items) {
			values.push(i);
		}
		values=values.join(',');
		element.val(values);
	}
	AddressPicker.prototype={
		originalOpt:{
			title:"城市选择器",
			autoHide:true,
			maxSelectCount:3
		},
		selector:null,
		show:function () {
			this.selector.show();
			return this;
		},
		hide:function () {
			this.selector.hide();
			return this;
		},
		initAll:function () {
			var root=new Address('A1');
			cities=root.getSiblings(),
			items=[];
			for (var i in cities) {
				items.push({
					text:cities[i].name,
					cid:cities[i].id,
					own:cities[i].own,
					items:cities[i].type==2?null:AddressPicker.getChildren
				});
			}
			this.originalOpt.items=items;
			this.selector=new MultiSelector(this.originalOpt);
			this.show();
			
			
			
		}
	};
	
	


	
	//opt可以为地址的ID (A1形式) 也可以为城市名称
	function Address(opt) {
		if (Address.cache[opt]) return Address.cache[opt];
		var addr=opt.indexOf("A")<0?Address.getAddressByName(opt):Address.getAddressById(opt);
		$.extend(this,addr);
		Address.cache[this.id]=this;
	}
	$.extend(Address,{
		getAddressByName:function (name) {
			if (!ADBA[name]) {
				for (var i in ADB) {
					if (ADB[i][0]==name) {
						ADBA[name]={
							name:name,
							type:ADB[i][1],
							own:ADB[i][2],
							id:i
						};
					}
				}
			}
			return ADBA[name];
		},
		getAddressById:function (id) {
			if (!ADB[id]) return null;
			if (ADBA[ADB[id][0]]) {
				return ADBA[ADB[id][0]];
			}
			return ADBA[ADB[id][0]]={
				name:ADB[id][0],
				type:ADB[id][1],
				own:ADB[id][2],
				id:id
			};
		},
		cache:[]
	});
	Address.prototype.toString=Address.prototype.valueOf=function () {
		return this.name;
	};
	$.extend(Address.prototype,{
		children:null,//下级地区
		parent:null,//上级地区
		parents:null,//上级的所有地区
		siblings:null,//同级的所有地区
		address:"",//完整地址(格式:省-市-县)
		getChildren:function () {
			if (!this.children) {
				this.children=[];
				if (2==this.type) {
					return null;
				}
				for (var i in ADB) {
					if (this.id==ADB[i][2]) {
						this.children.push(new Address(i));
					}
				}
			}
			return this.children;
		},
		getParent:function () {
			if (!this.parent) {
				if (!this.own) return null;
				this.parent=new Address(this.own);
			}
			return this.parent;
		},
		getParents:function () {
			if (!this.parents) {
				if (!this.own) return null;
				var parents=[this.getParent()],p;
				if (p=parents[0].getParent()) {
					parents.unshift(p);
				}
				this.parents=parents;
			}
			return this.parents;
		},
		getAddress:function () {
			if (!this.address) {
				var parents=this.getParents(),a;
				if (!parents) a=this.name;
				else {
					parents.push(this);
					a=parents.join("-");
				}
			}
			return this.address=a;
		},
		getSiblings:function () {
			if (!this.siblings) {
				this.siblings=[];
				for (var i in ADB) {
					if (this.own==ADB[i][1]) {
						this.siblings.push(new Address(i));
					}
				}
			}
			return this.siblings;
		}
		
	});
})(jQuery);