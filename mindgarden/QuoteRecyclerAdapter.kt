package hr.ferit.matejajakic.mindgarden

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuoteRecyclerAdapter(private var items: List<Quotes>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return QuoteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.quotes_recycler, parent,
                false
            )
        )

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is QuoteViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class QuoteViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val quotesListed: TextView =
            itemView.findViewById(R.id.quotesListed)
        private val titleAndAuthor: TextView =
            itemView.findViewById(R.id.titleAndAuthor)

        fun bind(quotes: Quotes) {
            quotesListed.text = quotes.quoteInOrder
            titleAndAuthor.text = quotes.quoteTitleAndAuthor

        }

    }
}