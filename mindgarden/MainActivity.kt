package hr.ferit.matejajakic.mindgarden

import android.content.Intent
import android.content.SharedPreferences
import android.media.audiofx.LoudnessEnhancer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), LogOutListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentController) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)
        val preference : SharedPreferences
        preference = getSharedPreferences("EXTRA", MODE_PRIVATE)
        var pref : String
        pref = preference.getString("intent_extra","").toString()
        intent.putExtra("username", pref)

    }

    override fun logOut() {
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", true).commit()
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
        this.finish()
    }


}