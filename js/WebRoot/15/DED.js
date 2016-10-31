//一个简单的观察者的应用
(function(){
	//1.我们利用空对象来设立命名空间
	window.DED = window.DED||{};
	DED.util = DED.util || {}
	//观察者
	DED.util.Observer = function(){
		this.fns = []
	}
	//扩展他
	DED.util.Observer.prototype = {
		//观察
		subscribe : function(fn){
			this.fns.push(fn);
		},
		//取消观察
		unsubscribe : function(fn){
			this.fns = this.fns.filter(function(el){
				if(el != fn){
					return el;
				}
			})
		},
		//循环执行函数被观察的数组
		fire:function(o){
			this.fns.forEach(function(el){
				el(o);
			})
		}
	}
	//序列
	/**
	 * 使用了观察.序列就可以装在任何的对象,对象内部函数的调用方法不是有队列来完成
	 * 是有观察者来执行的
	 */
	DED.Queue = function(){
		//定义个空队列
		this.queue = [];
		//成功的观察
		this.onComplete = new DED.util.Observer();
		//失败的队列
		this.onFailure = new DED.util.Observer();
		//刷新观察
		//................................
		this.onFlush = new DED.util.Observer();
		//重复次数
		this.retryCount = 3;
		//当前执行次数
		this.currentRetry = 0;
		//停顿
		this.paused = false;
		//请求超时时间
		this.timeout = 5000;
		//链接对象
		this.conn = {};
		//计时器
		this.timer = {};
	}
	//静态函数添加
	DED.Queue.method("flush",function(){
		//刷新功能
		if(!this.queue.length >0){
			return;
		}
		//如果是停顿状态 他也不需要刷新
		if(this.paused){
			this.paused = false;
			return;
		}
		var self = this;
		//当执行接次数+1
		this.currentRetry++;
		var abort = function(){
			//可以通知一个XMLHttpRequest对象的Http请求
			self.conn.abort();
			if(self.currentRetry == self.retryCount){
				//执行失败过的序列
				self.onFailure.fire();
				//重置当前次数
				self.currentRetry = 0;
			}else{
				self.flush();
			}
		}//......................
			//计时器开始计时
			this.timer = window.setTimeout(abort,this.timeout);
			//准备回调函数
			var callback = function(o){
				//清楚计数器
				//....................................
				window.clearTimeout(self.timer);
				//把当前次数清零
				self.currentRetry = 0;
				//本着先进先出的原则,把队列反序排序
				self.queue.shift();
				//执行队列
				self.onFlush.fire(o.responseText);
				if(self.queue.length == 0){
					//如何队列等于0执行默认的成功队列
					self.onComplete.fire();
					return;
				}
				self.flush();
			}
			//改变链接对象
			this.conn = asyncRuquest(
				this.queue[0]['method'],
				this.queue[0]['uri'],
				callback,
				this.queue[0]['params']
			)
	}).method("setRetryCount",function(count){
		this.retryCount = count;
	}).method("setTimeout",function(time){
		this.timer = time;
	}).method("add",function(o){
		this.queue.push(o)
	}).method("pause",function(o){
		this.paused = true;
	}).method("clear",function(o){
		this.queue = [];
	}).method("dequeue",function(o){
		this.queue.pop();
	})
	
})()
















