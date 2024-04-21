<?php /** @noinspection PhpUnused */

namespace controllers;

use model\NoteModel;
use model\types\Note;

require_once(__DIR__ . '/Request.php');
require_once(__DIR__ . '/../models/Database.php');
require_once(__DIR__ . '/Response.php');
require_once(__DIR__ . '/../models/types/Note.php');
require_once(__DIR__ . '/../models/NoteModel.php');

class NoteController
{
    static public function post(Request $req): Response
    {
        $note = new Note($req->data);
        $success = NoteModel::createNote($note);
        $status = $success ? 0 : 1;
        return new Response(null, null, $status);
    }

    static public function get(Request $req): Response
    {
        $array = NoteModel::getNote($req->id);
        return new Response($array, null, $array == null ? 1 : 0);
    }

    static public function delete(Request $req): Response
    {
        $success = NoteModel::deleteNote($req->id);
        return new Response(null, null, $success ? 0 : 1);
    }

    static public function put(Request $req): Response
    {
        $note = new Note($req->data);
        $note->id = $req->id;
        $success = NoteModel::updateNote($note);
        return new Response(null, null, $success ? 0 : 1);
    }
}