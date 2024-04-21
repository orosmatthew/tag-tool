<?php

namespace model;

use model\types\TagType;

require_once(__DIR__ . "/types/TagType.php");
require_once(__DIR__ . "/Database.php");

class TagTypeModel
{
    static function createTagType(TagType $tagType): bool
    {
        Database::executeSql("INSERT INTO TagType (name) VALUES (?)",
            "s", array($tagType->name));
        return !isset(Database::$lastError);
    }

    static function getTagType(int $id): ?array
    {
        $results = Database::executeSql("SELECT id, createdAt, name FROM TagType WHERE id = ?",
            "i", array($id));
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function getTagTypes(): ?array
    {
        $results = Database::executeSql("SELECT id, createdAt, name FROM TagType");
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function deleteTagType(int $id): bool
    {
        Database::executeSql("DELETE FROM TagType WHERE id = ?", "i", array($id));
        return !isset(Database::$lastError);
    }

    static function updateTagType(TagType $tagType): bool
    {
        Database::executeSql("UPDATE TagType SET name = ? WHERE id = ?", "si",
            array($tagType->name, $tagType->id));
        return !isset(Database::$lastError);
    }
}