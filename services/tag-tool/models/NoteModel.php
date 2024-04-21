<?php

namespace model;

use model\types\Note;

require_once(__DIR__ . "/types/Note.php");
require_once(__DIR__ . "/Database.php");

class NoteModel
{
    static function createNote(Note $note): bool
    {
        Database::executeSql("INSERT INTO Note (itemId, content) VALUES (?, ?)",
            "is", array($note->itemId, $note->content));
        return !isset(Database::$lastError);
    }

    static function getNote(int $id): ?array
    {
        $results = Database::executeSql("SELECT id, itemId, content, createdAt FROM Note WHERE id = ?",
            "i", array($id));
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function getNotes(): ?array
    {
        $results = Database::executeSql("SELECT id, itemId, content, createdAt FROM Note");
        if (isset(Database::$lastError)) {
            return null;
        }
        return $results;
    }

    static function deleteNote(int $id): bool
    {
        Database::executeSql("DELETE FROM Note WHERE id = ?", "i", array($id));
        return !isset(Database::$lastError);
    }

    static function updateNote(Note $note): bool
    {
        Database::executeSql("UPDATE Note SET itemId = ?, content = ? WHERE id = ?", "isi",
            array($note->itemId, $note->content, $note->id));
        return !isset(Database::$lastError);
    }
}