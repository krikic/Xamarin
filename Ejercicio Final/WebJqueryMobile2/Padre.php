<?php

/**
 * Clase padre a todas las clases hijas
 */
include_once 'include/functions.php';

class Padre {
    
    public function __construct() {
        $user = new User();
        if( !isset($_SESSION)) {
            session_start();
        }
        
        if( !$user->get_session() )
        {
            header("location:index.php");
        }
    
    }

}

$padre = new Padre();