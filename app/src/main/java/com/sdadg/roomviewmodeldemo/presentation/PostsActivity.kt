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
import com.sdadg.roomviewmodeldemo.data.ObjectBoxApplication
import com.sdadg.roomviewmodeldemo.data.adapters.PostRecyclerViewAdapter
import com.sdadg.roomviewmodeldemo.presentation.roomviewmodeldemo.data.entities.Post
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.content_posts.*
import java.util.*

class PostsActivity : AppCompatActivity() {

    val postItemAdapterListener = AdapterListener(this)
    //val db: IDataRepository = RoomRepository(this)
//    val db = CustomSqliteOpenHelper(this)
    lateinit var postBox: Box<Post>
    lateinit var posts: List<Post>
    val adapter = PostRecyclerViewAdapter(postItemAdapterListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postBox = (application as ObjectBoxApplication).boxStore.boxFor<Post>()

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

    fun loadData() {
        posts = postBox.all

        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        adapter.loadData(posts)
    }

    fun insertTestData() {
        for (x in 1..20) {
            createPost(x.toLong())
        }
    }

    fun createPost(postId: Long) {
        postBox.put(Post(0, "Post $postId", Calendar.getInstance().timeInMillis))
    }

    fun clearData() {
        postBox.removeAll()
        loadData()
    }

    private fun refreshComments() {
        loadData()
        adapter.notifyDataSetChanged()
    }

    class AdapterListener (val context: Context) : PostRecyclerViewAdapter.Listeners {
        override fun onItemClick(postId: Long, postTitle: String) {
            val intent = Intent(context, PostDetailsActivity::class.java)
            intent.putExtra("postId", postId)
            intent.putExtra("postTitle", postTitle)
            context.startActivity(intent)
        }
    }
}
