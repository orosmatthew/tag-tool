<?php /** @noinspection PhpUnused */

namespace controllers;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/../models/Database.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../models/types/Note.php');
require_once(__DIR__ . '/../models/NoteModel.php');

use model\NoteModel;

class NotesController
{
    static public function get(Request $req): Response
    {
        $array = NoteModel::getNotes();
        return new Response($array, null, $array == null ? 1 : 0);
    }
}