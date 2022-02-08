package hr.ferit.matejajakic.mindgarden

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val openActivityButton = findViewById<Button>(R.id.logInButton)
        val enterUserName = findViewById<EditText>(R.id.enterUserName)
        val enterNewUserName = findViewById<EditText>(R.id.enterNewUserName)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val database : DatabaseReference = FirebaseDatabase.getInstance("https://mindgarden-ae8ca-default-rtdb.europe-west1.firebasedatabase.app/").getReference("User")
        var isFirstRun : Boolean
        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true)



        val intent = Intent(this, MainActivity::class.java)
        if(isFirstRun){


            openActivityButton.setOnClickListener {
                var provjera : Int
                provjera = 1
                if(enterUserName.text.isNotEmpty()) {

                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            if(provjera == 1 ){
                                if(p0!!.exists()) {
                                    provjera = provjera + 1
                                    if (p0.hasChild(enterUserName.text.toString())) {
                                        startActivity(intent)
                                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                            .putBoolean("isFirstRun", false).commit()
                                        getSharedPreferences("EXTRA", MODE_PRIVATE).edit()
                                            .putString("intent_extra", enterUserName.text.toString()).commit()
                                        finish()


                                    }else{
                                        Toast.makeText(
                                            this@SecondActivity,
                                            "Korisničko ime ne postoji",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }

                            }

                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Log.w(ContentValues.TAG, "Failed to read value.")

                        }


                    })


                } else{
                    Toast.makeText(this, "Unesite korisničko ime", Toast.LENGTH_SHORT).show()
                }
            }

        }



        if(isFirstRun){

            signUpButton.setOnClickListener {
                var provjera : Int
                provjera = 1
                if(enterNewUserName.text.isNotEmpty()) {
                    database.addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            if(provjera == 1 ){
                            if(p0!!.exists()) {
                                provjera = provjera + 1
                                if (p0.hasChild(enterNewUserName.text.toString())) {

                                                Toast.makeText(
                                                    this@SecondActivity,
                                                    "Korisničko ime već postoji",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                }else{
                                        startActivity(intent)
                                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                            .putBoolean("isFirstRun", false).commit()
                                        getSharedPreferences("EXTRA", MODE_PRIVATE).edit()
                                            .putString("intent_extra", enterNewUserName.text.toString()).commit()
                                        finish()
                                }
                                }

                            }

                        }

                        override fun onCancelled(p0: DatabaseError) {
                            Log.w(ContentValues.TAG, "Failed to read value.")

                        }


                    })

                } else{
                    Toast.makeText(this, "Unesite novo korisničko ime", Toast.LENGTH_SHORT).show()
                }
            }

        }else{

            startActivity(intent)
            this.finish()
        }
    }

}