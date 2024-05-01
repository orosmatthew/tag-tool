package site.pixeled.tagtool

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import site.pixeled.tagtool.databinding.FragmentListItemBinding
import site.pixeled.tagtool.model.Item

class ItemRecyclerViewAdapter(
    private val items: List<Item>, private val onRowSelect: ((Int) -> Unit)?
) : RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(binding: FragmentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.ListItemName

        init {
            binding.root.setOnClickListener {
                val row = layoutPosition
                onRowSelect?.let {
                    it(row)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nameView.text = item.name
    }
}