
<?php
$hostname = "localhost";
$username = "root";
$password = "";
$database = "kambing";

$connect = mysqli_connect($hostname,$username,$password,$database);

if(mysqli_connect_errno()){
    echo "Failed to Connect to MYSQL";
    die();
}