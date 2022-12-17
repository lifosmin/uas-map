package id.ac.umn.uas.activities.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R
import id.ac.umn.uas.models.Job
import com.bumptech.glide.request.RequestOptions
import id.ac.umn.uas.activities.seeker.appliedjobs.DetailAppliedSeekerActivity

class AppliedJobListAdapter(private val dataSet: List<Job>, private val context: Context) :
    RecyclerView.Adapter<AppliedJobListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: LinearLayout
        val id : TextView
        val image : CircleImageView
        val judul : TextView
        val lokasi : TextView
        val tanggal : TextView
        val gaji : TextView
        val deskripsi : TextView

        init {
            textView = view.findViewById(R.id.itemView)
            id = view.findViewById(R.id.jobId)
            image = view.findViewById(R.id.gambarJob)
            judul = view.findViewById(R.id.judulJob)
            lokasi = view.findViewById(R.id.lokasiJob)
            tanggal = view.findViewById(R.id.tanggalJob)
            gaji = view.findViewById(R.id.jobGaji)
            deskripsi = view.findViewById(R.id.jobDeskripsi)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.seeker_applied_job, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.id.text = dataSet[position].id.toString()
        viewHolder.judul.text = dataSet[position].job_title
        viewHolder.lokasi.text = dataSet[position].job_location
        viewHolder.tanggal.text = dataSet[position].job_date
        viewHolder.gaji.text = dataSet[position].job_price
        viewHolder.deskripsi.text = dataSet[position].job_desc

        Glide.with(context)
            .load(dataSet[position].job_image)
            .apply(RequestOptions().override(100, 100))
            .into(viewHolder.image)

        viewHolder.textView.setOnClickListener {
            val intent = Intent(context, DetailAppliedSeekerActivity::class.java)
            intent.putExtra("id", viewHolder.id.text.toString())
            intent.putExtra("judul", viewHolder.judul.text.toString())
            intent.putExtra("lokasi", viewHolder.lokasi.text.toString())
            intent.putExtra("tanggal", viewHolder.tanggal.text.toString())
            intent.putExtra("gaji", viewHolder.gaji.text.toString())
            intent.putExtra("deskripsi", viewHolder.deskripsi.text.toString())
            intent.putExtra("image", dataSet[position].job_image)
            context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
