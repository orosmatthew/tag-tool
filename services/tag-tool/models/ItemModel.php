<?php

namespace model;

use model\types\Item;

require_once(__DIR__ . "/types/Item.php");
require_once(__DIR__ . "/Database.php");

class ItemModel
{
    static function createItem(Item $item): bool
    {
        Database::executeSql("INSERT INTO Item (name, description, codeData) VALUES (?, ?, ?)",
            "sss", array($item->name, $item->description, $item->codeData));
        return isset(Database::$lastError);
    }
}