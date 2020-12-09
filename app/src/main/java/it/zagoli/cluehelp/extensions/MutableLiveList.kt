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

    private fun notifyObservers() {
        this.value = this.value
    }


}