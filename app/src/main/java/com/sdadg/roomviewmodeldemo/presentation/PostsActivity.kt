package com.sdadg.roomviewmodeldemo.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.Menu
import android.view.MenuItem
import com.sdadg.roomviewmodeldemo.ObjectBox
import com.sdadg.roomviewmodeldemo.R
import com.sdadg.roomviewmodeldemo.data.adapters.PostRecyclerViewAdapter
import com.sdadg.roomviewmodeldemo.data.entities.Post
import com.sdadg.roomviewmodeldemo.services.PostService
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.kotlin.boxFor
import io.objectbox.reactive.DataSubscriptionList
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.content_posts.*
import java.util.*

class PostsActivity : AppCompatActivity() {

    val TAG = PostsActivity::class.simpleName

    private val postItemAdapterListener = AdapterListener(this)
    private var posts: LiveData<List<Post>> = MutableLiveData<List<Post>>()
    private val adapter = PostRecyclerViewAdapter(postItemAdapterListener)
    private val dataSub = DataSubscriptionList()

    private lateinit var mPostBox: Box<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPostBox = (application as ObjectBox).boxStore.boxFor()
        var query = mPostBox.query().build()
        var list = query.find()


        query.subscribe(dataSub).onlyChanges().on(AndroidScheduler.mainThread()).observer { data ->
            adapter.loadData(data)
            adapter.notifyDataSetChanged()
        }


        setContentView(R.layout.activity_posts)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            createPost(((adapter.itemCount) + 1).toLong())
        }
        adapter.loadData(list)
        loadData()

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
                //loadData()
                true
            }
            R.id.action_start_auto_load -> {
                startLoadingService()
                true
            }
            R.id.action_stop_auto_load -> {
                stopLoadingService()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startLoadingService() {
        startService(Intent(this, PostService::class.java))
    }

    private fun stopLoadingService() {
        stopService(Intent(this, PostService::class.java))
    }

    private fun loadData() {
        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this, VERTICAL, false)
    }

    private fun insertTestData() {
        for (x in 1..20) {
            createPost(x.toLong())
        }
    }

    private fun createPost(postId: Long) {
        mPostBox.put(Post(null, "Manually created Post #: $postId", Calendar.getInstance().timeInMillis))
    }

    private fun clearData() {
        mPostBox.removeAll()
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
