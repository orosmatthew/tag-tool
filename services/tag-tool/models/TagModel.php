<?php

namespace model;

use model\types\Tag;

require_once(__DIR__ . "/types/Tag.php");
require_once(__DIR__ . "/Database.php");

class TagModel
{
    static function createTag(Tag $tag): ?int
    {
        $result = Database::executeSql("INSERT INTO Tag (tagTypeId, itemId) VALUES (?, ?)",
            "ii", array($tag->tagTypeId, $tag->itemId));
        return !isset(Database::$lastError) ? $result : null;
    }

    static function getTag(int $id): ?array
    {
        $results = Database::executeSql("SELECT id, tagTypeId, createdAt, itemId FROM Tag WHERE id = ?",
            "i", array($id));
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function getTags(): ?array
    {
        $results = Database::executeSql("SELECT id, tagTypeId, createdAt, itemId FROM Tag");
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function deleteTag(int $id): bool
    {
        Database::executeSql("DELETE FROM Tag WHERE id = ?", "i", array($id));
        return !isset(Database::$lastError);
    }

    static function updateTag(Tag $tag): bool
    {
        Database::executeSql("UPDATE Tag SET tagTypeId = ?, itemId = ? WHERE id = ?", "isi",
            array($tag->tagTypeId, $tag->itemId, $tag->id));
        return !isset(Database::$lastError);
    }
}