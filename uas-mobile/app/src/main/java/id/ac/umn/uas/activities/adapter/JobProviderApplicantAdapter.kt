package id.ac.umn.uas.activities.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.umn.uas.R
import id.ac.umn.uas.models.Job
import com.bumptech.glide.request.RequestOptions
import id.ac.umn.uas.activities.provider.DetailProviderActivity
import id.ac.umn.uas.activities.seeker.DetailSeekerActivity
import id.ac.umn.uas.models.User

class JobProviderApplicantAdapter(private val dataSet: List<User>, private val context: Context) :
    RecyclerView.Adapter<JobProviderApplicantAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: LinearLayout
        val id : TextView
        val image : CircleImageView
        val nama : TextView
        val email : TextView
        val kontak : TextView
        val tanggal : TextView
        val kelamin : TextView
        val alamat : TextView
        val btnDetail : Button

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.itemView)
            id = view.findViewById(R.id.userId)
            image = view.findViewById(R.id.userGambar)
            nama = view.findViewById(R.id.userNama)
            email = view.findViewById(R.id.userEmail)
            kontak = view.findViewById(R.id.userKontak)
            tanggal = view.findViewById(R.id.userTanggalLahir)
            kelamin = view.findViewById(R.id.userJenisKelamin)
            alamat = view.findViewById(R.id.userAlamat)
            btnDetail = view.findViewById(R.id.buttonDetail)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.provider_applicant, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        val user = dataSet[position].id.toString()
        viewHolder.id.text = user
        viewHolder.nama.text = dataSet[position].nama
        viewHolder.email.text = dataSet[position].email
        viewHolder.kontak.text = dataSet[position].no_telp
        viewHolder.tanggal.text = dataSet[position].tanggal_lahir
        viewHolder.kelamin.text = dataSet[position].jenis_kelamin
        viewHolder.alamat.text = dataSet[position].alamat

        Glide.with(context)
            .load(dataSet[position].image)
            .apply(RequestOptions().override(100, 100))
            .into(viewHolder.image)

        viewHolder.btnDetail.setOnClickListener {
            val intent = Intent(context, DetailProviderActivity::class.java)
            intent.putExtra("id", user)
            intent.putExtra("nama", dataSet[position].nama)
            intent.putExtra("email", dataSet[position].email)
            intent.putExtra("kontak", dataSet[position].no_telp)
            intent.putExtra("tanggal", dataSet[position].tanggal_lahir)
            intent.putExtra("kelamin", dataSet[position].jenis_kelamin)
            intent.putExtra("alamat", dataSet[position].alamat)
            context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
