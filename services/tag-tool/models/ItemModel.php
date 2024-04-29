<?php

namespace model;

use model\types\Item;

require_once(__DIR__ . "/types/Item.php");
require_once(__DIR__ . "/Database.php");

class ItemModel
{
    static function createItem(Item $item): ?int
    {
        $result = Database::executeSql("INSERT INTO Item (name, description, codeData, userId) VALUES (?, ?, ?, ?)",
            "sssi", array($item->name, $item->description, $item->codeData, $item->userId));
        return !isset(Database::$lastError) ? $result : null;
    }

    static function getItem(int $userId, int $id): ?array
    {
        $results = Database::executeSql("SELECT id, name, description, createdAt, codeData, userId FROM Item WHERE id = ? AND userId = ?",
            "ii", array($id, $userId));
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function getItems(int $userId): ?array
    {
        $results = Database::executeSql("SELECT id, name, description, createdAt, codeData, userId FROM Item WHERE userId = ?", "i", array($userId));
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function deleteItem(int $userId, int $id): bool
    {
        Database::executeSql("DELETE FROM Item WHERE id = ? AND userId = ?", "ii", array($id, $userId));
        return !isset(Database::$lastError);
    }

    static function updateItem(Item $item): bool
    {
        Database::executeSql("UPDATE Item SET name = ?, description = ?, codeData = ?, userId = ? WHERE id = ? AND userId = ?", "sssiii",
            array($item->name, $item->description, $item->codeData, $item->userId, $item->id, $item->userId));
        return !isset(Database::$lastError);
    }
}