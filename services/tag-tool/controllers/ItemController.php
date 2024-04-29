<?php /** @noinspection PhpUnused */

namespace controllers;

use model\ItemModel;
use model\types\Item;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/../models/Database.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../models/types/Item.php');
require_once(__DIR__ . '/../models/ItemModel.php');

class ItemController
{
    static public function post(Request $req): Response
    {
        $item = new Item($req->data);
        $item->userId = $req->userId;
        $newId = ItemModel::createItem($item);
        return new Response($newId, null, $newId != null ? 0 : 1);
    }

    static public function get(Request $req): Response
    {
        $array = ItemModel::getItem($req->userId, $req->id);
        return new Response($array, null, $array == null ? 1 : 0);
    }

    static public function delete(Request $req): Response
    {
        $success = ItemModel::deleteItem($req->userId, $req->id);
        return new Response(null, null, $success ? 0 : 1);
    }

    static public function put(Request $req): Response
    {
        $item = new Item($req->data);
        $item->userId = $req->userId;
        $item->id = $req->id;
        $success = ItemModel::updateItem($item);
        return new Response(null, null, $success ? 0 : 1);
    }
}