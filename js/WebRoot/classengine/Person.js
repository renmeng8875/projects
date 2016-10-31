//中间阶段测试
var p = Ext.define("Person",{
		extend:"User",
		name:"USPCAT",
			config:{
				sex : "M"
			},		
		statics:{
			show:function(){
				alert("uspcat.com")
			}
		},
		mixin:{
			mix : "Mixin"
		},
		age:1
	})
//p.show()