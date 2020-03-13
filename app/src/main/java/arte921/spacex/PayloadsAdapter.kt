package arte921.spacex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.payloadslayout.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Math.random

class PayloadsAdapter(private val pjo: JSONArray): RecyclerView.Adapter<PayloadsAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder = MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.payloadslayout,parent,false))

    override fun getItemCount(): Int = pjo.length()

    class MainViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.view.tva.text = random().toString()
    }

}

