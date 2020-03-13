package arte921.spacex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.launchdetailspage.*
import org.json.JSONObject
import java.text.DateFormat
import java.util.*

class LaunchDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launchdetailspage)

        val jo = JSONObject(intent.getStringExtra(JONAME))

        missionname5.text = jo.get("mission_name").toString()
        datetime5.text = DateFormat.getDateTimeInstance().format(Date(jo.get("launch_date_unix").toString().toLong() * 1000)).toString()
        rocketname5.text = jo.getJSONObject("rocket").get("rocket_name").toString()
        launchlocation5.text = jo.getJSONObject("launch_site").get("site_name_long").toString()

        val viewManager = LinearLayoutManager(this)
        payloadsrv.apply{
            layoutManager = viewManager
            adapter = PayloadsAdapter(jo.getJSONObject("rocket").getJSONObject("second_stage").getJSONArray("payloads"))
        }
    }
}