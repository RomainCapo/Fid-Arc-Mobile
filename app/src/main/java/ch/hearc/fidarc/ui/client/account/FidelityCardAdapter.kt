package ch.hearc.fidarc.ui.client.account

import android.annotation.SuppressLint
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ch.hearc.fidarc.R
import ch.hearc.fidarc.ui.data.model.FidelityCard



class FidelityCardAdapter(private val context: Context, private val fidelityCardList:List<FidelityCard>):RecyclerView.Adapter<FidelityCardAdapter.MyViewHolder>(){
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Get the component of the recycler view
        var container = view.findViewById<View>(R.id.fidelityCardContainer) as LinearLayout
        var companyName = view.findViewById<View>(R.id.companyName) as TextView
        var messageToUser = view.findViewById<View>(R.id.messageToUser) as TextView
        var nbFidelityPoints = view.findViewById<View>(R.id.nbFidelityPoints) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fidelity_card_list_row, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fidelityCard = fidelityCardList[position]

        //Bind the data on the correct view item
        holder.container.setBackgroundColor(ContextCompat.getColor(context,getColorIdFromString("color" + fidelityCard.card_color_id)))
        holder.companyName.text = fidelityCard.company_name
        holder.messageToUser.text = fidelityCard.message_to_user
        holder.nbFidelityPoints.text = context.resources.getString(R.string.number_of_points) + fidelityCard.current_number_of_points+"/"+fidelityCard.total_number_of_points
    }

    override fun getItemCount(): Int {
        return fidelityCardList.size
    }

    /**
     * Return the color resource id from the given name
     *
     * @param colorName name of the color resource
     * @return return the id from the color resource
     */
    private fun getColorIdFromString(colorName: String):Int{
        return context.resources.getIdentifier(colorName, "color", context.packageName)
    }
}