<?php
//echo time();
//echo json_encode($_POST);
echo $_GET["callback"]."(".json_encode($_GET).");";
?>