FireBugLite 1.2
ʹ�÷���:
	1,��Ա����ļ�����!(������ʹ��file:///Э����ʵ���ҳ�ļ�)
	��FireBugLite�ļ��з��ڱ��ش���ĳ��λ��,Ȼ���ȡ·��,��:
	file:///E:/Web/FireBugLite/
	�޸�����Ĵ���,script֮��Ĳ���
	<script>
	javascript:
	(function (){
		/*fireBugPath��fireBug�����ļ���·��,��Ҫ�滻�ɶ�Ӧ·��
		����JS�ļ�����ʹ��firebug-lite.js,����δѹ�����
		Ҳ����ʹ��firebug-lite-compressed.js,���Ǿ���ѹ����,�����С
		��������ļ���Ϊfirebug-lite-cn.js
		������ʹ�õ�δѹ����,�������*/
		var fireBugPath='file:///E:/Web/FireBugLite/';
		var fireBugJSPath=fireBugPath+'firebug-lite.js';
		var fireBugCSSPath =fireBugPath+'firebug-lite.css';
		var firebugLoader=document.createElement('script');
		firebugLoader.setAttribute('src',fireBugJSPath);
		firebugLoader.setAttribute('charset',"GBK");//���ĵ���Ҫ���ϴ˾�
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
	�޸���ɺ�,������Ĵ���ȥ��ע��,ѹ����һ��,��:
	javascript:(function (){var fireBugPath='file:///E:/Web/FireBugLite/';var fireBugJSPath=fireBugPath+'firebug-lite.js';var fireBugCSSPath =fireBugPath+'firebug-lite.css';var firebugLoader=document.createElement('script');firebugLoader.setAttribute('src',fireBugJSPath);firebugLoader.setAttribute('charset',"GBK");document.body.appendChild(firebugLoader);(function(){if(window.firebug && window.firebug.version){window.firebug.env.css=fireBugCSSPath;window.firebug.init();}else{setTimeout(arguments.callee);}})();firebugLoader=null;})();
	/*���߰�(Ӣ��)��������Ϊ:
	 *javascript:var firebug=document.createElement('script');firebug.setAttribute('src','http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js');document.body.appendChild(firebug);(function(){if(window.firebug.version){firebug.init();}else{setTimeout(arguments.callee);}})();void(firebug);
	 */
	Ȼ�����������,������ӵ��ղؼ���,��ĳ��ҳ����Ҫ����ʱ,����ղؼ������Ӿͳ���FireBugLite��
	���ڰ�ȫ����,Safari������Ӳ���Bookmarks,����Safari�Լ��ĵ�����Ҳ��ǿ����
	��Safari Console:Edit->Preferences->Advanced->��ѡ�����"Show Develop menu in menu bar"
	Ȼ����ʾ�˵���:����(�͵�ַ�����Ǹ�������ͼ��)->Show Menu Bar(����Ѿ���ʾ��,��û�д�����)
	�˵���-->Develop-->Show Error Console
	ע:��FireBugLite�ļ�������һ���ղؼ�����,ֻҪ���������潫fireBugPath��ֵ�޸��¾�����

	�����ͨ��HTTPЭ�����,����Ҫ��FireBugLite�ŵ���������(��ΪԶ�̵�ҳ�治�ܼ��ر��ص��ļ�)
	�����ʹ�õ����߰�,���޴�����
	�Ƽ�:����Ѿ���װ��IIS��Apache,�ɽ�FireBugLite�ŵ�wwwĿ¼��,Ȼ��ͨ��HTTPЭ�����,��
	fireBugPath�޸�Ϊ"http://localhost:8080/FireBugLite/"
	
��Է����������µĵ���,�ɽ�FireBugLite�ļ��з���վ������,����Ҫ���Ե�ҳ��������JS��CSS�ļ�:
<script type="text/javascript" charset="GBK" src="FireBugLite/firebug-lite-cn.js"></script>
<style type="text/css">@import url("FireBugLite/firebug-lite.css");</style>
Ȼ��������CSS�ļ�λ��.������init����,������
<script>
	if (window.firebug && window.firebug.version)  {
		/*firebug.env���������������Ϣ��,��
		 *��height��FireBugLite���ڵĸ߶�
		 *window.firebug.env.css="FireBugLite/firebug-lite.css";//Ҳ����ʹ�����ַ�������CSS�ļ���λ��
		 *��"ѡ��"���޸ĵ�������Ϣ��洢��Cookie��
		 */
		window.firebug.init();
	}
</script>

ע������:
	FireBugLite�ڷǱ�׼��HTMLҳ����޷�ʹ��,�����ڱ�׼��XHTML(������,�ϸ��͵ȶ�����)ҳ����ʹ��
	
	
FireBugLite һЩ����ʱ�õ���API���
	API����·��firebug.d.console.cmd[*]
	���ѡ����"override window.console" -"����window.console"(��ָ������:firebug.env.override=true)
	�������window.console[*] �ķ�ʽ���÷���
	log,warn,info,debug,error �⼸���������,����һ���ַ������������,FireBugLite�����ӡ�ڿ���̨��
	ֻ�Ǵ�ӡ�ַ�����ʹ�õ���ʽ��ͬ����(������Ϻͷ�����һ��������)
	���������Ƕ���(console.log(obj)),��Ὣ������ :
	Object attribute1="value1" attribute2="value2"
	����ʽ��ӡ����,���ҵ������DOM���в鿴
	dir����,�оٳ����������
	dirxml����,��log����һ��
	
	���Ϸ����ڰ�װ������FireBug����Ļ���������Ҳ�Կ���
	
�ļ��������minifier.py�ļ���һ����Python��д��JavaScriptѹ������,��װ��Python�����ʹ��
�÷�:���ն����� "python minifier.py firebug-lite.js firebug-lite-compressed.js" Enter
��ѹ�����߽�����ȥ������,�����Ʊ����ע��
	
	
	
	