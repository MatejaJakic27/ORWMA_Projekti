package hr.ferit.matejajakic.mindgarden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UserRecyclerAdapter(private var items : MutableList<ReadBooks>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.read_books_recycler, parent,
                false
            )
        )

    }
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is UserViewHolder -> {
                holder.bind(items[position])
            }

        }
    }
    override fun getItemCount(): Int {
        return items.size
    }


    class UserViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val bookImage: ImageView =
            itemView.findViewById(R.id.bookDisplay3)
        private val bookTitle: TextView =
            itemView.findViewById(R.id.bookTitle2)
        private val bookAuthor: TextView =
            itemView.findViewById(R.id.authorName2)
        private val startDate: TextView =
            itemView.findViewById(R.id.startingDate)
        private val bookRating: RatingBar =
            itemView.findViewById(R.id.readRating)
        private val endDate: TextView =
            itemView.findViewById(R.id.endDate)

        fun bind(readBooks: ReadBooks) {
            bookTitle.text = readBooks.title
            bookAuthor.text = readBooks.author
            startDate.text = readBooks.startDate
            startDate.text = readBooks.startDate
            endDate.text = readBooks.endDate
            bookRating.rating = readBooks.rating!!.toFloat()
            Picasso.get()
                .load(readBooks.image)
                .fit()
                .placeholder(R.drawable.spletkarica)
                .error(android.R.drawable.stat_notify_error)
                .into(bookImage)
        }


    }
}