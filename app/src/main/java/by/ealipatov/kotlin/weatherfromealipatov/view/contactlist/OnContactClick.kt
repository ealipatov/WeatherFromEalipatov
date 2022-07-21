package by.ealipatov.kotlin.weatherfromealipatov.view.contactlist

import by.ealipatov.kotlin.weatherfromealipatov.domain.Contact

fun interface OnContactClick {
    fun onContactClick(contact: Contact)
}