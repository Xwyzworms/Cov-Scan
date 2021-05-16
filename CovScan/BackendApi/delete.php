<?php 

    include_once("koneksi.php");

    function set_response($isSucess,$message) {

        $result = array(
            "isSucces" => $isSucess,
            "message" => $message
        );

        echo json_encode($result);
    }

    if (!empty($_POST["id"])) {
        $id = $_POST["id"];
        $query = "DELETE FROM user where id='$id'";
        
        $delete = mysqli_query($connect,$query);

        if($delete) {
            set_response(true,"Sucess Delete One");
        }
        else {
            set_response(false,"failed Delete One Data");
        }
    }