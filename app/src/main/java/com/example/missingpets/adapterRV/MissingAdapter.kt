import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.missingpets.R
import com.example.missingpets.network.recyclerPet
import com.squareup.picasso.Picasso

class MissingAdapter (private val dataset: List<recyclerPet>, private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<MissingAdapter.MissingViewHolder>() {

    class MissingViewHolder (val view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MissingViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.vh_list_buscado, parent, false)
        Log.d("RV", "onCreateViewHolder")
        return MissingViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MissingViewHolder, position: Int) {
        val item = dataset[position]

        holder.textView.text = item.description
        val BASE_URL = "https://sea.net.ar/missingpets/"
        val pathfile = BASE_URL + "img/" +item.photopath
        Picasso.get().load(pathfile).into(holder.imageView)
        Log.d("RV", "onBindViewHolder")

        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        Log.d("RV", "getItemCount es: "+ dataset.size.toString())
        return dataset.size
    }

    class OnClickListener(val clickListener: (pet: recyclerPet) -> Unit) {
        fun onClick(pet: recyclerPet) = clickListener(pet)
    }

}