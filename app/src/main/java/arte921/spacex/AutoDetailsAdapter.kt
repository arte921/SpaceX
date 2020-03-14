package arte921.spacex

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.onetextview.view.*

class AutoDetailsAdapter(private val nsi: List<NamedStringIndented>): RecyclerView.Adapter<AutoDetailsAdapter.MainViewHolder>() {
    class MainViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val tv = LayoutInflater.from(parent.context).inflate(R.layout.onetextview,parent,false) as TextView
        return MainViewHolder(tv)
    }

    override fun getItemCount(): Int = nsi.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        var prototype = ""
        prototype += "   ".repeat(nsi[position].Indentation)

        when(nsi[position].typeflag){
            ISHEADER -> {
                holder.view.tv.setTypeface(holder.view.tv.typeface, Typeface.BOLD)
                prototype += nsi[position].label.replace("-"," ").replace("_"," ")
            }
            ISPROPERTY -> {
                prototype += nsi[position].label.replace("-"," ").replace("_"," ")
                prototype += ": "
                prototype += nsi[position].str

            }
        }

        holder.view.tv.text = prototype



    }


}