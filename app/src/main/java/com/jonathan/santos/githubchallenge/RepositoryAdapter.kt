package com.jonathan.santos.githubchallenge


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jonathan.santos.githubchallenge.databinding.ItemRepositoryBinding
import com.jonathan.santos.githubchallenge.model.Repository
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

class RepositoryAdapter() : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    val picasso: Picasso by inject(Picasso::class.java)

    private var actualPage = 1

    var items: MutableList<Repository> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var loadNextPage: (Int) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryAdapter.RepositoryViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryAdapter.RepositoryViewHolder, position: Int) {
        if(position >= itemCount - 1){
           loadNextPage()
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setLoadNextPageFunction(loadFunction: (Int) -> Unit) {
        loadNextPage = loadFunction
        loadNextPage()
    }

    fun mergeItemsList(list: List<Repository>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    private fun loadNextPage(){
        loadNextPage.invoke(actualPage)
        actualPage++
    }

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {
            with(binding) {
                textViewRepositoryNameValue.text = repository.name
                textViewRepositoryAuthorNameValue.text = repository.author.name
                textViewRepositoryForksValue.text = repository.numberOfForks.toString()
                textViewRepositoryStarsValue.text = repository.numberOfStars.toString()
                picasso
                    .load(repository.author.picture)
                    .resize(IMAGE_SIZE_PX,IMAGE_SIZE_PX)
                    .centerCrop()
                    .into(imageViewAvatarPicture, object : Callback{
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }
                })
            }
        }
    }

    companion object {
        const val IMAGE_SIZE_PX = 200
    }
}
