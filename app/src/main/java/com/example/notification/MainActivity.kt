package com.Notification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notification.Badger
import com.example.notification.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var textview : TextView
    private lateinit var button : Button
    private lateinit var imageView: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // textview reference
        textview = findViewById(R.id.textView)

            // button reference
        button = findViewById(R.id.button)

        imageView = findViewById(R.id.baderbarg)

        imageView.setOnClickListener {
            val intent = Intent(this, Badger::class.java)
            startActivity(intent)
        }


        // realtime database reference
        //val realtimeDatabase = Firebase.initialize(this@MainActivity)

        // button on click, firebase
        // token will obtain on clicking
        button.setOnClickListener {
            // Retrieving the FCM token


            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    textview.text = "Fetching FCM registration token failed"
                    return@OnCompleteListener
                }

                // fetching the token
                val token = task.result

                Log.d("mytoken",token)

                textview.text = "Token saved successfully!"

                // directory reference
               // val tokenDirRef = realtimeDatabase.getReference("Tokens")

                // storing the value
               // tokenDirRef.setValue(token.toString())

                // toast to show message
                Toast.makeText(
                    baseContext,
                    "Firebase Generated Successfully and saved to realtime database",
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }
}
