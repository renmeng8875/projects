<?php

//ÈÃä¯ÀÀÆ÷²»»º´æ
header("Cache-Control: must-revalidate");
header('Cache-Control: public, max-age=0');
header('Expires: '.gmdate('D, d M Y H:i:s',time()).' GMT');
header('Last-Modified: '.gmdate('D, d M Y H:i:s',time()).' GMT');
header("Pragma: no-cache");



//»º´æ
header("Cache-Control: max-age=999999");
header('Expires: '.gmdate('D, d M Y H:i:s',time()+999999).' GMT');

?>