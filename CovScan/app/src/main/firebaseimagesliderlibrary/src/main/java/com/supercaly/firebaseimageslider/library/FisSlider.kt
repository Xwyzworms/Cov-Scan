package com.supercaly.firebaseimageslider.library

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Class raprensenting a single element of
 * the {@link ViewPager}
 *
 * @param context the {@link Context} of the caller
 * @param url String with the url of the image
 * @param placeholderRes resource id for the placeholder
 * @param onClick function called when user click the FisSlider
 */
class FisSlider(context: Context,
                var url: String,
                placeholderRes: Int,
                onClick: () -> Unit) {

    /**
     * The {@link ImageView} in witch the image is
     * loaded
     */
    var imageView: ImageView = ImageView(context)

    init {
        //Init ImageView
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)

        //Load the image in the view
        Glide.with(context)
            .load(url)
            .apply(RequestOptions()
                .centerCrop()
                .placeholder(placeholderRes))
            .into(imageView)

        //Add onClick
        imageView.setOnClickListener { onClick() }
    }

    /**
     * Redefine the way two FisSliders are compared
     *
     * <p> Two FisSliders are equals if the url of the
     * image is the same. </p>
     */
    override fun equals(other: Any?): Boolean {
        if (other is FisSlider)
            return url == other.url
        return super.equals(other)
    }
}