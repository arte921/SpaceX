package arte921.spacex

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

const val url = "https://api.spacexdata.com/v3/launches/upcoming"
const val SUCCESSFUL = 0
const val ISLOADING = 1
const val HASFAILED = 2


class MainActivity : AppCompatActivity() {
    var json = JSONObject()
    var currentFlag = ISLOADING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)
        mainrv.apply{
            layoutManager = viewManager
            adapter = RecyclerAdapter(json, ISLOADING)
        }

        val queue = Volley.newRequestQueue(this)
        val missionsRequest = StringRequest(Request.Method.GET,url,Response.Listener { response ->
            json = JSONObject(response)
            mainrv.adapter?.notifyDataSetChanged()
            currentFlag = SUCCESSFUL
        }, Response.ErrorListener {
            mainrv.adapter?.notifyDataSetChanged()
            currentFlag = HASFAILED
        })
        queue.add(missionsRequest)


    }
}
