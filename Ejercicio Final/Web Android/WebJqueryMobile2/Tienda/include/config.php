<?php
if(!defined('DB_SERVER'))
    define('DB_SERVER', 'localhost');
if(!defined('DB_USERNAME'))
    define('DB_USERNAME', 'root');
if(!defined('DB_PASSWORD'))
    define('DB_PASSWORD', 'wanchope');
if(!defined('DB_DATABASE'))
    define('DB_DATABASE', 'david16');
/*
define('DB_SERVER', 'mysql.hostinger.es');
define('DB_USERNAME', 'u486881094_root');
define('DB_PASSWORD', '');
define('DB_DATABASE', 'u486881094_mydb	');
*/
if(!class_exists("DB_Class")){
    class DB_Class {

        function __construct() {
            $connection = mysql_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD) or die('Oops connection error -> ' . mysql_error());
            mysql_select_db(DB_DATABASE, $connection) or die('Database error -> ' . mysql_error());
        }

       

    }
}
?>
