/**
 * 函数使用技巧
 */
(function(){
	//代理函数-->用程序来决定返回的新的函数(他是一个生产函数的函数)
	//模拟数据库
	var person = {"jim":"m","lili":"w"}
	var test = function(name){
		if(person[name] == "m"){
		
			/**
			 * 内科,外科
			 */
			return function(nk,wk){
				alert(nk+"  "+wk)
			}
			
		}else if(person[name] == "w"){
			/**
			 * 内科,外科,妇科
			 */
			return function(nk,wk,fk){
				alert(nk+"  "+wk+" "+fk)
			}			
		}
	}
	test("jim")("ok","ok")
	test("lili")("ok","ok","no")
})()







