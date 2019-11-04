package ch.hearc.fidarc.ui.client.account

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.hearc.fidarc.R

class FidelityCardAdapter(private val context: Context, val fidelityCardList:List<FidelityCard>):RecyclerView.Adapter<FidelityCardAdapter.MyViewHolder>(){
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var companyName = view.findViewById<View>(R.id.companyName) as TextView
        var nbFidelityPoints = view.findViewById<View>(R.id.nbFidelityPoint) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fidelity_card_list_row, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fidelityCard = fidelityCardList[position]
        holder.companyName.text = fidelityCard.companyName

        holder.nbFidelityPoints.text = context.resources.getString(R.string.number_of_points) + fidelityCard.currentNbOfPoints+"/"+fidelityCard.totalNbOfPoints
    }

    override fun getItemCount(): Int {
        return fidelityCardList.size
    }
}