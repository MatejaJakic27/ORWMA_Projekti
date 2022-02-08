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
import com.squareup.picasso.Picasso


class UserFragment() : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_user, container, false)

        var userName = view.findViewById<TextView>(R.id.userName)
        userName.text =  activity?.intent?.extras?.getString("username")
        val profileAvatar = view.findViewById<ImageView>(R.id.profileImage)
        var avatarDB : DatabaseReference = FirebaseDatabase.getInstance("https://mindgarden-ae8ca-default-rtdb.europe-west1.firebasedatabase.app/").getReference("User").child(userName.text.toString()).child("Images")

        view.findViewById<ImageButton>(R.id.LogOutButton).setOnClickListener {
            (activity as MainActivity).logOut()
        }

        profileAvatar.setOnClickListener {
            val window = PopupWindow(this.context)
            val popUpView = layoutInflater.inflate(R.layout.avatar_popup, null)
            window.contentView = popUpView
            window.width = (900)
            window.height = (950)

            window.showAsDropDown(profileAvatar)
            popUpView.findViewById<ImageView>(R.id.firstAvatar).setOnClickListener {
                val avatarID = avatarDB.push().key
                if(avatarID != null){
                    val image = ImageData("https://media.istockphoto.com/vectors/shiba-inu-dog-in-glasses-reading-book-cute-funny-japan-pet-animal-vector-id1152485418?k=20&m=1152485418&s=612x612&w=0&h=iH9JkJ0MSAUFfArZTzM2qe7t8mOG8LjVtwjNtnaxHdg=")
                    avatarDB.child(avatarID).setValue(image)
                }

                window.dismiss()
            }
            popUpView.findViewById<ImageView>(R.id.secondAvatar).setOnClickListener {
                val avatarID = avatarDB.push().key
                if(avatarID != null){
                    val image = ImageData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMDM8dV4QnnLe5ob_KTThBprRNro5XaUbu5Q&usqp=CAU")
                    avatarDB.child(avatarID).setValue(image)
                }
                window.dismiss()
            }
            popUpView.findViewById<ImageView>(R.id.thirdAvatar).setOnClickListener {
                val avatarID = avatarDB.push().key
                if (avatarID != null) {
                    val image =
                        ImageData("https://media.istockphoto.com/vectors/cartoon-cat-reading-a-book-vector-id1292283375?k=20&m=1292283375&s=612x612&w=0&h=UJDKK9oyL_gt6qbHTZtDc_xdeWmL3stl0CFr6SyOKu8=")
                    avatarDB.child(avatarID).setValue(image)

                }
                window.dismiss()
            }
            popUpView.findViewById<ImageView>(R.id.fourthAvatar).setOnClickListener {
                val avatarID = avatarDB.push().key
                if (avatarID != null) {
                    val image = ImageData("https://www.seekpng.com/png/small/233-2331610_large-reading-owl-whats-your-favourite-book.png")
                    avatarDB.child(avatarID).setValue(image)
                }
                window.dismiss()
            }

        }

        avatarDB.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()) {
                    for (avatarSnapshot in p0.children) {
                       // var numofAvatars = p0.childrenCount - 1
                        val avatar = avatarSnapshot.getValue(ImageData::class.java)
                        Picasso.get()
                            .load(avatar?.image)
                            .fit()
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(android.R.drawable.stat_notify_error)
                            .into(profileAvatar)
                    }
                }

            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.")

            }


        })







        var readingCount = view.findViewById<TextView>(R.id.readingCount)
        var readList : MutableList<ReadBooks>
        readList = mutableListOf()

        var readRecycler = view.findViewById<RecyclerView>(R.id.readBooksRecycler)
        readRecycler.layoutManager = LinearLayoutManager(this.context)


        var database : DatabaseReference = FirebaseDatabase.getInstance("https://mindgarden-ae8ca-default-rtdb.europe-west1.firebasedatabase.app/").getReference("User").child(userName.text.toString()).child("ReadBooks")


        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for(bookSnapshot in p0.children){
                    val book = bookSnapshot.getValue(ReadBooks::class.java)
                    readList.add(book!!)
                }
                var readAdapter = UserRecyclerAdapter(readList)
                readRecycler.adapter = readAdapter
                val itemCount : Int
                itemCount = readAdapter.itemCount
                readingCount.text = itemCount.toString()

            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.")

            }


        })




        return view
    }



}