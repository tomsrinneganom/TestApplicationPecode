package com.rinnestudio.testapplicationpecode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var screenSlidePagerAdapter: ScreenSlidePagerAdapter
    private var viewPageItemCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)

        NotificationManager().createNotificationChannel(this)

        screenSlidePagerAdapter = ScreenSlidePagerAdapter(this)

        viewPager.adapter = screenSlidePagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateItemCount(position)
            }
        })

        checkIsCalledFromNotification()

        viewModel.destination.observe(this) {
            if (it == viewPager.currentItem - 1) {
                screenSlidePagerAdapter.removeFragment(it)
            }
            changeCurrentItem(it)
        }
    }

    private fun checkIsCalledFromNotification(mIntent: Intent? = intent) {
        val notificationId: Int? = mIntent?.getIntExtra(NOTIFICATION_INTENT_NAME, -1)

        if (notificationId != null && notificationId != -1) {
            changeCurrentItem(notificationId)
        }
    }

    private fun changeCurrentItem(destination: Int) {
        updateItemCount(destination)
        viewPager.currentItem = destination
    }

    private fun updateItemCount(destination: Int) {
        if (viewPageItemCount <= destination + 2) {
            viewPageItemCount = destination + 2
            screenSlidePagerAdapter.notifyDataSetChanged()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkIsCalledFromNotification(intent)
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = viewPageItemCount

        override fun createFragment(position: Int): Fragment {
            Log.i("Log_tag", "$position, $itemCount")
            return MyFragment(position)
        }

        fun removeFragment(position: Int) {
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, viewPageItemCount)
        }
    }


}