package hr.ferit.matejajakic.mindgarden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class SearchRecyclerAdapter(private val items: ArrayList<BookJSON>,private val searchListener : SearchInteractionListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return BookViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_recycler, parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
                                  position: Int) {
        when(holder) {
            is BookViewHolder -> {

                    val book = items[position]
                    holder.bind(book, searchListener)
                }
            }

    }

    override fun getItemCount(): Int {
        return items.size
    }

        class BookViewHolder constructor(
            itemView: View
        ): RecyclerView.ViewHolder(itemView) {
            private val bookTitle: TextView =
                itemView.findViewById(R.id.bookTitle1)
            private val bookAuthor: TextView =
                itemView.findViewById(R.id.bookAuthor1)
            private val bookLanguage: TextView =
                itemView.findViewById(R.id.bookLanguage)
            private val bookImage: ImageView =
                itemView.findViewById(R.id.bookDisplay2)
            private val bookPages: TextView =
                itemView.findViewById(R.id.bookPages)
            private val startButton: Button =
                itemView.findViewById(R.id.startReadingButton)

            fun bind(books: BookJSON, searchListener: SearchInteractionListener) {

                    bookTitle.text = books.title
                    bookAuthor.text = books.author
                    bookLanguage.text = books.language
                    bookPages.text = books.pages.toString()
                    Picasso.get()
                        .load(books.imageLink)
                        .fit()
                        .placeholder(R.drawable.spletkarica)
                        .error(android.R.drawable.stat_notify_error)
                        .into(bookImage)


                startButton.setOnClickListener {
                    searchListener.onAdd(books); true;}
                }
            }



}