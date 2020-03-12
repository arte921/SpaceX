package arte921.spacex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.launchcard.view.*
import org.json.JSONObject
import java.lang.Math.random
import java.text.DateFormat.getDateTimeInstance
import java.util.*

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.MainViewHolder = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.launchcard,parent,false))

    class MainViewHolder(val view: View) :RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int{
        return when(currentFlag){
            SUCCESSFUL -> json.length()
            HASFAILED -> 2
            TESTING -> 3
            else -> 1

        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        when(currentFlag){

            SUCCESSFUL -> {
                val jo = json.getJSONObject(position)
                holder.view.missionname.text = jo.get("mission_name").toString()
                holder.view.datetime.text = getDateTimeInstance().format(Date(jo.get("launch_date_unix").toString().toLong() * 1000)).toString()
                holder.view.rocketname.text = jo.getJSONObject("rocket").get("rocket_name").toString()
                holder.view.launchlocation.text = jo.getJSONObject("launch_site").get("site_name_long").toString()
            }

            ISLOADING -> holder.view.datetime.text = context.getString(R.string.loading)

            HASFAILED -> {
                when(position) {
                    0 -> holder.view.datetime.text = context.getString(R.string.failed1)
                    1 -> holder.view.datetime.text = context.getString(R.string.failed2)
                    else -> holder.view.datetime.text = ""
                }
            }

            TESTING -> {
                when(position) {
                    0 -> holder.view.datetime.text = context.getString(R.string.test)
                    1 -> holder.view.datetime.text = random().toString()
                    2 -> holder.view.datetime.text = utilTestString
                    else -> holder.view.datetime.text = ""
                }
            }

        }
    }
}