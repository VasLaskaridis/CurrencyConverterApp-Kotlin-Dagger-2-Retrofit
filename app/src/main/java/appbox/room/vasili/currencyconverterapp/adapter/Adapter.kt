package appbox.room.vasili.currencyconverterapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import appbox.room.vasili.currencyconverterapp.R
import javax.inject.Inject


class Adapter @Inject constructor(private val currencyList: Array<String>): RecyclerView.Adapter<Adapter.MyViewHolder>(){

    private var currencyData: ArrayList<Double> = ArrayList()
    lateinit var context: Context
    private var currencyClickListener: Adapter.onCurrencyClickListener? = null


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.getContext()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_currency, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.amound.text=String.format("%.2f", currencyData.get(position))
        holder.name.text=currencyList[position]
        val id=context.resources.getIdentifier("drawable/_"+currencyList[position].lowercase(),null,context.getPackageName())
        holder.image.setImageResource(id)
    }

    override fun getItemCount(): Int {
        return currencyData.size
    }

    fun setCurrencyData(data: ArrayList<Double>) {
        currencyData=data
        notifyDataSetChanged()
    }

    interface onCurrencyClickListener {
        fun onCurrencyClick(position: Int)
    }

    fun setCurrencyClickListener(listener: onCurrencyClickListener) {
        currencyClickListener = listener
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val amound: TextView = itemView.findViewById(R.id.txtview_amountConverted)
        val name: TextView = itemView.findViewById(R.id.txtview_currencyName)
        val image: ImageView = itemView.findViewById(R.id.imgview_currencyImage)
        init{
            itemView.setOnClickListener(object:View.OnClickListener{
                override fun onClick(v: View?) {
                    val position = adapterPosition
                    if (currencyClickListener != null && position != RecyclerView.NO_POSITION) {
                        currencyClickListener!!.onCurrencyClick(position)

                    }
                }
            })
        }

    }
}