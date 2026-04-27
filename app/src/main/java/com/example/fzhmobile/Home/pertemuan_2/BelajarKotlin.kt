package com.example.fzhmobile.Home.pertemuan_2

fun main(){
    println("Hai rekan rekan...")
    println("selamat datang di bahasa pemrograman Kotlin!")

    println("=================")

    var angka = 15
    println("Hasil dari 15 + 10 = ${angka+10}")

    var nilaiInt = 10000
    var nilaiDouble = 100.003
    var nilaiFloat = 1000.0f

    println("nilai Integer = $nilaiInt")
    println("nilai Double = $nilaiDouble")
    println("nilai Float = $nilaiFloat")

    println("========= STRING =========")
    val huruf = 'a'
    println("Ini penggunaan karakter '$huruf'")

    val nilaiString = "Mawar"
    println("Halo $nilaiString!\nApa kabar?")

    println("========== KONDISI ========")

    val nilai = 10
    if(nilai<0)
        println ("Bilangan negatif")
    else{
        if(nilai%2 == 0)
            println("Bilangan Genap")
        else
            println("Bilangan Ganjil")
    }

    println("============== PERULANGAN ===========")
    val kampusKu: Array<String> = arrayOf("kampus", "politeknik", "caltex", "riau")
    for (kampus: String in kampusKu) {
        println(kampus)
    }
}