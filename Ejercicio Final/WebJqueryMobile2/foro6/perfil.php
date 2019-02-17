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

if (isset($_GET["uid"])) 
    if($_GET["uid"] !== $_SESSION["uid"]){
        $id = $_GET["uid"];
        $u = $objC->usuariosid($id);
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
            $name = $_POST['name'];
            $email = $_POST['email'];
            $com->update($_SESSION["uid"],$name,$email);
        }

        ?>
        <div class="caja">
            <div class="categorias">Usuarios</div>
            <div class="foro">
                <form action="" method="post"  class="formulario">
                    <img src="../img/avatares/<?php echo $u[0]["avatar"]; ?>?id=<?php echo rand(); ?>" width="70px" /> <br/> 
                                    <a class="btn btn-link" href="foto.php"> - Cambiar foto -</a><br/>
                                   
                    <label>Username:</label>
                    <?php echo $u[0]["username"]; ?> <br/>
                    <label>Nombre:</label>
                    <input type="text" name="name" value="<?php echo $u[0]["name"]; ?>" /> <br/>
                    <label>Email:</label>
                    <input type="text" name="email" value="<?php echo $u[0]["email"]; ?>" /> <br/>


                    <button type="submit" name="guardar" class="btn btn-default">Actualizar</button>
                </form>
            </div>
        </div>

        <?php
    }

include 'footer.php';