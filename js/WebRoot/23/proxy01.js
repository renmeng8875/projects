/**
 * 代理模式的定义
 * 代理是一个对象(proxy)用它来控制目标对象的访问
 * 他要是先与目标对象相同的接口,但是他不同于装饰者模式
 * 他对目标对象不进行任何修改.
 * 他的目的在于延缓"复杂"对象的初始化时间
 * 这样可以在用到这个目标对象的时候再初始化他(对于单例来讲更是重要)
 */
(function(){
	var bookShop= new Interface("bookShop",[
		"addBook","findBook","chechBook","returnBook"
	])
	//图书类
	var Book = function(bid,bName,bPrice){
		this.bid = bid;
		this.bName = bName;
		this.bPrice = bPrice;
	}
	//目标类
	var myBookShop = (function(){
		//书店里的书
		var books = {};
		return function(bks){
			if(typeof bks == "object"){
				books = bks;
			}
			//加书
			this.addBook = function(book){
				books[book.bid] = book;
			
			}
			//找书
			this.findBook = function(bid){
				//这块后面课程我们用责任链模式改写
				if(books[bid]){
					return books[bid];
				}else{
					return null;
				}
			}
			//还书
			this.returnBook = function(book){
				this.addBook(book);
			}
			//借书
			this.chechBook = function(bid){
				var book = this.findBook(bid);
				return book;
			}
		}
	})();
	//普通代理
	var myBookShopProxy = function(bks){
		var obj = new myBookShop(bks);
			//加书
			this.addBook = function(book){
				obj.addBook(book)
			}
			//找书
			this.findBook = function(bid){
				return obj.findBook(bid);
			}
			//还书
			this.returnBook = function(book){
				obj.returnBook(book);
			}
			//借书
			this.chechBook = function(bid){
				return obj.chechBook(bid);
			}		
	}
	var proxy = new myBookShopProxy({
		"001":new Book("001","EXTJS","45"),
		"002":new Book("002","JS","60")
	})
	alert(proxy.chechBook("001").bName)
	//这个代理是我们严格安装定义来写的
	//一般开发中不会用到,应为他没什么意义
})()




