package dev.mainhq.schedules.utils.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dev.mainhq.schedules.R
import dev.mainhq.schedules.Times
import dev.mainhq.schedules.fragments.dataStore
import dev.mainhq.schedules.preferences.BusInfo
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.launch


class StopListElemsAdapter(private val data: List<String>, private val headsign: String)
    : RecyclerView.Adapter<StopListElemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.stop_list_elem, parent, false),
            headsign
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = this.data[position]
        holder.stopNameTextView.text = data
        holder.favouriteSelectedView.setOnClickListener{ view ->
            if (view.tag.equals("off")) {
                view.setBackgroundResource(R.drawable.favourite_drawable_on)
                view.tag = "on"
                view.findViewTreeLifecycleOwner()!!.lifecycleScope.launch {
                    view.context.dataStore.updateData{favourites ->
                        favourites.copy(list = favourites.list.mutate {
                            //maybe add a tripid or some identifier so that it is a unique thing deleted
                            it.add(BusInfo(data, headsign))
                            Log.d("FAVOURITES", "CLICKED TO ADD A FAVOURITE!")
                        })
                    }
                }
            }
            else {
                view.setBackgroundResource(R.drawable.favourite_drawable_off)
                view.tag = "off"
                //todo add to favourites
                view.findViewTreeLifecycleOwner()!!.lifecycleScope.launch {
                    view.context.dataStore.updateData {favourites ->
                        favourites.copy(list = favourites.list.mutate {
                            it.remove(BusInfo(data, headsign))
                            Log.d("FAVOURITES", "CLICKED TO REMOVE A FAVOURITE!")
                        })
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    class ViewHolder(view: View, private val headsign : String) : RecyclerView.ViewHolder(view) {
        //TODO INSTEAD ADD A COLUMN IN DATABASE TO SET AS FAVOURITE A CERTAIN STOP, AND AFFICHER ONLY THE NEXT STOP
        val stopNameTextView: TextView
        val favouriteSelectedView : ImageView
        init {
            stopNameTextView = view.findViewById(R.id.stop)
            stopNameTextView.setOnClickListener{
                val stopName = (it as TextView).text as String
                val intent = Intent(view.context, Times::class.java)
                intent.putExtra("stopName", stopName)
                intent.putExtra("headsign", headsign)
                it.context.startActivity(intent)
                it.clearFocus()
            }
            favouriteSelectedView = view.findViewById(R.id.favourite_star_selection)
            //fixme check in datastore if the data references this. if yes, then set tag as "on" instead, and drawable also
            favouriteSelectedView.setBackgroundResource(R.drawable.favourite_drawable_off)
            favouriteSelectedView.tag = "off"
        }
    }
}