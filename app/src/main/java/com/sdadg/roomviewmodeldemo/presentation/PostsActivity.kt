package com.sdadg.roomviewmodeldemo.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.Menu
import android.view.MenuItem
import com.sdadg.roomviewmodeldemo.R
import com.sdadg.roomviewmodeldemo.data.adapters.PostRecyclerViewAdapter
import com.sdadg.roomviewmodeldemo.data.entities.Post
import com.sdadg.roomviewmodeldemo.data.repositories.IDataRepository
import com.sdadg.roomviewmodeldemo.data.repositories.RoomRepository
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.content_posts.*
import java.util.*

class PostsActivity : AppCompatActivity() {

    private val postItemAdapterListener = AdapterListener(this)
    private val db: IDataRepository = RoomRepository(this)
    //val db = CustomSqliteOpenHelper(this)
    private lateinit var posts: List<Post>
    private val adapter = PostRecyclerViewAdapter(postItemAdapterListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            createPost(((posts.size) + 1).toLong())
            loadData()
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

    private fun loadData() {
        posts = db.getAllPosts()

        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        adapter.loadData(posts)
    }

    private fun insertTestData() {
        for (x in 1..20) {
            createPost(x.toLong())
        }
    }

    private fun createPost(postId: Long) {
        db.insertPost(Post(postId, "Post $postId", Calendar.getInstance().timeInMillis))
    }

    private fun clearData() {
        db.deletePosts()
        loadData()
    }

    private fun refreshComments() {
        loadData()
        adapter.notifyDataSetChanged()
    }

    class AdapterListener (val context: Context) : PostRecyclerViewAdapter.Listeners {
        override fun onItemClick(postId: Long) {
            val intent = Intent(context, PostDetailsActivity::class.java)
            intent.putExtra("postId", postId)
            context.startActivity(intent)
        }
    }
}
