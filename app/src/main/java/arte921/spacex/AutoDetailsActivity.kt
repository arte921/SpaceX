package arte921.spacex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.autodetailslayout.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Math.random



class AutoDetailsActivity :AppCompatActivity() {
    var nsi: MutableList<NamedStringIndented> = mutableListOf()

    fun examineObject(cjo: JSONObject, indentation: Int, parenttag: String){
        nsi.add(NamedStringIndented(parenttag,"", indentation-1, ISHEADER))
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

    fun examineArray(cja: JSONArray, indentation: Int, parenttag: String){
        nsi.add(NamedStringIndented(parenttag,"", indentation-1, ISHEADER))
        for(i in 0 until cja.length()){
            try{
                examineArray(cja.getJSONArray(i),indentation+1, "${parenttag.trimEnd('s')} ${i+1}")
            }catch(_: JSONException){
                try{
                    examineObject(cja.getJSONObject(i),indentation+1,"${parenttag.trimEnd('s')} ${i+1}")
                }catch(_: JSONException){
                    if(!cja.isNull(i)) nsi.add(NamedStringIndented(i.toString(),cja.getString(i),indentation,ISPROPERTY))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.autodetailslayout)

        val jo = JSONObject(intent.getStringExtra(JONAME))
        title = jo.get("mission_name").toString()

        examineObject(jo,1,jo.get("mission_name").toString())

        val viewManager = LinearLayoutManager(this)
        autorv.apply{
            layoutManager = viewManager
            adapter = AutoDetailsAdapter(nsi)
        }
    }
}