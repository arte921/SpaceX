package arte921.spacex

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

const val url = "https://api.spacexdata.com/v3/launches/upcoming"
const val testmode = true

const val SUCCESSFUL = 0
const val ISLOADING = 1
const val HASFAILED = 2
const val TESTING = 3
const val JONAME = "the_jo_JSONObject"

lateinit var json: JSONArray
var currentFlag = ISLOADING
var utilTestString = ""
lateinit var context: Context

class MainActivity : AppCompatActivity() {

    private fun logError(msg: String){
        if(testmode){
            Log.e("error function",msg)
            currentFlag = TESTING
            utilTestString = msg
            mainrv.adapter?.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        val viewManager = LinearLayoutManager(this)
        mainrv.apply{
            layoutManager = viewManager
            adapter = RecyclerAdapter()
        }

        //class NamedStringIntended(val label: String, val str: String, val Indentation: Int)

        val queue = RequestQueue(DiskBasedCache(cacheDir,1024*1024), BasicNetwork(HurlStack())).apply{start()}

        val missionsRequest = JsonArrayRequest(Request.Method.GET,url,null,Response.Listener { response ->
            currentFlag = SUCCESSFUL
            json = response
            mainrv.adapter?.notifyDataSetChanged()
        }, Response.ErrorListener {error ->
            currentFlag = HASFAILED
            mainrv.adapter?.notifyDataSetChanged()
            logError(error.toString())
        })
        queue.add(missionsRequest)
    }
}