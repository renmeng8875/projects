<?php

file_put_contents("test.txt",date("H:i:s"));
header("Content-Type:text/xml");
//header("Cache-Control: no-cache, must-revalidate");//�������������������
echo "<?xml version='1.0' encoding='GBK'?>
<root>
".print_r($_GET,true)."
</root>";


/*header("Content-Encoding:gzip");
header("Content-type:image/jpeg");
echo gzencode(file_get_contents("a.jpg"),9);*/


?>