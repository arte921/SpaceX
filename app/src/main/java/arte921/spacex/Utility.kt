package arte921.spacex

import com.android.volley.Header

const val ISHEADER = 1
const val ISPROPERTY = ISHEADER+1

var showfull = false

class NamedStringIndented(val label: String, val str: String, val Indentation: Int,val typeflag: Int)


/*
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
        }*/