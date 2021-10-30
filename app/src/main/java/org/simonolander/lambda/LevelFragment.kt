package org.simonolander.lambda

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import org.simonolander.lambda.campaignmain.C1L1Fragment

class LevelFragment : Fragment() {

    private lateinit var viewModel: TutorialViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.level, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TutorialViewModel::class.java]
        viewModel.getPage().observe(viewLifecycleOwner) {
            displayTutorialPage(it)
        }
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add<C1L1Fragment>(R.id.tutorial_page_container)
        }
    }

    private fun displayTutorialPage(pageIndex: Int?) {
        val fragmentClass = when (pageIndex) {
            0 -> C1L1Fragment::class.java
            else -> C1L1Fragment::class.java
        }
    }

}
