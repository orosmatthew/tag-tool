<?php

namespace model;

use model\types\Item;

require_once(__DIR__ . "/types/Item.php");
require_once(__DIR__ . "/Database.php");

class ItemModel
{
    static function createItem(Item $item): bool
    {
        Database::executeSql("INSERT INTO Item (name, description, codeData, userId) VALUES (?, ?, ?, ?)",
            "sssi", array($item->name, $item->description, $item->codeData, $item->userId));
        return !isset(Database::$lastError);
    }

    static function getItem(int $id): ?array
    {
        $results = Database::executeSql("SELECT id, name, description, createdAt, codeData, userId FROM Item WHERE id = ?",
            "i", array($id));
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function getItems(): ?array
    {
        $results = Database::executeSql("SELECT id, name, description, createdAt, codeData, userId FROM Item");
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
        Database::executeSql("UPDATE Item SET name = ?, description = ?, codeData = ?, userId = ? WHERE id = ?", "sssii",
            array($item->name, $item->description, $item->codeData, $item->userId, $item->id));
        return !isset(Database::$lastError);
    }
}