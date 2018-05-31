package com.sdadg.roomviewmodeldemo.presentation

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.Menu
import android.view.MenuItem
import com.sdadg.roomviewmodeldemo.R
import com.sdadg.roomviewmodeldemo.data.adapters.PostRecyclerViewAdapter
import com.sdadg.roomviewmodeldemo.data.entities.Post
import com.sdadg.roomviewmodeldemo.data.old.CustomSqliteOpenHelper
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.content_posts.*
import java.lang.ref.WeakReference
import java.util.*

class PostsActivity : AppCompatActivity() {

    private val postItemAdapterListener = AdapterListener(this)
    //val db: IDataRepository = RoomRepository(this)
    val db = CustomSqliteOpenHelper(this)
    lateinit var posts: List<Post>
    val adapter = PostRecyclerViewAdapter(postItemAdapterListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            createPost(((posts.size) + 1).toLong())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_clear -> {
                clearData()
                true
            }
            R.id.action_load_more_data -> {
                insertTestData()
                loadData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()

        loadData()
    }

    fun loadData() {
        LoadDataTask(WeakReference(this)).execute()

        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this, VERTICAL, false)
    }

    private fun insertTestData() {
        for (x in 1..20) {
            createPost(x.toLong())
        }
    }

    private fun createPost(postId: Long) {
        InsertDataTask(WeakReference(this)).execute(Post(null, "Post $postId", Calendar.getInstance().timeInMillis))
    }

    private fun clearData() {

        DeleteDataTask(WeakReference(this)).execute()
    }

    class AdapterListener (val context: Context) : PostRecyclerViewAdapter.Listeners {
        override fun onItemClick(postId: Long, postTitle: String) {
            val intent = Intent(context, PostDetailsActivity::class.java)
            intent.putExtra("postId", postId)
            intent.putExtra("postTitle", postTitle)
            context.startActivity(intent)
        }
    }

    class LoadDataTask(private var weakReference: WeakReference<PostsActivity>) : AsyncTask<Void, Void, List<Post>>() {

        override fun doInBackground(vararg params: Void?): List<Post> {
            return weakReference.get()?.db?.allPosts ?: arrayListOf()
        }

        override fun onPostExecute(result: List<Post>) {
            super.onPostExecute(result)

            weakReference.get()?.posts = result
            weakReference.get()?.adapter?.loadData(result)
            weakReference.get()?.adapter?.notifyDataSetChanged()
        }
    }

    class InsertDataTask(private var weakReference: WeakReference<PostsActivity>) : AsyncTask<Post, Void, Void>() {

        override fun doInBackground(vararg params: Post): Void? {
            weakReference.get()?.db?.insertPost(params[0])
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            weakReference.get()?.loadData()
        }
    }

    class DeleteDataTask(private var weakReference: WeakReference<PostsActivity>) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            weakReference.get()?.db?.deletePosts()
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            weakReference.get()?.loadData()
        }
    }
}
