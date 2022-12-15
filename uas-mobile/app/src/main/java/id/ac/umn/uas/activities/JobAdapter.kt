import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R
import id.ac.umn.uas.models.Job
import id.ac.umn.uas.models.User
import androidx.fragment.app.FragmentActivity;
import id.ac.umn.uas.models.JobList

class JobAdapter(private val dataSet: List<JobList>, private val context: Context) :
    RecyclerView.Adapter<JobAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: ConstraintLayout
        val id : TextView
        val image : CircleImageView
        val judul : TextView
        val lokasi : TextView
        val tanggal : TextView

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.itemView)
            id = view.findViewById(R.id.jobId)
            image = view.findViewById(R.id.gambarJob)
            judul = view.findViewById(R.id.judulJob)
            lokasi = view.findViewById(R.id.lokasiJob)
            tanggal = view.findViewById(R.id.tanggalJob)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.seeker, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.id.text = dataSet[position].job?.get(position)?.id.toString()
        viewHolder.judul.text = dataSet[position].job?.get(position)?.job_title
        viewHolder.lokasi.text = dataSet[position].job?.get(position)?.job_location
        viewHolder.tanggal.text = dataSet[position].job?.get(position)?.job_date

        Glide.with(context)
            .load(dataSet[position].job?.get(position)?.job_image)
            .into(viewHolder.image)

        viewHolder.textView.setOnClickListener {
            val sp = context.getSharedPreferences("sharedPrefs", AppCompatActivity.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("job", Gson().toJson(dataSet[position]))
            editor.apply()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
