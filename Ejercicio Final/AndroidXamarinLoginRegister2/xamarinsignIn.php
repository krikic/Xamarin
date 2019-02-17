<?php
	mysql_connect("127.0.0.1","root","");
	mysql_select_db("codeclogin");
if(isset($_POST['uemail']) && isset($_POST['pass'])){
		$SignInEmail  = $_POST['uemail'];
		$SignInPass = $_POST['pass'];
		
		$user_query = mysql_query("select * from newuser where email='$SignInEmail' AND password='$SignInPass'");
		
		if(mysql_fetch_row($user_query)){
			$response["success"]=1;
			echo json_encode($response);
		}
		else{
			$response["error"]=2;
			echo json_encode($response);
		}
}
else{
	$response["error"]=3;
	echo json_encode($response);
}
	
?>