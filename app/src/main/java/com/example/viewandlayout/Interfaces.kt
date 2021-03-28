package com.example.viewandlayout

interface IOnDetailChangeListener  {
    fun onChange(data: Any)
}

interface IOnBackPressed {
    fun onBackPressed(): Boolean
}