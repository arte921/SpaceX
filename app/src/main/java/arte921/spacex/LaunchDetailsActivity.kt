package arte921.spacex

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.autodetailslayout.*
import kotlinx.android.synthetic.main.launchdetailspage.*
import kotlinx.android.synthetic.main.launchdetailspage.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DateFormat
import java.util.*

lateinit var jostring: String
lateinit var jo: JSONObject

class LaunchDetailsActivity : AppCompatActivity() {

    fun normal(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launchdetailspage)

        jostring = intent.getStringExtra(JONAME)
        jo = JSONObject(jostring)
        title = jo.get("mission_name").toString()

        missionname5.text = jo.get("mission_name").toString()
        datetime5.text = DateFormat.getDateTimeInstance().format(Date(jo.get("launch_date_unix").toString().toLong() * 1000)).toString()
        rocketname5.text = jo.getJSONObject("rocket").get("rocket_name").toString()
        launchlocation5.text = jo.getJSONObject("launch_site").get("site_name_long").toString()
        description.text = jo.get("details").toString()

        val viewManager = LinearLayoutManager(this)
        payloadsrv.apply{
            layoutManager = viewManager
            adapter = PayloadsAdapter(jo.getJSONObject("rocket").getJSONObject("second_stage").getJSONArray("payloads"))
        }
    }
}