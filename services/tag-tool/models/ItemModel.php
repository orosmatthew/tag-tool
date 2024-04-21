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
        return !isset(Database::$lastError);
    }

    static function getItem(int $id): ?array
    {
        $results = Database::executeSql("SELECT id, name, description, createdAt, codeData FROM Item WHERE id = ?",
            "i", array($id));
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function getItems(): ?array
    {
        $results = Database::executeSql("SELECT id, name, description, createdAt, codeData FROM Item");
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function deleteItem(int $id): bool
    {
        Database::executeSql("DELETE FROM Item WHERE id = ?", "i", array($id));
        return !isset(Database::$lastError);
    }

    static function updateItem(Item $item): bool
    {
        Database::executeSql("UPDATE Item SET name = ?, description = ?, codeData = ? WHERE id = ?", "sssi",
            array($item->name, $item->description, $item->codeData, $item->id));
        return !isset(Database::$lastError);
    }
}