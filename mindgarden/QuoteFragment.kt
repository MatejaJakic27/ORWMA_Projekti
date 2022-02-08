package hr.ferit.matejajakic.mindgarden

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.w3c.dom.Text


class QuoteFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_quote, container, false)
        val userName : String
        userName = activity?.intent?.extras?.getString("username").toString()
       // Toast.makeText(this.context, userName, Toast.LENGTH_SHORT).show()

        var popupButton = view.findViewById<Button>(R.id.popupButton)
        var quoteList : MutableList<Quotes>
        quoteList = mutableListOf()
        val database = FirebaseDatabase.getInstance("https://mindgarden-ae8ca-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("User").child(userName).child("Quotes")

        var quoteRecycler = view.findViewById<RecyclerView>(R.id.quoteRecycler)
        quoteRecycler.layoutManager = LinearLayoutManager(this.context)
        quoteRecycler.adapter = QuoteRecyclerAdapter(quoteList)

        popupButton.setOnClickListener {
            val window = PopupWindow(this.context)
            val popUpView = layoutInflater.inflate(R.layout.quote_popup, null)
            window.contentView = popUpView
            window.width = (850)
            window.height = (700)

            window.setFocusable(true);
            window.update();
            window.showAsDropDown(popupButton)
            val popUpDismissButton = popUpView.findViewById<Button>(R.id.popUpDismiss)
            val authorAndTitle = popUpView.findViewById<EditText>(R.id.popupTitleAndAuthor)
            val quoteItself = popUpView.findViewById<EditText>(R.id.popupQuote)
            popUpDismissButton.setOnClickListener {
                window.dismiss()
                val authorAndTitleReady = authorAndTitle.text.toString()
                val quoteReady = quoteItself.text.toString()
                val quote = Quotes(authorAndTitleReady, quoteReady)
                val quoteID = database.push().key
                if (quoteID != null) {
                    database.child(quoteID).setValue(quote).addOnCompleteListener {
                        Toast.makeText(
                            this.context,
                            "Uspješno pohranjen citat",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }


        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()) {
                    quoteList.clear()
                    for (quoteSnapshot in p0.children) {
                        val quote1 = quoteSnapshot.getValue(Quotes::class.java)
                        quoteList.add(quote1!!)
                    }
                    quoteRecycler.adapter = QuoteRecyclerAdapter(quoteList)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.")

            }


        })


        return view
    }

}












