<!doctype html>
<html>
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="/shop_webservice/theme/css/bootstrap.css">

    <!--    <script src="http://code.jquery.com/jquery.js"></script>-->
    <!--    <script src="/shop_webservice/theme/js/bootstrap.min.js"></script>-->
    <!--    <script src="/shop_webservice/theme/js/bootstrap.js"></script>-->




</head>


<?php

require_once('DBFunction.php');

$dbQuery = new DBFunction();
$response = array();

$name = $_POST["item"];
$price = $_POST["price"];
$description = $_POST["description"];
$quantity = $_POST["quantity"];


$image_name = $_FILES["image"]["name"];
$temp = $_FILES["image"]["tmp_name"];
$error = $_FILES ["image"]["error"];
$type = $_FILES ["image"]["type"];
$owner = $_POST["owner"];

$sell = mysql_query("SELECT login FROM user WHERE id = '$owner'") or die(mysql_error());
$no_of_rows3 = mysql_num_rows($sell);

while($row=mysql_fetch_row($sell)){
    $userName = $row[0];
}

$image_path = "images";

if(isset($_POST['Add'])){
    if($_POST["item"]=="" || $_POST["category"]=="" || $_POST["description"]=="" || $_POST["price"]=="" || $_POST["quantity"]=="" || $_POST["owner"]==""){
        echo "<div class='jumbotron'><div class='alert alert-danger'>Sorry fill all the fields !</div>";
        echo "<a class='btn btn-primary' role='button' href='/shop_webservice/ItemAdd.php?userlog=$userName'>Back</a></div>";
    }

    else{

        $category = $_POST["category"];


        if($error > 0){
            die ("Error uploading image! Code $error.");
        }
        elseif ($type == "image/png"
            || $type == "image/jpeg"
            || $type == "image/jpg"
            || $type == "image/pjpeg"
            || $type == "image/x-png"
            || $type == "image/gif"){

            move_uploaded_file($temp,"$image_path/$image_name");





            $time = date('Y-m-d H:i:s');

            mysql_query("INSERT INTO shop_items (item_name, item_price, item_description, item_owner, item_category, item_quantity, item_image, addedtime)
        VALUES ('$name', '$price', '$description', '$owner', '$category', '$quantity', '$image_name', '$time')");


            echo "<div class='jumbotron'>";

            echo " <div class='modal-dialog'>
        <div class='modal-content'>
            <div class='alert alert-success'>
            <b>Advertisement added successfully!</b>
            </div>
            <div class='modal-body'>
        <p>Please wait 20min - 1h till our moderators accept your advertisement.</p>
      </div>
                <div class='modal-body'><center>
                 <a href='/shop_webservice/ItemAdd.php?userlog=$userName&&action=backToTheMain'><button type='button' class='btn btn-default' data-dismiss='modal'>Continue</button></a>
                </center></div>
        </div>

</div>";

            echo "</div>";



        }
        else{

            echo "<div class='jumbotron'><div class='alert alert-danger'>The file that you are trying to upload couldn't be an
             image.Please check again!!</div>";
            echo "<a class='btn btn-primary' role='button' href='/shop_webservice/ItemAdd.php?userlog=$userName'>Back</a></div>";
        }


    }

}
?>