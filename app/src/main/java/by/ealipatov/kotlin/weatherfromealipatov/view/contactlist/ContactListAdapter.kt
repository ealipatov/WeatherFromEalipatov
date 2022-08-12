package by.ealipatov.kotlin.weatherfromealipatov.view.contactlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentContactListItemViewBinding
import by.ealipatov.kotlin.weatherfromealipatov.domain.Contact


class ContactListAdapter(private val dataList: List<Contact>, private val callback: OnContactClick) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            FragmentContactListItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size

    inner class ContactViewHolder(private val binding:FragmentContactListItemViewBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(contact: Contact) {
            binding.name.text = contact.name
            binding.phoneNumber.text = contact.phoneNumber
            binding.callFAB.setOnClickListener {
                callback.onContactClick(contact)
            }
        }
    }
}
