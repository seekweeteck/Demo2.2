package my.edu.tarc.demo22

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "my.edu.tarc.demo22"
    val notificationId = 1
    var font_size: Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val metrics = resources.displayMetrics

        font_size = textViewMessage.textSize / metrics.density

        createNotificationChannel()

        buttonNotifyMe.setOnClickListener {
            var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_info_outline_black_24dp)
                .setContentTitle(getString(R.string.title))
                .setContentText(getString(R.string.content_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)){
                notify(notificationId, builder.build())
            }
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        //TODO 4: Create a Search Manager
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.app_bar_search).actionView as SearchView).apply{
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) //DO not icontify the widget; expand it by default
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_zoom_in -> zoomIn()
            R.id.action_zoom_out -> zoomOut()
            R.id.app_bar_search -> search()
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun zoomIn(): Boolean {
        textViewMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, ++font_size)
        return true
    }

    private fun zoomOut(): Boolean {
        textViewMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, --font_size)
        return true
    }

    private fun search(): Boolean{
        val intentSearch: Intent = Intent(this, SearchActivity::class.java)
        startActivity(intentSearch)
        return true
    }
}
