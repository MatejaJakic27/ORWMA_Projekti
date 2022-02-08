package hr.ferit.matejajakic.mindgarden

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val userName : String
        userName = activity?.intent?.extras?.getString("username").toString()


        var dbref : DatabaseReference = FirebaseDatabase.getInstance("https://mindgarden-ae8ca-default-rtdb.europe-west1.firebasedatabase.app/").getReference("User").child(userName).child("CurrentlyReading")
        var dbrefRB : DatabaseReference = FirebaseDatabase.getInstance("https://mindgarden-ae8ca-default-rtdb.europe-west1.firebasedatabase.app/").getReference("User").child(userName).child("ReadBooks")



        var currFireBase :  MutableList<CurrentlyReading>
        currFireBase = mutableListOf()

        var recyFireBase = view.findViewById<RecyclerView>(R.id.homeRecycler)

        recyFireBase.layoutManager = LinearLayoutManager(this.context)
        recyFireBase.setHasFixedSize(true)

        val bookListener = object: BookInteractionListener {
            override fun onRemove(title : String?, rating : String?) {
              if (title != null){

                var query : Query = dbref.orderByChild("title").equalTo(title)
                    query.addValueEventListener(object: ValueEventListener{
                        override fun onDataChange(p0: DataSnapshot) {
                            for(bookSnapshot in p0.children) {

                                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
                                val currentDT: String = simpleDateFormat.format(Date())
                               val readBookTrans = bookSnapshot.getValue(CurrentlyReading::class.java)
                                var readBookDone = ReadBooks(readBookTrans?.title, readBookTrans?.author, readBookTrans?.startDate,
                                currentDT, rating, readBookTrans?.image)
                                val readBookID = dbrefRB.push().key


                                bookSnapshot.ref.removeValue().addOnCompleteListener {
                                    currFireBase.clear()
                                }
                                if (readBookID != null){
                                    dbrefRB.child(readBookID).setValue(readBookDone)
                                        .addOnCompleteListener {
                                            Toast.makeText(
                                                context,
                                                "Uspješno prenesena pročitana knjiga",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }

                            }
                            }

                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Log.w(TAG, "Failed to read value.")

                        }


                    })



            }
            }
        }

            dbref.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    currFireBase.clear()
                        for(bookSnapshot in p0.children){
                            val book = bookSnapshot.getValue(CurrentlyReading::class.java)
                            currFireBase.add(book!!)
                        }
                        recyFireBase.adapter = CurrReadingAdapter(currFireBase, bookListener)

                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.w(TAG, "Failed to read value.")

                }


            })

        return view
    }



}