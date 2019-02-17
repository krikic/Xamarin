<?php
//Include las clases a utikizar
require_once 'class/usuarios.php';
// creamos el objeto usuarios
$objC = new Usuarios();

include 'header.php';

// obtenemos el id 
// primero validamos si la url trae el id por get, si no lo contiene entonces un usuario esta
// revisando su perfil y lo obtenemos de la session que creamos al momento del login
// si es por web lo considera como un visitante
// si es por session activa el formulario para modificarlo

if (isset($_GET["id"])) {
    $id = $_GET["id"];
    ?>
    <div class="caja">
        <div class="categorias">Usuarios</div>
        <div class="foro">
            <img src="../img/avatares/<?php echo $u[0]["avatar"]; ?>" width="70px" /><br/>
            Username: <?php echo $u[0]["username"]; ?> <br/>
            Nombre: <?php echo $u[0]["name"]; ?> <br/>
            Email: <?php echo $u[0]["email"]; ?> <br/>
           
        </div>
    </div>
    <?php
} else {
    $id = $_SESSION["uid"];
    $u = $objC->usuariosid($id);
    
    if (isset($_POST['guardar'])) {
        $com = new Usuarios();
        $com->updateavatar($_SESSION["uid"]);
    }
    
    ?>
    <div class="caja">
        <div class="categorias">Usuarios</div>
        <div class="foro">
            <form action="" data-ajax="false" method="post" enctype="multipart/form-data"  class="formulario">
                <img src="../img/avatares/<?php echo $u[0]["avatar"]; ?>?id=<?php echo rand(); ?>" width="70px" /><br/>
            <label>Nick:</label>
            <?php echo $u[0]["username"]; ?> <br/>
            <label>Nombre:</label>
            <?php echo $u[0]["name"]; ?> <br/>
            <input type="file"  name="foto" value="" /><br/>
            
            <button type="submit" name="guardar" class="btn btn-default">Registrar</button>
            </form>
        </div>
    </div>
    <?php
}

include 'footer.php';
