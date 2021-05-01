<?php

$hostname = "localhost";
$username = "root";
$password = "";
$database = "Ehealth";

$connect = mysqli_connect($hostname,$username,$password,$database);

if(mysqli_connect_errno()) {
    echo "Failed to Connect to MYSQL";
    die();
}
