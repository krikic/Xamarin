<?php
session_start();
include_once 'include/functions.php';

$user = new User();

$uid = $_SESSION['uid'];

if (!$user->get_session())
{
   header("location:index.php");
}
$q = $_GET['q'];
if ($q == 'logout') 
{
    $user->user_logout();
    header("location:main.php");
    
}


?>

<!doctype html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>jQuery-Based Mobile Web App Navigation - Hongkiat Demo</title>
	<meta name="author" content="Jake Rocheleau">
	<meta name="description" content="Small demo for a Sparrow-like jQuery mobile navigation menu. This is built using HTML5/CSS3 and should run properly in Mobile Safari + Android web browsers.">
    <meta name="keywords" content="tutorial, jquery, javascript, demo, sparrow, email, app, navigation, menu, sidebar, swipe, animated, design, coding">
    <link rel="shortcut icon" href="http://www.hongkiat.com/blog/favicon.ico">
	<link rel="icon" href="http://www.hongkiat.com/blog/favicon.ico">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link type="text/css" rel="stylesheet" media="all" href="css/style2.css">
	<link type="text/css" rel="stylesheet" media="all" href="css/responsive.css">
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script type="text/javascript" src="js/script.js"></script>
</head>

<body>

	<div id="w">
		
		<div id="pagebody">
			<header id="toolbarnav">
				<a href="#navmenu" id="menu-btn"></a>
			
				<h1>HK Mobile</h1>
			</header>
			
			<section id="content" class="clearfix">
				<h2>Welcome to the Mobile Site!</h2>
				
				<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed lorem sapien, auctor placerat varius sed, aliquam et nibh. Quisque posuere erat nec tortor vestibulum id dignissim quam ornare. Suspendisse sapien ante, pellentesque non interdum ac, feugiat at eros. Morbi lacus augue, blandit ac porta a, rutrum quis tellus. Nam ut velit lorem, sit amet placerat lorem.</p>
				
				<img src="img/hongkiat-lim.png" alt="Hongkiat Lim" class="photo">
				
				<p>Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Aliquam convallis lacinia dapibus. Sed nunc enim, ultrices nec suscipit ac, malesuada pharetra diam. Etiam massa orci, pellentesque nec lacinia eu, vulputate non est. Donec faucibus, tellus eu ultrices lobortis, leo nisl iaculis elit, id dictum arcu massa in nibh. Nulla auctor vehicula rutrum. Vivamus mi mauris, molestie sit amet placerat ac, tempor vitae nisi. Fusce pharetra eleifend aliquam. Cras ultricies orci sit amet ligula tempor pulvinar.</p>
				
				<p>Vivamus consectetur nulla vel neque accumsan bibendum lacinia nibh venenatis. Morbi placerat tempor nunc, eu congue metus pellentesque vitae.</p>

				<p>Maecenas lacinia commodo venenatis. Sed nec mauris sapien. Donec eget justo sapien, id elementum magna. Integer et orci quis urna tempus eleifend eget eu nulla. Quisque interdum porttitor tincidunt. Nulla ornare dolor elit, eu adipiscing felis. Nulla viverra blandit bibendum. Mauris non tellus lacus.</p>
				
				<p>Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Aliquam convallis lacinia dapibus. Sed nunc enim, ultrices nec suscipit ac, malesuada pharetra diam. Etiam massa orci, pellentesque nec lacinia eu, vulputate non est. Donec faucibus, tellus eu ultrices lobortis, leo nisl iaculis elit, id dictum arcu massa in nibh.</p>
				
				<p>Nulla auctor vehicula rutrum. Vivamus mi mauris, molestie sit amet placerat ac, tempor vitae nisi. Fusce pharetra eleifend aliquam. Cras ultricies orci sit amet ligula tempor pulvinar. Vivamus consectetur nulla vel neque accumsan bibendum lacinia nibh venenatis. Morbi placerat tempor nunc, eu congue metus pellentesque vitae.</p>

				<p>Maecenas lacinia commodo venenatis. Sed nec mauris sapien. Donec eget justo sapien, id elementum magna. Integer et orci quis urna tempus eleifend eget eu nulla. Quisque interdum porttitor tincidunt. Nulla ornare dolor elit, eu adipiscing felis. Nulla viverra blandit bibendum. Mauris non tellus lacus.</p>
				
				<p><center><img src="img/pencilman.jpg" alt="pencilmannn"></center></p>
			</section>
		</div>
		
		<div id="navmenu">
			<header>
				<h1>Menu Links</h1>
			</header>
			
				<ul>
						<li><a href="main.html" class="navlink">Home</a></li>
					<li><a href="Tienda/tienda.html" class="navlink">Tienda</a></li>
					<li><a href="Foro/foro.php" class="navlink">Foro</a></li>
					<li><a href="Contacto/contact_form.html" class="navlink">Contacto</a></li>
					<li><a href="Cursos/cursos.html" class="navlink">Cursos</a></li>
						<li><a href="MisCursos/miscursos.html" class="navlink"> Mis Cursos</a></li>
						<li><a href="home.php?q=logout" class="navlink">Salir</a></li>
				</ul>
		</div>
	</div>
	<div data-role="footer" data-position="fixed" >
    <h1>Footer Text</h1>
  </div>
</body>
</html>

