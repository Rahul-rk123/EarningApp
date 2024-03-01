package com.example.earningapp.modelclass

class HistoryModalClass{
    var timeanddata:String=""
    var coin:String=""
    var iswithdrawl:Boolean = false
    constructor()
    constructor(timeanddata: String, coin: String, iswithdrawl: Boolean) {
        this.timeanddata = timeanddata
        this.coin = coin
        this.iswithdrawl = iswithdrawl
    }

}
