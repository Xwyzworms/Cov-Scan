package com.supercaly.firebaseimageslider.library

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * Adapter class for manage the ViewPager
 */
class FisAdapter: PagerAdapter() {

    companion object {
        private const val TAG = "FisAdapter"
    }

    private var mSliders: ArrayList<FisSlider> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * Check if the {@link FisSlider} is present
     * and add it to the list
     *
     * @param slider the {@link FisSlider} to add
     */
    fun addSlider(slider: FisSlider) {
        if (!mSliders.contains(slider)) {
            Log.i(TAG, "addSlider: ")
            mSliders.add(slider)
            notifyDataSetChanged()
        }
    }

    /**
     * Returns a list of all the urls
     */
    fun getUrls(): List<String> {
        val urls = ArrayList<String>()
        for (slider in mSliders) urls.add(slider.url)
        return urls
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d(TAG, "instantiateItem: $position")

        container.addView(mSliders[position].imageView)
        return mSliders[position].imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        Log.d(TAG, "destroyItem: $position")

        container.removeView(mSliders[position].imageView)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean { return view == `object` }

    override fun getCount(): Int { return mSliders.size }
}
