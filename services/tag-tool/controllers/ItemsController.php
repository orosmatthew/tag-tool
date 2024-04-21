<?php

namespace controllers;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/../models/Database.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../models/types/Item.php');
require_once(__DIR__ . '/../models/ItemModel.php');

use model\ItemModel;

class ItemsController
{
    static public function get(Request $req): Response
    {
        $array = ItemModel::getItems();
        return new Response($array, null, $array == null ? 1 : 0);
    }
}