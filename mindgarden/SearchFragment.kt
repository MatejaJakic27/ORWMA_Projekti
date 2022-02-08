package hr.ferit.matejajakic.mindgarden

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val request = ServiceBuilder.buildService(FakerEndpoints::class.java)
        val call = request.getBooks()

        val userName : String
        userName = activity?.intent?.extras?.getString("username").toString()


        var dbref : DatabaseReference = FirebaseDatabase.getInstance("https://mindgarden-ae8ca-default-rtdb.europe-west1.firebasedatabase.app/").getReference("User").child(userName).child("CurrentlyReading")

        var searchRecycler = view.findViewById<RecyclerView>(R.id.searchRecycler)

        val searchListener = object : SearchInteractionListener {
            override fun onAdd(book: BookJSON) {

                    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val currentDT: String = simpleDateFormat.format(Date())
                    var bookTrans = CurrentlyReading(book.title, book.author, currentDT, book.imageLink)
                    val readBookID = dbref.push().key
                    if (readBookID != null) {
                        dbref.child(readBookID).setValue(bookTrans)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    context,
                                    "Uspješno započeto čitanje knjige",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    }

                }




        }

        call.enqueue(object : Callback<ArrayList<BookJSON>> {
            override fun onResponse(call: Call<ArrayList<BookJSON>>, response:
            Response<ArrayList<BookJSON>>) {
                if (response.isSuccessful) {
                    searchRecycler.layoutManager = LinearLayoutManager(context)
                    searchRecycler.adapter = SearchRecyclerAdapter(response.body()!!, searchListener)
                    var insertTitle = view.findViewById<EditText>(R.id.insertTitle)
                    val searchButton = view.findViewById<Button>(R.id.searchButton1)
                    val data = response.body()!!

                    searchButton.setOnClickListener {
                        val filteredList : ArrayList<BookJSON>
                        filteredList = data.filter{ it.title == insertTitle.text.toString() } as ArrayList<BookJSON>
                        searchRecycler.adapter = SearchRecyclerAdapter(filteredList, searchListener)
                    }
                    }

                }
            override fun onFailure(call: Call<ArrayList<BookJSON>>, t: Throwable)
            {
                Log.d("FAIL", t.message.toString())
            }

        })






        return view
    }
}