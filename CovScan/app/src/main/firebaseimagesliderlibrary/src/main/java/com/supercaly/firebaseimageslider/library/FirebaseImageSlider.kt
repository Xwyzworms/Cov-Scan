package com.supercaly.firebaseimageslider.library

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.stfalcon.imageviewer.StfalconImageViewer
import java.util.ArrayList

class FirebaseImageSlider(c: Context,
                          attrs: AttributeSet?): RelativeLayout(c, attrs) {

    companion object {
        private const val TAG = "FirebaseImageSlider"
    }

    private val mReference = FirebaseStorage.getInstance().reference

    private var mPlaceholderResource: Int = R.color.default_placeholder_color

    private var mViewPager: ViewPager

    private var mAdapter: FisAdapter

    constructor(c: Context) : this(c, null)

    init {
        LayoutInflater.from(c).inflate(R.layout.fis_layout, this, true)

        mViewPager = findViewById(R.id.fis_view_pager)
        mAdapter = FisAdapter()

        mViewPager.adapter = mAdapter

        //Get the placeholder resource from attributes
        attrs?.let {
            val typedArray = c.obtainStyledAttributes(it, R.styleable.FirebaseImageSlider, 0, 0)
            try {
                mPlaceholderResource = typedArray.getResourceId(
                    R.styleable.FirebaseImageSlider_placeholder_image,
                    R.color.default_placeholder_color
                )

            } finally {
                typedArray.recycle()
            }
        }

        Log.i(TAG, "init: $TAG Initialized!!")
    }

    /**
     * Method called to set pass the images urls
     *
     * <p> Accept a list of strings representing the urls of the images.
     * For every element we convert it to a {@link StorageReference} and try
     * to get the download url from Firebase if we get it we create a new
     * {@link FisSlider} with the url. </p>
     *
     * @param list List<String>? list of urls passed by the user; it can be null
     */
    fun images(list: List<String>?) {
        if (list != null) {
            //The list contain elements... loop throw them
            for (value: String in list) {
                //Create StorageReference and get the download url
                mReference.child(value).downloadUrl
                    .addOnSuccessListener { url ->
                        //Create a new FisSlider with the url and add it to the FisAdapter
                        Log.d(TAG, "images: Adding image $url")
                        mAdapter.addSlider(FisSlider(context, url.toString(), mPlaceholderResource, ::onClick))
                    }
                    .addOnFailureListener {
                        //Cannot get download url... skip this photo
                        Log.e(TAG, "images: Cannot get url for $value")
                    }
            }
        }
    }

    /**
     * Method called to load the images in the {@link StfalconImageViewer}
     */
    private fun onClick() {
        // TODO: 28/01/19 Customize how the images will be loaded, such as the placeholder or the error
        StfalconImageViewer.Builder(context, mAdapter.getUrls()) { imageView, image ->
            Glide.with(imageView)
                .load(image)
                .into(imageView)
        }.show()
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putStringArrayList("imageUrls", mAdapter.getUrls() as ArrayList<String>)
        Log.i(TAG, "onSaveInstanceState: $bundle")
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        Log.i(TAG, "onRestoreInstanceState: ")
        var newState = state
        if (newState is Bundle) {
            val bundle = state as Bundle?
            val urls = bundle?.getStringArrayList("imageUrls")
            if (urls != null)
                for (url in urls)
                    mAdapter.addSlider(FisSlider(context, url, mPlaceholderResource, ::onClick))
            newState = bundle?.getParcelable("superState")
        }
        Log.i(TAG, "onRestoreInstanceState: $newState")
        super.onRestoreInstanceState(newState)
    }
}