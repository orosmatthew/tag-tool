<?php

use controllers\Request;
use controllers\Response;
use model\ItemModel;
use model\types\Item;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../models/types/Item.php');
require_once(__DIR__ . '/../models/ItemModel.php');

class ItemController
{
    static public function post(Request $req): Response
    {
        $item = new Item($req->data);
        $success = ItemModel::createItem($item);
        $status = $success ? 0 : 1;
        return new Response(null, null, $status);
    }
}