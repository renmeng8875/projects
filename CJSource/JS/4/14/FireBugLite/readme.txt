FireBugLite 1.2
使用方法:
	1,针对本地文件调试!(即调试使用file:///协议访问的网页文件)
	将FireBugLite文件夹放在本地磁盘某个位置,然后获取路径,如:
	file:///E:/Web/FireBugLite/
	修改下面的代码,script之间的部分
	<script>
	javascript:
	(function (){
		/*fireBugPath是fireBug核心文件的路径,需要替换成对应路径
		核心JS文件可以使用firebug-lite.js,这是未压缩版的
		也可以使用firebug-lite-compressed.js,这是经过压缩的,体积更小
		汉化版的文件则为firebug-lite-cn.js
		个人是使用的未压缩的,方便调试*/
		var fireBugPath='file:///E:/Web/FireBugLite/';
		var fireBugJSPath=fireBugPath+'firebug-lite.js';
		var fireBugCSSPath =fireBugPath+'firebug-lite.css';
		var firebugLoader=document.createElement('script');
		firebugLoader.setAttribute('src',fireBugJSPath);
		firebugLoader.setAttribute('charset',"GBK");//中文的则要加上此句
		document.body.appendChild(firebugLoader);
		(function(){
			if(window.firebug && window.firebug.version){
				window.firebug.env.css=fireBugCSSPath;
				window.firebug.init();
			}else{
				setTimeout(arguments.callee);
			}
		})();
		firebugLoader=null;
	})();
	</script>
	修改完成后,将上面的代码去掉注释,压缩至一行,如:
	javascript:(function (){var fireBugPath='file:///E:/Web/FireBugLite/';var fireBugJSPath=fireBugPath+'firebug-lite.js';var fireBugCSSPath =fireBugPath+'firebug-lite.css';var firebugLoader=document.createElement('script');firebugLoader.setAttribute('src',fireBugJSPath);firebugLoader.setAttribute('charset',"GBK");document.body.appendChild(firebugLoader);(function(){if(window.firebug && window.firebug.version){window.firebug.env.css=fireBugCSSPath;window.firebug.init();}else{setTimeout(arguments.callee);}})();firebugLoader=null;})();
	/*在线版(英文)的链接则为:
	 *javascript:var firebug=document.createElement('script');firebug.setAttribute('src','http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js');document.body.appendChild(firebug);(function(){if(window.firebug.version){firebug.init();}else{setTimeout(arguments.callee);}})();void(firebug);
	 */
	然后在浏览器中,将其添加到收藏夹中,当某个页面需要调试时,点击收藏夹中链接就出现FireBugLite了
	由于安全限制,Safari下面添加不进Bookmarks,不过Safari自己的调试器也很强大了
	打开Safari Console:Edit->Preferences->Advanced->勾选下面的"Show Develop menu in menu bar"
	然后显示菜单栏:配置(就地址栏旁那个齿轮形图标)->Show Menu Bar(如果已经显示了,则没有此项了)
	菜单栏-->Develop-->Show Error Console
	注:此FireBugLite文件夹下有一个收藏夹链接,只要在属性里面将fireBugPath的值修改下就行了

	如果是通过HTTP协议访问,则需要将FireBugLite放到服务器上(因为远程的页面不能加载本地的文件)
	如果是使用的在线版,则无此限制
	推荐:如果已经安装了IIS或Apache,可将FireBugLite放到www目录中,然后通过HTTP协议访问,将
	fireBugPath修改为"http://localhost:8080/FireBugLite/"
	
针对服务器环境下的调试,可将FireBugLite文件夹放在站点下面,在需要调试的页面中引用JS和CSS文件:
<script type="text/javascript" charset="GBK" src="FireBugLite/firebug-lite-cn.js"></script>
<style type="text/css">@import url("FireBugLite/firebug-lite.css");</style>
然后配置其CSS文件位置.最后调用init方法,就行了
<script>
	if (window.firebug && window.firebug.version)  {
		/*firebug.env是用来存放配置信息的,如
		 *如height是FireBugLite窗口的高度
		 *window.firebug.env.css="FireBugLite/firebug-lite.css";//也可以使用这种方法配置CSS文件的位置
		 *在"选项"中修改的配置信息会存储在Cookie中
		 */
		window.firebug.init();
	}
</script>

注意事项:
	FireBugLite在非标准的HTML页面会无法使用,必须在标准的XHTML(过渡型,严格型等都可以)页面中使用
	
	
FireBugLite 一些调试时用到的API简介
	API访问路径firebug.d.console.cmd[*]
	如果选择了"override window.console" -"覆盖window.console"(或指定参数:firebug.env.override=true)
	则可以以window.console[*] 的方式调用方法
	log,warn,info,debug,error 这几个方法差不多,传入一个字符串参数或对象,FireBugLite将其打印在控制台中
	只是打印字符串所使用的样式不同而已(分类加上和方法名一样的类名)
	如果传入的是对象(console.log(obj)),则会将对象以 :
	Object attribute1="value1" attribute2="value2"
	的形式打印出来,并且点击可以DOM栏中查看
	dir方法,列举出对象的属性
	dirxml方法,与log方法一样
	
	以上方法在安装并开启FireBug插件的火狐浏览器中也皆可用
	
文件夹下面的minifier.py文件是一个用Python编写的JavaScript压缩工具,安装了Python后可以使用
用法:在终端输入 "python minifier.py firebug-lite.js firebug-lite-compressed.js" Enter
此压缩工具仅仅是去掉换行,缩进制表符及注释
	
	
	
	