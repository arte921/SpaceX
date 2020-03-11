package arte921.spacex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class RecyclerAdapter(val inputData: JSONObject, val StatusFlag: Int) : RecyclerView.Adapter<RecyclerAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.MainViewHolder = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.launchcard,parent,false))

    class MainViewHolder(view: View) :RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MainViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}