<?php
    include '../Padre.php';
?>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="HandheldFriendly" content="True">
	<meta name="MobileOptimized" content="320"/>
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name='viewport' content='width=device-width' />
        <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no' />
	

	<title>Formulario de Contacto</title>

	<!--Include JQM and JQ-->
	<link rel="stylesheet" href="../css/themes/jqmfb.min.css"/>
	<link rel="stylesheet" href="http://code.jquery.com/mobile/latest/jquery.mobile.structure.min.css"/>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>
	<script src="../js/jquery.animate-enhanced.min.js"></script>
        <script src="../js/contact_form.js"></script>
        
	
	
	<script src="../js/jqm.globals.js"></script>
	
	<script src="http://code.jquery.com/mobile/latest/jquery.mobile.min.js"></script>

	<!--JQM SlideMenu-->
	<link rel="stylesheet" href="../css/jqm.slidemenu.css" />
	<script src="../js/jqm.slidemenu.js"></script>
        <script src="main.js"></script>	
         <style>
            #header{
    overflow: visible; /* Let menu content overflow outside the header */
}
#header ul { /* Menu Name */
    margin-top: 1em;
}
#header .ui-btn-corner-all {
    /* border-radius: 0; /* Make the menu button squarish */
}
#header ul ul { /* Menu Item List */
    position: absolute; /* Position absolutely */
    display: none; /* Hide */
    z-index:500; /* Ensure visibility over other elements on page */
    margin-top: 0px; /* Bring menu closer to button; not needed on mobile */
}
#header ul ul li {
    width: 150px; /* Fixed width menu items*/
    display: block; /* JQM makes a inline-blocks... reset it to block */
}
#header ul ul li a {
    white-space: normal; /* Stop long menu names from truncating */
}
#header ul:hover ul {
    /* display: block; /* Display menu on hover over parent */
}
#menu-left {
    float: left;
    margin-left:0.5em;
}
#menu-right {
    float: right;
    margin-right:0.5em; 
}
#menu-left ul {
    margin-left:0.5em;
}
#menu-right ul {
    margin-right: 0.5em;
    right: 0em;
}
#home .ui-header {
    height: 75px;
}
#home .ui-header h1 {
    font-size: 16pt;
    margin-bottom:0px;
}
#txtSpan {
    position: relative;
    display: inline-block;
    top: -5px; /* Doesnt seem possible to vertically center otherwise */
}
#home .ui-header h2 {
    font-size: 14pt;
    margin-top: 0px;
}
.badge {
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: bold;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #777;
    border-radius: 10px;
}
        </style>
</head>
<body>

	<div id="slidemenu">
	
	<h3>MENU</h3>
<ul>
<li><a onclick="window.location='../main.php'" href="#"><img src="../img/smico3.png">Home</a><span class="icon"></span></li>
<li><a onclick="window.location='formcontact.php'" href="#"><img src="../img/smico1.png">Contacto</a><span class="icon"></span></li>
<li><a onclick="window.location='../foro6/index.php'" href="#"><img src="../img/smico5.png">Foro</a><span class="icon"></span></li>
<li><a onclick="window.location='../Tienda/index.php'" href="#"><img src="../img/smico6.png">Tienda</a><span class="icon"></span></li>
</ul>
</div>
<div data-role="page" id="main_page" data-theme="a">
<div data-role="header" data-position="fixed" data-tap-toggle="false" data-update-page-padding="false" style="height: 50px">
			<a href="#" data-slidemenu="#slidemenu" data-slideopen="false" data-icon="smico" data-corners="false" data-iconpos="notext">Menu</a>
			
    <div style="max-width: 50%; margin-left: 25%;">
        <input type="search" name="search" id="search-basic" value="" style="border-radius:  5px; width: 80%;"/> 
        <a href="#" data-role="button" style="top: -28px;width: 20%;margin: -14px;left: 103%;">Buscar</a>
    </div>
    
    <div style="float: right; top: -57px; position: relative;">
        <img src="../img/cesta.gif" alt="" width="40px" style="padding: 5px"/>
        <span class="badge" style="display: inline-block;">
            <?php
              include_once 'include/functions.php';

$user = new User();
$uid = $_SESSION['uid'];
$sql="SELECT * FROM cart WHERE user_id='$uid'";
	$run_query=mysql_query($sql);
        $qt = mysql_num_rows($run_query);

echo $qt;
             ?>
</span>
      
    </div>
           

    <ul id="menu-right" data-role="menu" style="top: -34px; position: relative;">
        <li>
            <!-- CODIGO -->
                 <div style="float: right; top: -18px; position: relative;"> 
        
             Hello 
              <?php
              include_once 'include/functions.php';

$user = new User();
$uid = $_SESSION['uid'];

$user->get_fullname($uid);

             ?>

                        
     </div>
   <div style="float: right; top: -30px; position: relative;">
       <img src="<?php $user->get_imagen_url($uid); ?>?id=<?php echo rand(); ?>" alt="" width="40px" style="padding: 5px;border-radius: 50%;"/>
       
   </div>
            <!-- FIN CODIGO -->
            <ul data-role="listview" data-inset="true">
                      <li data-icon="false"><a onclick="window.location='../foro6/perfil.php?uid=<?php echo $_SESSION["uid"]; ?>'" href="#">Perfil</a></li>
                <li data-icon="false"><a onclick="window.location='../Tienda/cart.php'" href="#">Cart</a></li>                
                <li data-icon="false"><a onclick="window.location='../logout.php'" href="#">Logout</a></li>
            </ul>
        </li>
    </ul>     
</div>


<div data-role="content">
        
    
  <div class="pad-12">
              
            <div class="white-block status-list pad-2">
                
                <div id="mainform">
    <h2>Formulario de contacto</h2>
    <!-- Required Div Starts Here -->
    <form id="form">
      
        <p id="returnmessage"></p>
        <label>Nombre: <span>*</span></label>
        <input type="text" id="name" placeholder="Nombre"/>
        <label>Email: <span>*</span></label>
        <input type="text" id="email" placeholder="Email"/>
        <label>Telefono: <span>*</span></label>
        <input type="text" id="contact" placeholder="10 digitos Mobil no."/>
        <label>Mensaje:</label>
        <textarea id="message" placeholder="Mensaje......."></textarea>
        <input type="button" id="submit" value="Enviar"/>
    </form>
                </div>
            </div>
              
        </div>
              
         
        <div class="jquery-script-ads"><script type="text/javascript"><!--
google_ad_client = "ca-pub-2783044520727903";
/* jQuery_demo */
google_ad_slot = "2780937993";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript"
src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
</script></div>

    </div>
    
<div data-role="footer" data-position="fixed" data-tap-toggle="false" data-update-page-padding="false" style="height: 50px">
    <center> Footer</center>
</div>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36251023-1']);
  _gaq.push(['_setDomainName', 'jqueryscript.net']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<script type="text/javascript">
    $(function(){
        $('#side-menu').slideMenu();
    });
    </script>

    </body>
</html>
