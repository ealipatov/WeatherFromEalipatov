package by.ealipatov.kotlin.weatherfromealipatov.view.contactlist

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentContactListBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Contact
import by.ealipatov.kotlin.weatherfromealipatov.utils.REQUEST_CODE_CALL
import by.ealipatov.kotlin.weatherfromealipatov.utils.REQUEST_CODE_READ_CONTACTS

class ContactListFragment: Fragment(), OnContactClick {

    private var _binding: FragmentContactListBinding? = null
    private val binding: FragmentContactListBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission(Manifest.permission.READ_CONTACTS)
    }

    private fun checkPermission(permission: String) {
        val permResult =
            ContextCompat.checkSelfPermission(requireContext(), permission)
        PackageManager.PERMISSION_GRANTED
        if (permResult == PackageManager.PERMISSION_GRANTED) {
            getContacts()
        } else if(shouldShowRequestPermissionRationale(permission)){
            AlertDialog.Builder(requireContext())
                .setTitle("Разрешение")
                .setMessage("Для работы приложения требуется разрешение")
                .setPositiveButton("Разрешить") { _, _ ->
                    permissionRequest(permission)
                }
                .setNegativeButton("Запретить") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionRequest(permission)
        }
    }

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_READ_CONTACTS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            for (pIndex in permissions.indices) {
                if (permissions[pIndex] == Manifest.permission.READ_CONTACTS
                    && grantResults[pIndex] == PackageManager.PERMISSION_GRANTED
                ) {
                    getContacts()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("Range")
    private fun getContacts() {
        val contacts = mutableListOf<Contact>()
        val contentResolver: ContentResolver = requireContext().contentResolver
        // Отправляем запрос на получение контактов и получаем ответ в виде Cursor
        val cursorWithContacts: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
        cursorWithContacts?.let { cursor->
            for(i in 0 until cursor.count){
                cursor.moveToPosition(i)
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val contactId =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val number = getNumberFromID(contentResolver,contactId)
                contacts.add(Contact(name,number))
            }
        }
        cursorWithContacts?.close()
        binding.contactListRecyclerView.adapter =
            ContactListAdapter(contacts,this)
    }

    @SuppressLint("Range")
    private fun getNumberFromID(cr: ContentResolver, contactId: String) :String {
        val phones = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null
        )
        var number = "none"
        phones?.let { cursor ->
            while (cursor.moveToNext()) {
                number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
        }
        return number
    }

    override fun onContactClick(contact: Contact) {
        val numberCurrent = contact.phoneNumber
        if(ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED){
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$numberCurrent"))
            startActivity(intent)
        }else{
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}