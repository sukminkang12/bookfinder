package com.sukminkang.bookfinder.ui.component.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sukminkang.bookfinder.R
import com.sukminkang.bookfinder.data.model.SearchBooksModel
import com.sukminkang.bookfinder.databinding.CellSearchBookBinding
import com.sukminkang.bookfinder.ui.base.BaseViewHolder
import com.sukminkang.bookfinder.ui.base.loadFromUrlString

class SearchListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private lateinit var context: Context
    private val bookList : ArrayList<SearchBooksModel> = arrayListOf()
    private var isRequestNextPage = false
    var requestNextPageCallback: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(CellSearchBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context

    }

    fun initList(list:ArrayList<SearchBooksModel>) {
        notifyItemRangeRemoved(0, bookList.lastIndex)
        bookList.clear()
        bookList.addAll(list)
        notifyItemRangeChanged(0, bookList.lastIndex)
    }

    fun addList(list:ArrayList<SearchBooksModel>) {
        isRequestNextPage = false
        var start = bookList.lastIndex + 1
        bookList.addAll(list)
        notifyItemRangeInserted(start, bookList.lastIndex)
    }

    inner class ItemViewHolder(
        var binding:CellSearchBookBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(position: Int) {
            val itemData = bookList[position]

            val title = itemData.title
            val subtitle = if(itemData.subtitle.isNotBlank()) {
                "(${itemData.subtitle})"
            } else {
                ""
            }

            binding.image.loadFromUrlString(itemData.image)
            binding.title.text = title + subtitle
            binding.isbn.text = "${context.getString(R.string.search_screen_isbn)} : ${itemData.isbn13}"
            binding.price.text = itemData.price

            if (position == bookList.size - 1 && !isRequestNextPage) {
                isRequestNextPage = true
                requestNextPageCallback?.invoke()
            }
        }

    }
}