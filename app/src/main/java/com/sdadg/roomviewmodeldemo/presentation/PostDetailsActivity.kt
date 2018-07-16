package com.sdadg.roomviewmodeldemo.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.sdadg.roomviewmodeldemo.R
import com.sdadg.roomviewmodeldemo.data.ObjectBoxApplication
import com.sdadg.roomviewmodeldemo.data.adapters.CommentRecyclerViewAdapter
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.data.entities.Comment_
import com.sdadg.roomviewmodeldemo.data.old.CustomSqliteOpenHelper
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.content_feed.*
import java.lang.ref.WeakReference
import java.util.*

class PostDetailsActivity : AppCompatActivity() {

    var postId = 0L
    var commentListener = CommentListener(WeakReference(this))
    val adapter = CommentRecyclerViewAdapter(commentListener)
    //val db: IDataRepository = RoomRepository(this)

    lateinit var commentBox: Box<Comment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        commentBox = (application as ObjectBoxApplication).boxStore.boxFor<Comment>()
        setContentView(R.layout.activity_feed)

        postId = intent.getLongExtra("postId", 0)
        val postTitle = intent.getStringExtra("postTitle")
        toolbar.title = "Comments for ${postTitle}"
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            addComment();
        }

        loadComments()
    }

    private fun addComment() {
        commentBox.put(Comment(0, postId, "Comment ${adapter.itemCount+1}", Calendar.getInstance().timeInMillis))
        refreshComments()
    }

    private fun loadComments() {
        val commentList = commentBox.query().equal(Comment_.postId, postId).build().find()
//        val commentList = db.getAllCommentsByPostId(postId)
        rvComments.adapter = adapter
        rvComments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.loadData(commentList)
    }

    private fun refreshComments() {
        loadComments()
        adapter.notifyDataSetChanged()
    }

    class CommentListener(val weakReference: WeakReference<PostDetailsActivity>) : CommentRecyclerViewAdapter.Listeners {
        override fun onItemClickByPosition(position: Int) {
            /*val db = RoomRepository(weakReference.get() as PostDetailsActivity)
            db.deleteComment((weakReference.get() as PostDetailsActivity).adapter.getItemByPosition(position))

            (weakReference.get() as PostDetailsActivity).refreshComments()*/
        }

        override fun onItemClick(commentId: Long) {
            if (weakReference.get() != null) {

                val db = CustomSqliteOpenHelper(weakReference.get())
                db.deleteComment(commentId)

                (weakReference.get() as PostDetailsActivity).refreshComments()
            }
        }
    }
}
