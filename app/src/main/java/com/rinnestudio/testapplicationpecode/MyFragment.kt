package com.rinnestudio.testapplicationpecode

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyFragment(private val position: Int) : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()
    private val notificationManager = NotificationManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_layot, container, false)

        view.findViewById<TextView>(R.id.textView2).text = (position+1).toString()

        view.findViewById<FloatingActionButton>(R.id.nextFragmentFab).setOnClickListener {
            viewModel.destination.value = position + 1
        }

        view.findViewById<FloatingActionButton>(R.id.deleteFragmentFab).apply {
            visibility = if (position == 0) {
                View.GONE
            } else
                View.VISIBLE

            setOnClickListener {
                notificationManager.removeNotification(requireContext(),position)
                viewModel.destination.value = position - 1
            }
        }

        view.findViewById<MaterialCardView>(R.id.createNotificationCardView).setOnClickListener {
           notificationManager.createNotification(requireActivity(),position)
        }

        return view
    }

}