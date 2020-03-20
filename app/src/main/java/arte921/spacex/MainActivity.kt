package arte921.spacex
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Math.random
import java.lang.RuntimeException
import java.text.DateFormat
import java.util.*

lateinit var json: JSONArray
var currentFlag = ISLOADING
var utilTestString = ""
lateinit var context: Context
lateinit var sharedPref: SharedPreferences
lateinit var queue: RequestQueue

class MainActivity : AppCompatActivity() {

    private fun logError(msg: String){
        if(testmode){
            Log.e("error function",msg)
            currentFlag = TESTING
            utilTestString = msg
            mainrv.adapter?.notifyDataSetChanged()
        }
    }

    fun refresh(){
        val missionsRequest = JsonArrayRequest(Request.Method.GET,url,null,Response.Listener { response ->
            currentFlag = SUCCESSFUL
            json = response
            mainrv.adapter?.notifyDataSetChanged()

            with(sharedPref.edit()){
                putString(JSONSAVENAME, response.toString())
                putLong(TIMESAVENAME, System.currentTimeMillis())
                apply()
            }

        }, Response.ErrorListener {error ->
            val cachedString = sharedPref.getString(JSONSAVENAME, thiscantbejson)
            if(cachedString != thiscantbejson){
                currentFlag = SUCCESSFUL
                json = JSONArray(cachedString)
                val formattedcachedate = DateFormat.getDateTimeInstance().format(Date(sharedPref.getLong(TIMESAVENAME,0))).toString()
                Toast.makeText(applicationContext,"No connection.\nShowing data from $formattedcachedate",Toast.LENGTH_LONG).show()
            }else{
                currentFlag = HASFAILED
                logError(error.toString())
            }
            mainrv.adapter?.notifyDataSetChanged()
        })
        queue.add(missionsRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        sharedPref = this.getSharedPreferences(FILEKEY,Context.MODE_PRIVATE)
        showfull = sharedPref.getBoolean(FULLSAVENAME, showfull)

        showfull = sharedPref.getBoolean(FULLSAVENAME,true)

        val viewManager = LinearLayoutManager(this)
        mainrv.apply{
            layoutManager = viewManager
            adapter = RecyclerAdapter()
        }

        queue = RequestQueue(DiskBasedCache(cacheDir,1024*1024), BasicNetwork(HurlStack())).apply{start()}
        refresh()
/*
        swiperefresh.setOnRefreshListener{
            refresh()
        }*/
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.details,menu)
        if (menu != null) menu.getItem(0).isChecked  = showfull

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.showall -> {
                showfull = !item.isChecked
                item.isChecked  = showfull
                with(sharedPref.edit()){
                    putBoolean(FULLSAVENAME, showfull)
                    apply()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}