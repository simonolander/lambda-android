package org.simonolander.lambda.campaignmain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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
        view.findViewById<View>(R.id.content_root)
            ?.setOnClickListener {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<C1L2Fragment>(R.id.tutorial_page_container)
                }
            }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            C1L1Fragment()
    }
}
