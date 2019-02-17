<?php

	header('Content-type: application/json');
	mysql_connect("127.0.0.1","root","");
	mysql_select_db("codeclogin");
		$name = $_POST['uName'];
		$email = $_POST['uEmail'];
		$pass = $_POST['uPass'];
		
		$query = mysql_query("insert into newuser VALUES(DEFAULT,'$name','$email','$pass')");
		if($query){
			$response["success"]=1;
			echo json_encode($response);
		}
		else{
			$response["error"]=2;
			echo json_encode($response);
		}
?>