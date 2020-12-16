package it.zagoli.cluehelp.extensions

import androidx.lifecycle.MutableLiveData

/**
 * extends [MutableLiveData] to better support lists
 */
class MutableLiveList<T>: MutableLiveData<MutableList<T>>() {

    init {
        this.value = mutableListOf()
    }

    fun addElementToList(element: T) {
        this.value?.let {
            it.add(element)
            notifyObservers()
        }
    }

    fun addAll(elements: List<T>) {
        this.value?.let {
            it.addAll(elements)
            notifyObservers()
        }
    }

    fun removeElementFromList(element: T) {
        this.value?.let {
            it.remove(element)
            notifyObservers()
        }
    }

    // I wanted this to be private, but it's necessary to call when the content of
    // the objects of the list gets updated (no elements added/removed)
    fun notifyObservers() {
        this.value = this.value
    }


}