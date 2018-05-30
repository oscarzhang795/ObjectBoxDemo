package com.sdadg.roomviewmodeldemo.presentation

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.sdadg.roomviewmodeldemo.R
import com.sdadg.roomviewmodeldemo.data.adapters.CommentRecyclerViewAdapter
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.data.old.CustomSqliteOpenHelper
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.content_feed.*
import java.lang.ref.WeakReference
import java.util.*

class PostDetailsActivity : AppCompatActivity() {

    var postId = 0L
    private var commentListener = CommentListener(WeakReference(this))
    val adapter = CommentRecyclerViewAdapter(commentListener)
    //val db: IDataRepository = RoomRepository(this)
    val db = CustomSqliteOpenHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        postId = intent.getLongExtra("postId", 0)
        val postTitle = intent.getStringExtra("postTitle")
        toolbar.title = "Comments for $postTitle"
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            addComment()
        }

        loadComments()
    }

    private fun addComment() {
        InsertDataTask(WeakReference(this)).execute(Comment(null, postId, "Comment ${adapter.itemCount+1}", Calendar.getInstance().timeInMillis))
    }

    private fun loadComments() {
        rvComments.adapter = adapter
        rvComments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        refreshComments()
    }

    private fun refreshComments() {
        LoadDataTask(WeakReference(this)).execute(postId)
    }

    class CommentListener(private val weakReference: WeakReference<PostDetailsActivity>) : CommentRecyclerViewAdapter.Listeners {
        override fun onItemClickByPosition(position: Int) {
            DeleteDataTask(WeakReference(weakReference.get()!!)).execute((weakReference.get() as PostDetailsActivity).adapter.getItemByPosition(position))
        }

        override fun onItemClick(commentId: Long) {
            if (weakReference.get() != null) {
                val db = CustomSqliteOpenHelper(weakReference.get())
                db.deleteComment(commentId)

                (weakReference.get() as PostDetailsActivity).refreshComments()
            }
        }
    }

    class LoadDataTask(private var weakReference: WeakReference<PostDetailsActivity>) : AsyncTask<Long, Void, List<Comment>>() {
        override fun doInBackground(vararg params: Long?): List<Comment> {
            return weakReference.get()?.db?.getAllCommentsByPostId(params[0]?: 0)?: arrayListOf()
        }

        override fun onPostExecute(result: List<Comment>) {
            super.onPostExecute(result)

            weakReference.get()?.adapter?.loadData(result)
            weakReference.get()?.adapter?.notifyDataSetChanged()
        }
    }

    class InsertDataTask(private var weakReference: WeakReference<PostDetailsActivity>) : AsyncTask<Comment, Void, Void>() {

        override fun doInBackground(vararg params: Comment): Void? {
            weakReference.get()?.db?.insertComment(params[0])
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            weakReference.get()?.refreshComments()
        }
    }

    class DeleteDataTask(private var weakReference: WeakReference<PostDetailsActivity>) : AsyncTask<Comment, Void, Void>() {
        override fun doInBackground(vararg params: Comment): Void? {
            //weakReference.get()?.db?.deleteComment(params[0])
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            weakReference.get()?.refreshComments()
        }
    }
}
