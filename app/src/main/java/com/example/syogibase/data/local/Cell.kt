package com.example.syogibase.data.local

//１マスの情報クラス

class Cell {
    var piece:Piece
    var turn:Int
    var hint:Boolean


    init {
        piece = Piece.None
        turn = 0
        hint = false

    }
}