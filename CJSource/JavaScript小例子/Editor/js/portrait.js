function getPortraitTable(cols,caption,cn) {
	var PortraitText = ["Î¢Ğ¦","Æ²×ì","É«","·¢´ô","µÃÒâ","Á÷Àá","º¦Ğß","±Õ×ì","Ë¯","´ó¿Ş","ŞÏŞÎ","·¢Å­","µ÷Æ¤","ßÚÑÀ","¾ªÑÈ","ÄÑ¹ı","¿á","Àäº¹","×¥¿ñ","ÍÂ","ÍµĞ¦","¿É°®","°×ÑÛ","°ÁÂı","¼¢¶ö","À§","¾ª¿Ö","Á÷º¹","º©Ğ¦","´ó±ø","·Ü¶·","ÖäÂî","ÒÉÎÊ","Ğê...","ÔÎ","ÕÛÄ¥","Ë¥","÷¼÷Ã","ÇÃ´ò","ÔÙ¼û","²Áº¹","¿Ù±Ç","¹ÄÕÆ","ôÜ´óÁË","»µĞ¦","×óºßºß","ÓÒºßºß","¹şÇ·","±ÉÊÓ","Î¯Çü","¿ì¿ŞÁË","ÒõÏÕ","Ç×Ç×","ÏÅ","¿ÉÁ¯"];
	var table = document.createElement("table");
	if (cn) {table.className = cn;}
	if (caption) {table.createCaption().innerHTML = caption;}
	var PortraitNum = PortraitText.length;
	var rowsNum = Math.ceil(PortraitNum/cols);
	var row;
	var cell;
	var img;
	var index;
	for (var i=0;i<rowsNum;i++) {
		row = table.insertRow(i);
		for (var j=0;j< cols;j++) {
			index = cols*i+j;
			if (PortraitText[index]) {
				cell = row.insertCell(j);
				img = document.createElement("img");
				img.title= PortraitText[index];
				img.alt =PortraitText[index];
				img.src="images/portrait/"+index+".gif";
				cell.appendChild(img);
			} else {
				break;
			}
		}
	}
	return table;
}