/**
 * 责任链模式
 * 消除发送这与接收者之间的耦合
 * 1.责任链的发送者知道链的入口是谁
 * 2.每一个链节点知道自己的下一个节点是谁
 * 3.每一个链的传入和传输值是一样的
 */
(function(){
	//扩展window
	window.setSuccessor = function(after,before){
		after.successor = before;
	}
	//用于拷贝字符串的方法20*"-" ----------
	window.cpoyStr = function(num,str){
		var newStr = "";
		for (var i = 0; i < num; i++) {
			newStr = newStr+str;
		}
		return newStr;
	}
	//书店找书,和加书两个需求
	//用户可以输入,关键字找书(书号,作者,名称)
	//不同书架管理员管理自己的书籍添加任务
	var bookShop = new Interface("bookShop",
			["addBook","findBook","showBooks"]);
	//书
	var Book = function(bBm,bName,bAuthor,bType){
		this.bBm = bBm;
		this.bName = bName;
		this.bAuthor = bAuthor;
		this.bType = bType;
	}
	//书店
	var pcatBookShop = (function(){
		//书架
		var jsBooks = new Array();//js书架
		var cBooks = new Array();//c书架
		var javaBooks = new Array();//java书架
		//内部类1
		function AddJsBooks(book){
			if(book.bType == "JS"){
				jsBooks.push(book)
			}else{
				//负责传递到下一个功能
				AddJsBooks.successor(book);
			}		
		}
		//内部类2
		function AddJavaBooks(book){
			if(book.bType == "JAVA"){
				javaBooks.push(book)
			}else{
				//负责传递到下一个功能
				AddJavaBooks.successor(book);
			}		
		}
		//内部类3
		function AddCBooks(book){
			if(book.bType == "C"){
				cBooks.push(book)
			}else{
				//负责传递到下一个功能
				AddCBooks.successor(book);
			}		
		}		
		//设置责任链
		setSuccessor(AddJsBooks,AddJavaBooks);
		setSuccessor(AddJavaBooks,AddCBooks);
		/**********查询书籍************/
		var bookList  = null;
		function FindByBbm(keyword){
			//连的头部来初始化参数
			if(!bookList){
				bookList = jsBooks.concat(cBooks).concat(javaBooks);
			}
			var book = new Array();
			book = bookList.filter(function(book){
				if(book.bBm.indexOf(keyword) != -1){
					return true;
				}else{
					return false;
				}
			});
			//我要进行链式查询
			return book.concat(FindByBbm.successor(keyword,book));
			//return book;
		}
		function FindByName(keyword,book){
			var book = book;
			book = bookList.filter(function(book){
				if(book.bName.indexOf(keyword) != -1){
					return true;
				}else{
					return false;
				}
			});			
			return book;
		}
		//规划责任链
		setSuccessor(FindByBbm,FindByName);
		//***************真正的图书馆类*****
		return function(){
			this.addBook = function(book){
				if(book instanceof Book){
					//因为我知道谁是链的入口
					AddJsBooks(book);
				}
			}
			this.findBook = function(keyword){
				return FindByBbm(keyword);
			}
			this.showBooks = function(){
				document.write("JS类图书"+jsBooks.toSource()+"<br>");
				document.write("Java类图书"+javaBooks.toSource()+"<br>");
				document.write("C类图书"+cBooks.toSource()+"<br>");
				//python 中 20*"-"
				document.write(cpoyStr(60,"-")+"<br>");
			}
		}
	})();
	var pb = new pcatBookShop();
	pb.addBook(new Book("00101","EXTJS4.1","USPCAT","JS"));
	pb.addBook(new Book("00102","JAVA","JIM","JAVA"));
	pb.addBook(new Book("00103","HIBERNATE","JIM","JAVA"));
	pb.addBook(new Book("00201","C#","YUNFENGCHENG","C"));
	pb.addBook(new Book("00202","C++/C","YUNFENGCHENG","C"));
	pb.addBook(new Book("00301","JAVASCRIPT","USPCAT","JS"));
	//展示
	pb.showBooks();
	//查询
	document.write(pb.findBook("C").toSource())
})()


