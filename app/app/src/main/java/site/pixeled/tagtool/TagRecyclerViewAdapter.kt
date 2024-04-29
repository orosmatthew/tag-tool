package site.pixeled.tagtool

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import site.pixeled.tagtool.databinding.FragmentListTagBinding
import site.pixeled.tagtool.model.TagType

class TagRecyclerViewAdapter(
    private val tags: Array<TagType>, private val onRowSelect: ((Int) -> Unit)?
) : RecyclerView.Adapter<TagRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(binding: FragmentListTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.tagText

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
            FragmentListTagBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return tags.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = tags[position]
        holder.nameView.text = tag.name
    }

}