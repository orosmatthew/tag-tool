<?php

namespace model;

use model\types\TagType;

require_once(__DIR__ . "/types/TagType.php");
require_once(__DIR__ . "/Database.php");

class TagTypeModel
{
    static function createTagType(TagType $tagType): bool
    {
        Database::executeSql("INSERT INTO TagType (name, userId) VALUES (?, ?)",
            "si", array($tagType->name, $tagType->userId));
        return !isset(Database::$lastError);
    }

    static function getTagType(int $userId, int $id): ?array
    {
        $results = Database::executeSql("SELECT id, createdAt, name FROM TagType WHERE id = ? AND userId = ?",
            "ii", array($id, $userId));
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function getTagTypes(int $userId): ?array
    {
        $results = Database::executeSql("SELECT id, createdAt, name FROM TagType WHERE userId = ?", "i", array($userId));
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function deleteTagType(int $userId, int $id): bool
    {
        Database::executeSql("DELETE FROM TagType WHERE id = ? AND userId = ?", "ii", array($id, $userId));
        return !isset(Database::$lastError);
    }

    static function updateTagType(TagType $tagType): bool
    {
        Database::executeSql("UPDATE TagType SET name = ? WHERE id = ? AND userId = ?", "sii",
            array($tagType->name, $tagType->id, $tagType->userId));
        return !isset(Database::$lastError);
    }
}