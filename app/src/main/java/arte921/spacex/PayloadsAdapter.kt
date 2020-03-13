package arte921.spacex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.payloadslayout.view.*
import org.json.JSONArray
import java.lang.Math.random

class PayloadsAdapter(private val pjo: JSONArray): RecyclerView.Adapter<PayloadsAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder = MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.payloadslayout,parent,false))

    override fun getItemCount(): Int = pjo.length()

    class MainViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val jo = pjo.getJSONObject(position)

        holder.view.payloadnumber.text = (position + 1).toString()
        holder.view.payloadname.text = jo.get("payload_id").toString()
        holder.view.payloadtype.text = jo.get("payload_type").toString()
        holder.view.manufacturer.text = jo.get("manufacturer").toString()
        holder.view.nationality.text = jo.get("nationality").toString()
        holder.view.payloadmass.text = "${jo.get("payload_mass_kg")} kg"

        holder.view.orbittype.text = jo.getJSONObject("orbit_params").get("reference_system").toString()
        holder.view.regime.text = jo.getJSONObject("orbit_params").get("regime").toString()

        var customerstring = ""
        for(i in 0 until jo.getJSONArray("customers").length()){
            if(i > 0) customerstring += ", "
            customerstring += jo.getJSONArray("customers").get(i)
        }
        holder.view.customers.text = customerstring

    }

}

