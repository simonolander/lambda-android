package org.simonolander.lambda.campaignmain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.simonolander.lambda.R

/**
 * A simple [Fragment] subclass.
 * Use the [C1L2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class C1L2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chapter_tutorial_level_2, container, false)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            C1L2Fragment()
    }
}
