String.prototype.trim = function () {
	return this.ltrim().rtrim();
};
String.prototype.ltrim = function () {
	return this.replace(arguments.callee.re,"");
};
(String.prototype.ltrim.re = new RegExp()).compile("^\\s+");
String.prototype.rtrim = function () {
	return this.replace(arguments.callee.re,"");
};
(String.prototype.rtrim.re = new RegExp()).compile("\\s+$");
String.prototype.ftrim = function () {
	return this.replace(arguments.callee.re,"");
};
(String.prototype.ftrim.re = new RegExp()).compile("\\s","g");
String.prototype.nl2br = function () {
	return this.replace(arguments.callee.re,"<br />");
};
(String.prototype.nl2br.re = new RegExp()).compile("\\r\\n|\\n\\r|\\r|\\n","g");
String.prototype.isEmail = function () {
	//var re = /^\w{1,25}(?:@(?!-))(?:(?:[a-z0-9-]*)(?:[a-z0-9](?!-))(?:\.(?!-)))+[a-z]{2,4}$/;
	return arguments.callee.re.test(this);
};
(String.prototype.isEmail.re = new RegExp()).compile("^\\w{1,15}(?:@(?!-))(?:(?:[a-z0-9-]*)(?:[a-z0-9](?!-))(?:\\.(?!-)))+[a-z]{2,4}$","i");
