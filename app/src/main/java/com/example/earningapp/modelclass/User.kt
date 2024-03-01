package com.example.earningapp.modelclass

class User {
    var name = ""
     var age = 0
     var Email = ""
     var password = ""
    constructor()
     constructor(name:String, age:Int, Email:String, password:String){
        this.name = name
        this.age = age
        this.Email = Email
        this.password = password

    }
}