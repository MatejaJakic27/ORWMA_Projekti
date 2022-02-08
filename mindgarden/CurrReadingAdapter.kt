package hr.ferit.matejajakic.mindgarden

import android.annotation.SuppressLint
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class CurrReadingAdapter(books: MutableList<CurrentlyReading>, bookListener : BookInteractionListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val bookListener: BookInteractionListener
    private val books: MutableList<CurrentlyReading>

    init {
        this.books = mutableListOf()
        this.books.addAll(books)
        this.bookListener = bookListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            BookHolder {
        val bookView  = LayoutInflater.from(parent.context).inflate(
                R.layout.curr_reading_recycler, parent,
                false)
        val bookHolder = BookHolder(bookView)
        return bookHolder

    }
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is BookHolder -> {
                val book = books[position]
                holder.bind(book,bookListener)
            }
        }
    }


    override fun getItemCount(): Int {
        return books.size
    }


    class BookHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val bookTitle: TextView =
            itemView.findViewById(R.id.bookTitle)
        private val bookAuthor: TextView =
            itemView.findViewById(R.id.bookAuthor)
        private val startDate: TextView =
            itemView.findViewById(R.id.startDate)
        private val bookImage: ImageView =
            itemView.findViewById(R.id.bookDisplay1)
        private val endButton : Button =
            itemView.findViewById(R.id.endButton)
        private val currRating : RatingBar =
            itemView.findViewById(R.id.currRating)
        fun bind(currBooks: CurrentlyReading, bookListener: BookInteractionListener) {
            bookTitle.text = currBooks.title
            bookAuthor.text = currBooks.author
            startDate.text = currBooks.startDate
            Picasso.get()
                .load(currBooks.image)
                .fit()
                .placeholder(R.drawable.spletkarica)
                .error(android.R.drawable.stat_notify_error)
                .into(bookImage)

           endButton.setOnClickListener{
               bookListener.onRemove(bookTitle.text.toString(), currRating.rating.toString()); true;}


        }



    }
}