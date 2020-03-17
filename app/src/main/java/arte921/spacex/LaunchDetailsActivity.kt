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
    var nsi: MutableList<NamedStringIndented> = mutableListOf()

    fun examineObject(cjo: JSONObject, indentation: Int, thisdottag: String){
        nsi.add(NamedStringIndented(thisdottag,"", indentation-1, ISHEADER))
        for(tag in cjo.keys()){
            try{
                examineArray(cjo.getJSONArray(tag),indentation+1,tag)
            }catch(_: JSONException){
                try{
                    examineObject(cjo.getJSONObject(tag),indentation+1,tag)
                }catch(_: JSONException){
                    if(!cjo.isNull(tag)) nsi.add(NamedStringIndented(tag,cjo.getString(tag),indentation,ISPROPERTY))
                }
            }
        }
    }

    fun examineArray(cja: JSONArray, indentation: Int, thisdottag: String){
        nsi.add(NamedStringIndented(thisdottag,"", indentation-1, ISHEADER))
        for(i in 0 until cja.length()){
            try{
                examineArray(cja.getJSONArray(i),indentation+1, "${thisdottag.trimEnd('s')} ${i+1}")
            }catch(_: JSONException){
                try{
                    examineObject(cja.getJSONObject(i),indentation+1,"${thisdottag.trimEnd('s')} ${i+1}")
                }catch(_: JSONException){
                    if(!cja.isNull(i)) nsi.add(NamedStringIndented(i.toString(),cja.getString(i),indentation,ISPROPERTY))
                }
            }
        }
    }

    fun normal(){
        setContentView(R.layout.launchdetailspage)

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

    fun auto(){
        setContentView(R.layout.autodetailslayout)

        if(nsi.size < 3) examineObject(jo,1,jo.get("mission_name").toString())

        val viewManager = LinearLayoutManager(this)
        autorv.apply{
            layoutManager = viewManager
            adapter = AutoDetailsAdapter(nsi)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        jostring = intent.getStringExtra(JONAME)
        jo = JSONObject(jostring)
        title = jo.get("mission_name").toString()

        if(showfull){
            auto()
        }else{
            normal()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.details,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.showall -> {
                /*
                val intent = Intent(this, AutoDetailsActivity()::class.java).apply{
                    putExtra(JONAME,jostring)
                }
                context.startActivity(intent)*/

                showfull = !showfull
                if(showfull){
                    auto()
                }else{
                    normal()
                }
                /*
                finish()
                startActivity(intent)*/
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}