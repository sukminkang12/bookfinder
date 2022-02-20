package com.sukminkang.bookfinder.ui.component.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sukminkang.bookfinder.data.model.SearchBooksModel
import com.sukminkang.bookfinder.databinding.CellSearchBookBinding

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.ItemViewHolder>() {

    private val bookList : ArrayList<SearchBooksModel> = arrayListOf()
    private var isRequestNextPage = false
    var requestNextPageCallback: (() -> Unit)? = null
    var onItemClickCallback: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(CellSearchBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemData = bookList[position]
        holder.apply {
            bind(itemData)
        }

        if (position >= bookList.size - 2 && !isRequestNextPage) {
            isRequestNextPage = true
            requestNextPageCallback?.invoke()
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun initList(list:ArrayList<SearchBooksModel>) {
        notifyItemRangeRemoved(0, bookList.lastIndex)
        bookList.clear()
        bookList.addAll(list)
        notifyItemRangeChanged(0, bookList.lastIndex)
    }

    fun addList(list:ArrayList<SearchBooksModel>) {
        isRequestNextPage = false
        val start = bookList.lastIndex + 1
        bookList.addAll(list)
        notifyItemRangeInserted(start, bookList.lastIndex)
    }

    inner class ItemViewHolder(private val binding: CellSearchBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recyclerViewItem: SearchBooksModel) = with(binding) {
            bookItem = recyclerViewItem
            bookItem?.isbn13?.let { isbn->
                mainCl.setOnClickListener {
                    onItemClickCallback?.invoke(isbn)
                }
            }
        }

    }
}