package org.simonolander.lambda.campaignmain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.simonolander.lambda.R

/**
 * A simple [Fragment] subclass.
 * Use the [C1L1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class C1L1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chapter_tutorial_level_1, container, false)
        view.findViewById<View>(R.id.content_root)?.setOnClickListener { completeLevel() }
        return view
    }

    private fun completeLevel() {
        findNavController().navigate(R.id.action_complete_c1l1)
    }
}
